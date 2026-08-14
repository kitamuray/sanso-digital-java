/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption.internal;

import com.nipponsanso.jp.digital.adsorption.ComponentKey;
import com.nipponsanso.jp.digital.adsorption.GetterSetterTester;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/// [FluidCell] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
class FluidCellTest {

  /// [FluidCell] の内部実装.
  private static final class LocalCell extends FluidCell {

    private final double[] doubleArray = new double[FluidCell.SIZE];

    @Override
    public int offset() {
      return 0;
    }

    @Override
    public double[] doubleArray() {
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

    } // PreviousTest

  } // ComponentValueTest

}
