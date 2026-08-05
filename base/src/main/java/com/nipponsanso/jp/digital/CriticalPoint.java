/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital;

import com.google.common.base.Preconditions;

///
/// 臨界点.
///
/// @param temperature 熱力学的温度 (K).
/// @param pressure 絶対圧力 (Pa).
/// @param molarVolume モル体積 (m<sup>3</sup> mol<sup>-1</sup>).
/// @since 1.0
/// @author KITAMURA Yuichiro
///
public record CriticalPoint(double temperature, double pressure, double molarVolume) {

  ///
  /// 臨界点のインスタンスを生成する.
  ///
  /// @param temperature 熱力学的温度 (K).
  /// @param pressure 絶対圧力 (Pa).
  /// @param molarVolume モル体積 (m<sup>3</sup> mol<sup>-1</sup>).
  /// @throws IllegalArgumentException `temperature`, `pressure`, `molarVolume`
  ///     のいずれかが 0 以下の場合
  ///     (`temperature <= 0 || pressure <= 0 || molarVolume <= 0`).
  ///
  public CriticalPoint {
    Preconditions.checkArgument(
        temperature > 0., "temperature = %e", temperature);
    Preconditions.checkArgument(
        pressure > 0., "pressure = %e", pressure);
    Preconditions.checkArgument(
        molarVolume > 0., "molarVolume = %e", molarVolume);
  }

}
