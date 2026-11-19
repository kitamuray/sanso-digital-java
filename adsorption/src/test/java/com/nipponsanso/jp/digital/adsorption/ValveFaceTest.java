/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import com.google.common.collect.Sets;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [ValveFace] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class ValveFaceTest {

  @Mock
  private Composition composition;

  @Test
  void test() {
    Set<ComponentKey> componentKeySet = Sets.immutableEnumSet(
        ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    ValveFace face = new ValveFace(this.composition);

    final double massFlow = 1.2;
    face.setMassFlow(massFlow);
    assertThat(face.getMassFlow()).isEqualTo(massFlow);

    final double speciesFlow = 2.3;
    face.componentMap().get(ComponentKey.OXYGEN).setSpeciesFlow(speciesFlow);
    assertThat(face.componentMap().get(ComponentKey.OXYGEN).getSpeciesFlow())
        .isEqualTo(speciesFlow);
  }

}
