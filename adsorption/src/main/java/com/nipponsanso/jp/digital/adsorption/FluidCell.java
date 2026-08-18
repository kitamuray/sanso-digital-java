/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.Map;

/// 流体を含むセル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class FluidCell extends Cell {

  /// 流体の熱力学的温度の位置.
  private static final int TEMPERATURE = Cell.SIZE;

  /// 流体の絶対圧力の位置.
  private static final int PRESSURE = TEMPERATURE + 1;

  /// 流体の密度の位置.
  private static final int DENSITY = PRESSURE + 1;

  /// 流体の比エンタルピの位置.
  private static final int SPECIFIC_ENTHALPY = DENSITY + 1;

  /// 流体の定圧比熱の位置.
  private static final int SPECIFIC_CONSTANT_PRESSURE_SPECIFIC_HEAT = SPECIFIC_ENTHALPY + 1;

  /// 流体の粘性係数の位置.
  private static final int VISCOSITY = SPECIFIC_CONSTANT_PRESSURE_SPECIFIC_HEAT + 1;

  /// 流体の熱伝導率の位置.
  private static final int THERMAL_CONDUCTIVITY = VISCOSITY + 1;

  /// 流体の平均分子量の位置.
  private static final int MOLECULAR_WEIGHT = THERMAL_CONDUCTIVITY + 1;

  /// 変数の数.
  static final int SIZE = MOLECULAR_WEIGHT + 1;

  /// 流体を含むセルのインスタンスを生成する.
  FluidCell() {}

  /// 流体の熱力学的温度を返す.
  ///
  /// @return 流体の熱力学的温度 (K).
  double getTemperature() {
    return this.doubleArray()[this.offset() + TEMPERATURE];
  }

  /// 流体の熱力学的温度を変更する.
  ///
  /// @param temperature 流体の新たな熱力学的温度 (K).
  /// @throws IllegalArgumentException `temperature` が 0 以下の場合 (`temperature <= 0`).
  ///
  void setTemperature(double temperature) {
    Preconditions.checkArgument(temperature > 0.);

    this.doubleArray()[this.offset() + TEMPERATURE] = temperature;
  }

  /// 流体の絶対圧力を返す.
  /// @return 流体の絶対圧力 (Pa).
  double getPressure() {
    return this.doubleArray()[this.offset() + PRESSURE];
  }

  /// 流体の絶対圧力を変更する.
  ///
  /// @param pressure 流体の絶対圧力.
  /// @throws IllegalArgumentException `pressure` が 0 以下の場合 (`pressure <= 0`).
  void setPressure(double pressure) {
    Preconditions.checkArgument(pressure > 0.);

    this.doubleArray()[this.offset() + PRESSURE] = pressure;
  }

  /// 流体の密度を返す.
  ///
  /// @return 流体の密度 (kg m<sup>-3</sup>).
  double getDensity() {
    return this.doubleArray()[this.offset() + DENSITY];
  }

  /// 流体の密度を変更する.
  ///
  /// @param density 流体の新たな密度 (kg m<sup>-3</sup>).
  /// @throws IllegalArgumentException `density` が 0 以下の場合 (`density <= 0`).
  void setDensity(double density) {
    Preconditions.checkArgument(density > 0.);

    this.doubleArray()[this.offset() + DENSITY] = density;
  }

  /// 流体の比エンタルピを返す.
  ///
  /// @return 流体の比エンタルピ (J kg<sup>-1</sup>).
  double getSpecificEnthalpy() {
    return this.doubleArray()[this.offset() + SPECIFIC_ENTHALPY];
  }

  /// 流体の比エンタルピを変更する.
  ///
  /// @param specificEnthalpy 流体の新たな比エンタルピ (J kg<sup>-1</sup>).
  void setSpecificEnthalpy(double specificEnthalpy) {
    this.doubleArray()[this.offset() + SPECIFIC_ENTHALPY] = specificEnthalpy;
  }

  /// 流体の定圧比熱を返す.
  ///
  /// @return 流体の定圧比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  double getSpecificConstantPressureSpecificHeat() {
    return this.doubleArray()[this.offset() + SPECIFIC_CONSTANT_PRESSURE_SPECIFIC_HEAT];
  }

  /// 流体の定圧比熱を変更する.
  ///
  /// @param specificConstantPressureSpecificHeat 流体の新たな定圧比熱
  ///                                             (J kg<sup>-1</sup> K<sup>-1</sup>).
  /// @throws IllegalArgumentException `specificConstantPressureSpecificHeat` が 0 以下の場合
  ///     (`specificConstantPressureSpecificHeat <= 0.`)
  void setSpecificConstantPressureSpecificHeat(double specificConstantPressureSpecificHeat) {
    Preconditions.checkArgument(specificConstantPressureSpecificHeat > 0.);

    this.doubleArray()[this.offset() + SPECIFIC_CONSTANT_PRESSURE_SPECIFIC_HEAT]
        = specificConstantPressureSpecificHeat;
  }

  /// 流体の粘性係数を返す.
  ///
  /// @return 流体の粘性係数 (Pa s).
  double getViscosity() {
    return this.doubleArray()[this.offset() + VISCOSITY];
  }

  /// 流体の粘性係数を変更する.
  ///
  /// @param viscosity 流体の新たな粘性係数 (Pa s).
  /// @throws IllegalArgumentException `viscosity` が 0 以下の場合 (`viscosity <= 0`).
  void setViscosity(double viscosity) {
    Preconditions.checkArgument(viscosity > 0.);

    this.doubleArray()[this.offset() + VISCOSITY] = viscosity;
  }

  /// 流体の熱伝導率を返す.
  ///
  /// @return 流体の熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>).
  double getThermalConductivity() {
    return this.doubleArray()[this.offset() + THERMAL_CONDUCTIVITY];
  }

  /// 流体の熱伝導率を変更する.
  ///
  /// @param thermalConductivity 流体の新たな熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>).
  /// @throws IllegalArgumentException `thermalConductivity` が 0 以下の場合
  ///     (`thermalConductivity <= 0`).
  void setThermalConductivity(double thermalConductivity) {
    Preconditions.checkArgument(thermalConductivity > 0.);

    this.doubleArray()[this.offset() + THERMAL_CONDUCTIVITY] = thermalConductivity;
  }

  /// 流体の平均分子量を返す.
  ///
  /// 流体の平均分子量は永続化の対象外である.
  ///
  /// @return 流体の平均分子量.
  double getMolecularWeight() {
    return this.doubleArray()[this.offset() + MOLECULAR_WEIGHT];
  }

  /// 流体の平均分子量を変更する.
  ///
  /// 流体の平均分子量は永続化の対象外である.
  ///
  /// @param molecularWeight 流体の新たな平均分子量.
  /// @throws IllegalArgumentException `molecularWeight` が 0 以下の場合 (`molecularWeight <= 0`).
  void setMolecularWeight(double molecularWeight) {
    Preconditions.checkArgument(molecularWeight > 0.);

    this.doubleArray()[this.offset() + MOLECULAR_WEIGHT] = molecularWeight;
  }

  /// 永続化オブジェクトへ情報を書き込む.
  ///
  /// @param entity 永続化オブジェクト.
  void write(FluidCellEntity<?, ?, ?> entity) {
    super.write(entity);

    entity.setTemperature(this.getTemperature());
    entity.setPressure(this.getPressure());
    entity.setDensity(this.getDensity());
    entity.setSpecificEnthalpy(this.getSpecificEnthalpy());
    entity.setSpecificConstantPressureSpecificHeat(
        this.getSpecificConstantPressureSpecificHeat());
    entity.setViscosity(this.getViscosity());
    entity.setThermalConductivity(this.getThermalConductivity());
  }

  /// 永続化オブジェクトから情報を読み込む.
  ///
  /// @param entity 永続化オブジェクト.
  void read(FluidCellEntity<?, ?, ?> entity) {
    super.read(entity);

    this.setTemperature(entity.getTemperature());
    this.setPressure(entity.getPressure());
    this.setDensity(entity.getDensity());
    this.setSpecificEnthalpy(entity.getSpecificEnthalpy());
    this.setSpecificConstantPressureSpecificHeat(
        entity.getSpecificConstantPressureSpecificHeat());
    this.setViscosity(entity.getViscosity());
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

  /// 流体を含むセルの前ステップ値.
  abstract static class Previous extends Cell.Previous {

    /// 流体の熱力学的温度の位置.
    private static final int TEMPERATURE = Cell.Previous.SIZE;

    /// 流体の密度の位置.
    private static final int DENSITY = TEMPERATURE + 1;

    /// 流体の比エンタルピの位置.
    private static final int SPECIFIC_ENTHALPY = DENSITY + 1;

    /// 流体の定圧比熱の位置.
    private static final int SPECIFIC_CONSTANT_PRESSURE_SPECIFIC_HEAT = SPECIFIC_ENTHALPY + 1;

    /// 変数の数.
    static final int SIZE = SPECIFIC_CONSTANT_PRESSURE_SPECIFIC_HEAT + 1;

    /// 流体を含むセルの前ステップ値のインスタンスを生成する.
     Previous() {}

    /// 流体の熱力学的温度を返す.
    ///
    /// @return 流体の熱力学的温度 (K).
    double getTemperature() {
      return this.doubleArray()[this.offset() + TEMPERATURE];
    }

    /// 流体の熱力学的温度を変更する.
    ///
    /// @param temperature 流体の新たな熱力学的温度 (K).
    /// @throws IllegalArgumentException `temperature` が 0 以下の場合 (`temperature <= 0`).
    void setTemperature(double temperature) {
      Preconditions.checkArgument(temperature > 0.);

      this.doubleArray()[this.offset() + TEMPERATURE] = temperature;
    }

    /// 流体の密度を返す.
    ///
    /// @return 流体の密度 (kg m<sup>-3</sup>).
    double getDensity() {
      return this.doubleArray()[this.offset() + DENSITY];
    }

    /// 流体の密度を変更する.
    ///
    /// @param density 流体の新たな密度 (kg m<sup>-3</sup>).
    /// @throws IllegalArgumentException `density` が 0 以下の場合 (`density <= 0`).
    void setDensity(double density) {
      Preconditions.checkArgument(density > 0.);

      this.doubleArray()[this.offset() + DENSITY] = density;
    }

    /// 流体の比エンタルピを返す.
    ///
    /// @return 流体の比エンタルピ (J kg<sup>-1</sup>).
    double getSpecificEnthalpy() {
      return this.doubleArray()[this.offset() + SPECIFIC_ENTHALPY];
    }

    /// 流体の比エンタルピを変更する.
    ///
    /// @param specificEnthalpy 流体の新たな比エンタルピ (J kg<sup>-1</sup>).
    void setSpecificEnthalpy(double specificEnthalpy) {
      this.doubleArray()[this.offset() + SPECIFIC_ENTHALPY] = specificEnthalpy;
    }

    /// 流体の定圧比熱を返す.
    ///
    /// @return 流体の定圧比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    double getSpecificConstantPressureSpecificHeat() {
      return this.doubleArray()[this.offset() + SPECIFIC_CONSTANT_PRESSURE_SPECIFIC_HEAT];
    }

    /// 流体の定圧比熱を変更する.
    ///
    /// @param specificConstantPressureSpecificHeat 流体の新たな定圧比熱
    ///                                             (J kg<sup>-1</sup> K<sup>-1</sup>).
    /// @throws IllegalArgumentException `specificConstantPressureSpecificHeat` が 0 以下の場合
    ///     (`specificConstantPressureSpecificHeat <= 0.`)
    void setSpecificConstantPressureSpecificHeat(
        double specificConstantPressureSpecificHeat) {
      Preconditions.checkArgument(specificConstantPressureSpecificHeat > 0.);

      this.doubleArray()[this.offset() + SPECIFIC_CONSTANT_PRESSURE_SPECIFIC_HEAT]
          = specificConstantPressureSpecificHeat;
    }

    /// 永続化オブジェクトへ情報を書き込む.
    ///
    /// @param entity 永続化オブジェクト.
    void write(FluidCellEntity.Previous entity) {
      super.write(entity);

      entity.setTemperature(this.getTemperature());
      entity.setDensity(this.getDensity());
      entity.setSpecificEnthalpy(this.getSpecificEnthalpy());
      entity.setSpecificConstantPressureSpecificHeat(
          this.getSpecificConstantPressureSpecificHeat());
    }

    /// 永続化オブジェクトから情報を読み込む.
    ///
    /// @param entity 永続化オブジェクト.
    void read(FluidCellEntity.Previous entity) {
      super.read(entity);

      this.setTemperature(entity.getTemperature());
      this.setDensity(entity.getDensity());
      this.setSpecificEnthalpy(entity.getSpecificEnthalpy());
      this.setSpecificConstantPressureSpecificHeat(
          entity.getSpecificConstantPressureSpecificHeat());
    }

  } // Previous

  /// 流体を含むセルの成分値.
  abstract static class ComponentValue extends Cell.ComponentValue {

    /// 流体成分の質量分率の位置.
    private static final int FRACTION = Cell.ComponentValue.SIZE;

    /// 流体成分の物質拡散係数の位置.
    private static final int DIFFUSIVITY = FRACTION + 1;

    /// 流体成分のモル分率の位置.
    private static final int MOLAR_FRACTION = DIFFUSIVITY + 1;

    /// 流体成分の粘性係数の位置.
    private static final int VISCOSITY = MOLAR_FRACTION + 1;

    /// 変数の数.
    static final int SIZE = VISCOSITY + 1;

    /// 流体を含むセルの成分値のインスタンスを生成する.
    ComponentValue() {}

    /// 流体成分の質量分率を返す.
    ///
    /// @return 流体成分の質量分率 (-)
    double getFraction() {
      return this.doubleArray()[this.offset() + FRACTION];
    }

    /// 流体成分の質量分率を変更する.
    ///
    /// @param fraction 流体成分の新たな質量分率 (-).
    /// @throws IllegalArgumentException `fraction` が 0 未満か 1 を超えている場合
    ///     (`fraction < 0 || 1 < fraction`).
    void setFraction(double fraction) {
      Preconditions.checkArgument(0. <= fraction && fraction <= 1.);

      this.doubleArray()[this.offset() + FRACTION] = fraction;
    }

    /// 流体成分の物質拡散係数を返す.
    ///
    /// @return 流体成分の物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
    double getDiffusivity() {
      return this.doubleArray()[this.offset() + DIFFUSIVITY];
    }

    /// 流体成分の物質拡散係数を変更する.
    ///
    /// @param diffusivity 流体成分の新たな物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
    /// @throws IllegalArgumentException `diffusivity` が 0 以下の場合 (`diffusivity <= 0`).
    void setDiffusivity(double diffusivity) {
      Preconditions.checkArgument(diffusivity > 0.);

      this.doubleArray()[this.offset() + DIFFUSIVITY] = diffusivity;
    }

    /// 流体成分のモル分率を返す.
    ///
    /// 流体成分のモル分率は永続化の対象外である.
    ///
    /// @return 流体成分のモル分率 (-).
    double getMolarFraction() {
      return this.doubleArray()[this.offset() + MOLAR_FRACTION];
    }

    /// 流体成分のモル分率を変更する.
    ///
    /// 流体成分のモル分率は永続化の対象外である.
    ///
    /// @param molarFraction 流体成分の新たなモル分率 (-).
    /// @throws IllegalArgumentException `molarFraction` が 0 未満か 1 を超えている場合
    ///     (`molarFraction < 0 || 1 < molarFraction`).
    void setMolarFraction(double molarFraction) {
      Preconditions.checkArgument(0. <= molarFraction && molarFraction <= 1.);

      this.doubleArray()[this.offset() + MOLAR_FRACTION] = molarFraction;
    }

    /// 流体成分の粘性係数を返す.
    ///
    /// 流体成分の粘性係数は永続化の対象外である.
    ///
    /// @return 流体成分の粘性係数 (Pa s).
    double getViscosity() {
      return this.doubleArray()[this.offset() + VISCOSITY];
    }

    /// 流体成分の粘性係数を設定する.
    ///
    /// 流体成分の粘性係数は永続化の対象外である.
    ///
    /// @param viscosity 劉岱成分の新たな粘性係数 (Pa s).
    /// @throws IllegalArgumentException `viscosity` が 0 以下の場合 (`viscosity <= 0`)
    void setViscosity(double viscosity) {
      Preconditions.checkArgument(viscosity > 0.);

      this.doubleArray()[this.offset() + VISCOSITY] = viscosity;
    }

    /// 永続化オブジェクトへ情報を書き込む.
    ///
    /// @param entity 永続化オブジェクト.
    void write(FluidCellEntity.ComponentValue<?> entity) {
      super.write(entity);

      entity.setFraction(this.getFraction());
      entity.setDiffusivity(this.getDiffusivity());
    }

    /// 永続化オブジェクトから情報を読み込む.
    ///
    /// @param entity 永続化オブジェクト.
    void read(FluidCellEntity.ComponentValue<?> entity) {
      super.read(entity);

      this.setFraction(entity.getFraction());
      this.setDiffusivity(entity.getDiffusivity());
    }

    /// {@inheritDoc}
    ///
    /// @return {@inheritDoc}
    @Override
    abstract Previous previous();

    /// 流体を含むセルの成分値の前ステップ値.
    abstract static class Previous extends Cell.ComponentValue.Previous {

      /// 流体成分の質量分率の位置.
      private static final int FRACTION = Cell.ComponentValue.Previous.SIZE;

      /// 変数の数.
      static final int SIZE = FRACTION + 1;

      /// 流体を含むセルの成分値のインスタンスを生成する.
      Previous() {}

      /// 流体成分の質量分率を返す.
      ///
      /// @return 流体成分の質量分率 (-).
      double getFraction() {
        return this.doubleArray()[this.offset() + FRACTION];
      }

      /// 流体成分の質量分率を変更する.
      ///
      /// @param fraction 流体成分の新たな質量分率 (-).
      /// @throws IllegalArgumentException `fraction` が 0 未満か 1 を超えている場合
      ///     (`fraction < 0 || 1 < fraction`).
      void setFraction(double fraction) {
        Preconditions.checkArgument(0. <= fraction && fraction <= 1.);

        this.doubleArray()[this.offset() + FRACTION] = fraction;
      }

      /// 永続化オブジェクトへ情報を書き込む.
      ///
      /// @param entity 永続化オブジェクト.
      void write(FluidCellEntity.ComponentValue.Previous entity) {
        super.write(entity);

        entity.setFraction(this.getFraction());
      }

      /// 永続化オブジェクトから情報を読み込む.
      ///
      /// @param entity 永続化オブジェクト.
      void read(FluidCellEntity.ComponentValue.Previous entity) {
        super.read(entity);

        this.setFraction(entity.getFraction());
      }

    } // Previous

  } // ComponentValue

}
