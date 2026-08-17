/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.google.common.base.MoreObjects;
import com.google.common.testing.EqualsTester;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/// [Composition] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
class CompositionTest {

  /// [Composition.Builder] のテスト.
  @Nested
  class BuilderTest {

    /// [Composition.Builder#majorComponentKey(ComponentKey)] のテスト.
    @Test
    void testMajorComponentKeyComponentKey() {
      assertThatThrownBy(
          () -> Composition.builder()
              .majorComponentKey(null))
          .isInstanceOf(NullPointerException.class);
      assertThatThrownBy(
          () -> Composition.builder()
              .minorComponentKey(ComponentKey.NITROGEN)
              .majorComponentKey(ComponentKey.NITROGEN))
          .isInstanceOf(IllegalArgumentException.class);
    }

    /// [Composition.Builder#minorComponentKey(ComponentKey)] のテスト.
    @Test
    void testMinorComponentKeyComponentKey() {
      assertThatThrownBy(
          () -> Composition.builder()
              .minorComponentKey(null))
          .isInstanceOf(NullPointerException.class);
      assertThatThrownBy(
          () -> Composition.builder()
              .minorComponentKey(ComponentKey.NITROGEN)
              .minorComponentKey(ComponentKey.NITROGEN))
          .isInstanceOf(IllegalArgumentException.class);
      assertThatThrownBy(
          () -> Composition.builder()
              .majorComponentKey(ComponentKey.NITROGEN)
              .minorComponentKey(ComponentKey.NITROGEN))
          .isInstanceOf(IllegalArgumentException.class);
    }

    /// [Composition.Builder#build()] のテスト.
    @Test
    void testBuild() {
      assertThatThrownBy(
          () -> Composition.builder()
              .build())
          .isInstanceOf(IllegalStateException.class);
    }

  } // BuilderTest

  /// [Composition#majorComponentKey()] のテスト.
  @Test
  void testMajorComponentKey() {
    assertThat(
        Composition.builder()
            .majorComponentKey(ComponentKey.NITROGEN)
            .minorComponentKey(ComponentKey.ARGON)
            .minorComponentKey(ComponentKey.OXYGEN)
            .build()
            .majorComponentKey())
        .isEqualTo(ComponentKey.NITROGEN);
  }

  /// [Composition#minorComponentKeySet()] のテスト.
  @Test
  void testMinorComponentKeySet() {
    assertThat(
        Composition.builder()
            .majorComponentKey(ComponentKey.NITROGEN)
            .minorComponentKey(ComponentKey.ARGON)
            .minorComponentKey(ComponentKey.OXYGEN)
            .build()
            .minorComponentKeySet())
        .isEqualTo(
            Set.of(ComponentKey.ARGON, ComponentKey.OXYGEN));
  }

  /// [Composition#componentKeySet()] のテスト.
  @Test
  void testComponentKeySet() {
    assertThat(
        Composition.builder()
            .majorComponentKey(ComponentKey.NITROGEN)
            .minorComponentKey(ComponentKey.ARGON)
            .minorComponentKey(ComponentKey.OXYGEN)
            .build()
            .componentKeySet())
        .isEqualTo(
            Set.of(
                ComponentKey.ARGON,
                ComponentKey.NITROGEN,
                ComponentKey.OXYGEN));
  }

  /// [Composition#equals(Object)] と [Composition#hashCode()] のテスト.
  @Test
  void testEquals() {
    EqualsTester tester = new EqualsTester();

    Stream.<Supplier<Composition>>of(
            () -> Composition.builder()
                .majorComponentKey(ComponentKey.NITROGEN)
                .minorComponentKey(ComponentKey.ARGON)
                .minorComponentKey(ComponentKey.OXYGEN)
                .build(),
            () -> Composition.builder()
                .majorComponentKey(ComponentKey.HYDROGEN)
                .minorComponentKey(ComponentKey.ARGON)
                .minorComponentKey(ComponentKey.OXYGEN)
                .build(),
            () -> Composition.builder()
                .majorComponentKey(ComponentKey.NITROGEN)
                .minorComponentKey(ComponentKey.HELIUM)
                .minorComponentKey(ComponentKey.OXYGEN)
                .build(),
            () -> Composition.builder()
                .majorComponentKey(ComponentKey.NITROGEN)
                .minorComponentKey(ComponentKey.OXYGEN)
                .build())
        .map(supplier -> Stream.generate(supplier).limit(3).toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

  /// [Composition#toString()] のテスト.
  @Test
  void testToString() {
    assertThat(
        Composition.builder()
            .majorComponentKey(ComponentKey.NITROGEN)
            .minorComponentKey(ComponentKey.ARGON)
            .minorComponentKey(ComponentKey.OXYGEN)
            .build())
        .hasToString(
            MoreObjects.toStringHelper(Composition.class)
                .add("majorComponentKey", ComponentKey.NITROGEN)
                .add(
                    "minorComponentKeySet",
                    EnumSet.of(ComponentKey.ARGON, ComponentKey.OXYGEN))
                .toString());
  }

}
