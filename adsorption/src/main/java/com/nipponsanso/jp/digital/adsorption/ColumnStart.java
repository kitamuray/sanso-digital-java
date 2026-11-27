/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import java.util.Objects;

/// 吸着筒の開始条件.
///
/// このクラスのインスタンスは不変(immutable)である.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public class ColumnStart extends Start {

  /// 吸着筒の開始条件のビルダ.
  public static final class Builder extends Start.Builder<Builder> {

    /// 容器の熱力学的温度 (K).
    private double shellTemperature;

    /// 吸着筒の開始条件のビルダのインスタンスを生成する.
    private Builder() {}

    /// 容器の熱力学的温度を設定する.
    ///
    /// @param shellTemperature 容器の熱力学的温度 (K).
    /// @return ビルダのインスタンス.
    /// @throws IllegalArgumentException `shellTemperature` が 0 以下の場合
    ///     (`shellTemperature <= 0`).
    public Builder shellTemperature(double shellTemperature) {
      Preconditions.checkArgument(shellTemperature > 0.);
      this.shellTemperature = shellTemperature;
      return this;
    }

    @Override
    Builder self() {
      return this;
    }

    /// 吸着筒の開始条件のインスタンスを生成して返す.
    ///
    /// @return 吸着筒の開始条件のインスタンス.
    @Override
    public ColumnStart build() {
      return new ColumnStart(this);
    }

  } // Builder

  /// 容器の熱力学的温度 (K).
  private final double shellTemperature;

  /// 吸着筒の開始条件のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private ColumnStart(Builder builder) {
    super(builder);
    Preconditions.checkState(builder.shellTemperature > 0.);

    this.shellTemperature = builder.shellTemperature;
  }

  /// ビルダのインスタンスを生成して返す.
  ///
  /// @return ビルダのインスタンス.
  public static Builder builder() {
    return new Builder();
  }

  /// 容器の熱力学的温度を返す.
  ///
  /// @return 容器の熱力学的温度 (K).
  public double shellTemperature() {
    return this.shellTemperature;
  }

  /// 指定された吸着筒のセルへ開始条件を設定する.
  ///
  /// @param cell 吸着筒のセル.
  void start(ColumnCell cell) {
    cell.setTemperature(this.temperature());
    cell.setPressure(this.pressure());
    cell.componentMap().forEach((key, value) -> value.setFraction(this.fractionMap().get(key)));
    cell.setShellTemperature(this.shellTemperature);
  }

  @Override
  public boolean equals(Object o) {
    if (!super.equals(o)) {
      return false;
    }

    ColumnStart that = (ColumnStart) o;

    return Double.compare(this.shellTemperature, that.shellTemperature) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        super.hashCode(),
        this.shellTemperature);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("temperature", this.temperature())
        .add("pressure", this.pressure())
        .add("fractionMap", this.fractionMap())
        .add("shellTemperature", this.shellTemperature)
        .toString();
  }

}
