/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.EnumMap;
import java.util.Map;

/// 流体のソルバ.
///
/// @param <S> ソルバの型.
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class FluidSolver<S extends FluidSolver<S>> extends Solver<S> {

  /// 流体の連続の式の収束条件のデフォルト値 (-).
  public static final double CONTINUITY_CRITERIA = 1e-3;

  /// 流体の連続の式の基準流量のデフォルト値 (kg s<sup>-1</sup>).
  public static final double CONTINUITY_FLOW_RATE = 1.;

  /// 流体のエネルギ輸送式の緩和係数のデフォルト値 (-).
  public static final double ENERGY_RELAXATION = 1.;

  /// 流体のエネルギ輸送式の収束条件のデフォルト値 (-).
  public static final double ENERGY_CRITERIA = 1e-3;

  /// 流体のエネルギ輸送式の基準流量のデフォルト値 (W).
  public static final double ENERGY_FLOW_RATE = 1.;

  /// 流体の熱力学的温度の探索範囲下限のデフォルト値 (K).
  public static final double LOWER_TEMPERATURE = Double.MIN_VALUE;

  /// 流体の熱力学的温度の探索範囲下限のデフォルト値 (K).
  public static final double UPPER_TEMPERATURE = Double.MAX_VALUE;

  /// 流体の熱力学的温度の許容絶対誤差のデフォルト値 (K).
  public static final double TEMPERATURE_ACCURACY = Double.MIN_VALUE;

  /// 流体の熱力学的温度の緩和係数のデフォルト値 (-).
  public static final double TEMPERATURE_RELAXATION = 1.;

  /// 流体の絶対圧力の探索範囲下限のデフォルト値 (Pa).
  public static final double LOWER_PRESSURE = Double.MIN_VALUE;

  /// 流体の絶対圧力の探索範囲上限のデフォルト値 (Pa).
  public static final double UPPER_PRESSURE = Double.MAX_VALUE;

  /// 流体の絶対圧力の許容絶対誤差のデフォルト値 (Pa)
  public static final double PRESSURE_ACCURACY = Double.MIN_VALUE;

  /// 流体の絶対圧力の緩和係数のデフォルト値 (-).
  public static final double PRESSURE_RELAXATION = 1.;

  /// 流体の密度の緩和係数のデフォルト値 (-).
  public static final double DENSITY_RELAXATION = 1.;

  /// 流体の定圧比熱の緩和係数のデフォルト値 (-).
  public static final double SPECIFIC_CONSTANT_PRESSURE_SPECIFIC_HEAT_RELAXATION = 1.;

  /// 流体の粘性係数の緩和係数のデフォルト値 (-).
  public static final double VISCOSITY_RELAXATION = 1.;

  /// 流体の熱伝導率の緩和係数のデフォルト値 (-).
  public static final double THERMAL_CONDUCTIVITY_RELAXATION = 1.;

  /// 流体成分の化学種輸送式の緩和係数のデフォルト値 (-).
  public static final double SPECIES_RELAXATION = 1.;

  /// 流体成分の化学種輸送式の収束条件のデフォルト値 (-).
  public static final double SPECIES_CRITERIA = 1e-3;

  /// 流体成分の化学種輸送式の基準流量のデフォルト値 (kg s<sup>-1</sup>).
  public static final double SPECIES_FLOW_RATE = 1.;

  /// 流体成分の物質拡散係数の緩和係数のデフォルト値 (-).
  public static final double DIFFUSIVITY_RELAXATION = 1.;

  /// 流体の連続の式の収束条件 (-).
  private double continuityCriteria = CONTINUITY_CRITERIA;

  /// 流体の連続の式の基準流量 (kg s<sup>-1</sup>).
  private double continuityFlowRate = CONTINUITY_FLOW_RATE;

  /// 流体のエネルギ輸送式の緩和係数 (-).
  private double energyRelaxation = ENERGY_RELAXATION;

  /// 流体のエネルギ輸送式の収束条件 (-).
  private double energyCriteria = ENERGY_CRITERIA;

  /// 流体のエネルギ輸送式の基準流量 (W).
  private double energyFlowRate = ENERGY_FLOW_RATE;

  /// 流体の熱力学的温度の探索範囲下限 (K).
  private double lowerTemperature = LOWER_TEMPERATURE;

  /// 流体の熱力学的温度の探索範囲上限 (K).
  private double upperTemperature = UPPER_TEMPERATURE;

  /// 流体の熱力学的温度の許容絶対誤差 (K).
  private double temperatureAccuracy = TEMPERATURE_ACCURACY;

  /// 流体の熱力学的温度の緩和係数 (-).
  private double temperatureRelaxation = TEMPERATURE_RELAXATION;

  /// 流体の絶対圧力の探索範囲下限 (Pa).
  private double lowerPressure = LOWER_PRESSURE;

  /// 流体の絶対圧力の探索範囲上限 (Pa).
  private double upperPressure = UPPER_PRESSURE;

  /// 流体の絶対圧力の許容絶対誤差 (Pa).
  private double pressureAccuracy = PRESSURE_ACCURACY;

  /// 流体の絶対圧力の緩和係数 (-).
  private double pressureRelaxation = PRESSURE_RELAXATION;

  /// 流体の密度の緩和係数 (-).
  private double densityRelaxation = DENSITY_RELAXATION;

  /// 流体の定圧比熱の緩和係数 (-).
  private double specificConstantPressureSpecificHeatRelaxation
      = SPECIFIC_CONSTANT_PRESSURE_SPECIFIC_HEAT_RELAXATION;

  /// 流体の粘性係数の緩和係数 (-).
  private double viscosityRelaxation = VISCOSITY_RELAXATION;

  /// 流体の熱伝導率の緩和係数 (-).
  private double thermalConductivityRelaxation = THERMAL_CONDUCTIVITY_RELAXATION;

  /// 流体成分の化学種輸送式の緩和係数 (-) のマップ.
  private final Map<ComponentKey, Double> speciesRelaxationMap = new EnumMap<>(ComponentKey.class);

  /// 流体成分の化学種輸送式の収束条件 (-) のマップ.
  private final Map<ComponentKey, Double> speciesCriteriaMap = new EnumMap<>(ComponentKey.class);

  /// 流体成分の化学種輸送式の基準流量 (kg s<sup>-1</sup>) のマップ.
  private final Map<ComponentKey, Double> speciesFlowRateMap = new EnumMap<>(ComponentKey.class);

  /// 流体成分の物質拡散係数の緩和係数 (-) のマップ.
  private final Map<ComponentKey, Double> diffusivityRelaxationMap
      = new EnumMap<>(ComponentKey.class);

  /// 流体のソルバのインスタンスを生成する.
  FluidSolver() {}

  /// 流体の連続の式の収束条件を設定する.
  ///
  /// @param continuityCriteria 流体の連続の式の収束条件 (-).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException `continuityCriteria` が 0 以下の場合
  ///     (`continuityCriteria <= 0`).
  public final S continuityCriteria(double continuityCriteria) {
    Preconditions.checkArgument(continuityCriteria > 0.);
    this.continuityCriteria = continuityCriteria;
    return this.self();
  }

  /// 流体の連続の式の基準流量を設定する.
  ///
  /// @param continuityFlowRate 流体の連続の式の基準流量 (kg s<sup>-1</sup>).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException `continuityFlowRate` が 0 以下の場合
  ///     (`continuityFlowRate <= 0`).
  public final S continuityFlowRate(double continuityFlowRate) {
    Preconditions.checkArgument(continuityFlowRate > 0.);
    this.continuityFlowRate = continuityFlowRate;
    return this.self();
  }

  /// 流体のエネルギ輸送式の緩和係数を設定する.
  ///
  /// @param energyRelaxation 流体のエネルギ輸送式の緩和係数 (-).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException `energyRelaxation` が 0 以下の場合
  ///     (`energyRelaxation <= 0`).
  public final S energyRelaxation(double energyRelaxation) {
    Preconditions.checkArgument(energyRelaxation > 0.);
    this.energyRelaxation = energyRelaxation;
    return this.self();
  }

  /// 流体のエネルギ輸送式の収束条件を設定する.
  ///
  /// @param energyCriteria 流体のエネルギ輸送式の収束条件 (-).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException `energyCriteria` が 0 以下の場合
  ///     (`energyCriteria <= 0`).
  public final S energyCriteria(double energyCriteria) {
    Preconditions.checkArgument(energyCriteria > 0.);
    this.energyCriteria = energyCriteria;
    return this.self();
  }

  /// 流体のエネルギ輸送式の基準流量を設定する.
  ///
  /// @param energyFlowRate 流体のエネルギ輸送式の基準流量 (W).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException `energyFlowRate` が 0 以下の場合
  ///     (`energyFlowRate <= 0`).
  public final S energyFlowRate(double energyFlowRate) {
    Preconditions.checkArgument(energyFlowRate > 0.);
    this.energyFlowRate = energyFlowRate;
    return this.self();
  }

  /// 流体の熱力学的温度の探索範囲下限を設定する.
  ///
  /// @param lowerTemperature 流体の熱力学的温度の探索範囲下限 (K).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException `lowerTemperature` が範囲外の場合.
  public final S lowerTemperature(double lowerTemperature) {
    Preconditions.checkArgument(
        0. < lowerTemperature && lowerTemperature < this.upperTemperature);
    this.lowerTemperature = lowerTemperature;
    return this.self();
  }

  /// 流体の熱力学的温度の探索範囲上限を設定する.
  ///
  /// @param upperTemperature 流体の熱力学的温度の探索範囲上限 (K).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException `upperTemperature` が範囲外の場合.
  public final S upperTemperature(double upperTemperature) {
    Preconditions.checkArgument(this.lowerTemperature < upperTemperature);
    this.upperTemperature = upperTemperature;
    return this.self();
  }

  /// 流体の熱力学的温度の許容絶対誤差を設定する.
  ///
  /// @param temperatureAccuracy 流体の熱力学的温度の許容絶対誤差 (K).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException `temperatureAccuracy` が 0 以下の場合
  ///     (`temperatureAccuracy <= 0`).
  public final S temperatureAccuracy(double temperatureAccuracy) {
    Preconditions.checkArgument(temperatureAccuracy > 0.);
    this.temperatureAccuracy = temperatureAccuracy;
    return this.self();
  }

  /// 流体の熱力学的温度の緩和係数を設定する.
  ///
  /// @param temperatureRelaxation 流体の熱力学的温度の緩和係数 (-).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException `temperatureRelaxation` が 0 以下の場合
  ///     (`temperatureRelaxation <= 0`).
  public final S temperatureRelaxation(double temperatureRelaxation) {
    Preconditions.checkArgument(temperatureRelaxation > 0.);
    this.temperatureRelaxation = temperatureRelaxation;
    return this.self();
  }

  /// 流体の絶対圧力の探索範囲下限を設定する.
  ///
  /// @param lowerPressure 流体の絶対圧力の探索範囲下限 (Pa).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException `lowerPressure` が範囲外の場合.
  public final S lowerPressure(double lowerPressure) {
    Preconditions.checkArgument(0. < lowerPressure && lowerPressure < this.upperPressure);
    this.lowerPressure = lowerPressure;
    return this.self();
  }

  /// 流体の絶対圧力の探索範囲上限を設定する.
  ///
  /// @param upperPressure 流体の絶対圧力の探索範囲上限 (Pa).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException `upperPressure` が範囲外の場合.
  public final S upperPressure(double upperPressure) {
    Preconditions.checkArgument(this.lowerPressure < upperPressure);
    this.upperPressure = upperPressure;
    return this.self();
  }

  /// 流体の絶対圧力の許容絶対誤差を設定する.
  ///
  /// @param pressureAccuracy 流体の絶対圧力の許容絶対誤差 (Pa).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException `pressureAccuracy` が 0 以下の場合
  ///     (`pressureAccuracy <= 0`).
  public final S pressureAccuracy(double pressureAccuracy) {
    Preconditions.checkArgument(pressureAccuracy > 0.);
    this.pressureAccuracy = pressureAccuracy;
    return this.self();
  }

  /// 流体の絶対圧力の緩和係数を設定する.
  ///
  /// @param pressureRelaxation 流体の絶対圧力の緩和係数 (-).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException `pressureRelaxation` が 0 以下の場合
  ///     (`pressureRelaxation <= 0`).
  public final S pressureRelaxation(double pressureRelaxation) {
    Preconditions.checkArgument(pressureRelaxation > 0.);
    this.pressureRelaxation = pressureRelaxation;
    return this.self();
  }

  /// 流体の密度の緩和係数を設定する.
  ///
  /// @param densityRelaxation 流体の密度の緩和係数 (-).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException `densityRelaxation` が 0 以下の場合
  ///     (`densityRelaxation <= 0`).
  public final S densityRelaxation(double densityRelaxation) {
    Preconditions.checkArgument(densityRelaxation > 0.);
    this.densityRelaxation = densityRelaxation;
    return this.self();
  }

  /// 流体の定圧比熱の緩和係数を設定する.
  ///
  /// @param specificConstantPressureSpecificHeatRelaxation 流体の定圧比熱の緩和係数 (-).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException
  ///     `specificConstantPressureSpecificHeatRelaxation` が 0 以下の場合
  ///     (`specificConstantPressureSpecificHeatRelaxation <= 0`).
  public final S specificConstantPressureSpecificHeatRelaxation(
      double specificConstantPressureSpecificHeatRelaxation) {
    Preconditions.checkArgument(
        specificConstantPressureSpecificHeatRelaxation > 0.);
    this.specificConstantPressureSpecificHeatRelaxation
        = specificConstantPressureSpecificHeatRelaxation;
    return this.self();
  }

  /// 流体の粘性係数の緩和係数を設定する.
  ///
  /// @param viscosityRelaxation 流体の粘性係数の緩和係数 (-).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException `viscosityRelaxation` が 0 以下の場合
  ///     (`viscosityRelaxation <= 0`).
  public final S viscosityRelaxation(double viscosityRelaxation) {
    Preconditions.checkArgument(viscosityRelaxation > 0.);
    this.viscosityRelaxation = viscosityRelaxation;
    return this.self();
  }

  /// 流体の熱伝導率の緩和係数を設定する.
  ///
  /// @param thermalConductivityRelaxation 流体の熱伝導率の緩和係数 (-).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException `thermalConductivityRelaxation` が 0 以下の場合
  ///     (`thermalConductivityRelaxation <= 0`).
  public final S thermalConductivityRelaxation(double thermalConductivityRelaxation) {
    Preconditions.checkArgument(thermalConductivityRelaxation > 0.);
    this.thermalConductivityRelaxation = thermalConductivityRelaxation;
    return this.self();
  }

  /// 流体成分の化学種輸送式の緩和係数を設定する.
  ///
  /// @param speciesRelaxation 流体成分の化学種輸送式の緩和係数 (-).
  /// @return ソルバのインスタンス.
  /// @throws IllegalStateException 事前に化学種のキーが設定されていない場合.
  /// @throws IllegalArgumentException `speciesRelaxation` が 0 以下の場合
  ///     (`speciesRelaxation <= 0`).
  public final S speciesRelaxation(double speciesRelaxation) {
    Preconditions.checkState(this.componentKey() != null);
    Preconditions.checkArgument(speciesRelaxation > 0.);
    this.speciesRelaxationMap.put(this.componentKey(), speciesRelaxation);
    return this.self();
  }

  /// 流体成分の化学種輸送式の収束条件を設定する.
  ///
  /// @param speciesCriteria 流体成分の化学種輸送式の収束条件 (-).
  /// @return ソルバのインスタンス.
  /// @throws IllegalStateException 事前に化学種のキーが設定されていない場合.
  /// @throws IllegalArgumentException `speciesCriteria` が 0 以下の場合
  ///     (`speciesCriteria <= 0`).
  public final S speciesCriteria(double speciesCriteria) {
    Preconditions.checkState(this.componentKey() != null);
    Preconditions.checkArgument(speciesCriteria > 0.);
    this.speciesCriteriaMap.put(this.componentKey(), speciesCriteria);
    return this.self();
  }

  /// 流体成分の化学種輸送式の基準流量を設定する.
  ///
  /// @param speciesFlowRate 流体成分の化学種輸送式の基準流量 (kg s<sup>-1</sup>).
  /// @return ソルバのインスタンス.
  /// @throws IllegalStateException 事前に化学種のキーが設定されていない場合.
  /// @throws IllegalArgumentException `speciesFlowRate` が 0 以下の場合
  ///     (`speciesFlowRate <= 0`).
  public final S speciesFlowRate(double speciesFlowRate) {
    Preconditions.checkState(this.componentKey() != null);
    Preconditions.checkArgument(speciesFlowRate > 0.);
    this.speciesFlowRateMap.put(this.componentKey(), speciesFlowRate);
    return this.self();
  }

  /// 流体成分の物質拡散係数の緩和係数を設定する.
  ///
  /// @param diffusivityRelaxation 流体成分の物質拡散係数の緩和係数 (-).
  /// @return ソルバのインスタンス.
  /// @throws IllegalStateException 事前に化学種のキーが設定されていない場合.
  /// @throws IllegalArgumentException `diffusivityRelaxation` が 0 以下の場合
  ///     (`diffusivityRelaxation <= 0`).
  public final S diffusivityRelaxation(double diffusivityRelaxation) {
    Preconditions.checkState(this.componentKey() != null);
    Preconditions.checkArgument(diffusivityRelaxation > 0.);
    this.diffusivityRelaxationMap.put(this.componentKey(), diffusivityRelaxation);
    return this.self();
  }

  /// 流体の連続の式の収束条件を返す.
  ///
  /// @return 流体の連続の式の収束条件 (-).
  final double continuityCriteria() {
    return this.continuityCriteria;
  }

  /// 流体の連続の式の基準流量を返す.
  ///
  /// @return 流体の連続の式の基準流量 (kg s<sup>-1</sup>).
  final double continuityFlowRate() {
    return this.continuityFlowRate;
  }

  /// 流体のエネルギ輸送式の緩和係数を返す.
  ///
  /// @return 流体のエネルギ輸送式の緩和係数 (-).
  final double energyRelaxation() {
    return this.energyRelaxation;
  }

  /// 流体のエネルギ輸送式の収束条件を返す.
  ///
  /// @return 流体のエネルギ輸送式の収束条件 (-).
  final double energyCriteria() {
    return this.energyCriteria;
  }

  /// 流体のエネルギ輸送式の基準流量を返す.
  ///
  /// @return 流体のエネルギ輸送式の基準流量 (W).
  final double energyFlowRate() {
    return this.energyFlowRate;
  }

  /// 流体の熱力学的温度の探索範囲下限を返す.
  ///
  /// @return 流体の熱力学的温度の探索範囲下限 (K).
  final double lowerTemperature() {
    return this.lowerTemperature;
  }

  /// 流体の熱力学的温度の探索範囲上限を返す.
  ///
  /// @return 流体の熱力学的温度の探索範囲上限 (K).
  final double upperTemperature() {
    return this.upperTemperature;
  }

  /// 流体の熱力学的温度の許容絶対誤差を返す.
  ///
  /// @return 流体の熱力学的温度の許容絶対誤差 (K).
  final double temperatureAccuracy() {
    return this.temperatureAccuracy;
  }

  /// 流体の熱力学的温度の緩和係数を返す.
  ///
  /// @return 流体の熱力学的温度の緩和係数 (-).
  final double temperatureRelaxation() {
    return this.temperatureRelaxation;
  }

  /// 流体の絶対圧力の探索範囲下限を返す.
  ///
  /// @return 流体の絶対圧力の探索範囲下限 (Pa).
  final double lowerPressure() {
    return this.lowerPressure;
  }

  /// 流体の絶対圧力の探索範囲上限を返す.
  ///
  /// @return 流体の絶対圧力の探索範囲上限 (Pa)
  final double upperPressure() {
    return this.upperPressure;
  }

  /// 流体の絶対圧力の許容絶対誤差を返す.
  ///
  /// @return 流体の絶対圧力の許容絶対誤差 (Pa).
  final double pressureAccuracy() {
    return this.pressureAccuracy;
  }

  /// 流体の絶対圧力の緩和係数を返す.
  ///
  /// @return 流体の絶対圧力の緩和係数 (-).
  final double pressureRelaxation() {
    return this.pressureRelaxation;
  }

  /// 流体の密度の緩和係数を返す.
  ///
  /// @return 流体の密度の緩和係数 (-).
  final double densityRelaxation() {
    return this.densityRelaxation;
  }

  /// 流体の定圧比熱の緩和係数を返す.
  ///
  /// @return 流体の定圧比熱の緩和係数 (-).
  final double specificConstantPressureSpecificHeatRelaxation() {
    return this.specificConstantPressureSpecificHeatRelaxation;
  }

  /// 流体の粘性係数の緩和係数を返す.
  ///
  /// @return 流体の粘性係数の緩和係数 (-).
  final double viscosityRelaxation() {
    return this.viscosityRelaxation;
  }

  /// 流体の熱伝導率の緩和係数を返す.
  ///
  /// @return 流体の熱伝導率の緩和係数 (-).
  final double thermalConductivityRelaxation() {
    return this.thermalConductivityRelaxation;
  }

  /// 流体成分の化学種輸送式の緩和係数を返す.
  ///
  /// @param componentKey 成分のキー.
  /// @return 流体成分の化学種輸送式の緩和係数 (-).
  /// @throws NullPointerException `componentKey` が `null` の場合.
  final double speciesRelaxation(ComponentKey componentKey) {
    Preconditions.checkNotNull(componentKey);
    return this.speciesRelaxationMap.getOrDefault(componentKey, SPECIES_RELAXATION);
  }

  /// 流体成分の化学種輸送式の収束条件を返す.
  ///
  /// @param componentKey 成分のキー.
  /// @return 流体成分の化学種輸送式の収束条件 (-).
  /// @throws NullPointerException `componentKey` が `null` の場合.
  final double speciesCriteria(ComponentKey componentKey) {
    Preconditions.checkNotNull(componentKey);
    return this.speciesCriteriaMap.getOrDefault(componentKey, SPECIES_CRITERIA);
  }

  /// 流体成分の化学種輸送式の基準流量を返す.
  ///
  /// @param componentKey 成分のキー.
  /// @return 流体成分の化学種輸送式の基準流量 (kg s<sup>-1</sup>).
  /// @throws NullPointerException `componentKey` が `null` の場合.
  final double speciesFlowRate(ComponentKey componentKey) {
    Preconditions.checkNotNull(componentKey);
    return this.speciesFlowRateMap.getOrDefault(componentKey, SPECIES_FLOW_RATE);
  }

  /// 流体成分の物質拡散係数の緩和係数を返す.
  ///
  /// @param componentKey 成分のキー.
  /// @return 流体成分の物質拡散係数の緩和係数 (-).
  /// @throws NullPointerException `componentKey` が `null` の場合.
  final double diffusivityRelaxation(ComponentKey componentKey) {
    Preconditions.checkNotNull(componentKey);
    return this.diffusivityRelaxationMap.getOrDefault(componentKey, DIFFUSIVITY_RELAXATION);
  }

}
