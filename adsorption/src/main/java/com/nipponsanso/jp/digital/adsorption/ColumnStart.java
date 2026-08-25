/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.MoreObjects;

/// 吸着筒の開始条件.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public class ColumnStart extends AdsorbentStart {

  /// 吸着筒の開始条件のビルダ.
  public static final class Builder extends AdsorbentStart.Builder<Builder> {

    /// 吸着筒の開始条件のビルダのインスタンスを生成する.
    private Builder() {}

    /// {@inheritDoc}
    ///
    /// @return {@inheritDoc}
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

  /// 吸着筒の開始条件のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private ColumnStart(Builder builder) {
    super(builder);
  }

  /// ビルダのインスタンスを生成して返す.
  ///
  /// @return ビルダのインスタンス.
  public static Builder builder() {
    return new Builder();
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("temperature", this.temperature())
        .add("pressure", this.pressure())
        .add("fractionMap", this.fractionMap())
        .add("shellTemperature", this.shellTemperature())
        .toString();
  }

}
