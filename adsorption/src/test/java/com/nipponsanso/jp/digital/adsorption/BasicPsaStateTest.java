/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.google.common.base.MoreObjects;
import com.google.common.testing.EqualsTester;
import java.util.Collections;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/// [BasicPsaState] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
class BasicPsaStateTest {

  /// [BasicPsaState.Builder] のテスト.
  @Nested
  class BuilderTest {

    /// [BasicPsaState.Builder#blowValve(LineKey)] のテスト.
    @Test
    void testBlowValveLineKey() {
      assertThatThrownBy(() -> BasicPsaState.builder().blowValve(null))
          .isInstanceOf(NullPointerException.class);
    }

    /// [BasicPsaState.Builder#upperEqualizationValve(LineKey)] のテスト.
    @Test
    void testUpperEqualizationValveLineKey() {
      assertThatThrownBy(() -> BasicPsaState.builder().upperEqualizationValve(null))
          .isInstanceOf(NullPointerException.class);
    }

    /// [BasicPsaState.Builder#lowerEqualizationValve(LineKey)] のテスト.
    @Test
    void testLowerEqualizationValveLineKey() {
      assertThatThrownBy(() -> BasicPsaState.builder().lowerEqualizationValve(null))
          .isInstanceOf(NullPointerException.class);
    }

  } // BuilderTest

  /// [BasicPsaState#isFeedValve()] のテスト.
  @Test
  void testIsFeedValve() {
    assertThat(BasicPsaState.builder().build().isFeedValve()).isFalse();

    assertThat(BasicPsaState.builder().feedValve().build().isFeedValve()).isTrue();
  }

  /// [BasicPsaState#isProductValve()] のテスト.
  @Test
  void testIsProductValve() {
    assertThat(BasicPsaState.builder().build().isProductValve()).isFalse();

    assertThat(BasicPsaState.builder().productValve().build().isProductValve()).isTrue();
  }

  /// [BasicPsaState#isPressurizationValve()] のテスト.
  @Test
  void testIsPressurizationValve() {
    assertThat(BasicPsaState.builder().build().isPressurizationValve()).isFalse();

    assertThat(BasicPsaState.builder().pressurizationValve().build().isPressurizationValve()).isTrue();
  }

  /// [BasicPsaState#isRinseValve()] のテスト.
  @Test
  void testIsRinseValve() {
    assertThat(BasicPsaState.builder().build().isRinseValve()).isFalse();

    assertThat(BasicPsaState.builder().rinseValve().build().isRinseValve()).isTrue();
  }

  /// [BasicPsaState#isVacuumValve()] のテスト.
  @Test
  void testIsVacuumValve() {
    assertThat(BasicPsaState.builder().build().isVacuumValve()).isFalse();

    assertThat(BasicPsaState.builder().vacuumValve().build().isVacuumValve()).isTrue();
  }

  /// [BasicPsaState#blowValveSet()] のテスト.
  @Test
  void testBlowValveSet() {
    assertThat(BasicPsaState.builder().build().blowValveSet()).isEmpty();

    assertThat(
        BasicPsaState.builder()
            .blowValve(LineKey.FIRST)
            .blowValve(LineKey.THIRD)
            .build()
            .blowValveSet())
        .isEqualTo(Set.of(LineKey.FIRST, LineKey.THIRD));
  }

  /// [BasicPsaState#upperEqualizationValveSet()] のテスト.
  @Test
  void testUpperEqualizationValveSet() {
    assertThat(BasicPsaState.builder().build().upperEqualizationValveSet()).isEmpty();

    assertThat(
        BasicPsaState.builder()
            .upperEqualizationValve(LineKey.SECOND)
            .upperEqualizationValve(LineKey.FOURTH)
            .build()
            .upperEqualizationValveSet())
        .isEqualTo(Set.of(LineKey.SECOND, LineKey.FOURTH));
  }

  /// [BasicPsaState#lowerEqualizationValveSet()] のテスト.
  @Test
  void testLowerEqualizationValveSet() {
    assertThat(BasicPsaState.builder().build().lowerEqualizationValveSet()).isEmpty();

    assertThat(
        BasicPsaState.builder()
            .lowerEqualizationValve(LineKey.THIRD)
            .lowerEqualizationValve(LineKey.FIFTH)
            .build()
            .lowerEqualizationValveSet())
        .isEqualTo(Set.of(LineKey.THIRD, LineKey.FIFTH));
  }

  /// [BasicPsaState#equals(Object)] と [BasicPsaState#hashCode()] のテスト.
  @Test
  void testEquals() {
    EqualsTester tester = new EqualsTester();

    Stream.<Supplier<BasicPsaState>>of(
        () -> BasicPsaState.builder().build(),
        () -> BasicPsaState.builder().feedValve().build(),
        () -> BasicPsaState.builder().productValve().build(),
        () -> BasicPsaState.builder().pressurizationValve().build(),
        () -> BasicPsaState.builder().rinseValve().build(),
        () -> BasicPsaState.builder().vacuumValve().build(),
        () -> BasicPsaState.builder().blowValve(LineKey.FIRST).build(),
        () -> BasicPsaState.builder().blowValve(LineKey.SECOND).build(),
        () -> BasicPsaState.builder().upperEqualizationValve(LineKey.THIRD).build(),
        () -> BasicPsaState.builder().upperEqualizationValve(LineKey.FOURTH).build(),
        () -> BasicPsaState.builder().lowerEqualizationValve(LineKey.FIFTH).build(),
        () -> BasicPsaState.builder().lowerEqualizationValve(LineKey.SIXTH).build())
        .map(supplier -> Stream.generate(supplier).limit(3).toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

  /// [BasicPsaState#toString()] のテスト.
  @Test
  void testToString() {
    assertThat(BasicPsaState.builder().build())
        .hasToString(
            MoreObjects.toStringHelper(BasicPsaState.class)
                .add("isFeedValve", false)
                .add("isProductValve", false)
                .add("isPressurizationValve", false)
                .add("isRinseValve", false)
                .add("isVacuumValve", false)
                .add("blowValveSet", Collections.emptySet())
                .add("upperEqualizationValveSet", Collections.emptySet())
                .add("lowerEqualizationValveSet", Collections.emptySet())
                .toString());
  }

}
