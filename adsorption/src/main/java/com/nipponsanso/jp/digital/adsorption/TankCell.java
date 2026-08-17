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
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class TankCell extends FluidCell {

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
    this.doubleArray = new double[
        SIZE + Previous.SIZE
            + composition.minorComponentKeySet().size()
            * (ComponentValue.SIZE + ComponentValue.Previous.SIZE)];

    this.previous = new Previous();

    Map<ComponentKey, ComponentValue> componentMap = new EnumMap<>(ComponentKey.class);
    int offset = SIZE + Previous.SIZE;
    for (var componentKey : composition.componentKeySet()) {
      componentMap.put(componentKey, new ComponentValue(offset));
      offset += ComponentValue.SIZE + ComponentValue.Previous.SIZE;
    }

    this.componentMap = Maps.immutableEnumMap(componentMap);
  }

  /// {@inheritDoc}
  ///
  /// @return {@inheritDoc}
  @Override
  int offset() {
    return 0;
  }

  /// {@inheritDoc}
  ///
  /// @return {@inheritDoc}
  @Override
  double[] doubleArray() {
    return this.doubleArray;
  }

  /// {@inheritDoc}
  ///
  /// @return {@inheritDoc}
  @Override
  Previous previous() {
    return this.previous;
  }

  /// {@inheritDoc}
  ///
  /// @return {@inheritDoc}
  @Override
  Map<ComponentKey, ComponentValue> componentMap() {
    return this.componentMap;
  }

  /// 貯槽のセルの前ステップ値.
  final class Previous extends FluidCell.Previous {

    /// 変数の数.
    private static final int SIZE = FluidCell.Previous.SIZE;

    /// 貯槽のセルの前ステップ値のインスタンスを生成する.
    private Previous() {}

    /// {@inheritDoc}
    ///
    /// @return {@inheritDoc}
    @Override
    int offset() {
      return TankCell.this.offset() + TankCell.SIZE;
    }

    /// {@inheritDoc}
    ///
    /// @return {@inheritDoc}
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

    /// {@inheritDoc}
    ///
    /// @return {@inheritDoc}
    @Override
    int offset() {
      return this.offset;
    }

    /// {@inheritDoc}
    ///
    /// @return {@inheritDoc}
    @Override
    double[] doubleArray() {
      return TankCell.this.doubleArray();
    }

    /// {@inheritDoc}
    ///
    /// @return {@inheritDoc}
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

      /// {@inheritDoc}
      ///
      /// @return {@inheritDoc}
      @Override
      int offset() {
        return ComponentValue.this.offset() + ComponentValue.SIZE;
      }

      /// {@inheritDoc}
      ///
      /// @return {@inheritDoc}
      @Override
      double[] doubleArray() {
        return TankCell.this.doubleArray();
      }

    } // Previous

  } // ComponentValue

}
