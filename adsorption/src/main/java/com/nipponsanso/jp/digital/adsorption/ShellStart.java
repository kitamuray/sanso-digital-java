/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/// 容器が関わる開始条件のスケルトン実装.
///
/// このクラスは可変(mutable)フィールドを有しない.
/// 継承した具象クラスは不変(immutable)でなければならない.
///
/// 容器が関わる場合は必ず流体も関わる.
///
/// @since 1.1
/// @author KITAMURA Yuichiro
abstract class ShellStart extends FluidStart {

  private static final Logger LOGGER = LogManager.getLogger(ShellStart.class);

  /// 容器が関わる開始条件のビルダのスケルトン実装.
  ///
  /// @param <B> ビルダの型.
  static abstract class Builder<B extends Builder<B>> extends FluidStart.Builder<B> {

    /// 容器の熱力学的温度 (K).
    private double shellTemperature = 298.15;

    /// 容器の開始条件のビルダのインスタンスを生成する.
    Builder() {}

    /// 容器の熱力学的温度を設定する.
    ///
    /// @param shellTemperature 容器の熱力学的温度 (K).
    /// @return ビルダのインスタンス.
    /// @throws IllegalArgumentException `shellTemperature` が 0 以下の場合
    ///     (`shellTemperature <= 0`).
    public final B shellTemperature(double shellTemperature) {
      Preconditions.checkArgument(shellTemperature > 0.);

      this.shellTemperature = shellTemperature;

      return this.self();
    }

    /// 容器が関わる開始条件のインスタンスを生成して返す.
    ///
    /// @return 容器が関わる開始条件のインスタンス.
    @Override
    public abstract ShellStart build();

  }

  /// 容器の熱力学的温度 (K).
  private final double shellTemperature;

  /// 容器が関わる開始条件のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  ShellStart(Builder<?> builder) {
    super(builder);

    this.shellTemperature = builder.shellTemperature;

    LOGGER.debug("shellTemperature = {}", this.shellTemperature);
  }

  /// 容器の熱力学的温度を返す.
  ///
  /// @return 容器の熱力学的温度 (K).
  public double shellTemperature() {
    return this.shellTemperature;
  }

  /// 指定された容器を含むセルへ開始条件を設定する.
  ///
  /// @param cell セル.
  void start(ShellCell2 cell) {
    super.start(cell);
    cell.setShellTemperature(this.shellTemperature);
  }

  @Override
  public boolean equals(Object o) {
    if (!super.equals(o)) {
      return false;
    }

    ShellStart that = (ShellStart) o;

    return Double.compare(this.shellTemperature, that.shellTemperature) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.shellTemperature);
  }

}
