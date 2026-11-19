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

/// [ColumnSpace] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
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
      assertThatThrownBy(() -> ColumnSpace.builder().length(1.).fileName(null))
          .isInstanceOf(NullPointerException.class);
    }

    /// [MonitorMapBuilder#monitor(Monitor)] のテスト.
    @Test
    void testMonitorMonitor() {
      assertThatThrownBy(() -> ColumnSpace.builder().length(1).fileName("test").monitor(null))
          .isInstanceOf(NullPointerException.class);
    }

    /// [ColumnSpace.Builder#build()] のテスト.
    @Test
    void testBuild() {
      assertThatThrownBy(() -> ColumnSpace.builder().build())
          .isInstanceOf(IllegalStateException.class);
    }

  } // BuilderTest

  /// [ColumnSpace#length()] のテスト.
  @Test
  void testLength() {
    final double length = 1.2;

    assertThat(ColumnSpace.builder().length(length).build().length()).isEqualTo(length);
  }

  @Mock
  private Monitor monitor0;

  @Mock
  private Monitor monitor1;

  /// [ColumnSpace#monitorMap()] のテスト.
  @Test
  void testMonitorMap() {
    assertThat(ColumnSpace.builder().length(1.2).build().monitorMap()).isEmpty();

    final String fileName0 = "test0";
    final String fileName1 = "test1";

    assertThat(
        ColumnSpace.builder()
            .length(1.2)
            .fileName(fileName0).monitor(this.monitor0)
            .fileName(fileName1).monitor(this.monitor1)
            .build()
            .monitorMap())
        .isEqualTo(
            Map.of(
                fileName0, this.monitor0,
                fileName1, this.monitor1));
  }

  /// [ColumnSpace#equals(Object)] と [ColumnSpace#hashCode()] のテスト.
  @Test
  void testEquals() {
    EqualsTester tester = new EqualsTester();

    Stream.<Supplier<ColumnSpace>>of(
        () -> ColumnSpace.builder().length(1.2).build(),
        () -> ColumnSpace.builder().length(2.1).build(),
        () -> ColumnSpace.builder().length(1.2).fileName("test0").monitor(this.monitor0).build())
        .map(supplier -> Stream.generate(supplier).limit(3).toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

  /// [ColumnSpace#toString()] のテスト.
  @Test
  void testToString() {
    ColumnSpace space = ColumnSpace.builder()
        .length(1.2)
        .fileName("test0").monitor(this.monitor0)
        .fileName("test1").monitor(this.monitor1)
        .build();

    final double length = space.length();
    final Map<String, Monitor> monitorMap = space.monitorMap();

    assertThat(space)
        .hasToString(
            MoreObjects.toStringHelper(ColumnSpace.class)
                .add("length", length)
                .add("monitorMap", monitorMap)
                .toString());
  }

}
