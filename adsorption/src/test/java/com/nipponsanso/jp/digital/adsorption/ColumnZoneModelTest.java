/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [ColumnZoneModel] のテスト.
///
/// @since 1.1
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class ColumnZoneModelTest {

  @Mock
  private ColumnZone zone;

  @Mock
  private ColumnCell2 cell0;

  @Mock
  private ColumnCell2 cell1;

  @Mock
  private ColumnCell2 cell2;

  @Mock
  private ColumnCell2 cell3;

  @Mock
  private ColumnCell2 cell4;

  /// [ColumnZoneModel#cellList()] のテスト.
  @Test
  void testCellList() {
    final List<ColumnCell2> cellList = List.of(
        this.cell0, this.cell1, this.cell2, this.cell3, this.cell4);

    ColumnZoneModel model = new ColumnZoneModel(this.zone, cellList);

    assertThat(model.cellList()).isEqualTo(cellList);
  }

  /// [ColumnZoneModel#historyListMap()],
  /// [ColumnZoneModel#addHistories()],
  /// [ColumnZoneModel#clearHistories()] のテスト.
  @Test
  void testHistories() {
    doReturn(
        Map.of(
            "temperature", new IndexMonitor(1, Monitor.temperature()),
            "pressure",    new IndexMonitor(3, Monitor.pressure())))
        .when(this.zone)
        .indexMonitorMap();

    ColumnZoneModel model = new ColumnZoneModel(
        this.zone,
        List.of(this.cell0, this.cell1, this.cell2, this.cell3, this.cell4));

    assertThat(model.historyListMap().keySet()).isEqualTo(Set.of("temperature", "pressure"));
    model.historyListMap().values().forEach(valueList -> assertThat(valueList).isEmpty());

    doReturn(298.15).when(this.cell1).getTemperature();
    doReturn(101325.).when(this.cell3).getPressure();

    model.addHistories();

    assertThat(model.historyListMap().get("temperature")).isEqualTo(List.of(298.15));
    assertThat(model.historyListMap().get("pressure")).isEqualTo(List.of(101325.));

    doReturn(303.15).when(this.cell1).getTemperature();
    doReturn(111325.).when(this.cell3).getPressure();

    model.addHistories();

    assertThat(model.historyListMap().get("temperature")).isEqualTo(List.of(298.15, 303.15));
    assertThat(model.historyListMap().get("pressure")).isEqualTo(List.of(101325., 111325.));

    model.clearHistories();

    model.historyListMap().values().forEach(valueList -> assertThat(valueList).isEmpty());
  }

}
