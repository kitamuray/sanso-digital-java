/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.ToDoubleFunction;

/// モニタ.
///
/// このクラスを実装した具象クラスのインスタンスは不変（Immutable）である.
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
  static final class FluidCellMonitor extends Monitor {

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
  /// このメソッドは常に同じインスタンスを返し,
  /// 無条件のスレッド安全（Unconditionally thread-safe）である.
  ///
  /// @return 流体の熱力学的温度 (K) のモニタ.
  public static Monitor temperature() {
    return FluidCellMonitor.TEMPERATURE;
  }

  /// 流体の絶対圧力のモニタを返す.
  ///
  /// このメソッドは常に同じインスタンスを返し,
  /// 無条件のスレッド安全（Unconditionally thread-safe）である.
  ///
  /// @return 流体の絶対圧力 (Pa) のモニタ.
  public static Monitor pressure() {
    return FluidCellMonitor.PRESSURE;
  }

  /// 流体の密度のモニタを返す.
  ///
  /// このメソッドは常に同じインスタンスを返し,
  /// 無条件のスレッド安全（Unconditionally thread-safe）である.
  ///
  /// @return 流体の密度 (kg m<sup>-3</sup>) のモニタ.
  public static Monitor density() {
    return FluidCellMonitor.DENSITY;
  }

  /// 流体の比エンタルピのモニタを返す.
  ///
  /// このメソッドは常に同じインスタンスを返し,
  /// 無条件のスレッド安全（Unconditionally thread-safe）である.
  ///
  /// @return 流体の比エンタルピ (J kg<sup>-1</sup>) のモニタ.
  public static Monitor specificEnthalpy() {
    return FluidCellMonitor.SPECIFIC_ENTHALPY;
  }

  /// 流体の定圧比熱のモニタを返す.
  ///
  /// このメソッドは常に同じインスタンスを返し,
  /// 無条件のスレッド安全（Unconditionally thread-safe）である.
  ///
  /// @return 流体の定圧比熱 (J kg<sup>-1</sup> K<sup>-1</sup>)のモニタ.
  public static Monitor specificConstantPressureSpecificHeat() {
    return FluidCellMonitor.SPECIFIC_CONSTANT_PRESSURE_SPECIFIC_HEAT;
  }

  /// 流体の粘性係数のモニタを返す.
  ///
  /// このメソッドは常に同じインスタンスを返し,
  /// 無条件のスレッド安全（Unconditionally thread-safe）である.
  ///
  /// @return 流体の粘性係数 (Pa s) のモニタ.
  public static Monitor viscosity() {
    return FluidCellMonitor.VISCOSITY;
  }

  /// 流体の熱伝導率のモニタを返す.
  ///
  /// このメソッドは常に同じインスタンスを返し,
  /// 無条件のスレッド安全（Unconditionally thread-safe）である.
  ///
  /// @return 流体の熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>) のモニタ.
  public static Monitor thermalConductivity() {
    return FluidCellMonitor.THERMAL_CONDUCTIVITY;
  }

  /// 流体セル成分値のモニタ.
  static final class FluidCellComponentValueMonitor extends Monitor {

    /// 成分のキー.
    private final ComponentKey componentKey;

    /// 流体セル成分値を引数に取り従属変数の値を返す関数.
    private final ToDoubleFunction<FluidCell.ComponentValue> function;

    /// 流体セル成分値のモニタのインスタンスを生成する.
    ///
    /// @param toString 文字列表現.
    /// @param componentKey 成分のキー.
    /// @param function 流体セル成分値を引数に取り従属変数の値を返す関数.
    private FluidCellComponentValueMonitor(
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
    private static final Map<ComponentKey, FluidCellComponentValueMonitor> FRACTION_MAP
        = new ConcurrentHashMap<>();

    /// 流体成分の物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>) のモニタのマップ.
    private static final Map<ComponentKey, FluidCellComponentValueMonitor> DIFFUSIVITY_MAP
        = new ConcurrentHashMap<>();

  } // FluidCellComponentValueMonitor

  /// 流体成分の質量分率のモニタを返す.
  ///
  /// このメソッドは指定された `majorComponentKey` に対し常に同じインスタンスを返し,
  /// 無条件のスレッド安全（Unconditionally thread-safe）である.
  ///
  /// @param componentKey 成分のキー.
  /// @return 流体成分の質量分率 (-) のモニタ.
  /// @throws NullPointerException `majorComponentKey` が `null` の場合.
  public static Monitor fraction(ComponentKey componentKey) {
    Preconditions.checkNotNull(componentKey);

    return FluidCellComponentValueMonitor.FRACTION_MAP.computeIfAbsent(
        componentKey,
        key -> new FluidCellComponentValueMonitor(
            "fraction", key, FluidCell.ComponentValue::getFraction));
  }

  /// 流体成分の質量分率のモニタを返す.
  ///
  /// このメソッドは指定された `majorComponentKey` に対し常に同じインスタンスを返し,
  /// 無条件のスレッド安全（Unconditionally thread-safe）である.
  ///
  /// @param componentKey 成分のキー.
  /// @return 流体成分の質量分率 (-) のモニタ.
  /// @throws NullPointerException `majorComponentKey` が `null` の場合.
  public static Monitor diffusivity(ComponentKey componentKey) {
    Preconditions.checkNotNull(componentKey);

    return FluidCellComponentValueMonitor.DIFFUSIVITY_MAP.computeIfAbsent(
        componentKey,
        key -> new FluidCellComponentValueMonitor(
            "diffusivity", key, FluidCell.ComponentValue::getDiffusivity));
  }

  /// 容器セルのモニタ.
  static final class ShellCellMonitor extends Monitor {

    /// 容器セルを引数に取り従属変数の値を返す関数.
    private final ToDoubleFunction<ShellCell> function;

    /// 容器セルのモニタのインスタンスを生成する.
    ///
    /// @param toString 文字列表現.
    /// @param function 容器セルを引数に取り従属変数の値を返す関数.
    private ShellCellMonitor(String toString, ToDoubleFunction<ShellCell> function) {
      super(toString);
      this.function = function;
    }

    @Override
    double applyAsDouble(Object object) {
      Preconditions.checkNotNull(object);
      if (!(object instanceof ShellCell)) {
        throw new ClassCastException();
      }

      return this.function.applyAsDouble((ShellCell) object);
    }

    /// 容器の熱力学的温度 (K) のモニタ.
    private static final ShellCellMonitor SHELL_TEMPERATURE
        = new ShellCellMonitor(
            "shellTemperature",
            ShellCell::getShellTemperature);

    /// 容器の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>) のモニタ.
    private static final ShellCellMonitor SHELL_SPECIFIC_HEAT
        = new ShellCellMonitor(
            "shellSpecificHeat",
            ShellCell::getShellSpecificHeat);

    /// 容器の熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>) のモニタ.
    private static final ShellCellMonitor SHELL_THERMAL_CONDUCTIVITY
        = new ShellCellMonitor(
            "shellThermalConductivity",
            ShellCell::getShellThermalConductivity);

  } // ShellCellMonitor

  /// 容器の熱力学的温度 (K) のモニタを返す.
  ///
  /// このメソッドは常に同じインスタンスを返し,
  /// 無条件のスレッド安全（Unconditionally thread-safe）である.
  ///
  /// @return 容器の熱力学的温度 (K) のモニタ.
  public static Monitor shellTemperature() {
    return ShellCellMonitor.SHELL_TEMPERATURE;
  }

  /// 容器の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>) のモニタを返す.
  ///
  /// このメソッドは常に同じインスタンスを返し,
  /// 無条件のスレッド安全（Unconditionally thread-safe）である.
  ///
  /// @return 容器の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>) のモニタ.
  public static Monitor shellSpecificHeat() {
    return ShellCellMonitor.SHELL_SPECIFIC_HEAT;
  }

  /// 容器の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>) のモニタを返す.
  ///
  /// このメソッドは常に同じインスタンスを返し,
  /// 無条件のスレッド安全（Unconditionally thread-safe）である.
  ///
  /// @return 容器の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>) のモニタ.
  public static Monitor shellThermalConductivity() {
    return ShellCellMonitor.SHELL_THERMAL_CONDUCTIVITY;
  }

  /// 吸着剤セルのモニタ.
  static final class AdsorbentCellMonitor extends Monitor {

    /// 吸着剤セルを引数に取り従属変数の値を返す関数.
    private final ToDoubleFunction<AdsorbentCell> function;

    /// 吸着剤セルのモニタのインスタンスを生成する.
    ///
    /// @param toString 文字列表現.
    /// @param function 吸着剤セルを引数に取り従属変数の値を返す関数.
    private AdsorbentCellMonitor(String toString, ToDoubleFunction<AdsorbentCell> function) {
      super(toString);
      this.function = function;
    }

    @Override
    double applyAsDouble(Object object) {
      Preconditions.checkNotNull(object);
      if (!(object instanceof AdsorbentCell)) {
        throw new ClassCastException();
      }

      return this.function.applyAsDouble((AdsorbentCell) object);
    }

    /// 吸着剤の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>) のモニタ.
    private static final AdsorbentCellMonitor ADSORBENT_SPECIFIC_HEAT
        = new AdsorbentCellMonitor(
        "adsorbentSpecificHeat",
        AdsorbentCell::getAdsorbentSpecificHeat);

  } // AdsorbentCellMonitor

  /// 吸着剤の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>) のモニタを返す.
  ///
  /// このメソッドは常に同じインスタンスを返し,
  /// 無条件のスレッド安全（Unconditionally thread-safe）である.
  ///
  /// @return 吸着剤の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>) のモニタ.
  public static Monitor adsorbentSpecificHeat() {
    return AdsorbentCellMonitor.ADSORBENT_SPECIFIC_HEAT;
  }

  /// 吸着剤セル成分値のモニタ.
  static final class AdsorbentCellComponentValueMonitor extends Monitor {

    /// 成分のキー.
    private final ComponentKey componentKey;

    /// 吸着剤セル成分値を引数に取り従属変数の値を返す関数.
    private final ToDoubleFunction<AdsorbentCell.ComponentValue> function;

    /// 吸着剤セル成分値のモニタのインスタンスを生成する.
    ///
    /// @param toString 文字列表現.
    /// @param componentKey 成分のキー.
    /// @param function 吸着剤セル成分値を引数に取り従属変数の値を返す関数.
    private AdsorbentCellComponentValueMonitor(
        String toString,
        ComponentKey componentKey,
        ToDoubleFunction<AdsorbentCell.ComponentValue> function) {
      super(toString + "[" + componentKey + "]");
      this.componentKey = componentKey;
      this.function = function;
    }

    @Override
    double applyAsDouble(Object object) {
      Preconditions.checkNotNull(object);
      if (!(object instanceof AdsorbentCell)) {
        throw new ClassCastException();
      }

      return this.function.applyAsDouble(
          ((AdsorbentCell) object).componentMap().get(this.componentKey));
    }

    /// 吸着質成分のモル吸着量 (mol kg<sup>-1</sup>) のモニタのマップ.
    private static final Map<ComponentKey, AdsorbentCellComponentValueMonitor> MOLAR_QUANTITY_MAP
        = new ConcurrentHashMap<>();

    /// 吸着質成分のモル定積比熱 (J mol<sup>-1</sup> K<sup>-1</sup>) のモニタのマップ.
    private static final Map<ComponentKey, AdsorbentCellComponentValueMonitor>
        MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT_MAP
          = new ConcurrentHashMap<>();

  } // AdsorbentCellComponentValueMonitor

  /// 吸着質成分のモル吸着量のモニタを返す.
  ///
  /// このメソッドは指定された `majorComponentKey` に対し常に同じインスタンスを返し,
  /// 無条件のスレッド安全（Unconditionally thread-safe）である.
  ///
  /// @param componentKey 成分のキー.
  /// @return 吸着質成分のモル吸着量 (mol kg<sup>-1</sup>) のモニタ.
  /// @throws NullPointerException `majorComponentKey` が `null` の場合.
  public static Monitor molarQuantity(ComponentKey componentKey) {
    Preconditions.checkNotNull(componentKey);

    return AdsorbentCellComponentValueMonitor.MOLAR_QUANTITY_MAP.computeIfAbsent(
        componentKey,
        key -> new AdsorbentCellComponentValueMonitor(
            "molarQuantity", key, AdsorbentCell.ComponentValue::getMolarQuantity));
  }

  /// 吸着質成分のモル定積比熱のモニタを返す.
  ///
  /// このメソッドは指定された `majorComponentKey` に対し常に同じインスタンスを返し,
  /// 無条件のスレッド安全（Unconditionally thread-safe）である.
  ///
  /// @param componentKey 成分のキー.
  /// @return 吸着質成分のモル定積比熱 (J mol<sup>-1</sup> K<sup>-1</sup>) のモニタ.
  /// @throws NullPointerException `majorComponentKey` が `null` の場合.
  public static Monitor molarSpecificConstantVolumeSpecificHeat(ComponentKey componentKey) {
    Preconditions.checkNotNull(componentKey);

    return AdsorbentCellComponentValueMonitor.MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT_MAP
        .computeIfAbsent(
            componentKey,
            key -> new AdsorbentCellComponentValueMonitor(
                "molarSpecificConstantVolumeSpecificHeat",
                key,
                AdsorbentCell.ComponentValue::getMolarSpecificConstantVolumeSpecificHeat));
  }

}
