/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/// 吸着筒領域モデル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
final class ColumnZoneModel {

  private final ColumnZone zone;

  private final List<ColumnCell> cellList;

  private final Map<String, List<Double>> historyListMap;

  ColumnZoneModel(ColumnZone zone, List<ColumnCell> cellList) {
    this.zone = zone;
    this.cellList = ImmutableList.copyOf(cellList);

    this.historyListMap = this.zone.indexMonitorMap().keySet().stream()
        .collect(
            ImmutableMap.toImmutableMap(
                fileName -> fileName,
                _ -> new ArrayList<>()));
  }

  List<ColumnCell> cellList() {
    return this.cellList;
  }

  Map<String, List<Double>> historyListMap() {
    return this.historyListMap;
  }

  void clearHistories() {
    this.historyListMap.values().forEach(List::clear);
  }

  void addHistories() {
    this.zone.indexMonitorMap().forEach((fileName, indexMonitor) -> {
      List<Double> monitorList = this.historyListMap.get(fileName);
      int index = indexMonitor.index();
      Monitor monitor = indexMonitor.monitor();

      monitorList.add(monitor.applyAsDouble(this.cellList.get(index)));
    });
  }
}
