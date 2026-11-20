/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

/// [ColumnZone] のテスト.
///
/// @author KITAMURA Yuichiro
/// @since 1.0
@ExtendWith(MockitoExtension.class)
class ColumnZoneTest {

  /// [ColumnZone.Builder] のテスト.
  @Nested
  class BuilderTest {

    @Mock
    private Section section;

    @Mock
    private Monitor monitor;

    /// [ColumnZone.Builder#section(Section)] のテスト.
    @Test
    void testSectionSection() {
      assertThatThrownBy(() -> ColumnZone.builder().section(null))
          .isInstanceOf(NullPointerException.class);

      final int size = 5;
      doReturn(size).when(this.section).number();

      assertThatThrownBy(
          () -> ColumnZone.builder()
              .fileName("test").index(size + 1).monitor(this.monitor)
              .section(this.section))
          .isInstanceOf(IllegalArgumentException.class);
    }

    /// [ColumnZone.Builder#adsorbent(Adsorbent)] のテスト.
    @Test
    void testAdsorbentAdsorbent() {
      assertThatThrownBy(() -> ColumnZone.builder().adsorbent(null))
          .isInstanceOf(NullPointerException.class);
    }

    /// [ColumnZone.Builder#fileName(String)] のテスト.
    @Test
    void testFileNameString() {
      assertThatThrownBy(() -> ColumnZone.builder().fileName(null))
          .isInstanceOf(NullPointerException.class);
    }

    /// [IndexMonitorMapBuilder#index(int)] のテスト.
    @Test
    void testIndexInt() {
      assertThatThrownBy(() -> ColumnZone.builder().fileName("test").index(-1))
          .isInstanceOf(IndexOutOfBoundsException.class);

      final int size = 5;
      doReturn(size).when(this.section).number();

      assertThatThrownBy(
          () -> ColumnZone.builder()
              .section(this.section)
              .fileName("test").index(size + 1))
          .isInstanceOf(IndexOutOfBoundsException.class);
    }

    /// [IndexMonitorMapBuilder#monitor(Monitor)] のテスト.
    @Test
    void testMonitorMonitor() {
      assertThatThrownBy(
          () -> ColumnZone.builder()
              .fileName("test").index(1).monitor(null))
          .isInstanceOf(NullPointerException.class);
    }

    @Mock
    private Adsorbent adsorbent;

    /// [ColumnZone.Builder#build()] のテスト.
    @Test
    void testBuild() {
      doReturn(5).when(this.section).number();

      assertThatThrownBy(() -> ColumnZone.builder().section(this.section).build())
          .isInstanceOf(IllegalStateException.class);
      assertThatThrownBy(() -> ColumnZone.builder().adsorbent(this.adsorbent).build())
          .isInstanceOf(IllegalStateException.class);
    }

  } // BuilderTest

  @Mock
  private Section section0;

  @Mock
  private Adsorbent adsorbent0;

  /// [ColumnZone#section()] のテスト.
  @Test
  void testSection() {
    doReturn(5).when(this.section0).number();

    assertThat(
        ColumnZone.builder()
            .section(this.section0)
            .adsorbent(this.adsorbent0)
            .build()
            .section())
        .isEqualTo(this.section0);
  }

  /// [ColumnZone#adsorbent()] のテスト.
  @Test
  void testAdsorbent() {
    doReturn(5).when(this.section0).number();

    assertThat(
        ColumnZone.builder()
            .section(this.section0)
            .adsorbent(this.adsorbent0)
            .build()
            .adsorbent())
        .isEqualTo(this.adsorbent0);
  }

  @Mock
  private Monitor monitor0;

  @Mock
  private Monitor monitor1;

  /// [ColumnZone#indexMonitorMap()], [ColumnZone#indexMap()], [ColumnZone#monitorMap()] のテスト.
  @Test
  void testIndexMonitorMap() {
    doReturn(5).when(this.section0).number();

    ColumnZone zone0 = ColumnZone.builder()
        .section(this.section0)
        .adsorbent(this.adsorbent0)
        .build();

    assertThat(zone0.indexMonitorMap()).isEmpty(); // package-private
    assertThat(zone0.indexMap()).isEmpty(); // public
    assertThat(zone0.monitorMap()).isEmpty(); // public

    final String fileName0 = "test0";
    final String fileName1 = "test1";
    final int index0 = 1;
    final int index1 = 3;

    ColumnZone zone1 = ColumnZone.builder()
        .section(this.section0)
        .adsorbent(this.adsorbent0)
        .fileName(fileName0).index(index0).monitor(this.monitor0)
        .fileName(fileName1).index(index1).monitor(this.monitor1)
        .build();

    assertThat(zone1.indexMonitorMap()) // package-private
        .isEqualTo(
            Map.of(
                fileName0, new IndexMonitor(index0, this.monitor0),
                fileName1, new IndexMonitor(index1, this.monitor1)));
    assertThat(zone1.indexMap()) // public
        .isEqualTo(
            Map.of(
                fileName0, index0,
                fileName1, index1));
    assertThat(zone1.monitorMap()) // public
        .isEqualTo(
            Map.of(
                fileName0, this.monitor0,
                fileName1, this.monitor1));
  }

  @Mock
  private Section section1;

  @Mock
  private Adsorbent adsorbent1;

  /// [ColumnZone#equals(Object)] と [ColumnZone#hashCode()] のテスト.
  @Test
  void testEquals() {
    doReturn(5).when(this.section0).number();
    doReturn(5).when(this.section1).number();

    EqualsTester tester = new EqualsTester();

    Stream.<Supplier<ColumnZone>>of(
            () -> ColumnZone.builder()
                .section(this.section0)
                .adsorbent(this.adsorbent0)
                .build(),
            () -> ColumnZone.builder()
                .section(this.section1)
                .adsorbent(this.adsorbent0)
                .build(),
            () -> ColumnZone.builder()
                .section(this.section0)
                .adsorbent(this.adsorbent1)
                .build(),
            () -> ColumnZone.builder()
                .section(this.section0)
                .adsorbent(this.adsorbent0)
                .fileName("test0").index(1).monitor(this.monitor0)
                .fileName("test1").index(3).monitor(this.monitor1)
                .build(),
            () -> ColumnZone.builder()
                .section(this.section0)
                .adsorbent(this.adsorbent0)
                .fileName("test1").index(1).monitor(this.monitor0)
                .fileName("test0").index(3).monitor(this.monitor1)
                .build(),
            () -> ColumnZone.builder()
                .section(this.section0)
                .adsorbent(this.adsorbent0)
                .fileName("test0").index(3).monitor(this.monitor0)
                .fileName("test1").index(1).monitor(this.monitor1)
                .build(),
            () -> ColumnZone.builder()
                .section(this.section0)
                .adsorbent(this.adsorbent0)
                .fileName("test0").index(1).monitor(this.monitor1)
                .fileName("test1").index(3).monitor(this.monitor0)
                .build())
        .map(supplier -> Stream.generate(supplier).limit(3).toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

  /// [ColumnZone#toString()] のテスト.
  @Test
  void testToString() {
    doReturn(5).when(this.section0).number();

    ColumnZone zone = ColumnZone.builder()
        .section(this.section0)
        .adsorbent(this.adsorbent0)
        .fileName("test0").index(1).monitor(this.monitor0)
        .fileName("test1").index(3).monitor(this.monitor1)
        .build();

    Map<String, Integer> indexMap = zone.indexMap();
    Map<String, Monitor> monitorMap = zone.monitorMap();

    assertThat(zone)
        .hasToString(
            MoreObjects.toStringHelper(ColumnZone.class)
                .add("section", this.section0)
                .add("adsorbent", this.adsorbent0)
                .add("indexMap", indexMap)
                .add("monitorMap", monitorMap)
                .toString());
  }

}
