/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital;

import com.google.common.base.Preconditions;

/// Chapman と Enskog による二成分気体の物質拡散係数推算式.
///
/// The Properties of Gases and Liquid, 6th edition, p.613, eq.(12.4-1).
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class ChapmanEnskogDiffusivity {

  private ChapmanEnskogDiffusivity() {}

  /// 二成分系気体の物質拡散係数の推算値を返す.
  ///
  /// @param temperature 温度 (K).
  /// @param pressure 圧力 (Pa).
  /// @param molecularWeightA 成分 A の分子量.
  /// @param molecularWeightB 成分 B の分子量.
  /// @param lennardJonesPotentialA 成分 A のレナード-ジョーンズ・ポテンシャル.
  /// @param lennardJonesPotentialB 成分 B のレナード-ジョーンズ・ポテンシャル.
  /// @return 二成分系気体の物質拡散係数の推算値 (m<sup>2</sup> s<sup>-1</sup>).
  /// @throws IllegalArgumentException `temperature`, `pressure`,
  ///     `molecularWeightA`, `molecularWeightB` のいずれかが 0 以下の場合.
  /// @throws NullPointerException `lennardJonesPotentialA`, `lennardJonesPotentialB`
  ///     のいずれかが `null` の場合.
  public static double calculate(
      double temperature, double pressure,
      double molecularWeightA, double molecularWeightB,
      LennardJonesPotential lennardJonesPotentialA, LennardJonesPotential lennardJonesPotentialB) {
    Preconditions.checkArgument(
        temperature > 0. && pressure > 0. && molecularWeightA > 0. && molecularWeightB > 0.);
    Preconditions.checkNotNull(lennardJonesPotentialA);
    Preconditions.checkNotNull(lennardJonesPotentialB);

    // パラメータの混合則.
    final double molecularWeight12 =
        Math.sqrt(2. / (1. / molecularWeightA + 1. / molecularWeightB));
    final double sigma2 =
        0.25 * (lennardJonesPotentialA.sigma() + lennardJonesPotentialB.sigma())
             * (lennardJonesPotentialA.sigma() + lennardJonesPotentialB.sigma());
    final double epsilonK =
        Math.sqrt(lennardJonesPotentialA.epsilonK() * lennardJonesPotentialB.epsilonK());

    return 0.00266 * temperature * Math.sqrt(temperature)
        / (pressure * 1e-5) / molecularWeight12 / sigma2
        / DiffusivityUtility.collisionIntegral(temperature / epsilonK) * 1e-4;
  }

}
