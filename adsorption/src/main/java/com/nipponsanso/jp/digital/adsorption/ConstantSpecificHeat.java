/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import java.util.Objects;

/// 定数値による固相の比熱.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class ConstantSpecificHeat extends SpecificHeat {

  /// 定数値 (J kg<sup>-1</sup> K<sup>-1</sup>).
  private final double value;

  /// 定数値による固相の比熱のインスタンスを生成する.
  ///
  /// @param value 定数値 (J kg<sup>-1</sup> K<sup>-1</sup>).
  /// @throws IllegalArgumentException `value` が 0 以下の場合 (`value <= 0`).
  private ConstantSpecificHeat(double value) {
    Preconditions.checkArgument(value > 0.);

    this.value = value;
  }

  /// 定数値を返す.
  ///
  /// @return 定数値 (J kg<sup>-1</sup> K<sup>-1</sup>).
  public double value() {
    return this.value;
  }

  /// {@inheritDoc}
  ///
  /// @param temperature {@inheritDoc}
  /// @return {@inheritDoc}
  @Override
  double specificHeat(double temperature) {
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

    ConstantSpecificHeat that = (ConstantSpecificHeat) o;

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

  /// 定数値による固相の比熱のインスタンスを生成して返す.
  ///
  /// @param value 定数値 (J kg<sup>-1</sup> K<sup>-1</sup>).
  /// @return 定数値による固相の比熱のインスタンス.
  /// @throws IllegalArgumentException `value` が 0 以下の場合 (`value <= 0`).
  public static ConstantSpecificHeat valueOf(double value) {
    return new ConstantSpecificHeat(value);
  }

}
