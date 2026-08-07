/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import java.util.Objects;

/// 定数値による固相の熱伝導率.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class ConstantThermalConductivity extends ThermalConductivity {

  /// 定数値 (W m<sup>-1</sup> K<sup>-1</sup>).
  private final double value;

  /// 定数値による固相の熱伝導率のインスタンスを生成する.
  ///
  /// @param value 定数値 (W m<sup>-1</sup> K<sup>-1</sup>).
  /// @throws IllegalArgumentException `value` が 0 以下の場合 (`value <= 0`).
  private ConstantThermalConductivity(double value) {
    Preconditions.checkArgument(value > 0.);

    this.value = value;
  }

  /// 定数値を返す.
  ///
  /// @return 定数値 (W m<sup>-1</sup> K<sup>-1</sup>).
  public double value() {
    return this.value;
  }

  /// {@inheritDoc}
  ///
  /// @param temperature {@inheritDoc}
  /// @return {@inheritDoc}
  @Override
  double thermalConductivity(double temperature) {
    return this.value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    ConstantThermalConductivity that = (ConstantThermalConductivity) o;

    return Double.compare(this.value, that.value) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.value);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("value", this.value)
        .toString();
  }

  /**
   * 定数値による固相の熱伝導率のインスタンスを生成して返す.
   *
   * @param value 定数値 (W m<sup>-1</sup> K<sup>-1</sup>).
   * @return 定数値による固相の熱伝導率のインスタンス.
   * @throws IllegalArgumentException {@code value} が 0 以下の場合 ({@code value <= 0}).
   */
  public static ConstantThermalConductivity valueOf(double value) {
    return new ConstantThermalConductivity(value);
  }

}
