/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

/// 固相の熱伝導率.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public abstract class ThermalConductivity {

  /// 固相の熱伝導率のインスタンスを生成する.
  ThermalConductivity() {}

  /// 指定された熱力学的温度における熱伝導率を返す.
  ///
  /// @param temperature 熱力学的温度 (K).
  /// @return 指定された熱力学的温度における熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>).
  /// @throws IllegalArgumentException `temperature` が範囲外の場合.
  abstract double thermalConductivity(double temperature);

}
