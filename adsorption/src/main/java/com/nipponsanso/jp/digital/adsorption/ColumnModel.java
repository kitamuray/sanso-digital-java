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

  /// 吸着筒モデルのインスタンスを生成する.
  ///
  /// @param column 吸着筒.
  /// @param composition 組成.
  ColumnModel(Column column, Composition composition) {
    this.column = column;

    int size
        = column.spaceList().size()
        + column.zoneListList().stream()
          .mapToInt(
              zoneList -> zoneList.stream()
                  .mapToInt(zone -> zone.section().number())
                  .sum())
          .sum()
        + 2;

    final int cellSize = ColumnCell.size(composition);
    final int faceSize = ColumnFace.size(composition);
    this.doubleArray = new double[size * cellSize + (size - 1) * faceSize];

    double origin = column.origin();
    int offset = 0;
    final int stride = cellSize + faceSize;

    this.westCell = new InnerCell(
        offset, column.shape(), origin, origin, composition, Adsorbent.empty(), column.shell());
    offset += stride;

    ImmutableList.Builder<ColumnCell> spaceCellListBuilder = ImmutableList.builder();
    ImmutableList.Builder<List<List<ColumnCell>>> zoneCellListListListBuilder
        = ImmutableList.builder();

    Iterator<ColumnSpace> spaceIterator = column.spaceList().iterator();

    for (List<ColumnZone> zoneList : column.zoneListList()) {
      ColumnSpace space = spaceIterator.next();
      spaceCellListBuilder.add(
          new InnerCell(
              offset, column.shape(), origin, origin + space.length(),
              composition, Adsorbent.empty(), column.shell()));
      origin += space.length();
      offset += stride;

      ImmutableList.Builder<List<ColumnCell>> zoneCellListListBuilder = ImmutableList.builder();
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
        zoneCellListListBuilder.add(zoneCellListBuilder.build());
      }
      zoneCellListListListBuilder.add(zoneCellListListBuilder.build());
    }

    {
      ColumnSpace space = spaceIterator.next();
      spaceCellListBuilder.add(
          new InnerCell(
              offset, column.shape(), origin, origin + space.length(),
              composition, Adsorbent.empty(), column.shell()));
      origin += space.length();
      offset += stride;
    }

    this.spaceCellList = spaceCellListBuilder.build();
    this.zoneCellListListList = zoneCellListListListBuilder.build();

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

  void write(ColumnModelEntity entity) {
    entity.setCellEntityList(
        this.cellList.stream()
            .map(cell -> {
              ColumnCellEntity cellEntity = new ColumnCellEntity();
              cell.write(cellEntity);
              return cellEntity;
            })
            .collect(Collectors.toList()));
  }

  void read(ColumnModelEntity entity) {
    Preconditions.checkArgument(entity.getCellEntityList().size() == this.cellList.size());

    Iterator<ColumnCellEntity> entityIterator = entity.getCellEntityList().iterator();

    for (ColumnCell cell : this.cellList) {
      cell.read(entityIterator.next());
    }
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

    /// {@inheritDoc}
    ///
    /// @return {@inheritDoc}
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

    /// {@inheritDoc}
    ///
    /// @return {@inheritDoc}
    @Override
    double[] doubleArray() {
      return ColumnModel.this.doubleArray;
    }

  } // InnerFace

}
