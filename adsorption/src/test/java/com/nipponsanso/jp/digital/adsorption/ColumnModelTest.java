/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [ColumnModel] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class ColumnModelTest {

  @Mock
  private ColumnShape shape;

  @Mock
  private ColumnSpace space0;

  @Mock
  private ColumnSpace space1;

  @Mock
  private ColumnSpace space2;

  @Mock
  private ColumnZone zone0;

  @Mock
  private ColumnZone zone1;

  @Mock
  private ColumnZone zone2;

  @Mock
  private Section section0;

  @Mock
  private Section section1;

  @Mock
  private Section section2;

  @Mock
  private Adsorbent adsorbent0;

  @Mock
  private Adsorbent adsorbent1;

  @Mock
  private Adsorbent adsorbent2;

  @Mock
  private Composition composition;

  @Mock
  private Shell shell;

  @Mock
  private Column column;

  /// [ColumnModel] の格子情報をテスト.
  @SuppressWarnings("SequencedCollectionMethodCanBeUsed")
  @Test
  void testGrid() {
    doAnswer(
        invocation -> 2.3 * (invocation.getArgument(1, Double.class) - invocation.getArgument(0, Double.class)))
        .when(this.shape).fluidVolume(anyDouble(), anyDouble());
    doAnswer(
        invocation -> 1.2 * (invocation.getArgument(1, Double.class) - invocation.getArgument(0, Double.class)))
        .when(this.shape).shellVolume(anyDouble(), anyDouble());
    doAnswer(
        invocation -> 0.2 * (invocation.getArgument(1, Double.class) - invocation.getArgument(0, Double.class)))
        .when(this.shape).insideArea(anyDouble(), anyDouble());
    doAnswer(
        invocation -> 0.3 * (invocation.getArgument(1, Double.class) - invocation.getArgument(0, Double.class)))
        .when(this.shape).outsideArea(anyDouble(), anyDouble());

    doReturn(0.3).when(this.space0).length();
    doReturn(0.2).when(this.space1).length();
    doReturn(0.4).when(this.space2).length();

    doReturn(new double[]{0.3, 0.3}).when(this.section0).segments();
    doReturn(2).when(this.section0).number();
    doReturn(new double[]{0.1, 0.1, 0.1}).when(this.section1).segments();
    doReturn(3).when(this.section1).number();
    doReturn(new double[]{0.2, 0.2, 0.2, 0.2}).when(this.section2).segments();
    doReturn(4).when(this.section2).number();

    doReturn(this.section0).when(this.zone0).section();
    doReturn(this.section1).when(this.zone1).section();
    doReturn(this.section2).when(this.zone2).section();

    doReturn(this.adsorbent0).when(this.zone0).adsorbent();
    doReturn(this.adsorbent1).when(this.zone1).adsorbent();
    doReturn(this.adsorbent2).when(this.zone2).adsorbent();

    final double origin = 0.2;
    doReturn(this.shape).when(this.column).shape();
    doReturn(origin).when(this.column).origin();
    doReturn(List.of(this.space0, this.space1, this.space2)).when(this.column).spaceList();
    doReturn(List.of(List.of(this.zone0, this.zone1), List.of(this.zone2)))
        .when(this.column)
        .zoneListList();
    doReturn(this.shell).when(this.column).shell();

    final Set<ComponentKey> componentKeySet = Sets.immutableEnumSet(
        ComponentKey.ARGON,
        ComponentKey.OXYGEN,
        ComponentKey.NITROGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    ColumnModel model = new ColumnModel(this.column, this.composition);

    List<ColumnCell> cellList = model.cellList();
    assertThat(cellList.size()).isEqualTo(14);

    assertThat(model.westCell()).isEqualTo(cellList.get(0));
    assertThat(model.spaceCellList().get(0)).isEqualTo(cellList.get(1));
    assertThat(model.zoneCellListListList().get(0).get(0).get(0)).isEqualTo(cellList.get(2));
    assertThat(model.zoneCellListListList().get(0).get(0).get(1)).isEqualTo(cellList.get(3));
    assertThat(model.zoneCellListListList().get(0).get(1).get(0)).isEqualTo(cellList.get(4));
    assertThat(model.zoneCellListListList().get(0).get(1).get(1)).isEqualTo(cellList.get(5));
    assertThat(model.zoneCellListListList().get(0).get(1).get(2)).isEqualTo(cellList.get(6));
    assertThat(model.spaceCellList().get(1)).isEqualTo(cellList.get(7));
    assertThat(model.zoneCellListListList().get(1).get(0).get(0)).isEqualTo(cellList.get(8));
    assertThat(model.zoneCellListListList().get(1).get(0).get(1)).isEqualTo(cellList.get(9));
    assertThat(model.zoneCellListListList().get(1).get(0).get(2)).isEqualTo(cellList.get(10));
    assertThat(model.zoneCellListListList().get(1).get(0).get(3)).isEqualTo(cellList.get(11));
    assertThat(model.spaceCellList().get(2)).isEqualTo(cellList.get(12));
    assertThat(model.eastCell()).isEqualTo(cellList.get(13));

    assertThat(cellList.get(0).centroid()).isCloseTo(0.20, within(1e-6)); // west
    assertThat(cellList.get(1).centroid()).isCloseTo(0.35, within(1e-6)); // space0
    assertThat(cellList.get(2).centroid()).isCloseTo(0.65, within(1e-6)); // zone0
    assertThat(cellList.get(3).centroid()).isCloseTo(0.95, within(1e-6)); // zone0
    assertThat(cellList.get(4).centroid()).isCloseTo(1.15, within(1e-6)); // zone1
    assertThat(cellList.get(5).centroid()).isCloseTo(1.25, within(1e-6)); // zone1
    assertThat(cellList.get(6).centroid()).isCloseTo(1.35, within(1e-6)); // zone1
    assertThat(cellList.get(7).centroid()).isCloseTo(1.50, within(1e-6)); // space1
    assertThat(cellList.get(8).centroid()).isCloseTo(1.70, within(1e-6)); // zone2
    assertThat(cellList.get(9).centroid()).isCloseTo(1.90, within(1e-6)); // zone2
    assertThat(cellList.get(10).centroid()).isCloseTo(2.10, within(1e-6)); // zone2
    assertThat(cellList.get(11).centroid()).isCloseTo(2.30, within(1e-6)); // zone2
    assertThat(cellList.get(12).centroid()).isCloseTo(2.60, within(1e-6)); // space2
    assertThat(cellList.get(13).centroid()).isCloseTo(2.80, within(1e-6)); // east

    List<ColumnFace> faceList = model.faceList();
    assertThat(faceList.size()).isEqualTo(13);

    assertThat(faceList.get(0).coordinate()).isCloseTo(0.20, within(1e-6));
    assertThat(faceList.get(1).coordinate()).isCloseTo(0.50, within(1e-6));
    assertThat(faceList.get(2).coordinate()).isCloseTo(0.80, within(1e-6));
    assertThat(faceList.get(3).coordinate()).isCloseTo(1.10, within(1e-6));
    assertThat(faceList.get(4).coordinate()).isCloseTo(1.20, within(1e-6));
    assertThat(faceList.get(5).coordinate()).isCloseTo(1.30, within(1e-6));
    assertThat(faceList.get(6).coordinate()).isCloseTo(1.40, within(1e-6));
    assertThat(faceList.get(7).coordinate()).isCloseTo(1.60, within(1e-6));
    assertThat(faceList.get(8).coordinate()).isCloseTo(1.80, within(1e-6));
    assertThat(faceList.get(9).coordinate()).isCloseTo(2.00, within(1e-6));
    assertThat(faceList.get(10).coordinate()).isCloseTo(2.20, within(1e-6));
    assertThat(faceList.get(11).coordinate()).isCloseTo(2.40, within(1e-6));
    assertThat(faceList.get(12).coordinate()).isCloseTo(2.80, within(1e-6));
  }

}
