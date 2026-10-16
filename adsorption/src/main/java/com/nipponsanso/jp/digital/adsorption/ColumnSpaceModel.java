/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/// 吸着筒空間モデル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
final class ColumnSpaceModel {

  /// 空間.
  private final ColumnSpace space;

  /// セル.
  private final ColumnCell cell;

  /// 履歴情報の不変マップ.
  private final Map<String, List<Double>> historyListMap;

  /// 吸着筒空間モデルのインスタンスを生成する.
  ///
  /// @param space 空間.
  /// @param cell セル.
  ColumnSpaceModel(ColumnSpace space, ColumnCell cell) {
    this.space = space;
    this.cell = cell;

    this.historyListMap = this.space.monitorMap().keySet().stream()
        .collect(
            ImmutableMap.toImmutableMap(
                fileName -> fileName,
                _ -> new ArrayList<>()));
  }

  /// セルを返す.
  ///
  /// @return セル.
  ColumnCell cell() {
    return this.cell;
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
    this.space.monitorMap().forEach((fileName, monitor) -> {
      List<Double> monitorList = this.historyListMap.get(fileName);

      monitorList.add(monitor.applyAsDouble(this.cell));
    });
  }

}
