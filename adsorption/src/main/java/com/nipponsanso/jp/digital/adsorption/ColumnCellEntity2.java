/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/// 吸着筒のセルの永続化クラス.
///
/// @param temperature 流体の熱力学的温度 (K).
/// @param pressure 流体の絶対圧力 (Pa).
/// @param density 流体の密度 (kg m<sup>-3</sup>).
/// @param specificEnthalpy 流体の比エンタルピ (J kg<sup>-1</sup>).
/// @param specificConstantPressureSpecificHeat 流体の定圧比熱 (J kg<sup>-1</sup> K<sup>-1</sup>>)
/// @param viscosity 流体の粘性係数 (Pa s).
/// @param thermalConductivity 流体の熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>).
/// @param shellTemperature 容器の熱力学的温度 (K).
/// @param shellSpecificHeat 容器の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
/// @param shellThermalConductivity
/// @param adsorbentSpecificHeat 吸着剤の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
/// @param previous 前ステップ値.
/// @param componentMap 成分のマップ.
/// @since 1.1
/// @author KITAMURA Yuichiro
public record ColumnCellEntity2(
    double temperature,
    double pressure,
    double density,
    double specificEnthalpy,
    double specificConstantPressureSpecificHeat,
    double viscosity,
    double thermalConductivity,
    double shellTemperature,
    double shellSpecificHeat,
    double shellThermalConductivity,
    double adsorbentSpecificHeat,
    Previous previous,
    @JsonProperty("component") Map<ComponentKey, ComponentValue> componentMap)
      implements Serializable {

  @Serial
  private static final long serialVersionUID = 7930008013651506285L;

  /// 吸着筒のセルの前ステップ値の永続化クラス.
  ///
  /// @param temperature 流体の熱力学的温度 (K).
  /// @param density 流体の密度 (kg m<sup>-3</sup>).
  /// @param specificEnthalpy 流体の比エンタルピ (J kg<sup>-1</sup>).
  /// @param specificConstantPressureSpecificHeat 流体の定圧比熱 (J kg<sup>-1</sup> K<sup>-1</sup>>)
  /// @param shellTemperature 容器の熱力学的温度 (K).
  /// @param shellSpecificHeat 容器の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  /// @param adsorbentSpecificHeat 吸着剤の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  public record Previous(
      double temperature,
      double density,
      double specificEnthalpy,
      double specificConstantPressureSpecificHeat,
      double shellTemperature,
      double shellSpecificHeat,
      double adsorbentSpecificHeat) implements Serializable {

    @Serial
    private static final long serialVersionUID = -5738731280071909701L;

  } // Previous

  /// 吸着筒のセルの成分値の永続化クラス.
  ///
  /// @param fraction 流体成分の質量分率 (-).
  /// @param diffusivity 流体成分の物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
  /// @param molarQuantity 吸着質成分のモル吸着量 (mol kg<sup>-1</sup>).
  /// @param molarSpecificConstantVolumeSpecificHeat 吸着質成分のモル定積比熱
  ///     (J kg<sup>-1</sup> K<sup>-1</sup>).
  /// @param previous 前ステップ値.
  public record ComponentValue(
      double fraction,
      double diffusivity,
      double molarQuantity,
      double molarSpecificConstantVolumeSpecificHeat,
      Previous previous) implements Serializable {

    @Serial
    private static final long serialVersionUID = -6414632462566749798L;

    /// 吸着筒のセルの成分値の前ステップ値の永続化クラス.
    ///
    /// @param fraction 流体成分の質量分率 (-).
    /// @param molarQuantity 吸着質成分のモル吸着量 (mol kg<sup>-1</sup>).
    /// @param molarSpecificConstantVolumeSpecificHeat 吸着質成分のモル定積比熱
    ///     (J kg<sup>-1</sup> K<sup>-1</sup>).
    public record Previous(
        double fraction,
        double molarQuantity,
        double molarSpecificConstantVolumeSpecificHeat) implements Serializable {

      @Serial
      private static final long serialVersionUID = -1775447488695452782L;

    } // ComponentValue.Previous

  } // ComponentValue

}
