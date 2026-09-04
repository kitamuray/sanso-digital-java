/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.EnumMap;
import java.util.Map;

/// 吸着剤のソルバ.
///
/// @param <S> ソルバの型.
/// @since 1.1
/// @author KITAMURA Yuichiro
abstract class AdsorbentSolver<S extends AdsorbentSolver<S>> extends ShellSolver<S> {

  /// 吸着剤の比熱の緩和係数のデフォルト値 (-).
  public static final double ADSORBENT_SPECIFIC_HEAT_RELAXATION = 1.;

  /// 吸着質成分の定積比熱の緩和係数のデフォルト値 (-).
  public static final double ADSORBATE_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT = 1.;

  /// 吸着剤の比熱の緩和係数 (-).
  private double adsorbentSpecificHeatRelaxation = ADSORBENT_SPECIFIC_HEAT_RELAXATION;

  /// 吸着質成分の定積比熱の緩和係数 (-) のマップ.
  private final Map<ComponentKey, Double> adsorbateSpecificConstantVolumeSpecificHeatRelaxationMap
      = new EnumMap<>(ComponentKey.class);

  /// 吸着剤のソルバのインスタンスを生成する.
  AdsorbentSolver() {}

  /// 吸着剤の比熱の緩和係数を設定する.
  ///
  /// @param adsorbentSpecificHeatRelaxation 吸着剤の比熱の緩和係数 (-).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException `adsorbentSpecificHeatRelaxation` が 0 以下の場合
  ///     (`adsorbentSpecificHeatRelaxation <= 0`).
  public final S adsorbentSpecificHeatRelaxation(double adsorbentSpecificHeatRelaxation) {
    Preconditions.checkArgument(adsorbentSpecificHeatRelaxation > 0.);
    this.adsorbentSpecificHeatRelaxation = adsorbentSpecificHeatRelaxation;
    return this.self();
  }

  /// 吸着質成分の定積比熱の緩和係数を設定する.
  ///
  /// @param adsorbateSpecificConstantVolumeSpecificHeatRelaxation
  ///     吸着質成分の定積比熱の緩和係数 (-).
  /// @return ソルバのインスタンス.
  /// @throws IllegalArgumentException
  ///     `adsorbateSpecificConstantVolumeSpecificHeatRelaxation` が 0 以下の場合
  ///     (`adsorbateSpecificConstantVolumeSpecificHeatRelaxation <= 0`).
  public final S adsorbateSpecificConstantVolumeSpecificHeatRelaxation(
      double adsorbateSpecificConstantVolumeSpecificHeatRelaxation) {
    Preconditions.checkState(this.componentKey() != null);
    Preconditions.checkArgument(adsorbateSpecificConstantVolumeSpecificHeatRelaxation > 0.);
    this.adsorbateSpecificConstantVolumeSpecificHeatRelaxationMap.put(
        this.componentKey(), adsorbateSpecificConstantVolumeSpecificHeatRelaxation);
    return this.self();
  }

  /// 吸着剤の比熱の緩和係数を返す.
  ///
  /// @return 吸着剤の比熱の緩和係数 (-).
  final double adsorbentSpecificHeatRelaxation() {
    return this.adsorbentSpecificHeatRelaxation;
  }

  /// 吸着質成分の定積比熱の緩和係数を返す.
  ///
  /// @param componentKey 成分のキー.
  /// @return 吸着質成分の定積比熱の緩和係数 (-)
  /// @throws NullPointerException `componentKey` が `null` の場合.
  final double adsorbateSpecificConstantVolumeSpecificHeatRelaxationMap(ComponentKey componentKey) {
    Preconditions.checkNotNull(componentKey);
    return this.adsorbateSpecificConstantVolumeSpecificHeatRelaxationMap.getOrDefault(
        componentKey, ADSORBATE_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT);
  }

}
