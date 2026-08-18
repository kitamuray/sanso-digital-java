/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.ToDoubleFunction;

/// モニタ.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public abstract class Monitor {

  /// 文字列表現.
  private final String toString;

  /// モニタのインスタンスを生成する.
  ///
  /// @param toString 文字列表現.
  Monitor(String toString) {
    this.toString = toString;
  }

  /// 指定されたオブジェクトの有する従属変数の値を返す.
  ///
  /// @param object オブジェクト.
  /// @return 指定されたオブジェクトの有する従属変数の値.
  /// @throws NullPointerException `object` が `null` の場合.
  /// @throws ClassCastException `object` が対象とするデータ型ではない場合.
  abstract double applyAsDouble(Object object);

  @Override
  public String toString() {
    return this.toString;
  }

  /// 流体セルのモニタ.
  private static final class FluidCellMonitor extends Monitor {

    /// 流体セルを引数に取り従属変数の値を返す関数.
    private final ToDoubleFunction<FluidCell> function;

    /// 流体セルのモニタのインスタンスを生成する.
    ///
    /// @param toString 文字列表現.
    /// @param function 流体セルを引数に取り従属変数の値を返す関数.
    private FluidCellMonitor(String toString, ToDoubleFunction<FluidCell> function) {
      super(toString);
      this.function = function;
    }

    @Override
    double applyAsDouble(Object object) {
      Preconditions.checkNotNull(object);
      if (!(object instanceof FluidCell)) {
        throw new ClassCastException();
      }

      return this.function.applyAsDouble((FluidCell) object);
    }

    /// 流体の熱力学的温度 (K) のモニタ.
    private static final FluidCellMonitor TEMPERATURE
        = new FluidCellMonitor("temperature", FluidCell::getTemperature);

    /// 流体の絶対圧力 (Pa) のモニタ.
    private static final FluidCellMonitor PRESSURE
        = new FluidCellMonitor("pressure", FluidCell::getPressure);

    /// 流体の密度 (kg m<sup>-3</sup>) のモニタ.
    private static final FluidCellMonitor DENSITY
        = new FluidCellMonitor("density", FluidCell::getDensity);

    /// 流体の比エンタルピ (J kg<sup>-1</sup>) のモニタ.
    private static final FluidCellMonitor SPECIFIC_ENTHALPY
        = new FluidCellMonitor("specificEnthalpy", FluidCell::getSpecificEnthalpy);

    /// 流体の定圧比熱 (J kg<sup>-1</sup> K<sup>-1</sup>) のモニタ.
    private static final FluidCellMonitor SPECIFIC_CONSTANT_PRESSURE_SPECIFIC_HEAT
        = new FluidCellMonitor(
            "specificConstantPressureSpecificHeat",
            FluidCell::getSpecificConstantPressureSpecificHeat);

    /// 流体の粘性係数 (Pa s) のモニタ.
    private static final FluidCellMonitor VISCOSITY
        = new FluidCellMonitor("viscosity", FluidCell::getViscosity);

    /// 流体の熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>) のモニタ.
    private static final FluidCellMonitor THERMAL_CONDUCTIVITY
        = new FluidCellMonitor("thermalConductivity", FluidCell::getThermalConductivity);

  } // FluidCellMonitor

  /// 流体の熱力学的温度のモニタを返す.
  ///
  /// @return 流体の熱力学的温度 (K) のモニタ.
  public static Monitor temperature() {
    return FluidCellMonitor.TEMPERATURE;
  }

  /// 流体の絶対圧力のモニタを返す.
  ///
  /// @return 流体の絶対圧力 (Pa) のモニタ.
  public static Monitor pressure() {
    return FluidCellMonitor.PRESSURE;
  }

  /// 流体の密度のモニタを返す.
  ///
  /// @return 流体の密度 (kg m<sup>-3</sup>) のモニタ.
  public static Monitor density() {
    return FluidCellMonitor.DENSITY;
  }

  /// 流体の比エンタルピのモニタを返す.
  ///
  /// @return 流体の比エンタルピ (J kg<sup>-1</sup>) のモニタ.
  public static Monitor specificEnthalpy() {
    return FluidCellMonitor.SPECIFIC_ENTHALPY;
  }

  /// 流体の定圧比熱のモニタを返す.
  ///
  /// @return 流体の定圧比熱 (J kg<sup>-1</sup> K<sup>-1</sup>)のモニタ.
  public static Monitor specificConstantPressureSpecificHeat() {
    return FluidCellMonitor.SPECIFIC_CONSTANT_PRESSURE_SPECIFIC_HEAT;
  }

  /// 流体の粘性係数のモニタを返す.
  ///
  /// @return 流体の粘性係数 (Pa s) のモニタ.
  public static Monitor viscosity() {
    return FluidCellMonitor.VISCOSITY;
  }

  /// 流体の熱伝導率のモニタを返す.
  ///
  /// @return 流体の熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>) のモニタ.
  public static Monitor thermalConductivity() {
    return FluidCellMonitor.THERMAL_CONDUCTIVITY;
  }

  /// 流体セル成分値のモニタ.
  private static final class FluidValueMonitor extends Monitor {

    /// 成分のキー.
    private final ComponentKey componentKey;

    /// 流体セル成分値を引数に取り従属変数の値を返す関数.
    private final ToDoubleFunction<FluidCell.ComponentValue> function;

    /// 流体セル成分値のモニタのインスタンスを生成する.
    ///
    /// @param toString 文字列表現.
    /// @param componentKey 成分のキー.
    /// @param function 流体セル成分値を引数に取り従属変数の値を返す関数.
    private FluidValueMonitor(
        String toString,
        ComponentKey componentKey,
        ToDoubleFunction<FluidCell.ComponentValue> function) {
      super(toString + "[" + componentKey + "]");
      this.componentKey = componentKey;
      this.function = function;
    }

    @Override
    double applyAsDouble(Object object) {
      Preconditions.checkNotNull(object);
      if (!(object instanceof FluidCell)) {
        throw new ClassCastException();
      }

      return this.function.applyAsDouble(
          ((FluidCell) object).componentMap().get(this.componentKey));
    }

    /// 流体成分の質量分率 (-) のモニタのマップ.
    private static final Map<ComponentKey, FluidValueMonitor> FRACTION_MAP
        = new EnumMap<>(ComponentKey.class);

    /// 流体成分の物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>) のモニタのマップ.
    private static final Map<ComponentKey, FluidValueMonitor> DIFFUSIVITY_MAP
        = new EnumMap<>(ComponentKey.class);

  } // FluidValueMonitor

  /// 流体成分の質量分率のモニタを返す.
  ///
  /// @param componentKey 成分のキー.
  /// @return 流体成分の質量分率 (-) のモニタ.
  /// @throws NullPointerException `componentKey` が `null` の場合.
  public static Monitor fraction(ComponentKey componentKey) {
    Preconditions.checkNotNull(componentKey);

    return FluidValueMonitor.FRACTION_MAP.computeIfAbsent(
        componentKey,
        key -> new FluidValueMonitor(
            "fraction", key, FluidCell.ComponentValue::getFraction));
  }

  /// 流体成分の質量分率のモニタを返す.
  ///
  /// @param componentKey 成分のキー.
  /// @return 流体成分の質量分率 (-) のモニタ.
  /// @throws NullPointerException `componentKey` が `null` の場合.
  public static Monitor diffusivity(ComponentKey componentKey) {
    Preconditions.checkNotNull(componentKey);

    return FluidValueMonitor.DIFFUSIVITY_MAP.computeIfAbsent(
        componentKey,
        key -> new FluidValueMonitor(
            "fraction", key, FluidCell.ComponentValue::getDiffusivity));
  }


}
