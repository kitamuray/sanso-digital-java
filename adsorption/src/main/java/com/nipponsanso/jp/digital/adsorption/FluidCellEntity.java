/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.Objects;

/// 流体を含むセルの永続化クラスの抽象実装.
///
/// - 流体に関する従属変数が対象
/// - 前ステップ値や成分値を受ける setter が存在するため, 総称型とした
///
/// @param <P> 前ステップ値の永続化クラスの型.
/// @param <C> 成分値の永続化クラスの型.
/// @param <Q> 成分値の前ステップ値の永続化クラスの型.
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class FluidCellEntity<
    P extends FluidCellEntity.Previous,
    C extends FluidCellEntity.ComponentValue<Q>,
    Q extends FluidCellEntity.ComponentValue.Previous> extends CellEntity<P, C, Q> {

  /// 流体の熱力学的温度 (K).
  private double temperature = Double.MIN_VALUE;

  /// 流体の絶対圧力 (Pa).
  private double pressure = Double.MIN_VALUE;

  /// 流体の密度 (kg m<sup>-3</sup>).
  private double density = Double.MIN_VALUE;

  /// 流体の比エンタルピ (J kg<sup>-1</sup>).
  private double specificEnthalpy; // = 0

  /// 流体の定圧比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  private double specificConstantPressureSpecificHeat = Double.MIN_VALUE;

  /// 流体の粘性係数 (Pa s).
  private double viscosity = Double.MIN_VALUE;

  /// 流体の熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>).
  private double thermalConductivity = Double.MIN_VALUE;

  /// 流体セルの永続化クラスのインスタンスを生成する.
  ///
  /// @param previous 前ステップ値の永続化オブジェクト.
  FluidCellEntity(P previous) {
    super(previous);
  }

  /// 流体の熱力学的温度を返す.
  ///
  /// @return 流体の熱力学的温度 (K).
  public final double getTemperature() {
    return this.temperature;
  }

  /// 流体の熱力学的温度を設定する.
  ///
  /// @param temperature 流体の新たな熱力学的温度 (K).
  /// @throws IllegalArgumentException `temperature` が 0 以下の場合 (`temperature <= 0`).
  public final void setTemperature(double temperature) {
    Preconditions.checkArgument(temperature > 0.);

    this.temperature = temperature;
  }

  /// 流体の絶対圧力を返す.
  ///
  /// @return 流体の絶対圧力 (Pa).
  public final double getPressure() {
    return this.pressure;
  }

  /// 流体の絶対圧力を設定する.
  ///
  /// @param pressure 流体の新たな絶対圧力 (Pa).
  /// @throws IllegalArgumentException `pressure` が 0 以下の場合 (`pressure <= 0`).
  public final void setPressure(double pressure) {
    Preconditions.checkArgument(pressure > 0.);

    this.pressure = pressure;
  }

  /// 流体の密度を返す.
  ///
  /// @return 流体の密度 (kg m<sup>-3</sup>).
  public final double getDensity() {
    return this.density;
  }

  /// 流体の密度を設定する.
  ///
  /// @param density 流体の新たな密度 (kg m<sup>-3</sup>).
  /// @throws IllegalArgumentException `density` が 0 以下の場合 (`density <= 0`).
  public final void setDensity(double density) {
    Preconditions.checkArgument(density > 0.);

    this.density = density;
  }

  /// 流体の比エンタルピを返す.
  ///
  /// @return 流体の比エンタルピ (J kg<sup>-1</sup>).
  public final double getSpecificEnthalpy() {
    return this.specificEnthalpy;
  }

  /// 流体の比エンタルピを設定する.
  ///
  /// @param specificEnthalpy 流体の新たな比エンタルピ (J kg<sup>-1</sup>).
  public final void setSpecificEnthalpy(double specificEnthalpy) {
    this.specificEnthalpy = specificEnthalpy;
  }

  /// 流体の定圧比熱を返す.
  ///
  /// @return 流体の定圧比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  public final double getSpecificConstantPressureSpecificHeat() {
    return this.specificConstantPressureSpecificHeat;
  }

  /// 流体の定圧比熱を設定する.
  ///
  /// @param specificConstantPressureSpecificHeat
  ///     流体の新たな定圧比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  /// @throws IllegalArgumentException `specificConstantPressureSpecificHeat` が 0 以下の場合
  ///     (`specificConstantPressureSpecificHeat <= 0`).
  public final void setSpecificConstantPressureSpecificHeat(
      double specificConstantPressureSpecificHeat) {
    Preconditions.checkArgument(specificConstantPressureSpecificHeat > 0.);

    this.specificConstantPressureSpecificHeat = specificConstantPressureSpecificHeat;
  }

  /// 流体の粘性係数を返す.
  ///
  /// @return 流体の粘性係数 (Pa s).
  public final double getViscosity() {
    return this.viscosity;
  }

  /// 流体の粘性係数を設定する.
  ///
  /// @param viscosity 流体の新たな粘性係数.
  /// @throws IllegalArgumentException `viscosity` が 0 以下の場合 (`viscosity <= 0`).
  public final void setViscosity(double viscosity) {
    Preconditions.checkArgument(viscosity > 0.);

    this.viscosity = viscosity;
  }

  /// 流体の熱伝導率を返す.
  ///
  /// @return 流体の熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>).
  public final double getThermalConductivity() {
    return this.thermalConductivity;
  }

  /// 流体の熱伝導率を設定する.
  ///
  /// @param thermalConductivity 流体の新たな熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>).
  /// @throws IllegalArgumentException `thermalConductivity` が 0 以下の場合
  ///     (`thermalConductivity <= 0`).
  public final void setThermalConductivity(double thermalConductivity) {
    Preconditions.checkArgument(thermalConductivity > 0.);

    this.thermalConductivity = thermalConductivity;
  }

  @Override
  public boolean equals(Object o) {
    if (!super.equals(o)) {
      return false;
    }

    FluidCellEntity<?, ?, ?> that = (FluidCellEntity<?, ?, ?>) o;

    return Double.compare(this.temperature, that.temperature) == 0
        && Double.compare(this.pressure, that.pressure) == 0
        && Double.compare(this.density, that.density) == 0
        && Double.compare(this.specificEnthalpy, that.specificEnthalpy) == 0
        && Double.compare(
            this.specificConstantPressureSpecificHeat,
            that.specificConstantPressureSpecificHeat) == 0
        && Double.compare(this.viscosity, that.viscosity) == 0
        && Double.compare(this.thermalConductivity, that.thermalConductivity) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        super.hashCode(),
        this.temperature,
        this.pressure,
        this.density,
        this.specificEnthalpy,
        this.specificConstantPressureSpecificHeat,
        this.viscosity,
        this.thermalConductivity);
  }

  /// 流体を含むセルの前ステップ値の永続化クラスの抽象実装.
  abstract static class Previous extends CellEntity.Previous {

    /// 流体の熱力学的温度 (K).
    private double temperature = Double.MIN_VALUE;

    /// 流体の密度 (kg m<sup>-3</sup>).
    private double density = Double.MIN_VALUE;

    /// 流体の比エンタルピ (J kg<sup>-1</sup>).
    private double specificEnthalpy; // = 0

    /// 流体の定圧比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    private double specificConstantPressureSpecificHeat = Double.MIN_VALUE;

    /// 流体セルの前ステップ値の永続化クラスのインスタンスを生成する.
    Previous() {}

    /// 流体の熱力学的温度を返す.
    ///
    /// @return 流体の熱力学的温度 (K).
    public final double getTemperature() {
      return this.temperature;
    }

    /// 流体の熱力学的温度を設定する.
    ///
    /// @param temperature 流体の新たな熱力学的温度 (K).
    /// @throws IllegalArgumentException `temperature` が 0 以下の場合 (`temperature <= 0`).
    public final void setTemperature(double temperature) {
      Preconditions.checkArgument(temperature > 0.);

      this.temperature = temperature;
    }

    /// 流体の密度を返す.
    ///
    /// @return 流体の密度 (kg m<sup>-3</sup>).
    public final double getDensity() {
      return this.density;
    }

    /// 流体の密度を設定する.
    ///
    /// @param density 流体の新たな密度 (kg m<sup>-3</sup>).
    /// @throws IllegalArgumentException `density` が 0 以下の場合 (`density <= 0`).
    public final void setDensity(double density) {
      Preconditions.checkArgument(density > 0.);

      this.density = density;
    }

    /// 流体の比エンタルピを返す.
    ///
    /// @return 流体の比エンタルピ (J kg<sup>-1</sup>).
    public final double getSpecificEnthalpy() {
      return this.specificEnthalpy;
    }

    /// 流体の比エンタルピを設定する.
    ///
    /// @param specificEnthalpy 流体の新たな比エンタルピ (J kg<sup>-1</sup>).
    public final void setSpecificEnthalpy(double specificEnthalpy) {
      this.specificEnthalpy = specificEnthalpy;
    }

    /// 流体の定圧比熱を返す.
    ///
    /// @return 流体の定圧比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    public final double getSpecificConstantPressureSpecificHeat() {
      return this.specificConstantPressureSpecificHeat;
    }

    /// 流体の定圧比熱を設定する.
    ///
    /// @param specificConstantPressureSpecificHeat
    ///     流体の新たな定圧比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    /// @throws IllegalArgumentException `specificConstantPressureSpecificHeat` が 0 以下の場合
    ///     (`specificConstantPressureSpecificHeat <= 0`).
    public final void setSpecificConstantPressureSpecificHeat(
        double specificConstantPressureSpecificHeat) {
      Preconditions.checkArgument(specificConstantPressureSpecificHeat > 0.);

      this.specificConstantPressureSpecificHeat = specificConstantPressureSpecificHeat;
    }

    @Override
    public boolean equals(Object o) {
      if (!super.equals(o)) {
        return false;
      }

      Previous that = (Previous) o;

      return Double.compare(this.temperature, that.temperature) == 0
          && Double.compare(this.density, that.density) == 0
          && Double.compare(this.specificEnthalpy, that.specificEnthalpy) == 0
          && Double.compare(
              this.specificConstantPressureSpecificHeat,
              that.specificConstantPressureSpecificHeat) == 0;
    }

    @Override
    public int hashCode() {
      return Objects.hash(
          super.hashCode(),
          this.temperature,
          this.density,
          this.specificEnthalpy,
          this.specificConstantPressureSpecificHeat);
    }

  } // Previous

  /// 流体を含むセルの成分値の永続化クラスの抽象実装.
  ///
  /// @param <Q> 前ステップ値の永続化クラスの型.
  abstract static class ComponentValue<Q extends ComponentValue.Previous>
      extends CellEntity.ComponentValue<Q> {

    /// 流体成分の質量分率 (-).
    private double fraction; // = 0

    /// 流体成分の物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
    private double diffusivity = Double.MIN_VALUE;

    /// 流体セルの成分値の永続化クラスのインスタンスを生成する.
    ///
    /// @param previous 前ステップ値の永続化オブジェクトを生成するオペレーション.
    ComponentValue(Q previous) {
      super(previous);
    }

    /// 流体成分の質量分率を返す.
    ///
    /// @return 流体成分の質量分率 (-).
    public final double getFraction() {
      return this.fraction;
    }

    /// 流体成分の質量分率を設定する.
    ///
    /// @param fraction 流体成分の新たな質量分率 (-).
    /// @throws IllegalArgumentException `fraction` が 0 未満か 1 を超えている場合
    ///     (`fraction < 0 || 1 < fraction`).
    public final void setFraction(double fraction) {
      Preconditions.checkArgument(0. <= fraction && fraction <= 1.);

      this.fraction = fraction;
    }

    /// 流体成分の物質拡散係数を返す.
    ///
    /// @return 流体成分の物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
    public final double getDiffusivity() {
      return this.diffusivity;
    }

    /// 流体成分の物質拡散係数を設定する.
    ///
    /// @param diffusivity 流体成分の新たな物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
    /// @throws IllegalArgumentException
    /// `diffusivity` が 0 以下の場合 (`diffusivity <= 0`).
    public final void setDiffusivity(double diffusivity) {
      Preconditions.checkArgument(diffusivity > 0.);

      this.diffusivity = diffusivity;
    }

    @Override
    public boolean equals(Object o) {
      if (!super.equals(o)) {
        return false;
      }

      ComponentValue<?> that = (ComponentValue<?>) o;

      return Double.compare(this.fraction, that.fraction) == 0
          && Double.compare(this.diffusivity, that.diffusivity) == 0;
    }

    @Override
    public int hashCode() {
      return Objects.hash(
          super.hashCode(),
          this.fraction,
          this.diffusivity);
    }

    /// 流体を含むセルの成分値の前ステップ値の永続化クラスの抽象実装.
    abstract static class Previous extends CellEntity.ComponentValue.Previous {

      /// 流体成分の質量分率 (-).
      private double fraction; // = 0

      /// 流体セルの成分値の前ステップ値の永続化クラスのインスタンスを生成する.
      Previous() {}

      /// 流体成分の質量分率を返す.
      ///
      /// @return 流体成分の質量分率 (-).
      public final double getFraction() {
        return this.fraction;
      }

      /// 流体成分の質量分率を設定する.
      ///
      /// @param fraction 流体成分の新たな質量分率 (-).
      /// @throws IllegalArgumentException `fraction` が 0 未満か 1 を超えている場合
      ///     (`fraction < 0 || 1 < fraction`).
      public final void setFraction(double fraction) {
        Preconditions.checkArgument(0. <= fraction && fraction <= 1.);

        this.fraction = fraction;
      }

      @Override
      public boolean equals(Object o) {
        if (!super.equals(o)) {
          return false;
        }

        Previous that = (Previous) o;

        return Double.compare(this.fraction, that.fraction) == 0;
      }

      @Override
      public int hashCode() {
        return Objects.hash(
            super.hashCode(),
            this.fraction);
      }

    } // ComponentValue.Previous

  } // ComponentValue

}
