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

/// [TankCell2] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class TankCell2Test {

  @Mock
  private Tank tank;

  @Mock
  private Composition composition;

  /// [TankCell2#fluidVolume()] のテスト.
  @Test
  void testFluidVolume() {
    final double fluidVolume = 1.2;
    doReturn(fluidVolume).when(this.tank).volume();

    final Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    TankCell2 cell = new TankCell2(this.tank, this.composition);

    assertThat(cell.fluidVolume()).isEqualTo(fluidVolume);
  }

  /// [TankCell2] におけるオブジェクトを返すメソッドをテスト.
  @Test
  void testObjects() {
    final Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    TankCell2 cell = new TankCell2(this.tank, this.composition);

    assertThat(cell.previous()).isInstanceOf(TankCell2.Previous.class);
    assertThat(cell.componentMap()).isNotNull();
    assertThat(cell.componentMap().keySet()).isEqualTo(componentKeySet);
    cell.componentMap().values().forEach(value -> {
      assertThat(value).isInstanceOf(TankCell2.ComponentValue.class);
      assertThat(value.previous()).isInstanceOf(TankCell2.ComponentValue.Previous.class);
    });
  }

  /// [TankCell2] の永続化をテスト.
  @Test
  void testCellEntity() {
    final Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    record Entry(
        ToDoubleFunction<TankCell2> getter,
        ObjDoubleConsumer<TankCell2> setter,
        ToDoubleFunction<TankCellEntity2> getter2,
        double value) {}

    Stream.of(
            new Entry(
                TankCell2::getTemperature,
                TankCell2::setTemperature,
                TankCellEntity2::temperature,
                298.15),
            new Entry(
                TankCell2::getPressure,
                TankCell2::setPressure,
                TankCellEntity2::pressure,
                101325.),
            new Entry(
                TankCell2::getDensity,
                TankCell2::setDensity,
                TankCellEntity2::density,
                1.2),
            new Entry(
                TankCell2::getSpecificEnthalpy,
                TankCell2::setSpecificEnthalpy,
                TankCellEntity2::specificEnthalpy,
                100000.),
            new Entry(
                TankCell2::getSpecificConstantPressureSpecificHeat,
                TankCell2::setSpecificConstantPressureSpecificHeat,
                TankCellEntity2::specificConstantPressureSpecificHeat,
                1000.),
            new Entry(
                TankCell2::getViscosity,
                TankCell2::setViscosity,
                TankCellEntity2::viscosity,
                18e-6),
            new Entry(
                TankCell2::getThermalConductivity,
                TankCell2::setThermalConductivity,
                TankCellEntity2::thermalConductivity,
                0.02))
        .forEach(entry -> {
          TankCell2 cell1 = new TankCell2(this.tank, this.composition);
          entry.setter().accept(cell1, entry.value());

          TankCellEntity2 entity = cell1.newEntity();
          assertThat(entry.getter2().applyAsDouble(entity)).isEqualTo(entry.value());

          TankCell2 cell2 = new TankCell2(this.tank, this.composition);
          cell2.accept(entity);
          assertThat(entry.getter().applyAsDouble(cell2)).isEqualTo(entry.value());
        });
  }

  /// [TankCell2.Previous] の永続化をテスト.
  @Test
  void testPreviousEntity() {
    final Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    record Entry(
        ToDoubleFunction<TankCell2.Previous> getter,
        ObjDoubleConsumer<TankCell2.Previous> setter,
        ToDoubleFunction<TankCellEntity2.Previous> getter2,
        double value) {}

    Stream.of(
            new Entry(
                TankCell2.Previous::getTemperature,
                TankCell2.Previous::setTemperature,
                TankCellEntity2.Previous::temperature,
                298.15),
            new Entry(
                TankCell2.Previous::getDensity,
                TankCell2.Previous::setDensity,
                TankCellEntity2.Previous::density,
                1.2),
            new Entry(
                TankCell2.Previous::getSpecificEnthalpy,
                TankCell2.Previous::setSpecificEnthalpy,
                TankCellEntity2.Previous::specificEnthalpy,
                1e6),
            new Entry(
                TankCell2.Previous::getSpecificConstantPressureSpecificHeat,
                TankCell2.Previous::setSpecificConstantPressureSpecificHeat,
                TankCellEntity2.Previous::specificConstantPressureSpecificHeat,
                1e3))
        .forEach(entry -> {
          TankCell2 cell1 = new TankCell2(this.tank, this.composition);
          entry.setter().accept(cell1.previous(), entry.value());

          TankCellEntity2 entity = cell1.newEntity();
          assertThat(entry.getter2().applyAsDouble(entity.previous())).isEqualTo(entry.value());

          TankCell2 cell2 = new TankCell2(this.tank, this.composition);
          cell2.accept(entity);
          assertThat(entry.getter().applyAsDouble(cell2.previous())).isEqualTo(entry.value());
        });
  }

  /// [TankCell2.ComponentValue] の永続化をテスト.
  @Test
  void testComponentValueEntity() {
    final Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    record Entry(
        ToDoubleFunction<TankCell2.ComponentValue> getter,
        ObjDoubleConsumer<TankCell2.ComponentValue> setter,
        ToDoubleFunction<TankCellEntity2.ComponentValue> getter2,
        double value) {}

    Stream.of(
            new Entry(
                TankCell2.ComponentValue::getFraction,
                TankCell2.ComponentValue::setFraction,
                TankCellEntity2.ComponentValue::fraction,
                0.12),
            new Entry(
                TankCell2.ComponentValue::getDiffusivity,
                TankCell2.ComponentValue::setDiffusivity,
                TankCellEntity2.ComponentValue::diffusivity,
                5e-4))
        .forEach(entry -> {
          TankCell2 cell1 = new TankCell2(this.tank, this.composition);
          entry.setter().accept(cell1.componentMap().get(ComponentKey.OXYGEN), entry.value());

          TankCellEntity2 entity = cell1.newEntity();
          assertThat(entry.getter2().applyAsDouble(entity.componentMap().get(ComponentKey.OXYGEN)))
              .isEqualTo(entry.value());

          TankCell2 cell2 = new TankCell2(this.tank, this.composition);
          cell2.accept(entity);
          assertThat(entry.getter().applyAsDouble(cell2.componentMap().get(ComponentKey.OXYGEN)))
              .isEqualTo(entry.value());
        });
  }

  /// [TankCell2.ComponentValue.Previous] の永続化をテスト.
  @Test
  void testComponentValuePreviousEntity() {
    final Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    record Entry(
        ToDoubleFunction<TankCell2.ComponentValue.Previous> getter,
        ObjDoubleConsumer<TankCell2.ComponentValue.Previous> setter,
        ToDoubleFunction<TankCellEntity2.ComponentValue.Previous> getter2,
        double value) {}

    Stream.of(
            new Entry(
                TankCell2.ComponentValue.Previous::getFraction,
                TankCell2.ComponentValue.Previous::setFraction,
                TankCellEntity2.ComponentValue.Previous::fraction,
                0.12))
        .forEach(entry -> {
          TankCell2 cell1 = new TankCell2(this.tank, this.composition);
          entry.setter()
              .accept(cell1.componentMap().get(ComponentKey.OXYGEN).previous(), entry.value());

          TankCellEntity2 entity = cell1.newEntity();
          assertThat(
              entry.getter2()
                  .applyAsDouble(entity.componentMap().get(ComponentKey.OXYGEN).previous()))
              .isEqualTo(entry.value());

          TankCell2 cell2 = new TankCell2(this.tank, this.composition);
          cell2.accept(entity);
          assertThat(entry.getter()
              .applyAsDouble(
                  cell2.componentMap().get(ComponentKey.OXYGEN).previous()))
              .isEqualTo(entry.value());
        });
  }

}
