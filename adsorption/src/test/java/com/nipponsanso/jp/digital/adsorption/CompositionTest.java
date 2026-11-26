/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;

import com.google.common.base.MoreObjects;
import com.google.common.testing.EqualsTester;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [Composition] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
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

  @Mock
  private FluidCell2 cell;

  @Mock
  private FluidCell2.ComponentValue argonValue;

  @Mock
  private FluidCell2.ComponentValue oxygenValue;

  @Mock
  private FluidCell2.ComponentValue nitrogenValue;

  /// [Composition#updateMajorFraction(FluidCell2)] のテスト.
  @Test
  void testUpdateMajorFractionFluidCell() {
    final double argonFraction = 0.01288;
    final double oxygenFraction = 0.23139;
    final double nitrogenFraction = 1. - argonFraction - oxygenFraction;

    doReturn(argonFraction).when(this.argonValue).getFraction();
    doReturn(oxygenFraction).when(this.oxygenValue).getFraction();
    doAnswer(
        invocation -> assertThat(invocation.getArgument(0, Double.class))
            .isCloseTo(nitrogenFraction, within(1e-9)))
        .when(this.nitrogenValue).setFraction(anyDouble());
    doReturn(
        Map.of(
            ComponentKey.ARGON, this.argonValue,
            ComponentKey.OXYGEN, this.oxygenValue,
            ComponentKey.NITROGEN, this.nitrogenValue))
        .when(this.cell).componentMap();

    Composition composition = Composition.builder()
        .majorComponentKey(ComponentKey.NITROGEN)
        .minorComponentKey(ComponentKey.ARGON)
        .minorComponentKey(ComponentKey.OXYGEN)
        .build();

    composition.updateMajorFraction(this.cell);
  }

  /// [Composition#updateMolecularWeight(FluidCell2)] のテスト.
  @Test
  void testUpdateMolecularWeightFluidCell() {
    final double argonFraction = 0.01288;
    final double oxygenFraction = 0.23139;
    final double nitrogenFraction = 1. - argonFraction - oxygenFraction;
    final double molecularWeight = 28.97;

    doReturn(argonFraction).when(this.argonValue).getFraction();
    doReturn(oxygenFraction).when(this.oxygenValue).getFraction();
    doReturn(nitrogenFraction).when(this.nitrogenValue).getFraction();
    doReturn(
        Map.of(
            ComponentKey.ARGON, this.argonValue,
            ComponentKey.OXYGEN, this.oxygenValue,
            ComponentKey.NITROGEN, this.nitrogenValue))
        .when(this.cell).componentMap();

    doAnswer(
        invocation -> assertThat(invocation.getArgument(0, Double.class))
            .isCloseTo(molecularWeight, within(0.1)))
        .when(this.cell)
        .setMolecularWeight(anyDouble());

    Composition composition = Composition.builder()
        .majorComponentKey(ComponentKey.NITROGEN)
        .minorComponentKey(ComponentKey.ARGON)
        .minorComponentKey(ComponentKey.OXYGEN)
        .build();

    composition.updateMolecularWeight(this.cell);
  }

  /// [Composition#updateMolarFractions(FluidCell2)] のテスト.
  @Test
  void testUpdateMolarFractionsFluidCell() {
    final double argonFraction = 0.01288;
    final double oxygenFraction = 0.23139;
    final double nitrogenFraction = 1. - argonFraction - oxygenFraction;
    final double molecularWeight = 28.97;
    final double argonMolarFraction = 0.0093;
    final double oxygenMolarFraction = 0.2095;
    final double nitrogenMolarFraction = 1. - argonMolarFraction - oxygenMolarFraction;

    doReturn(argonFraction).when(this.argonValue).getFraction();
    doReturn(oxygenFraction).when(this.oxygenValue).getFraction();
    doReturn(nitrogenFraction).when(this.nitrogenValue).getFraction();
    doReturn(molecularWeight).when(this.cell).getMolecularWeight();
    doReturn(
        Map.of(
            ComponentKey.ARGON, this.argonValue,
            ComponentKey.OXYGEN, this.oxygenValue,
            ComponentKey.NITROGEN, this.nitrogenValue))
        .when(this.cell).componentMap();

    doAnswer(
        invocation -> assertThat(invocation.getArgument(0, Double.class))
            .isCloseTo(argonMolarFraction, within(1e-3)))
        .when(this.argonValue)
        .setMolarFraction(anyDouble());
    doAnswer(
        invocation -> assertThat(invocation.getArgument(0, Double.class))
            .isCloseTo(oxygenMolarFraction, within(1e-3)))
        .when(this.oxygenValue)
        .setMolarFraction(anyDouble());
    doAnswer(
        invocation -> assertThat(invocation.getArgument(0, Double.class))
            .isCloseTo(nitrogenMolarFraction, within(1e-3)))
        .when(this.nitrogenValue)
        .setMolarFraction(anyDouble());

    Composition composition = Composition.builder()
        .majorComponentKey(ComponentKey.NITROGEN)
        .minorComponentKey(ComponentKey.ARGON)
        .minorComponentKey(ComponentKey.OXYGEN)
        .build();

    composition.updateMolarFractions(this.cell);
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
