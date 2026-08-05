/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * {@link ThermodynamicFunction} のテスト.
 *
 * @since 1.0
 * @author KITAMURA Yuichiro
 */
@ExtendWith(MockitoExtension.class)
class ThermodynamicFunctionTest {

  @Mock
  private ThermodynamicFunction thermodynamicFunction;

  /**
   * {@link ThermodynamicFunction#molarSpecificEntropy(double)} のテスト.
   */
  @Test
  void testMolarSpecificEntropyDouble() {
    doCallRealMethod().when(this.thermodynamicFunction).molarSpecificEntropy(anyDouble());

    assertThatThrownBy(() -> this.thermodynamicFunction.molarSpecificEntropy(300.))
        .isInstanceOf(UnsupportedOperationException.class);
  }

  /**
   * {@link ThermodynamicFunction#molarSpecificGibbsEnergy(double)} のテスト.
   */
  @Test
  void testMolarSpecificGibbsEnergyDouble() {
    final double temperature = 300.;
    final double molarSpecificEnthalpy = 8723.9; // N2, 0.1MPa, 300K (NIST REFPROP)
    final double molarSpecificEntropy = 191.79;
    final double molarSpecificGibbsEnergy = -48813.;

    doReturn(molarSpecificEnthalpy)
        .when(this.thermodynamicFunction)
        .molarSpecificEnthalpy(temperature);
    doReturn(molarSpecificEntropy)
        .when(this.thermodynamicFunction)
        .molarSpecificEntropy(temperature);
    doCallRealMethod()
        .when(this.thermodynamicFunction)
        .molarSpecificGibbsEnergy(temperature);

    assertThat(this.thermodynamicFunction.molarSpecificGibbsEnergy(temperature))
        .isCloseTo(molarSpecificGibbsEnergy, within(1.));
  }

}
