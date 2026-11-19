/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doReturn;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
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

  private record Environment(double[] doubleArray) {

    private Environment(Composition composition) {
      this(new double[ColumnCell.size(composition)]);
    }

    private class LocalCell extends ColumnCell {

      private LocalCell(
          int offset,
          ColumnShape shape, double fromX, double toX,
          Composition composition,
          Adsorbent adsorbent, Shell shell) {
        super(offset, shape, fromX, toX, composition, adsorbent, shell);
      }

      @Override
      double[] doubleArray() {
        return Environment.this.doubleArray;
      }

    } // LocalCell

  } // Environment

  @Mock
  private ColumnShape shape;

  @Mock
  private Composition composition;

  @Mock
  private Adsorbent adsorbent;

  @Mock
  private Shell shell;

  /// [ColumnCell] の [GridCell] 関連のテスト.
  @Test
  void testGeometry() {
    final double fromX = 1.;
    final double toX = 3.;
    final double fluidVolume = 5.4;
    final double shellVolume = 4.3;
    final double insideArea = 2.3;
    final double outsideArea = 3.4;

    doReturn(fluidVolume).when(this.shape).fluidVolume(anyDouble(), anyDouble());
    doReturn(shellVolume).when(this.shape).shellVolume(anyDouble(), anyDouble());
    doReturn(insideArea).when(this.shape).insideArea(anyDouble(), anyDouble());
    doReturn(outsideArea).when(this.shape).outsideArea(anyDouble(), anyDouble());

    Set<ComponentKey> componentKeySet = Sets.immutableEnumSet(
        ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    Environment environment = new Environment(this.composition);
    Environment.LocalCell cell = environment.new LocalCell(
        0, this.shape, fromX, toX, this.composition, this.adsorbent, this.shell);

    assertThat(cell.centroid()).isEqualTo(0.5 * (fromX + toX));
    assertThat(cell.length()).isEqualTo(toX - fromX);
    assertThat(cell.fluidVolume()).isEqualTo(fluidVolume);
    assertThat(cell.shellVolume()).isEqualTo(shellVolume);
    assertThat(cell.insideArea()).isEqualTo(insideArea);
    assertThat(cell.outsideArea()).isEqualTo(outsideArea);
  }

  /// [ColumnCell] の [SolverCell] 関連のテスト.
  @Test
  void testSolver() {
    Set<ComponentKey> componentKeySet = Sets.immutableEnumSet(
        ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    Environment environment = new Environment(this.composition);
    Supplier<ColumnCell> supplier = () -> environment.new LocalCell(
        0, this.shape, 0., 1., this.composition, this.adsorbent, this.shell);

    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell::getAp)
        .setter(ColumnCell::setAp)
        .valid(1.2)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell::getAw)
        .setter(ColumnCell::setAw)
        .valid(2.3)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell::getAe)
        .setter(ColumnCell::setAe)
        .valid(3.4)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell::getSp)
        .setter(ColumnCell::setSp)
        .valid(4.5)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell::getSc)
        .setter(ColumnCell::setSc)
        .valid(5.6)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell::getP)
        .setter(ColumnCell::setP)
        .valid(6.7)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell::getQ)
        .setter(ColumnCell::setQ)
        .valid(7.8)
        .test();
  }

  /// [ColumnCell] 及びネストされたクラスの getter/setter の一部をテスト.
  @Test
  void testTemperature() {
    Set<ComponentKey> componentKeySet = Sets.immutableEnumSet(
        ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    Environment environment = new Environment(this.composition);
    Environment.LocalCell cell = environment.new LocalCell(
        0, this.shape, 0., 1., this.composition, this.adsorbent, this.shell);

    final double temperature = 298.15;

    cell.setTemperature(temperature);
    assertThat(cell.getTemperature()).isEqualTo(temperature);

    cell.previous().setTemperature(temperature);
    assertThat(cell.previous().getTemperature()).isEqualTo(temperature);

    final double fraction = 0.12;

    cell.componentMap().get(ComponentKey.OXYGEN).setFraction(fraction);
    assertThat(cell.componentMap().get(ComponentKey.OXYGEN).getFraction()).isEqualTo(fraction);

    cell.componentMap().get(ComponentKey.OXYGEN).previous().setFraction(fraction);
    assertThat(cell.componentMap().get(ComponentKey.OXYGEN).previous().getFraction())
        .isEqualTo(fraction);
  }

  /// [ColumnCell#write(ColumnCellEntity)] のテスト.
  @Test
  void testWriteColumnCellEntity() {
    Set<ComponentKey> componentKeySet = Sets.immutableEnumSet(
        ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    Environment environment = new Environment(this.composition);
    Environment.LocalCell cell = environment.new LocalCell(
        0, this.shape, 0., 1., this.composition, this.adsorbent, this.shell);

    final double temperature = 298.15;
    final double fraction = 0.12;

    cell.setTemperature(temperature);
    cell.setPressure(Double.MIN_VALUE);
    cell.setDensity(Double.MIN_VALUE);
    cell.setSpecificConstantPressureSpecificHeat(Double.MIN_VALUE);
    cell.setViscosity(Double.MIN_VALUE);
    cell.setThermalConductivity(Double.MIN_VALUE);
    cell.setShellTemperature(Double.MIN_VALUE);
    cell.setShellSpecificHeat(Double.MIN_VALUE);
    cell.setShellThermalConductivity(Double.MIN_VALUE);
    cell.setAdsorbentSpecificHeat(Double.MIN_VALUE);
    cell.previous().setTemperature(temperature);
    cell.previous().setDensity(Double.MIN_VALUE);
    cell.previous().setSpecificConstantPressureSpecificHeat(Double.MIN_VALUE);
    cell.previous().setShellTemperature(Double.MIN_VALUE);
    cell.previous().setShellSpecificHeat(Double.MIN_VALUE);
    cell.previous().setAdsorbentSpecificHeat(Double.MIN_VALUE);
    cell.componentMap().values().forEach(componentValue -> {
      componentValue.setFraction(0.);
      componentValue.setDiffusivity(Double.MIN_VALUE);
      componentValue.setMolarQuantity(Double.MIN_VALUE);
      componentValue.setMolarSpecificConstantVolumeSpecificHeat(Double.MIN_VALUE);
      componentValue.previous().setFraction(0.);
      componentValue.previous().setMolarQuantity(Double.MIN_VALUE);
      componentValue.previous().setMolarSpecificConstantVolumeSpecificHeat(Double.MIN_VALUE);
    });
    cell.componentMap().get(ComponentKey.OXYGEN).setFraction(fraction);

    ColumnCellEntity entity = new ColumnCellEntity();
    cell.write(entity);

    assertThat(entity.getTemperature()).isEqualTo(temperature);
    assertThat(entity.getComponentEntityMap().get(ComponentKey.OXYGEN).getFraction())
        .isEqualTo(fraction);
  }

  /// [ColumnCell#read(ColumnCellEntity)] のテスト.
  @Test
  void testReadColumnCellEntity() {
    Set<ComponentKey> componentKeySet = Sets.immutableEnumSet(
        ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    ColumnCellEntity entity = new ColumnCellEntity();
    entity.setComponentEntityMap(
        componentKeySet.stream()
            .collect(
                Collectors.toMap(
                    componentKey -> componentKey,
                    _ -> new ColumnCellEntity.ComponentValue())));

    final double temperature = 298.15;
    final double fraction = 0.12;

    entity.setTemperature(temperature);
    entity.getComponentEntityMap().get(ComponentKey.OXYGEN).setFraction(fraction);

    Environment environment = new Environment(this.composition);
    Environment.LocalCell cell = environment.new LocalCell(
        0, this.shape, 0., 1., this.composition, this.adsorbent, this.shell);

    cell.read(entity);
    assertThat(cell.getTemperature()).isEqualTo(temperature);
    assertThat(cell.componentMap().get(ComponentKey.OXYGEN).getFraction()).isEqualTo(fraction);
  }

}
