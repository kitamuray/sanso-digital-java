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

/// [ColumnSpaceModel] のテスト.
///
/// @since 1.1
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class ColumnSpaceModelTest {

  @Mock
  private ColumnSpace space;

  @Mock
  private ColumnCell cell;

  /// [ColumnSpaceModel#cell()] のテスト.
  @Test
  void testCell() {
    ColumnSpaceModel model = new ColumnSpaceModel(this.space, this.cell);

    assertThat(model.cell()).isEqualTo(this.cell);
  }

  /// [ColumnSpaceModel#historyListMap()],
  /// [ColumnSpaceModel#addHistories()],
  /// [ColumnSpaceModel#clearHistories()] のテスト.
  @Test
  void testHistory() {
    doReturn(
        Map.of(
            "temperature", Monitor.temperature(),
            "pressure", Monitor.pressure()))
        .when(this.space)
        .monitorMap();

    ColumnSpaceModel model = new ColumnSpaceModel(this.space, this.cell);

    assertThat(model.historyListMap().keySet()).isEqualTo(Set.of("temperature", "pressure"));
    model.historyListMap().values().forEach(valueList -> assertThat(valueList).isEmpty());

    doReturn(298.15).when(this.cell).getTemperature();
    doReturn(101325.).when(this.cell).getPressure();

    model.addHistories();

    assertThat(model.historyListMap().get("temperature")).isEqualTo(List.of(298.15));
    assertThat(model.historyListMap().get("pressure")).isEqualTo(List.of(101325.));

    doReturn(303.15).when(this.cell).getTemperature();
    doReturn(111325.).when(this.cell).getPressure();

    model.addHistories();

    assertThat(model.historyListMap().get("temperature")).isEqualTo(List.of(298.15, 303.15));
    assertThat(model.historyListMap().get("pressure")).isEqualTo(List.of(101325., 111325.));

    model.clearHistories();

    model.historyListMap().values().forEach(valueList -> assertThat(valueList).isEmpty());
  }

}
