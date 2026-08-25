/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import java.util.Objects;

/// 真空ポンプ.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class VacuumPump {

  /// 真空ポンプのビルダ.
  public static final class Builder {

    /// 流体の最大体積流量 (m<sup>3</sup> s<sup>-1</sup>).
    private double maxVolumeFlowRate;

    /// 流体の最小絶対圧力 (Pa).
    private double minPressure;

    /// 真空ポンプのビルダのインスタンスを生成する.
    private Builder() {}

    /// 流体の最大体積流量を設定する.
    ///
    /// @param maxVolumeFlowRate 流体の最大体積流量 (m<sup>3</sup> s<sup>-1</sup>).
    /// @return ビルダのインスタンス.
    /// @throws IllegalArgumentException `maxVolumeFlowRate` が 0 以下の場合
    ///     (`maxVolumeFlowRate <= 0`).
    public Builder maxVolumeFlowRate(double maxVolumeFlowRate) {
      Preconditions.checkArgument(maxVolumeFlowRate > 0.);

      this.maxVolumeFlowRate = maxVolumeFlowRate;
      return this;
    }

    /// 流体の最小絶対圧力を設定する.
    ///
    /// @param minPressure 流体の最小絶対圧力 (Pa).
    /// @throws IllegalArgumentException `minPressure` が 0 以下の場合 (`minPressure <= 0`).
    public Builder minPressure(double minPressure) {
      Preconditions.checkArgument(minPressure > 0.);

      this.minPressure = minPressure;
      return this;
    }

    /// 真空ポンプのインスタンスを生成して返す.
    ///
    /// @return 真空ポンプのインスタンス.
    /// @throws IllegalStateException ビルダが不完全な場合.
    public VacuumPump build() {
      Preconditions.checkState(this.maxVolumeFlowRate > 0.);
      Preconditions.checkState(this.minPressure > 0.);

      return new VacuumPump(this);
    }

  } // Builder

  /// 流体の最大体積流量 (m<sup>3</sup> s<sup>-1</sup>).
  private final double maxVolumeFlowRate;

  /// 流体の最小絶対圧力 (Pa).
  private final double minPressure;

  /// 真空ポンプのインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private VacuumPump(Builder builder) {
    this.maxVolumeFlowRate = builder.maxVolumeFlowRate;
    this.minPressure = builder.minPressure;
  }

  /// 流体の最大体積流量を返す.
  ///
  /// @return 流体の最大体積流量 (m<sup>3</sup> s<sup>-1</sup>).
  public double maxVolumeFlowRate() {
    return this.maxVolumeFlowRate;
  }

  /// 流体の最小絶対圧力を返す.
  ///
  /// @return 流体の最小絶対圧力 (Pa).
  public double minPressure() {
    return this.minPressure;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    VacuumPump that = (VacuumPump) o;

    return Double.compare(this.maxVolumeFlowRate, that.maxVolumeFlowRate) == 0
        && Double.compare(this.minPressure, that.minPressure) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.maxVolumeFlowRate,
        this.minPressure);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("maxVolumeFlowRate", this.maxVolumeFlowRate)
        .add("minPressure", this.minPressure)
        .toString();
  }

}
