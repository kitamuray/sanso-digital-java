/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.MoreObjects;

/// 貯槽の開始条件.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class TankStart extends FluidStart {

  /// 貯槽の開始条件のビルダ.
  public static final class Builder extends FluidStart.Builder<Builder> {

    /// 貯槽の開始条件のビルダのインスタンスを生成する.
    private Builder() {}

    /// {@inheritDoc}
    ///
    /// @return {@inheritDoc}
    @Override
    Builder self() {
      return this;
    }

    /// 貯槽の開始条件のインスタンスを生成して返す.
    ///
    /// @return 貯槽の開始条件のインスタンス.
    @Override
    public TankStart build() {
      return new TankStart(this);
    }

  } // Builder

  /// 貯槽の開始条件のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private TankStart(Builder builder) {
    super(builder);
  }

  /// 貯槽の開始条件のビルダのインスタンスを生成して返す.
  ///
  /// @return 貯槽の開始条件のビルダのインスタンス.
  public static Builder builder() {
    return new Builder();
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("temperature", this.temperature())
        .add("pressure", this.pressure())
        .add("fractionMap", this.fractionMap())
        .toString();
  }

}
