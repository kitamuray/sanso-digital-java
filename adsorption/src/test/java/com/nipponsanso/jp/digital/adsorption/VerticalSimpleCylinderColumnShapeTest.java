/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.withinPercentage;

import com.google.common.base.MoreObjects;
import com.google.common.testing.EqualsTester;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/// [VerticalSimpleCylinderColumnShape] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
class VerticalSimpleCylinderColumnShapeTest {

  /// [VerticalSimpleCylinderColumnShape.Builder] のテスト.
  @Nested
  class BuilderTest {

    /// [VerticalSimpleCylinderColumnShape.Builder#insideDiameter(double)] のテスト.
    @Test
    void testInsideDiameterDouble() {
      //noinspection WriteOnlyObject
      assertThatThrownBy(() -> VerticalSimpleCylinderColumnShape.builder().insideDiameter(0.))
          .isInstanceOf(IllegalArgumentException.class);
    }

    /// [VerticalSimpleCylinderColumnShape.Builder#thickness(double)] のテスト.
    @Test
    void testThicknessDouble() {
      //noinspection WriteOnlyObject
      assertThatThrownBy(() -> VerticalSimpleCylinderColumnShape.builder().thickness(0.))
          .isInstanceOf(IllegalArgumentException.class);
    }

    /// [VerticalSimpleCylinderColumnShape.Builder#build()] のテスト.
    @Test
    void testBuilder() {
      assertThatThrownBy(
          () -> VerticalSimpleCylinderColumnShape.builder()
              .thickness(20e-3)
              .build())
          .isInstanceOf(IllegalStateException.class);

      assertThatThrownBy(
          () -> VerticalSimpleCylinderColumnShape.builder()
              .insideDiameter(2.)
              .build())
          .isInstanceOf(IllegalStateException.class);
    }

  } // BuilderTest

  /// [VerticalSimpleCylinderColumnShape#insideDiameter()] のテスト.
  @Test
  void testInsideDiameter() {
    final double insideDiameter = 1.2;

    assertThat(
        VerticalSimpleCylinderColumnShape.builder()
            .insideDiameter(insideDiameter)
            .thickness(20e-3)
            .build()
            .insideDiameter())
        .isEqualTo(insideDiameter);
  }

  /// [VerticalSimpleCylinderColumnShape#thickness()] のテスト.
  @Test
  void testThickness() {
    final double thickness = 20e-3;

    assertThat(
        VerticalSimpleCylinderColumnShape.builder()
            .insideDiameter(1.2)
            .thickness(thickness)
            .build()
            .thickness())
        .isEqualTo(thickness);
  }

  /// [VerticalSimpleCylinderColumnShape#fluidVolume(double, double)] のテスト.
  @Test
  void testFluidVolumeDoubleDouble() {
    final double insideDiameter = 1.2;
    final double thickness = 20e-3;
    final double fromX = 0.2;
    final double toX = 1.8;

    assertThat(
        VerticalSimpleCylinderColumnShape.builder()
            .insideDiameter(insideDiameter)
            .thickness(thickness)
            .build()
            .fluidVolume(fromX, toX))
        .isCloseTo(
            0.25 * Math.PI * insideDiameter * insideDiameter * (toX - fromX),
            withinPercentage(0.1));
  }

  /// [VerticalSimpleCylinderColumnShape#shellVolume(double, double)] のテスト.
  @Test
  void testShellVolumeDoubleDouble() {
    final double insideDiameter = 1.2;
    final double thickness = 20e-3;
    final double fromX = 0.2;
    final double toX = 1.8;

    assertThat(
        VerticalSimpleCylinderColumnShape.builder()
            .insideDiameter(insideDiameter)
            .thickness(thickness)
            .build()
            .shellVolume(fromX, toX))
        .isCloseTo(
            0.25 * Math.PI
                * ((insideDiameter + 2. * thickness) * (insideDiameter + 2. * thickness)
                - insideDiameter * insideDiameter)
                * (toX - fromX),
            withinPercentage(0.1));
  }

  /// [VerticalSimpleCylinderColumnShape#insideArea(double, double)] のテスト.
  @Test
  void testInsideAreaDoubleDouble() {
    final double insideDiameter = 1.2;
    final double thickness = 20e-3;
    final double fromX = 0.2;
    final double toX = 1.8;

    assertThat(
        VerticalSimpleCylinderColumnShape.builder()
            .insideDiameter(insideDiameter)
            .thickness(thickness)
            .build()
            .insideArea(fromX, toX))
        .isCloseTo(
            Math.PI * insideDiameter * (toX - fromX),
            withinPercentage(0.1));
  }

  /// [VerticalSimpleCylinderColumnShape#outsideArea(double, double)] のテスト.
  @Test
  void testOutsideAreaDoubleDouble() {
    final double insideDiameter = 1.2;
    final double thickness = 20e-3;
    final double fromX = 0.2;
    final double toX = 1.8;

    assertThat(
        VerticalSimpleCylinderColumnShape.builder()
            .insideDiameter(insideDiameter)
            .thickness(thickness)
            .build()
            .outsideArea(fromX, toX))
        .isCloseTo(
            Math.PI * (insideDiameter + 2. * thickness) * (toX - fromX),
            withinPercentage(0.1));
  }

  /// [VerticalSimpleCylinderColumnShape#fluidArea(double)] のテスト.
  @Test
  void testFluidAreaDouble() {
    final double insideDiameter = 1.2;
    final double thickness = 20e-3;
    final double coordinate = 0.1;

    assertThat(
        VerticalSimpleCylinderColumnShape.builder()
            .insideDiameter(insideDiameter)
            .thickness(thickness)
            .build()
            .fluidArea(coordinate))
        .isCloseTo(
            0.25 * Math.PI * insideDiameter * insideDiameter,
            withinPercentage(0.1));
  }

  /// [VerticalSimpleCylinderColumnShape#shellArea(double)] のテスト.
  @Test
  void testShellAreaDouble() {
    final double insideDiameter = 1.2;
    final double thickness = 20e-3;
    final double coordinate = 0.1;

    assertThat(
        VerticalSimpleCylinderColumnShape.builder()
            .insideDiameter(insideDiameter)
            .thickness(thickness)
            .build()
            .shellArea(coordinate))
        .isCloseTo(
            0.25 * Math.PI
                * ((insideDiameter + 2. * thickness) * (insideDiameter + 2. * thickness)
                - insideDiameter * insideDiameter),
            withinPercentage(0.1));
  }

  /// [VerticalSimpleCylinderColumnShape#equals(Object)] と
  /// [VerticalSimpleCylinderColumnShape#hashCode()] のテスト.
  @Test
  void testEquals() {
    EqualsTester tester = new EqualsTester();

    Stream.<Supplier<VerticalSimpleCylinderColumnShape>>of(
            () -> VerticalSimpleCylinderColumnShape.builder()
                .insideDiameter(1.2)
                .thickness(20e-3)
                .build(),
            () -> VerticalSimpleCylinderColumnShape.builder()
                .insideDiameter(2.0)
                .thickness(20e-3)
                .build(),
            () -> VerticalSimpleCylinderColumnShape.builder()
                .insideDiameter(1.2)
                .thickness(12e-3)
                .build())
        .map(supplier -> Stream.generate(supplier).limit(3).toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

  /// [VerticalSimpleCylinderColumnShape#toString()] のテスト.
  @Test
  void testToString() {
    assertThat(
        VerticalSimpleCylinderColumnShape.builder()
            .insideDiameter(1.2)
            .thickness(20e-3)
            .build())
        .hasToString(
            MoreObjects.toStringHelper(VerticalSimpleCylinderColumnShape.class)
                .add("insideDiameter", 1.2)
                .add("thickness", 20e-3)
                .toString());
  }

}
