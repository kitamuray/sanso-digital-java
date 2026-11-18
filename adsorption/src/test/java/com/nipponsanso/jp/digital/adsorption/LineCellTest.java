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

/// [LineCell] のテスト.
///
/// @since 1.1
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class LineCellTest {

  @Mock
  private Composition composition;

  @Mock
  private FluidCellEntity<?, ?, ?> entity;

  /// [FluidCell] を代表して
  /// [LineCell#getTemperature()] と [LineCell#setTemperature(double)] のテスト.
  @Test
  void testTemperature() {
    final Set<ComponentKey> componentKeySet
        = Sets.immutableEnumSet(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    LineCell cell = new LineCell(this.composition);

    final double temperature = 298.15;
    cell.setTemperature(temperature);
    assertThat(cell.getTemperature()).isEqualTo(temperature);
  }

  /// [LineCell#componentMap()] のテスト.
  @Test
  void testComponentMap() {
    final Set<ComponentKey> componentKeySet
        = Sets.immutableEnumSet(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    LineCell cell = new LineCell(this.composition);

    assertThat(cell.componentMap().keySet()).isEqualTo(componentKeySet);
  }

  /// [LineCell#read(FluidCellEntity)] と [LineCell#write(FluidCellEntity)] のテスト.
  @Test
  void testEntity() {
    final Set<ComponentKey> componentKeySet
        = Sets.immutableEnumSet(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    LineCell cell = new LineCell(this.composition);

    assertThatThrownBy(() -> cell.read(this.entity))
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> cell.write(this.entity))
        .isInstanceOf(UnsupportedOperationException.class);
  }

  /// [LineCell.Previous] のテスト.
  @Nested
  class PreviousTest {

    @Mock
    private Composition composition;

    @Mock
    private FluidCellEntity.Previous entity;

    /// [FluidCell.Previous] を代表して
    /// [LineCell.Previous#getTemperature()] と [LineCell.Previous#setTemperature(double)] のテスト.
    @Test
    void testTemperature() {
      final Set<ComponentKey> componentKeySet
          = Sets.immutableEnumSet(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
      doReturn(componentKeySet).when(this.composition).componentKeySet();

      LineCell cell = new LineCell(this.composition);

      final double temperature = 298.15;
      cell.previous().setTemperature(temperature);
      assertThat(cell.previous().getTemperature()).isEqualTo(temperature);
    }

    /// [LineCell.Previous#read(FluidCellEntity.Previous)] と
    /// [LineCell.Previous#write(FluidCellEntity.Previous)] のテスト.
    @Test
    void testEntity() {
      final Set<ComponentKey> componentKeySet
          = Sets.immutableEnumSet(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
      doReturn(componentKeySet).when(this.composition).componentKeySet();

      LineCell cell = new LineCell(this.composition);

      assertThatThrownBy(() -> cell.previous().read(this.entity))
          .isInstanceOf(UnsupportedOperationException.class);
      assertThatThrownBy(() -> cell.previous().write(this.entity))
          .isInstanceOf(UnsupportedOperationException.class);
    }

  } // PreviousTest

  /// [LineCell.ComponentValue] のテスト.
  @Nested
  class ComponentValueTest {

    @Mock
    private Composition composition;

    @Mock
    private FluidCellEntity.ComponentValue<?> entity;

    /// [FluidCell.ComponentValue] を代表して
    /// [LineCell.ComponentValue#getFraction()] と
    /// [LineCell.ComponentValue#setFraction(double)] のテスト.
    @Test
    void testFraction() {
      final Set<ComponentKey> componentKeySet
          = Sets.immutableEnumSet(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
      doReturn(componentKeySet).when(this.composition).componentKeySet();

      LineCell cell = new LineCell(this.composition);

      final double fraction = 0.12;
      cell.componentMap().get(ComponentKey.NITROGEN).setFraction(fraction);
      assertThat(cell.componentMap().get(ComponentKey.NITROGEN).getFraction()).isEqualTo(fraction);
    }

    /// [LineCell.ComponentValue#read(FluidCellEntity.ComponentValue)] と
    /// [LineCell.ComponentValue#write(FluidCellEntity.ComponentValue)] のテスト.
    @Test
    void testEntity() {
      final Set<ComponentKey> componentKeySet
          = Sets.immutableEnumSet(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
      doReturn(componentKeySet).when(this.composition).componentKeySet();

      LineCell cell = new LineCell(this.composition);

      assertThatThrownBy(() -> cell.componentMap().get(ComponentKey.NITROGEN).read(this.entity))
          .isInstanceOf(UnsupportedOperationException.class);
      assertThatThrownBy(() -> cell.componentMap().get(ComponentKey.NITROGEN).write(this.entity))
          .isInstanceOf(UnsupportedOperationException.class);
    }

    /// [LineCell.ComponentValue.Previous] のテスト.
    @Nested
    class PreviousTest {

      @Mock
      private Composition composition;

      @Mock
      private FluidCellEntity.ComponentValue.Previous entity;

      /// [FluidCell.ComponentValue.Previous] を代表して
      /// [LineCell.ComponentValue.Previous#getFraction()] と
      /// [LineCell.ComponentValue.Previous#setFraction(double)] のテスト.
      @Test
      void testFraction() {
        final Set<ComponentKey> componentKeySet
            = Sets.immutableEnumSet(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
        doReturn(componentKeySet).when(this.composition).componentKeySet();

        LineCell cell = new LineCell(this.composition);

        final double fraction = 0.12;
        cell.componentMap().get(ComponentKey.NITROGEN).previous().setFraction(fraction);
        assertThat(cell.componentMap().get(ComponentKey.NITROGEN).previous().getFraction())
            .isEqualTo(fraction);
      }

      /// [LineCell.ComponentValue.Previous#read(FluidCellEntity.ComponentValue.Previous)] と
      /// [LineCell.ComponentValue.Previous#write(FluidCellEntity.ComponentValue.Previous)] のテスト.
      @Test
      void testEntity() {
        final Set<ComponentKey> componentKeySet
            = Sets.immutableEnumSet(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
        doReturn(componentKeySet).when(this.composition).componentKeySet();

        LineCell cell = new LineCell(this.composition);

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
