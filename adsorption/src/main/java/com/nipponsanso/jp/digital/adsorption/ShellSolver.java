/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;

/// 容器のソルバ.
///
/// @param <S> ソルバの型.
/// @since 1.1
/// @author KITAMURA Yuichiro
abstract class ShellSolver<S extends ShellSolver<S>> extends FluidSolver<S> {

  /// 容器の熱伝導方程式の緩和係数のデフォルト値 (-).
  public static final double SHELL_CONDUCTIVITY_RELAXATION = 1.;

  /// 容器の熱伝導方程式の収束条件のデフォルト値 (-).
  public static final double SHELL_CONDUCTIVITY_CRITERIA = 1e-3;

  /// 容器の熱伝導方程式の基準熱流量のデフォルト値 (W).
  public static final double SHELL_CONDUCTIVITY_FLOW_RATE = 1.;

  /// 容器の比熱の緩和係数のデフォルト値 (-).
  public static final double SHELL_SPECIFIC_HEAT_RELAXATION = 1.;

  /// 容器の熱伝導率の緩和係数のデフォルト値 (-).
  public static final double SHELL_THERMAL_CONDUCTIVITY_RELAXATION = 1.;

  /// 容器の熱伝導率の緩和係数 (-).
  private double shellConductivityRelaxation = SHELL_CONDUCTIVITY_RELAXATION;

  /// 容器の熱伝導方程式の収束条件 (-).
  private double shellConductivityCriteria = SHELL_CONDUCTIVITY_CRITERIA;

  /// 容器の熱伝導方程式の基準熱流量 (W).
  private double shellConductivityFlowRate = SHELL_CONDUCTIVITY_FLOW_RATE;

  /// 容器の比熱の緩和係数 (-).
  private double shellSpecificHeatRelaxation = SHELL_SPECIFIC_HEAT_RELAXATION;

  /// 容器の熱伝導率の緩和係数 (-).
  private double shellThermalConductivityRelaxation = SHELL_THERMAL_CONDUCTIVITY_RELAXATION;

  /// 容器のソルバのインスタンスを生成する.
  ShellSolver() {}

  /// 容器の熱伝導方程式の緩和係数を設定する.
  ///
  /// @param shellConductivityRelaxation 容器の熱伝導方程式の緩和係数 (-).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException `shellConductivityRelaxation` が 0 以下の場合
  ///     (`shellConductivityRelaxation <= 0`).
  final S shellConductivityRelaxation(double shellConductivityRelaxation) {
    Preconditions.checkArgument(shellConductivityRelaxation > 0.);
    this.shellConductivityRelaxation = shellConductivityRelaxation;
    return this.self();
  }

  /// 容器の熱伝導方程式の収束条件を設定する.
  ///
  /// @param shellConductivityCriteria 容器の熱伝導方程式の収束条件 (-).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException `shellConductivityCriteria` が 0 以下の場合
  ///     (`shellConductivityCriteria <= 0`).
  final S shellConductivityCriteria(double shellConductivityCriteria) {
    Preconditions.checkArgument(shellConductivityCriteria > 0.);
    this.shellConductivityCriteria = shellConductivityCriteria;
    return this.self();
  }

  /// 容器の熱伝導方程式の基準熱流量を設定する.
  ///
  /// @param shellConductivityFlowRate 容器の熱伝導方程式の基準熱流量 (W).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException `shellConductivityFlowRate` が 0 以下の場合
  ///     (`shellConductivityFlowRate <= 0`).
  final S shellConductivityFlowRate(double shellConductivityFlowRate) {
    Preconditions.checkArgument(shellConductivityFlowRate > 0.);
    this.shellConductivityFlowRate = shellConductivityFlowRate;
    return this.self();
  }

  /// 容器の比熱の緩和係数を設定する.
  ///
  /// @param shellSpecificHeatRelaxation 容器の比熱の緩和係数 (-).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException `shellSpecificHeatRelaxation` が 0 以下の場合
  ///     (`shellSpecificHeatRelaxation <= 0`).
  final S shellSpecificHeatRelaxation(double shellSpecificHeatRelaxation) {
    Preconditions.checkArgument(shellSpecificHeatRelaxation > 0.);
    this.shellSpecificHeatRelaxation = shellSpecificHeatRelaxation;
    return this.self();
  }

  /// 容器の比熱の緩和係数を設定する.
  ///
  /// @param shellThermalConductivityRelaxation 容器の熱伝導率の緩和係数 (-).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException `shellThermalConductivityRelaxation` が 0 以下の場合
  ///     (`shellThermalConductivityRelaxation <= 0`).
  final S shellThermalConductivityRelaxation(double shellThermalConductivityRelaxation) {
    Preconditions.checkArgument(shellThermalConductivityRelaxation > 0.);
    this.shellThermalConductivityRelaxation = shellThermalConductivityRelaxation;
    return this.self();
  }

  /// 容器の熱伝導方程式の緩和係数を返す.
  ///
  /// @return 容器の熱伝導方程式の緩和係数 (-).
  final double shellConductivityRelaxation() {
    return this.shellConductivityRelaxation;
  }

  /// 容器の熱伝導方程式の収束条件を返す.
  ///
  /// @return 容器の熱伝導方程式の収束条件 (-).
  final double shellConductivityCriteria() {
    return this.shellConductivityCriteria;
  }

  /// 容器の熱伝導方程式の基準熱流量を返す.
  ///
  /// @return 容器の熱伝導方程式の基準熱流量 (W).
  final double shellConductivityFlowRate() {
    return this.shellConductivityFlowRate;
  }

  /// 容器の比熱の緩和係数を返す.
  ///
  /// @return 容器の比熱の緩和係数 (-).
  final double shellSpecificHeatRelaxation() {
    return this.shellSpecificHeatRelaxation;
  }

  /// 容器の熱伝導率の緩和係数を返す.
  ///
  /// @return 容器の熱伝導率の緩和係数 (-).
  final double shellThermalConductivityRelaxation() {
    return this.shellThermalConductivityRelaxation;
  }

}
