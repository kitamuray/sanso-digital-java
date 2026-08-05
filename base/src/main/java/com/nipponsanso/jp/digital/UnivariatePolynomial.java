/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital;

import java.util.List;

///
/// 一変数多項式.
///
/// @param <T> 変数の型.
/// @param <P> 多項式の型.
/// @since 1.0
/// @author KITAMURA Yuichiro
///
public interface UnivariatePolynomial<T, P extends UnivariatePolynomial<T, P>> {

  ///
  /// この一変数多項式における係数の不変リストを返す.
  ///
  /// @return この一変数多項式における係数の不変リスト.
  ///
  List<T> coefficientList();

  ///
  /// この一変数多項式を指定された値で評価した結果を返す.
  ///
  /// @param x 評価する値.
  /// @return 評価した結果.
  /// @throws NullPointerException `x` が `null` の場合.
  ///
  T value(T x);

  ///
  /// この一変数多項式の導関数を返す.
  ///
  /// @return この一変数多項式の導関数.
  ///
  P derivative();

}
