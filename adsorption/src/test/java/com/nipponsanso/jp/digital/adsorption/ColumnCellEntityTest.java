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

/// [ColumnCellEntity] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class ColumnCellEntityTest {

  @Mock
  private ColumnCellEntity.Previous previous;

  @Mock
  private ColumnCellEntity.ComponentValue componentValue;

  /// [ColumnCellEntity#toString()] のテスト.
  @SuppressWarnings("ExtractMethodRecommender")
  @Test
  void testToString() {
    ColumnCellEntity entity = new ColumnCellEntity();

    entity.setTemperature(298.15);
    entity.setPressure(101325.);
    entity.setDensity(1.2);
    entity.setSpecificEnthalpy(10000.);
    entity.setSpecificConstantPressureSpecificHeat(1000.);
    entity.setViscosity(18e-6);
    entity.setThermalConductivity(0.02);
    entity.setShellTemperature(303.15);
    entity.setShellSpecificHeat(590.);
    entity.setShellThermalConductivity(16.7);
    entity.setAdsorbentSpecificHeat(800.);
    entity.setPreviousEntity(this.previous);
    entity.setComponentEntityMap(
        Collections.singletonMap(ComponentKey.NITROGEN, this.componentValue));

    assertThat(entity)
        .hasToString(
            MoreObjects.toStringHelper(ColumnCellEntity.class)
                .add("temperature", 298.15)
                .add("pressure", 101325.)
                .add("density", 1.2)
                .add("specificEnthalpy", 10000.)
                .add("specificConstantPressureSpecificHeat", 1000.)
                .add("viscosity", 18e-6)
                .add("thermalConductivity", 0.02)
                .add("shellTemperature", 303.15)
                .add("shellSpecificHeat", 590.)
                .add("shellThermalConductivity", 16.7)
                .add("adsorbentSpecificHeat", 800.)
                .add("previous", this.previous)
                .add(
                    "componentMap",
                    Collections.singletonMap(ComponentKey.NITROGEN, this.componentValue))
                .toString());
  }

  /// [ColumnCellEntity.Previous] のテスト.
  @Nested
  class PreviousTest {

    /// [ColumnCellEntity.Previous#toString()] のテスト.
    @Test
    void testToString() {
      ColumnCellEntity.Previous entity = new ColumnCellEntity.Previous();

      entity.setTemperature(298.15);
      entity.setDensity(1.2);
      entity.setSpecificEnthalpy(10000.);
      entity.setSpecificConstantPressureSpecificHeat(1000.);
      entity.setShellTemperature(303.15);
      entity.setShellSpecificHeat(590.);
      entity.setAdsorbentSpecificHeat(800.);

      assertThat(entity)
          .hasToString(
              MoreObjects.toStringHelper(ColumnCellEntity.Previous.class)
                  .add("temperature", 298.15)
                  .add("density", 1.2)
                  .add("specificEnthalpy", 10000.)
                  .add("specificConstantPressureSpecificHeat", 1000.)
                  .add("shellTemperature", 303.15)
                  .add("shellSpecificHeat", 590.)
                  .add("adsorbentSpecificHeat", 800.)
                  .toString());
    }

  } // PreviousTest

  /// [ColumnCellEntity.ComponentValue] のテスト.
  @Nested
  class ComponentValueTest {

    @Mock
    private ColumnCellEntity.ComponentValue.Previous previous;

    /// [ColumnCellEntity.ComponentValue#toString()] のテスト.
    @Test
    void testToString() {
      ColumnCellEntity.ComponentValue entity = new ColumnCellEntity.ComponentValue();

      entity.setFraction(0.1);
      entity.setDiffusivity(5e-4);
      entity.setMolarQuantity(100.);
      entity.setMolarSpecificConstantVolumeSpecificHeat(990.);
      entity.setPreviousEntity(this.previous);

      assertThat(entity)
          .hasToString(
              MoreObjects.toStringHelper(ColumnCellEntity.ComponentValue.class)
                  .add("fraction", 0.1)
                  .add("diffusivity", 5e-4)
                  .add("molarQuantity", 100.)
                  .add("molarSpecificConstantVolumeSpecificHeat", 990.)
                  .add("previous", this.previous)
                  .toString());
    }

    /// [ColumnCellEntity.ComponentValue.Previous] のテスト.
    @Nested
    class PreviousTest {

      /// [ColumnCellEntity.ComponentValue.Previous#toString()] のテスト.
      @Test
      void testToString() {
        ColumnCellEntity.ComponentValue.Previous entity =
            new ColumnCellEntity.ComponentValue.Previous();

        entity.setFraction(0.2);
        entity.setMolarQuantity(100.);
        entity.setMolarSpecificConstantVolumeSpecificHeat(990.);

        assertThat(entity)
            .hasToString(
              MoreObjects.toStringHelper(ColumnCellEntity.ComponentValue.Previous.class)
                  .add("fraction", 0.2)
                  .add("molarQuantity", 100.)
                  .add("molarSpecificConstantVolumeSpecificHeat", 990.)
                  .toString());
      }

    } // ComponentValueTest.PreviousTest

  } // ComponentValueTest

}
