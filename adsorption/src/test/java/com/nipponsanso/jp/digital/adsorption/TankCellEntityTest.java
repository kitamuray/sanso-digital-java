/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.base.MoreObjects;
import java.util.Collections;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [TankCellEntity] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class TankCellEntityTest {

  @Mock
  private TankCellEntity.Previous previous;

  @Mock
  private TankCellEntity.ComponentValue componentValue;

  /// [TankCellEntity#toString()] のテスト.
  @Test
  void testToString() {
    TankCellEntity entity = new TankCellEntity();

    entity.setTemperature(298.15);
    entity.setPressure(101325.);
    entity.setDensity(1.2);
    entity.setSpecificEnthalpy(10000.);
    entity.setSpecificConstantPressureSpecificHeat(1000.);
    entity.setViscosity(18e-6);
    entity.setThermalConductivity(0.02);
    entity.setPreviousEntity(this.previous);
    entity.setComponentEntityMap(
        Collections.singletonMap(ComponentKey.NITROGEN, this.componentValue));

    assertThat(entity)
        .hasToString(
            MoreObjects.toStringHelper(TankCellEntity.class)
                .add("temperature", 298.15)
                .add("pressure", 101325.)
                .add("density", 1.2)
                .add("specificEnthalpy", 10000.)
                .add("specificConstantPressureSpecificHeat", 1000.)
                .add("viscosity", 18e-6)
                .add("thermalConductivity", 0.02)
                .add("previous", this.previous)
                .add(
                    "componentMap",
                    Collections.singletonMap(ComponentKey.NITROGEN, this.componentValue))
                .toString());
  }

  /// [TankCellEntity.Previous] のテスト.
  @Nested
  class PreviousTest {

    /// [TankCellEntity.Previous#toString()] のテスト.
    @Test
    void testToString() {
      TankCellEntity.Previous entity = new TankCellEntity.Previous();

      entity.setTemperature(298.15);
      entity.setDensity(1.2);
      entity.setSpecificEnthalpy(10000.);
      entity.setSpecificConstantPressureSpecificHeat(1000.);

      assertThat(entity)
          .hasToString(
              MoreObjects.toStringHelper(TankCellEntity.Previous.class)
                  .add("temperature", 298.15)
                  .add("density", 1.2)
                  .add("specificEnthalpy", 10000.)
                  .add("specificConstantPressureSpecificHeat", 1000.)
                  .toString());
    }

  } // PreviousTest

  /// [TankCellEntity.ComponentValue] のテスト.
  @Nested
  class ComponentValueTest {

    @Mock
    private TankCellEntity.ComponentValue.Previous previous;

    /**
     * {@link TankCellEntity.ComponentValue#toString()} のテスト.
     */
    @Test
    void testToString() {
      TankCellEntity.ComponentValue entity = new TankCellEntity.ComponentValue();

      entity.setFraction(0.1);
      entity.setDiffusivity(5e-4);
      entity.setPreviousEntity(this.previous);

      assertThat(entity)
          .hasToString(
              MoreObjects.toStringHelper(TankCellEntity.ComponentValue.class)
                  .add("fraction", 0.1)
                  .add("diffusivity", 5e-4)
                  .add("previous", this.previous)
                  .toString());
    }

    /// [TankCellEntity.ComponentValue.Previous] のテスト.
    @Nested
    class PreviousTest {

      /// [TankCellEntity.ComponentValue.Previous#toString()] のテスト.
      @Test
      void testToString() {
        TankCellEntity.ComponentValue.Previous entity =
            new TankCellEntity.ComponentValue.Previous();

        entity.setFraction(0.2);

        assertThat(entity)
            .hasToString(
              MoreObjects.toStringHelper(TankCellEntity.ComponentValue.Previous.class)
                  .add("fraction", 0.2)
                  .toString());
      }

    } // ComponentValueTest.PreviousTest

  } // ComponentValueTest

}
