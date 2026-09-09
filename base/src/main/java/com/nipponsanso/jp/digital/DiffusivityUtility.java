/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital;

/// 物質拡散係数のユーティリティ.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
final class DiffusivityUtility {

  private DiffusivityUtility() {}

  /// 指定された無次元温度における物質拡散係数の衝突積分を返す.
  ///
  /// @param dimensionlessTemperature 無次元温度 (-).
  /// @return 指定された無次元温度における物質拡散係数の衝突積分 _&Omega;_<sub>_D_</sub> (-).
  static double collisionIntegral(double dimensionlessTemperature) {
    final double a = 1.06036;
    final double b = 0.15610;
    final double c = 0.19300;
    final double d = 0.47635;
    final double e = 1.03587;
    final double f = 1.52996;
    final double g = 1.76474;
    final double h = 3.89411;

    return a / Math.pow(dimensionlessTemperature, b)
        + c / Math.exp(d * dimensionlessTemperature)
        + e / Math.exp(f * dimensionlessTemperature)
        + g / Math.exp(h * dimensionlessTemperature);
  }

}
