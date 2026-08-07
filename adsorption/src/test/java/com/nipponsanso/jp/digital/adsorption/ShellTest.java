/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.google.common.base.MoreObjects;
import com.google.common.testing.EqualsTester;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [Shell] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class ShellTest {

  /// [Shell.Builder] のテスト.
  @Nested
  class BuilderTest {

    /// [Shell.Builder#density(double)] のテスト.
    @Test
    void testDensityDouble() {
      //noinspection WriteOnlyObject
      assertThatThrownBy(() -> Shell.builder().density(0.))
          .isInstanceOf(IllegalArgumentException.class);
    }

    /// [Shell.Builder#specificHeat(SpecificHeat)] のテスト.
    @Test
    void testSpecificHeatSpecificHeat() {
      //noinspection WriteOnlyObject
      assertThatThrownBy(() -> Shell.builder().specificHeat(null))
          .isInstanceOf(NullPointerException.class);
    }

    /// [Shell.Builder#thermalConductivity(ThermalConductivity)] のテスト.
    @Test
    void testThermalConductivityThermalConductivity() {
      //noinspection WriteOnlyObject
      assertThatThrownBy(() -> Shell.builder().thermalConductivity(null))
          .isInstanceOf(NullPointerException.class);
    }

    @Mock
    private SpecificHeat specificHeat;

    @Mock
    private ThermalConductivity thermalConductivity;

    /// [Shell.Builder#build()] のテスト.
    @Test
    void testBuild() {
      assertThatThrownBy(
          () -> Shell.builder()
              .specificHeat(this.specificHeat)
              .thermalConductivity(this.thermalConductivity)
              .build())
          .isInstanceOf(IllegalStateException.class);

      assertThatThrownBy(
          () -> Shell.builder()
              .density(7930.)
              .thermalConductivity(this.thermalConductivity)
              .build())
          .isInstanceOf(IllegalStateException.class);

      assertThatThrownBy(
          () -> Shell.builder()
              .density(7930.)
              .specificHeat(this.specificHeat)
              .build())
          .isInstanceOf(IllegalStateException.class);
    }

  } // BuilderTest

  @Mock
  private SpecificHeat specificHeat;

  @Mock
  private ThermalConductivity thermalConductivity;

  /// [Shell#density()] のテスト.
  @Test
  void testDensity() {
    final double density = 7930.;

    assertThat(
        Shell.builder()
            .density(density)
            .specificHeat(this.specificHeat)
            .thermalConductivity(this.thermalConductivity)
            .build()
            .density())
        .isEqualTo(density);
  }

  /// [Shell#specificHeat()] のテスト.
  @Test
  void testSpecificHeat() {
    assertThat(
        Shell.builder()
            .density(7930.)
            .specificHeat(this.specificHeat)
            .thermalConductivity(this.thermalConductivity)
            .build()
            .specificHeat())
        .isSameAs(this.specificHeat);
  }

  /// [Shell#thermalConductivity()] のテスト.
  @Test
  void testThermalConductivity() {
    assertThat(
        Shell.builder()
            .density(7930.)
            .specificHeat(this.specificHeat)
            .thermalConductivity(this.thermalConductivity)
            .build()
            .thermalConductivity())
        .isSameAs(this.thermalConductivity);
  }

  @Mock
  private SpecificHeat specificHeat2;

  @Mock
  private ThermalConductivity thermalConductivity2;

  /// [Shell#equals(Object)] と [Shell#hashCode()] のテスト.
  @Test
  void testEquals() {
    EqualsTester tester = new EqualsTester();

    Stream.<Supplier<Shell>>of(
        () -> Shell.builder()
            .density(7930.)
            .specificHeat(this.specificHeat)
            .thermalConductivity(this.thermalConductivity)
            .build(),
        () -> Shell.builder()
            .density(8000.)
            .specificHeat(this.specificHeat)
            .thermalConductivity(this.thermalConductivity)
            .build(),
        () -> Shell.builder()
            .density(7930.)
            .specificHeat(this.specificHeat2)
            .thermalConductivity(this.thermalConductivity)
            .build(),
        () -> Shell.builder()
            .density(7930.)
            .specificHeat(this.specificHeat)
            .thermalConductivity(this.thermalConductivity2)
            .build())
        .map(supplier -> Stream.generate(supplier).limit(3).toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

  /// [Shell#toString()] のテスト.
  @Test
  void testToString() {
    assertThat(
        Shell.builder()
            .density(7930.)
            .specificHeat(this.specificHeat)
            .thermalConductivity(this.thermalConductivity)
            .build())
        .hasToString(
            MoreObjects.toStringHelper(Shell.class)
                .add("density", 7930.)
                .add("specificHeat", this.specificHeat)
                .add("thermalConductivity", this.thermalConductivity)
                .toString());
  }

}
