/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.Map;

/// 吸着筒のフェイス.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class ColumnFace2 implements GridFace, FluidFace2 {

  /// 数値の位置.
  private enum Position {

    /// 座標の位置.
    COORDINATE,

    /// 流体領域の断面積の位置.
    FLUID_AREA,

    /// 容器領域の断面積の位置.
    SHELL_AREA,

    /// 流体の質量流量の位置.
    MASS_FLOW,

    /// 流体のエネルギ流量の位置.
    ENERGY_FLOW

  } // Position

  /// 数値の位置のオフセット量.
  private final int offset;

  /// 吸着筒のフェイスの成分の不変マップ.
  private final Map<ComponentKey, ComponentValue> componentMap;

  /// 吸着筒のフェイスのインスタンスを生成する.
  ///
  /// @param offset 数値の位置のオフセット量.
  /// @param composition 組成.
  /// @param shape 形状.
  /// @param coordinate 座標 (m).
  ColumnFace2(int offset, Composition composition, ColumnShape shape, double coordinate) {
    int o = this.offset = offset;
    o += Position.values().length;

    Map<ComponentKey, ComponentValue> map = new EnumMap<>(ComponentKey.class);
    for (ComponentKey componentKey : composition.componentKeySet()) {
      map.put(componentKey, new ComponentValue(o));
      o += ComponentValue.number();
    }
    this.componentMap = Maps.immutableEnumMap(map);

    // 要注意 : コンストラクタ内からインスタンスメソッドを利用
    this.setValue(Position.COORDINATE, coordinate);
    this.setValue(Position.FLUID_AREA, shape.fluidArea(coordinate));
    this.setValue(Position.SHELL_AREA, shape.shellArea(coordinate));
  }

  /// 数値の数を返す.
  ///
  /// @param composition 組成.
  /// @return 数値の数.
  static int number(Composition composition) {
    return Position.values().length
        + composition.componentKeySet().size() * ColumnFace2.ComponentValue.number();
  }

  /// 数値を記憶する配列を返す.
  ///
  /// @return 数値を記憶する配列.
  abstract double[] doubleArray();

  /// 指定された数値を返す.
  ///
  /// @param position 数値の位置.
  /// @return 指定された数値.
  private double getValue(Position position) {
    return this.doubleArray()[this.offset + position.ordinal()];
  }

  /// 指定された数値を設定する.
  ///
  /// @param position 数値の位置.
  /// @param value 指定された新な数値.
  private void setValue(Position position, double value) {
    this.doubleArray()[this.offset + position.ordinal()] = value;
  }

  @Override
  public double coordinate() {
    return this.getValue(Position.COORDINATE);
  }

  @Override
  public double fluidArea() {
    return this.getValue(Position.FLUID_AREA);
  }

  @Override
  public double shellArea() {
    return this.getValue(Position.SHELL_AREA);
  }

  @Override
  public double getMassFlow() {
    return this.getValue(Position.MASS_FLOW);
  }

  @Override
  public void setMassFlow(double massFlow) {
    this.setValue(Position.MASS_FLOW, massFlow);
  }

  @Override
  public double getEnergyFlow() {
    return this.getValue(Position.ENERGY_FLOW);
  }

  @Override
  public void setEnergyFlow(double energyFlow) {
    this.setValue(Position.ENERGY_FLOW, energyFlow);
  }

  @Override
  public Map<ComponentKey, ComponentValue> componentMap() {
    return this.componentMap;
  }

  /// 吸着筒のフェイスの成分値.
  final class ComponentValue implements FluidFace2.ComponentValue {

    /// 数値の位置.
    private enum Position {

      /// 流体成分の化学種流量の位置.
      SPECIES_FLOW

    } // Position

    /// 数値の位置のオフセット量.
    private final int offset;

    /// 吸着筒のフェイスの成分値のインスタンスを生成する.
    ///
    /// @param offset 数値の位置のオフセット量.
    ComponentValue(int offset) {
      this.offset = offset;
    }

    /// 吸着筒のフェイスの成分値における数値の数を返す.
    ///
    /// @return 吸着筒のフェイスの成分値における数値の数.
    private static int number() {
      return Position.values().length;
    }

    /// 指定された数値を返す.
    ///
    /// @param position 数値の位置.
    /// @return 指定された数値.
    @SuppressWarnings("SameParameterValue")
    private double getValue(Position position) {
      return ColumnFace2.this.doubleArray()[this.offset + position.ordinal()];
    }

    /// 指定された数値を設定する.
    ///
    /// @param position 数値の位置.
    /// @param value 指定された新な数値.
    @SuppressWarnings("SameParameterValue")
    private void setValue(Position position, double value) {
      ColumnFace2.this.doubleArray()[this.offset + position.ordinal()] = value;
    }

    @Override
    public double getSpeciesFlow() {
      return this.getValue(Position.SPECIES_FLOW);
    }

    @Override
    public void setSpeciesFlow(double speciesFlow) {
      this.setValue(Position.SPECIES_FLOW, speciesFlow);
    }

  } // ComponentValue

}
