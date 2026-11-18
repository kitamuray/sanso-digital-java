/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.Map;

/// 貯槽のセル.
///
/// - 貯槽を対象としたセルのコンクリート実装
/// - 永続化の対象である
///
/// @since 1.0
/// @author KITAMURA Yuichiro
final class TankCell extends FluidCell {

  /// 変数の数.
  private static final int SIZE = FluidCell.SIZE;

  /// プリミティブ double 型の数値を格納する配列.
  private final double[] doubleArray;

  /// 前ステップ値.
  private final Previous previous;

  /// 成分の不変マップ.
  private final Map<ComponentKey, ComponentValue> componentMap;

  /// 貯槽のセルのインスタンスを生成する.
  ///
  /// @param composition 組成.
  TankCell(Composition composition) {
    this.doubleArray = new double[size(composition)];

    this.previous = new Previous();

    Map<ComponentKey, ComponentValue> componentMap = new EnumMap<>(ComponentKey.class);
    int offset = size();
    for (var componentKey : composition.componentKeySet()) {
      componentMap.put(componentKey, new ComponentValue(offset));
      offset += ComponentValue.size();
    }

    this.componentMap = Maps.immutableEnumMap(componentMap);
  }

  /// 永続化オブジェクトへ情報を再帰的に書き込む.
  ///
  /// @param entity 永続化オブジェクト.
  void write(TankCellEntity entity) {
    super.write(entity);

    this.previous.write(entity.getPreviousEntity());
    this.componentMap.forEach(
        (key, value) -> value.write(
            entity.getComponentEntityMap().computeIfAbsent(
                key, _ -> new TankCellEntity.ComponentValue())));
  }

  /// 永続化オブジェクトから情報を再帰的に読み込む.
  ///
  /// @param entity 永続化オブジェクト.
  void read(TankCellEntity entity) {
    super.read(entity);

    this.previous.read(entity.getPreviousEntity());
    this.componentMap.forEach(
        (key, value) -> value.read(
            entity.getComponentEntityMap().get(key)));
  }

  @Override
  Previous previous() {
    return this.previous;
  }

  @Override
  Map<ComponentKey, ComponentValue> componentMap() {
    return this.componentMap;
  }

  @Override
  int offset() {
    return 0;
  }

  @Override
  double[] doubleArray() {
    return this.doubleArray;
  }

  /// 成分値以外における変数の数を返す.
  ///
  /// @return 成分値以外における変数の数.
  private static int size() {
    return SIZE + Previous.SIZE;
  }

  /// すべての変数の数を返す.
  ///
  /// @return すべての変数の数.
  static int size(Composition composition) {
    return size() + composition.componentKeySet().size() * ComponentValue.size();
  }

  /// 貯槽のセルの前ステップ値.
  final class Previous extends FluidCell.Previous {

    /// 変数の数.
    private static final int SIZE = FluidCell.Previous.SIZE;

    /// 貯槽のセルの前ステップ値のインスタンスを生成する.
    private Previous() {}

    /// 永続化オブジェクトへ情報を書き込む.
    ///
    /// @param entity 永続化オブジェクト.
    private void write(TankCellEntity.Previous entity) {
      super.write(entity);
    }

    /// 永続化オブジェクトから情報を読み込む.
    ///
    /// @param entity 永続化オブジェクト.
    private void read(TankCellEntity.Previous entity) {
      super.read(entity);
    }

    @Override
    int offset() {
      return TankCell.this.offset() + TankCell.SIZE;
    }

    @Override
    double[] doubleArray() {
      return TankCell.this.doubleArray();
    }

  } // Previous

  /// 貯槽のセルの成分値.
  final class ComponentValue extends FluidCell.ComponentValue {

    /// 変数の数.
    private static final int SIZE = FluidCell.ComponentValue.SIZE;

    /// オフセット値.
    private final int offset;

    /// 前ステップ値.
    private final Previous previous;

    /// 貯槽のセルの成分値のインスタンスを生成する.
    ///
    /// @param offset オフセット値.
    private ComponentValue(int offset) {
      this.offset = offset;
      this.previous = new Previous();
    }

    /// 永続化オブジェクトへ情報を再帰的に書き込む.
    ///
    /// @param entity 永続化オブジェクト.
    private void write(TankCellEntity.ComponentValue entity) {
      super.write(entity);

      this.previous.write(entity.getPreviousEntity());
    }

    /// 永続化オブジェクトから情報を再帰的に読み込む.
    ///
    /// @param entity 永続化オブジェクト.
    private void read(TankCellEntity.ComponentValue entity) {
      super.read(entity);

      this.previous.read(entity.getPreviousEntity());
    }

    @Override
    int offset() {
      return this.offset;
    }

    @Override
    double[] doubleArray() {
      return TankCell.this.doubleArray();
    }

    private static int size() {
      return SIZE + Previous.SIZE;
    }

    @Override
    Previous previous() {
      return this.previous;
    }

    /// 貯槽のセルの成分値の前ステップ値.
    final class Previous extends FluidCell.ComponentValue.Previous {

      /// 変数の数.
      private static final int SIZE = FluidCell.ComponentValue.Previous.SIZE;

      /// 貯槽のセルの成分値の前ステップ値のインスタンスを生成する.
      private Previous() {}

      /// 永続化オブジェクトへ情報を再帰的に書き込む.
      ///
      /// @param entity 永続化オブジェクト.
      private void write(TankCellEntity.ComponentValue.Previous entity) {
        super.write(entity);
      }

      /// 永続化オブジェクトから情報を再帰的に読み込む.
      ///
      /// @param entity 永続化オブジェクト.
      private void read(TankCellEntity.ComponentValue.Previous entity) {
        super.read(entity);
      }

      @Override
      int offset() {
        return ComponentValue.this.offset() + ComponentValue.SIZE;
      }

      @Override
      double[] doubleArray() {
        return TankCell.this.doubleArray();
      }

    } // Previous

  } // ComponentValue

}
