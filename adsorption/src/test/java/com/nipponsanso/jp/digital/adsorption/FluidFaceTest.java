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

/// [FluidFace] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class FluidFaceTest {

  @Mock
  private Composition composition;

  /// [FluidFace#getMassFlow()] と [FluidFace#setMassFlow(double)] のテスト.
  @Test
  void testMassFlow() {
    final Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    FluidFace face = FluidFace.newInstance(this.composition);

    final double massFlow = 1.2;
    face.setMassFlow(massFlow);
    assertThat(face.getMassFlow()).isEqualTo(massFlow);
  }

  /// [FluidFace#getEnergyFlow()] と [FluidFace#setEnergyFlow(double)] のテスト.
  @Test
  void testEnergyFlow() {
    final Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    FluidFace face = FluidFace.newInstance(this.composition);

    final double energyFlow = 2.3;
    face.setEnergyFlow(energyFlow);
    assertThat(face.getEnergyFlow()).isEqualTo(energyFlow);
  }

  /// [FluidFace#componentMap()] のテスト.
  @Test
  void testComponentMap() {
    final Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    FluidFace face = FluidFace.newInstance(this.composition);

    assertThat(face.componentMap().keySet()).isEqualTo(componentKeySet);
    face.componentMap().values().forEach(o -> assertThat(o).isNotNull());
  }

  /// [FluidFace.ComponentValue] のテスト.
  @Nested
  class ComponentValueTest {

    @Mock
    private Composition composition;

    /// [FluidFace.ComponentValue#getSpeciesFlow()] と
    /// [FluidFace.ComponentValue#setSpeciesFlow(double)] のテスト.
    @Test
    void testSpeciesFlow() {
      final Set<ComponentKey> componentKeySet
          = Set.of(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
      doReturn(componentKeySet).when(this.composition).componentKeySet();

      FluidFace.ComponentValue componentValue = FluidFace.newInstance(this.composition)
          .componentMap().get(ComponentKey.NITROGEN);

      final double speciesFlow = 3.4;
      componentValue.setSpeciesFlow(speciesFlow);
      assertThat(componentValue.getSpeciesFlow()).isEqualTo(speciesFlow);
    }

  } // ComponentValue

}
