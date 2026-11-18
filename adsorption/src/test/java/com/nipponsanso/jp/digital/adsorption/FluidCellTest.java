/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;

import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [FluidCell] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class FluidCellTest {

  /// [FluidCell] の内部実装.
  private static final class LocalCell extends FluidCell {

    private final double[] doubleArray = new double[FluidCell.SIZE];

    @Override
    int offset() {
      return 0;
    }

    @Override
    double[] doubleArray() {
      return this.doubleArray;
    }

    @Override
    public FluidCell.Previous previous() {
      return null;
    }

    @Override
    public Map<ComponentKey, ? extends FluidCell.ComponentValue> componentMap() {
      return Map.of();
    }

    private static final class Previous extends FluidCell.Previous {

      private final double[] doubleArray = new double[FluidCell.Previous.SIZE];

      @Override
      public int offset() {
        return 0;
      }

      @Override
      public double[] doubleArray() {
        return this.doubleArray;
      }

    } // Previous

    private static final class ComponentValue extends FluidCell.ComponentValue {

      private final double[] doubleArray = new double[FluidCell.ComponentValue.SIZE];

      @Override
      public int offset() {
        return 0;
      }

      @Override
      public double[] doubleArray() {
        return this.doubleArray;
      }

      @Override
      public FluidCell.ComponentValue.Previous previous() {
        return null;
      }

      private static final class Previous extends FluidCell.ComponentValue.Previous {

        private final double[] doubleArray = new double[FluidCell.ComponentValue.Previous.SIZE];

        @Override
        public int offset() {
          return 0;
        }

        @Override
        public double[] doubleArray() {
          return this.doubleArray;
        }

      } // Previous

    } // ComponentValue

  } // LocalCell

  /// [FluidCell] の Getter メソッドや Setter メソッドをテスト.
  @Test
  void testGetterAndSetter() {
    GetterSetterTester.supplier(LocalCell::new)
        .getter(FluidCell::getTemperature)
        .setter(FluidCell::setTemperature)
        .valid(298.15)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(LocalCell::new)
        .getter(FluidCell::getPressure)
        .setter(FluidCell::setPressure)
        .valid(101325.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(LocalCell::new)
        .getter(FluidCell::getDensity)
        .setter(FluidCell::setDensity)
        .valid(1.2)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(LocalCell::new)
        .getter(FluidCell::getSpecificEnthalpy)
        .setter(FluidCell::setSpecificEnthalpy)
        .valid(10000.)
        .test();
    GetterSetterTester.supplier(LocalCell::new)
        .getter(FluidCell::getSpecificConstantPressureSpecificHeat)
        .setter(FluidCell::setSpecificConstantPressureSpecificHeat)
        .valid(1000.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(LocalCell::new)
        .getter(FluidCell::getViscosity)
        .setter(FluidCell::setViscosity)
        .valid(18e-6)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(LocalCell::new)
        .getter(FluidCell::getThermalConductivity)
        .setter(FluidCell::setThermalConductivity)
        .valid(0.02)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(LocalCell::new)
        .getter(FluidCell::getMolecularWeight)
        .setter(FluidCell::setMolecularWeight)
        .valid(29.7)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
  }

  @Mock
  private FluidCellEntity<?, ?, ?> entity;
  
  static void initMock(FluidCellEntity<?, ?, ?> entity) {
    final double temperature = 298.15;
    doReturn(temperature).when(entity).getTemperature();
    doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(temperature))
        .when(entity)
        .setTemperature(anyDouble());

    final double pressure = 101325.;
    doReturn(pressure).when(entity).getPressure();
    doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(pressure))
        .when(entity)
        .setPressure(anyDouble());

    final double density = 1.2;
    doReturn(density).when(entity).getDensity();
    doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(density))
        .when(entity)
        .setDensity(anyDouble());

    final double specificHeat = 1.2;
    doReturn(specificHeat).when(entity).getSpecificConstantPressureSpecificHeat();
    doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(specificHeat))
        .when(entity)
        .setSpecificConstantPressureSpecificHeat(anyDouble());

    final double specificEnthalpy = 10000.;
    doReturn(specificEnthalpy).when(entity).getSpecificEnthalpy();
    doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(specificEnthalpy))
        .when(entity)
        .setSpecificEnthalpy(anyDouble());

    final double viscosity = 18e-6;
    doReturn(viscosity).when(entity).getViscosity();
    doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(viscosity))
        .when(entity)
        .setViscosity(anyDouble());

    final double thermalConductivity = 0.02;
    doReturn(thermalConductivity).when(entity).getThermalConductivity();
    doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(thermalConductivity))
        .when(entity)
        .setThermalConductivity(anyDouble());
  }

  /// [FluidCell#read(FluidCellEntity)] と [FluidCell#write(FluidCellEntity)] のテスト.
  @Test
  void testEntity() {
    initMock(this.entity);

    LocalCell cell = new LocalCell();
    cell.read(this.entity);
    cell.write(this.entity);
  }


  /// [FluidCell.Previous] のテスト.
  @Nested
  class PreviousTest {

    /// [FluidCell.Previous] の Getter と Setter をテスト.
    @Test
    void testGetterAndSetter() {
      GetterSetterTester.supplier(LocalCell.Previous::new)
          .getter(FluidCell.Previous::getTemperature)
          .setter(FluidCell.Previous::setTemperature)
          .valid(298.15)
          .thrown(IllegalArgumentException.class).value(0.)
          .test();
      GetterSetterTester.supplier(LocalCell.Previous::new)
          .getter(FluidCell.Previous::getDensity)
          .setter(FluidCell.Previous::setDensity)
          .valid(1.2)
          .thrown(IllegalArgumentException.class).value(0.)
          .test();
      GetterSetterTester.supplier(LocalCell.Previous::new)
          .getter(FluidCell.Previous::getSpecificEnthalpy)
          .setter(FluidCell.Previous::setSpecificEnthalpy)
          .valid(10000.)
          .test();
      GetterSetterTester.supplier(LocalCell.Previous::new)
          .getter(FluidCell.Previous::getSpecificConstantPressureSpecificHeat)
          .setter(FluidCell.Previous::setSpecificConstantPressureSpecificHeat)
          .valid(1000.)
          .thrown(IllegalArgumentException.class).value(0.)
          .test();
    }

    static void initMock(FluidCellEntity.Previous entity) {
      final double temperature = 298.15;
      doReturn(temperature).when(entity).getTemperature();
      doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(temperature))
          .when(entity)
          .setTemperature(anyDouble());

      final double density = 1.2;
      doReturn(density).when(entity).getDensity();
      doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(density))
          .when(entity)
          .setDensity(anyDouble());

      final double specificHeat = 1.2;
      doReturn(specificHeat).when(entity).getSpecificConstantPressureSpecificHeat();
      doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(specificHeat))
          .when(entity)
          .setSpecificConstantPressureSpecificHeat(anyDouble());

      final double specificEnthalpy = 10000.;
      doReturn(specificEnthalpy).when(entity).getSpecificEnthalpy();
      doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(specificEnthalpy))
          .when(entity)
          .setSpecificEnthalpy(anyDouble());
    }

    @Mock
    private FluidCellEntity.Previous entity;

    /// [FluidCell.Previous#read(FluidCellEntity.Previous)] と
    /// [FluidCell.Previous#write(FluidCellEntity.Previous)] のテスト.
    @Test
    void testEntity() {
      initMock(this.entity);

      LocalCell.Previous previous = new LocalCell.Previous();
      previous.read(this.entity);
      previous.write(this.entity);
    }

  } // PreviousTest

  /// [FluidCell.ComponentValue] のテスト.
  @Nested
  class ComponentValueTest {

    /// [FluidCell.ComponentValue] の Getter と Setter をテスト.
    @Test
    void testGetterAndSetter() {
      GetterSetterTester.supplier(LocalCell.ComponentValue::new)
          .getter(FluidCell.ComponentValue::getFraction)
          .setter(FluidCell.ComponentValue::setFraction)
          .valid(0.1)
          .thrown(IllegalArgumentException.class).value(-0.1)
          .thrown(IllegalArgumentException.class).value(1.1)
          .test();
      GetterSetterTester.supplier(LocalCell.ComponentValue::new)
          .getter(FluidCell.ComponentValue::getDiffusivity)
          .setter(FluidCell.ComponentValue::setDiffusivity)
          .valid(4e-4)
          .thrown(IllegalArgumentException.class).value(0.)
          .test();
      GetterSetterTester.supplier(LocalCell.ComponentValue::new)
          .getter(FluidCell.ComponentValue::getMolarFraction)
          .setter(FluidCell.ComponentValue::setMolarFraction)
          .valid(0.1)
          .thrown(IllegalArgumentException.class).value(-0.1)
          .thrown(IllegalArgumentException.class).value(1.1)
          .test();
      GetterSetterTester.supplier(LocalCell.ComponentValue::new)
          .getter(FluidCell.ComponentValue::getViscosity)
          .setter(FluidCell.ComponentValue::setViscosity)
          .valid(18e-6)
          .thrown(IllegalArgumentException.class).value(0.)
          .test();
    }

    static void initMock(FluidCellEntity.ComponentValue<?> entity) {
      final double fraction = 0.123;
      doReturn(fraction).when(entity).getFraction();
      doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(fraction))
          .when(entity)
          .setFraction(anyDouble());

      final double diffusivity = 5e-4;
      doReturn(diffusivity).when(entity).getDiffusivity();
      doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(diffusivity))
          .when(entity)
          .setDiffusivity(anyDouble());
    }

    @Mock
    private FluidCellEntity.ComponentValue<?> entity;

    /// [FluidCell.ComponentValue#read(FluidCellEntity.ComponentValue)] と
    /// [FluidCell.ComponentValue#write(FluidCellEntity.ComponentValue)] のテスト.
    @Test
    void testEntity() {
      initMock(this.entity);

      LocalCell.ComponentValue componentValue = new LocalCell.ComponentValue();
      componentValue.read(this.entity);
      componentValue.write(this.entity);
    }

    /// [FluidCell.ComponentValue.Previous] のテスト.
    @Nested
    class PreviousTest {

      /// [FluidCell.ComponentValue.Previous] の Getter と Setter をテスト.
      @Test
      void testGetterAndSetter() {
        GetterSetterTester.supplier(LocalCell.ComponentValue.Previous::new)
            .getter(FluidCell.ComponentValue.Previous::getFraction)
            .setter(FluidCell.ComponentValue.Previous::setFraction)
            .valid(0.1)
            .thrown(IllegalArgumentException.class).value(-0.1)
            .thrown(IllegalArgumentException.class).value(1.1)
            .test();
      }

      static void initMock(FluidCellEntity.ComponentValue.Previous entity) {
        final double fraction = 0.123;
        doReturn(fraction).when(entity).getFraction();
        doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(fraction))
            .when(entity)
            .setFraction(anyDouble());
      }

      @Mock
      private FluidCellEntity.ComponentValue.Previous entity;

      /// [FluidCell.ComponentValue.Previous#read(FluidCellEntity.ComponentValue.Previous)] と
      /// [FluidCell.ComponentValue.Previous#write(FluidCellEntity.ComponentValue.Previous)] のテスト.
      @Test
      void testEntity() {
        initMock(this.entity);

        LocalCell.ComponentValue.Previous previous = new LocalCell.ComponentValue.Previous();
        previous.read(this.entity);
        previous.write(this.entity);
      }

    } // PreviousTest

  } // ComponentValueTest

}
