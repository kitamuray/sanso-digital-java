/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;
import org.junit.jupiter.api.Test;

/// [AbstractFluidCell] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
class AbstractFluidCellTest {

  /// [AbstractFluidCell] の getter/setter をテスト.
  @Test
  void testCell() {
    AbstractFluidCell o = new AbstractFluidCell() {

      @Override
      public FluidCell.Previous previous() {
        return null;
      }

      @Override
      public Map<ComponentKey, ? extends FluidCell.ComponentValue> componentMap() {
        return Map.of();
      }

    };

    assertThatThrownBy(() -> o.setTemperature(0.)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> o.setPressure(0.)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> o.setDensity(0.)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> o.setSpecificConstantPressureSpecificHeat(0.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> o.setViscosity(0.)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> o.setThermalConductivity(0.))
        .isInstanceOf(IllegalArgumentException.class);

    final double temperature = 298.15;
    final double pressure = 101325.;
    final double density = 1.2;
    final double specificEnthalpy = 1e6;
    final double specificConstantPressureSpecificHeat = 1e3;
    final double viscosity = 18e-6;
    final double thermalConductivity = 0.02;
    final double molecularWeight = 29.;

    o.setTemperature(temperature);
    o.setPressure(pressure);
    o.setDensity(density);
    o.setSpecificEnthalpy(specificEnthalpy);
    o.setSpecificConstantPressureSpecificHeat(specificConstantPressureSpecificHeat);
    o.setViscosity(viscosity);
    o.setThermalConductivity(thermalConductivity);
    o.setMolecularWeight(molecularWeight);

    assertThat(o)
        .returns(temperature, AbstractFluidCell::getTemperature)
        .returns(pressure, AbstractFluidCell::getPressure)
        .returns(density, AbstractFluidCell::getDensity)
        .returns(specificEnthalpy, AbstractFluidCell::getSpecificEnthalpy)
        .returns(
            specificConstantPressureSpecificHeat,
            AbstractFluidCell::getSpecificConstantPressureSpecificHeat)
        .returns(viscosity, AbstractFluidCell::getViscosity)
        .returns(thermalConductivity, AbstractFluidCell::getThermalConductivity)
        .returns(molecularWeight, AbstractFluidCell::getMolecularWeight);
  }

  /// [AbstractFluidCell.Previous] のテスト.
  @Test
  void testCellPrevious() {
    AbstractFluidCell.Previous o = new AbstractFluidCell.Previous() {
    };

    assertThatThrownBy(() -> o.setTemperature(0.)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> o.setDensity(0.)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> o.setSpecificConstantPressureSpecificHeat(0.))
        .isInstanceOf(IllegalArgumentException.class);

    final double temperature = 298.15;
    final double density = 1.2;
    final double specificEnthalpy = 1e6;
    final double specificConstantPressureSpecificHeat = 1e3;

    o.setTemperature(temperature);
    o.setDensity(density);
    o.setSpecificEnthalpy(specificEnthalpy);
    o.setSpecificConstantPressureSpecificHeat(specificConstantPressureSpecificHeat);

    assertThat(o)
        .returns(temperature, AbstractFluidCell.Previous::getTemperature)
        .returns(density, AbstractFluidCell.Previous::getDensity)
        .returns(specificEnthalpy, AbstractFluidCell.Previous::getSpecificEnthalpy)
        .returns(
            specificConstantPressureSpecificHeat,
            AbstractFluidCell.Previous::getSpecificConstantPressureSpecificHeat);
  }

  /// [AbstractFluidCell.ComponentValue] のテスト.
  @Test
  void testCellComponentValue() {
    AbstractFluidCell.ComponentValue o = new AbstractFluidCell.ComponentValue() {

      @Override
      public FluidCell.ComponentValue.Previous previous() {
        return null;
      }

    };

    assertThatThrownBy(() -> o.setFraction(-1.)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> o.setFraction(2.)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> o.setDiffusivity(0.)).isInstanceOf(IllegalArgumentException.class);

    final double fraction = 0.12;
    final double diffusivity = 5e-4;
    final double molarFraction = 0.23;
    final double viscosity = 18e-6;

    o.setFraction(fraction);
    o.setDiffusivity(diffusivity);
    o.setMolarFraction(molarFraction);
    o.setViscosity(viscosity);

    assertThat(o)
        .returns(fraction, AbstractFluidCell.ComponentValue::getFraction)
        .returns(diffusivity, AbstractFluidCell.ComponentValue::getDiffusivity)
        .returns(molarFraction, AbstractFluidCell.ComponentValue::getMolarFraction)
        .returns(viscosity, AbstractFluidCell.ComponentValue::getViscosity);
  }

  /// [AbstractFluidCell.ComponentValue.Previous] のテスト.
  @Test
  void testCellComponentValuePrevious() {
    AbstractFluidCell.ComponentValue.Previous o = new AbstractFluidCell.ComponentValue.Previous() {
    };

    assertThatThrownBy(() -> o.setFraction(-1.)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> o.setFraction(2.)).isInstanceOf(IllegalArgumentException.class);

    final double fraction = 0.12;

    o.setFraction(fraction);

    assertThat(o)
        .returns(fraction, AbstractFluidCell.ComponentValue.Previous::getFraction);
  }

}
