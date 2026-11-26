/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/// 貯槽のセルの永続化クラス.
///
/// @param temperature 流体の熱力学的温度 (K).
/// @param pressure 流体の絶対圧力 (Pa).
/// @param density 流体の密度 (kg m<sup>-3</sup>).
/// @param specificEnthalpy 流体の比エンタルピ (J kg<sup>-1</sup>).
/// @param specificConstantPressureSpecificHeat 流体の定圧比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
/// @param viscosity 流体の粘性係数 (Pa s).
/// @param thermalConductivity 流体の熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>).
/// @param previous 前ステップ値.
/// @param componentMap 成分のマップ.
/// @since 1.0
/// @author KITAMURA Yuichiro
public record TankCellEntity2(
    double temperature,
    double pressure,
    double density,
    double specificEnthalpy,
    double specificConstantPressureSpecificHeat,
    double viscosity,
    double thermalConductivity,
    Previous previous,
    @JsonProperty("component") Map<ComponentKey, ComponentValue> componentMap)
      implements Serializable {

  @Serial
  private static final long serialVersionUID = -7801081476653706011L;

  /// 貯槽のセルの前ステップ値の永続化クラス.
  ///
  /// @param temperature 流体の熱力学的温度 (K).
  /// @param density 流体の密度 (kg m<sup>-3</sup>).
  /// @param specificEnthalpy 流体の比エンタルピ (J kg<sup>-1</sup>).
  /// @param specificConstantPressureSpecificHeat 流体の定圧比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  public record Previous(
      double temperature,
      double density,
      double specificEnthalpy,
      double specificConstantPressureSpecificHeat) implements Serializable {

    @Serial
    private static final long serialVersionUID = 7802761683037021862L;

  } // Previous

  /// 貯槽のセルの成分値の永続化クラス.
  ///
  /// @param fraction 流体成分の質量分率 (-).
  /// @param diffusivity 流体成分の物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>)
  /// @param previous 前ステップ値.
  public record ComponentValue(
      double fraction,
      double diffusivity,
      Previous previous) implements Serializable {

    @Serial
    private static final long serialVersionUID = -4792074988771542757L;

    /// 貯槽のセルの成分値の前ステップ値の永続化クラス.
    ///
    /// @param fraction 流体成分の質量分率 (-).
    public record Previous(
        double fraction) implements Serializable {

      @Serial
      private static final long serialVersionUID = -3793189106270619139L;

    } // ComponentValue.Previous

  } // ComponentValue

}
