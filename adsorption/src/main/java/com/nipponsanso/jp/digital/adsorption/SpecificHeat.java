/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

/// 固相の比熱.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public abstract class SpecificHeat {

  /// 固相の比熱のインスタンスを生成する.
  SpecificHeat() {}

  /// 指定された熱力学的温度における比熱を返す.
  ///
  /// @param temperature 熱力学的温度 (K).
  /// @return 指定された熱力学的温度における比熱.
  /// @throws IllegalArgumentException `temperature` が範囲外の場合.
  abstract double specificHeat(double temperature);

}
