/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.Map;

/// 系統セル.
///
/// - 系統内の流体を対象としたセルのコンクリート実装
/// - 永続化の対象ではない
///
/// @since 1.0
/// @author KITAMURA Yuichiro
final class LineCell extends FluidCell {

  /// プリミティブ double 値を格納する配列.
  private final double[] doubleArray;

  /// 前ステップ値.
  private final Previous previous;

  /// 成分の不変マップ.
  private final Map<ComponentKey, ComponentValue> componentMap;

  /// 系統セルのインスタンスを生成する.
  ///
  /// @param composition 組成.
  LineCell(Composition composition) {
    this.doubleArray = new double[
        FluidCell.SIZE + FluidCell.Previous.SIZE
            + composition.componentKeySet().size()
            * (FluidCell.ComponentValue.SIZE + FluidCell.ComponentValue.Previous.SIZE)];

    this.previous = new Previous();

    int offset = FluidCell.SIZE + FluidCell.Previous.SIZE;
    Map<ComponentKey, ComponentValue> componentMap = new EnumMap<>(ComponentKey.class);
    for (ComponentKey componentKey : composition.componentKeySet()) {
      componentMap.put(componentKey, new ComponentValue(offset));
      offset += FluidCell.ComponentValue.SIZE + FluidCell.ComponentValue.Previous.SIZE;
    }

    this.componentMap = Maps.immutableEnumMap(componentMap);
  }

  @Override
  int offset() {
    return 0;
  }

  @Override
  double[] doubleArray() {
    return this.doubleArray;
  }

  @Override
  Previous previous() {
    return this.previous;
  }

  @Override
  Map<ComponentKey, ? extends ComponentValue> componentMap() {
    return this.componentMap;
  }

  @Override
  void write(FluidCellEntity<?, ?, ?> entity) {
    throw new UnsupportedOperationException();
  }

  @Override
  void read(FluidCellEntity<?, ?, ?> entity) {
    throw new UnsupportedOperationException();
  }

  /// 系統セルの前ステップ値.
  final class Previous extends FluidCell.Previous {

    /// 系統セルの前ステップ値のインスタンスを生成する.
    private Previous() {}

    @Override
    int offset() {
      return FluidCell.SIZE;
    }

    @Override
    double[] doubleArray() {
      return LineCell.this.doubleArray;
    }

    @Override
    void write(FluidCellEntity.Previous entity) {
      throw new UnsupportedOperationException();
    }

    @Override
    void read(FluidCellEntity.Previous entity) {
      throw new UnsupportedOperationException();
    }

  } // Previous

  /// 系統セルの成分値.
  final class ComponentValue extends FluidCell.ComponentValue {

    /// オフセット値.
    private final int offset;

    /// 前ステップ値.
    private final Previous previous;

    /// 系統セルの成分値のインスタンスを生成する.
    ///
    /// @param offset オフセット値.
    private ComponentValue(int offset) {
      this.offset = offset;
      this.previous = new Previous();
    }

    @Override
    Previous previous() {
      return this.previous;
    }

    @Override
    int offset() {
      return this.offset;
    }

    @Override
    double[] doubleArray() {
      return LineCell.this.doubleArray;
    }

    @Override
    void write(FluidCellEntity.ComponentValue<?> entity) {
      throw new UnsupportedOperationException();
    }

    @Override
    void read(FluidCellEntity.ComponentValue<?> entity) {
      throw new UnsupportedOperationException();
    }

    /// 系統セルの成分値の前ステップ値.
    final class Previous extends FluidCell.ComponentValue.Previous {

      /// 系統セルの成分値の前ステップ値のインスタンスを生成する.
      private Previous() {}

      @Override
      int offset() {
        return ComponentValue.this.offset + FluidCell.ComponentValue.SIZE;
      }

      @Override
      double[] doubleArray() {
        return LineCell.this.doubleArray;
      }

      @Override
      void write(FluidCellEntity.ComponentValue.Previous entity) {
        throw new UnsupportedOperationException();
      }

      @Override
      void read(FluidCellEntity.ComponentValue.Previous entity) {
        throw new UnsupportedOperationException();
      }

    } // Previous

  } // ComponentValue

}
