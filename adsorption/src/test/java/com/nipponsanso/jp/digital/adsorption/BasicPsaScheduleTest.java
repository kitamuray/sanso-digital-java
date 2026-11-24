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
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [BasicPsaSchedule] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class BasicPsaScheduleTest {

  /// [BasicPsaSchedule.Builder] のテスト.
  @Nested
  class BuilderTest {

    @Mock
    private Section section;

    @Mock
    private Monitor monitor;

    /// [BasicPsaSchedule.Builder#section(Section)] のテスト.
    @Test
    void testSectionSection() {
      assertThatThrownBy(() -> BasicPsaSchedule.builder().section(null))
          .isInstanceOf(NullPointerException.class);

      final int size = 5;
      doReturn(size).when(this.section).number();

      assertThatThrownBy(
          () -> BasicPsaSchedule.builder()
              .fileName("test")
              .index(size + 1).monitor(this.monitor)
              .section(this.section))
          .isInstanceOf(IllegalArgumentException.class);
    }

    /// [BasicPsaSchedule.Builder#column(ColumnKey)] のテスト.
    @Test
    void testColumnColumnKey() {
      assertThatThrownBy(() -> BasicPsaSchedule.builder().column(null))
          .isInstanceOf(NullPointerException.class);
    }

    /// [StateMapBuilder#state(Object)] のテスト.
    @Test
    void testStateObject() {
      assertThatThrownBy(() -> BasicPsaSchedule.builder().column(ColumnKey.FIRST).state(null))
          .isInstanceOf(NullPointerException.class);
    }

    /// [BasicPsaSchedule.Builder#fileName(String)] のテスト.
    @Test
    void testFileNameString() {
      assertThatThrownBy(() -> BasicPsaSchedule.builder().fileName(null))
          .isInstanceOf(NullPointerException.class);
    }

    /// [IndexMonitorMapBuilder#index(int)] のテスト.
    @Test
    void testIndexInt() {
      final int size = 5;
      doReturn(size).when(this.section).number();

      assertThatThrownBy(
          () -> BasicPsaSchedule.builder()
              .section(this.section)
              .fileName("test").index(-1))
          .isInstanceOf(IndexOutOfBoundsException.class);
      assertThatThrownBy(
          () -> BasicPsaSchedule.builder()
              .section(this.section)
              .fileName("test").index(size + 1))
          .isInstanceOf(IndexOutOfBoundsException.class);
    }

    /// [IndexMonitorMapBuilder#monitor(Monitor)] のテスト.
    @Test
    void testMonitorMonitor() {
      assertThatThrownBy(
          () -> BasicPsaSchedule.builder().fileName("test").monitor(null))
          .isInstanceOf(NullPointerException.class);
    }

    /// [BasicPsaSchedule.Builder#build()] のテスト.
    @Test
    void testBuild() {
      assertThatThrownBy(() -> BasicPsaSchedule.builder().build())
          .isInstanceOf(IllegalStateException.class);
    }

  } // BuilderTest

  @Mock
  private Section section;

  /// [BasicPsaSchedule#section()] のテスト.
  @Test
  void testSection() {
    doReturn(5).when(this.section).number();

    assertThat(BasicPsaSchedule.builder().section(this.section).build().section())
        .isEqualTo(this.section);
  }

  /// [BasicPsaSchedule#stateMap()] のテスト.
  @Test
  void testStateMap() {
    doReturn(5).when(this.section).number();

    assertThat(BasicPsaSchedule.builder().section(this.section).build().stateMap())
        .isEmpty();

    assertThat(
        BasicPsaSchedule.builder()
            .section(this.section)
            .column(ColumnKey.FIRST).state(BasicPsaState.builder().build())
            .build()
            .stateMap())
        .isEqualTo(Map.of(ColumnKey.FIRST, BasicPsaState.builder().build()));
  }

  @Mock
  private Monitor monitor;

  /// [BasicPsaSchedule#indexMonitorMap()], [BasicPsaSchedule#indexMap()],
  /// [BasicPsaSchedule#monitorMap()] のテスト.
  @Test
  void testIndexMonitorMap() {
    doReturn(5).when(this.section).number();

    assertThat(BasicPsaSchedule.builder().section(this.section).build().indexMonitorMap())
        .isEmpty();
    assertThat(BasicPsaSchedule.builder().section(this.section).build().indexMap())
        .isEmpty();
    assertThat(BasicPsaSchedule.builder().section(this.section).build().monitorMap())
        .isEmpty();

    assertThat(
        BasicPsaSchedule.builder()
            .section(this.section)
            .fileName("test").index(1).monitor(this.monitor)
            .build().indexMonitorMap())
        .isEqualTo(Map.of("test", new IndexMonitor(1, this.monitor)));
    assertThat(
        BasicPsaSchedule.builder()
            .section(this.section)
            .fileName("test").index(1).monitor(this.monitor)
            .build().indexMap())
        .isEqualTo(Map.of("test", 1));
    assertThat(
        BasicPsaSchedule.builder()
            .section(this.section)
            .fileName("test").index(1).monitor(this.monitor)
            .build().monitorMap())
        .isEqualTo(Map.of("test", this.monitor));
  }

  /// [BasicPsaSchedule#feedValveSet()] のテスト.
  @Test
  void testFeedValveSet() {
    doReturn(5).when(this.section).number();

    assertThat(
        BasicPsaSchedule.builder()
            .section(this.section)
            .build().feedValveSet())
        .isEmpty();

    assertThat(
        BasicPsaSchedule.builder()
            .section(this.section)
            .column(ColumnKey.SECOND)
            .state(BasicPsaState.builder().feedValve().build())
            .column(ColumnKey.FOURTH)
            .state(BasicPsaState.builder().feedValve().build())
            .build().feedValveSet())
        .isEqualTo(Set.of(ColumnKey.SECOND, ColumnKey.FOURTH));
  }

  /// [BasicPsaSchedule#productValveSet()] のテスト.
  @Test
  void testProductValveSet() {
    doReturn(5).when(this.section).number();

    assertThat(
        BasicPsaSchedule.builder()
            .section(this.section)
            .build().productValveSet())
        .isEmpty();

    assertThat(
        BasicPsaSchedule.builder()
            .section(this.section)
            .column(ColumnKey.SECOND)
            .state(BasicPsaState.builder().productValve().build())
            .column(ColumnKey.FOURTH)
            .state(BasicPsaState.builder().productValve().build())
            .build().productValveSet())
        .isEqualTo(Set.of(ColumnKey.SECOND, ColumnKey.FOURTH));
  }

  /// [BasicPsaSchedule#pressurizationValveSet()] のテスト.
  @Test
  void testPressurizationValveSet() {
    doReturn(5).when(this.section).number();

    assertThat(
        BasicPsaSchedule.builder()
            .section(this.section)
            .build().pressurizationValveSet())
        .isEmpty();

    assertThat(
        BasicPsaSchedule.builder()
            .section(this.section)
            .column(ColumnKey.SECOND)
            .state(BasicPsaState.builder().pressurizationValve().build())
            .column(ColumnKey.FOURTH)
            .state(BasicPsaState.builder().pressurizationValve().build())
            .build().pressurizationValveSet())
        .isEqualTo(Set.of(ColumnKey.SECOND, ColumnKey.FOURTH));
  }

  /// [BasicPsaSchedule#rinseValveSet()] のテスト.
  @Test
  void testRinseValveSet() {
    doReturn(5).when(this.section).number();

    assertThat(
        BasicPsaSchedule.builder()
            .section(this.section)
            .build().rinseValveSet())
        .isEmpty();

    assertThat(
        BasicPsaSchedule.builder()
            .section(this.section)
            .column(ColumnKey.SECOND)
            .state(BasicPsaState.builder().rinseValve().build())
            .column(ColumnKey.FOURTH)
            .state(BasicPsaState.builder().rinseValve().build())
            .build().rinseValveSet())
        .isEqualTo(Set.of(ColumnKey.SECOND, ColumnKey.FOURTH));
  }

  /// [BasicPsaSchedule#vacuumValveSet()] のテスト.
  @Test
  void testVacuumValveSet() {
    doReturn(5).when(this.section).number();

    assertThat(
        BasicPsaSchedule.builder()
            .section(this.section)
            .build().vacuumValveSet())
        .isEmpty();

    assertThat(
        BasicPsaSchedule.builder()
            .section(this.section)
            .column(ColumnKey.SECOND)
            .state(BasicPsaState.builder().vacuumValve().build())
            .column(ColumnKey.FOURTH)
            .state(BasicPsaState.builder().vacuumValve().build())
            .build().vacuumValveSet())
        .isEqualTo(Set.of(ColumnKey.SECOND, ColumnKey.FOURTH));
  }

  /// [BasicPsaSchedule#blowValveSetMap()] のテスト.
  @Test
  void testBlowValveSetMap() {
    doReturn(5).when(this.section).number();

    assertThat(
        BasicPsaSchedule.builder()
            .section(this.section)
            .build().blowValveSetMap())
        .isEmpty();

    assertThat(
        BasicPsaSchedule.builder()
            .section(this.section)
            .column(ColumnKey.SECOND)
            .state(
                BasicPsaState.builder()
                    .blowValve(LineKey.FIRST)
                    .blowValve(LineKey.THIRD)
                    .build())
            .column(ColumnKey.FOURTH)
            .state(
                BasicPsaState.builder()
                    .blowValve(LineKey.THIRD)
                    .build())
            .build().blowValveSetMap())
        .isEqualTo(
            Map.of(
                LineKey.FIRST, Set.of(ColumnKey.SECOND),
                LineKey.THIRD, Set.of(ColumnKey.SECOND, ColumnKey.FOURTH)));
  }

  /// [BasicPsaSchedule#upperEqualizationValveSetMap()] のテスト.
  @Test
  void testUpperEqualizationValveSetMap() {
    doReturn(5).when(this.section).number();

    assertThat(
        BasicPsaSchedule.builder()
            .section(this.section)
            .build().upperEqualizationValveSetMap())
        .isEmpty();

    assertThat(
        BasicPsaSchedule.builder()
            .section(this.section)
            .column(ColumnKey.SECOND)
            .state(
                BasicPsaState.builder()
                    .upperEqualizationValve(LineKey.FIRST)
                    .upperEqualizationValve(LineKey.THIRD)
                    .build())
            .column(ColumnKey.FOURTH)
            .state(
                BasicPsaState.builder()
                    .upperEqualizationValve(LineKey.THIRD)
                    .build())
            .build().upperEqualizationValveSetMap())
        .isEqualTo(
            Map.of(
                LineKey.FIRST, Set.of(ColumnKey.SECOND),
                LineKey.THIRD, Set.of(ColumnKey.SECOND, ColumnKey.FOURTH)));
  }

  /// [BasicPsaSchedule#lowerEqualizationValveSetMap()] のテスト.
  @Test
  void testLowerEqualizationValveSetMap() {
    doReturn(5).when(this.section).number();

    assertThat(
        BasicPsaSchedule.builder()
            .section(this.section)
            .build().lowerEqualizationValveSetMap())
        .isEmpty();

    assertThat(
        BasicPsaSchedule.builder()
            .section(this.section)
            .column(ColumnKey.SECOND)
            .state(
                BasicPsaState.builder()
                    .lowerEqualizationValve(LineKey.FIRST)
                    .lowerEqualizationValve(LineKey.THIRD)
                    .build())
            .column(ColumnKey.FOURTH)
            .state(
                BasicPsaState.builder()
                    .lowerEqualizationValve(LineKey.THIRD)
                    .build())
            .build().lowerEqualizationValveSetMap())
        .isEqualTo(
            Map.of(
                LineKey.FIRST, Set.of(ColumnKey.SECOND),
                LineKey.THIRD, Set.of(ColumnKey.SECOND, ColumnKey.FOURTH)));
  }

  @Mock
  private Section section2;

  /// [BasicPsaSchedule#equals(Object)] と [BasicPsaSchedule#hashCode()] のテスト.
  @Test
  void testEquals() {
    doReturn(5).when(this.section).number();
    doReturn(4).when(this.section2).number();

    EqualsTester tester = new EqualsTester();

    Stream.<Supplier<BasicPsaSchedule>>of(
            () -> BasicPsaSchedule.builder()
                .section(this.section)
                .build(),
            () -> BasicPsaSchedule.builder()
                .section(this.section2)
                .build(),
            () -> BasicPsaSchedule.builder()
                .section(this.section)
                .column(ColumnKey.FIRST)
                .state(BasicPsaState.builder().feedValve().build())
                .build(),
            () -> BasicPsaSchedule.builder()
                .section(this.section)
                .fileName("test")
                .index(3)
                .monitor(Monitor.temperature())
                .build())
        .map(supplier -> Stream.generate(supplier).limit(3).toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

  /// [BasicPsaSchedule#toString()] のテスト.
  @Test
  void testToString() {
    doReturn(5).when(this.section).number();

    assertThat(
        BasicPsaSchedule.builder()
            .section(this.section)
            .build())
        .hasToString(
            MoreObjects.toStringHelper(BasicPsaSchedule.class)
                .add("section", this.section)
                .add("stateMap", Collections.emptyMap())
                .add("indexMap", Collections.emptyMap())
                .add("monitorMap", Collections.emptyMap())
                .toString());
  }

}
