/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.nipponsanso.jp.digital.DoubleRealUnivariatePolynomial;
import java.util.Objects;

/// 熱力学的温度の多項式による固相の熱伝導率.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class PolynomialThermalConductivity extends ThermalConductivity {

  /// 多項式.
  private final DoubleRealUnivariatePolynomial polynomial;

  /// 熱力学的温度の多項式による固相の熱伝導率のインスタンスを生成する.
  ///
  /// @param coefficients 多項式係数.
  private PolynomialThermalConductivity(double[] coefficients) {
    this.polynomial = new DoubleRealUnivariatePolynomial(coefficients);
  }

  /// 多項式係数を返す.
  ///
  /// @return 多項式係数.
  public double[] coefficients() {
    return this.polynomial.doubleCoefficients();
  }

  /// {@inheritDoc}
  ///
  /// @param temperature {@inheritDoc}
  /// @return {@inheritDoc}
  /// @throws IllegalArgumentException `temperature` が 0 以下の場合 (`temperature <= 0`).
  @Override
  double thermalConductivity(double temperature) {
    Preconditions.checkArgument(temperature > 0.);

    return this.polynomial.value(temperature);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    PolynomialThermalConductivity that = (PolynomialThermalConductivity) o;
    
    return this.polynomial.equals(that.polynomial);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.polynomial);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("coefficients", this.polynomial.doubleCoefficients())
        .toString();
  }

  /// 熱力学的温度の多項式による固相の熱伝導率のインスタンスを生成して返す.
  ///
  /// @param coefficients 多項式係数.
  /// @return 熱力学的温度の多項式による固相の熱伝導率のインスタンス.
  /// @throws IllegalArgumentException `coefficients` が指定されていない場合.
  public static PolynomialThermalConductivity coefficientsOf(double... coefficients) {
    Preconditions.checkArgument(coefficients.length > 0);

    return new PolynomialThermalConductivity(coefficients);
  }

}
