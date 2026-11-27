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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [OutletPort] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class OutletPortTest {

  /// [OutletPort.Builder] のテスト.
  @Nested
  class BuilderTest {

    /// [OutletPort.Builder#pressure(double)] のテスト.
    @Test
    void testPressureDouble() {
      assertThatThrownBy(() -> OutletPort.builder().pressure(0.))
          .isInstanceOf(IllegalArgumentException.class);
    }

    /// [OutletPort.Builder#fileName(String)] のテスト.
    @Test
    void testFileNameString() {
      assertThatThrownBy(() -> OutletPort.builder().fileName(null))
          .isInstanceOf(NullPointerException.class);
    }

    /// [MonitorMapBuilder#monitor(Monitor)] のテスト.
    @Test
    void testMonitorMonitor() {
      assertThatThrownBy(() -> OutletPort.builder().fileName("test").monitor(null))
          .isInstanceOf(NullPointerException.class);
    }

    /// [OutletPort.Builder#build()] のテスト.
    @Test
    void testBuild() {
      assertThatThrownBy(() -> OutletPort.builder().build())
          .isInstanceOf(IllegalStateException.class);
    }

  } // BuilderTest


  /// [OutletPort.Builder] のテスト.
  @Test
  void testBuilder() {
    assertThatThrownBy(() -> OutletPort.builder().pressure(0.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> OutletPort.builder().fileName(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> OutletPort.builder().fileName("test").monitor(null))
        .isInstanceOf(NullPointerException.class);

    assertThatThrownBy(() -> OutletPort.builder().build()) // pressure を未指定
        .isInstanceOf(IllegalStateException.class);
  }

  @Mock
  private Monitor monitor;

  /// [OutletPort] のテスト.
  @Test
  void test() {
    final double pressure = 101325.;
    final String fileName = "test";

    assertThat(
        OutletPort.builder()
            .pressure(pressure)
            .fileName(fileName).monitor(this.monitor)
            .build())
        .returns(pressure, OutletPort::pressure)
        .returns(Map.of(fileName, this.monitor), OutletPort::monitorMap)
        .hasToString(
            MoreObjects.toStringHelper(OutletPort.class)
                .add("pressure", pressure)
                .add("monitorMap", Map.of(fileName, this.monitor))
                .toString());

    EqualsTester tester = new EqualsTester();

    Stream.<Supplier<OutletPort>>of(
            () -> OutletPort.builder()
                .pressure(101325.)
                .build(),
            () -> OutletPort.builder()
                .pressure(111325.)
                .build(),
            () -> OutletPort.builder()
                .pressure(101325.)
                .fileName("test").monitor(this.monitor)
                .build())
        .map(supplier -> Stream.generate(supplier).limit(3).toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

}
