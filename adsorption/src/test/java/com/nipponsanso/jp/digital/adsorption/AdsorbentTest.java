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

  /// [Adsorbent.Builder] のテスト.
  @Nested
  class BuilderTest {

    /// [Adsorbent.Builder#diameter(double)] のテスト.
    @Test
    void testDiameterDouble() {
      //noinspection WriteOnlyObject
      assertThatThrownBy(() -> Adsorbent.builder().diameter(0.))
          .isInstanceOf(IllegalArgumentException.class);
    }

    /// [Adsorbent.Builder#voidFraction(double)] のテスト.
    @Test
    void testVoidFractionDouble() {
      //noinspection WriteOnlyObject
      assertThatThrownBy(() -> Adsorbent.builder().voidFraction(0.))
          .isInstanceOf(IllegalArgumentException.class);
      //noinspection WriteOnlyObject
      assertThatThrownBy(() -> Adsorbent.builder().voidFraction(1.1))
          .isInstanceOf(IllegalArgumentException.class);
    }

    /// [Adsorbent.Builder#bulkDensity(double)] のテスト.
    @Test
    void testBulkDensityDouble() {
      //noinspection WriteOnlyObject
      assertThatThrownBy(() -> Adsorbent.builder().bulkDensity(0.))
          .isInstanceOf(IllegalArgumentException.class);
    }

    /// [Adsorbent.Builder#specificHeat(SpecificHeat)] のテスト.
    @Test
    void testSpecificHeatSpecificHeat() {
      //noinspection WriteOnlyObject
      assertThatThrownBy(() -> Adsorbent.builder().specificHeat(null))
          .isInstanceOf(NullPointerException.class);
    }

    @Mock
    private SpecificHeat specificHeat;

    /// [Adsorbent.Builder#build()] のテスト.
    @Test
    void testBuild() {
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

  } // BuilderTest

  @Mock
  private SpecificHeat specificHeat;

  /// [Adsorbent#diameter()] のテスト.
  @Test
  void testDiameter() {
    final double diameter = 2e-3;
    final double voidFraction = 0.4;
    final double bulkDensity = 800.;

    assertThat(
        Adsorbent.builder()
            .diameter(diameter)
            .voidFraction(voidFraction)
            .bulkDensity(bulkDensity)
            .specificHeat(this.specificHeat)
            .build()
            .diameter())
        .isEqualTo(diameter);
  }

  /// [Adsorbent#voidFraction()] のテスト.
  @Test
  void testVoidFraction() {
    final double diameter = 2e-3;
    final double voidFraction = 0.4;
    final double bulkDensity = 800.;

    assertThat(
        Adsorbent.builder()
            .diameter(diameter)
            .voidFraction(voidFraction)
            .bulkDensity(bulkDensity)
            .specificHeat(this.specificHeat)
            .build()
            .voidFraction())
        .isEqualTo(voidFraction);
  }

  /// [Adsorbent#bulkDensity()] のテスト.
  @Test
  void testBulkDensity() {
    final double diameter = 2e-3;
    final double voidFraction = 0.4;
    final double bulkDensity = 800.;

    assertThat(
        Adsorbent.builder()
            .diameter(diameter)
            .voidFraction(voidFraction)
            .bulkDensity(bulkDensity)
            .specificHeat(this.specificHeat)
            .build()
            .bulkDensity())
        .isEqualTo(bulkDensity);
  }

  /// [Adsorbent#specificHeat()] のテスト.
  @Test
  void testSpecificHeat() {
    final double diameter = 2e-3;
    final double voidFraction = 0.4;
    final double bulkDensity = 800.;

    assertThat(
        Adsorbent.builder()
            .diameter(diameter)
            .voidFraction(voidFraction)
            .bulkDensity(bulkDensity)
            .specificHeat(this.specificHeat)
            .build()
            .specificHeat())
        .isSameAs(this.specificHeat);
  }

  @Mock
  private SpecificHeat specificHeat2;

  /// [Adsorbent#equals(Object)] と [Adsorbent#hashCode()] のテスト.
  @Test
  void testEquals() {
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

  /// [Adsorbent#toString()] のテスト.
  @Test
  void testToString() {
    assertThat(
        Adsorbent.builder()
            .diameter(2e-3)
            .voidFraction(0.4)
            .bulkDensity(800.)
            .specificHeat(this.specificHeat)
            .build())
        .hasToString(
            MoreObjects.toStringHelper(Adsorbent.class)
                .add("diameter", 2e-3)
                .add("voidFraction", 0.4)
                .add("bulkDensity", 800.)
                .add("specificHeat", this.specificHeat)
                .toString());
  }

}
