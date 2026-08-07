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

/// [PolynomialThermalConductivity] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
class PolynomialThermalConductivityTest {

  /// [PolynomialThermalConductivity#coefficientsOf(double...)] のテスト.
  @Test
  void testCoefficientsOfDoubleArray() {
    //noinspection Convert2MethodRef
    assertThatThrownBy(() -> PolynomialThermalConductivity.coefficientsOf())
        .isInstanceOf(IllegalArgumentException.class);
  }

  /// [PolynomialThermalConductivity#coefficients()] のテスト.
  @Test
  void testCoefficients() {
    final double coefficient0 = 16.7;
    final double coefficient1 = 1.;

    assertThat(PolynomialThermalConductivity.coefficientsOf(coefficient0, coefficient1).coefficients())
        .isEqualTo(new double[]{coefficient0, coefficient1});
  }

  /// [PolynomialThermalConductivity#thermalConductivity(double)] のテスト.
  @Test
  void testThermalConductivityDouble() {
    final double coefficient0 = 16.7;
    final double coefficient1 = 1.;

    assertThat(
        PolynomialThermalConductivity
            .coefficientsOf(coefficient0, coefficient1)
            .thermalConductivity(298.15))
        .isEqualTo(coefficient0 + 298.15 * coefficient1);
  }

  /// [PolynomialThermalConductivity#equals(Object)] と
  /// [PolynomialThermalConductivity#hashCode()] のテスト.
  @Test
  void testEquals() {
    EqualsTester tester = new EqualsTester();

    Stream.<Supplier<PolynomialThermalConductivity>>of(
            () -> PolynomialThermalConductivity.coefficientsOf(16.7, 1.),
            () -> PolynomialThermalConductivity.coefficientsOf(18.0, 1.))
        .map(supplier -> Stream.generate(supplier).limit(3).toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

  /// [PolynomialThermalConductivity#toString()] のテスト.
  @Test
  void testToString() {
    assertThat(PolynomialThermalConductivity.coefficientsOf(16.7, 1.))
        .hasToString(
            MoreObjects.toStringHelper(PolynomialThermalConductivity.class)
                .add("coefficients", new double[]{16.7, 1.})
                .toString());
  }

}
