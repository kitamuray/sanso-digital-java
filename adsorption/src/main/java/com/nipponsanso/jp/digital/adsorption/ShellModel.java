/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

/// 容器モデル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
final class ShellModel {

  /// 容器.
  private final Shell shell;

  /// 容器モデルのインスタンスを生成する.
  ///
  /// @param shell 容器.
  ShellModel(Shell shell) {
    this.shell = shell;
  }

  /// 容器の比熱を返す.
  ///
  /// @param cell セル.
  /// @return 容器の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  double shellSpecificHeat(ShellCell2 cell) {
    return this.shell.specificHeat().specificHeat(cell.getShellTemperature());
  }

  /// 容器の熱伝導率を返す.
  ///
  /// @param cell セル.
  /// @return 容器の熱伝導率.
  double shellThermalConductivity(ShellCell2 cell) {
    return this.shell.thermalConductivity().thermalConductivity(cell.getShellTemperature());
  }

}
