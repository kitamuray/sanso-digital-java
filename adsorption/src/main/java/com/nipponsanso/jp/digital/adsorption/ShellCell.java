/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

/// 容器を含むセル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
interface ShellCell extends Cell {

  /// 容器の密度を返す.
  ///
  /// 永続化の対象ではない.
  ///
  /// @return 容器の密度 (kg m<sup>-3</sup>).
  double shellDensity();

  /// 容器の熱力学的温度を返す.
  ///
  /// @return 容器の熱力学的温度 (K).
  double getShellTemperature();

  /// 容器の熱力学的温度を設定する.
  ///
  /// @param shellTemperature 容器の熱力学的温度 (K).
  /// @throws IllegalArgumentException `shellTemperature` が 0 以下の場合
  ///      (`shellTemperature <= 0`).
  void setShellTemperature(double shellTemperature);

  /// 容器の比熱を返す.
  ///
  /// @return 容器の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  double getShellSpecificHeat();

  /// 容器の比熱を設定する.
  ///
  /// @param shellSpecificHeat 容器の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  /// @throws IllegalArgumentException `shellSpecificHeat` が 0 以下の場合
  ///     (`shellSpecificHeat <= 0`).
  void setShellSpecificHeat(double shellSpecificHeat);

  /// 容器の熱伝導率を返す.
  ///
  /// @return 容器の熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>).
  double getShellThermalConductivity();

  /// 容器の熱伝導率を設定する.
  ///
  /// @param shellThermalConductivity 容器の熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>).
  /// @throws IllegalArgumentException `shellThermalConductivity` が 0 以下の場合
  ///     (`shellThermalConductivity <= 0`).
  void setShellThermalConductivity(double shellThermalConductivity);

  /// 容器を含むセルの前ステップ値を返す.
  ///
  /// @return 容器を含むセルの前ステップ値.
  @Override
  Previous previous();

  /// 容器を含むセルの前ステップ値.
  interface Previous extends Cell.Previous {

    /// 容器の熱力学的温度を返す.
    ///
    /// @return 容器の熱力学的温度 (K).
    double getShellTemperature();

    /// 容器の熱力学的温度を設定する.
    ///
    /// @param shellTemperature 容器の熱力学的温度 (K).
    /// @throws IllegalArgumentException `shellTemperature` が 0 以下の場合
    ///      (`shellTemperature <= 0`).
    void setShellTemperature(double shellTemperature);

    /// 容器の比熱を返す.
    ///
    /// @return 容器の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    double getShellSpecificHeat();

    /// 容器の比熱を設定する.
    ///
    /// @param shellSpecificHeat 容器の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    /// @throws IllegalArgumentException `shellSpecificHeat` が 0 以下の場合
    ///     (`shellSpecificHeat <= 0`).
    void setShellSpecificHeat(double shellSpecificHeat);

  } // Previous

}
