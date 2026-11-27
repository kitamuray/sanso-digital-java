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

/// [TankStart] のテスト.
///
/// @since 1.1
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class TankStartTest {

  /// [TankStart.Builder] のテスト.
  @Nested
  class BuilderTest {

    /// [TankStart.Builder#temperature(double)] のテスト.
    @Test
    void testTemperatureDouble() {
      assertThatThrownBy(() -> TankStart.builder().temperature(0.))
          .isInstanceOf(IllegalArgumentException.class);
    }

    /// [TankStart.Builder#pressure(double)] のテスト.
    @Test
    void testPressureDouble() {
      assertThatThrownBy(() -> TankStart.builder().pressure(0.))
          .isInstanceOf(IllegalArgumentException.class);
    }

    /// [TankStart.Builder#componentKey(ComponentKey)] のテスト.
    @Test
    void testComponentKeyComponentKey() {
      assertThatThrownBy(() -> TankStart.builder().componentKey(null))
          .isInstanceOf(NullPointerException.class);
    }

    /// [FractionMapBuilder#fraction(double)] のテスト.
    @Test
    void testFractionDouble() {
      assertThatThrownBy(
          () -> TankStart.builder().componentKey(ComponentKey.NITROGEN).fraction(-1.))
          .isInstanceOf(IllegalArgumentException.class);
      assertThatThrownBy(
          () -> TankStart.builder().componentKey(ComponentKey.NITROGEN).fraction(2.))
          .isInstanceOf(IllegalArgumentException.class);
    }

  } // BuilderTest

  /// [TankStart#temperature()] のテスト.
  @Test
  void testTemperature() {
    final double temperature = 303.15;
    assertThat(
        TankStart.builder()
            .temperature(temperature)
            .pressure(101325.)
            .build().temperature())
        .isEqualTo(temperature);
  }

  /// [TankStart#pressure()] のテスト.
  @Test
  void testPressure() {
    final double pressure = 111325;
    assertThat(
        TankStart.builder()
            .temperature(298.15)
            .pressure(pressure)
            .build().pressure())
        .isEqualTo(pressure);
  }

  /// [TankStart#fractionMap()] のテスト.
  @Test
  void testFractionMap() {
    final ComponentKey componentKey = ComponentKey.NITROGEN;
    final double fraction = 0.123;
    assertThat(
        TankStart.builder()
            .temperature(298.15)
            .pressure(101325.)
            .componentKey(componentKey).fraction(fraction)
            .build().fractionMap())
        .isEqualTo(Map.of(componentKey, fraction));
  }
  
  @Mock
  private TankCell cell;

  @Mock
  private TankCell.ComponentValue componentValue;

  /// [TankStart#start(TankCell)] のテスト.
  @Test
  void testStartTankCell() {
    final double temperature = 303.15;
    final double pressure = 111325.;
    final ComponentKey componentKey = ComponentKey.NITROGEN;
    final double fraction = 0.123;

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

    TankStart start = TankStart.builder()
        .temperature(temperature)
        .pressure(pressure)
        .componentKey(componentKey).fraction(fraction)
        .build();

    start.start(this.cell);
  }

  /// [TankStart#hashCode()] と [TankStart#equals(Object)] のテスト.
  @Test
  void testEquals() {
    EqualsTester tester = new EqualsTester();

    Stream.<Supplier<TankStart>>of(
            () -> TankStart.builder()
                .temperature(298.15)
                .pressure(101325.)
                .build(),
            () -> TankStart.builder()
                .temperature(303.15)
                .pressure(101325.)
                .build(),
            () -> TankStart.builder()
                .temperature(298.15)
                .pressure(112315.)
                .build(),
            () -> TankStart.builder()
                .temperature(298.15)
                .pressure(101325.)
                .componentKey(ComponentKey.NITROGEN).fraction(0.123)
                .build())
        .map(supplier -> Stream.generate(supplier).limit(3).toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

  /// [TankStart#toString()] のテスト.
  @Test
  void testToString() {
    assertThat(
        TankStart.builder()
            .temperature(303.15)
            .pressure(111325.).
            componentKey(ComponentKey.OXYGEN).fraction(0.3).build())
        .hasToString(
            MoreObjects.toStringHelper(TankStart.class)
                .add("temperature", 303.15)
                .add("pressure", 111325.)
                .add("fractionMap", Map.of(ComponentKey.OXYGEN, 0.3))
                .toString());
  }

}
