/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/// 基本 PSA モデル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public class BasicPsaModel {

  /// 流体モデル.
  private final FluidModel fluidModel;

  /// 吸着筒周辺モデルの可変マップ.
  private final Map<ColumnKey, ColumnGroup> columnGroupMap;

  /// 製品槽モデル.
  private final TankModel productTankModel;

  /// 原料供給境界セル.
  private final PortCell feedPortCell;

  /// 製品払出境界セル.
  private final PortCell productPortCell;

  /// 大気放出境界セル.
  private final PortCell exhaustPortCell;

  /// 真空ポンプ境界セル.
  private final VacuumPumpCell vacuumPumpCell;

  /// 原料供給系統セル.
  private final LineCell feedLineCell;

  /// 充圧系統セル.
  private final LineCell pressurizationLineCell;

  /// リンス系統セル.
  private final LineCell rinseLineCell;

  /// 真空排気系統セル.
  private final LineCell vacuumLineCell;

  /// 上部均圧系統の不変マップ.
  private final Map<LineKey, LineCell> upperEqualizationLineCell;

  /// 下部均圧系統の不変マップ.
  private final Map<LineKey, LineCell> lowerEqualizationLineCell;

  /// 原料供給境界フェイス.
  private final PortFace feedPortFace;

  /// 製品払出境界フェイス.
  private final PortFace productPortFace;

  /// 大気放出境界フェイス.
  private final PortFace exhaustPortFace;

  /// 真空ポンプ・フェイス.
  private final PortFace vacuumPumpFace;

  /// 原料供給主弁フェイス.
  private final ValveFace mainFeedValveFace;

  /// 製品払出主弁フェイス.
  private final ValveFace mainProductValveFace;

  /// 充圧主弁フェイス.
  private final ValveFace mainPressurizationValveFace;

  /// リンス主弁フェイス.
  private final ValveFace mainRinseValveFace;

  /// 指定されたプロセスで構成されたモデルのインスタンスを生成する.
  ///
  /// @param process プロセス.
  BasicPsaModel(BasicPsaProcess process) {
    this.fluidModel = process.fluid().model(process.composition());
    this.columnGroupMap = process.columnEntryMap().entrySet().stream()
        .collect(
            Collectors.toMap(
                Map.Entry::getKey,
                entry -> new ColumnGroup(
                    process.column(),
                    this.fluidModel.composition(),
                    entry.getValue().start(),
                    process.blowValveMap().keySet(),
                    process.upperEqualizationValveMap().keySet(),
                    process.lowerEqualizationValveMap().keySet()),
                (a, _) -> a,
                () -> new EnumMap<>(ColumnKey.class)));
    this.productTankModel = new TankModel(
        process.productTank(),
        this.fluidModel.composition()
    );

    this.feedPortCell = new PortCell(process.composition());
    process.feedPort().set(this.feedPortCell);

    this.productPortCell = new PortCell(process.composition());
    process.productPort().set(this.productPortCell);

    this.exhaustPortCell = new PortCell(process.composition());
    process.exhaustPort().set(this.exhaustPortCell);

    this.vacuumPumpCell = new VacuumPumpCell(process.composition());
    process.vacuumPump().set(this.vacuumPumpCell);

    this.feedLineCell = new LineCell(process.composition());
    this.pressurizationLineCell = new LineCell(process.composition());
    this.rinseLineCell = new LineCell(process.composition());
    this.vacuumLineCell = new LineCell(process.composition());
    this.upperEqualizationLineCell = process.upperEqualizationValveMap().keySet().stream()
        .collect(
            Maps.toImmutableEnumMap(
                lineKey -> lineKey,
                _ -> new LineCell(process.composition())));
    this.lowerEqualizationLineCell = process.lowerEqualizationValveMap().keySet().stream()
        .collect(
            Maps.toImmutableEnumMap(
                lineKey -> lineKey,
                _ -> new LineCell(process.composition())));

    this.feedPortFace = new PortFace(process.composition());
    this.productPortFace = new PortFace(process.composition());
    this.exhaustPortFace = new PortFace(process.composition());
    this.vacuumPumpFace = new PortFace(process.composition());

    this.mainFeedValveFace = new ValveFace(process.composition());
    this.mainProductValveFace = new ValveFace(process.composition());
    this.mainPressurizationValveFace = new ValveFace(process.composition());
    this.mainRinseValveFace = new ValveFace(process.composition());
  }

  /// 流体モデルを返す.
  ///
  /// @return 流体モデル.
  FluidModel fluidModel() {
    return this.fluidModel;
  }

  /// 吸着筒周辺モデルのマップを返す.
  ///
  /// @return 吸着筒周辺モデルのマップ.
  Map<ColumnKey, ColumnGroup> columnGroupMap() {
    return this.columnGroupMap;
  }

  /// 製品槽モデルを返す.
  ///
  /// @return 製品槽モデル.
  TankModel productTankModel() {
    return this.productTankModel;
  }

  /// 原料供給境界セルを返す.
  ///
  /// @return 原料供給境界セル.
  PortCell feedPortCell() {
    return this.feedPortCell;
  }

  /// 製品払出境界セルを返す.
  ///
  /// @return 製品払出境界セル.
  PortCell productPortCell() {
    return this.productPortCell;
  }

  /// 大気放出境界セルを返す.
  ///
  /// @return 大気放出境界セル.
  PortCell exhaustPortCell() {
    return this.exhaustPortCell;
  }

  /// 真空ポンプ・セルを返す.
  ///
  /// @return 真空ポンプ・セル.
  VacuumPumpCell vacuumPumpCell() {
    return this.vacuumPumpCell;
  }

  /// 原料供給系統セルを返す.
  ///
  /// @return 原料供給系統セル.
  LineCell feedLineCell() {
    return this.feedLineCell;
  }

  /// 充圧系統セルを返す.
  ///
  /// @return 充圧系統セル.
  LineCell pressurizationLineCell() {
    return this.pressurizationLineCell;
  }

  /// リンス系統セルを返す.
  ///
  /// @return リンス系統セル.
  LineCell rinseLineCell() {
    return this.rinseLineCell;
  }

  /// 真空排気系統セルを返す.
  ///
  /// @return 真空排気系統セル.
  LineCell vacuumLineCell() {
    return this.vacuumLineCell;
  }

  /// 上部均圧系統セルの不変マップを返す.
  ///
  /// @return 上部均圧系統セルの不変マップ.
  Map<LineKey, LineCell> upperEqualizationLineCell() {
    return this.upperEqualizationLineCell;
  }

  /// 下部均圧系統セルの不変マップを返す.
  ///
  /// @return 下部均圧系統セルの不変マップ.
  Map<LineKey, LineCell> lowerEqualizationLineCell() {
    return this.lowerEqualizationLineCell;
  }

  /// 原料供給境界フェイスを返す.
  ///
  /// @return 原料供給境界フェイス.
  PortFace feedPortFace() {
    return this.feedPortFace;
  }

  /// 製品払出境界フェイスを返す.
  ///
  /// @return 製品払出境界フェイス.
  PortFace productPortFace() {
    return this.productPortFace;
  }

  /// 大気放出境界フェイスを返す.
  ///
  /// @return 大気放出境界フェイス.
  PortFace exhaustPortFace() {
    return this.exhaustPortFace;
  }

  /// 真空ポンプ・フェイスを返す.
  ///
  /// @return 真空ポンプ・フェイス.
  PortFace vacuumPumpFace() {
    return this.vacuumPumpFace;
  }

  /// 原料供給主弁フェイスを返す.
  ///
  /// @return 原料供給主弁フェイス.
  ValveFace mainFeedValveFace() {
    return this.mainFeedValveFace;
  }

  /// 製品払出主弁フェイスを返す.
  ///
  /// @return 製品払出主弁フェイス.
  ValveFace mainProductValveFace() {
    return this.mainProductValveFace;
  }

  /// 充圧主弁フェイスを返す.
  ///
  /// @return 充圧主弁フェイス.
  ValveFace mainPressurizationValveFace() {
    return this.mainPressurizationValveFace;
  }

  /// リンス主弁フェイスを返す.
  ///
  /// @return リンス主弁フェイス.
  ValveFace mainRinseValveFace() {
    return this.mainRinseValveFace;
  }

  void write(BasicPsaModelEntity entity) {
    entity.setColumnModelEntityMap(
        this.columnGroupMap.entrySet().stream()
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> {
                      ColumnModelEntity columnEntity = new ColumnModelEntity();
                      entry.getValue().columnModel.write(columnEntity);
                      return columnEntity;
                    })));

    TankModelEntity productTankEntity = new TankModelEntity();
    this.productTankModel.write(productTankEntity);
    entity.setProductTankModelEntity(productTankEntity);
  }

  void read(BasicPsaModelEntity entity) {
    Preconditions.checkArgument(
        this.columnGroupMap.keySet().equals(
            entity.getColumnModelEntityMap().keySet()));
    this.columnGroupMap.forEach(
        (columnKey, columnGroup) -> columnGroup.columnModel.read(
            entity.getColumnModelEntityMap().get(columnKey)));

    this.productTankModel.read(entity.getProductTankModelEntity());
  }

  /// 吸着筒周辺モデル.
  static final class ColumnGroup {

    /// 吸着筒モデル.
    private final ColumnModel columnModel;

    /// 原料供給弁フェイス.
    private final ValveFace feedValveFace;

    /// 製品払出主弁フェイス.
    private final ValveFace productValveFace;

    /// 充圧主弁フェイス.
    private final ValveFace pressurizationValveFace;

    /// リンス主弁フェイス.
    private final ValveFace rinseValveFace;

    /// 脱圧弁フェイスの不変マップ.
    private final Map<LineKey, ValveFace> blowValveFaceMap;

    /// 上部均圧弁フェイスの不変マップ.
    private final Map<LineKey, ValveFace> upperEqualizationValveFaceMap;

    /// 下部均圧弁フェイスの不変マップ.
    private final Map<LineKey, ValveFace> lowerEqualizationValveFaceMap;

    /// 吸着筒周辺モデルのインスタンスを生成する.
    ///
    /// @param column 吸着筒.
    /// @param composition 組成.
    /// @param start 吸着筒の開始条件.
    /// @param blowLineKey 脱圧系統のキーのセット.
    /// @param upperEqualizationLineKey 上部均圧系統のキーのセット.
    /// @param lowerEqualizationLineKey 下部均圧系統のキーのセット.
    private ColumnGroup(
        Column column, Composition composition, ColumnStart start,
        Set<LineKey> blowLineKey,
        Set<LineKey> upperEqualizationLineKey, Set<LineKey> lowerEqualizationLineKey) {
      this.columnModel = new ColumnModel(column, composition);

      this.feedValveFace = new ValveFace(composition);
      this.productValveFace = new ValveFace(composition);
      this.pressurizationValveFace = new ValveFace(composition);
      this.rinseValveFace = new ValveFace(composition);

      this.blowValveFaceMap = blowLineKey.stream()
          .collect(
              Maps.toImmutableEnumMap(
                  lineKey -> lineKey,
                  _ -> new ValveFace(composition)));
      this.upperEqualizationValveFaceMap = upperEqualizationLineKey.stream()
          .collect(
              Maps.toImmutableEnumMap(
                  lineKey -> lineKey,
                  _ -> new ValveFace(composition)));
      this.lowerEqualizationValveFaceMap = lowerEqualizationLineKey.stream()
          .collect(
              Maps.toImmutableEnumMap(
                  lineKey -> lineKey,
                  _ -> new ValveFace(composition)));
    }

    /// 吸着筒モデルを返す.
    ///
    /// @return 吸着筒モデル.
    ColumnModel columnModel() {
      return this.columnModel;
    }

    /// 原料供給弁フェイスを返す.
    ///
    /// @return 原料供給弁フェイス.
    ValveFace feedValveFace() {
      return this.feedValveFace;
    }

    /// 製品払出弁フェイスを返す.
    ///
    /// @return 製品払出弁フェイス.
    ValveFace productValveFace() {
      return this.productValveFace;
    }

    /// 充圧弁フェイスを返す.
    ///
    /// @return 充圧弁フェイス.
    ValveFace pressurizationValveFace() {
      return this.pressurizationValveFace;
    }

    /// リンス弁フェイスを返す.
    ///
    /// @return リンス弁フェイス.
    ValveFace rinseValveFace() {
      return this.rinseValveFace;
    }

    /// 脱圧弁フェイスの不変セットを返す.
    ///
    /// @return 脱圧弁フェイスの不変セット.
    Map<LineKey, ValveFace> blowValveFaceMap() {
      return this.blowValveFaceMap;
    }

    /// 上部均圧弁フェイスの不変セットを返す.
    ///
    /// @return 上部均圧弁フェイスの不変セット.
    Map<LineKey, ValveFace> upperEqualizationValveFaceMap() {
      return this.upperEqualizationValveFaceMap;
    }

    /// 下部均圧弁フェイスの不変セットを返す.
    ///
    /// @return 下部均圧弁フェイスの不変セット.
    Map<LineKey, ValveFace> lowerEqualizationValveFaceMap() {
      return this.lowerEqualizationValveFaceMap;
    }

  } // ColumnGroup

}
