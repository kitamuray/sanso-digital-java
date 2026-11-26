/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;

import com.google.common.base.MoreObjects;
import com.google.common.testing.EqualsTester;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [ColumnStart] のテスト.
///
/// @since 1.1
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class ColumnStartTest {

  /// [ColumnStart.Builder] のテスト.
  @Nested
  class BuilderTest {

    /// [ColumnStart.Builder#temperature(double)] のテスト.
    @Test
    void testTemperatureDouble() {
      assertThatThrownBy(() -> ColumnStart.builder().temperature(0.))
          .isInstanceOf(IllegalArgumentException.class);
    }

    /// [ColumnStart.Builder#pressure(double)] のテスト.
    @Test
    void testPressureDouble() {
      assertThatThrownBy(() -> ColumnStart.builder().pressure(0.))
          .isInstanceOf(IllegalArgumentException.class);
    }

    /// [ColumnStart.Builder#componentKey(ComponentKey)] のテスト.
    @Test
    void testComponentKeyComponentKey() {
      assertThatThrownBy(() -> ColumnStart.builder().componentKey(null))
          .isInstanceOf(NullPointerException.class);
    }

    /// [ColumnStart.Builder#shellTemperature(double)] のテスト.
    @Test
    void testShellTemperatureDouble() {
      assertThatThrownBy(() -> ColumnStart.builder().shellTemperature(0.))
          .isInstanceOf(IllegalArgumentException.class);
    }

  } // BuilderTest

  /// [ColumnStart#temperature()] のテスト.
  @Test
  void testTemperature() {
    assertThat(ColumnStart.builder().build().temperature()).isEqualTo(298.15);

    final double temperature = 303.15;
    assertThat(ColumnStart.builder().temperature(temperature).build().temperature())
        .isEqualTo(temperature);
  }

  /// [ColumnStart#pressure()] のテスト.
  @Test
  void testPressure() {
    assertThat(ColumnStart.builder().build().pressure()).isEqualTo(101325.);

    final double pressure = 111325.;
    assertThat(ColumnStart.builder().pressure(pressure).build().pressure())
        .isEqualTo(pressure);
  }

  /// [ColumnStart#fractionMap()] のテスト.
  @Test
  void testFractionMap() {
    assertThat(ColumnStart.builder().build().fractionMap()).isEmpty();

    final ComponentKey componentKey = ComponentKey.NITROGEN;
    final double fraction = 0.123;
    assertThat(
        ColumnStart.builder().componentKey(componentKey).fraction(fraction).build().fractionMap())
        .isEqualTo(Map.of(componentKey, fraction));
  }

  /// [ColumnStart#shellTemperature()] のテスト.
  @Test
  void testShellTemperature() {
    assertThat(ColumnStart.builder().build().shellTemperature()).isEqualTo(298.15);

    final double shellTemperature = 313.15;
    assertThat(ColumnStart.builder().shellTemperature(shellTemperature).build().shellTemperature())
        .isEqualTo(shellTemperature);
  }

  @Mock
  private ColumnCell2 cell;

  @Mock
  private ColumnCell2.ComponentValue componentValue;

  /// [ColumnStart#start(ColumnCell2)] のテスト.
  @Test
  void testStartColumnCell() {
    final double temperature = 303.15;
    final double pressure = 111325.;
    final ComponentKey componentKey = ComponentKey.NITROGEN;
    final double fraction = 0.123;
    final double shellTemperature = 313.15;

    doAnswer(
        invocation -> assertThat(invocation.getArgument(0, Double.class))
            .isEqualTo(temperature))
        .when(this.cell).setTemperature(anyDouble());
    doAnswer(
        invocation -> assertThat(invocation.getArgument(0, Double.class))
            .isEqualTo(pressure))
        .when(this.cell).setPressure(anyDouble());
    doAnswer(
        invocation -> assertThat(invocation.getArgument(0, Double.class))
            .isEqualTo(fraction))
        .when(this.componentValue).setFraction(anyDouble());
    doReturn(Map.of(componentKey, this.componentValue)).when(this.cell).componentMap();
    doAnswer(
        invocation -> assertThat(invocation.getArgument(0, Double.class))
            .isEqualTo(shellTemperature))
        .when(this.cell).setShellTemperature(anyDouble());

    ColumnStart start = ColumnStart.builder()
        .temperature(temperature)
        .pressure(pressure)
        .componentKey(componentKey).fraction(fraction)
        .shellTemperature(shellTemperature)
        .build();

    start.start(this.cell);
  }

  /// [ColumnStart#hashCode()] と [ColumnStart#equals(Object)] のテスト.
  @Test
  void testEquals() {
    EqualsTester tester = new EqualsTester();

    Stream.<Supplier<ColumnStart>>of(
            () -> ColumnStart.builder().build(),
            () -> ColumnStart.builder().temperature(303.15).build(),
            () -> ColumnStart.builder().pressure(111325.).build(),
            () -> ColumnStart.builder().componentKey(ComponentKey.NITROGEN).fraction(0.123).build(),
            () -> ColumnStart.builder().shellTemperature(313.15).build())
        .map(supplier -> Stream.generate(supplier).limit(3).toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

  /// [ColumnStart#toString()] のテスト.
  @Test
  void testToString() {
    assertThat(
        ColumnStart.builder()
            .temperature(303.15)
            .pressure(111325.)
            .componentKey(ComponentKey.NITROGEN).fraction(0.123)
            .shellTemperature(313.15)
            .build())
        .hasToString(
            MoreObjects.toStringHelper(ColumnStart.class)
                .add("temperature", 303.15)
                .add("pressure", 111325.)
                .add("fractionMap", Map.of(ComponentKey.NITROGEN, 0.123))
                .add("shellTemperature", 313.15)
                .toString());
  }

}
