/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

/// 開始条件のスケルトン実装.
///
/// このクラスは可変(mutable)フィールドを有しない.
/// 継承した具象クラスは不変(immutable)でなければならない.
///
/// @since 1.1
/// @author KITAMURA Yuihchiro
abstract class Start {

  /// 開始条件のビルダのスケルトン実装.
  ///
  /// @param <B> ビルダの型.
  static abstract class Builder<B extends Builder<B>> {

    /// 開始条件のビルダのインスタンスを生成する.
    Builder() {}

    /// ビルダのインスタンスを返す.
    ///
    /// @return ビルダのインスタンス.
    abstract B self();

    /// 開始条件のインスタンスを生成して返す.
    ///
    /// @return 開始条件のインスタンス.
    abstract Start build();

  } // Builder

  /// 開始条件のインスタンスを生成する.
  ///
  /// @param builder 開始条件のビルダ.
  Start(Builder<?> builder) {}

  /// 指定されたセルへ開始条件を設定する.
  ///
  /// @param cell セル.
  void start(Cell cell) {}

}
