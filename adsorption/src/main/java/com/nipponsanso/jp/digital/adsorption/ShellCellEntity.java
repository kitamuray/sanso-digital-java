/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.Objects;

/// 容器を含むセルの永続化クラスの抽象実装.
///
/// - 流体に関する従属変数は親クラス, 容器に関する従属変数は本クラスが対象
/// - 前ステップ値や成分値を受ける setter が存在するため, 総称型とした
///
/// @param <P> 前ステップ値の永続化クラスの型.
/// @param <C> 成分値の永続化クラスの型.
/// @param <Q> 成分値の前ステップ値の永続化クラスの型.
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class ShellCellEntity<
    P extends ShellCellEntity.Previous,
    C extends ShellCellEntity.ComponentValue<Q>,
    Q extends ShellCellEntity.ComponentValue.Previous> extends FluidCellEntity<P, C, Q> {

  /// 容器の熱力学的温度 (K).
  private double shellTemperature = Double.MIN_VALUE;

  /// 容器の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  private double shellSpecificHeat = Double.MIN_VALUE;

  /// 容器の熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>).
  private double shellThermalConductivity = Double.MIN_VALUE;

  /// 容器セルの永続化クラスのインスタンスを生成する.
  ///
  /// @param previous 前ステップ値の永続化オブジェクトを生成するオペレーション.
  ShellCellEntity(P previous) {
    super(previous);
  }

  /// 容器の熱力学的温度を返す.
  ///
  /// @return 容器の熱力学的温度 (K).
  public final double getShellTemperature() {
    return this.shellTemperature;
  }

  /// 容器の熱力学的温度を設定する.
  ///
  /// @param shellTemperature 容器の新たな熱力学的温度 (K).
  /// @throws IllegalArgumentException `shellTemperature` が 0 以下の場合 (`shellTemperature <= 0`).
  public final void setShellTemperature(double shellTemperature) {
    Preconditions.checkArgument(shellTemperature > 0.);

    this.shellTemperature = shellTemperature;
  }

  /// 容器の比熱を返す.
  ///
  /// @return 容器の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  public final double getShellSpecificHeat() {
    return shellSpecificHeat;
  }

  /// 容器の比熱を設定する.
  ///
  /// @param shellSpecificHeat 容器の新たな比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  /// @throws IllegalArgumentException `shellSpecificHeat` が 0 以下の場合
  ///     (`shellSpecificHeat <= 0`).
  public final void setShellSpecificHeat(double shellSpecificHeat) {
    Preconditions.checkArgument(shellSpecificHeat > 0.);

    this.shellSpecificHeat = shellSpecificHeat;
  }

  /// 容器の熱伝導率を返す.
  ///
  /// @return 容器の熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>).
  public final double getShellThermalConductivity() {
    return shellThermalConductivity;
  }

  /// 容器の熱伝導率を設定する.
  ///
  /// @param shellThermalConductivity 容器の新たな熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>).
  /// @throws IllegalArgumentException `shellThermalConductivity` が 0 以下の場合
  ///     (`shellThermalConductivity <= 0`).
  public final void setShellThermalConductivity(double shellThermalConductivity) {
    Preconditions.checkArgument(shellThermalConductivity > 0.);

    this.shellThermalConductivity = shellThermalConductivity;
  }

  @Override
  public boolean equals(Object o) {
    if (!super.equals(o)) {
      return false;
    }

    ShellCellEntity<?, ?, ?> that = (ShellCellEntity<?, ?, ?>) o;

    return Double.compare(this.shellTemperature, that.shellTemperature) == 0
        && Double.compare(this.shellSpecificHeat, that.shellSpecificHeat) == 0
        && Double.compare(this.shellThermalConductivity, that.shellThermalConductivity) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        super.hashCode(),
        this.shellTemperature,
        this.shellSpecificHeat,
        this.shellThermalConductivity);
  }

  /// 容器を含むセルの前ステップ値の永続化クラスの抽象実装.
  abstract static class Previous extends FluidCellEntity.Previous {

    /// 容器の熱力学的温度 (K).
    private double shellTemperature = Double.MIN_VALUE;

    /// 容器の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    private double shellSpecificHeat = Double.MIN_VALUE;

    /// 容器セルの前ステップ値の永続化クラスのインスタンスを生成する.
    Previous() {}

    /// 容器の熱力学的温度を返す.
    ///
    /// @return 容器の熱力学的温度 (K).
    public final double getShellTemperature() {
      return this.shellTemperature;
    }

    /// 容器の熱力学的温度を設定する.
    ///
    /// @param shellTemperature 容器の新たな熱力学的温度 (K).
    /// @throws IllegalArgumentException `shellTemperature` が 0 以下の場合
    ///     (`shellTemperature <= 0`).
    public final void setShellTemperature(double shellTemperature) {
      Preconditions.checkArgument(shellTemperature > 0.);

      this.shellTemperature = shellTemperature;
    }

    /// 容器の比熱を返す.
    ///
    /// @return 容器の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    public final double getShellSpecificHeat() {
      return shellSpecificHeat;
    }

    /// 容器の比熱を設定する.
    ///
    /// @param shellSpecificHeat 容器の新たな比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    /// @throws IllegalArgumentException `shellSpecificHeat` が 0 以下の場合
    ///     (`shellSpecificHeat <= 0`).
    public final void setShellSpecificHeat(double shellSpecificHeat) {
      Preconditions.checkArgument(shellSpecificHeat > 0.);

      this.shellSpecificHeat = shellSpecificHeat;
    }

    @Override
    public boolean equals(Object o) {
      if (!super.equals(o)) {
        return false;
      }

      Previous that = (Previous) o;

      return Double.compare(this.shellTemperature, that.shellTemperature) == 0
          && Double.compare(this.shellSpecificHeat, that.shellSpecificHeat) == 0;
    }

    @Override
    public int hashCode() {
      return Objects.hash(
          super.hashCode(),
          this.shellTemperature,
          this.shellSpecificHeat);
    }

  } // Previous

  /// 容器を含むセルの成分値の永続化クラスの抽象実装.
  ///
  /// @param <Q> 前ステップ値の永続化クラスの型.
  abstract static class ComponentValue<Q extends ComponentValue.Previous>
      extends FluidCellEntity.ComponentValue<Q> {

    /// 容器を含むセルの成分値の永続化クラスのインスタンスを生成する.
    ///
    /// @param previous 前ステップ値の永続化オブジェクト.
    ComponentValue(Q previous) {
      super(previous);
    }

    /// 容器セルの成分値の前ステップ値の永続化クラスの抽象実装.
    abstract static class Previous extends FluidCellEntity.ComponentValue.Previous {

      /// 容器セルの成分値の前ステップ値の永続化クラスのインスタンスを生成する.
      Previous() {}

    } // Previous

  } // ComponentValue

}
