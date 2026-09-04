/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import java.util.Objects;

/// 貯槽モデルの永続化クラス.
///
/// @since 1.1
/// @author KITAMURA Yuichiro
public final class TankModelEntity {

  /// 貯槽セルの永続化オブジェクト.
  private TankCellEntity cellEntity = new TankCellEntity();

  /// 貯槽モデルの永続化クラスのインスタンスを生成する.
  public TankModelEntity() {}

  /// セルの永続化オブジェクトを返す.
  ///
  /// @return セルの永続化オブジェクト.
  @JsonProperty("cell")
  public TankCellEntity getCellEntity() {
    return this.cellEntity;
  }

  /// セルの永続化オブジェクトを設定する.
  ///
  /// @param cellEntity セルの永続化オブジェクト.
  /// @throws NullPointerException `cellEntity` が `null` の場合.
  @JsonProperty("cell")
  public void setCellEntity(TankCellEntity cellEntity) {
    this.cellEntity = Preconditions.checkNotNull(cellEntity);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    TankModelEntity that = (TankModelEntity) o;

    return this.cellEntity.equals(that.cellEntity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.cellEntity);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("cellEntity", this.cellEntity)
        .toString();
  }

}
