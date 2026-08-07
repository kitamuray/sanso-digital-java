/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.google.common.base.MoreObjects;
import com.google.common.testing.EqualsTester;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

/// [PolynomialSpecificHeat] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
class PolynomialSpecificHeatTest {

  /// [PolynomialSpecificHeat#coefficientsOf(double...)] のテスト.
  @Test
  void testCoefficientsOfDoubleArray() {
    //noinspection Convert2MethodRef
    assertThatThrownBy(() -> PolynomialSpecificHeat.coefficientsOf())
        .isInstanceOf(IllegalArgumentException.class);
  }

  /// [PolynomialSpecificHeat#coefficients()] のテスト.
  @Test
  void testCoefficients() {
    final double coefficient0 = 590.;
    final double coefficient1 = 1.;

    assertThat(PolynomialSpecificHeat.coefficientsOf(coefficient0, coefficient1).coefficients())
        .isEqualTo(new double[]{coefficient0, coefficient1});
  }

  /// [PolynomialSpecificHeat#specificHeat(double)] のテスト.
  @Test
  void testSpecificHeatDouble() {
    final double coefficient0 = 590.;
    final double coefficient1 = 1.;

    assertThat(
        PolynomialSpecificHeat
            .coefficientsOf(coefficient0, coefficient1)
            .specificHeat(298.15))
        .isEqualTo(coefficient0 + 298.15 * coefficient1);
  }

  /// [PolynomialSpecificHeat#equals(Object)] と [PolynomialSpecificHeat#hashCode()] のテスト.
  @Test
  void testEquals() {
    EqualsTester tester = new EqualsTester();

    Stream.<Supplier<PolynomialSpecificHeat>>of(
            () -> PolynomialSpecificHeat.coefficientsOf(590., 1.),
            () -> PolynomialSpecificHeat.coefficientsOf(610., 1.))
        .map(supplier -> Stream.generate(supplier).limit(3).toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

  /// [PolynomialSpecificHeat#toString()] のテスト.
  @Test
  void testToString() {
    assertThat(PolynomialSpecificHeat.coefficientsOf(590., 1.))
        .hasToString(
            MoreObjects.toStringHelper(PolynomialSpecificHeat.class)
                .add("coefficients", new double[]{590., 1.})
                .toString());
  }

}
