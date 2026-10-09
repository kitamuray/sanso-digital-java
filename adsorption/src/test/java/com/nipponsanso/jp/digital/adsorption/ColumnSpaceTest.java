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

/// [ColumnSpace] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
class ColumnSpaceTest {

  /// [ColumnSpace.Builder] のテスト.
  @Nested
  class BuilderTest {

    /// [ColumnSpace.Builder#length(double)] のテスト.
    @Test
    void testLengthDouble() {
      assertThatThrownBy(() -> ColumnSpace.builder().length(0.))
          .isInstanceOf(IllegalArgumentException.class);
    }

    /// [ColumnSpace.Builder#fileName(String)] のテスト.
    @Test
    void testFileNameString() {
      assertThatThrownBy(() -> ColumnSpace.builder().fileName(null))
          .isInstanceOf(NullPointerException.class);
    }

    /// [ColumnSpace.Builder#build()] のテスト.
    @Test
    void testBuild() {
      assertThatThrownBy(() -> ColumnSpace.builder().build())
          .isInstanceOf(IllegalStateException.class);
    }

    /// [ColumnSpace.Builder.MonitorBuilder] のテスト.
    @Nested
    class MonitorBuilderTest {

      /// [ColumnSpace.Builder.MonitorBuilder#monitor(Monitor)] のテスト.
      @Test
      void testMonitorMonitor() {
        assertThatThrownBy(() -> ColumnSpace.builder().fileName("foo").monitor(null))
            .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(
            () -> ColumnSpace.builder()
                .fileName("foo")
                .monitor(Monitor.massFlow()))
            .isInstanceOf(IllegalArgumentException.class);
      }

    } // MonitorBuilderTest

  } // BuilderTest

  /// [ColumnSpace#length()] のテスト.
  @Test
  void testLength() {
    final double length = 0.3;

    assertThat(
        ColumnSpace.builder()
            .length(length)
            .build()
            .length())
        .isEqualTo(length);
  }

  /// [ColumnSpace#monitorMap()] のテスト.
  @Test
  void testMonitorMap() {
    final String fileName = "foo";
    final Monitor monitor = Monitor.viscosity();

    assertThat(
        ColumnSpace.builder()
            .length(0.3)
            .fileName(fileName)
              .monitor(monitor)
            .build()
            .monitorMap())
        .isEqualTo(Map.of(fileName, monitor));
  }

  /// [ColumnSpace#equals(Object)] と [ColumnSpace#hashCode()] のテスト.
  @Test
  void testEquals() {
    EqualsTester tester = new EqualsTester();

    Stream.<Supplier<ColumnSpace>>of(
            () -> ColumnSpace.builder()
                .length(0.3)
                .build(),
            () -> ColumnSpace.builder()
                .length(0.5)
                .build(),
            () -> ColumnSpace.builder()
                .length(0.3)
                .fileName("foo")
                  .monitor(Monitor.temperature())
                .build(),
            () -> ColumnSpace.builder()
                .length(0.3)
                .fileName("bar")
                  .monitor(Monitor.temperature())
                .build(),
            () -> ColumnSpace.builder()
                .length(0.3)
                .fileName("foo")
                  .monitor(Monitor.pressure())
                .build())
        .map(supplier -> Stream.generate(supplier).limit(3).toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

  /// [ColumnSpace#toString()] のテスト.
  @Test
  void testToString() {
    assertThat(
        ColumnSpace.builder()
            .length(0.3)
            .fileName("foo")
            .monitor(Monitor.temperature())
            .build())
        .hasToString(
            MoreObjects.toStringHelper(ColumnSpace.class)
                .add("length", 0.3)
                .add("monitorMap", Map.of("foo", Monitor.temperature()))
                .toString());
  }

}
