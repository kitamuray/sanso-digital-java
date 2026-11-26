/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [FluidCell] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class FluidCellTest {

  @Mock
  private Composition composition;

  /// [FluidCell#newInstance(Composition)] のテスト.
  @Test
  void testNewInstanceComposition() {
    final Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    FluidCell cell = FluidCell.newInstance(this.composition);

    assertThat(cell.previous()).isInstanceOf(FluidCell.Previous.class);
    assertThat(cell.componentMap().keySet()).isEqualTo(componentKeySet);
    cell.componentMap().values().forEach(componentValue -> {
      assertThat(componentValue).isInstanceOf(FluidCell.ComponentValue.class);
      assertThat(componentValue.previous()).isInstanceOf(FluidCell.ComponentValue.Previous.class);
    });
  }

}
