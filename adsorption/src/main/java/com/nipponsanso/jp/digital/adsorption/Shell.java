/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/// 容器.
///
/// このクラスのインスタンスは不変（immutable）である.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class Shell {

  private static final Logger LOGGER = LogManager.getLogger(Shell.class);

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

  /// 密度 (kg m<sup>-3</sup>).
  private final double density;

  /// 比熱.
  private final SpecificHeat specificHeat;

  /// 熱伝導率.
  private final ThermalConductivity thermalConductivity;

  /// 容器のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private Shell(Builder builder) {
    this.density = builder.density;
    this.specificHeat = builder.specificHeat;
    this.thermalConductivity = builder.thermalConductivity;

    LOGGER.debug("density = {} (kg m^-3)", this.density);
    LOGGER.debug("specificHeat = {}", this.specificHeat);
    LOGGER.debug("thermalConductivity = {}", this.thermalConductivity);
  }

  /// ビルダのインスタンスを生成して返す.
  ///
  /// @return ビルダのインスタンス.
  public static Builder builder() {
    return new Builder();
  }

  /// 密度を返す.
  ///
  /// @return 密度 (kg m<sup>-3</sup>).
  public double density() {
    return this.density;
  }

  /// 比熱を返す.
  ///
  /// @return 比熱.
  public SpecificHeat specificHeat() {
    return this.specificHeat;
  }

  /// 熱伝導率を返す.
  ///
  /// @return 熱伝導率.
  public ThermalConductivity thermalConductivity() {
    return this.thermalConductivity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    Shell that = (Shell) o;

    return Double.compare(this.density, that.density) == 0
        && this.specificHeat.equals(that.specificHeat)
        && this.thermalConductivity.equals(that.thermalConductivity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.density,
        this.specificHeat,
        this.thermalConductivity);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("density", this.density)
        .add("specificHeat", this.specificHeat)
        .add("thermalConductivity", this.thermalConductivity)
        .toString();
  }

}
