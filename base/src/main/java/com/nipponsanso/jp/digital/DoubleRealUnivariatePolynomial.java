/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

///
/// プリミティブ double 型実数一変数多項式.
///
/// @param doubleCoefficients 係数の配列.
/// @author KITAMURA Yuichiro
/// @since 1.0
///
public record DoubleRealUnivariatePolynomial(double[] doubleCoefficients)
    implements RealUnivariatePolynomial<Double, DoubleRealUnivariatePolynomial> {

  ///
  /// プリミティブ double 型実数一変数多項式のインスタンスを生成する.
  ///
  /// @param doubleCoefficients 係数の配列.
  /// @throws NullPointerException `doubleCoefficients` が `null` の場合.
  ///
  public DoubleRealUnivariatePolynomial(double[] doubleCoefficients) {
    Preconditions.checkNotNull(doubleCoefficients);

    this.doubleCoefficients = doubleCoefficients.clone();
  }

  ///
  /// このプリミティブ double 型実数一変数多項式における係数の配列を返す.
  ///
  /// @return このプリミティブ double 型実数一変数多項式における係数の配列.
  ///
  @Override
  public double[] doubleCoefficients() {
    return this.doubleCoefficients.clone();
  }

  ///
  /// このプリミティブ double 型実数一変数多項式における係数の不変リストを返す.
  ///
  /// @return このプリミティブ double 型実数一変数多項式における係数の不変リスト.
  ///
  @Override
  public List<Double> coefficientList() {
    return Arrays.stream(this.doubleCoefficients)
        .boxed()
        .collect(ImmutableList.toImmutableList());
  }

  ///
  /// このプリミティブ double 型実数一変数多項式を指定された値で評価した結果を返す.
  ///
  /// @param x 評価する値.
  /// @return 評価した結果.
  ///
  public double value(double x) {
    double result = 0.;

    for (int index = this.doubleCoefficients.length - 1; index >= 0; index--) {
      result = result * x + this.doubleCoefficients[index];
    }

    return result;
  }

  ///
  /// このプリミティブ double 型実数一変数多項式を指定された値で評価した結果を返す.
  ///
  /// @param x 評価する値.
  /// @return 評価した結果.
  /// @throws NullPointerException `x` が `null` の場合.
  ///
  @Override
  public Double value(Double x) {
    return this.value(Preconditions.checkNotNull(x).doubleValue());
  }

  ///
  /// この一変数多項式の導関数を返す.
  ///
  /// @return この一変数多項式の導関数.
  ///
  @Override
  public DoubleRealUnivariatePolynomial derivative() {
    if (this.doubleCoefficients.length <= 1) {
      return new DoubleRealUnivariatePolynomial(new double[0]);
    } else {
      return new DoubleRealUnivariatePolynomial(
          IntStream.range(1, this.doubleCoefficients.length)
              .mapToDouble(index -> index * this.doubleCoefficients[index])
              .toArray());
    }
  }

  // record は doubleCoefficients の要素まで比較しないため,
  // 独自に実装しなければならない

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    DoubleRealUnivariatePolynomial that = (DoubleRealUnivariatePolynomial) o;

    return Arrays.equals(this.doubleCoefficients, that.doubleCoefficients);
  }

}
