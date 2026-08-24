/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.Map;

/// 吸着筒フェイス.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class ColumnFace extends FluidFace implements GridFace {

  /// 座標の位置.
  private static final int COORDINATE = FluidFace.SIZE;

  /// 流体領域の断面積の位置.
  private static final int FLUID_AREA = COORDINATE + 1;

  /// 容器の位置.
  private static final int SHELL_AREA = FLUID_AREA + 1;

  /// 変数の数.
  private static final int SIZE = SHELL_AREA + 1;

  /// オフセット値.
  private final int offset;

  /// 成分の不変マップ.
  private final Map<ComponentKey, ComponentValue> componentMap;

  /// 吸着筒フェイスのインスタンスを生成する.
  ///
  /// @param offset オフセット値.
  /// @param shape 形状.
  /// @param coordinate 座標 (m).
  /// @param composition 組成.
  ColumnFace(int offset, ColumnShape shape, double coordinate, Composition composition) {
    this.offset = offset;

    this.doubleArray()[this.offset + COORDINATE] = coordinate;
    this.doubleArray()[this.offset + FLUID_AREA] = shape.fluidArea(coordinate);
    this.doubleArray()[this.offset + SHELL_AREA] = shape.shellArea(coordinate);

    Map<ComponentKey, ComponentValue> componentMap = new EnumMap<>(ComponentKey.class);
    offset += size();
    for (var componentKey : composition.componentKeySet()) {
      componentMap.put(componentKey, new ComponentValue(offset));
      offset += ComponentValue.size();
    }

    this.componentMap = Maps.immutableEnumMap(componentMap);
  }

  /// {@inheritDoc}
  ///
  /// @return {@inheritDoc}
  public double coordinate() {
    return this.doubleArray()[this.offset() + COORDINATE];
  }

  /// {@inheritDoc}
  ///
  /// @return {@inheritDoc}
  public double fluidArea() {
    return this.doubleArray()[this.offset() + FLUID_AREA];
  }

  /// {@inheritDoc}
  ///
  /// @return {@inheritDoc}
  public double shellArea() {
    return this.doubleArray()[this.offset() + SHELL_AREA];
  }

  /// オフセット値を返す.
  ///
  /// @return オフセット値.
  @Override
  int offset() {
    return this.offset;
  }

  /// {@inheritDoc}
  ///
  /// @return {@inheritDoc}
  @Override
  Map<ComponentKey, ? extends ComponentValue> componentMap() {
    return this.componentMap;
  }

  /// 変数の数を返す.
  ///
  /// @return 変数の数.
  private static int size() {
    return SIZE;
  }

  /// フェイス全体における変数の数を返す.
  ///
  /// @return フェイス全体における変数の数.
  static int size(Composition composition) {
    return size() + composition.componentKeySet().size() * ComponentValue.size();
  }

  /// 吸着筒フェイスの成分値.
  final class ComponentValue extends FluidFace.ComponentValue {

    /// 変数の数.
    private static final int SIZE = FluidFace.ComponentValue.SIZE;

    /// オフセット値.
    private final int offset;

    /// 吸着筒フェイスの成分値のインスタンスを生成する.
    ///
    /// @param offset オフセット値.
    private ComponentValue(int offset) {
      this.offset = offset;
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
      return ColumnFace.this.doubleArray();
    }

    /// 変数の数を返す.
    ///
    /// @return 変数の数.
    private static int size() {
      return SIZE;
    }

  } // ComponentValue

}
