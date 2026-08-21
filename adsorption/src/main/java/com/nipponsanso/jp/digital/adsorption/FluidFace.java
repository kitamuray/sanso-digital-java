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

  /// 質量流束の位置.
  private static final int MASS_FLUX = Face.SIZE;

  /// エネルギ流束の位置.
  private static final int ENERGY_FLUX = MASS_FLUX + 1;

  /// 変数の数.
  static final int SIZE = ENERGY_FLUX + 1;

  /// 流体を含むフェイスのインスタンスを生成する.
  FluidFace() {}

  /// 質量流束を返す.
  ///
  /// @return 質量流束 (kg m<sup>-2</sup> s<sup>-1</sup>).
  double getMassFlux() {
    return this.doubleArray()[this.offset() + MASS_FLUX];
  }

  /// 質量流束を設定する.
  ///
  /// @param massFlux 新たな質量流束 (kg m<sup>-2</sup> s<sup>-1</sup>).
  void setMassFlux(double massFlux) {
    this.doubleArray()[this.offset() + MASS_FLUX] = massFlux;
  }

  /// エネルギ流束を返す.
  ///
  /// @return エネルギ流束 (W m<sup>-2</sup>).
  double getEnergyFlux() {
    return this.doubleArray()[this.offset() + ENERGY_FLUX];
  }

  /// エネルギ流束を設定する.
  ///
  /// @param energyFlux 新たなエネルギ流束 (W m<sup>-2</sup>).
  void setEnergyFlux(double energyFlux) {
    this.doubleArray()[this.offset() + ENERGY_FLUX] = energyFlux;
  }

  /// {@inheritDoc}
  ///
  /// @return {@inheritDoc}
  @Override
  abstract Map<ComponentKey, ? extends ComponentValue> componentMap();

  /// 流体を含むフェイスの成分値.
  abstract static class ComponentValue extends Face.ComponentValue {

    /// 化学種流束の位置.
    private static final int SPECIES_FLUX = Face.ComponentValue.SIZE;

    /// 変数の数.
    static final int SIZE = SPECIES_FLUX + 1;

    /// 流体を含むフェイスの成分値のインスタンスを生成する.
    ComponentValue() {}

    /// 化学種流束を返す.
    ///
    /// @return 化学種流束 (kg m<sup>-2</sup> s<sup>-1</sup>).
    double getSpeciesFlux() {
      return this.doubleArray()[this.offset() + SPECIES_FLUX];
    }

    /// 化学種流束を設定する.
    ///
    /// @param speciesFlux 新たな化学種流束 (kg m<sup>-2</sup> s<sup>-1</sup>).
    void setSpeciesFlux(double speciesFlux) {
      this.doubleArray()[this.offset() + SPECIES_FLUX] = speciesFlux;
    }

  } // ComponentValue

}
