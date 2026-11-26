/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

/// 吸着剤が関わる開始条件のスケルトン実装.
///
/// このクラスは可変(mutable)フィールドを有しない.
/// 継承した具象クラスは不変(immutable)でなければならない.
///
/// 吸着剤が関わる場合は必ず容器の関わる.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class AdsorbentStart extends ShellStart {

  /// 吸着剤が関わる開始条件のビルダのスケルトン実装.
  ///
  /// @param <B> ビルダの型.
  abstract static class Builder<B extends Builder<B>> extends ShellStart.Builder<B> {

    /// 吸着剤の開始条件のビルダのインスタンスを生成する.
    Builder() {}

    /// 吸着剤が関わる開始条件のインスタンスを生成して返す.
    ///
    /// @return 吸着剤が関わる開始条件のインスタンス.
    @Override
    public abstract AdsorbentStart build();

  } // Builder

  /// 吸着剤が関わるの開始条件のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  AdsorbentStart(Builder<?> builder) {
    super(builder);
  }

  /// 指定された吸着剤を含むセルへ開始条件を設定する.
  ///
  /// @param cell 吸着剤を含むセル.
  void start(AdsorbentCell2 cell) {
    super.start(cell);
  }

}
