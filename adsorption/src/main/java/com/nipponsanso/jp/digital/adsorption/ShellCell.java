/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.Map;

/// 容器を含むセル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public abstract class ShellCell extends FluidCell {

  /// 容器.
  private final Shell shell;

  /// 容器の熱力学的温度の位置.
  private static final int SHELL_TEMPERATURE = FluidCell.SIZE;

  /// 容器の比熱の位置.
  private static final int SHELL_SPECIFIC_HEAT = SHELL_TEMPERATURE + 1;

  /// 容器の熱伝導率の位置.
  private static final int SHELL_THERMAL_CONDUCTIVITY = SHELL_SPECIFIC_HEAT + 1;

  /// 変数の数.
  static final int SIZE = SHELL_THERMAL_CONDUCTIVITY + 1;

  /// 容器を含むセルのインスタンスを生成する.
  ///
  /// @param shell 容器.
  ShellCell(Shell shell) {
    this.shell = shell;
  }

  /// 容器の密度を返す.
  /// @return 容器の密度 (kg m<sup>-3</sup>).
  double shellDensity() {
    return this.shell.density();
  }

  /// 容器の熱力学的温度を返す.
  ///
  /// @return 容器の熱力学的温度 (K).
  double getShellTemperature() {
    return this.doubleArray()[this.offset() + SHELL_TEMPERATURE];
  }

  /// 容器の熱力学的温度を変更する.
  ///
  /// @param shellTemperature 容器の熱力学的温度 (K).
  /// @throws IllegalArgumentException `shellTemperature` が 0 以下の場合
  ///     (`shellTemperature <= 0`).
  void setShellTemperature(double shellTemperature) {
    Preconditions.checkArgument(shellTemperature > 0.);

    this.doubleArray()[this.offset() + SHELL_TEMPERATURE] = shellTemperature;
  }

  /// 容器の比熱を返す.
  ///
  /// @return 容器の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  double getShellSpecificHeat() {
    return this.doubleArray()[this.offset() + SHELL_SPECIFIC_HEAT];
  }

  /// 容器の比熱を変更する.
  ///
  /// @param shellSpecificHeat 容器の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  /// @throws IllegalArgumentException `shellSpecificHeat` が 0 以下の場合
  ///     (`shellSpecificHeat <= 0`).
  void setShellSpecificHeat(double shellSpecificHeat) {
    Preconditions.checkArgument(shellSpecificHeat > 0.);

    this.doubleArray()[this.offset() + SHELL_SPECIFIC_HEAT] = shellSpecificHeat;
  }

  /// 容器の熱伝導率を返す.
  ///
  /// @return 容器の熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>).
  double getShellThermalConductivity() {
    return this.doubleArray()[this.offset() + SHELL_THERMAL_CONDUCTIVITY];
  }

  /// 容器の熱伝導率を変更する.
  ///
  /// @param shellThermalConductivity 容器の熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>).
  /// @throws IllegalArgumentException `shellThermalConductivity` が 0 以下の場合
  ///     (`shellThermalConductivity <= 0`).
  void setShellThermalConductivity(double shellThermalConductivity) {
    Preconditions.checkArgument(shellThermalConductivity > 0.);

    this.doubleArray()[this.offset() + SHELL_THERMAL_CONDUCTIVITY] = shellThermalConductivity;
  }

  /// 永続化オブジェクトへ情報を書き込む.
  ///
  /// @param entity 永続化オブジェクト.
  void write(ShellCellEntity<?, ?, ?> entity) {
    super.write(entity);

    entity.setShellTemperature(this.getShellTemperature());
    entity.setShellSpecificHeat(this.getShellSpecificHeat());
    entity.setShellThermalConductivity(this.getShellThermalConductivity());
  }

  /// 永続化オブジェクトから情報を読み込む.
  ///
  /// @param entity 永続化オブジェクト.
  void read(ShellCellEntity<?, ?, ?> entity) {
    super.read(entity);

    this.setShellTemperature(entity.getShellTemperature());
    this.setShellSpecificHeat(entity.getShellSpecificHeat());
    this.setShellThermalConductivity(entity.getShellThermalConductivity());
  }

  /// {@inheritDoc}
  ///
  /// @return {@inheritDoc}
  @Override
  abstract Previous previous();

  /// {@inheritDoc}
  ///
  /// @return {@inheritDoc}
  @Override
  abstract Map<ComponentKey, ? extends ComponentValue> componentMap();

  /// 容器を含むセルの前ステップ値.
  abstract static class Previous extends FluidCell.Previous {

    /// 容器の熱力学的温度の位置.
    private static final int SHELL_TEMPERATURE = FluidCell.Previous.SIZE;

    /// 容器の比熱の位置.
    private static final int SHELL_SPECIFIC_HEAT = SHELL_TEMPERATURE + 1;

    /// 変数の数.
    static final int SIZE = SHELL_SPECIFIC_HEAT + 1;

    /// 容器を含むセルの前ステップ値のインスタンスを生成して返す.
    Previous() {}

    /// 容器の熱力学的温度を返す.
    ///
    /// @return 容器の熱力学的温度 (K).
    double getShellTemperature() {
      return this.doubleArray()[this.offset() + SHELL_TEMPERATURE];
    }

    /// 容器の熱力学的温度を変更する.
    ///
    /// @param shellTemperature 容器の熱力学的温度 (K).
    /// @throws IllegalArgumentException `shellTemperature` が 0 以下の場合
    ///     (`shellTemperature <= 0`).
    void setShellTemperature(double shellTemperature) {
      Preconditions.checkArgument(shellTemperature > 0.);

      this.doubleArray()[this.offset() + SHELL_TEMPERATURE] = shellTemperature;
    }

    /// 容器の比熱を返す.
    ///
    /// @return 容器の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    double getShellSpecificHeat() {
      return this.doubleArray()[this.offset() + SHELL_SPECIFIC_HEAT];
    }

    /// 容器の比熱を変更する.
    ///
    /// @param shellSpecificHeat 容器の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    /// @throws IllegalArgumentException `shellSpecificHeat` が 0 以下の場合
    ///     (`shellSpecificHeat <= 0`).
    void setShellSpecificHeat(double shellSpecificHeat) {
      Preconditions.checkArgument(shellSpecificHeat > 0.);

      this.doubleArray()[this.offset() + SHELL_SPECIFIC_HEAT] = shellSpecificHeat;
    }

    /// 永続化オブジェクトへ情報を書き込む.
    ///
    /// @param entity 永続化オブジェクト.
    void write(ShellCellEntity.Previous entity) {
      super.write(entity);

      entity.setShellTemperature(this.getShellTemperature());
      entity.setShellSpecificHeat(this.getShellSpecificHeat());
    }

    /// 永続化オブジェクトから情報を読み込む.
    ///
    /// @param entity 永続化オブジェクト.
    void read(ShellCellEntity.Previous entity) {
      super.read(entity);

      this.setShellTemperature(entity.getShellTemperature());
      this.setShellSpecificHeat(entity.getShellSpecificHeat());
    }

  } // Previous

  /// 容器を含むセルの成分値.
  abstract static class ComponentValue extends FluidCell.ComponentValue {

    /// 変数の数.
    static final int SIZE = FluidCell.ComponentValue.SIZE;

    /// 容器を含むセルの成分値のインスタンスを生成して返す.
    ComponentValue() {}

    /// 永続化オブジェクトへ情報を書き込む.
    ///
    /// @param entity 永続化オブジェクト.
    void write(ShellCellEntity.ComponentValue<?> entity) {
      super.write(entity);
    }

    /// 永続化オブジェクトから情報を読み込む.
    ///
    /// @param entity 永続化オブジェクト.
    void read(ShellCellEntity.ComponentValue<?> entity) {
      super.read(entity);
    }

    /// {@inheritDoc}
    ///
    /// @return {@inheritDoc}
    @Override
    abstract Previous previous();

    /// 容器を含むセルの成分値の前ステップ値.
    abstract static class Previous extends FluidCell.ComponentValue.Previous {

      /// 変数の数.
      static final int SIZE = FluidCell.ComponentValue.Previous.SIZE;

      /// 容器を含むセルの成分値の前ステップ値のインスタンスを生成して返す.
      Previous() {}

      /// 永続化オブジェクトへ情報を書き込む.
      ///
      /// @param entity 永続化オブジェクト.
      void write(ShellCellEntity.ComponentValue.Previous entity) {
        super.write(entity);
      }

      /// 永続化オブジェクトから情報を読み込む.
      ///
      /// @param entity 永続化オブジェクト.
      void read(ShellCellEntity.ComponentValue.Previous entity) {
        super.read(entity);
      }

    } // Previous

  } // ComponentValue

}
