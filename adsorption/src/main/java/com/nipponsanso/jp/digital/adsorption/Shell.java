/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import org.apache.logging.log4j.LogManager;

/// 容器.
///
/// @param density 密度 (kg m<sup>-3</sup>).
/// @param specificHeat 比熱.
/// @param thermalConductivity 熱伝導率.
public record Shell(
    double density,
    SpecificHeat specificHeat,
    ThermalConductivity thermalConductivity) {

  static {
    LogManager.getLogger(Shell.class);
  }

  /// ビルダ.
  public static final class Builder {

    /// 密度 (kg m<sup>-3</sup>).
    private double density;

    /// 比熱.
    private SpecificHeat specificHeat;

    /// 熱伝導率.
    private ThermalConductivity thermalConductivity;

    /// ビルダのインスタンスを生成する.
    private Builder() {}

    /// 密度を設定する.
    ///
    /// @param density 密度 (kg m<sup>-3</sup>).
    /// @return ビルダのインスタンス自身.
    /// @throws IllegalArgumentException `density` が 0 以下の場合 (`density <= 0`).
    public Builder density(double density) {
      Preconditions.checkArgument(density > 0.);

      this.density = density;

      return this;
    }

    /// 比熱を設定する.
    ///
    /// @param specificHeat 比熱.
    /// @return ビルダのインスタンス自身.
    /// @throws NullPointerException `specificHeat` が `null` の場合.
    public Builder specificHeat(SpecificHeat specificHeat) {
      this.specificHeat = Preconditions.checkNotNull(specificHeat);

      return this;
    }

    /// 熱伝導率を設定する.
    ///
    /// @param thermalConductivity 熱伝導率.
    /// @return ビルダのインスタンス自身.
    /// @throws NullPointerException `thermalConductivity` が `null` の場合.
    public Builder thermalConductivity(ThermalConductivity thermalConductivity) {
      this.thermalConductivity = Preconditions.checkNotNull(thermalConductivity);

      return this;
    }

    /// 容器のインスタンスを生成して返す.
    ///
    /// @return 容器のインスタンス.
    /// @throws IllegalStateException ビルダの状態が不完全な場合.
    public Shell build() {
      Preconditions.checkState(this.density > 0.);
      Preconditions.checkState(this.specificHeat != null);
      Preconditions.checkState(this.thermalConductivity != null);

      return new Shell(this);
    }

  } // Builder

  /// 容器のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private Shell(Builder builder) {
    this(builder.density, builder.specificHeat, builder.thermalConductivity);
  }

  /// ビルダのインスタンスを生成して返す.
  ///
  /// @return ビルダのインスタンス.
  public static Builder builder() {
    return new Builder();
  }

}
