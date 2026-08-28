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
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class ColumnZoneTest {

  /// [ColumnZone.Builder] のテスト.
  @Nested
  class BuilderTest {

    @Mock
    private Section section;

    /// [ColumnZone.Builder#section(Section)] のテスト.
    @Test
    void testSectionSection() {
      assertThatThrownBy(() -> ColumnZone.builder().section(null))
          .isInstanceOf(NullPointerException.class);

      doReturn(3).when(this.section).number();

      assertThatThrownBy(() -> ColumnZone.builder()
          .fileName("foo")
          .index(5)
          .monitor(Monitor.temperature())
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

    @Mock
    private Adsorbent adsorbent;

    /// [ColumnZone.Builder#build()] のテスト.
    @Test
    void testBuild() {
      doReturn(5).when(this.section).number();

      assertThatThrownBy(
          () -> ColumnZone.builder()
              .adsorbent(this.adsorbent)
              .build())
          .isInstanceOf(IllegalStateException.class);
      assertThatThrownBy(
          () -> ColumnZone.builder()
              .section(this.section)
              .build())
          .isInstanceOf(IllegalStateException.class);
    }

    /// [ColumnZone.Builder.IndexMonitorBuilder] のテスト.
    @Nested
    class IndexMonitorBuilderTest {

      @Mock
      private Section section;

      /// [ColumnZone.Builder.IndexMonitorBuilder#index(int)] のテスト.
      @Test
      void testIndexInt() {
        assertThatThrownBy(() -> ColumnZone.builder().fileName("foo").index(-1))
            .isInstanceOf(IllegalArgumentException.class);

        doReturn(3).when(this.section).number();

        assertThatThrownBy(
            () -> ColumnZone.builder()
                .section(this.section)
                .fileName("foo")
                  .index(-1))
            .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
            () -> ColumnZone.builder()
                .section(this.section)
                .fileName("foo")
                  .index(3))
            .isInstanceOf(IllegalArgumentException.class);
      }

      /// [ColumnZone.Builder.IndexMonitorBuilder#monitor(Monitor)] のテスト.
      @Test
      void testMonitorMonitor() {
        assertThatThrownBy(() -> ColumnZone.builder().fileName("foo").monitor(null))
            .isInstanceOf(NullPointerException.class);
      }

      /// [ColumnZone.Builder.IndexMonitorBuilder.MonitorBuilder] のテスト.
      @Nested
      class MonitorBuilderTest {

        /// [ColumnZone.Builder.IndexMonitorBuilder.MonitorBuilder#monitor(Monitor)] のテスト.
        @Test
        void testMonitorMonitor() {
          assertThatThrownBy(
              () -> ColumnZone.builder()
                  .fileName("foo")
                    .index(5)
                    .monitor(null))
              .isInstanceOf(NullPointerException.class);
        }

      } // MonitorBuilderTest

      /// [ColumnZone.Builder.IndexMonitorBuilder.IndexBuilder] のテスト.
      @Nested
      class IndexBuilderTest {

        @Mock
        private Section section;

        /// [ColumnZone.Builder.IndexMonitorBuilder.IndexBuilder#index(int)] のテスト.
        @Test
        void testIndexInt() {
          assertThatThrownBy(
              () -> ColumnZone.builder()
                  .fileName("foo")
                    .monitor(Monitor.temperature())
                    .index(-1))
              .isInstanceOf(IllegalArgumentException.class);

          doReturn(5).when(this.section).number();

          assertThatThrownBy(
              () -> ColumnZone.builder()
                  .section(this.section)
                  .fileName("foo")
                    .monitor(Monitor.temperature())
                    .index(-1))
              .isInstanceOf(IllegalArgumentException.class);
          assertThatThrownBy(
              () -> ColumnZone.builder()
                  .section(this.section)
                  .fileName("foo")
                    .monitor(Monitor.temperature())
                    .index(5))
              .isInstanceOf(IllegalArgumentException.class);
        }

      } // IndexBuilderTest

    } // IndexMonitorBuilderTest

  } // BuilderTest

  @Mock
  private Section section;

  @Mock
  private Adsorbent adsorbent;

  /// [ColumnZone#section()] のテスト.
  @Test
  void testSection() {
    doReturn(5).when(this.section).number();

    assertThat(
        ColumnZone.builder()
            .section(this.section)
            .adsorbent(this.adsorbent)
            .build()
            .section())
        .isSameAs(this.section);
  }

  /// [ColumnZone#adsorbent()] のテスト.
  @Test
  void testAdsorbent() {
    doReturn(5).when(this.section).number();

    assertThat(
        ColumnZone.builder()
            .section(this.section)
            .adsorbent(this.adsorbent)
            .build()
            .adsorbent())
        .isSameAs(this.adsorbent);
  }

  /// [ColumnZone#indexMap()] のテスト.
  @Test
  void testIndexMap() {
    doReturn(5).when(this.section).number();

    assertThat(
        ColumnZone.builder()
            .section(this.section)
            .adsorbent(this.adsorbent)
            .build()
            .indexMap())
        .isEmpty();

    assertThat(
        ColumnZone.builder()
            .section(this.section)
            .adsorbent(this.adsorbent)
            .fileName("foo")
              .index(3)
              .monitor(Monitor.temperature())
            .build()
            .indexMap())
        .isEqualTo(Map.of("foo", 3));
  }

  /// [ColumnZone#monitorMap()] のテスト.
  @Test
  void testMonitorMap() {
    doReturn(5).when(this.section).number();

    assertThat(
        ColumnZone.builder()
            .section(this.section)
            .adsorbent(this.adsorbent)
            .build()
            .monitorMap())
        .isEmpty();

    assertThat(
        ColumnZone.builder()
            .section(this.section)
            .adsorbent(this.adsorbent)
            .fileName("foo")
            .index(3)
            .monitor(Monitor.temperature())
            .build()
            .monitorMap())
        .isEqualTo(Map.of("foo", Monitor.temperature()));
  }

  /// [ColumnZone#indexMonitorMap()] のテスト.
  @Test
  void testIndexMonitorMap() {
    doReturn(5).when(this.section).number();

    assertThat(
        ColumnZone.builder()
            .section(this.section)
            .adsorbent(this.adsorbent)
            .build()
            .indexMonitorMap())
        .isEmpty();

    assertThat(
        ColumnZone.builder()
            .section(this.section)
            .adsorbent(this.adsorbent)
            .fileName("foo")
            .index(3)
            .monitor(Monitor.temperature())
            .build()
            .indexMonitorMap())
        .isEqualTo(Map.of("foo", new IndexMonitor(3, Monitor.temperature())));
  }

  @Mock
  private Section section2;

  @Mock
  private Adsorbent adsorbent2;

  /// [ColumnZone#equals(Object)] と [ColumnZone#hashCode()] のテスト.
  @Test
  void testEquals() {
    doReturn(5).when(this.section).number();
    doReturn(4).when(this.section2).number();

    EqualsTester tester = new EqualsTester();

    Stream.<Supplier<ColumnZone>>of(
            () -> ColumnZone.builder()
                .section(this.section)
                .adsorbent(this.adsorbent)
                .build(),
            () -> ColumnZone.builder()
                .section(this.section2)
                .adsorbent(this.adsorbent)
                .build(),
            () -> ColumnZone.builder()
                .section(this.section)
                .adsorbent(this.adsorbent2)
                .build(),
            () -> ColumnZone.builder()
                .section(this.section)
                .adsorbent(this.adsorbent)
                .fileName("foo")
                  .index(3)
                  .monitor(Monitor.temperature())
                .build(),
            () -> ColumnZone.builder()
                .section(this.section)
                .adsorbent(this.adsorbent)
                .fileName("bar")
                  .index(3)
                  .monitor(Monitor.temperature())
                .build(),
            () -> ColumnZone.builder()
                .section(this.section)
                .adsorbent(this.adsorbent)
                .fileName("foo")
                  .index(2)
                  .monitor(Monitor.temperature())
                .build(),
            () -> ColumnZone.builder()
                .section(this.section)
                .adsorbent(this.adsorbent)
                .fileName("foo")
                  .index(3)
                  .monitor(Monitor.pressure())
                .build())
        .map(supplier -> Stream.generate(supplier).limit(3).toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

  /// [ColumnZone#toString()] のテスト.
  @Test
  void testToString() {
    doReturn(5).when(this.section).number();

    assertThat(
        ColumnZone.builder()
            .section(this.section)
            .adsorbent(this.adsorbent)
            .fileName("foo")
              .index(3)
              .monitor(Monitor.temperature())
            .build())
        .hasToString(
            MoreObjects.toStringHelper(ColumnZone.class)
                .add("section", this.section)
                .add("adsorbent", this.adsorbent)
                .add("indexMap", Map.of("foo", 3))
                .add("monitorMap", Map.of("foo", Monitor.temperature()))
                .toString());
  }

}
