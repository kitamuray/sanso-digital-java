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

/// 流出境界条件.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class OutletPort extends Port {

  private static final Logger LOGGER = LogManager.getLogger(OutletPort.class);

  /// 流体の絶対圧力 (Pa).
  private final double pressure;

  /// 流出境界条件のインスタンスを生成する.
  ///
  /// @param pressure 流体の絶対圧力 (Pa).
  /// @throws IllegalArgumentException `pressure` が 0 以下の場合 (`pressure <= 0`).
  private OutletPort(double pressure) {
    Preconditions.checkArgument(pressure > 0.);
    this.pressure = pressure;

    LOGGER.debug("pressure = {}", this.pressure);
  }

  /// 流出境界条件のインスタンスを生成して返す.
  ///
  /// @param pressure 流体の絶対圧力 (Pa).
  /// @return 流出境界条件のインスタンス.
  /// @throws IllegalArgumentException `pressure` が 0 以下の場合 (`pressure <= 0`).
  public OutletPort pressureOf(double pressure) {
    return new OutletPort(pressure);
  }

  /// 流体の絶対圧力を返す.
  ///
  /// @return 流体の絶対圧力 (Pa).
  public double pressure() {
    return this.pressure;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    OutletPort that = (OutletPort) o;

    return Double.compare(this.pressure, that.pressure) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.pressure);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("pressure", this.pressure)
        .toString();
  }

}
