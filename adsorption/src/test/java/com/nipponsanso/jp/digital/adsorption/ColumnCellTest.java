/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doReturn;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [ColumnCell] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class ColumnCellTest {

  @SuppressWarnings("ClassCanBeRecord")
  private static final class LocalModel {

    private final double[] doubleArray;

    private LocalModel(Composition composition) {
      this.doubleArray = new double[ColumnCell.number(composition)];
    }

    private final class LocalCell extends ColumnCell {

      private LocalCell(
          ColumnShape shape, double fromX, double toX,
          Composition composition, Shell shell, Adsorbent adsorbent) {
        super(0, shape, fromX, toX, composition, adsorbent, shell);
      }

      @Override
      double[] doubleArray() {
        return LocalModel.this.doubleArray;
      }

    } // LocalCell

  } // LocalModel

  @Mock
  private ColumnShape shape;

  @Mock
  private Composition composition;

  @Mock
  private Shell shell;

  @Mock
  private Adsorbent adsorbent;

  /// [ColumnCell] の getter/setter をテスト.
  @Test
  void testCell() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);

    doReturn(componentKeySet).when(this.composition).componentKeySet();

    final double fluidVolume = 5.4;
    final double shellVolume = 4.3;
    final double insideArea = 2.1;
    final double outsideArea = 1.0;
    final double fromX = 0.2;
    final double toX = 2.8;

    doReturn(fluidVolume).when(this.shape).fluidVolume(anyDouble(), anyDouble());
    doReturn(shellVolume).when(this.shape).shellVolume(anyDouble(), anyDouble());
    doReturn(insideArea).when(this.shape).insideArea(anyDouble(), anyDouble());
    doReturn(outsideArea).when(this.shape).outsideArea(anyDouble(), anyDouble());

    final double shellDensity = 5800.;

    doReturn(shellDensity).when(this.shell).density();

    final double particleDiameter = 2e-3;
    final double voidFraction = 0.4;
    final double bulkDensity = 800.;

    doReturn(particleDiameter).when(this.adsorbent).diameter();
    doReturn(voidFraction).when(this.adsorbent).voidFraction();
    doReturn(bulkDensity).when(this.adsorbent).bulkDensity();

    ColumnCell cell = new LocalModel(this.composition)
        .new LocalCell(this.shape, fromX, toX, this.composition, this.shell, this.adsorbent);

    assertThatThrownBy(() -> cell.setTemperature(0.)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> cell.setPressure(0.)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> cell.setDensity(0.)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> cell.setSpecificConstantPressureSpecificHeat(0.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> cell.setViscosity(0.)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> cell.setThermalConductivity(0.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> cell.setShellTemperature(0.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> cell.setShellSpecificHeat(0.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> cell.setShellThermalConductivity(0.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> cell.setAdsorbentSpecificHeat(0.))
        .isInstanceOf(IllegalArgumentException.class);

    final double temperature = 298.15;
    final double pressure = 101325.;
    final double density = 1.2;
    final double specificEnthalpy = 1e6;
    final double specificConstantPressureSpecificHeat = 1000.;
    final double viscosity = 18e-6;
    final double thermalConductivity = 0.02;
    final double molecularWeight = 29.;
    final double shellTemperature = 303.15;
    final double shellSpecificHeat = 640.;
    final double shellThermalConductivity = 0.2;
    final double adsorbentSpecificHeat = 800.;
    final double ap = 1.2;
    final double aw = 2.3;
    final double ae = 3.4;
    final double sp = 4.5;
    final double sc = 5.6;
    final double p = 2.1;
    final double q = 3.2;

    cell.setTemperature(temperature);
    cell.setPressure(pressure);
    cell.setDensity(density);
    cell.setSpecificEnthalpy(specificEnthalpy);
    cell.setSpecificConstantPressureSpecificHeat(specificConstantPressureSpecificHeat);
    cell.setViscosity(viscosity);
    cell.setThermalConductivity(thermalConductivity);
    cell.setMolecularWeight(molecularWeight);
    cell.setShellTemperature(shellTemperature);
    cell.setShellSpecificHeat(shellSpecificHeat);
    cell.setShellThermalConductivity(shellThermalConductivity);
    cell.setAdsorbentSpecificHeat(adsorbentSpecificHeat);
    cell.setAp(ap);
    cell.setAw(aw);
    cell.setAe(ae);
    cell.setSp(sp);
    cell.setSc(sc);
    cell.setP(p);
    cell.setQ(q);

    assertThat(cell)
        .returns(.5 * (fromX + toX), ColumnCell::centroid)
        .returns(toX - fromX, ColumnCell::length)
        .returns(fluidVolume, ColumnCell::fluidVolume)
        .returns(shellVolume, ColumnCell::shellVolume)
        .returns(insideArea, ColumnCell::insideArea)
        .returns(outsideArea, ColumnCell::outsideArea)
        .returns(temperature, ColumnCell::getTemperature)
        .returns(pressure, ColumnCell::getPressure)
        .returns(density, ColumnCell::getDensity)
        .returns(specificEnthalpy, ColumnCell::getSpecificEnthalpy)
        .returns(
            specificConstantPressureSpecificHeat,
            ColumnCell::getSpecificConstantPressureSpecificHeat)
        .returns(viscosity, ColumnCell::getViscosity)
        .returns(thermalConductivity, ColumnCell::getThermalConductivity)
        .returns(molecularWeight, ColumnCell::getMolecularWeight)
        .returns(shellDensity, ColumnCell::shellDensity)
        .returns(shellTemperature, ColumnCell::getShellTemperature)
        .returns(shellSpecificHeat, ColumnCell::getShellSpecificHeat)
        .returns(shellThermalConductivity, ColumnCell::getShellThermalConductivity)
        .returns(particleDiameter, ColumnCell::particleDiameter)
        .returns(voidFraction, ColumnCell::voidFraction)
        .returns(bulkDensity, ColumnCell::bulkDensity)
        .returns(adsorbentSpecificHeat, ColumnCell::getAdsorbentSpecificHeat)
        .returns(ap, ColumnCell::getAp)
        .returns(aw, ColumnCell::getAw)
        .returns(ae, ColumnCell::getAe)
        .returns(sp, ColumnCell::getSp)
        .returns(sc, ColumnCell::getSc)
        .returns(p, ColumnCell::getP)
        .returns(q, ColumnCell::getQ);
  }

  /// [ColumnCell] の永続化をテスト.
  @Test
  void testCellEntity() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);

    doReturn(componentKeySet).when(this.composition).componentKeySet();

    ColumnCell srcCell = new LocalModel(this.composition)
        .new LocalCell(this.shape, 0., 1., this.composition, this.shell, this.adsorbent);

    final double temperature = 298.15;
    final double pressure = 101325.;
    final double density = 1.2;
    final double specificEnthalpy = 1e6;
    final double specificConstantPressureSpecificHeat = 1000.;
    final double viscosity = 18e-6;
    final double thermalConductivity = 0.02;
    final double shellTemperature = 303.15;
    final double shellSpecificHeat = 640.;
    final double shellThermalConductivity = 0.2;
    final double adsorbentSpecificHeat = 800.;

    srcCell.setTemperature(temperature);
    srcCell.setPressure(pressure);
    srcCell.setDensity(density);
    srcCell.setSpecificEnthalpy(specificEnthalpy);
    srcCell.setSpecificConstantPressureSpecificHeat(specificConstantPressureSpecificHeat);
    srcCell.setViscosity(viscosity);
    srcCell.setThermalConductivity(thermalConductivity);
    srcCell.setShellTemperature(shellTemperature);
    srcCell.setShellSpecificHeat(shellSpecificHeat);
    srcCell.setShellThermalConductivity(shellThermalConductivity);
    srcCell.setAdsorbentSpecificHeat(adsorbentSpecificHeat);

    ColumnCellEntity entity = srcCell.newEntity();

    assertThat(entity)
        .returns(componentKeySet, o -> o.componentMap().keySet())
        .returns(temperature, ColumnCellEntity::temperature)
        .returns(pressure, ColumnCellEntity::pressure)
        .returns(density, ColumnCellEntity::density)
        .returns(specificEnthalpy, ColumnCellEntity::specificEnthalpy)
        .returns(specificConstantPressureSpecificHeat, ColumnCellEntity::specificConstantPressureSpecificHeat)
        .returns(viscosity, ColumnCellEntity::viscosity)
        .returns(thermalConductivity, ColumnCellEntity::thermalConductivity)
        .returns(shellTemperature, ColumnCellEntity::shellTemperature)
        .returns(shellSpecificHeat, ColumnCellEntity::shellSpecificHeat)
        .returns(shellThermalConductivity, ColumnCellEntity::shellThermalConductivity)
        .returns(adsorbentSpecificHeat, ColumnCellEntity::adsorbentSpecificHeat);

    ColumnCell dstCell = new LocalModel(this.composition)
        .new LocalCell(this.shape, 0., 1., this.composition, this.shell, this.adsorbent);

    dstCell.accept(entity);

    assertThat(dstCell)
        .returns(temperature, ColumnCell::getTemperature)
        .returns(pressure, ColumnCell::getPressure)
        .returns(density, ColumnCell::getDensity)
        .returns(specificEnthalpy, ColumnCell::getSpecificEnthalpy)
        .returns(
            specificConstantPressureSpecificHeat,
            ColumnCell::getSpecificConstantPressureSpecificHeat)
        .returns(viscosity, ColumnCell::getViscosity)
        .returns(thermalConductivity, ColumnCell::getThermalConductivity)
        .returns(shellTemperature, ColumnCell::getShellTemperature)
        .returns(shellSpecificHeat, ColumnCell::getShellSpecificHeat)
        .returns(shellThermalConductivity, ColumnCell::getShellThermalConductivity)
        .returns(adsorbentSpecificHeat, ColumnCell::getAdsorbentSpecificHeat);
  }

  /// [ColumnCell.Previous] のテスト.
  @Test
  void testCellPrevious() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);

    doReturn(componentKeySet).when(this.composition).componentKeySet();

    ColumnCell.Previous previous = new LocalModel(this.composition)
        .new LocalCell(this.shape, 0., 1., this.composition, this.shell, this.adsorbent)
        .previous();

    assertThatThrownBy(() -> previous.setTemperature(0.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> previous.setDensity(0.)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> previous.setSpecificConstantPressureSpecificHeat(0.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> previous.setShellTemperature(0.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> previous.setShellSpecificHeat(0.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> previous.setAdsorbentSpecificHeat(0.))
        .isInstanceOf(IllegalArgumentException.class);

    final double temperature = 298.15;
    final double density = 1.2;
    final double specificEnthalpy = 1e6;
    final double specificConstantPressureSpecificHeat = 1000.;
    final double shellTemperature = 303.15;
    final double shellSpecificHeat = 640.;
    final double adsorbentSpecificHeat = 800.;

    previous.setTemperature(temperature);
    previous.setDensity(density);
    previous.setSpecificEnthalpy(specificEnthalpy);
    previous.setSpecificConstantPressureSpecificHeat(specificConstantPressureSpecificHeat);
    previous.setShellTemperature(shellTemperature);
    previous.setShellSpecificHeat(shellSpecificHeat);
    previous.setAdsorbentSpecificHeat(adsorbentSpecificHeat);

    assertThat(previous)
        .returns(temperature, ColumnCell.Previous::getTemperature)
        .returns(density, ColumnCell.Previous::getDensity)
        .returns(specificEnthalpy, ColumnCell.Previous::getSpecificEnthalpy)
        .returns(
            specificConstantPressureSpecificHeat,
            ColumnCell.Previous::getSpecificConstantPressureSpecificHeat)
        .returns(shellTemperature, ColumnCell.Previous::getShellTemperature)
        .returns(shellSpecificHeat, ColumnCell.Previous::getShellSpecificHeat)
        .returns(adsorbentSpecificHeat, ColumnCell.Previous::getAdsorbentSpecificHeat);
  }

  /// [ColumnCell.Previous] の永続化をテスト.
  @Test
  void testCellPreviousEntity() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    ColumnCell srcCell = new LocalModel(this.composition)
        .new LocalCell(this.shape, 0., 1., this.composition, this.shell, this.adsorbent);
    ColumnCell.Previous previous = srcCell.previous();

    final double temperature = 298.15;
    final double density = 1.2;
    final double specificEnthalpy = 1e6;
    final double specificConstantPressureSpecificHeat = 1000.;
    final double shellTemperature = 303.15;
    final double shellSpecificHeat = 640.;
    final double adsorbentSpecificHeat = 800.;

    previous.setTemperature(temperature);
    previous.setDensity(density);
    previous.setSpecificEnthalpy(specificEnthalpy);
    previous.setSpecificConstantPressureSpecificHeat(specificConstantPressureSpecificHeat);
    previous.setShellTemperature(shellTemperature);
    previous.setShellSpecificHeat(shellSpecificHeat);
    previous.setAdsorbentSpecificHeat(adsorbentSpecificHeat);

    ColumnCellEntity entity = srcCell.newEntity();

    assertThat(entity.previous())
        .returns(temperature, ColumnCellEntity.Previous::temperature)
        .returns(density, ColumnCellEntity.Previous::density)
        .returns(specificEnthalpy, ColumnCellEntity.Previous::specificEnthalpy)
        .returns(
            specificConstantPressureSpecificHeat,
            ColumnCellEntity.Previous::specificConstantPressureSpecificHeat)
        .returns(shellTemperature, ColumnCellEntity.Previous::shellTemperature)
        .returns(shellSpecificHeat, ColumnCellEntity.Previous::shellSpecificHeat)
        .returns(adsorbentSpecificHeat, ColumnCellEntity.Previous::adsorbentSpecificHeat);

    ColumnCell dstCell = new LocalModel(this.composition)
        .new LocalCell(this.shape, 0., 1., this.composition, this.shell, this.adsorbent);
    dstCell.accept(entity);

    assertThat(dstCell.previous())
        .returns(temperature, ColumnCell.Previous::getTemperature)
        .returns(density, ColumnCell.Previous::getDensity)
        .returns(specificEnthalpy, ColumnCell.Previous::getSpecificEnthalpy)
        .returns(
            specificConstantPressureSpecificHeat,
            ColumnCell.Previous::getSpecificConstantPressureSpecificHeat)
        .returns(shellTemperature, ColumnCell.Previous::getShellTemperature)
        .returns(shellSpecificHeat, ColumnCell.Previous::getShellSpecificHeat)
        .returns(adsorbentSpecificHeat, ColumnCell.Previous::getAdsorbentSpecificHeat);
  }

  /// [ColumnCell.ComponentValue] の getter 及び setter をテスト.
  @Test
  void testCellComponentValue() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    ColumnCell.ComponentValue componentValue = new LocalModel(this.composition)
        .new LocalCell(this.shape, 0., 1., this.composition, this.shell, this.adsorbent)
        .componentMap().get(ComponentKey.OXYGEN);

    assertThatThrownBy(() -> componentValue.setFraction(-1.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> componentValue.setFraction(2.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> componentValue.setDiffusivity(0.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> componentValue.setMolarQuantity(0.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> componentValue.setMolarSpecificConstantVolumeSpecificHeat(0.))
        .isInstanceOf(IllegalArgumentException.class);

    final double fraction = 0.123;
    final double diffusivity = 5e-4;
    final double molarFraction = 0.234;
    final double viscosity = 18e-6;
    final double molarQuantity = 1.234;
    final double molarSpecificConstantVolumeSpecificHeat = 12.;

    componentValue.setFraction(fraction);
    componentValue.setDiffusivity(diffusivity);
    componentValue.setMolarFraction(molarFraction);
    componentValue.setViscosity(viscosity);
    componentValue.setMolarQuantity(molarQuantity);
    componentValue.setMolarSpecificConstantVolumeSpecificHeat(
        molarSpecificConstantVolumeSpecificHeat);

    assertThat(componentValue)
        .returns(fraction, ColumnCell.ComponentValue::getFraction)
        .returns(diffusivity, ColumnCell.ComponentValue::getDiffusivity)
        .returns(molarFraction, ColumnCell.ComponentValue::getMolarFraction)
        .returns(viscosity, ColumnCell.ComponentValue::getViscosity)
        .returns(molarQuantity, ColumnCell.ComponentValue::getMolarQuantity)
        .returns(
            molarSpecificConstantVolumeSpecificHeat,
            ColumnCell.ComponentValue::getMolarSpecificConstantVolumeSpecificHeat);
  }

  /// [ColumnCell.ComponentValue] の永続化をテスト.
  @Test
  void testCellComponentValueEntity() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    ColumnCell srcCell = new LocalModel(this.composition)
        .new LocalCell(this.shape, 0., 1., this.composition, this.shell, this.adsorbent);
    ColumnCell.ComponentValue srcComponentValue = srcCell.componentMap().get(ComponentKey.OXYGEN);

    final double fraction = 0.123;
    final double diffusivity = 5e-4;
    final double molarQuantity = 1.234;
    final double molarSpecificConstantVolumeSpecificHeat = 12.;

    srcComponentValue.setFraction(fraction);
    srcComponentValue.setDiffusivity(diffusivity);
    srcComponentValue.setMolarQuantity(molarQuantity);
    srcComponentValue.setMolarSpecificConstantVolumeSpecificHeat(
        molarSpecificConstantVolumeSpecificHeat);

    ColumnCellEntity entity = srcCell.newEntity();

    assertThat(entity.componentMap().get(ComponentKey.OXYGEN))
        .returns(fraction, ColumnCellEntity.ComponentValue::fraction)
        .returns(diffusivity, ColumnCellEntity.ComponentValue::diffusivity)
        .returns(molarQuantity, ColumnCellEntity.ComponentValue::molarQuantity)
        .returns(
            molarSpecificConstantVolumeSpecificHeat,
            ColumnCellEntity.ComponentValue::molarSpecificConstantVolumeSpecificHeat);

    ColumnCell dstCell = new LocalModel(this.composition)
        .new LocalCell(this.shape, 0., 1., this.composition, this.shell, this.adsorbent);
    dstCell.accept(entity);

    assertThat(dstCell.componentMap().get(ComponentKey.OXYGEN))
        .returns(fraction, ColumnCell.ComponentValue::getFraction)
        .returns(diffusivity, ColumnCell.ComponentValue::getDiffusivity)
        .returns(molarQuantity, ColumnCell.ComponentValue::getMolarQuantity)
        .returns(
            molarSpecificConstantVolumeSpecificHeat,
            ColumnCell.ComponentValue::getMolarSpecificConstantVolumeSpecificHeat);
  }

  /// [ColumnCell.ComponentValue.Previous] の getter 及び setter をテスト.
  @Test
  void testCellComponentValuePrevious() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    ColumnCell.ComponentValue.Previous previous = new LocalModel(this.composition)
        .new LocalCell(this.shape, 0., 1., this.composition, this.shell, this.adsorbent)
        .componentMap().get(ComponentKey.OXYGEN).previous();

    assertThatThrownBy(() -> previous.setFraction(-1.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> previous.setFraction(2.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> previous.setMolarQuantity(0.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> previous.setMolarSpecificConstantVolumeSpecificHeat(0.))
        .isInstanceOf(IllegalArgumentException.class);

    final double fraction = 0.123;
    final double molarQuantity = 1.234;
    final double molarSpecificConstantVolumeSpecificHeat = 12.;

    previous.setFraction(fraction);
    previous.setMolarQuantity(molarQuantity);
    previous.setMolarSpecificConstantVolumeSpecificHeat(
        molarSpecificConstantVolumeSpecificHeat);

    assertThat(previous)
        .returns(fraction, ColumnCell.ComponentValue.Previous::getFraction)
        .returns(molarQuantity, ColumnCell.ComponentValue.Previous::getMolarQuantity)
        .returns(
            molarSpecificConstantVolumeSpecificHeat,
            ColumnCell.ComponentValue.Previous::getMolarSpecificConstantVolumeSpecificHeat);
  }

  /// [ColumnCell.ComponentValue.Previous] の永続化をテスト.
  @Test
  void testCellComponentValuePreviousEntity() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    ColumnCell srcCell = new LocalModel(this.composition)
        .new LocalCell(this.shape, 0., 1., this.composition, this.shell, this.adsorbent);
    ColumnCell.ComponentValue.Previous srcPrevious
        = srcCell.componentMap().get(ComponentKey.OXYGEN).previous();

    final double fraction = 0.123;
    final double molarQuantity = 1.234;
    final double molarSpecificConstantVolumeSpecificHeat = 12.;

    srcPrevious.setFraction(fraction);
    srcPrevious.setMolarQuantity(molarQuantity);
    srcPrevious.setMolarSpecificConstantVolumeSpecificHeat(
        molarSpecificConstantVolumeSpecificHeat);

    ColumnCellEntity entity = srcCell.newEntity();

    assertThat(entity.componentMap().get(ComponentKey.OXYGEN).previous())
        .returns(fraction, ColumnCellEntity.ComponentValue.Previous::fraction)
        .returns(molarQuantity, ColumnCellEntity.ComponentValue.Previous::molarQuantity)
        .returns(
            molarSpecificConstantVolumeSpecificHeat,
            ColumnCellEntity.ComponentValue.Previous::molarSpecificConstantVolumeSpecificHeat);

    ColumnCell dstCell = new LocalModel(this.composition)
        .new LocalCell(this.shape, 0., 1., this.composition, this.shell, this.adsorbent);
    dstCell.accept(entity);

    assertThat(dstCell.componentMap().get(ComponentKey.OXYGEN).previous())
        .returns(fraction, ColumnCell.ComponentValue.Previous::getFraction)
        .returns(molarQuantity, ColumnCell.ComponentValue.Previous::getMolarQuantity)
        .returns(
            molarSpecificConstantVolumeSpecificHeat,
            ColumnCell.ComponentValue.Previous::getMolarSpecificConstantVolumeSpecificHeat);
  }

}
