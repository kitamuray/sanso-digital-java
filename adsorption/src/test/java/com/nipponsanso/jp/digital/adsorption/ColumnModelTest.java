/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
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
/// @since 1.1
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class ColumnModelTest {

  @Mock
  private Composition composition;

  @Mock
  private Column column;

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
  private Shell shell;

  // space0 - zone0 - space1 - zone1 - zone2 - space2

  @Test
  void testGeometry() {
    doReturn(this.section0).when(this.zone0).section();
    doReturn(this.section1).when(this.zone1).section();
    doReturn(this.section2).when(this.zone2).section();
    doReturn(this.adsorbent0).when(this.zone0).adsorbent();
    doReturn(this.adsorbent1).when(this.zone1).adsorbent();
    doReturn(this.adsorbent2).when(this.zone2).adsorbent();
    doReturn(this.shape).when(this.column).shape();
    doReturn(List.of(this.space0, this.space1, this.space2)).when(this.column).spaceList();
    doReturn(List.of(List.of(this.zone0), List.of(this.zone1, this.zone2)))
        .when(this.column)
        .zoneListList();
    doReturn(this.shell).when(this.column).shell();

    doReturn(new double[]{2., 2.}).when(this.section0).segments();
    doReturn(new double[]{1., 1., 1.}).when(this.section1).segments();
    doReturn(new double[]{5.}).when(this.section2).segments();
    doReturn(2).when(this.section0).number();
    doReturn(3).when(this.section1).number();
    doReturn(1).when(this.section2).number();

    doReturn(2.).when(this.space0).length();
    doReturn(3.).when(this.space1).length();
    doReturn(2.).when(this.space2).length();

    doReturn(2.).when(this.column).origin();

    Set<ComponentKey> componentKeySet = Sets.immutableEnumSet(
        ComponentKey.ARGON, ComponentKey.NITROGEN, ComponentKey.OXYGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    ColumnModel model = new ColumnModel(this.column, this.composition);

    assertThat(model.faceList().get(0).coordinate()).isEqualTo(2.);
    assertThat(model.faceList().get(1).coordinate()).isEqualTo(4.);
    assertThat(model.faceList().get(2).coordinate()).isEqualTo(6.);
    assertThat(model.faceList().get(3).coordinate()).isEqualTo(8.);
    assertThat(model.faceList().get(4).coordinate()).isEqualTo(11.);
    assertThat(model.faceList().get(5).coordinate()).isEqualTo(12.);
    assertThat(model.faceList().get(6).coordinate()).isEqualTo(13.);
    assertThat(model.faceList().get(7).coordinate()).isEqualTo(14.);
    assertThat(model.faceList().get(8).coordinate()).isEqualTo(19.);
    assertThat(model.faceList().get(9).coordinate()).isEqualTo(21.);
    assertThat(model.faceList().size()).isEqualTo(10);

    assertThat(model.cellList().get(0).centroid()).isEqualTo(2.); // west
    assertThat(model.cellList().get(1).centroid()).isEqualTo(3.); // space0
    assertThat(model.cellList().get(2).centroid()).isEqualTo(5.); // zone0
    assertThat(model.cellList().get(3).centroid()).isEqualTo(7.); // zone0
    assertThat(model.cellList().get(4).centroid()).isEqualTo(9.5); // space1
    assertThat(model.cellList().get(5).centroid()).isEqualTo(11.5); // zone1
    assertThat(model.cellList().get(6).centroid()).isEqualTo(12.5); // zone1
    assertThat(model.cellList().get(7).centroid()).isEqualTo(13.5); // zone1
    assertThat(model.cellList().get(8).centroid()).isEqualTo(16.5); // zone2
    assertThat(model.cellList().get(9).centroid()).isEqualTo(20.); // space2
    assertThat(model.cellList().get(10).centroid()).isEqualTo(21.); // east
    assertThat(model.cellList().size()).isEqualTo(11);

    assertThat(model.westCell()).isEqualTo(model.cellList().get(0));
    assertThat(model.eastCell()).isEqualTo(model.cellList().get(10));
    assertThat(model.spaceCellList())
        .isEqualTo(
            List.of(model.cellList().get(1), model.cellList().get(4), model.cellList().get(9)));
    assertThat(model.zoneCellListListList())
        .isEqualTo(
            List.of(
                List.of(
                  List.of(model.cellList().get(2), model.cellList().get(3))),
                List.of(
                    List.of(model.cellList().get(5), model.cellList().get(6), model.cellList().get(7)),
                    List.of(model.cellList().get(8)))));

    assertThat(model.spaceModelList().get(0).cell()).isEqualTo(model.cellList().get(1));
    assertThat(model.spaceModelList().get(1).cell()).isEqualTo(model.cellList().get(4));
    assertThat(model.spaceModelList().get(2).cell()).isEqualTo(model.cellList().get(9));

    //noinspection SequencedCollectionMethodCanBeUsed
    assertThat(model.zoneModelListList().get(0).get(0).cellList())
        .isEqualTo(List.of(model.cellList().get(2), model.cellList().get(3)));
    assertThat(model.zoneModelListList().get(1).get(0).cellList())
        .isEqualTo(List.of(model.cellList().get(5), model.cellList().get(6), model.cellList().get(7)));
    assertThat(model.zoneModelListList().get(1).get(1).cellList())
        .isEqualTo(List.of(model.cellList().get(8)));
  }

}
