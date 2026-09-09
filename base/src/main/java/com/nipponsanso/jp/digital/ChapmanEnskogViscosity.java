/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital;

import com.google.common.base.Preconditions;

/// Chapman と Enskog による低圧下における純成分気体の粘性係数推算式.
///
/// The Properties of Gases and Liquid, 6th edition, p.457, eq.(10.3-9).
///
/// @since 1.0
/// @author KITAMURA YUichiro
public final class ChapmanEnskogViscosity {

  private ChapmanEnskogViscosity() {}

  /// 粘性係数の推算値を返す.
  ///
  /// @param temperature 温度 (K).
  /// @param molecularWeight 分子量.
  /// @param lennardJonesPotential レナード-ジョーンズ・ポテンシャル.
  /// @return 粘性係数の推算値 (Pa s).
  /// @throws IllegalArgumentException `temperature` または `molecularWeight` が 0 以下の場合
  ///     (`temperature <= 0 || molecularWeight <= 0`).
  /// @throws NullPointerException `lennardJonesPotential` が `null` の場合.
  public static double calculate(
      double temperature, double molecularWeight, LennardJonesPotential lennardJonesPotential) {
    Preconditions.checkArgument(temperature > 0.);
    Preconditions.checkArgument(molecularWeight > 0.);
    Preconditions.checkNotNull(lennardJonesPotential);

    double omegaV = ViscosityUtility.collisionIntegral(
        temperature / lennardJonesPotential.epsilonK());

    return 26.693 * Math.sqrt(molecularWeight * temperature)
        / lennardJonesPotential.sigma() / lennardJonesPotential.sigma() / omegaV * 1e-7;
  }

}
