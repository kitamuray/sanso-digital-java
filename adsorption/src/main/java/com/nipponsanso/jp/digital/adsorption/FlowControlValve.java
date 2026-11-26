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

/// 流量調整弁.
///
/// このクラスのインスタンスは不変（immutable）である.
///
/// 逆流は許容しない.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class FlowControlValve extends Valve {

  private static final Logger LOGGER = LogManager.getLogger(FlowControlValve.class);

  /// 流量調整弁のビルダ.
  public static final class Builder {

    /// 流量 (kg s<sup>-1</sup>).
    private double flowRate;

    /// CV 値.
    private double cvValue;

    /// 流量調整弁のビルダのインスタンスを生成する.
    private Builder() {}

    /// 流量を設定する.
    ///
    /// @param flowRate 流量 (kg s<sup>-1</sup>).
    /// @return ビルダのインスタンス自身.
    /// @throws IllegalArgumentException `flowRate` が 0 以下の場合 (`flowRate <= 0`).
    public Builder flowRate(double flowRate) {
      Preconditions.checkArgument(flowRate > 0.);

      this.flowRate = flowRate;

      return this;
    }

    /// CV 値を設定する.
    ///
    /// @param cvValue CV 値.
    /// @return ビルダのインスタンス自身.
    /// @throws IllegalArgumentException `cvValue` が 0 以下の場合 (`cvValue <= 0`).
    public Builder cvValue(double cvValue) {
      Preconditions.checkArgument(cvValue > 0.);

      this.cvValue = cvValue;

      return this;
    }

    /// 流量調整弁のインスタンスを生成して返す.
    ///
    /// @return 流量調整弁のインスタンス.
    /// @throws IllegalStateException ビルダの状態が不完全な場合.
    public FlowControlValve build() {
      Preconditions.checkState(this.flowRate > 0.);
      Preconditions.checkState(this.cvValue > 0.);

      return new FlowControlValve(this);
    }

  } // Builder

  /// 流量 (kg s<sup>-1</sup>).
  private final double flowRate;

  /// CV 値.
  private final double cvValue;

  /// 流量調整弁のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private FlowControlValve(Builder builder) {
    this.flowRate = builder.flowRate;
    this.cvValue = builder.cvValue;

    LOGGER.debug("flowRate = {} (kg s^-1)", this.flowRate);
    LOGGER.debug("cvValue = {}", this.cvValue);
  }

  /**
   * ビルダのインスタンスを生成して返す.
   *
   * @return ビルダのインスタンス.
   */
  public static Builder builder() {
    return new Builder();
  }

  /// 弁の流量を返す.
  ///
  /// 二次側が一次側よりも高圧の場合, 流量をゼロと見なす.
  ///
  /// @param upCell 一次側の流体セル.
  /// @param downCell 二次側の流体セル.
  /// @return 弁の流量 (kg s<sup>-1</sup>).
  @Override
  double flowRate(FluidCell2 upCell, FluidCell2 downCell) {
    if (upCell.getPressure() > downCell.getPressure()) {
      return Math.min(super.flowRate(upCell, downCell), this.flowRate);
    } else {
      LOGGER.warn("reverse flow: {} (Pa) < {} (Pa)", upCell.getPressure(), downCell.getPressure());
      return 0.;
    }
  }

  /// 流量を返す.
  ///
  /// @return 流量 (kg s<sup>-1</sup>).
  public double flowRate() {
    return this.flowRate;
  }

  @Override
  public double cvValue() {
    return this.cvValue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    FlowControlValve that = (FlowControlValve) o;

    return Double.compare(this.flowRate, that.flowRate) == 0
        && Double.compare(this.cvValue, that.cvValue) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.flowRate,
        this.cvValue);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("flowRate", this.flowRate)
        .add("cvValue", this.cvValue)
        .toString();
  }

}
