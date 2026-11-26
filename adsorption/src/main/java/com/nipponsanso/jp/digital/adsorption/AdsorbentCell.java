/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import java.util.Map;

/// 流体・吸着質・吸着剤を含むセル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
interface AdsorbentCell extends FluidCell {

  /// 吸着剤の粒子径を返す.
  ///
  /// 永続化の対象ではない.
  ///
  /// @return diameter 吸着剤の粒子径 (m).
  double particleDiameter();

  /// 吸着剤の空隙率を返す.
  ///
  /// 永続化の対象ではない.
  ///
  ///@return 吸着剤の空隙率 (-).
  double voidFraction();

  /// 吸着剤の嵩密度を返す.
  ///
  /// 永続化の対象ではない.
  ///
  /// @return 吸着剤の嵩密度 (kg m<sup>-1</sup>)
  double bulkDensity();

  /// 流体・吸着質・吸着剤の熱力学的温度を返す.
  ///
  /// @return 流体・吸着質・吸着剤の熱力学的温度 (K).
  double getTemperature();

  /// 流体・吸着質・吸着剤の熱力学的温度を設定する.
  ///
  /// @param temperature 流体・吸着質・吸着剤の熱力学的温度 (K).
  /// @throws IllegalArgumentException `temperature` が 0 以下の場合 (`temperature <= 0`).
  void setTemperature(double temperature);

  /// 吸着剤の比熱を返す.
  ///
  /// @return 吸着剤の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  double getAdsorbentSpecificHeat();

  /// 吸着剤の比熱を設定する.
  ///
  /// @param adsorbentSpecificHeat 吸着剤の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  /// @throws IllegalArgumentException `adsorbentSpecificHeat` が 0 以下の場合
  ///     (`adsorbentSpecificHeat <= 0`).
  void setAdsorbentSpecificHeat(double adsorbentSpecificHeat);

  /// 流体・吸着質・吸着剤を含むセルの前ステップ値を返す.
  ///
  /// @return 流体・吸着質・吸着剤を含むセルの前ステップ値.
  @Override
  Previous previous();

  /// 流体・吸着質・吸着剤を含むセルの不変マップを返す.
  ///
  /// @return 流体・吸着質・吸着剤を含むセルの不変マップ.
  @Override
  Map<ComponentKey, ? extends ComponentValue> componentMap();

  /// 流体・吸着質・吸着剤を含むセルの前ステップ値.
  interface Previous extends FluidCell.Previous {

    /// 流体・吸着質・吸着剤の熱力学的温度を返す.
    ///
    /// @return 流体・吸着質・吸着剤の熱力学的温度 (K).
    double getTemperature();

    /// 流体・吸着質・吸着剤の熱力学的温度を設定する.
    ///
    /// @param temperature 流体・吸着質・吸着剤の熱力学的温度 (K).
    /// @throws IllegalArgumentException `temperature` が 0 以下の場合 (`temperature <= 0`).
    void setTemperature(double temperature);

    /// 吸着剤の比熱を返す.
    ///
    /// @return 吸着剤の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    double getAdsorbentSpecificHeat();

    /// 吸着剤の比熱を設定する.
    ///
    /// @param adsorbentSpecificHeat 吸着剤の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    /// @throws IllegalArgumentException `adsorbentSpecificHeat` が 0 以下の場合
    ///     (`adsorbentSpecificHeat <= 0`).
    void setAdsorbentSpecificHeat(double adsorbentSpecificHeat);

  } // Previous

  /// 流体・吸着質・吸着剤を含むセルの成分値.
  interface ComponentValue extends FluidCell.ComponentValue {

    /// 吸着質成分のモル吸着量を返す.
    ///
    /// @return 吸着質成分のモル吸着量 (mol kg<sup>-1</sup>).
    double getMolarQuantity();

    /// 吸着質成分のモル吸着量を設定する.
    ///
    /// @param molarQuantity 吸着質成分のモル吸着量 (mol kg<sup>-1</sup>).
    /// @throws IllegalArgumentException `molarQuantity` が 0 以下の場合 (`molarQuantity <= 0`).
    void setMolarQuantity(double molarQuantity);

    /// 吸着質成分のモル定積比熱を返す.
    ///
    /// @return 吸着質成分のモル定積比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    double getMolarSpecificConstantVolumeSpecificHeat();

    /// 吸着質成分のモル定積比熱を設定する.
    ///
    /// @param molarSpecificConstantVolumeSpecificHeat 吸着質成分のモル定積比熱
    ///     (J kg<sup>-1</sup> K<sup>-1</sup>).
    /// @throws IllegalArgumentException `molarSpecificConstantVolumeSpecificHeat` が 0 以下の場合
    ///     (`molarSpecificConstantVolumeSpecificHeat <= 0`)
    void setMolarSpecificConstantVolumeSpecificHeat(double molarSpecificConstantVolumeSpecificHeat);

    /// 流体・吸着質・吸着剤を含むセルの成分値の前ステップ値を返す.
    ///
    /// @return 流体・吸着質・吸着剤を含むセルの成分値の前ステップ値.
    @Override
    Previous previous();

    /// 流体・吸着質・吸着剤を含むセルの成分値の前ステップ値.
    interface Previous extends FluidCell.ComponentValue.Previous {

      /// 吸着質成分のモル吸着量を返す.
      ///
      /// @return 吸着質成分のモル吸着量 (mol kg<sup>-1</sup>).
      double getMolarQuantity();

      /// 吸着質成分のモル吸着量を設定する.
      ///
      /// @param molarQuantity 吸着質成分のモル吸着量 (mol kg<sup>-1</sup>).
      /// @throws IllegalArgumentException `molarQuantity` が 0 以下の場合 (`molarQuantity <= 0`).
      void setMolarQuantity(double molarQuantity);

      /// 吸着質成分のモル定積比熱を返す.
      ///
      /// @return 吸着質成分のモル定積比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
      double getMolarSpecificConstantVolumeSpecificHeat();

      /// 吸着質成分のモル定積比熱を設定する.
      ///
      /// @param molarSpecificConstantVolumeSpecificHeat 吸着質成分のモル定積比熱
      ///     (J kg<sup>-1</sup> K<sup>-1</sup>).
      /// @throws IllegalArgumentException `molarSpecificConstantVolumeSpecificHeat`
      ///     が 0 以下の場合 (`molarSpecificConstantVolumeSpecificHeat <= 0`)
      void setMolarSpecificConstantVolumeSpecificHeat(
          double molarSpecificConstantVolumeSpecificHeat);

    } // ComponentValue.Previous

  } // ComponentValue

}
