/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital;

import com.google.common.base.Preconditions;

/// 修正 Eucken の低圧下における純成分気体の熱伝導率推算式.
///
/// The Properties of Gases and Liquid, 6th edition, p.547, eq.(11.3-5).
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class ModifiedEuckenThermalConductivity {

  private ModifiedEuckenThermalConductivity() {}

  /// 熱伝導率の推算値を返す.
  ///
  /// @param viscosity 粘性係数 (Pa).
  /// @param molarSpecificConstantVolumeSpecificHeat 定積モル比熱
  ///     (J mol<sup>-1</sup> K<sup>-1</sup>).
  /// @param molecularWeight 分子量.
  /// @return 熱伝導率の推算値 (W m<sup>-1</sup> K<sup>-1</sup>).
  /// @throws IllegalArgumentException `viscosity`, `molarSpecificConstantVolumeSpecificHeat`,
  ///     `molecularWeight` のいずれかが 0 以下の場合.
  public static double calculate(
      double viscosity, double molarSpecificConstantVolumeSpecificHeat, double molecularWeight) {
    Preconditions.checkArgument(viscosity > 0.);
    Preconditions.checkArgument(molarSpecificConstantVolumeSpecificHeat > 0.);
    Preconditions.checkArgument(molecularWeight > 0.);

    return viscosity * molarSpecificConstantVolumeSpecificHeat / (molecularWeight * 1e-3)
        * (1.32
        + 1.77 / (molarSpecificConstantVolumeSpecificHeat / PhysicalConstants.MOLAR_GAS_CONSTANT));
  }

}
