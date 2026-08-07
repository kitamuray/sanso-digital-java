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

/// [ConstantSpecificHeat] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
class ConstantSpecificHeatTest {

  /// [ConstantSpecificHeat#valueOf(double)]  のテスト.
  @Test
  void testValueOfDouble() {
    assertThatThrownBy(() -> ConstantSpecificHeat.valueOf(0.))
        .isInstanceOf(IllegalArgumentException.class);
  }

  /// [ConstantSpecificHeat#value()] のテスト.
  @Test
  void testValue() {
    final double specificHeat = 590.;

    assertThat(ConstantSpecificHeat.valueOf(specificHeat).value())
        .isEqualTo(specificHeat);
  }

  /// [ConstantSpecificHeat#specificHeat(double)] のテスト.
  @Test
  void testSpecificHeatDouble() {
    final double specificHeat = 590.;

    assertThat(ConstantSpecificHeat.valueOf(specificHeat).specificHeat(298.15))
        .isEqualTo(specificHeat);
  }

  /// [ConstantSpecificHeat#equals(Object)] と [ConstantSpecificHeat#hashCode()] のテスト.
  @Test
  void testEquals() {
    EqualsTester tester = new EqualsTester();

    Stream.<Supplier<ConstantSpecificHeat>>of(
            () -> ConstantSpecificHeat.valueOf(590.),
            () -> ConstantSpecificHeat.valueOf(610.))
        .map(supplier -> Stream.generate(supplier).limit(3).toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

  /// [ConstantSpecificHeat#toString()] のテスト.
  @Test
  void testToString() {
    assertThat(ConstantSpecificHeat.valueOf(590.))
        .hasToString(
            MoreObjects.toStringHelper(ConstantSpecificHeat.class)
                .add("value", 590.)
                .toString());
  }

}
