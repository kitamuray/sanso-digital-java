/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
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
    this.cell = new TankCell(this.tank, composition);
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

  /// 貯槽モデルの永続化クラスのインスタンスを生成して返す.
  ///
  /// @return 貯槽モデルの永続化クラスのインスタンス.
  TankModelEntity newEntity() {
    return new TankModelEntity(this.cell.newEntity());
  }

  /// 指定された永続化クラスのインスタンスを反映する.
  ///
  /// @param entity 永続化クラスのインスタンス.
  /// @throws NullPointerException `entity` の構成要素に `null` が含まれている場合.
  void accept(TankModelEntity entity) {
    Preconditions.checkNotNull(entity);
    Preconditions.checkNotNull(entity.cellEntity());

    this.cell.accept(entity.cellEntity());
  }

  /// 履歴リストを初期化する.
  void clearHistories() {
    this.historyListMap.values().forEach(List::clear);
  }

  /// 履歴リストへ追記する.
  void addHistories() {
    this.tank.monitorMap().forEach((fileName, monitor) -> {
      List<Double> monitorList = this.historyListMap.get(fileName);
      monitorList.add(monitor.applyAsDouble(this.cell));
    });
  }

  /// 履歴リストのマップを返す.
  ///
  /// @return 履歴リストのマップ.
  Map<String, List<Double>> historyListMap() {
    return this.historyListMap;
  }

}
