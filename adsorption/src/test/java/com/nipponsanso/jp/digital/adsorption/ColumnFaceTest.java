/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doReturn;

import com.google.common.collect.Sets;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [ColumnFace] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class ColumnFaceTest {

  private static final class Environment {

    private final double[] doubleArray;

    private Environment(Composition composition) {
      this.doubleArray = new double[ColumnFace.size(composition)];
    }

    private final class LocalFace extends ColumnFace {

      private LocalFace(int offset, ColumnShape shape, double coordinate, Composition composition) {
        super(offset, shape, coordinate, composition);
      }

      @Override
      double[] doubleArray() {
        return Environment.this.doubleArray;
      }

    } // LocalFace

  } // Environment

  @Mock
  private ColumnShape shape;

  @Mock
  private Composition composition;

  @Test
  void testGeometry() {
    final double coordinate = 4.1;
    final double fluidArea = 3.2;
    doReturn(fluidArea).when(this.shape).fluidArea(anyDouble());
    final double shellArea = 1.2;
    doReturn(shellArea).when(this.shape).shellArea(anyDouble());

    Set<ComponentKey> componentKeySet = Sets.immutableEnumSet(
        ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    Environment.LocalFace face = new Environment(this.composition)
        .new LocalFace(0, this.shape, coordinate, this.composition);

    assertThat(face.coordinate()).isEqualTo(coordinate);
    assertThat(face.fluidArea()).isEqualTo(fluidArea);
    assertThat(face.shellArea()).isEqualTo(shellArea);
  }

  @Test
  void testVariable() {
    final double coordinate = 4.1;
    final double fluidArea = 3.2;
    doReturn(fluidArea).when(this.shape).fluidArea(anyDouble());
    final double shellArea = 1.2;
    doReturn(shellArea).when(this.shape).shellArea(anyDouble());

    Set<ComponentKey> componentKeySet = Sets.immutableEnumSet(
        ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    Environment.LocalFace face = new Environment(this.composition)
        .new LocalFace(0, this.shape, coordinate, this.composition);

    final double massFlow = 3.2;
    face.setMassFlow(massFlow);
    assertThat(face.getMassFlow()).isEqualTo(massFlow);

    final double speciesFlow = 2.5;
    face.componentMap().get(ComponentKey.OXYGEN).setSpeciesFlow(speciesFlow);
    assertThat(face.componentMap().get(ComponentKey.OXYGEN).getSpeciesFlow())
        .isEqualTo(speciesFlow);
  }

}
