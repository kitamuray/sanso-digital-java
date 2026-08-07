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

/// [ConstantThermalConductivity] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
class ConstantThermalConductivityTest {

  /// [ConstantThermalConductivity#valueOf(double)]  のテスト.
  @Test
  void testValueOfDouble() {
    assertThatThrownBy(() -> ConstantThermalConductivity.valueOf(0.))
        .isInstanceOf(IllegalArgumentException.class);
  }

  /// [ConstantThermalConductivity#value()] のテスト.
  @Test
  void testValue() {
    final double thermalConductivity = 16.7;

    assertThat(ConstantThermalConductivity.valueOf(thermalConductivity).value())
        .isEqualTo(thermalConductivity);
  }

  /// [ConstantThermalConductivity#thermalConductivity(double)] のテスト.
  @Test
  void testThermalConductivityDouble() {
    final double thermalConductivity = 16.7;

    assertThat(
        ConstantThermalConductivity.valueOf(thermalConductivity)
            .thermalConductivity(298.15))
        .isEqualTo(thermalConductivity);
  }

  /**
   * {@link ConstantThermalConductivity#equals(Object)} と
   * {@link ConstantThermalConductivity#hashCode()} のテスト.
   */
  @Test
  void testEquals() {
    EqualsTester tester = new EqualsTester();

    Stream.<Supplier<ConstantThermalConductivity>>of(
            () -> ConstantThermalConductivity.valueOf(16.7),
            () -> ConstantThermalConductivity.valueOf(18.0))
        .map(supplier -> Stream.generate(supplier).limit(3).toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

  /**
   * {@link ConstantThermalConductivity#toString()} のテスト.
   */
  @Test
  void testToString() {
    assertThat(ConstantThermalConductivity.valueOf(16.7))
        .hasToString(
            MoreObjects.toStringHelper(ConstantThermalConductivity.class)
                .add("value", 16.7)
                .toString());
  }

}
