/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.Set;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ToDoubleFunction;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [TankCell] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class TankCellTest {

  @Mock
  private Tank tank;

  @Mock
  private Composition composition;

  /// [TankCell#fluidVolume()] のテスト.
  @Test
  void testFluidVolume() {
    final double fluidVolume = 1.2;
    doReturn(fluidVolume).when(this.tank).volume();

    final Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    TankCell cell = new TankCell(this.tank, this.composition);

    assertThat(cell.fluidVolume()).isEqualTo(fluidVolume);
  }

  /// [TankCell] におけるオブジェクトを返すメソッドをテスト.
  @Test
  void testObjects() {
    final Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    TankCell cell = new TankCell(this.tank, this.composition);

    assertThat(cell.previous()).isInstanceOf(TankCell.Previous.class);
    assertThat(cell.componentMap()).isNotNull();
    assertThat(cell.componentMap().keySet()).isEqualTo(componentKeySet);
    cell.componentMap().values().forEach(value -> {
      assertThat(value).isInstanceOf(TankCell.ComponentValue.class);
      assertThat(value.previous()).isInstanceOf(TankCell.ComponentValue.Previous.class);
    });
  }

  /// [TankCell] の永続化をテスト.
  @Test
  void testCellEntity() {
    final Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    record Entry(
        ToDoubleFunction<TankCell> getter,
        ObjDoubleConsumer<TankCell> setter,
        ToDoubleFunction<TankCellEntity> getter2,
        double value) {}

    Stream.of(
            new Entry(
                TankCell::getTemperature,
                TankCell::setTemperature,
                TankCellEntity::temperature,
                298.15),
            new Entry(
                TankCell::getPressure,
                TankCell::setPressure,
                TankCellEntity::pressure,
                101325.),
            new Entry(
                TankCell::getDensity,
                TankCell::setDensity,
                TankCellEntity::density,
                1.2),
            new Entry(
                TankCell::getSpecificEnthalpy,
                TankCell::setSpecificEnthalpy,
                TankCellEntity::specificEnthalpy,
                100000.),
            new Entry(
                TankCell::getSpecificConstantPressureSpecificHeat,
                TankCell::setSpecificConstantPressureSpecificHeat,
                TankCellEntity::specificConstantPressureSpecificHeat,
                1000.),
            new Entry(
                TankCell::getViscosity,
                TankCell::setViscosity,
                TankCellEntity::viscosity,
                18e-6),
            new Entry(
                TankCell::getThermalConductivity,
                TankCell::setThermalConductivity,
                TankCellEntity::thermalConductivity,
                0.02))
        .forEach(entry -> {
          TankCell cell1 = new TankCell(this.tank, this.composition);
          entry.setter().accept(cell1, entry.value());

          TankCellEntity entity = cell1.newEntity();
          assertThat(entry.getter2().applyAsDouble(entity)).isEqualTo(entry.value());

          TankCell cell2 = new TankCell(this.tank, this.composition);
          cell2.accept(entity);
          assertThat(entry.getter().applyAsDouble(cell2)).isEqualTo(entry.value());
        });
  }

  /// [TankCell.Previous] の永続化をテスト.
  @Test
  void testPreviousEntity() {
    final Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    record Entry(
        ToDoubleFunction<TankCell.Previous> getter,
        ObjDoubleConsumer<TankCell.Previous> setter,
        ToDoubleFunction<TankCellEntity.Previous> getter2,
        double value) {}

    Stream.of(
            new Entry(
                TankCell.Previous::getTemperature,
                TankCell.Previous::setTemperature,
                TankCellEntity.Previous::temperature,
                298.15),
            new Entry(
                TankCell.Previous::getDensity,
                TankCell.Previous::setDensity,
                TankCellEntity.Previous::density,
                1.2),
            new Entry(
                TankCell.Previous::getSpecificEnthalpy,
                TankCell.Previous::setSpecificEnthalpy,
                TankCellEntity.Previous::specificEnthalpy,
                1e6),
            new Entry(
                TankCell.Previous::getSpecificConstantPressureSpecificHeat,
                TankCell.Previous::setSpecificConstantPressureSpecificHeat,
                TankCellEntity.Previous::specificConstantPressureSpecificHeat,
                1e3))
        .forEach(entry -> {
          TankCell cell1 = new TankCell(this.tank, this.composition);
          entry.setter().accept(cell1.previous(), entry.value());

          TankCellEntity entity = cell1.newEntity();
          assertThat(entry.getter2().applyAsDouble(entity.previous())).isEqualTo(entry.value());

          TankCell cell2 = new TankCell(this.tank, this.composition);
          cell2.accept(entity);
          assertThat(entry.getter().applyAsDouble(cell2.previous())).isEqualTo(entry.value());
        });
  }

  /// [TankCell.ComponentValue] の永続化をテスト.
  @Test
  void testComponentValueEntity() {
    final Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    record Entry(
        ToDoubleFunction<TankCell.ComponentValue> getter,
        ObjDoubleConsumer<TankCell.ComponentValue> setter,
        ToDoubleFunction<TankCellEntity.ComponentValue> getter2,
        double value) {}

    Stream.of(
            new Entry(
                TankCell.ComponentValue::getFraction,
                TankCell.ComponentValue::setFraction,
                TankCellEntity.ComponentValue::fraction,
                0.12),
            new Entry(
                TankCell.ComponentValue::getDiffusivity,
                TankCell.ComponentValue::setDiffusivity,
                TankCellEntity.ComponentValue::diffusivity,
                5e-4))
        .forEach(entry -> {
          TankCell cell1 = new TankCell(this.tank, this.composition);
          entry.setter().accept(cell1.componentMap().get(ComponentKey.OXYGEN), entry.value());

          TankCellEntity entity = cell1.newEntity();
          assertThat(entry.getter2().applyAsDouble(entity.componentMap().get(ComponentKey.OXYGEN)))
              .isEqualTo(entry.value());

          TankCell cell2 = new TankCell(this.tank, this.composition);
          cell2.accept(entity);
          assertThat(entry.getter().applyAsDouble(cell2.componentMap().get(ComponentKey.OXYGEN)))
              .isEqualTo(entry.value());
        });
  }

  /// [TankCell.ComponentValue.Previous] の永続化をテスト.
  @Test
  void testComponentValuePreviousEntity() {
    final Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    record Entry(
        ToDoubleFunction<TankCell.ComponentValue.Previous> getter,
        ObjDoubleConsumer<TankCell.ComponentValue.Previous> setter,
        ToDoubleFunction<TankCellEntity.ComponentValue.Previous> getter2,
        double value) {}

    Stream.of(
            new Entry(
                TankCell.ComponentValue.Previous::getFraction,
                TankCell.ComponentValue.Previous::setFraction,
                TankCellEntity.ComponentValue.Previous::fraction,
                0.12))
        .forEach(entry -> {
          TankCell cell1 = new TankCell(this.tank, this.composition);
          entry.setter()
              .accept(cell1.componentMap().get(ComponentKey.OXYGEN).previous(), entry.value());

          TankCellEntity entity = cell1.newEntity();
          assertThat(
              entry.getter2()
                  .applyAsDouble(entity.componentMap().get(ComponentKey.OXYGEN).previous()))
              .isEqualTo(entry.value());

          TankCell cell2 = new TankCell(this.tank, this.composition);
          cell2.accept(entity);
          assertThat(entry.getter()
              .applyAsDouble(
                  cell2.componentMap().get(ComponentKey.OXYGEN).previous()))
              .isEqualTo(entry.value());
        });
  }

}
