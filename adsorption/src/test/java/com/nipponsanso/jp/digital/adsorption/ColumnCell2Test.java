/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doReturn;

import com.nipponsanso.jp.digital.adsorption.ColumnCell2.ComponentValue;
import java.util.Set;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [ColumnCell2] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class ColumnCell2Test {

  @SuppressWarnings("ClassCanBeRecord")
  private static final class LocalModel {

    private final double[] doubleArray;

    private LocalModel(Composition composition) {
      this.doubleArray = new double[ColumnCell2.number(composition)];
    }

    private final class LocalCell extends ColumnCell2 {

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

  /// [ColumnCell2] のデータ階層をテスト.
  @Test
  void testStructure() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    LocalModel model = new LocalModel(this.composition);
    ColumnCell2 cell = model.new LocalCell(
        this.shape, 0., 1., this.composition, this.shell, this.adsorbent);

    assertThat(cell.previous()).isInstanceOf(ColumnCell2.Previous.class);
    assertThat(cell.componentMap().keySet()).isEqualTo(componentKeySet);
    assertThat(cell.componentMap().get(ComponentKey.OXYGEN).previous())
        .isInstanceOf(ColumnCell2.ComponentValue.Previous.class);
  }

  /// [ColumnCell2] の幾何情報をテスト.
  @Test
  void testGeometry() {
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

    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    LocalModel model = new LocalModel(this.composition);
    ColumnCell2 cell = model.new LocalCell(
        this.shape, fromX, toX, this.composition, this.shell, this.adsorbent);

    assertThat(cell.centroid()).isEqualTo(.5 * (fromX + toX));
    assertThat(cell.length()).isEqualTo(toX - fromX);
    assertThat(cell.fluidVolume()).isEqualTo(fluidVolume);
    assertThat(cell.shellVolume()).isEqualTo(shellVolume);
    assertThat(cell.insideArea()).isEqualTo(insideArea);
    assertThat(cell.outsideArea()).isEqualTo(outsideArea);
  }

  /// [ColumnCell2] の [Shell] 由来メソッドをテスト.
  @Test
  void testShell() {
    final double shellDensity = 5800.;
    doReturn(shellDensity).when(this.shell).density();

    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    LocalModel model = new LocalModel(this.composition);
    ColumnCell2 cell = model.new LocalCell(
        this.shape, 0., 1., this.composition, this.shell, this.adsorbent);

    assertThat(cell.shellDensity()).isEqualTo(shellDensity);
  }

  /// [ColumnCell2] の [Adsorbent] 由来メソッドをテスト.
  @Test
  void testAdsorbent() {
    final double particleDiameter = 2e-3;
    final double voidFraction = 0.4;
    final double bulkDensity = 800.;

    doReturn(particleDiameter).when(this.adsorbent).diameter();
    doReturn(voidFraction).when(this.adsorbent).voidFraction();
    doReturn(bulkDensity).when(this.adsorbent).bulkDensity();

    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    LocalModel model = new LocalModel(this.composition);
    ColumnCell2 cell = model.new LocalCell(
        this.shape, 0., 1., this.composition, this.shell, this.adsorbent);

    assertThat(cell.particleDiameter()).isEqualTo(particleDiameter);
    assertThat(cell.voidFraction()).isEqualTo(voidFraction);
    assertThat(cell.bulkDensity()).isEqualTo(bulkDensity);
  }

  /// [ColumnCell2] の getter 及び setter をテスト.
  @Test
  void testGetterAndSetter() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    Supplier<ColumnCell2> supplier = () -> {
      LocalModel model = new LocalModel(this.composition);
      return model.new LocalCell(
          this.shape, 0., 1., this.composition, this.shell, this.adsorbent);
    };

    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2::getTemperature)
        .setter(ColumnCell2::setTemperature)
        .valid(298.15)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2::getPressure)
        .setter(ColumnCell2::setPressure)
        .valid(101325.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2::getDensity)
        .setter(ColumnCell2::setDensity)
        .valid(1.2)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2::getSpecificEnthalpy)
        .setter(ColumnCell2::setSpecificEnthalpy)
        .valid(1e6)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2::getSpecificConstantPressureSpecificHeat)
        .setter(ColumnCell2::setSpecificConstantPressureSpecificHeat)
        .valid(1e3)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2::getViscosity)
        .setter(ColumnCell2::setViscosity)
        .valid(18e-6)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2::getThermalConductivity)
        .setter(ColumnCell2::setThermalConductivity)
        .valid(0.02)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2::getMolecularWeight)
        .setter(ColumnCell2::setMolecularWeight)
        .valid(29.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2::getShellTemperature)
        .setter(ColumnCell2::setShellTemperature)
        .valid(303.15)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2::getShellSpecificHeat)
        .setter(ColumnCell2::setShellSpecificHeat)
        .valid(640.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2::getShellThermalConductivity)
        .setter(ColumnCell2::setShellThermalConductivity)
        .valid(0.2)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2::getAdsorbentSpecificHeat)
        .setter(ColumnCell2::setAdsorbentSpecificHeat)
        .valid(800.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2::getAp)
        .setter(ColumnCell2::setAp)
        .valid(1.2)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2::getAw)
        .setter(ColumnCell2::setAw)
        .valid(2.3)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2::getAe)
        .setter(ColumnCell2::setAe)
        .valid(3.4)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2::getSp)
        .setter(ColumnCell2::setSp)
        .valid(4.5)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2::getSc)
        .setter(ColumnCell2::setSc)
        .valid(5.6)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2::getP)
        .setter(ColumnCell2::setP)
        .valid(2.1)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2::getQ)
        .setter(ColumnCell2::setQ)
        .valid(3.2)
        .test();
  }

  /// [ColumnCell2] の永続化をテスト.
  @Test
  void testEntity() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    Supplier<ColumnCell2> supplier = () -> {
      LocalModel model = new LocalModel(this.composition);
      return model.new LocalCell(
          this.shape, 0., 1., this.composition, this.shell, this.adsorbent);
    };

    record Entry(
        ToDoubleFunction<ColumnCell2> getter,
        ObjDoubleConsumer<ColumnCell2> setter,
        ToDoubleFunction<ColumnCellEntity2> entityGetter,
        double value) {}

    Stream.of(
            new Entry(
                ColumnCell2::getTemperature,
                ColumnCell2::setTemperature,
                ColumnCellEntity2::temperature,
                298.15),
            new Entry(
                ColumnCell2::getPressure,
                ColumnCell2::setPressure,
                ColumnCellEntity2::pressure,
                101325.),
            new Entry(
                ColumnCell2::getDensity,
                ColumnCell2::setDensity,
                ColumnCellEntity2::density,
                1.2),
            new Entry(
                ColumnCell2::getSpecificEnthalpy,
                ColumnCell2::setSpecificEnthalpy,
                ColumnCellEntity2::specificEnthalpy,
                1000000.),
            new Entry(
                ColumnCell2::getSpecificConstantPressureSpecificHeat,
                ColumnCell2::setSpecificConstantPressureSpecificHeat,
                ColumnCellEntity2::specificConstantPressureSpecificHeat,
                1000.),
            new Entry(
                ColumnCell2::getViscosity,
                ColumnCell2::setViscosity,
                ColumnCellEntity2::viscosity,
                18e-6),
            new Entry(
                ColumnCell2::getThermalConductivity,
                ColumnCell2::setThermalConductivity,
                ColumnCellEntity2::thermalConductivity,
                0.02),
            new Entry(
                ColumnCell2::getShellTemperature,
                ColumnCell2::setShellTemperature,
                ColumnCellEntity2::shellTemperature,
                303.15),
            new Entry(
                ColumnCell2::getShellSpecificHeat,
                ColumnCell2::setShellSpecificHeat,
                ColumnCellEntity2::shellSpecificHeat,
                640.),
            new Entry(
                ColumnCell2::getShellThermalConductivity,
                ColumnCell2::setShellThermalConductivity,
                ColumnCellEntity2::shellThermalConductivity,
                0.2),
            new Entry(
                ColumnCell2::getAdsorbentSpecificHeat,
                ColumnCell2::setAdsorbentSpecificHeat,
                ColumnCellEntity2::adsorbentSpecificHeat,
                800.))
        .forEach(entry -> {
          ColumnCell2 cell1 = supplier.get();
          entry.setter().accept(cell1, entry.value());

          ColumnCellEntity2 entity = cell1.newEntity();
          assertThat(entry.entityGetter().applyAsDouble(entity)).isEqualTo(entry.value());

          ColumnCell2 cell2 = supplier.get();
          cell2.accept(entity);
          assertThat(entry.getter().applyAsDouble(cell2)).isEqualTo(entry.value());
        });
  }

  /// [ColumnCell2.Previous] の getter 及び setter をテスト.
  @Test
  void testPreviousGetterAndSetter() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    Supplier<ColumnCell2.Previous> supplier = () -> {
      LocalModel model = new LocalModel(this.composition);
      return model.new LocalCell(
          this.shape, 0., 1., this.composition, this.shell, this.adsorbent)
          .previous();
    };

    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2.Previous::getTemperature)
        .setter(ColumnCell2.Previous::setTemperature)
        .valid(298.15)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2.Previous::getDensity)
        .setter(ColumnCell2.Previous::setDensity)
        .valid(1.2)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2.Previous::getSpecificEnthalpy)
        .setter(ColumnCell2.Previous::setSpecificEnthalpy)
        .valid(1e6)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2.Previous::getSpecificConstantPressureSpecificHeat)
        .setter(ColumnCell2.Previous::setSpecificConstantPressureSpecificHeat)
        .valid(1e3)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2.Previous::getShellTemperature)
        .setter(ColumnCell2.Previous::setShellTemperature)
        .valid(303.15)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2.Previous::getShellSpecificHeat)
        .setter(ColumnCell2.Previous::setShellSpecificHeat)
        .valid(640.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2.Previous::getAdsorbentSpecificHeat)
        .setter(ColumnCell2.Previous::setAdsorbentSpecificHeat)
        .valid(800.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
  }

  /// [ColumnCell2.Previous] の永続化をテスト.
  @Test
  void testPreviousEntity() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    Supplier<ColumnCell2> supplier = () -> {
      LocalModel model = new LocalModel(this.composition);
      return model.new LocalCell(
          this.shape, 0., 1., this.composition, this.shell, this.adsorbent);
    };

    record Entry(
        ToDoubleFunction<ColumnCell2.Previous> getter,
        ObjDoubleConsumer<ColumnCell2.Previous> setter,
        ToDoubleFunction<ColumnCellEntity2.Previous> entityGetter,
        double value) {}

    Stream.of(
            new Entry(
                ColumnCell2.Previous::getTemperature,
                ColumnCell2.Previous::setTemperature,
                ColumnCellEntity2.Previous::temperature,
                298.15),
            new Entry(
                ColumnCell2.Previous::getDensity,
                ColumnCell2.Previous::setDensity,
                ColumnCellEntity2.Previous::density,
                1.2),
            new Entry(
                ColumnCell2.Previous::getSpecificEnthalpy,
                ColumnCell2.Previous::setSpecificEnthalpy,
                ColumnCellEntity2.Previous::specificEnthalpy,
                1000000.),
            new Entry(
                ColumnCell2.Previous::getSpecificConstantPressureSpecificHeat,
                ColumnCell2.Previous::setSpecificConstantPressureSpecificHeat,
                ColumnCellEntity2.Previous::specificConstantPressureSpecificHeat,
                1000.),
            new Entry(
                ColumnCell2.Previous::getShellTemperature,
                ColumnCell2.Previous::setShellTemperature,
                ColumnCellEntity2.Previous::shellTemperature,
                303.15),
            new Entry(
                ColumnCell2.Previous::getShellSpecificHeat,
                ColumnCell2.Previous::setShellSpecificHeat,
                ColumnCellEntity2.Previous::shellSpecificHeat,
                640.),
            new Entry(
                ColumnCell2.Previous::getAdsorbentSpecificHeat,
                ColumnCell2.Previous::setAdsorbentSpecificHeat,
                ColumnCellEntity2.Previous::adsorbentSpecificHeat,
                800.))
        .forEach(entry -> {
          ColumnCell2 cell1 = supplier.get();
          entry.setter().accept(cell1.previous(), entry.value());

          ColumnCellEntity2 entity = cell1.newEntity();
          assertThat(entry.entityGetter().applyAsDouble(entity.previous()))
              .isEqualTo(entry.value());

          ColumnCell2 cell2 = supplier.get();
          cell2.accept(entity);
          assertThat(entry.getter().applyAsDouble(cell2.previous())).isEqualTo(entry.value());
        });
  }

  /// [ColumnCell2.ComponentValue] の getter 及び setter をテスト.
  @Test
  void testComponentValueGetterAndSetter() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    Supplier<ColumnCell2.ComponentValue> supplier = () -> {
      LocalModel model = new LocalModel(this.composition);
      return model.new LocalCell(
          this.shape, 0., 1., this.composition, this.shell, this.adsorbent)
          .componentMap().get(ComponentKey.OXYGEN);
    };

    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2.ComponentValue::getFraction)
        .setter(ColumnCell2.ComponentValue::setFraction)
        .valid(0.123)
        .thrown(IllegalArgumentException.class).value(-1.)
        .thrown(IllegalArgumentException.class).value(2.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ComponentValue::getDiffusivity)
        .setter(ComponentValue::setDiffusivity)
        .valid(5e-4)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2.ComponentValue::getMolarFraction)
        .setter(ColumnCell2.ComponentValue::setMolarFraction)
        .valid(0.23)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2.ComponentValue::getViscosity)
        .setter(ColumnCell2.ComponentValue::setViscosity)
        .valid(18e-6)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2.ComponentValue::getMolarQuantity)
        .setter(ColumnCell2.ComponentValue::setMolarQuantity)
        .valid(1.2)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2.ComponentValue::getMolarSpecificConstantVolumeSpecificHeat)
        .setter(ColumnCell2.ComponentValue::setMolarSpecificConstantVolumeSpecificHeat)
        .valid(12.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
  }

  /// [ColumnCell2.ComponentValue] の永続化をテスト.
  @Test
  void testComponentValueEntity() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    Supplier<ColumnCell2> supplier = () -> {
      LocalModel model = new LocalModel(this.composition);
      return model.new LocalCell(
          this.shape, 0., 1., this.composition, this.shell, this.adsorbent);
    };

    record Entry(
        ToDoubleFunction<ColumnCell2.ComponentValue> getter,
        ObjDoubleConsumer<ColumnCell2.ComponentValue> setter,
        ToDoubleFunction<ColumnCellEntity2.ComponentValue> entityGetter,
        double value) {}

    Stream.of(
            new Entry(
                ColumnCell2.ComponentValue::getFraction,
                ColumnCell2.ComponentValue::setFraction,
                ColumnCellEntity2.ComponentValue::fraction,
                0.12),
            new Entry(
                ColumnCell2.ComponentValue::getDiffusivity,
                ColumnCell2.ComponentValue::setDiffusivity,
                ColumnCellEntity2.ComponentValue::diffusivity,
                5e-4),
            new Entry(
                ColumnCell2.ComponentValue::getMolarQuantity,
                ColumnCell2.ComponentValue::setMolarQuantity,
                ColumnCellEntity2.ComponentValue::molarQuantity,
                100.),
            new Entry(
                ColumnCell2.ComponentValue::getMolarSpecificConstantVolumeSpecificHeat,
                ColumnCell2.ComponentValue::setMolarSpecificConstantVolumeSpecificHeat,
                ColumnCellEntity2.ComponentValue::molarSpecificConstantVolumeSpecificHeat,
                12.))
        .forEach(entry -> {
          ColumnCell2 cell1 = supplier.get();
          entry.setter().accept(cell1.componentMap().get(ComponentKey.OXYGEN), entry.value());

          ColumnCellEntity2 entity = cell1.newEntity();
          assertThat(
              entry.entityGetter().applyAsDouble(entity.componentMap().get(ComponentKey.OXYGEN)))
              .isEqualTo(entry.value());

          ColumnCell2 cell2 = supplier.get();
          cell2.accept(entity);
          assertThat(entry.getter().applyAsDouble(cell2.componentMap().get(ComponentKey.OXYGEN)))
              .isEqualTo(entry.value());
        });
  }

  /// [ColumnCell2.ComponentValue.Previous] の getter 及び setter をテスト.
  @Test
  void testComponentValuePreviousGetterAndSetter() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    Supplier<ColumnCell2.ComponentValue.Previous> supplier = () -> {
      LocalModel model = new LocalModel(this.composition);
      return model.new LocalCell(
          this.shape, 0., 1., this.composition, this.shell, this.adsorbent)
          .componentMap().get(ComponentKey.OXYGEN).previous();
    };

    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2.ComponentValue.Previous::getFraction)
        .setter(ColumnCell2.ComponentValue.Previous::setFraction)
        .valid(0.123)
        .thrown(IllegalArgumentException.class).value(-1.)
        .thrown(IllegalArgumentException.class).value(2.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2.ComponentValue.Previous::getMolarQuantity)
        .setter(ColumnCell2.ComponentValue.Previous::setMolarQuantity)
        .valid(1.2)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell2.ComponentValue.Previous::getMolarSpecificConstantVolumeSpecificHeat)
        .setter(ColumnCell2.ComponentValue.Previous::setMolarSpecificConstantVolumeSpecificHeat)
        .valid(12.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
  }

  /// [ColumnCell2.ComponentValue.Previous] の永続化をテスト.
  @Test
  void testComponentValuePreviousEntity() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    Supplier<ColumnCell2> supplier = () -> {
      LocalModel model = new LocalModel(this.composition);
      return model.new LocalCell(
          this.shape, 0., 1., this.composition, this.shell, this.adsorbent);
    };

    record Entry(
        ToDoubleFunction<ColumnCell2.ComponentValue.Previous> getter,
        ObjDoubleConsumer<ColumnCell2.ComponentValue.Previous> setter,
        ToDoubleFunction<ColumnCellEntity2.ComponentValue.Previous> entityGetter,
        double value) {}

    Stream.of(
            new Entry(
                ColumnCell2.ComponentValue.Previous::getFraction,
                ColumnCell2.ComponentValue.Previous::setFraction,
                ColumnCellEntity2.ComponentValue.Previous::fraction,
                0.12),
            new Entry(
                ColumnCell2.ComponentValue.Previous::getMolarQuantity,
                ColumnCell2.ComponentValue.Previous::setMolarQuantity,
                ColumnCellEntity2.ComponentValue.Previous::molarQuantity,
                100.),
            new Entry(
                ColumnCell2.ComponentValue.Previous::getMolarSpecificConstantVolumeSpecificHeat,
                ColumnCell2.ComponentValue.Previous::setMolarSpecificConstantVolumeSpecificHeat,
                ColumnCellEntity2.ComponentValue.Previous::molarSpecificConstantVolumeSpecificHeat,
                12.))
        .forEach(entry -> {
          ColumnCell2 cell1 = supplier.get();
          entry.setter()
              .accept(cell1.componentMap().get(ComponentKey.OXYGEN).previous(), entry.value());

          ColumnCellEntity2 entity = cell1.newEntity();
          assertThat(
              entry.entityGetter()
                  .applyAsDouble(entity.componentMap().get(ComponentKey.OXYGEN).previous()))
              .isEqualTo(entry.value());

          ColumnCell2 cell2 = supplier.get();
          cell2.accept(entity);
          assertThat(entry.getter()
              .applyAsDouble(cell2.componentMap().get(ComponentKey.OXYGEN).previous()))
              .isEqualTo(entry.value());
        });
  }

}
