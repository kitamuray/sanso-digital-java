/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/// 吸着筒モデルの永続化クラス.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class ColumnModelEntity {

  /// セルの永続化オブジェクトのリスト.
  private final List<ColumnCellEntity> cellEntityList = new ArrayList<>();

  /// 吸着筒モデルの永続化クラスのインスタンスを生成する.
  public ColumnModelEntity() {}

  /// セルの永続化オブジェクトのリストを返す.
  ///
  /// @return セルの永続化オブジェクトのリスト.
  @JsonProperty("cells")
  public List<ColumnCellEntity> getCellEntityList() {
    return this.cellEntityList;
  }

  /// セルの永続化オブジェクトのリストを設定する.
  ///
  /// @param cellEntityList セルの永続化オブジェクトのリスト.
  /// @throws NullPointerException `cellEntityList` またはその要素が `null` の場合.
  @JsonProperty("cells")
  public void setCellEntityList(List<ColumnCellEntity> cellEntityList) {
    Preconditions.checkNotNull(cellEntityList).forEach(Preconditions::checkNotNull);

    this.cellEntityList.clear();
    this.cellEntityList.addAll(cellEntityList);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    ColumnModelEntity that = (ColumnModelEntity) o;

    return this.cellEntityList.equals(that.cellEntityList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.cellEntityList);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("cellEntityList", this.cellEntityList)
        .toString();
  }

}
