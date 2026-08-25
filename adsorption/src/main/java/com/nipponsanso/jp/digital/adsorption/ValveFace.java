/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.Map;

/// 弁フェイス.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
final class ValveFace extends FluidFace {

  /// プリミティブ double 値を保持する配列.
  private final double[] doubleArray;

  /// 成分の不変マップ.
  private final Map<ComponentKey, ComponentValue> componentMap;

  /// 弁フェイスのインスタンスを生成する.
  ///
  /// @param composition 組成.
  ValveFace(Composition composition) {
    this.doubleArray
        = new double[
            FluidFace.SIZE
                + composition.componentKeySet().size() * FluidFace.ComponentValue.SIZE];

    int offset = FluidFace.SIZE;
    Map<ComponentKey, ComponentValue> componentMap = new EnumMap<>(ComponentKey.class);
    for (ComponentKey componentKey : composition.componentKeySet()) {
      componentMap.put(componentKey, new ComponentValue(offset));
      offset += FluidFace.ComponentValue.SIZE;
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
  Map<ComponentKey, ? extends ComponentValue> componentMap() {
    return this.componentMap;
  }

  /// 弁フェイスの成分値.
  final class ComponentValue extends FluidFace.ComponentValue {

    /// オフセット値.
    private final int offset;

    /// 弁フェイスの成分値のインスタンスを生成する.
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
      return ValveFace.this.doubleArray;
    }

  } // ComponentValue

}
