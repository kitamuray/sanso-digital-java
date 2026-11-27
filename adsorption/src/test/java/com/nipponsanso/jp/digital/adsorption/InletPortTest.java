/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.google.common.base.MoreObjects;
import com.google.common.testing.EqualsTester;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [InletPort] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class InletPortTest {

  /// [InletPort.Builder] のテスト.
  @Test
  void testBuilder() {
    assertThatThrownBy(() -> InletPort.builder().temperature(0.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> InletPort.builder().pressure(0.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> InletPort.builder().componentKey(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(
        () -> InletPort.builder().componentKey(ComponentKey.NITROGEN).fraction(-1.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(
        () -> InletPort.builder().componentKey(ComponentKey.NITROGEN).fraction(2.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> InletPort.builder().fileName(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> InletPort.builder().fileName("test").monitor(null))
        .isInstanceOf(NullPointerException.class);

    assertThatThrownBy(
        () -> InletPort.builder()
            .temperature(298.15)
            .build()) // pressure が未指定
        .isInstanceOf(IllegalStateException.class);
    assertThatThrownBy(
        () -> InletPort.builder()
            .pressure(101325.)
            .build()) // temperature が未指定
        .isInstanceOf(IllegalStateException.class);
  }

  @Mock
  private Monitor monitor;

  /// [InletPort] のテスト.
  @Test
  void test() {
    final double temperature = 298.15;
    final double pressure = 101325.;
    final ComponentKey componentKey = ComponentKey.OXYGEN;
    final double fraction = 0.12;
    final String fileName = "test";

    assertThat(
        InletPort.builder()
            .temperature(temperature)
            .pressure(pressure)
            .componentKey(componentKey).fraction(fraction)
            .fileName(fileName).monitor(this.monitor)
            .build())
        .returns(temperature, InletPort::temperature)
        .returns(pressure, InletPort::pressure)
        .returns(Map.of(componentKey, fraction), InletPort::fractionMap)
        .returns(Map.of(fileName, this.monitor), InletPort::monitorMap)
        .hasToString(
            MoreObjects.toStringHelper(InletPort.class)
                .add("temperature", temperature)
                .add("pressure", pressure)
                .add("fractionMap", Map.of(componentKey, fraction))
                .add("monitorMap", Map.of(fileName, this.monitor))
                .toString());

    EqualsTester tester = new EqualsTester();

    Stream.<Supplier<InletPort>>of(
            () -> InletPort.builder()
                .temperature(298.15)
                .pressure(101325.)
                .build(),
            () -> InletPort.builder()
                .temperature(303.15)
                .pressure(101325.)
                .build(),
            () -> InletPort.builder()
                .temperature(298.15)
                .pressure(111325.)
                .build(),
            () -> InletPort.builder()
                .temperature(298.15)
                .pressure(101325.)
                .componentKey(ComponentKey.NITROGEN).fraction(0.12)
                .build(),
            () -> InletPort.builder()
                .temperature(298.15)
                .pressure(101325.)
                .fileName("test").monitor(this.monitor)
                .build())
        .map(supplier -> Stream.generate(supplier).limit(3).toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

}
