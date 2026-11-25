/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.collect.Maps;
import java.util.Map;

/// 境界のセル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
final class PortCell2 extends AbstractFluidCell {

  /// 前ステップ値.
  private final Previous previous;

  /// 成分の不変マップ.
  private final Map<ComponentKey, ComponentValue> componentMap;

  /// 境界のセルのインスタンスを生成する.
  ///
  /// @param composition 組成.
  PortCell2(Composition composition) {
    this.previous = new Previous();
    this.componentMap = composition.componentKeySet().stream()
        .collect(
            Maps.toImmutableEnumMap(
                componentKey -> componentKey,
                _ -> new ComponentValue()));
  }

  @Override
  public Previous previous() {
    return this.previous;
  }

  @Override
  public Map<ComponentKey, ComponentValue> componentMap() {
    return this.componentMap;
  }

  /// 境界のセルの前ステップ値.
  static final class Previous extends AbstractFluidCell.Previous {

    /// 境界のセルの前ステップ値のインスタンスを生成する.
    private Previous() {}

  } // Previous

  /// 境界のセルの成分値.
  static final class ComponentValue extends AbstractFluidCell.ComponentValue {

    /// 前ステップ値.
    private final Previous previous;

    /// 境界のセルの成分値のインスタンスを生成する.
    private ComponentValue() {
      this.previous = new Previous();
    }

    @Override
    public Previous previous() {
      return this.previous;
    }

    /// 境界のセルの成分値の前ステップ値.
    static final class Previous extends AbstractFluidCell.ComponentValue.Previous {

      /// 境界のセルの成分値の前ステップ値のインスタンスを生成する.
      private Previous() {}

    } // ComponentValue.Previous

  } // ComponentValue

}
