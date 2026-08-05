/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital;

///
/// 熱力学関数.
///
/// このインターフェースのインスタンスは不変（immutable）でなければならない.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
///
public interface ThermodynamicFunction {

  ///
  /// 指定された温度における, 標準状態のモル定圧比熱を返す.
  ///
  /// @param temperature 温度 (K).
  /// @return 指定された温度における, 標準状態のモル定圧比熱 (J mol<sup>-1</sup> K<sup>-1</sup>).
  /// @throws IllegalArgumentException `temperature` が範囲外の場合.
  ///
  double molarSpecificConstantPressureSpecificHeat(double temperature);

  ///
  /// 指定された温度における, 標準状態のモル比エンタルピを返す.
  ///
  /// @param temperature 温度 (K).
  /// @return 指定された温度における, 標準状態のモル比エンタルピ (J mol<sup>-1</sup>).
  /// @throws IllegalArgumentException `temperature` が範囲外の場合.
  ///
  double molarSpecificEnthalpy(double temperature);

  ///
  /// 指定された温度における, 標準状態のモル比エントロピを返す.
  ///
  /// デフォルト実装では常に [UnsupportedOperationException] 例外を発生する.
  ///
  /// @param temperature 温度 (K).
  /// @return 指定された温度における, 標準状態のモル比エントロピ (J mol<sup>-1</sup> K<sup>-1</sup>).
  /// @throws UnsupportedOperationException `molarSpecificEntropy`
  ///     オペレーションがサポートされていない場合.
  /// @throws IllegalArgumentException `temperature` が範囲外の場合.
  ///
  default double molarSpecificEntropy(@SuppressWarnings("unused") double temperature) {
    throw new UnsupportedOperationException();
  }

  ///
  /// 指定された温度における, 標準状態のモル比ギブスエネルギを返す.
  ///
  /// デフォルト実装では [#molarSpecificEnthalpy(double)] と
  /// [#molarSpecificEntropy(double)] を用いて定義式で計算する.
  ///
  /// @param temperature 温度 (K).
  /// @return 指定された温度における, 標準状態のモル比ギブスエネルギ (J mol<sup>-1</sup>).
  /// @throws UnsupportedOperationException `molarSpecificGibbsEnergy`
  ///     オペレーションがサポートされていない場合.
  /// @throws IllegalArgumentException `temperature` が範囲外の場合.
  ///
  default double molarSpecificGibbsEnergy(double temperature) {
    return this.molarSpecificEnthalpy(temperature)
        - temperature * this.molarSpecificEntropy(temperature);
  }

}
