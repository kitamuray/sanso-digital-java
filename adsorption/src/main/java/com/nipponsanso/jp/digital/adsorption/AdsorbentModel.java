/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.nipponsanso.jp.digital.PhysicalConstants;

/// 吸着剤モデル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
final class AdsorbentModel {

  /// 吸着剤.
  private final Adsorbent adsorbent;

  /// 吸着剤モデルのインスタンスを生成する.
  ///
  /// @param adsorbent 吸着剤.
  AdsorbentModel(Adsorbent adsorbent) {
    this.adsorbent = adsorbent;
  }

  /// 吸着剤の比熱を返す.
  ///
  /// @param cell セル.
  /// @return 吸着剤の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  double adsorbentSpecificHeat(AdsorbentCell2 cell) {
    return this.adsorbent.specificHeat().specificHeat(cell.getTemperature());
  }

  /// 吸着質成分のモル定積比熱を返す.
  ///
  /// @param cell セル.
  /// @param componentKey 成分のキー.
  /// @return 吸着質成分のモル定積比熱 (J mol<sup>-1</sup> K<sup>-1</sup>).
  double molarSpecificConstantVolumeSpecificHeat(AdsorbentCell2 cell, ComponentKey componentKey) {
    return componentKey.thermodynamicFunction()
        .molarSpecificConstantPressureSpecificHeat(cell.getTemperature())
        - PhysicalConstants.MOLAR_GAS_CONSTANT;
  }

}
