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

/// [TankModel] のテスト.
///
/// @since 1.1
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class TankModelTest {

  @Mock
  private Tank tank;

  @Mock
  private Composition composition;

  /// [TankModel#cell()] のテスト.
  @Test
  void testCell() {
    Map<String, Monitor> monitoprMap
        = Map.of(
        "temperature", Monitor.temperature(),
        "pressure", Monitor.pressure());
    doReturn(monitoprMap).when(this.tank).monitorMap();

    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    TankModel model = new TankModel(this.tank, this.composition);

    assertThat(model.cell().componentMap().keySet()).isEqualTo(componentKeySet);
  }

  @Test
  void testHistories() {
    Map<String, Monitor> monitorMap
        = Map.of(
        "temperature", Monitor.temperature(),
        "pressure", Monitor.pressure());
    doReturn(monitorMap).when(this.tank).monitorMap();

    Set<ComponentKey> componentKeySet
        = Set.of(ComponentKey.ARGON, ComponentKey.OXYGEN, ComponentKey.NITROGEN);
    doReturn(componentKeySet).when(this.composition).componentKeySet();

    TankModel model = new TankModel(this.tank, this.composition);

    assertThat(model.historyListMap().keySet()).isEqualTo(monitorMap.keySet());
    model.historyListMap().values().forEach(value -> assertThat(value).isEmpty());

    model.cell().setTemperature(298.15);
    model.cell().setPressure(101325.);

    model.addHistories();

    model.cell().setTemperature(303.15);
    model.cell().setPressure(111325.);

    model.addHistories();

    assertThat(model.historyListMap().get("temperature")).isEqualTo(List.of(298.15, 303.15));
    assertThat(model.historyListMap().get("pressure")).isEqualTo(List.of(101325., 111325.));

    model.clearHistories();

    model.historyListMap().values().forEach(value -> assertThat(value).isEmpty());
  }

}
