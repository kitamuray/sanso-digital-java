/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doReturn;

import com.nipponsanso.jp.digital.adsorption.ColumnCell.ComponentValue;
import java.util.Set;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.Stream;
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

  /// [ColumnCell] のデータ階層をテスト.
  @Test
  void testStructure() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    LocalModel model = new LocalModel(this.composition);
    ColumnCell cell = model.new LocalCell(
        this.shape, 0., 1., this.composition, this.shell, this.adsorbent);

    assertThat(cell.previous()).isInstanceOf(ColumnCell.Previous.class);
    assertThat(cell.componentMap().keySet()).isEqualTo(componentKeySet);
    assertThat(cell.componentMap().get(ComponentKey.OXYGEN).previous())
        .isInstanceOf(ColumnCell.ComponentValue.Previous.class);
  }

  /// [ColumnCell] の幾何情報をテスト.
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
    ColumnCell cell = model.new LocalCell(
        this.shape, fromX, toX, this.composition, this.shell, this.adsorbent);

    assertThat(cell.centroid()).isEqualTo(.5 * (fromX + toX));
    assertThat(cell.length()).isEqualTo(toX - fromX);
    assertThat(cell.fluidVolume()).isEqualTo(fluidVolume);
    assertThat(cell.shellVolume()).isEqualTo(shellVolume);
    assertThat(cell.insideArea()).isEqualTo(insideArea);
    assertThat(cell.outsideArea()).isEqualTo(outsideArea);
  }

  /// [ColumnCell] の [Shell] 由来メソッドをテスト.
  @Test
  void testShell() {
    final double shellDensity = 5800.;
    doReturn(shellDensity).when(this.shell).density();

    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    LocalModel model = new LocalModel(this.composition);
    ColumnCell cell = model.new LocalCell(
        this.shape, 0., 1., this.composition, this.shell, this.adsorbent);

    assertThat(cell.shellDensity()).isEqualTo(shellDensity);
  }

  /// [ColumnCell] の [Adsorbent] 由来メソッドをテスト.
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
    ColumnCell cell = model.new LocalCell(
        this.shape, 0., 1., this.composition, this.shell, this.adsorbent);

    assertThat(cell.particleDiameter()).isEqualTo(particleDiameter);
    assertThat(cell.voidFraction()).isEqualTo(voidFraction);
    assertThat(cell.bulkDensity()).isEqualTo(bulkDensity);
  }

  /// [ColumnCell] の getter 及び setter をテスト.
  @Test
  void testGetterAndSetter() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    Supplier<ColumnCell> supplier = () -> {
      LocalModel model = new LocalModel(this.composition);
      return model.new LocalCell(
          this.shape, 0., 1., this.composition, this.shell, this.adsorbent);
    };

    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell::getTemperature)
        .setter(ColumnCell::setTemperature)
        .valid(298.15)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell::getPressure)
        .setter(ColumnCell::setPressure)
        .valid(101325.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell::getDensity)
        .setter(ColumnCell::setDensity)
        .valid(1.2)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell::getSpecificEnthalpy)
        .setter(ColumnCell::setSpecificEnthalpy)
        .valid(1e6)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell::getSpecificConstantPressureSpecificHeat)
        .setter(ColumnCell::setSpecificConstantPressureSpecificHeat)
        .valid(1e3)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell::getViscosity)
        .setter(ColumnCell::setViscosity)
        .valid(18e-6)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell::getThermalConductivity)
        .setter(ColumnCell::setThermalConductivity)
        .valid(0.02)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell::getMolecularWeight)
        .setter(ColumnCell::setMolecularWeight)
        .valid(29.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell::getShellTemperature)
        .setter(ColumnCell::setShellTemperature)
        .valid(303.15)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell::getShellSpecificHeat)
        .setter(ColumnCell::setShellSpecificHeat)
        .valid(640.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell::getShellThermalConductivity)
        .setter(ColumnCell::setShellThermalConductivity)
        .valid(0.2)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell::getAdsorbentSpecificHeat)
        .setter(ColumnCell::setAdsorbentSpecificHeat)
        .valid(800.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
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
        .valid(2.1)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell::getQ)
        .setter(ColumnCell::setQ)
        .valid(3.2)
        .test();
  }

  /// [ColumnCell] の永続化をテスト.
  @Test
  void testEntity() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    Supplier<ColumnCell> supplier = () -> {
      LocalModel model = new LocalModel(this.composition);
      return model.new LocalCell(
          this.shape, 0., 1., this.composition, this.shell, this.adsorbent);
    };

    record Entry(
        ToDoubleFunction<ColumnCell> getter,
        ObjDoubleConsumer<ColumnCell> setter,
        ToDoubleFunction<ColumnCellEntity> entityGetter,
        double value) {}

    Stream.of(
            new Entry(
                ColumnCell::getTemperature,
                ColumnCell::setTemperature,
                ColumnCellEntity::temperature,
                298.15),
            new Entry(
                ColumnCell::getPressure,
                ColumnCell::setPressure,
                ColumnCellEntity::pressure,
                101325.),
            new Entry(
                ColumnCell::getDensity,
                ColumnCell::setDensity,
                ColumnCellEntity::density,
                1.2),
            new Entry(
                ColumnCell::getSpecificEnthalpy,
                ColumnCell::setSpecificEnthalpy,
                ColumnCellEntity::specificEnthalpy,
                1000000.),
            new Entry(
                ColumnCell::getSpecificConstantPressureSpecificHeat,
                ColumnCell::setSpecificConstantPressureSpecificHeat,
                ColumnCellEntity::specificConstantPressureSpecificHeat,
                1000.),
            new Entry(
                ColumnCell::getViscosity,
                ColumnCell::setViscosity,
                ColumnCellEntity::viscosity,
                18e-6),
            new Entry(
                ColumnCell::getThermalConductivity,
                ColumnCell::setThermalConductivity,
                ColumnCellEntity::thermalConductivity,
                0.02),
            new Entry(
                ColumnCell::getShellTemperature,
                ColumnCell::setShellTemperature,
                ColumnCellEntity::shellTemperature,
                303.15),
            new Entry(
                ColumnCell::getShellSpecificHeat,
                ColumnCell::setShellSpecificHeat,
                ColumnCellEntity::shellSpecificHeat,
                640.),
            new Entry(
                ColumnCell::getShellThermalConductivity,
                ColumnCell::setShellThermalConductivity,
                ColumnCellEntity::shellThermalConductivity,
                0.2),
            new Entry(
                ColumnCell::getAdsorbentSpecificHeat,
                ColumnCell::setAdsorbentSpecificHeat,
                ColumnCellEntity::adsorbentSpecificHeat,
                800.))
        .forEach(entry -> {
          ColumnCell cell1 = supplier.get();
          entry.setter().accept(cell1, entry.value());

          ColumnCellEntity entity = cell1.newEntity();
          assertThat(entry.entityGetter().applyAsDouble(entity)).isEqualTo(entry.value());

          ColumnCell cell2 = supplier.get();
          cell2.accept(entity);
          assertThat(entry.getter().applyAsDouble(cell2)).isEqualTo(entry.value());
        });
  }

  /// [ColumnCell.Previous] の getter 及び setter をテスト.
  @Test
  void testPreviousGetterAndSetter() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    Supplier<ColumnCell.Previous> supplier = () -> {
      LocalModel model = new LocalModel(this.composition);
      return model.new LocalCell(
          this.shape, 0., 1., this.composition, this.shell, this.adsorbent)
          .previous();
    };

    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell.Previous::getTemperature)
        .setter(ColumnCell.Previous::setTemperature)
        .valid(298.15)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell.Previous::getDensity)
        .setter(ColumnCell.Previous::setDensity)
        .valid(1.2)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell.Previous::getSpecificEnthalpy)
        .setter(ColumnCell.Previous::setSpecificEnthalpy)
        .valid(1e6)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell.Previous::getSpecificConstantPressureSpecificHeat)
        .setter(ColumnCell.Previous::setSpecificConstantPressureSpecificHeat)
        .valid(1e3)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell.Previous::getShellTemperature)
        .setter(ColumnCell.Previous::setShellTemperature)
        .valid(303.15)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell.Previous::getShellSpecificHeat)
        .setter(ColumnCell.Previous::setShellSpecificHeat)
        .valid(640.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell.Previous::getAdsorbentSpecificHeat)
        .setter(ColumnCell.Previous::setAdsorbentSpecificHeat)
        .valid(800.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
  }

  /// [ColumnCell.Previous] の永続化をテスト.
  @Test
  void testPreviousEntity() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    Supplier<ColumnCell> supplier = () -> {
      LocalModel model = new LocalModel(this.composition);
      return model.new LocalCell(
          this.shape, 0., 1., this.composition, this.shell, this.adsorbent);
    };

    record Entry(
        ToDoubleFunction<ColumnCell.Previous> getter,
        ObjDoubleConsumer<ColumnCell.Previous> setter,
        ToDoubleFunction<ColumnCellEntity.Previous> entityGetter,
        double value) {}

    Stream.of(
            new Entry(
                ColumnCell.Previous::getTemperature,
                ColumnCell.Previous::setTemperature,
                ColumnCellEntity.Previous::temperature,
                298.15),
            new Entry(
                ColumnCell.Previous::getDensity,
                ColumnCell.Previous::setDensity,
                ColumnCellEntity.Previous::density,
                1.2),
            new Entry(
                ColumnCell.Previous::getSpecificEnthalpy,
                ColumnCell.Previous::setSpecificEnthalpy,
                ColumnCellEntity.Previous::specificEnthalpy,
                1000000.),
            new Entry(
                ColumnCell.Previous::getSpecificConstantPressureSpecificHeat,
                ColumnCell.Previous::setSpecificConstantPressureSpecificHeat,
                ColumnCellEntity.Previous::specificConstantPressureSpecificHeat,
                1000.),
            new Entry(
                ColumnCell.Previous::getShellTemperature,
                ColumnCell.Previous::setShellTemperature,
                ColumnCellEntity.Previous::shellTemperature,
                303.15),
            new Entry(
                ColumnCell.Previous::getShellSpecificHeat,
                ColumnCell.Previous::setShellSpecificHeat,
                ColumnCellEntity.Previous::shellSpecificHeat,
                640.),
            new Entry(
                ColumnCell.Previous::getAdsorbentSpecificHeat,
                ColumnCell.Previous::setAdsorbentSpecificHeat,
                ColumnCellEntity.Previous::adsorbentSpecificHeat,
                800.))
        .forEach(entry -> {
          ColumnCell cell1 = supplier.get();
          entry.setter().accept(cell1.previous(), entry.value());

          ColumnCellEntity entity = cell1.newEntity();
          assertThat(entry.entityGetter().applyAsDouble(entity.previous()))
              .isEqualTo(entry.value());

          ColumnCell cell2 = supplier.get();
          cell2.accept(entity);
          assertThat(entry.getter().applyAsDouble(cell2.previous())).isEqualTo(entry.value());
        });
  }

  /// [ColumnCell.ComponentValue] の getter 及び setter をテスト.
  @Test
  void testComponentValueGetterAndSetter() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    Supplier<ColumnCell.ComponentValue> supplier = () -> {
      LocalModel model = new LocalModel(this.composition);
      return model.new LocalCell(
          this.shape, 0., 1., this.composition, this.shell, this.adsorbent)
          .componentMap().get(ComponentKey.OXYGEN);
    };

    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell.ComponentValue::getFraction)
        .setter(ColumnCell.ComponentValue::setFraction)
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
        .getter(ColumnCell.ComponentValue::getMolarFraction)
        .setter(ColumnCell.ComponentValue::setMolarFraction)
        .valid(0.23)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell.ComponentValue::getViscosity)
        .setter(ColumnCell.ComponentValue::setViscosity)
        .valid(18e-6)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell.ComponentValue::getMolarQuantity)
        .setter(ColumnCell.ComponentValue::setMolarQuantity)
        .valid(1.2)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell.ComponentValue::getMolarSpecificConstantVolumeSpecificHeat)
        .setter(ColumnCell.ComponentValue::setMolarSpecificConstantVolumeSpecificHeat)
        .valid(12.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
  }

  /// [ColumnCell.ComponentValue] の永続化をテスト.
  @Test
  void testComponentValueEntity() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    Supplier<ColumnCell> supplier = () -> {
      LocalModel model = new LocalModel(this.composition);
      return model.new LocalCell(
          this.shape, 0., 1., this.composition, this.shell, this.adsorbent);
    };

    record Entry(
        ToDoubleFunction<ColumnCell.ComponentValue> getter,
        ObjDoubleConsumer<ColumnCell.ComponentValue> setter,
        ToDoubleFunction<ColumnCellEntity.ComponentValue> entityGetter,
        double value) {}

    Stream.of(
            new Entry(
                ColumnCell.ComponentValue::getFraction,
                ColumnCell.ComponentValue::setFraction,
                ColumnCellEntity.ComponentValue::fraction,
                0.12),
            new Entry(
                ColumnCell.ComponentValue::getDiffusivity,
                ColumnCell.ComponentValue::setDiffusivity,
                ColumnCellEntity.ComponentValue::diffusivity,
                5e-4),
            new Entry(
                ColumnCell.ComponentValue::getMolarQuantity,
                ColumnCell.ComponentValue::setMolarQuantity,
                ColumnCellEntity.ComponentValue::molarQuantity,
                100.),
            new Entry(
                ColumnCell.ComponentValue::getMolarSpecificConstantVolumeSpecificHeat,
                ColumnCell.ComponentValue::setMolarSpecificConstantVolumeSpecificHeat,
                ColumnCellEntity.ComponentValue::molarSpecificConstantVolumeSpecificHeat,
                12.))
        .forEach(entry -> {
          ColumnCell cell1 = supplier.get();
          entry.setter().accept(cell1.componentMap().get(ComponentKey.OXYGEN), entry.value());

          ColumnCellEntity entity = cell1.newEntity();
          assertThat(
              entry.entityGetter().applyAsDouble(entity.componentMap().get(ComponentKey.OXYGEN)))
              .isEqualTo(entry.value());

          ColumnCell cell2 = supplier.get();
          cell2.accept(entity);
          assertThat(entry.getter().applyAsDouble(cell2.componentMap().get(ComponentKey.OXYGEN)))
              .isEqualTo(entry.value());
        });
  }

  /// [ColumnCell.ComponentValue.Previous] の getter 及び setter をテスト.
  @Test
  void testComponentValuePreviousGetterAndSetter() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    Supplier<ColumnCell.ComponentValue.Previous> supplier = () -> {
      LocalModel model = new LocalModel(this.composition);
      return model.new LocalCell(
          this.shape, 0., 1., this.composition, this.shell, this.adsorbent)
          .componentMap().get(ComponentKey.OXYGEN).previous();
    };

    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell.ComponentValue.Previous::getFraction)
        .setter(ColumnCell.ComponentValue.Previous::setFraction)
        .valid(0.123)
        .thrown(IllegalArgumentException.class).value(-1.)
        .thrown(IllegalArgumentException.class).value(2.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell.ComponentValue.Previous::getMolarQuantity)
        .setter(ColumnCell.ComponentValue.Previous::setMolarQuantity)
        .valid(1.2)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ColumnCell.ComponentValue.Previous::getMolarSpecificConstantVolumeSpecificHeat)
        .setter(ColumnCell.ComponentValue.Previous::setMolarSpecificConstantVolumeSpecificHeat)
        .valid(12.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
  }

  /// [ColumnCell.ComponentValue.Previous] の永続化をテスト.
  @Test
  void testComponentValuePreviousEntity() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    Supplier<ColumnCell> supplier = () -> {
      LocalModel model = new LocalModel(this.composition);
      return model.new LocalCell(
          this.shape, 0., 1., this.composition, this.shell, this.adsorbent);
    };

    record Entry(
        ToDoubleFunction<ColumnCell.ComponentValue.Previous> getter,
        ObjDoubleConsumer<ColumnCell.ComponentValue.Previous> setter,
        ToDoubleFunction<ColumnCellEntity.ComponentValue.Previous> entityGetter,
        double value) {}

    Stream.of(
            new Entry(
                ColumnCell.ComponentValue.Previous::getFraction,
                ColumnCell.ComponentValue.Previous::setFraction,
                ColumnCellEntity.ComponentValue.Previous::fraction,
                0.12),
            new Entry(
                ColumnCell.ComponentValue.Previous::getMolarQuantity,
                ColumnCell.ComponentValue.Previous::setMolarQuantity,
                ColumnCellEntity.ComponentValue.Previous::molarQuantity,
                100.),
            new Entry(
                ColumnCell.ComponentValue.Previous::getMolarSpecificConstantVolumeSpecificHeat,
                ColumnCell.ComponentValue.Previous::setMolarSpecificConstantVolumeSpecificHeat,
                ColumnCellEntity.ComponentValue.Previous::molarSpecificConstantVolumeSpecificHeat,
                12.))
        .forEach(entry -> {
          ColumnCell cell1 = supplier.get();
          entry.setter()
              .accept(cell1.componentMap().get(ComponentKey.OXYGEN).previous(), entry.value());

          ColumnCellEntity entity = cell1.newEntity();
          assertThat(
              entry.entityGetter()
                  .applyAsDouble(entity.componentMap().get(ComponentKey.OXYGEN).previous()))
              .isEqualTo(entry.value());

          ColumnCell cell2 = supplier.get();
          cell2.accept(entity);
          assertThat(entry.getter()
              .applyAsDouble(cell2.componentMap().get(ComponentKey.OXYGEN).previous()))
              .isEqualTo(entry.value());
        });
  }

}
