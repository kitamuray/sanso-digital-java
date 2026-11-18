/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [TankCell] のテスト.
///
/// @since 1.1
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class TankCellTest {

  @Mock
  private Composition composition;

  /// [TankCell#componentMap()] のテスト.
  @Test
  void testComponentMap() {
    Set<ComponentKey> componentKeySet = Sets.immutableEnumSet(
        ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    TankCell cell = new TankCell(this.composition);

    assertThat(cell.componentMap().keySet()).isEqualTo(componentKeySet);
  }

  /// [FluidCell] を代表して
  /// [TankCell#getTemperature()] と [TankCell#setTemperature(double)] のテスト.
  @Test
  void testTemperature() {
    Set<ComponentKey> componentKeySet = Sets.immutableEnumSet(
        ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    TankCell cell = new TankCell(this.composition);

    final double temperature = 298.15;
    cell.setTemperature(temperature);
    assertThat(cell.getTemperature()).isEqualTo(temperature);
  }

  /// [TankCell#write(TankCellEntity)] のテスト.
  @Test
  void testWriteTankCellEntity() {
    Set<ComponentKey> componentKeySet = Sets.immutableEnumSet(
        ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    TankCell cell = new TankCell(this.composition);

    final double temperature = 298.15;
    final double fraction = 0.12;

    cell.setTemperature(Double.MIN_VALUE);
    cell.setPressure(Double.MIN_VALUE);
    cell.setDensity(Double.MIN_VALUE);
    cell.setSpecificConstantPressureSpecificHeat(Double.MIN_VALUE);
    cell.setViscosity(Double.MIN_VALUE);
    cell.setThermalConductivity(Double.MIN_VALUE);
    cell.previous().setTemperature(Double.MIN_VALUE);
    cell.previous().setDensity(Double.MIN_VALUE);
    cell.previous().setSpecificConstantPressureSpecificHeat(Double.MIN_VALUE);
    cell.componentMap().values().forEach(componentValue -> {
      componentValue.setFraction(0.);
      componentValue.setDiffusivity(Double.MIN_VALUE);
      componentValue.previous().setFraction(0.);
    });

    cell.setTemperature(temperature);
    cell.componentMap().get(ComponentKey.OXYGEN).setFraction(fraction);

    TankCellEntity entity = new TankCellEntity();
    cell.write(entity);

    assertThat(entity.getTemperature()).isEqualTo(temperature);
  }

  /// [TankCell#read(TankCellEntity)] のテスト.
  @Test
  void testReadTankCellEntity() {
    Set<ComponentKey> componentKeySet = Sets.immutableEnumSet(
        ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    TankCellEntity entity = new TankCellEntity();

    final double temperature = 298.15;
    final double fraction = 0.12;

    entity.setTemperature(temperature);
    entity.setComponentEntityMap(
        componentKeySet.stream()
            .collect(
                ImmutableMap.toImmutableMap(
                    componentKey -> componentKey,
                    _ -> new TankCellEntity.ComponentValue())));
    entity.getComponentEntityMap().get(ComponentKey.OXYGEN).setFraction(fraction);

    TankCell cell = new TankCell(this.composition);
    cell.read(entity);

    assertThat(cell.getTemperature()).isEqualTo(temperature);
    assertThat(cell.componentMap().get(ComponentKey.OXYGEN).getFraction()).isEqualTo(fraction);
  }

  /// [TankCell.Previous] のテスト.
  @Nested
  class PreviousTest {

    @Mock
    private Composition composition;

    /// [FluidCell.Previous] を代表して
    /// [TankCell.Previous#getTemperature()] と
    /// [TankCell.Previous#setTemperature(double)] のテスト.
    @Test
    void testTemperature() {
      Set<ComponentKey> componentKeySet = Sets.immutableEnumSet(
          ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
      doReturn(componentKeySet).when(this.composition).componentKeySet();

      TankCell cell = new TankCell(this.composition);

      final double temperature = 298.15;
      cell.previous().setTemperature(temperature);
      assertThat(cell.previous().getTemperature()).isEqualTo(temperature);
    }

  } // Previous

  /// [TankCell.ComponentValue] のテスト.
  @Nested
  class ComponentValueTest {

    @Mock
    private Composition composition;

    /// [FluidCell.ComponentValue] を代表して
    /// [TankCell.ComponentValue#getFraction()] と
    /// [TankCell.ComponentValue#setFraction(double)] のテスト.
    @Test
    void testTemperature() {
      Set<ComponentKey> componentKeySet = Sets.immutableEnumSet(
          ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
      doReturn(componentKeySet).when(this.composition).componentKeySet();

      TankCell cell = new TankCell(this.composition);

      final double fraction = 0.12;
      cell.componentMap().get(ComponentKey.NITROGEN).setFraction(fraction);
      assertThat(cell.componentMap().get(ComponentKey.NITROGEN).getFraction())
          .isEqualTo(fraction);
    }

    /// [TankCell.ComponentValue.Previous] のテスト.
    @Nested
    class PreviousTest {

      @Mock
      private Composition composition;

      /// [FluidCell.ComponentValue.Previous を代表して
      /// [TankCell.ComponentValue.Previous#getFraction()] と
      /// [TankCell.ComponentValue.Previous#setFraction(double)] のテスト.
      @Test
      void testTemperature() {
        Set<ComponentKey> componentKeySet = Sets.immutableEnumSet(
            ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
        doReturn(componentKeySet).when(this.composition).componentKeySet();

        TankCell cell = new TankCell(this.composition);

        final double fraction = 0.12;
        cell.componentMap().get(ComponentKey.NITROGEN).previous().setFraction(fraction);
        assertThat(cell.componentMap().get(ComponentKey.NITROGEN).previous().getFraction())
            .isEqualTo(fraction);
      }

    } // PreviousTest

  } // ComponentValueTest

}
