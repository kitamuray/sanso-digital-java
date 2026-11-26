/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/// 吸着筒モデル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
final class ColumnModel {

  /// 吸着筒.
  private final Column column;

  /// セル・フェイスのプリミティブ double 値を一括保持する配列.
  private final double[] doubleArray;

  /// 西側境界セル.
  private final ColumnCell westCell;

  /// 空間セルの不変リスト.
  private final List<ColumnCell> spaceCellList;

  /// 領域セルの不変リストの不変リストの不変リスト.
  private final List<List<List<ColumnCell>>> zoneCellListListList;

  /// 東側境界セル.
  private final ColumnCell eastCell;

  /// 全セルの不変リスト.
  private final List<ColumnCell> cellList;

  /// 全フェイスの不変リスト.
  private final List<ColumnFace> faceList;

  /// 空間モデルの不変リスト.
  private final List<ColumnSpaceModel> spaceModelList;

  /// 領域モデルの不変リストの不変リスト.
  private final List<List<ColumnZoneModel>> zoneModelListList;

  /// 吸着筒モデルのインスタンスを生成する.
  ///
  /// @param column 吸着筒.
  /// @param composition 組成.
  ColumnModel(Column column, Composition composition) {
    this.column = column;

    int number
        = column.spaceList().size()
        + column.zoneListList().stream()
          .mapToInt(
              zoneList -> zoneList.stream()
                  .mapToInt(zone -> zone.section().number())
                  .sum())
          .sum()
        + 2;

    final int cellSize = ColumnCell.number(composition); // セルあたりの double 値の数
    final int faceSize = ColumnFace.number(composition); // フェイスあたりの double 値の数
    this.doubleArray = new double[number * cellSize + (number - 1) * faceSize];

    double origin = column.origin();
    int offset = 0;
    final int stride = cellSize + faceSize; // （セル + フェイス）あたりの double 値の数

    // 西側境界セル
    this.westCell = new InnerCell(
        offset, column.shape(), origin, origin, composition, Adsorbent.empty(), column.shell());
    offset += stride;

    ImmutableList.Builder<ColumnCell> spaceCellListBuilder = ImmutableList.builder();
    ImmutableList.Builder<ColumnSpaceModel> spaceModelListBuilder = ImmutableList.builder();

    ImmutableList.Builder<List<List<ColumnCell>>> zoneCellListListListBuilder
        = ImmutableList.builder();
    ImmutableList.Builder<List<ColumnZoneModel>> zoneModelListListBuilder
        = ImmutableList.builder();

    Iterator<ColumnSpace> spaceIterator = column.spaceList().iterator();

    for (List<ColumnZone> zoneList : column.zoneListList()) { // Space + List<Zone> の loop
      ColumnSpace space = spaceIterator.next();
      ColumnCell spaceCell = new InnerCell(
          offset, column.shape(), origin, origin + space.length(),
          composition, Adsorbent.empty(), column.shell());
      spaceCellListBuilder.add(spaceCell);
      spaceModelListBuilder.add(new ColumnSpaceModel(space, spaceCell));
      origin += space.length();
      offset += stride;

      ImmutableList.Builder<List<ColumnCell>> zoneCellListListBuilder = ImmutableList.builder();
      ImmutableList.Builder<ColumnZoneModel> zoneModelListBuilder = ImmutableList.builder();
      for (ColumnZone zone : zoneList) {
        ImmutableList.Builder<ColumnCell> zoneCellListBuilder = ImmutableList.builder();
        for (double segment : zone.section().segments()) {
          zoneCellListBuilder.add(
              new InnerCell(
                  offset, column.shape(), origin, origin + segment,
                  composition, zone.adsorbent(), column.shell()));
          origin += segment;
          offset += stride;
        }
        List<ColumnCell> zoneCellList = zoneCellListBuilder.build();
        zoneCellListListBuilder.add(zoneCellList);
        zoneModelListBuilder.add(new ColumnZoneModel(zone, zoneCellList));
      }
      zoneCellListListListBuilder.add(zoneCellListListBuilder.build());
      zoneModelListListBuilder.add(zoneModelListBuilder.build());
    }

    { // 最後の Space
      ColumnSpace space = spaceIterator.next();
      ColumnCell spaceCell = new InnerCell(
          offset, column.shape(), origin, origin + space.length(),
          composition, Adsorbent.empty(), column.shell());
      spaceCellListBuilder.add(spaceCell);
      spaceModelListBuilder.add(new ColumnSpaceModel(space, spaceCell));
      origin += space.length();
      offset += stride;
    }

    this.spaceCellList = spaceCellListBuilder.build();
    this.spaceModelList = spaceModelListBuilder.build();
    this.zoneCellListListList = zoneCellListListListBuilder.build();
    this.zoneModelListList = zoneModelListListBuilder.build();

    // 東側境界セル
    this.eastCell = new InnerCell(
        offset, column.shape(), origin, origin,
        composition, Adsorbent.empty(), column.shell());

    ImmutableList.Builder<ColumnCell> cellListBuilder = ImmutableList.builder();

    cellListBuilder.add(this.westCell);

    Iterator<ColumnCell> spaceCellIterator = this.spaceCellList.iterator();
    for (List<List<ColumnCell>> zoneCellListList : this.zoneCellListListList) {
      cellListBuilder.add(spaceCellIterator.next());
      cellListBuilder.addAll(
          zoneCellListList.stream()
              .flatMap(Collection::stream)
              .collect(Collectors.toList()));
    }
    cellListBuilder.add(spaceCellIterator.next());

    cellListBuilder.add(this.eastCell);

    this.cellList = cellListBuilder.build();

    ImmutableList.Builder<ColumnFace> faceListBuilder = ImmutableList.builder();

    offset = cellSize;
    origin = this.westCell.centroid();

    for (ColumnCell cell : this.cellList.subList(1, this.cellList.size())) {
      faceListBuilder.add(new InnerFace(offset, column.shape(), origin, composition));
      offset += stride;
      origin += cell.length();
    }

    this.faceList = faceListBuilder.build();

  }

  /// 永続化クラスのインスタンスを生成して返す.
  ///
  /// @return 永続化クラスのインスタンス.
  ColumnModelEntity newEntity() {
    return new ColumnModelEntity(
        this.cellList.stream()
            .map(ColumnCell::newEntity)
            .collect(ImmutableList.toImmutableList()));
  }

  /// 指定された永続化クラスのインスタンスを反映する.
  ///
  /// @param entity 永続化クラスのインスタンス.
  /// @throws NullPointerException `entity` の構成要素に `null` が含まれている場合.
  void accept(ColumnModelEntity entity) {
    Preconditions.checkNotNull(entity);
    Preconditions.checkNotNull(entity.cellEntityList());
    entity.cellEntityList().forEach(Preconditions::checkNotNull);

    Iterator<ColumnCellEntity> cellEntityIterator = entity.cellEntityList().iterator();
    for (ColumnCell cell : this.cellList) {
      cell.accept(cellEntityIterator.next());
    }
  }

  /// 履歴情報を初期化する.
  void clearHistories() {
    this.spaceModelList.forEach(ColumnSpaceModel::clearHistories);
    this.zoneModelListList.forEach(
        zoneModelList -> zoneModelList.forEach(ColumnZoneModel::clearHistories));
  }

  /// 履歴情報を追記する.
  void addHistories() {
    this.spaceModelList.forEach(ColumnSpaceModel::addHistories);
    this.zoneModelListList.forEach(
        zoneModelList -> zoneModelList.forEach(ColumnZoneModel::addHistories));
  }

  /// 吸着筒を返す.
  ///
  /// @return 吸着筒.
  Column column() {
    return this.column;
  }

  /// 西側境界セルを返す.
  ///
  /// @return 西側境界セル.
  ColumnCell westCell() {
    return this.westCell;
  }

  /// 空間セルの不変リストを返す.
  ///
  /// @return 空間セルの不変リスト.
  List<ColumnCell> spaceCellList() {
    return this.spaceCellList;
  }

  /// 領域セルの不変リストの不変リストの不変リストを返す.
  ///
  /// @return 領域セルの不変リストの不変リストの不変リスト.
  List<List<List<ColumnCell>>> zoneCellListListList() {
    return this.zoneCellListListList;
  }

  /// 東側境界セルを返す.
  ///
  /// @return 東側境界セル.
  ColumnCell eastCell() {
    return this.eastCell;
  }

  /// セルの不変リストを返す.
  ///
  /// @return セルの不変リスト.
  List<ColumnCell> cellList() {
    return this.cellList;
  }

  /// フェイスの不変リストを返す.
  ///
  /// @return フェイスの不変リスト.
  List<ColumnFace> faceList() {
    return this.faceList;
  }

  /// 空間モデルの不変リストを返す.
  ///
  /// @return 空間モデルの不変リスト.
  List<ColumnSpaceModel> spaceModelList() {
    return this.spaceModelList;
  }

  /// 領域モデルの不変リストの不変リストを返す.
  ///
  /// @return 領域モデルの不変リストの不変リスト.
  public List<List<ColumnZoneModel>> zoneModelListList() {
    return this.zoneModelListList;
  }

  /// セルの内部実装.
  final class InnerCell extends ColumnCell {

    /// セルのインスタンスを生成する.
    ///
    /// @param offset オフセット値.
    /// @param shape 形状.
    /// @param fromX 始点座標 (m).
    /// @param toX 終点座標 (m).
    /// @param composition 組成.
    /// @param adsorbent 吸着剤.
    /// @param shell 容器.
    private InnerCell(
        int offset,
        ColumnShape shape, double fromX, double toX,
        Composition composition, Adsorbent adsorbent, Shell shell) {
      super(offset, shape, fromX, toX, composition, adsorbent, shell);
    }

    @Override
    double[] doubleArray() {
      return ColumnModel.this.doubleArray;
    }

  } // InnerCell

  /// フェイスの内部実装.
  final class InnerFace extends ColumnFace {

    /// フェイスのインスタンスを生成する.
    ///
    /// @param offset オフセット値.
    /// @param shape 形状.
    /// @param coordinate 座標 (m).
    /// @param composition 組成.
    private InnerFace(
        int offset,
        ColumnShape shape, double coordinate,
        Composition composition) {
      super(offset, shape, coordinate, composition);
    }

    @Override
    double[] doubleArray() {
      return ColumnModel.this.doubleArray;
    }

  } // InnerFace

}
