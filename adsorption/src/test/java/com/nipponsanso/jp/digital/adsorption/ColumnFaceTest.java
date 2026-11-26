/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doReturn;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [ColumnFace] のテスト.
///
/// @since 1.1
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class ColumnFaceTest {

  @SuppressWarnings("ClassCanBeRecord")
  private static final class LocalModel {

    private final double[] doubleArray;

    private LocalModel(Composition composition) {
      this.doubleArray = new double[ColumnFace.number(composition)];
    }

    private final class LocalFace extends ColumnFace {

      LocalFace(Composition composition, ColumnShape shape, double coordinate) {
        super(0, shape, coordinate, composition);
      }

      @Override
      double[] doubleArray() {
        return LocalModel.this.doubleArray;
      }

    } // LocalFace

  } // LocalModel

  @Mock
  private Composition composition;

  @Mock
  private ColumnShape shape;

  /// [ColumnFace] のデータ構造をテスト.
  @Test
  void testStructure() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    ColumnFace face = new LocalModel(this.composition)
        .new LocalFace(this.composition, this.shape, 0.);

    assertThat(face.componentMap().keySet()).isEqualTo(componentKeySet);
    face.componentMap().values()
        .forEach(o -> assertThat(o).isInstanceOf(ColumnFace.ComponentValue.class));
  }

  /// [ColumnFace] の幾何情報メソッドをテスト.
  @Test
  void testGeometry() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    final double coordinate = 1.2;
    final double fluidArea = 2.3;
    final double shellArea = 1.2;
    doReturn(fluidArea).when(this.shape).fluidArea(anyDouble());
    doReturn(shellArea).when(this.shape).shellArea(anyDouble());

    ColumnFace face = new LocalModel(this.composition)
        .new LocalFace(this.composition, this.shape, coordinate);

    assertThat(face.coordinate()).isEqualTo(coordinate);
    assertThat(face.fluidArea()).isEqualTo(fluidArea);
    assertThat(face.shellArea()).isEqualTo(shellArea);
  }

  /// [ColumnFace#getMassFlow()] と [ColumnFace#setMassFlow(double)] のテスト.
  @Test
  void testMassFlow() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    ColumnFace face = new LocalModel(this.composition)
        .new LocalFace(this.composition, this.shape, 0.);

    final double massFlow = 1.2;
    face.setMassFlow(massFlow);
    assertThat(face.getMassFlow()).isEqualTo(massFlow);
  }

  /// [ColumnFace#getEnergyFlow()] と [ColumnFace#setEnergyFlow(double)] のテスト.
  @Test
  void testEnergyFlow() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    ColumnFace face = new LocalModel(this.composition)
        .new LocalFace(this.composition, this.shape, 0.);

    final double energyFlow = 1.2;
    face.setEnergyFlow(energyFlow);
    assertThat(face.getEnergyFlow()).isEqualTo(energyFlow);
  }

  /// [ColumnFace.ComponentValue#getSpeciesFlow()] と
  /// [ColumnFace.ComponentValue#setSpeciesFlow(double)] のテスト.
  @Test
  void testSpeciesFlow() {
    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    ColumnFace face = new LocalModel(this.composition)
        .new LocalFace(this.composition, this.shape, 0.);

    final double speciesFlow = 1.2;
    face.componentMap().get(ComponentKey.OXYGEN).setSpeciesFlow(speciesFlow);
    assertThat(face.componentMap().get(ComponentKey.OXYGEN).getSpeciesFlow())
        .isEqualTo(speciesFlow);
  }

}
