/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/// 吸着筒領域モデル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
final class ColumnZoneModel {

  /// 領域.
  private final ColumnZone zone;

  /// セルの不変リスト.
  private final List<ColumnCell> cellList;

  /// 履歴情報の不変マップ.
  private final Map<String, List<Double>> historyListMap;

  /// 吸着筒領域モデルのインスタンスを生成する.
  ///
  /// @param zone 領域.
  /// @param cellList セルのリスト.
  ColumnZoneModel(ColumnZone zone, List<ColumnCell> cellList) {
    this.zone = zone;
    this.cellList = ImmutableList.copyOf(cellList);

    this.historyListMap = this.zone.indexMonitorMap().keySet().stream()
        .collect(
            ImmutableMap.toImmutableMap(
                fileName -> fileName,
                _ -> new ArrayList<>()));
  }

  /// セルの不変リストを返す.
  ///
  /// @return セルの不変リスト.
  List<ColumnCell> cellList() {
    return this.cellList;
  }

  /// 履歴情報の不変マップを返す.
  ///
  /// @return 履歴情報の不変マップ.
  Map<String, List<Double>> historyListMap() {
    return this.historyListMap;
  }

  /// 履歴情報を初期化する.
  void clearHistories() {
    this.historyListMap.values().forEach(List::clear);
  }

  /// 履歴情報へ追記する.
  void addHistories() {
    this.zone.indexMonitorMap().forEach((fileName, indexMonitor) -> {
      List<Double> monitorList = this.historyListMap.get(fileName);
      int index = indexMonitor.index();
      Monitor monitor = indexMonitor.monitor();

      monitorList.add(monitor.applyAsDouble(this.cellList.get(index)));
    });
  }

}
