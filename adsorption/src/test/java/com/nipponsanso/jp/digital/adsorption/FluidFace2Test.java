/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [FluidFace2] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class FluidFace2Test {

  @Mock
  private Composition composition;

  /// [FluidFace2#getMassFlow()] と [FluidFace2#setMassFlow(double)] のテスト.
  @Test
  void testMassFlow() {
    final Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    FluidFace2 face = FluidFace2.newInstance(this.composition);

    final double massFlow = 1.2;
    face.setMassFlow(massFlow);
    assertThat(face.getMassFlow()).isEqualTo(massFlow);
  }

  /// [FluidFace2#getEnergyFlow()] と [FluidFace2#setEnergyFlow(double)] のテスト.
  @Test
  void testEnergyFlow() {
    final Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    FluidFace2 face = FluidFace2.newInstance(this.composition);

    final double energyFlow = 2.3;
    face.setEnergyFlow(energyFlow);
    assertThat(face.getEnergyFlow()).isEqualTo(energyFlow);
  }

  /// [FluidFace2#componentMap()] のテスト.
  @Test
  void testComponentMap() {
    final Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    FluidFace2 face = FluidFace2.newInstance(this.composition);

    assertThat(face.componentMap().keySet()).isEqualTo(componentKeySet);
    face.componentMap().values().forEach(o -> assertThat(o).isNotNull());
  }

  /// [FluidFace2.ComponentValue] のテスト.
  @Nested
  class ComponentValueTest {

    @Mock
    private Composition composition;

    /// [FluidFace2.ComponentValue#getSpeciesFlow()] と
    /// [FluidFace2.ComponentValue#setSpeciesFlow(double)] のテスト.
    @Test
    void testSpeciesFlow() {
      final Set<ComponentKey> componentKeySet
          = Set.of(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
      doReturn(componentKeySet).when(this.composition).componentKeySet();

      FluidFace2.ComponentValue componentValue = FluidFace2.newInstance(this.composition)
          .componentMap().get(ComponentKey.NITROGEN);

      final double speciesFlow = 3.4;
      componentValue.setSpeciesFlow(speciesFlow);
      assertThat(componentValue.getSpeciesFlow()).isEqualTo(speciesFlow);
    }

  } // ComponentValue

}
