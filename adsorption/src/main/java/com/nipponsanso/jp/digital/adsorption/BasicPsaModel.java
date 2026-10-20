/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import de.siegmar.fastcsv.writer.CsvWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/// 基本 PSA モデル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public class BasicPsaModel {

  /// プロセス.
  private final BasicPsaProcess process;

  /// 流体モデル.
  private final FluidService fluidService;

  /// 吸着筒周辺モデルの可変マップ.
  private final Map<ColumnKey, ColumnGroup> columnGroupMap;

  /// 製品槽モデル.
  private final TankModel productTankModel;

  /// 原料供給境界モデル.
  private final PortModel feedPortModel;

  /// 製品払出境界モデル.
  private final PortModel productPortModel;

  /// 大気放出境界モデル.
  private final PortModel exhaustPortModel;

  /// 真空ポンプモデル.
  private final VacuumPumpModel vacuumPumpModel;

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

  /// 原料供給主弁フェイス.
  private final ValveFace mainFeedValveFace;

  /// 製品払出主弁フェイス.
  private final ValveFace mainProductValveFace;

  /// 充圧主弁フェイス.
  private final ValveFace mainPressurizationValveFace;

  /// リンス主弁フェイス.
  private final ValveFace mainRinseValveFace;

  /// 履歴時刻 (s) の可変リスト.
  private final List<Double> historyTimeList = new ArrayList<>();

  /// 現在時刻.
  private double time;

  /// 指定されたプロセスで構成されたモデルのインスタンスを生成する.
  ///
  /// @param process プロセス.
  BasicPsaModel(BasicPsaProcess process) {
    this.process = process;

    this.fluidService = process.fluid().newService(process.composition());
    this.columnGroupMap = process.columnEntryMap().entrySet().stream()
        .collect(
            Collectors.toMap(
                Map.Entry::getKey,
                entry -> new ColumnGroup(
                    process.column(),
                    this.fluidService.composition(),
                    entry.getValue().start(),
                    process.blowValveMap().keySet(),
                    process.upperEqualizationValveMap().keySet(),
                    process.lowerEqualizationValveMap().keySet()),
                (a, _) -> a,
                () -> new EnumMap<>(ColumnKey.class)));
    this.productTankModel = new TankModel(
        process.productTank(),
        this.fluidService.composition()
    );
    this.feedPortModel = new PortModel(process.feedPort(), this.fluidService.composition());
    this.productPortModel = new PortModel(process.productPort(), this.fluidService.composition());
    this.exhaustPortModel = new PortModel(process.exhaustPort(), this.fluidService.composition());
    this.vacuumPumpModel
        = new VacuumPumpModel(process.vacuumPump(), this.fluidService.composition());

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

    this.mainFeedValveFace = new ValveFace(process.composition());
    this.mainProductValveFace = new ValveFace(process.composition());
    this.mainPressurizationValveFace = new ValveFace(process.composition());
    this.mainRinseValveFace = new ValveFace(process.composition());
  }

  /// 流体モデルを返す.
  ///
  /// @return 流体モデル.
  FluidService fluidModel() {
    return this.fluidService;
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

  /// 原料供給境界モデルを返す.
  ///
  /// @return 原料供給境界モデル.
  PortModel feedPortModel() {
    return this.feedPortModel;
  }

  /// 製品払出境界モデルを返す.
  ///
  /// @return 製品払出境界モデル.
  PortModel productPortModel() {
    return this.productPortModel;
  }

  /// 大気放出境界モデルを返す.
  ///
  /// @return 大気放出境界モデル.
  PortModel exhaustPortModel() {
    return this.exhaustPortModel;
  }

  /// 真空ポンプモデルを返す.
  ///
  /// @return 真空ポンプモデル.
  VacuumPumpModel vacuumPumpModel() {
    return this.vacuumPumpModel;
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

  /// 永続化オブジェクトへ情報を書き込む.
  ///
  /// @param entity 永続化オブジェクト.
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

  /// 永続化オブジェクトから情報を読み取る.
  ///
  /// @param entity 永続化オブジェクト.
  /// @throws IllegalArgumentException 永続化オブジェクトが不完全な場合.
  void read(BasicPsaModelEntity entity) {
    Preconditions.checkArgument(
        this.columnGroupMap.keySet().equals(
            entity.getColumnModelEntityMap().keySet()));
    this.columnGroupMap.forEach(
        (columnKey, columnGroup) -> columnGroup.columnModel.read(
            entity.getColumnModelEntityMap().get(columnKey)));

    this.productTankModel.read(entity.getProductTankModelEntity());
  }

  /// 履歴情報を追記する.
  void addHistories() {
    this.historyTimeList.add(this.time);
    this.columnGroupMap.values().stream()
        .map(ColumnGroup::columnModel)
        .forEach(ColumnModel::addHistories);
    this.productTankModel.addHistories();
    this.feedPortModel.addHistories();
    this.productPortModel.addHistories();
    this.exhaustPortModel.addHistories();
    this.vacuumPumpModel.addHistories();
  }

  /// 履歴情報を初期化する.
  void clearHistories() {
    this.time = 0.;
    this.historyTimeList.clear();
    this.columnGroupMap.values().stream()
        .map(ColumnGroup::columnModel)
        .forEach(ColumnModel::clearHistories);
    this.productTankModel.clearHistories();
    this.feedPortModel.clearHistories();
    this.productPortModel.clearHistories();
    this.exhaustPortModel.clearHistories();
    this.vacuumPumpModel.clearHistories();
  }

  /// 履歴情報を出力する.
  void flushHistories() {
    this.flushColumnSpaceHistories();
    this.flushColumnZoneHistories();
    this.flushProductTankHistories();
    this.flushPortHistories();
  }

  /// 吸着筒空間の履歴情報を出力する.
  private void flushColumnSpaceHistories() {
    for (ColumnSpace space : this.process.column().spaceList()) { // 空間のループ
      Map<ColumnKey, Iterator<ColumnSpaceModel>> modelIteratorMap
          = this.columnGroupMap.entrySet().stream()
          .collect(
              Collectors.toMap(
                  Map.Entry::getKey,
                  entry -> entry.getValue().columnModel().spaceModelList().iterator(),
                  (a, _) -> a,
                  () -> new EnumMap<>(ColumnKey.class)));

      for (String fileName : space.monitorMap().keySet()) { // 出力ファイル名のループ
        Map<ColumnKey, Iterator<Double>> historyIteratorMap = modelIteratorMap.entrySet().stream()
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> entry.getValue().next().historyListMap().get(fileName).iterator(),
                    (a, _) -> a,
                    () -> new EnumMap<>(ColumnKey.class)));

        this.flushHistories(fileName, historyIteratorMap);
      } // 出力ファイル名のループ
    } // 空間のループ
  }

  /// 吸着筒領域の履歴情報を出力する.
  private void flushColumnZoneHistories() {
    for (List<ColumnZone> zoneList : this.process.column().zoneListList()) { // 領域リストのループ
      Map<ColumnKey, Iterator<List<ColumnZoneModel>>> modelListIteratorMap
          = this.columnGroupMap.entrySet().stream()
          .collect(
              Collectors.toMap(
                  Map.Entry::getKey,
                  entry -> entry.getValue().columnModel().zoneModelListList().iterator(),
                  (a, _) -> a,
                  () -> new EnumMap<>(ColumnKey.class)));

      for (ColumnZone zone : zoneList) { // 領域のループ
        Map<ColumnKey, Iterator<ColumnZoneModel>> modelIteratorMap
            = modelListIteratorMap.entrySet().stream()
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> entry.getValue().next().iterator(),
                    (a, _) -> a,
                    () -> new EnumMap<>(ColumnKey.class)));

        for (String fileName : zone.indexMonitorMap().keySet()) { // 出力ファイル名のループ
          Map<ColumnKey, Iterator<Double>> historyIteratorMap = modelIteratorMap.entrySet().stream()
              .collect(
                  Collectors.toMap(
                      Map.Entry::getKey,
                      entry -> entry.getValue().next().historyListMap().get(fileName).iterator(),
                      (a, _) -> a,
                      () -> new EnumMap<>(ColumnKey.class)));
          this.flushHistories(fileName, historyIteratorMap);
        } // 出力ファイル名のループ
      } // 領域のループ
    } // 領域リストのループ
  }

  /// 製品槽の履歴情報を出力する.
  private void flushProductTankHistories() {
    enum ProductTankKey {PRODUCT_TANK}

    this.productTankModel.historyListMap().forEach(
        (fileName, historyList) -> this.flushHistories(
            fileName,
            Collections.singletonMap(ProductTankKey.PRODUCT_TANK, historyList.iterator())));
  }

  private void flushPortHistories() {
    enum PortKey {FEED_PORT, PRODUCT_PORT, EXHAUST_PORT, VACUUM_PUMP}

    this.feedPortModel.historyListMap().forEach(
        (fileName, historyList) -> this.flushHistories(
            fileName,
            Collections.singletonMap(PortKey.FEED_PORT, historyList.iterator())));
    this.productPortModel.historyListMap().forEach(
        (fileName, historyList) -> this.flushHistories(
            fileName,
            Collections.singletonMap(PortKey.PRODUCT_PORT, historyList.iterator())));
    this.exhaustPortModel.historyListMap().forEach(
        (fileName, historyList) -> this.flushHistories(
            fileName,
            Collections.singletonMap(PortKey.EXHAUST_PORT, historyList.iterator())));
    this.vacuumPumpModel.historyListMap().forEach(
        (fileName, historyList) -> this.flushHistories(
            fileName,
            Collections.singletonMap(PortKey.VACUUM_PUMP, historyList.iterator())));
  }

  /// 履歴情報を出力する共通処理.
  ///
  /// @param <K> マップのキーの型.
  /// @param fileName 出力ファイル名.
  /// @param historyIteratorMap 履歴情報イタレータのマップ.
  private <K extends Enum<K>> void flushHistories(
      String fileName, Map<K, Iterator<Double>> historyIteratorMap) {
    List<String> stringList = new ArrayList<>();
    Path file = Paths.get(fileName);

    try (CsvWriter csv = CsvWriter.builder().build(file, StandardCharsets.UTF_8)) {
      stringList.add("Time");
      historyIteratorMap.keySet().stream()
          .map(Enum::toString)
          .forEach(stringList::add);
      csv.writeRecord(stringList);
      stringList.clear();

      for (Double historyTime : this.historyTimeList) {
        stringList.add(historyTime.toString());
        for (Iterator<Double> historyIterator : historyIteratorMap.values()) {
          stringList.add(historyIterator.next().toString());
        }

        csv.writeRecord(stringList);
        stringList.clear();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /// 吸着筒のスナップショットを出力する.
  ///
  /// @param fileName 出力ファイル名.
  /// @param monitor モニタ.
  void snapshot(String fileName, Monitor monitor) {
    Map<ColumnKey, Iterator<ColumnCell>> cellIteratorMap = this.columnGroupMap.entrySet().stream()
        .collect(
            Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().columnModel().cellList().iterator(),
                (a, _) -> a,
                () -> new EnumMap<>(ColumnKey.class)));
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    List<ColumnCell> axisCellList = this.columnGroupMap.values().stream()
        .findAny().get()
        .columnModel()
        .cellList();

    List<String> stringList = new ArrayList<>();
    Path file = Paths.get(fileName);

    try (CsvWriter csv = CsvWriter.builder().build(file, StandardCharsets.UTF_8)) {
      stringList.add("Coordinate");
      cellIteratorMap.keySet().stream()
          .map(ColumnKey::toString)
          .forEach(stringList::add);
      csv.writeRecord(stringList);
      stringList.clear();

      for (ColumnCell axisCell : axisCellList) {
        stringList.add(Double.toString(axisCell.centroid()));
        cellIteratorMap.values().forEach(
            cellIterator -> stringList.add(
                Double.toString(monitor.applyAsDouble(cellIterator.next()))));

        csv.writeRecord(stringList);
        stringList.clear();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
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
