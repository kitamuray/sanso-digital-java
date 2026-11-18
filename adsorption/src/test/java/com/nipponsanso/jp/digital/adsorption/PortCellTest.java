/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;

import com.google.common.collect.Sets;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [PortCell] のテスト.
///
/// @since 1.1
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class PortCellTest {

  @Mock
  private Composition composition;

  @Mock
  private FluidCellEntity<?, ?, ?> entity;

  /// [FluidCell] を代表して
  /// [PortCell#getTemperature()] と [PortCell#setTemperature(double)] のテスト.
  @Test
  void testTemperature() {
    final Set<ComponentKey> componentKeySet
        = Sets.immutableEnumSet(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    PortCell cell = new PortCell(this.composition);

    final double temperature = 298.15;
    cell.setTemperature(temperature);
    assertThat(cell.getTemperature()).isEqualTo(temperature);
  }

  /// [PortCell#componentMap()] のテスト.
  @Test
  void testComponentMap() {
    final Set<ComponentKey> componentKeySet
        = Sets.immutableEnumSet(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    PortCell cell = new PortCell(this.composition);

    assertThat(cell.componentMap().keySet()).isEqualTo(componentKeySet);
  }

  /// [PortCell#read(FluidCellEntity)] と [PortCell#write(FluidCellEntity)] のテスト.
  @Test
  void testEntity() {
    final Set<ComponentKey> componentKeySet
        = Sets.immutableEnumSet(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    PortCell cell = new PortCell(this.composition);

    assertThatThrownBy(() -> cell.read(this.entity))
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> cell.write(this.entity))
        .isInstanceOf(UnsupportedOperationException.class);
  }

  /// [PortCell.Previous] のテスト.
  @Nested
  class PreviousTest {

    @Mock
    private Composition composition;

    @Mock
    private FluidCellEntity.Previous entity;

    /// [FluidCell.Previous] を代表して
    /// [PortCell.Previous#getTemperature()] と [PortCell.Previous#setTemperature(double)] のテスト.
    @Test
    void testTemperature() {
      final Set<ComponentKey> componentKeySet
          = Sets.immutableEnumSet(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
      doReturn(componentKeySet).when(this.composition).componentKeySet();

      PortCell cell = new PortCell(this.composition);

      final double temperature = 298.15;
      cell.previous().setTemperature(temperature);
      assertThat(cell.previous().getTemperature()).isEqualTo(temperature);
    }

    /// [PortCell.Previous#read(FluidCellEntity.Previous)] と
    /// [PortCell.Previous#write(FluidCellEntity.Previous)] のテスト.
    @Test
    void testEntity() {
      final Set<ComponentKey> componentKeySet
          = Sets.immutableEnumSet(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
      doReturn(componentKeySet).when(this.composition).componentKeySet();

      PortCell cell = new PortCell(this.composition);

      assertThatThrownBy(() -> cell.previous().read(this.entity))
          .isInstanceOf(UnsupportedOperationException.class);
      assertThatThrownBy(() -> cell.previous().write(this.entity))
          .isInstanceOf(UnsupportedOperationException.class);
    }

  } // PreviousTest

  /// [PortCell.ComponentValue] のテスト.
  @Nested
  class ComponentValueTest {

    @Mock
    private Composition composition;

    @Mock
    private FluidCellEntity.ComponentValue<?> entity;

    /// [FluidCell.ComponentValue] を代表して
    /// [PortCell.ComponentValue#getFraction()] と
    /// [PortCell.ComponentValue#setFraction(double)] のテスト.
    @Test
    void testFraction() {
      final Set<ComponentKey> componentKeySet
          = Sets.immutableEnumSet(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
      doReturn(componentKeySet).when(this.composition).componentKeySet();

      PortCell cell = new PortCell(this.composition);

      final double fraction = 0.12;
      cell.componentMap().get(ComponentKey.NITROGEN).setFraction(fraction);
      assertThat(cell.componentMap().get(ComponentKey.NITROGEN).getFraction()).isEqualTo(fraction);
    }

    /// [PortCell.ComponentValue#read(FluidCellEntity.ComponentValue)] と
    /// [PortCell.ComponentValue#write(FluidCellEntity.ComponentValue)] のテスト.
    @Test
    void testEntity() {
      final Set<ComponentKey> componentKeySet
          = Sets.immutableEnumSet(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
      doReturn(componentKeySet).when(this.composition).componentKeySet();

      PortCell cell = new PortCell(this.composition);

      assertThatThrownBy(() -> cell.componentMap().get(ComponentKey.NITROGEN).read(this.entity))
          .isInstanceOf(UnsupportedOperationException.class);
      assertThatThrownBy(() -> cell.componentMap().get(ComponentKey.NITROGEN).write(this.entity))
          .isInstanceOf(UnsupportedOperationException.class);
    }

    /// [PortCell.ComponentValue.Previous] のテスト.
    @Nested
    class PreviousTest {

      @Mock
      private Composition composition;

      @Mock
      private FluidCellEntity.ComponentValue.Previous entity;

      /// [FluidCell.ComponentValue.Previous] を代表して
      /// [PortCell.ComponentValue.Previous#getFraction()] と
      /// [PortCell.ComponentValue.Previous#setFraction(double)] のテスト.
      @Test
      void testFraction() {
        final Set<ComponentKey> componentKeySet
            = Sets.immutableEnumSet(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
        doReturn(componentKeySet).when(this.composition).componentKeySet();

        PortCell cell = new PortCell(this.composition);

        final double fraction = 0.12;
        cell.componentMap().get(ComponentKey.NITROGEN).previous().setFraction(fraction);
        assertThat(cell.componentMap().get(ComponentKey.NITROGEN).previous().getFraction())
            .isEqualTo(fraction);
      }

      /// [PortCell.ComponentValue.Previous#read(FluidCellEntity.ComponentValue.Previous)] と
      /// [PortCell.ComponentValue.Previous#write(FluidCellEntity.ComponentValue.Previous)] のテスト.
      @Test
      void testEntity() {
        final Set<ComponentKey> componentKeySet
            = Sets.immutableEnumSet(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
        doReturn(componentKeySet).when(this.composition).componentKeySet();

        PortCell cell = new PortCell(this.composition);

        assertThatThrownBy(
            () -> cell.componentMap().get(ComponentKey.NITROGEN).previous().read(this.entity))
            .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(
            () -> cell.componentMap().get(ComponentKey.NITROGEN).previous().write(this.entity))
            .isInstanceOf(UnsupportedOperationException.class);
      }

    } // PreviousTest

  } // ComponentValueTest

}
