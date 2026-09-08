/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/// 貯槽モデル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
final class TankModel {

  /// 貯槽.
  private final Tank tank;

  /// セル.
  private final TankCell cell;

  /// モニタ値の可変リストの不変マップ.
  private final Map<String, List<Double>> historyListMap;

  /// 貯槽モデルのインスタンスを生成する.
  ///
  /// @param tank 貯槽.
  /// @param composition 組成.
  TankModel(Tank tank, Composition composition) {
    this.tank = tank;
    this.cell = new TankCell(composition);
    this.historyListMap = this.tank.monitorMap().keySet().stream()
        .collect(
            ImmutableMap.toImmutableMap(
                fileName -> fileName,
                _ -> new ArrayList<>()));
  }

  /// セルを返す.
  TankCell cell() {
    return this.cell;
  }

  /// 指定された永続化オブジェクトへ情報を書き込む.
  ///
  /// @param entity 永続化オブジェクト.
  void write(TankModelEntity entity) {
    this.cell.write(entity.getCellEntity());
  }

  /// 指定された永続化オブジェクトから情報を読み出す.
  ///
  /// @param entity 永続化オブジェクト.
  void read(TankModelEntity entity) {
    this.cell.read(entity.getCellEntity());
  }

  void clearHistories() {
    this.historyListMap.values().forEach(List::clear);
  }

  void addHistories() {
    this.tank.monitorMap().forEach((fileName, monitor) -> {
      List<Double> monitorList = this.historyListMap.get(fileName);
      monitorList.add(monitor.applyAsDouble(this.cell));
    });
  }

  Map<String, List<Double>> historyListMap() {
    return this.historyListMap;
  }

}
