/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/// セルの永続化クラスの抽象実装.
///
/// - セルの永続化クラスの基本骨格を定義
/// - 前ステップ値や成分値を受ける setter が存在するため, 総称型とした
///
/// @param <P> 前ステップ値の永続化クラスの型.
/// @param <C> 成分値の永続化クラスの型.
/// @param <Q> 成分値の前ステップ値の永続化クラスの型.
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class CellEntity<
    P extends CellEntity.Previous,
    C extends CellEntity.ComponentValue<Q>,
    Q extends CellEntity.ComponentValue.Previous> {

  /// 前ステップ値の永続化オブジェクト.
  private P previousEntity;

  /// 成分値の永続化オブジェクトのマップ.
  private final Map<ComponentKey, C> componentEntityMap = new EnumMap<>(ComponentKey.class);

  /// セルの永続化クラスのインスタンスを生成する.
  ///
  /// @param previousEntity 前ステップ値の永続化オブジェクト.
  CellEntity(P previousEntity) {
    this.previousEntity = previousEntity;
  }

  /// 前ステップ値の永続化オブジェクトを返す.
  ///
  /// @return 前ステップ値の永続化オブジェクト.
  @JsonProperty("previous")
  public final P getPreviousEntity() {
    return this.previousEntity;
  }

  /// 前ステップ値の永続化オブジェクトを設定する.
  ///
  /// @param previousEntity 前ステップ値の新たな永続化オブジェクト.
  /// @throws NullPointerException `previousEntity` が `null` の場合.
  @JsonProperty("previous")
  public final void setPreviousEntity(P previousEntity) {
    this.previousEntity = Preconditions.checkNotNull(previousEntity);
  }

  /// 成分値の永続化オブジェクトの不変マップを返す.
  ///
  /// @return 成分値の永続化オブジェクトの不変マップ.
  @JsonProperty("component")
  public final Map<ComponentKey, C> getComponentEntityMap() {
    return this.componentEntityMap;
  }

  /// 成分値の永続化オブジェクトのマップを設定する.
  ///
  /// @param componentEntityMap 成分値の永続化オブジェクトの新たなマップ.
  /// @throws NullPointerException `componentEntityMap` またはそのキーや値が `null` の場合.
  @JsonProperty("component")
  public final void setComponentEntityMap(Map<ComponentKey, C> componentEntityMap) {
    Preconditions.checkNotNull(componentEntityMap).forEach((key, value) -> {
      Preconditions.checkNotNull(key);
      Preconditions.checkNotNull(value);
    });

    this.componentEntityMap.clear();
    this.componentEntityMap.putAll(componentEntityMap);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    CellEntity<?, ?, ?> that = (CellEntity<?, ?, ?>) o;

    return this.previousEntity.equals(that.previousEntity)
        && this.componentEntityMap.equals(that.componentEntityMap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.previousEntity,
        this.componentEntityMap);
  }

  /// セルの前ステップ値の永続化クラスの抽象実装.
  abstract static class Previous {

    /// セルの前ステップ値の永続化クラスのインスタンスを生成する.
    Previous() {}

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      return o != null && o.getClass() == this.getClass();
    }

    @Override
    public int hashCode() {
      return Objects.hash(this.getClass());
    }

  } // Previous

  /// セルの成分値の永続化クラスの抽象実装.
  ///
  /// @param <Q> 前ステップ値の型.
  abstract static class ComponentValue<Q extends ComponentValue.Previous> {

    /// 前ステップ値の永続化オブジェクト.
    private Q previousEntity;

    /// セルの成分値の永続化クラスのインスタンスを生成する.
    ///
    /// @param previousEntity 前ステップ値の永続化オブジェクト.
    ComponentValue(Q previousEntity) {
      this.previousEntity = previousEntity;
    }

    /// 前ステップ値の永続化オブジェクトを返す.
    ///
    /// @return 前ステップ値の永続化オブジェクト.
    @JsonProperty("previous")
    public final Q getPreviousEntity() {
      return this.previousEntity;
    }

    /// 前ステップ値の永続化オブジェクトを設定する.
    ///
    /// @param previousEntity 前ステップ値の新たな永続化オブジェクト.
    /// @throws NullPointerException `previous` が `null` の場合.
    @JsonProperty("previous")
    public final void setPreviousEntity(Q previousEntity) {
      this.previousEntity = Preconditions.checkNotNull(previousEntity);
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o == null || o.getClass() != this.getClass()) {
        return false;
      }

      ComponentValue<?> that = (ComponentValue<?>) o;

      return this.previousEntity.equals(that.previousEntity);
    }

    @Override
    public int hashCode() {
      return Objects.hash(
          this.getClass(),
          this.getPreviousEntity());
    }

    /// セルの成分値の前ステップ値の永続化クラスの抽象実装.
    abstract static class Previous {

      /// セルの成分値の前ステップ値の永続化クラスのインスタンスを生成する.
      Previous() {}

      @Override
      public boolean equals(Object o) {
        if (o == this) {
          return true;
        }
        return o != null && o.getClass() == this.getClass();
      }

      @Override
      public int hashCode() {
        return Objects.hash(this.getClass());
      }

    } // Previous

  } // ComponentValue

}
