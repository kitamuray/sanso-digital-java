/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.google.common.base.MoreObjects;
import com.google.common.testing.EqualsTester;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [Adsorbent] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class AdsorbentTest {
  @Mock
  private SpecificHeat specificHeat;

  /// [Adsorbent.Builder] のテスト.
  @Test
  void testBuilder() {
    assertThatThrownBy(() -> Adsorbent.builder().diameter(0.).build())
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> Adsorbent.builder().voidFraction(0.).build())
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> Adsorbent.builder().voidFraction(1.1).build())
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> Adsorbent.builder().bulkDensity(0.).build())
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> Adsorbent.builder().specificHeat(null).build())
        .isInstanceOf(NullPointerException.class);

    assertThatThrownBy(
        () -> Adsorbent.builder()
            .voidFraction(0.4)
            .bulkDensity(800.)
            .specificHeat(this.specificHeat)
            .build()) // diameter 不足
        .isInstanceOf(IllegalStateException.class);
    assertThatThrownBy(
        () -> Adsorbent.builder()
            .diameter(2e-3)
            .bulkDensity(800.)
            .specificHeat(this.specificHeat)
            .build()) // voidFraction 不足
        .isInstanceOf(IllegalStateException.class);
    assertThatThrownBy(
        () -> Adsorbent.builder()
            .diameter(2e-3)
            .voidFraction(0.4)
            .specificHeat(this.specificHeat)
            .build()) // bulkDensity 不足
        .isInstanceOf(IllegalStateException.class);
    assertThatThrownBy(
        () -> Adsorbent.builder()
            .diameter(2e-3)
            .voidFraction(0.4)
            .bulkDensity(800.)
            .build()) // specificHeat 不足
        .isInstanceOf(IllegalStateException.class);
  }

  @Mock
  private SpecificHeat specificHeat2;

  /// [Adsorbent] のテスト.
  @Test
  void test() {
    final double diameter = 2e-3;
    final double voidFraction = 0.4;
    final double bulkDensity = 800.;

    Adsorbent o = Adsorbent.builder()
        .diameter(diameter)
        .voidFraction(voidFraction)
        .bulkDensity(bulkDensity)
        .specificHeat(this.specificHeat)
        .build();

    assertThat(o)
        .returns(diameter, Adsorbent::diameter)
        .returns(voidFraction, Adsorbent::voidFraction)
        .returns(bulkDensity, Adsorbent::bulkDensity)
        .returns(this.specificHeat, Adsorbent::specificHeat)
        .hasToString(
            MoreObjects.toStringHelper(Adsorbent.class)
                .add("diameter", diameter)
                .add("voidFraction", voidFraction)
                .add("bulkDensity", bulkDensity)
                .add("specificHeat", this.specificHeat)
                .toString());

    EqualsTester tester = new EqualsTester();

    Stream.<Supplier<Adsorbent>>of(
            () -> Adsorbent.builder()
                .diameter(2e-3)
                .voidFraction(0.4)
                .bulkDensity(800.)
                .specificHeat(this.specificHeat)
                .build(),
            () -> Adsorbent.builder()
                .diameter(1e-3)
                .voidFraction(0.4)
                .bulkDensity(800.)
                .specificHeat(this.specificHeat)
                .build(),
            () -> Adsorbent.builder()
                .diameter(2e-3)
                .voidFraction(0.33)
                .bulkDensity(800.)
                .specificHeat(this.specificHeat)
                .build(),
            () -> Adsorbent.builder()
                .diameter(2e-3)
                .voidFraction(0.4)
                .bulkDensity(850.)
                .specificHeat(this.specificHeat)
                .build(),
            () -> Adsorbent.builder()
                .diameter(2e-3)
                .voidFraction(0.4)
                .bulkDensity(800.)
                .specificHeat(this.specificHeat2)
                .build())
        .map(
            supplier -> Stream.generate(supplier)
                .limit(3)
                .toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

  /// [Adsorbent#empty()] のテスト.
  @Test
  void testEmpty() {
    assertThat(Adsorbent.empty())
        .returns(Double.MAX_VALUE, Adsorbent::diameter)
        .returns(1., Adsorbent::voidFraction)
        .returns(Double.MIN_VALUE, Adsorbent::bulkDensity)
        .returns(ConstantSpecificHeat.valueOf(Double.MIN_VALUE), Adsorbent::specificHeat);
  }

}
