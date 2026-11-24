/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/// 基本 PSA モデルの永続化クラス.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class BasicPsaModelEntity {

  /// 吸着筒モデルの永続化オブジェクトのマップ.
  private final Map<ColumnKey, ColumnModelEntity> columnModelEntityMap
      = new EnumMap<>(ColumnKey.class);

  /// 製品槽モデルの永続化オブジェクト.
  private TankModelEntity productTankModelEntity = new TankModelEntity();

  /// 基本 PSA モデルの永続化クラスのインスタンスを生成する.
  public BasicPsaModelEntity() {}

  /// 吸着筒モデルの永続化オブジェクトの不変マップを返す.
  ///
  /// @return 吸着筒モデルの永続化オブジェクトの不変マップ.
  @JsonProperty("column")
  public Map<ColumnKey, ColumnModelEntity> getColumnModelEntityMap() {
    return Maps.immutableEnumMap(this.columnModelEntityMap);
  }

  /// 吸着筒モデルの永続化オブジェクトのマップを設定する.
  ///
  /// @param columnModelEntityMap 吸着筒モデルの永続化オブジェクトのマップ.
  /// @throws NullPointerException `columnModelEntryMap` またはそのキーや値が `null` の場合.
  @JsonProperty("column")
  public void setColumnModelEntityMap(Map<ColumnKey, ColumnModelEntity> columnModelEntityMap) {
    Preconditions.checkNotNull(columnModelEntityMap).forEach((key, value) -> {
      Preconditions.checkNotNull(key);
      Preconditions.checkNotNull(value);
    });

    this.columnModelEntityMap.clear();
    this.columnModelEntityMap.putAll(columnModelEntityMap);
  }

  /// 製品槽モデルの永続化オブジェクトを返す.
  ///
  /// @return 製品槽モデルの永続化オブジェクト.
  @JsonProperty("productTank")
  public TankModelEntity getProductTankModelEntity() {
    return this.productTankModelEntity;
  }

  /// 製品槽モデルの永続化オブジェクトを設定する.
  ///
  /// @param productTankModelEntity 製品槽モデルの永続化オブジェクト.
  /// @throws NullPointerException `productTankModelEntity` が `null` の場合.
  @JsonProperty("productTank")
  public void setProductTankModelEntity(TankModelEntity productTankModelEntity) {
    this.productTankModelEntity = Preconditions.checkNotNull(productTankModelEntity);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    BasicPsaModelEntity that = (BasicPsaModelEntity) o;

    return this.columnModelEntityMap.equals(that.columnModelEntityMap)
        && this.productTankModelEntity.equals(that.productTankModelEntity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.columnModelEntityMap,
        this.productTankModelEntity);
  }

}
