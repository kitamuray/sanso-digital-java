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

/// [Tank] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
class TankTest {

  /// [Tank.Builder] のテスト.
  @Nested
  class BuilderTest {

    /// [Tank.Builder#volume(double)] のテスト.
    @Test
    void testVolumeDouble() {
      assertThatThrownBy(() -> Tank.builder().volume(0.))
          .isInstanceOf(IllegalArgumentException.class);
    }

    /// [Tank.Builder#fileName(String)] のテスト.
    @Test
    void testFileNameString() {
      assertThatThrownBy(() -> Tank.builder().fileName(null))
          .isInstanceOf(NullPointerException.class);
    }

    /// [MonitorMapBuilder#monitor(Monitor)] のテスト.
    @Test
    void testMonitorMonitor() {
      assertThatThrownBy(() -> Tank.builder().fileName("test").monitor(null))
          .isInstanceOf(NullPointerException.class);
    }

    /// [Tank.Builder#build()] のテスト.
    @Test
    void testBuild() {
      assertThatThrownBy(() -> Tank.builder().build())
          .isInstanceOf(IllegalStateException.class);
    }

  } // BuilderTest

  /// [Tank#volume()] のテスト1.
  @Test
  void testVolume() {
    final double volume = 1.2;

    assertThat(
        Tank.builder()
            .volume(volume)
            .build().volume())
        .isEqualTo(volume);
  }

  /// [Tank#monitorMap()] のテスト.
  @Test
  void testMonitorMap() {
    assertThat(Tank.builder().volume(1.2).build().monitorMap()).isEmpty();

    assertThat(
        Tank.builder()
            .volume(1.2)
            .fileName("test")
            .monitor(Monitor.temperature())
            .build().monitorMap())
        .isEqualTo(Map.of("test", Monitor.temperature()));
  }

  /// [Tank#equals(Object)] と [Tank#hashCode()] のテスト.
  @Test
  void testEquals() {
    EqualsTester tester = new EqualsTester();

    Stream.<Supplier<Tank>>of(
        () -> Tank.builder()
            .volume(1.2)
            .build(),
        () -> Tank.builder()
            .volume(2.1)
            .build(),
        () -> Tank.builder()
            .volume(1.2)
            .fileName("test").monitor(Monitor.temperature())
            .build(),
        () -> Tank.builder()
            .volume(1.2)
            .fileName("test").monitor(Monitor.pressure())
            .build(),
        () -> Tank.builder()
            .volume(1.2)
            .fileName("sample").monitor(Monitor.temperature())
            .build())
        .map(supplier -> Stream.generate(supplier).limit(3).toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

  /// [Tank#toString()] のテスト.
  @Test
  void testToString() {
    assertThat(
        Tank.builder()
            .volume(1.2)
            .fileName("test").monitor(Monitor.temperature())
            .build())
        .hasToString(
            MoreObjects.toStringHelper(Tank.class)
                .add("volume", 1.2)
                .add("monitorMap", Map.of("test", Monitor.temperature()))
                .toString());
  }

}
