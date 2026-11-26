/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [ShellModel] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class ShellModelTest {

  @Mock
  private Shell shell;

  @Mock
  private SpecificHeat specificHeat;

  @Mock
  private ThermalConductivity thermalConductivity;

  @Mock
  private ShellCell2 cell;

  /// [ShellModel#shellSpecificHeat(ShellCell2)] のテスト.
  @Test
  void testShellSpecificHeatShellCell() {
    doReturn(640.).when(this.specificHeat).specificHeat(anyDouble());
    doReturn(this.specificHeat).when(this.shell).specificHeat();

    ShellModel model = new ShellModel(this.shell);

    doReturn(298.15).when(this.cell).getShellTemperature();

    assertThat(model.shellSpecificHeat(this.cell)).isEqualTo(640.);
  }

  /// [ShellModel#shellSpecificHeat(ShellCell2)] のテスト.
  @Test
  void testShellThermalConductivityShellCell() {
    doReturn(0.2).when(this.thermalConductivity).thermalConductivity(anyDouble());
    doReturn(this.thermalConductivity).when(this.shell).thermalConductivity();

    ShellModel model = new ShellModel(this.shell);

    doReturn(298.15).when(this.cell).getShellTemperature();

    assertThat(model.shellThermalConductivity(this.cell)).isEqualTo(0.2);
  }

}
