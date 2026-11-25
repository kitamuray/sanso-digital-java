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
interface FluidFace2 extends Face2 {

  /// 流体の質量流量を返す.
  ///
  /// @return 流体の質量流量 (kg s<sup>-1</sup>).
  double getMassFlow();

  /// 流体の質量流量を設定する.
  ///
  /// @param massFlow 流体の質量流量 (kg s<sup>-1</sup>).
  void setMassFlow(double massFlow);

  /// 流体のエネルギ流量を返す.
  ///
  /// @return 流体のエネルギ流量 (W).
  double getEnergyFlow();

  /// 流体のエネルギ流量を設定する.
  ///
  /// @param energyFlow 流体のエネルギ流量 (W).
  void setEnergyFlow(double energyFlow);

  /// 流体を含むフェイスの成分の不変マップを返す.
  ///
  /// @return 流体を含むフェイスの成分の不変マップ.
  @Override
  Map<ComponentKey, ? extends ComponentValue> componentValue();

  /// 流体を含むフェイスの成分値.
  interface ComponentValue extends Face2.ComponentValue {

    /// 流体成分の化学種流量を返す.
    ///
    /// @return 流体成分の化学種流量 (kg s<sup>-1</sup>).
    double getSpeciesFlow();

    /// 流体成分の化学種流量を設定する.
    ///
    /// @param speciesFlow 流体成分の化学種流量 (kg s<sup>-1</sup>).
    void setSpeciesFlow(double speciesFlow);

  } // ComponentValue

}
