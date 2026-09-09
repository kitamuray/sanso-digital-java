/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital;

/// 粘性係数のユーティリティ.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
final class ViscosityUtility {

  private ViscosityUtility() {}

  /// 指定された無次元温度における粘性係数の衝突積分を返す.
  ///
  /// @param dimensionlessTemperature 無次元温度 (-).
  /// @return 指定された無次元温度における粘性係数の衝突積分 _Ω_<sub>_v_</sub> (-).
  static double collisionIntegral(double dimensionlessTemperature) {
    final double a = 1.16145;
    final double b = 0.14874;
    final double c = 0.52487;
    final double d = 0.77320;
    final double e = 2.16178;
    final double f = 2.43787;

    return a * Math.pow(dimensionlessTemperature, - b)
        +  c * Math.exp(- d * dimensionlessTemperature)
        +  e * Math.exp(- f * dimensionlessTemperature);
  }

}
