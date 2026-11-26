/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/// 境界モデル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
final class PortModel {

  /// 境界.
  private final Port port;

  /// セル.
  private final FluidCell2 cell;

  /// フェイス.
  ///
  /// 境界外から中への向きを正とする.
  private final FluidFace2 face;

  /// モニタ値の可変リストの不変マップ.
  private final Map<String, List<Double>> historyListMap;

  /// 境界モデルのインスタンスを生成する.
  ///
  /// @param composition 組成.
  PortModel(Port port, Composition composition) {
    this.port = port;
    this.cell = FluidCell2.newInstance(composition);
    this.face = FluidFace2.newInstance(composition);
    this.historyListMap = this.port.monitorMap().keySet().stream()
        .collect(
            ImmutableMap.toImmutableMap(
                key -> key,
                _ -> new ArrayList<>()));

    this.port.set(this.cell);
  }

  /// 境界を返す.
  ///
  /// @return 境界.
  Port port() {
    return this.port;
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

  public Map<String, List<Double>> historyListMap() {
    return this.historyListMap;
  }

  void clearHistories() {
    this.historyListMap.values().forEach(List::clear);
  }

  void addHistories() {
    this.port.monitorMap().forEach(
        (fileName, monitor) -> this.historyListMap.get(fileName).add(switch (monitor) {
          case Monitor.FluidCellMonitor cm -> cm.applyAsDouble(this.cell);
          case Monitor.FluidFaceMonitor fm -> fm.applyAsDouble(this.face);
          default -> throw new IllegalStateException();
        }));
  }

}
