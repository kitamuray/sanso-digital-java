/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.collect.Maps;
import java.util.Map;

/// 貯槽のセル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
final class TankCell2 extends AbstractFluidCell {

  /// 貯槽.
  private final Tank tank;

  /// 前ステップ値.
  private final Previous previous;

  /// 成分の不変マップ.
  private final Map<ComponentKey, ComponentValue> componentMap;

  /// 貯槽のセルのインスタンスを生成する.
  ///
  /// @param tank 貯槽.
  /// @param composition 組成.
  TankCell2(Tank tank, Composition composition) {
    this.tank = tank;
    this.previous = new Previous();
    this.componentMap = composition.componentKeySet().stream()
        .collect(
            Maps.toImmutableEnumMap(
                componentKey -> componentKey,
                _ -> new ComponentValue()));
  }

  /// 流体が占める体積を返す.
  ///
  /// @return 流体が占める体積 (m<sup>3</sup>).
  double fluidVolume() {
    return this.tank.volume();
  }

  @Override
  public Previous previous() {
    return this.previous;
  }

  @Override
  public Map<ComponentKey, ComponentValue> componentMap() {
    return this.componentMap;
  }

  /// 貯槽のセルの前ステップ値.
  static final class Previous extends AbstractFluidCell.Previous {

    /// 貯槽のセルの前ステップ値のインスタンスを生成する.
    private Previous() {}

  } // Previous

  /// 貯槽のセルの成分値.
  static final class ComponentValue extends AbstractFluidCell.ComponentValue {

    /// 前ステップ値.
    private final Previous previous;

    /// 貯槽のセルの成分値のインスタンスを生成する.
    private ComponentValue() {
      this.previous = new Previous();
    }

    @Override
    public Previous previous() {
      return this.previous;
    }

    /// 貯槽のセルの成分値の前ステップ値.
    static final class Previous extends AbstractFluidCell.ComponentValue.Previous {

      /// 貯槽のセルの成分値の前ステップ値のインスタンスを生成する.
      private Previous() {}

    } // ComponentValue.Previous

  } // ComponentValue

}
