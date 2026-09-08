/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/// 吸着筒空間モデル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
final class ColumnSpaceModel {

  private final ColumnSpace space;

  private final ColumnCell cell;

  private final Map<String, List<Double>> historyListMap;

  ColumnSpaceModel(ColumnSpace space, ColumnCell cell) {
    this.space = space;
    this.cell = cell;

    this.historyListMap = this.space.monitorMap().keySet().stream()
        .collect(
            ImmutableMap.toImmutableMap(
                fileName -> fileName,
                _ -> new ArrayList<>()));
  }

  ColumnCell cell() {
    return this.cell;
  }

  Map<String, List<Double>> historyListMap() {
    return this.historyListMap;
  }

  void clearHistories() {
    this.historyListMap.values().forEach(List::clear);
  }

  void addHistories() {
    this.space.monitorMap().forEach((fileName, monitor) -> {
      List<Double> monitorList = this.historyListMap.get(fileName);

      monitorList.add(monitor.applyAsDouble(this.cell));
    });
  }

}
