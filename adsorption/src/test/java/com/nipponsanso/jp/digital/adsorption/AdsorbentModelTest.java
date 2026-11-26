/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doReturn;

import com.nipponsanso.jp.digital.PhysicalConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [AdsorbentModel] のテスト.
///
/// @since 1.1
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class AdsorbentModelTest {

  @Mock
  private Adsorbent adsorbent;

  @Mock
  private SpecificHeat specificHeat;

  @Mock
  private AdsorbentCell cell;

  /// [AdsorbentModel#adsorbentSpecificHeat(AdsorbentCell)] のテスト.
  @Test
  void testAdsorbentSpecificHeatAdsorbentCell() {
    doReturn(800.).when(this.specificHeat).specificHeat(anyDouble());
    doReturn(this.specificHeat).when(this.adsorbent).specificHeat();

    AdsorbentModel model = new AdsorbentModel(this.adsorbent);

    doReturn(298.15).when(this.cell).getTemperature();

    assertThat(model.adsorbentSpecificHeat(this.cell)).isEqualTo(800.);
  }

  /// [AdsorbentModel#molarSpecificConstantVolumeSpecificHeat(AdsorbentCell , ComponentKey)]
  /// のテスト.
  @Test
  void testMolarSpecificConstantVolumeSpecificHeatAdsorbentCellComponentKey() {
    final double temperature = 298.15;
    final double molarSpecificConstantPressureSpecificHeat
        = ComponentKey.NITROGEN.thermodynamicFunction()
        .molarSpecificConstantPressureSpecificHeat(temperature);

    doReturn(temperature).when(this.cell).getTemperature();

    AdsorbentModel model = new AdsorbentModel(this.adsorbent);

    assertThat(model.molarSpecificConstantVolumeSpecificHeat(this.cell, ComponentKey.NITROGEN))
        .isEqualTo(
            molarSpecificConstantPressureSpecificHeat - PhysicalConstants.MOLAR_GAS_CONSTANT);
  }

}
