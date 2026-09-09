/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import java.util.Map;

/// 流体を含むフェイス.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class FluidFace extends Face {

  /// 質量流量の位置.
  private static final int MASS_FLOW = Face.SIZE;

  /// エネルギ流量の位置.
  private static final int ENERGY_FLOW = MASS_FLOW + 1;

  /// 変数の数.
  static final int SIZE = ENERGY_FLOW + 1;

  /// 流体を含むフェイスのインスタンスを生成する.
  FluidFace() {}

  /// 質量流量を返す.
  ///
  /// @return 質量流量 (kg ms<sup>-1</sup>).
  double getMassFlow() {
    return this.doubleArray()[this.offset() + MASS_FLOW];
  }

  /// 質量流量を設定する.
  ///
  /// @param massFlow 新たな質量流量 (kg s<sup>-1</sup>).
  void setMassFlow(double massFlow) {
    this.doubleArray()[this.offset() + MASS_FLOW] = massFlow;
  }

  /// エネルギ流量を返す.
  ///
  /// @return エネルギ流量 (W).
  double getEnergyFlow() {
    return this.doubleArray()[this.offset() + ENERGY_FLOW];
  }

  /// エネルギ流量を設定する.
  ///
  /// @param energyFlow 新たなエネルギ流量 (W).
  void setEnergyFlow(double energyFlow) {
    this.doubleArray()[this.offset() + ENERGY_FLOW] = energyFlow;
  }

  /// {@inheritDoc}
  ///
  /// @return {@inheritDoc}
  @Override
  abstract Map<ComponentKey, ? extends ComponentValue> componentMap();

  /// 流体を含むフェイスの成分値.
  abstract static class ComponentValue extends Face.ComponentValue {

    /// 化学種流量の位置.
    private static final int SPECIES_FLOW = Face.ComponentValue.SIZE;

    /// 変数の数.
    static final int SIZE = SPECIES_FLOW + 1;

    /// 流体を含むフェイスの成分値のインスタンスを生成する.
    ComponentValue() {}

    /// 化学種流量を返す.
    ///
    /// @return 化学種流量 (kg s<sup>-1</sup>).
    double getSpeciesFlow() {
      return this.doubleArray()[this.offset() + SPECIES_FLOW];
    }

    /// 化学種流量を設定する.
    ///
    /// @param speciesFlow 新たな化学種流量 (kg s<sup>-1</sup>).
    void setSpeciesFlow(double speciesFlow) {
      this.doubleArray()[this.offset() + SPECIES_FLOW] = speciesFlow;
    }

  } // ComponentValue

}
