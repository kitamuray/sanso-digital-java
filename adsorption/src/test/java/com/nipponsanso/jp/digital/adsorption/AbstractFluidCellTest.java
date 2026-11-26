/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import java.util.Map;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;

/// [AbstractFluidCell] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
class AbstractFluidCellTest {

  /// [AbstractFluidCell] の getter 及び setter を一括でテスト.
  @Test
  void testCell() {
    Supplier<AbstractFluidCell> supplier = () -> new AbstractFluidCell() {

      @Override
      public FluidCell.Previous previous() {
        return null;
      }

      @Override
      public Map<ComponentKey, ? extends FluidCell.ComponentValue> componentMap() {
        return Map.of();
      }

    };

    GetterSetterTester.supplier(supplier)
        .getter(AbstractFluidCell::getTemperature)
        .setter(AbstractFluidCell::setTemperature)
        .valid(298.15)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(AbstractFluidCell::getPressure)
        .setter(AbstractFluidCell::setPressure)
        .valid(101325.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(AbstractFluidCell::getDensity)
        .setter(AbstractFluidCell::setDensity)
        .valid(1.2)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(AbstractFluidCell::getSpecificEnthalpy)
        .setter(AbstractFluidCell::setSpecificEnthalpy)
        .valid(1e6)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(AbstractFluidCell::getSpecificConstantPressureSpecificHeat)
        .setter(AbstractFluidCell::setSpecificConstantPressureSpecificHeat)
        .valid(1000.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(AbstractFluidCell::getViscosity)
        .setter(AbstractFluidCell::setViscosity)
        .valid(18e-6)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(AbstractFluidCell::getThermalConductivity)
        .setter(AbstractFluidCell::setThermalConductivity)
        .valid(0.02)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(AbstractFluidCell::getMolecularWeight)
        .setter(AbstractFluidCell::setMolecularWeight)
        .valid(28.)
        .test();
  }

  /// [AbstractFluidCell.Previous] の getter 及び setter を一括でテスト.
  @Test
  void testPrevious() {
    Supplier<AbstractFluidCell.Previous> supplier = () -> new AbstractFluidCell.Previous() {
    };

    GetterSetterTester.supplier(supplier)
        .getter(AbstractFluidCell.Previous::getTemperature)
        .setter(AbstractFluidCell.Previous::setTemperature)
        .valid(298.15)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(AbstractFluidCell.Previous::getDensity)
        .setter(AbstractFluidCell.Previous::setDensity)
        .valid(1.2)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(AbstractFluidCell.Previous::getSpecificEnthalpy)
        .setter(AbstractFluidCell.Previous::setSpecificEnthalpy)
        .valid(1e6)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(AbstractFluidCell.Previous::getSpecificConstantPressureSpecificHeat)
        .setter(AbstractFluidCell.Previous::setSpecificConstantPressureSpecificHeat)
        .valid(1000.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
  }

  /// [AbstractFluidCell.ComponentValue] の getter 及び setter を一括でテスト.
  @Test
  void testComponentValue() {
    Supplier<AbstractFluidCell.ComponentValue> supplier
        = () -> new AbstractFluidCell.ComponentValue() {

      @Override
      public FluidCell.ComponentValue.Previous previous() {
        return null;
      }

    };

    GetterSetterTester.supplier(supplier)
        .getter(AbstractFluidCell.ComponentValue::getFraction)
        .setter(AbstractFluidCell.ComponentValue::setFraction)
        .valid(0.123)
        .thrown(IllegalArgumentException.class).value(-1.)
        .thrown(IllegalArgumentException.class).value(2.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(AbstractFluidCell.ComponentValue::getDiffusivity)
        .setter(AbstractFluidCell.ComponentValue::setDiffusivity)
        .valid(5e-4)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(AbstractFluidCell.ComponentValue::getMolarFraction)
        .setter(AbstractFluidCell.ComponentValue::setMolarFraction)
        .valid(0.123)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(AbstractFluidCell.ComponentValue::getViscosity)
        .setter(AbstractFluidCell.ComponentValue::setViscosity)
        .valid(18e-6)
        .test();
  }

  /// [AbstractFluidCell.ComponentValue.Previous] の getter 及び setter を一括でテスト.
  @Test
  void testComponentValuePrevious() {

    Supplier<AbstractFluidCell.ComponentValue.Previous> supplier
        = () -> new AbstractFluidCell.ComponentValue.Previous() {
    };

    GetterSetterTester.supplier(supplier)
        .getter(AbstractFluidCell.ComponentValue.Previous::getFraction)
        .setter(AbstractFluidCell.ComponentValue.Previous::setFraction)
        .valid(0.123)
        .thrown(IllegalArgumentException.class).value(-1.)
        .thrown(IllegalArgumentException.class).value(2.)
        .test();
  }

}
