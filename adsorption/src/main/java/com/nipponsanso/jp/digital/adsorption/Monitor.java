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

  // Initialization-on-demand holder idiom を採用
  // セル・フェイスの選択が不適切な場合, compile 時ではなく runtime error とする

  /// 文字列表現.
  private final String toString;

  /// モニタのインスタンスを生成する.
  ///
  /// @param toString 文字列表現.
  private Monitor(String toString) {
    this.toString = toString;
  }

  /// 指定されたオブジェクトの有する従属変数の値を返す.
  ///
  /// @param object オブジェクト.
  /// @return 指定されたオブジェクトの有する従属変数の値.
  /// @throws NullPointerException `object` またはその構成要素が `null` の場合.
  /// @throws ClassCastException `object` が対象とするデータ型ではない場合.
  abstract double applyAsDouble(Object object);

  @Override
  public String toString() {
    return this.toString;
  }

  /// 流体セルのモニタ.
  static final class FluidCellMonitor extends Monitor {

    /// 流体セルを引数に取り従属変数の値を返す関数.
    private final ToDoubleFunction<FluidCell2> function;

    /// 流体セルのモニタのインスタンスを生成する.
    ///
    /// @param toString 文字列表現.
    /// @param function 流体セルを引数に取り従属変数の値を返す関数.
    private FluidCellMonitor(String toString, ToDoubleFunction<FluidCell2> function) {
      super(toString);
      this.function = function;
    }

    @Override
    double applyAsDouble(Object object) {
      Preconditions.checkNotNull(object);
      if (!(object instanceof FluidCell2)) {
        throw new ClassCastException();
      }

      return this.function.applyAsDouble((FluidCell2) object);
    }

    /// 流体の熱力学的温度 (K) のモニタ.
    private static final FluidCellMonitor TEMPERATURE
        = new FluidCellMonitor("temperature", FluidCell2::getTemperature);

    /// 流体の絶対圧力 (Pa) のモニタ.
    private static final FluidCellMonitor PRESSURE
        = new FluidCellMonitor("pressure", FluidCell2::getPressure);

    /// 流体の密度 (kg m<sup>-3</sup>) のモニタ.
    private static final FluidCellMonitor DENSITY
        = new FluidCellMonitor("density", FluidCell2::getDensity);

    /// 流体の比エンタルピ (J kg<sup>-1</sup>) のモニタ.
    private static final FluidCellMonitor SPECIFIC_ENTHALPY
        = new FluidCellMonitor("specificEnthalpy", FluidCell2::getSpecificEnthalpy);

    /// 流体の定圧比熱 (J kg<sup>-1</sup> K<sup>-1</sup>) のモニタ.
    private static final FluidCellMonitor SPECIFIC_CONSTANT_PRESSURE_SPECIFIC_HEAT
        = new FluidCellMonitor(
            "specificConstantPressureSpecificHeat",
            FluidCell2::getSpecificConstantPressureSpecificHeat);

    /// 流体の粘性係数 (Pa s) のモニタ.
    private static final FluidCellMonitor VISCOSITY
        = new FluidCellMonitor("viscosity", FluidCell2::getViscosity);

    /// 流体の熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>) のモニタ.
    private static final FluidCellMonitor THERMAL_CONDUCTIVITY
        = new FluidCellMonitor("thermalConductivity", FluidCell2::getThermalConductivity);

    /// 流体成分の質量分率 (-) のモニタ.
    private static final Map<ComponentKey, FluidCellMonitor> FRACTION_MAP
        = new ConcurrentHashMap<>();

    /// 流体成分の物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>) のモニタ.
    private static final Map<ComponentKey, FluidCellMonitor> DIFFUSIVITY_MAP
        = new ConcurrentHashMap<>();

  } // FluidCellMonitor

  /// 流体成分のラッパ.
  ///
  /// @param componentKey 成分のキー.
  /// @param function セルの成分値を引数に取り値を返す関数.
  private record FluidCellComponent(
      ComponentKey componentKey,
      ToDoubleFunction<FluidCell2.ComponentValue> function) implements ToDoubleFunction<FluidCell2> {

    @Override
    public double applyAsDouble(FluidCell2 cell) {
      // Preconditions.checkNotNull(cell); -> 事前に実行済
      FluidCell2.ComponentValue value = cell.componentMap().get(this.componentKey);
      Preconditions.checkNotNull(value);
      return this.function.applyAsDouble(value);
    }

  } // FluidCellComponent

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

  /// 流体成分の質量分率のモニタを返す.
  ///
  /// このメソッドは同じ `componentKey` に対し常に同じインスタンスを返し,
  /// 無条件のスレッド安全（Unconditionally thread-safe）である.
  ///
  /// @param componentKey 成分のキー.
  /// @return 流体成分の質量分率 (-) のモニタ.
  /// @throws NullPointerException `componentKey` が `null` の場合.
  public static Monitor fraction(ComponentKey componentKey) {
    return FluidCellMonitor.FRACTION_MAP.computeIfAbsent(
        componentKey, // null -> NullPointerException
        key -> new FluidCellMonitor(
            "fraction[" + key.name() + "]",
            new FluidCellComponent(key, FluidCell2.ComponentValue::getFraction)));
  }

  /// 流体成分の物質拡散係数のモニタを返す.
  ///
  /// このメソッドは同じ `componentKey` に対し常に同じインスタンスを返し,
  /// 無条件のスレッド安全（Unconditionally thread-safe）である.
  ///
  /// @param componentKey 成分のキー.
  /// @return 流体成分の物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>) のモニタ.
  /// @throws NullPointerException `componentKey` が `null` の場合.
  public static Monitor diffusivity(ComponentKey componentKey) {
    return FluidCellMonitor.DIFFUSIVITY_MAP.computeIfAbsent(
        componentKey, // null -> NullPointerException
        key -> new FluidCellMonitor(
            "diffusivity[" + key.name() + "]",
            new FluidCellComponent(key, FluidCell2.ComponentValue::getDiffusivity)));
  }

  /// 容器セルのモニタ.
  static final class ShellCellMonitor extends Monitor {

    /// 容器セルを引数に取り従属変数の値を返す関数.
    private final ToDoubleFunction<ShellCell2> function;

    /// 容器セルのモニタのインスタンスを生成する.
    ///
    /// @param toString 文字列表現.
    /// @param function 容器セルを引数に取り従属変数の値を返す関数.
    private ShellCellMonitor(String toString, ToDoubleFunction<ShellCell2> function) {
      super(toString);
      this.function = function;
    }

    @Override
    double applyAsDouble(Object object) {
      Preconditions.checkNotNull(object);
      if (!(object instanceof ShellCell2)) {
        throw new ClassCastException();
      }

      return this.function.applyAsDouble((ShellCell2) object);
    }

    /// 容器の熱力学的温度 (K) のモニタ.
    private static final ShellCellMonitor SHELL_TEMPERATURE
        = new ShellCellMonitor(
            "shellTemperature",
            ShellCell2::getShellTemperature);

    /// 容器の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>) のモニタ.
    private static final ShellCellMonitor SHELL_SPECIFIC_HEAT
        = new ShellCellMonitor(
            "shellSpecificHeat",
            ShellCell2::getShellSpecificHeat);

    /// 容器の熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>) のモニタ.
    private static final ShellCellMonitor SHELL_THERMAL_CONDUCTIVITY
        = new ShellCellMonitor(
            "shellThermalConductivity",
            ShellCell2::getShellThermalConductivity);

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
  static final class AdsorbentCell2Monitor extends Monitor {

    /// 吸着剤セルを引数に取り従属変数の値を返す関数.
    private final ToDoubleFunction<AdsorbentCell2> function;

    /// 吸着剤セルのモニタのインスタンスを生成する.
    ///
    /// @param toString 文字列表現.
    /// @param function 吸着剤セルを引数に取り従属変数の値を返す関数.
    private AdsorbentCell2Monitor(String toString, ToDoubleFunction<AdsorbentCell2> function) {
      super(toString);
      this.function = function;
    }

    @Override
    double applyAsDouble(Object object) {
      Preconditions.checkNotNull(object);
      if (!(object instanceof AdsorbentCell2)) {
        throw new ClassCastException();
      }

      return this.function.applyAsDouble((AdsorbentCell2) object);
    }

    /// 吸着剤の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>) のモニタ.
    private static final AdsorbentCell2Monitor ADSORBENT_SPECIFIC_HEAT
        = new AdsorbentCell2Monitor(
        "adsorbentSpecificHeat",
        AdsorbentCell2::getAdsorbentSpecificHeat);

    /// 吸着質成分のモル吸着量 (mol kg<sup>-1</sup>) のモニタのマップ.
    private static final Map<ComponentKey, AdsorbentCell2Monitor> MOLAR_QUANTITY_MAP
        = new ConcurrentHashMap<>();

  } // AdsorbentCell2Monitor

  /// 吸着剤成分のラッパ.
  ///
  /// @param componentKey 成分のキー.
  /// @param function セルの成分値を引数に取り値を返す関数.
  private record AdsorbentCell2Component(
      ComponentKey componentKey,
      ToDoubleFunction<AdsorbentCell2.ComponentValue> function)
      implements ToDoubleFunction<AdsorbentCell2> {

    @Override
    public double applyAsDouble(AdsorbentCell2 cell) {
      // Preconditions.checkNotNull(cell); -> 事前に実行済
      AdsorbentCell2.ComponentValue value = cell.componentMap().get(this.componentKey);
      Preconditions.checkNotNull(value);
      return this.function.applyAsDouble(value);
    }

  } // AdsorbentCell2Component

  /// 吸着剤の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>) のモニタを返す.
  ///
  /// このメソッドは常に同じインスタンスを返し,
  /// 無条件のスレッド安全（Unconditionally thread-safe）である.
  ///
  /// @return 吸着剤の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>) のモニタ.
  public static Monitor adsorbentSpecificHeat() {
    return AdsorbentCell2Monitor.ADSORBENT_SPECIFIC_HEAT;
  }

  /// 吸着質成分のモル吸着量のモニタを返す.
  ///
  /// このメソッドは指定された `majorComponentKey` に対し常に同じインスタンスを返し,
  /// 無条件のスレッド安全（Unconditionally thread-safe）である.
  ///
  /// @param componentKey 成分のキー.
  /// @return 吸着質成分のモル吸着量 (mol kg<sup>-1</sup>) のモニタ.
  /// @throws NullPointerException `majorComponentKey` が `null` の場合.
  public static Monitor molarQuantity(ComponentKey componentKey) {
    return AdsorbentCell2Monitor.MOLAR_QUANTITY_MAP.computeIfAbsent(
        componentKey,
        key -> new AdsorbentCell2Monitor(
            "molarQuantity[" + key.name() + "]",
            new AdsorbentCell2Component(key, AdsorbentCell2.ComponentValue::getMolarQuantity)));
  }

  /// 流体フェイスのモニタ.
  static final class FluidFaceMonitor extends Monitor {

      /// 流体フェイスを引数に取り従属変数の値を返す関数.
    private final ToDoubleFunction<FluidFace2> function;

    /// 流体フェイスのモニタのインスタンスを生成する.
    ///
    /// @param toString 文字列表現.
    /// @param function 流体フェイスを引数に取り従属変数の値を返す関数.
    private FluidFaceMonitor(String toString, ToDoubleFunction<FluidFace2> function) {
      super(toString);
      this.function = function;
    }

    @Override
    double applyAsDouble(Object object) {
      Preconditions.checkNotNull(object);
      if (!(object instanceof FluidFace2)) {
        throw new ClassCastException();
      }

      return this.function.applyAsDouble((FluidFace2) object);
    }

    /// 流体の質量流量 (kg s<sup>-1</sup>) のモニタ.
    private static final FluidFaceMonitor MASS_FLOW
        = new FluidFaceMonitor("massFlow", FluidFace2::getMassFlow);

    /// 流体のエネルギ流量 (W) のモニタ.
    private static final FluidFaceMonitor ENERGY_FLOW
        = new FluidFaceMonitor("energyFlow", FluidFace2::getEnergyFlow);

    /// 流体成分の化学種流量 (kg s<sup>-1</sup>) のモニタのマップ.
    private static final Map<ComponentKey, FluidFaceMonitor> SPECIES_FLOW_MAP
        = new ConcurrentHashMap<>();

  } // FluidFaceMonitor

  /// 流体成分のラッパ.
  ///
  /// @param componentKey 成分のキー.
  /// @param function フェイスの成分値を引数に取り値を返す関数.
  private record FluidFace2Component(
      ComponentKey componentKey,
      ToDoubleFunction<FluidFace2.ComponentValue> function) implements ToDoubleFunction<FluidFace2> {

    @Override
    public double applyAsDouble(FluidFace2 cell) {
      // Preconditions.checkNotNull(cell); -> 事前に実行済
      FluidFace2.ComponentValue value = cell.componentMap().get(this.componentKey);
      Preconditions.checkNotNull(value);
      return this.function.applyAsDouble(value);
    }

  } // FluidCellComponent

  /// 流体の質量流量のモニタのインスタンスを返す.
  ///
  /// @return 質量流量 (kg s<sup>-1</sup>) のモニタのインスタンス.
  public static Monitor massFlow() {
    return FluidFaceMonitor.MASS_FLOW;
  }

  /// 流体のエネルギ流量のモニタのインスタンスを返す.
  ///
  /// @return 流体のエネルギ流量 (W) のモニタのインスタンス.
  public static Monitor energyFlow() {
    return FluidFaceMonitor.ENERGY_FLOW;
  }

  /// 流体成分の質量流量のモニタを返す.
  ///
  /// このメソッドは指定された `componentKey` に対し常に同じインスタンスを返し,
  /// 無条件のスレッド安全（Unconditionally thread-safe）である.
  ///
  /// @param componentKey 成分のキー.
  /// @return 流体成分の質量流量 (kg s<sup>-1</sup>) のモニタ.
  /// @throws NullPointerException `componentKey` が `null` の場合.
  public static Monitor speciesFlow(ComponentKey componentKey) {
    return FluidFaceMonitor.SPECIES_FLOW_MAP.computeIfAbsent(
        componentKey, // null -> NullPointerException
        key -> new FluidFaceMonitor(
            "speciesFlow[" + key.name() + "]",
            new FluidFace2Component(key, FluidFace2.ComponentValue::getSpeciesFlow)));
  }

}
