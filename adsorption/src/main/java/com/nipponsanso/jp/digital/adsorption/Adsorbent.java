/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import java.util.Objects;

/// 吸着剤.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class Adsorbent {

  /// 吸着剤のビルダ.
  public static final class Builder {

    /// 粒子径 (m).
    private double diameter;

    /// 空隙率 (-).
    private double voidFraction;

    /// 嵩密度 (kg m<sup>-3</sup>).
    private double bulkDensity;

    /// 固相の比熱.
    private SpecificHeat specificHeat;

    /// 吸着剤のビルダのインスタンスを生成する.
    private Builder() {}

    /// 粒子径を設定する.
    ///
    /// @param diameter 粒子径 (m).
    /// @return ビルダ自身.
    /// @throws IllegalArgumentException `diameter` が 0 以下の場合 (`diameter <= 0`).
    public Builder diameter(double diameter) {
      Preconditions.checkArgument(diameter > 0.);
      this.diameter = diameter;
      return this;
    }

    /// 空隙率を設定する.
    ///
    /// @param voidFraction 空隙率 (-).
    /// @return ビルダ自身.
    /// @throws IllegalArgumentException `voidFraction` が 0 以下か 1 を超えている場合
    ///     (`voidFraction <= 0 || 1 < voidFraction`).
    public Builder voidFraction(double voidFraction) {
      Preconditions.checkArgument(0. < voidFraction && voidFraction <= 1.);
      this.voidFraction = voidFraction;
      return this;
    }

    /// 嵩密度を設定する.
    ///
    /// @param bulkDensity 嵩密度 (kg m<sup>-3</sup>).
    /// @return ビルダ自身.
    /// @throws IllegalArgumentException `bulkDensity` が 0 以下の場合 (`bulkDensity <= 0`).
    public Builder bulkDensity(double bulkDensity) {
      Preconditions.checkArgument(bulkDensity > 0.);
      this.bulkDensity = bulkDensity;
      return this;
    }

    /// 固相の比熱を設定する.
    ///
    /// @param specificHeat 固相の比熱.
    /// @return ビルダ自身.
    /// @throws NullPointerException `specificHeat` が `null` の場合.
    public Builder specificHeat(SpecificHeat specificHeat) {
      this.specificHeat = Preconditions.checkNotNull(specificHeat);
      return this;
    }

    /// 吸着剤のインスタンスを生成して返す.
    ///
    /// @return 吸着剤のインスタンス.
    /// @throws IllegalStateException ビルダの設定値が不完全な場合.
    public Adsorbent build() {
      Preconditions.checkState(this.diameter > 0.);
      Preconditions.checkState(this.voidFraction > 0.);
      Preconditions.checkState(this.bulkDensity > 0.);
      Preconditions.checkState(this.specificHeat != null);

      return new Adsorbent(this);
    }

  } // Builder

  /// 粒子径 (m).
  private final double diameter;

  /// 空隙率 (-).
  private final double voidFraction;

  /// 嵩密度 (kg m<sup>-3</sup>).
  private final double bulkDensity;

  /// 比熱.
  private final SpecificHeat specificHeat;

  /// 吸着剤のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private Adsorbent(Builder builder) {
    this.diameter = builder.diameter;
    this.voidFraction = builder.voidFraction;
    this.bulkDensity = builder.bulkDensity;
    this.specificHeat = builder.specificHeat;
  }

  /// ビルダのインスタンスを生成して返す.
  ///
  /// @return ビルダのインスタンス.
  public static Builder builder() {
    return new Adsorbent.Builder();
  }

  /// 粒子径を返す.
  ///
  /// @return 粒子径 (m).
  public double diameter() {
    return this.diameter;
  }

  /// 空隙率を返す.
  ///
  /// @return 空隙率 (-).
  public double voidFraction() {
    return this.voidFraction;
  }

  /// 嵩密度を返す.
  ///
  /// @return 嵩密度 (kg m<sup>-3</sup>).
  public double bulkDensity() {
    return this.bulkDensity;
  }

  /// 比熱を返す.
  ///
  /// @return 比熱.
  public SpecificHeat specificHeat() {
    return this.specificHeat;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    Adsorbent that = (Adsorbent) o;

    return Double.compare(this.diameter, that.diameter) == 0
        && Double.compare(this.voidFraction, that.voidFraction) == 0
        && Double.compare(this.bulkDensity, that.bulkDensity) == 0
        && this.specificHeat.equals(that.specificHeat);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.diameter,
        this.voidFraction,
        this.bulkDensity,
        this.specificHeat);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("diameter", this.diameter)
        .add("voidFraction", this.voidFraction)
        .add("bulkDensity", this.bulkDensity)
        .add("specificHeat", this.specificHeat)
        .toString();
  }

  /// 空筒領域の疑似的な吸着剤のインスタンス.
  private static final Adsorbent EMPTY = Adsorbent.builder()
      .diameter(Double.MAX_VALUE)
      .voidFraction(1.)
      .bulkDensity(Double.MIN_VALUE)
      .specificHeat(ConstantSpecificHeat.valueOf(Double.MIN_VALUE))
      .build();

  /// 空筒領域の疑似的な吸着剤のインスタンスを返す.
  static Adsorbent empty() {
    return EMPTY;
  }

}
