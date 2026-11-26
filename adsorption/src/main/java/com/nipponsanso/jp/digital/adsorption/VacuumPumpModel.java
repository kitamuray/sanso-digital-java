/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/// 真空ポンプモデル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
final class VacuumPumpModel {

  /// 真空ポンプ.
  private final VacuumPump vacuumPump;

  /// セル.
  private final FluidCell2 cell;

  /// フェイス.
  private final FluidFace2 face;

  /// モニタ値の可変リストの不変マップ.
  private final Map<String, List<Double>> historyListMap;

  /// 真空ポンプモデルのインスタンスを生成する.
  ///
  /// @param vacuumPump 真空ポンプ.
  /// @param composition 組成.
  VacuumPumpModel(VacuumPump vacuumPump, Composition composition) {
    this.vacuumPump = vacuumPump;
    this.cell = FluidCell2.newInstance(composition);
    this.face = FluidFace2.newInstance(composition);
    this.historyListMap = this.vacuumPump.monitorMap().keySet().stream()
        .collect(
            ImmutableMap.toImmutableMap(
                key -> key,
                _ -> new ArrayList<>()));

    this.vacuumPump.set(this.cell);
  }

  /// セルを返す.
  ///
  /// @return セル.
  FluidCell2 cell() {
    return this.cell;
  }

  /// フェイスを返す.
  ///
  /// 境界外から中への向きを正とする.
  ///
  /// @return フェイス.
  FluidFace2 face() {
    return this.face;
  }

  void clearHistories() {
    this.historyListMap.values().forEach(List::clear);
  }

  void addHistories() {
    this.vacuumPump.monitorMap().forEach(
        (fileName, monitor) -> this.historyListMap.get(fileName).add(switch (monitor) {
          case Monitor.FluidCellMonitor cm -> cm.applyAsDouble(this.cell);
          case Monitor.FluidFaceMonitor fm -> fm.applyAsDouble(this.face);
          default -> throw new IllegalStateException();
        }));
  }

  Map<String, List<Double>> historyListMap() {
    return this.historyListMap;
  }
}
