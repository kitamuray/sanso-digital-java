/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;

/// 流体を含むセルの抽象実装.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class AbstractFluidCell implements FluidCell {

  /// 流体の熱力学的温度 (K).
  private double temperature = Double.MIN_VALUE;

  /// 流体の絶対圧力 (Pa).
  private double pressure = Double.MIN_VALUE;

  /// 流体の密度 (kg m<sup>-3</sup>).
  private double density = Double.MIN_VALUE;

  /// 流体の比エンタルピ (J kg<sup>-1</sup>).
  private double specificEnthalpy;

  /// 流体の定圧比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  private double specificConstantPressureSpecificHeat = Double.MIN_VALUE;

  /// 流体の粘性係数 (Pa s).
  private double viscosity = Double.MIN_VALUE;

  /// 流体の熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>).
  private double thermalConductivity = Double.MIN_VALUE;

  /// 流体の平均分子量.
  private double molecularWeight;

  /// 流体を含むセルのインスタンスを生成する.
  AbstractFluidCell() {}

  @Override
  public double getTemperature() {
    return this.temperature;
  }

  @Override
  public void setTemperature(double temperature) {
    Preconditions.checkArgument(temperature > 0.);
    this.temperature = temperature;
  }

  @Override
  public double getPressure() {
    return this.pressure;
  }

  @Override
  public void setPressure(double pressure) {
    Preconditions.checkArgument(pressure > 0.);
    this.pressure = pressure;
  }

  @Override
  public double getDensity() {
    return this.density;
  }

  @Override
  public void setDensity(double density) {
    Preconditions.checkArgument(density > 0.);
    this.density = density;
  }

  @Override
  public double getSpecificEnthalpy() {
    return this.specificEnthalpy;
  }

  @Override
  public void setSpecificEnthalpy(double specificEnthalpy) {
    this.specificEnthalpy = specificEnthalpy;
  }

  @Override
  public double getSpecificConstantPressureSpecificHeat() {
    return this.specificConstantPressureSpecificHeat;
  }

  @Override
  public void setSpecificConstantPressureSpecificHeat(double specificConstantPressureSpecificHeat) {
    Preconditions.checkArgument(specificConstantPressureSpecificHeat > 0.);
    this.specificConstantPressureSpecificHeat = specificConstantPressureSpecificHeat;
  }

  @Override
  public double getViscosity() {
    return this.viscosity;
  }

  @Override
  public void setViscosity(double viscosity) {
    Preconditions.checkArgument(viscosity > 0.);
    this.viscosity = viscosity;
  }

  @Override
  public double getThermalConductivity() {
    return this.thermalConductivity;
  }

  @Override
  public void setThermalConductivity(double thermalConductivity) {
    Preconditions.checkArgument(thermalConductivity > 0.);
    this.thermalConductivity = thermalConductivity;
  }

  @Override
  public double getMolecularWeight() {
    return this.molecularWeight;
  }

  @Override
  public void setMolecularWeight(double molecularWeight) {
    this.molecularWeight = molecularWeight;
  }

  /// 流体を含むセルの前ステップ値の抽象実装.
  abstract static class Previous implements FluidCell.Previous {

    /// 流体の熱力学的温度 (K).
    private double temperature = Double.MIN_VALUE;

    /// 流体の密度 (kg m<sup>-3</sup>).
    private double density = Double.MIN_VALUE;

    /// 流体の比エンタルピ (J kg<sup>-1</sup>).
    private double specificEnthalpy;

    /// 流体の定圧比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    private double specificConstantPressureSpecificHeat = Double.MIN_VALUE;

    /// 流体を含むセルの前ステップ値のインスタンスを生成する.
    Previous() {}

    @Override
    public double getTemperature() {
      return this.temperature;
    }

    @Override
    public void setTemperature(double temperature) {
      Preconditions.checkArgument(temperature > 0.);
      this.temperature = temperature;
    }

    @Override
    public double getDensity() {
      return this.density;
    }

    @Override
    public void setDensity(double density) {
      Preconditions.checkArgument(density > 0.);
      this.density = density;
    }

    @Override
    public double getSpecificEnthalpy() {
      return this.specificEnthalpy;
    }

    @Override
    public void setSpecificEnthalpy(double specificEnthalpy) {
      this.specificEnthalpy = specificEnthalpy;
    }

    @Override
    public double getSpecificConstantPressureSpecificHeat() {
      return this.specificConstantPressureSpecificHeat;
    }

    @Override
    public void setSpecificConstantPressureSpecificHeat(double specificConstantPressureSpecificHeat) {
      Preconditions.checkArgument(specificConstantPressureSpecificHeat > 0.);
      this.specificConstantPressureSpecificHeat = specificConstantPressureSpecificHeat;
    }

  } // Previous

  /// 流体を含むセルの成分値の抽象実装.
  abstract static class ComponentValue implements FluidCell.ComponentValue {

    /// 流体成分の質量分率 (-).
    private double fraction;

    /// 流体成分の物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
    private double diffusivity = Double.MIN_VALUE;

    /// 流体成分のモル分率 (-).
    private double molarFraction;

    /// 流体成分の粘性係数 (Pa s).
    private double viscosity;

    /// 流体を含むセルの成分値のインスタンスを生成する.
    ComponentValue() {}

    @Override
    public double getFraction() {
      return this.fraction;
    }

    @Override
    public void setFraction(double fraction) {
      Preconditions.checkArgument(0. <= fraction && fraction <= 1.);
      this.fraction = fraction;
    }

    @Override
    public double getDiffusivity() {
      return this.diffusivity;
    }

    @Override
    public void setDiffusivity(double diffusivity) {
      Preconditions.checkArgument(diffusivity > 0.);
      this.diffusivity = diffusivity;
    }

    @Override
    public double getMolarFraction() {
      return this.molarFraction;
    }

    @Override
    public void setMolarFraction(double molarFraction) {
      this.molarFraction = molarFraction;
    }

    @Override
    public double getViscosity() {
      return this.viscosity;
    }

    @Override
    public void setViscosity(double viscosity) {
      this.viscosity = viscosity;
    }

    /// 流体を含むセルの成分値の前ステップ値の抽象実装.
    abstract static class Previous implements FluidCell.ComponentValue.Previous {

      /// 流体成分の質量分率 (-).
      private double fraction;

      /// 流体を含むセルの成分値の前ステップ値のインスタンスを生成する.
      Previous() {}

      @Override
      public double getFraction() {
        return this.fraction;
      }

      @Override
      public void setFraction(double fraction) {
        Preconditions.checkArgument(0. <= fraction && fraction <= 1.);
        this.fraction = fraction;
      }

    } // ComponentValue.Previous

  } // ComponentValue

}
