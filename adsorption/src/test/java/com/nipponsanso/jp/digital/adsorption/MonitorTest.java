/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [Monitor] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class MonitorTest {

  @Mock
  private FluidCell fluidCell;

  /// [Monitor#temperature()] のテスト.
  @Test
  void testTemperature() {
    assertThatThrownBy(() -> Monitor.temperature().applyAsDouble(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Monitor.temperature().applyAsDouble(1))
        .isInstanceOf(ClassCastException.class);

    assertThat(Monitor.temperature())
        .hasToString("temperature");

    final double value = 298.15;
    doReturn(value).when(this.fluidCell).getTemperature();
    assertThat(Monitor.temperature().applyAsDouble(this.fluidCell))
        .isEqualTo(value);
  }

  /// [Monitor#pressure()] のテスト.
  @Test
  void testPressure() {
    assertThatThrownBy(() -> Monitor.pressure().applyAsDouble(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Monitor.pressure().applyAsDouble(1))
        .isInstanceOf(ClassCastException.class);

    assertThat(Monitor.pressure())
        .hasToString("pressure");

    final double value = 101325.;
    doReturn(value).when(this.fluidCell).getPressure();
    assertThat(Monitor.pressure().applyAsDouble(this.fluidCell))
        .isEqualTo(value);
  }

  /// [Monitor#density()] のテスト.
  @Test
  void testDensity() {
    assertThatThrownBy(() -> Monitor.density().applyAsDouble(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Monitor.density().applyAsDouble(1))
        .isInstanceOf(ClassCastException.class);

    assertThat(Monitor.density())
        .hasToString("density");

    final double value = 1.2;
    doReturn(value).when(this.fluidCell).getDensity();
    assertThat(Monitor.density().applyAsDouble(this.fluidCell))
        .isEqualTo(value);
  }

  /// [Monitor#specificEnthalpy()] のテスト.
  @Test
  void testSpecificEnthalpy() {
    assertThatThrownBy(() -> Monitor.specificEnthalpy().applyAsDouble(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Monitor.specificEnthalpy().applyAsDouble(1))
        .isInstanceOf(ClassCastException.class);

    assertThat(Monitor.specificEnthalpy())
        .hasToString("specificEnthalpy");

    final double value = 10000.;
    doReturn(value).when(this.fluidCell).getSpecificEnthalpy();
    assertThat(Monitor.specificEnthalpy().applyAsDouble(this.fluidCell))
        .isEqualTo(value);
  }

  /// [Monitor#specificConstantPressureSpecificHeat()] のテスト.
  @Test
  void testSpecificConstantPressureSpecificHeat() {
    assertThatThrownBy(() -> Monitor.specificConstantPressureSpecificHeat().applyAsDouble(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Monitor.specificConstantPressureSpecificHeat().applyAsDouble(1))
        .isInstanceOf(ClassCastException.class);

    assertThat(Monitor.specificConstantPressureSpecificHeat())
        .hasToString("specificConstantPressureSpecificHeat");

    final double value = 1000.;
    doReturn(value).when(this.fluidCell).getSpecificConstantPressureSpecificHeat();
    assertThat(Monitor.specificConstantPressureSpecificHeat().applyAsDouble(this.fluidCell))
        .isEqualTo(value);
  }

  /// [Monitor#viscosity()] のテスト.
  @Test
  void testViscosity() {
    assertThatThrownBy(() -> Monitor.viscosity().applyAsDouble(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Monitor.viscosity().applyAsDouble(1))
        .isInstanceOf(ClassCastException.class);

    assertThat(Monitor.viscosity())
        .hasToString("viscosity");

    final double value = 18e-6;
    doReturn(value).when(this.fluidCell).getViscosity();
    assertThat(Monitor.viscosity().applyAsDouble(this.fluidCell))
        .isEqualTo(value);
  }

  /// [Monitor#thermalConductivity()] のテスト.
  @Test
  void testThermalConductivity() {
    assertThatThrownBy(() -> Monitor.thermalConductivity().applyAsDouble(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Monitor.thermalConductivity().applyAsDouble(1))
        .isInstanceOf(ClassCastException.class);

    assertThat(Monitor.thermalConductivity())
        .hasToString("thermalConductivity");

    final double value = 0.02;
    doReturn(value).when(this.fluidCell).getThermalConductivity();
    assertThat(Monitor.thermalConductivity().applyAsDouble(this.fluidCell))
        .isEqualTo(value);
  }

  @Mock
  private FluidCell.ComponentValue fluidCellValue;

  /// [Monitor#fraction(ComponentKey)] のテスト.
  @Test
  void testFractionComponentKey() {
    assertThatThrownBy(() -> Monitor.fraction(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Monitor.fraction(ComponentKey.NITROGEN).applyAsDouble(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Monitor.fraction(ComponentKey.NITROGEN).applyAsDouble(1))
        .isInstanceOf(ClassCastException.class);

    assertThat(Monitor.fraction(ComponentKey.NITROGEN))
        .hasToString("fraction[" + ComponentKey.NITROGEN + "]");

    final double value = 0.12;
    doReturn(value).when(this.fluidCellValue).getFraction();
    doReturn(Map.of(ComponentKey.NITROGEN, this.fluidCellValue)).when(this.fluidCell).componentMap();

    assertThat(Monitor.fraction(ComponentKey.NITROGEN).applyAsDouble(this.fluidCell))
        .isEqualTo(value);
  }

  /// [Monitor#diffusivity(ComponentKey)] のテスト.
  @Test
  void testDiffusivityComponentKey() {
    assertThatThrownBy(() -> Monitor.diffusivity(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Monitor.diffusivity(ComponentKey.NITROGEN).applyAsDouble(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Monitor.diffusivity(ComponentKey.NITROGEN).applyAsDouble(1))
        .isInstanceOf(ClassCastException.class);

    assertThat(Monitor.diffusivity(ComponentKey.NITROGEN))
        .hasToString("diffusivity[" + ComponentKey.NITROGEN + "]");

    final double value = 5e-4;
    doReturn(value).when(this.fluidCellValue).getDiffusivity();
    doReturn(Map.of(ComponentKey.NITROGEN, this.fluidCellValue)).when(this.fluidCell).componentMap();

    assertThat(Monitor.diffusivity(ComponentKey.NITROGEN).applyAsDouble(this.fluidCell))
        .isEqualTo(value);
  }

  @Mock
  private ShellCell shellCell;

  /// [Monitor#shellTemperature()] のテスト.
  @Test
  void testShellTemperature() {
    assertThatThrownBy(() -> Monitor.shellTemperature().applyAsDouble(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Monitor.shellTemperature().applyAsDouble(1))
        .isInstanceOf(ClassCastException.class);

    assertThat(Monitor.shellTemperature())
        .hasToString("shellTemperature");

    final double value = 303.15;
    doReturn(value).when(this.shellCell).getShellTemperature();
    assertThat(Monitor.shellTemperature().applyAsDouble(this.shellCell))
        .isEqualTo(value);
  }

  /// [Monitor#shellSpecificHeat()] のテスト.
  @Test
  void testShellSpecificHeat() {
    assertThatThrownBy(() -> Monitor.shellSpecificHeat().applyAsDouble(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Monitor.shellSpecificHeat().applyAsDouble(1))
        .isInstanceOf(ClassCastException.class);

    assertThat(Monitor.shellSpecificHeat())
        .hasToString("shellSpecificHeat");

    final double value = 640.;
    doReturn(value).when(this.shellCell).getShellSpecificHeat();
    assertThat(Monitor.shellSpecificHeat().applyAsDouble(this.shellCell))
        .isEqualTo(value);
  }

  /// [Monitor#shellThermalConductivity()] のテスト.
  @Test
  void testShellThermalConductivity() {
    assertThatThrownBy(() -> Monitor.shellThermalConductivity().applyAsDouble(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Monitor.shellThermalConductivity().applyAsDouble(1))
        .isInstanceOf(ClassCastException.class);

    assertThat(Monitor.shellThermalConductivity())
        .hasToString("shellThermalConductivity");

    final double value = 0.2;
    doReturn(value).when(this.shellCell).getShellThermalConductivity();
    assertThat(Monitor.shellThermalConductivity().applyAsDouble(this.shellCell))
        .isEqualTo(value);
  }
  
  @Mock
  private AdsorbentCell adsorbentCell;

  /// [Monitor#adsorbentSpecificHeat()] のテスト.
  @Test
  void testAdsorbentSpecificHeat() {
    assertThatThrownBy(() -> Monitor.adsorbentSpecificHeat().applyAsDouble(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Monitor.adsorbentSpecificHeat().applyAsDouble(1))
        .isInstanceOf(ClassCastException.class);

    assertThat(Monitor.adsorbentSpecificHeat())
        .hasToString("adsorbentSpecificHeat");

    final double value = 640.;
    doReturn(value).when(this.adsorbentCell).getAdsorbentSpecificHeat();
    assertThat(Monitor.adsorbentSpecificHeat().applyAsDouble(this.adsorbentCell))
        .isEqualTo(value);
  }
  
  @Mock
  private AdsorbentCell.ComponentValue adsorbentCellValue;

  /// [Monitor#molarQuantity(ComponentKey)] のテスト.
  @Test
  void testMolarQuantityComponentKey() {
    assertThatThrownBy(() -> Monitor.molarQuantity(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Monitor.molarQuantity(ComponentKey.NITROGEN).applyAsDouble(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Monitor.molarQuantity(ComponentKey.NITROGEN).applyAsDouble(1))
        .isInstanceOf(ClassCastException.class);

    assertThat(Monitor.molarQuantity(ComponentKey.NITROGEN))
        .hasToString("molarQuantity[" + ComponentKey.NITROGEN + "]");

    final double value = 10.;
    doReturn(value).when(this.adsorbentCellValue).getMolarQuantity();
    doReturn(Map.of(ComponentKey.NITROGEN, this.adsorbentCellValue))
        .when(this.adsorbentCell)
        .componentMap();

    assertThat(Monitor.molarQuantity(ComponentKey.NITROGEN).applyAsDouble(this.adsorbentCell))
        .isEqualTo(value);
  }

  @Mock
  private FluidFace fluidFace;

  /// [Monitor#massFlow()] のテスト.
  @Test
  void testMassFlow() {
    assertThatThrownBy(() -> Monitor.massFlow().applyAsDouble(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Monitor.massFlow().applyAsDouble(1))
        .isInstanceOf(ClassCastException.class);

    assertThat(Monitor.massFlow()).hasToString("massFlow");

    final double value = 1.2;
    doReturn(value).when(this.fluidFace).getMassFlow();
    assertThat(Monitor.massFlow().applyAsDouble(this.fluidFace)).isEqualTo(value);
  }

  /// [Monitor#energyFlow()] のテスト.
  @Test
  void testEnergyFlow() {
    assertThatThrownBy(() -> Monitor.energyFlow().applyAsDouble(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Monitor.energyFlow().applyAsDouble(1))
        .isInstanceOf(ClassCastException.class);

    assertThat(Monitor.energyFlow()).hasToString("energyFlow");

    final double value = 2.3;
    doReturn(value).when(this.fluidFace).getEnergyFlow();
    assertThat(Monitor.energyFlow().applyAsDouble(this.fluidFace)).isEqualTo(value);
  }

  @Mock
  private FluidFace.ComponentValue fluidFaceValue;

  /// [Monitor#speciesFlow(ComponentKey)] のテスト.
  @Test
  void testSpeciesFlowComponentKey() {
    assertThatThrownBy(() -> Monitor.speciesFlow(null)).isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Monitor.speciesFlow(ComponentKey.OXYGEN).applyAsDouble(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Monitor.speciesFlow(ComponentKey.OXYGEN).applyAsDouble(1))
        .isInstanceOf(ClassCastException.class);

    assertThat(Monitor.speciesFlow(ComponentKey.OXYGEN))
        .hasToString("speciesFlow[" + ComponentKey.OXYGEN + "]");

    final double value = 1.23;
    doReturn(value).when(this.fluidFaceValue).getSpeciesFlow();
    doReturn(Map.of(ComponentKey.OXYGEN, this.fluidFaceValue)).when(this.fluidFace).componentMap();

    assertThat(Monitor.speciesFlow(ComponentKey.OXYGEN).applyAsDouble(this.fluidFace))
        .isEqualTo(value);
  }

}
