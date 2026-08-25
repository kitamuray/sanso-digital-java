/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

/// 吸着剤の開始条件.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class AdsorbentStart extends ShellStart {

  /// 吸着剤の開始条件のビルダ.
  abstract static class Builder<B extends Builder<B>> extends ShellStart.Builder<B> {

    /// 吸着剤の開始条件のビルダのインスタンスを生成する.
    Builder() {}

    /// {@inheritDoc}
    ///
    /// @return {@inheritDoc}
    abstract B self();

    /// 吸着剤の開始条件のインスタンスを生成して返す.
    ///
    /// @return 吸着剤の開始条件のインスタンス.
    public abstract AdsorbentStart build();

  } // Builder

  /// 吸着剤の開始条件のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  AdsorbentStart(Builder<?> builder) {
    super(builder);
  }

}
