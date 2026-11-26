/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.collect.Maps;
import java.util.Map;

/// 流体を含むセル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
interface FluidCell2 extends Cell2 {

  /// 流体の熱力学的温度を返す.
  ///
  /// @return 流体の熱力学的温度 (K).
  double getTemperature();

  /// 流体の熱力学的温度を設定する.
  ///
  /// @param temperature 流体の熱力学的温度 (K).
  /// @throws IllegalArgumentException `temperature` が 0 以下の場合 (`temperature <= 0`).
  void setTemperature(double temperature);

  /// 流体の絶対圧力を返す.
  ///
  /// @return 流体の絶対圧力 (Pa).
  double getPressure();

  /// 流体の絶対圧力を設定する.
  ///
  /// @param pressure 流体の絶対圧力 (Pa).
  /// @throws IllegalArgumentException `pressure` が 0 以下の場合 (`pressure <= 0`).
  void setPressure(double pressure);

  /// 流体の密度を返す.
  ///
  /// @return 流体の密度 (kg m<sup>-3</sup>).
  double getDensity();

  /// 流体の密度を設定する.
  ///
  /// @param density 流体の密度 (kg m<sup>-3</sup>).
  /// @throws IllegalArgumentException `density` が 0 以下の場合 (`density <= 0`).
  void setDensity(double density);

  /// 流体の比エンタルピを返す.
  ///
  /// @return 流体の比エンタルピ (J kg<sup>-1</sup>).
  double getSpecificEnthalpy();

  /// 流体の比エンタルピを設定する.
  ///
  /// @param specificEnthalpy 流体の比エンタルピ (J kg<sup>-1</sup>).
  void setSpecificEnthalpy(double specificEnthalpy);

  /// 流体の定圧比熱を返す.
  ///
  /// @return 流体の定圧比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  double getSpecificConstantPressureSpecificHeat();

  /// 流体の定圧比熱を設定する.
  ///
  /// @param specificConstantPressureSpecificHeat 流体の定圧比熱
  ///     (J kg<sup>-1</sup> K<sup>-1</sup>).
  /// @throws IllegalArgumentException `specificConstantPressureSpecificHeat` が 0 以下の場合
  ///     (`specificConstantPressureSpecificHeat <= 0`).
  void setSpecificConstantPressureSpecificHeat(double specificConstantPressureSpecificHeat);

  /// 流体の粘性係数を返す.
  ///
  /// @return 流体の粘性係数 (Pa).
  double getViscosity();

  /// 流体の粘性係数を設定する.
  ///
  /// @param viscosity 流体の粘性係数 (Pa s).
  /// @throws IllegalArgumentException `viscosity` が 0 以下の場合 (`viscosity <= 0`).
  void setViscosity(double viscosity);

  /// 流体の熱伝導率を返す.
  ///
  /// @return 流体の熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>).
  double getThermalConductivity();

  /// 流体の熱伝導率を設定する.
  ///
  /// @param thermalConductivity 流体の熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>).
  /// @throws IllegalArgumentException `thermalConductivity` が 0 以下の場合
  ///     (`thermalConductivity <= 0`).
  void setThermalConductivity(double thermalConductivity);

  /// 流体の平均分子量を返す.
  ///
  /// 永続化の対象ではない.
  ///
  /// @return 流体の平均分子量.
  double getMolecularWeight();

  /// 流体の平均分子量を設定する.
  ///
  /// 永続化の対象ではない.
  ///
  /// @param molecularWeight 流体の平均分子量.
  void setMolecularWeight(double molecularWeight);

  /// 流体を含むセルの前ステップ値を返す.
  ///
  /// @return 流体を含むセルの前ステップ値.
  @Override
  Previous previous();

  /// 流体を含むセルの成分の不変マップを返す.
  ///
  /// @return 流体を含むセルの成分の不変マップ.
  @Override
  Map<ComponentKey, ? extends ComponentValue> componentMap();

  /// 流体を含むセルのインスタンスを生成して返す.
  ///
  /// @param composition 組成.
  /// @return 流体を含むセルのインスタンス.
  static FluidCell2 newInstance(Composition composition) {
    return new AbstractFluidCell() {

      private final FluidCell2.Previous previous = FluidCell2.Previous.newInstance();

      private final Map<ComponentKey, ? extends FluidCell2.ComponentValue> componentMap
          = composition.componentKeySet().stream()
          .collect(
              Maps.toImmutableEnumMap(
                  componentKey -> componentKey,
                  _ -> FluidCell2.ComponentValue.newInstance()));

      @Override
      public FluidCell2.Previous previous() {
        return this.previous;
      }

      @Override
      public Map<ComponentKey, ? extends FluidCell2.ComponentValue> componentMap() {
        return this.componentMap;
      }

    };
  }

  /// 流体を含むセルの前ステップ値.
  interface Previous extends Cell2.Previous {

    /// 流体の熱力学的温度を返す.
    ///
    /// @return 流体の熱力学的温度 (K).
    double getTemperature();

    /// 流体の熱力学的温度を設定する.
    ///
    /// @param temperature 流体の熱力学的温度 (K).
    /// @throws IllegalArgumentException `temperature` が 0 以下の場合 (`temperature <= 0`).
    void setTemperature(double temperature);

    /// 流体の密度を返す.
    ///
    /// @return 流体の密度 (kg m<sup>-3</sup>).
    double getDensity();

    /// 流体の密度を設定する.
    ///
    /// @param density 流体の密度 (kg m<sup>-3</sup>).
    /// @throws IllegalArgumentException `density` が 0 以下の場合 (`density <= 0`).
    void setDensity(double density);

    /// 流体の比エンタルピを返す.
    ///
    /// @return 流体の比エンタルピ (J kg<sup>-1</sup>).
    double getSpecificEnthalpy();

    /// 流体の比エンタルピを設定する.
    ///
    /// @param specificEnthalpy 流体の比エンタルピ (J kg<sup>-1</sup>).
    void setSpecificEnthalpy(double specificEnthalpy);

    /// 流体の定圧比熱を返す.
    ///
    /// @return 流体の定圧比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    double getSpecificConstantPressureSpecificHeat();

    /// 流体の定圧比熱を設定する.
    ///
    /// @param specificConstantPressureSpecificHeat 流体の定圧比熱
    ///     (J kg<sup>-1</sup> K<sup>-1</sup>).
    /// @throws IllegalArgumentException `specificConstantPressureSpecificHeat` が 0 以下の場合
    ///     (`specificConstantPressureSpecificHeat <= 0`).
    void setSpecificConstantPressureSpecificHeat(double specificConstantPressureSpecificHeat);

    /// 流体を含むセルの成分値のインスタンスを生成して返す.
    ///
    /// @return 流体を含むセルの成分値のインスタンス.
    static Previous newInstance() {
      return new AbstractFluidCell.Previous() {};
    }

  } // Previous

  /// 流体を含むセルの成分値.
  interface ComponentValue extends Cell2.ComponentValue {

    /// 流体成分の質量分率を返す.
    ///
    /// @return 流体成分の質量分率 (-).
    double getFraction();

    /// 流体成分の質量分率を設定する.
    ///
    /// @param fraction 流体成分の質量分率 (-).
    /// @throws IllegalArgumentException `fraction` が 0 未満か 1 を超えている場合
    ///     (`fraction < 0 || 1 < fraction`).
    void setFraction(double fraction);

    /// 流体成分の物質拡散係数を返す.
    ///
    /// @return 流体成分の物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
    double getDiffusivity();

    /// 流体成分の物質拡散係数を設定する.
    ///
    /// @param diffusivity 流体成分の物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
    /// @throws IllegalArgumentException `diffusivity` が 0 以下の場合 (`diffusivity <= 0`).
    void setDiffusivity(double diffusivity);

    /// 流体成分のモル分率を返す.
    ///
    /// 永続化の対象ではない.
    ///
    /// @return 流体成分のモル分率 (-).
    double getMolarFraction();

    /// 流体成分のモル率を設定する.
    ///
    /// 永続化の対象ではない.
    ///
    /// @param molarFraction 流体成分のモル分率 (-).
    void setMolarFraction(double molarFraction);

    /// 流体成分の粘性係数を返す.
    ///
    /// 永続化の対象ではない.
    ///
    /// @return 流体成分の粘性係数 (Pa s)
    double getViscosity();

    /// 流体成分の粘性係数を設定する.
    ///
    /// 永続化の対象ではない.
    ///
    /// @param viscosity 流体成分の粘性係数 (Pa s).
    void setViscosity(double viscosity);

    /// 流体を含むセルの成分値の前ステップ値を返す.
    ///
    /// @return 流体を含むセルの成分値の前ステップ値.
    @Override
    Previous previous();

    /// 流体を含むセルの成分値のインスタンスを生成して返す.
    ///
    /// @return 流体を含むセルの成分値のインスタンス.
    static ComponentValue newInstance() {
      return new AbstractFluidCell.ComponentValue() {

        private final ComponentValue.Previous previous = ComponentValue.Previous.newInstance();

        @Override
        public ComponentValue.Previous previous() {
          return this.previous;
        }

      };
    }

    /// 流体を含むセルの成分値の前ステップ値.
    interface Previous extends Cell2.ComponentValue.Previous {

      /// 流体成分の質量分率を返す.
      ///
      /// @return 流体成分の質量分率 (-).
      double getFraction();

      /// 流体成分の質量分率を設定する.
      ///
      /// @param fraction 流体成分の質量分率 (-).
      /// @throws IllegalArgumentException `fraction` が 0 未満か 1 を超えている場合
      ///     (`fraction < 0 || 1 < fraction`).
      void setFraction(double fraction);

      /// 流体を含むセルの成分値の前ステップ値のインスタンスを生成して返す.
      ///
      /// @return 流体を含むセルの成分値の前ステップ値のインスタンス.
      static Previous newInstance() {
        return new AbstractFluidCell.ComponentValue.Previous() {};
      }

    } // ComponentValue.Previous

  } // ComponentValue

}
