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
final class BasicPsaModel {

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
  private final FluidCell2 feedLineCell;

  /// 充圧系統セル.
  private final FluidCell2 pressurizationLineCell;

  /// リンス系統セル.
  private final FluidCell2 rinseLineCell;

  /// 真空排気系統セル.
  private final FluidCell2 vacuumLineCell;

  /// 上部均圧系統の不変マップ.
  private final Map<LineKey, FluidCell2> upperEqualizationLineCellMap;

  /// 下部均圧系統の不変マップ.
  private final Map<LineKey, FluidCell2> lowerEqualizationLineCellMap;

  /// 原料供給主弁フェイス.
  private final FluidFace2 mainFeedFluidFace;

  /// 製品払出主弁フェイス.
  private final FluidFace2 mainProductFluidFace;

  /// 充圧主弁フェイス.
  private final FluidFace2 mainPressurizationFluidFace;

  /// リンス主弁フェイス.
  private final FluidFace2 mainRinseFluidFace;

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

    this.feedLineCell = FluidCell2.newInstance(process.composition());
    this.pressurizationLineCell = FluidCell2.newInstance(process.composition());
    this.rinseLineCell = FluidCell2.newInstance(process.composition());
    this.vacuumLineCell = FluidCell2.newInstance(process.composition());
    this.upperEqualizationLineCellMap = process.upperEqualizationValveMap().keySet().stream()
        .collect(
            Maps.toImmutableEnumMap(
                lineKey -> lineKey,
                _ -> FluidCell2.newInstance(process.composition())));
    this.lowerEqualizationLineCellMap = process.lowerEqualizationValveMap().keySet().stream()
        .collect(
            Maps.toImmutableEnumMap(
                lineKey -> lineKey,
                _ -> FluidCell2.newInstance(process.composition())));

    this.mainFeedFluidFace = FluidFace2.newInstance(process.composition());
    this.mainProductFluidFace = FluidFace2.newInstance(process.composition());
    this.mainPressurizationFluidFace = FluidFace2.newInstance(process.composition());
    this.mainRinseFluidFace = FluidFace2.newInstance(process.composition());
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
  FluidCell2 feedLineCell() {
    return this.feedLineCell;
  }

  /// 充圧系統セルを返す.
  ///
  /// @return 充圧系統セル.
  FluidCell2 pressurizationLineCell() {
    return this.pressurizationLineCell;
  }

  /// リンス系統セルを返す.
  ///
  /// @return リンス系統セル.
  FluidCell2 rinseLineCell() {
    return this.rinseLineCell;
  }

  /// 真空排気系統セルを返す.
  ///
  /// @return 真空排気系統セル.
  FluidCell2 vacuumLineCell() {
    return this.vacuumLineCell;
  }

  /// 上部均圧系統セルの不変マップを返す.
  ///
  /// @return 上部均圧系統セルの不変マップ.
  Map<LineKey, FluidCell2> upperEqualizationLineCellMap() {
    return this.upperEqualizationLineCellMap;
  }

  /// 下部均圧系統セルの不変マップを返す.
  ///
  /// @return 下部均圧系統セルの不変マップ.
  Map<LineKey, FluidCell2> lowerEqualizationFluidCellMap() {
    return this.lowerEqualizationLineCellMap;
  }

  /// 原料供給主弁フェイスを返す.
  ///
  /// @return 原料供給主弁フェイス.
  FluidFace2 mainFeedFluidFace() {
    return this.mainFeedFluidFace;
  }

  /// 製品払出主弁フェイスを返す.
  ///
  /// @return 製品払出主弁フェイス.
  FluidFace2 mainProductFluidFace() {
    return this.mainProductFluidFace;
  }

  /// 充圧主弁フェイスを返す.
  ///
  /// @return 充圧主弁フェイス.
  FluidFace2 mainPressurizationFluidFace() {
    return this.mainPressurizationFluidFace;
  }

  /// リンス主弁フェイスを返す.
  ///
  /// @return リンス主弁フェイス.
  FluidFace2 mainRinseFluidFace() {
    return this.mainRinseFluidFace;
  }

  /// 永続化クラスのインスタンスを生成して返す.
  ///
  /// @return 永続化クラスのインスタンス.
  BasicPsaModelEntity2 newEntity() {
    return new BasicPsaModelEntity2(
        this.columnGroupMap.entrySet().stream()
            .collect(
                Maps.toImmutableEnumMap(
                    Map.Entry::getKey,
                    entry -> entry.getValue().columnModel.newEntity())),
        this.productTankModel.newEntity());
  }

  /// 永続化クラスのインスタンスを反映する.
  ///
  /// @param entity 永続化クラスのインスタンス.
  void accept(BasicPsaModelEntity2 entity) {
    Preconditions.checkNotNull(entity);
    Preconditions.checkNotNull(entity.columnModelEntityMap());
    Preconditions.checkArgument(
        this.columnGroupMap.keySet().equals(entity.columnModelEntityMap().keySet()));
    entity.columnModelEntityMap().values().forEach(Preconditions::checkNotNull);
    Preconditions.checkNotNull(entity.productTankModelEntity());

    this.columnGroupMap.forEach(
        (key, value) -> value.columnModel().accept(entity.columnModelEntityMap().get(key)));
    this.productTankModel.accept(entity.productTankModelEntity());
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
    Map<ColumnKey, Iterator<ColumnCell2>> cellIteratorMap = this.columnGroupMap.entrySet().stream()
        .collect(
            Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().columnModel().cellList().iterator(),
                (a, _) -> a,
                () -> new EnumMap<>(ColumnKey.class)));
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    List<ColumnCell2> axisCellList = this.columnGroupMap.values().stream()
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

      for (ColumnCell2 axisCell : axisCellList) {
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
    private final FluidFace2 feedFluidFace;

    /// 製品払出主弁フェイス.
    private final FluidFace2 productFluidFace;

    /// 充圧主弁フェイス.
    private final FluidFace2 pressurizationFluidFace;

    /// リンス主弁フェイス.
    private final FluidFace2 rinseFluidFace;

    /// 脱圧弁フェイスの不変マップ.
    private final Map<LineKey, FluidFace2> blowFluidFaceMap;

    /// 上部均圧弁フェイスの不変マップ.
    private final Map<LineKey, FluidFace2> upperEqualizationFluidFaceMap;

    /// 下部均圧弁フェイスの不変マップ.
    private final Map<LineKey, FluidFace2> lowerEqualizationFluidFaceMap;

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

      this.feedFluidFace = FluidFace2.newInstance(composition);
      this.productFluidFace = FluidFace2.newInstance(composition);
      this.pressurizationFluidFace = FluidFace2.newInstance(composition);
      this.rinseFluidFace = FluidFace2.newInstance(composition);

      this.blowFluidFaceMap = blowLineKey.stream()
          .collect(
              Maps.toImmutableEnumMap(
                  lineKey -> lineKey,
                  _ -> FluidFace2.newInstance(composition)));
      this.upperEqualizationFluidFaceMap = upperEqualizationLineKey.stream()
          .collect(
              Maps.toImmutableEnumMap(
                  lineKey -> lineKey,
                  _ -> FluidFace2.newInstance(composition)));
      this.lowerEqualizationFluidFaceMap = lowerEqualizationLineKey.stream()
          .collect(
              Maps.toImmutableEnumMap(
                  lineKey -> lineKey,
                  _ -> FluidFace2.newInstance(composition)));
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
    FluidFace2 feedFluidFace() {
      return this.feedFluidFace;
    }

    /// 製品払出弁フェイスを返す.
    ///
    /// @return 製品払出弁フェイス.
    FluidFace2 productFluidFace() {
      return this.productFluidFace;
    }

    /// 充圧弁フェイスを返す.
    ///
    /// @return 充圧弁フェイス.
    FluidFace2 pressurizationFluidFace() {
      return this.pressurizationFluidFace;
    }

    /// リンス弁フェイスを返す.
    ///
    /// @return リンス弁フェイス.
    FluidFace2 rinseFluidFace() {
      return this.rinseFluidFace;
    }

    /// 脱圧弁フェイスの不変セットを返す.
    ///
    /// @return 脱圧弁フェイスの不変セット.
    Map<LineKey, FluidFace2> blowFluidFaceMap() {
      return this.blowFluidFaceMap;
    }

    /// 上部均圧弁フェイスの不変セットを返す.
    ///
    /// @return 上部均圧弁フェイスの不変セット.
    Map<LineKey, FluidFace2> upperEqualizationFluidFaceMap() {
      return this.upperEqualizationFluidFaceMap;
    }

    /// 下部均圧弁フェイスの不変セットを返す.
    ///
    /// @return 下部均圧弁フェイスの不変セット.
    Map<LineKey, FluidFace2> lowerEqualizationFluidFaceMap() {
      return this.lowerEqualizationFluidFaceMap;
    }

  } // ColumnGroup

}
