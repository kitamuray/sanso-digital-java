/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;

/// 吸着筒エントリのビルダ.
///
/// @param <B> ビルダの型.
/// @since 1.1
/// @author KITAMURA Yuichiro
abstract class ColumnEntryBuilder<B> {

  /// 開始条件.
  private ColumnStart start;

  /// 次サイクルにおけるキー.
  private ColumnKey nextKey;

  /// 吸着筒エントリのビルダのインスタンスを生成する.
  ColumnEntryBuilder() {}

  /// 開始条件を設定する.
  ///
  /// @param start 開始条件.
  /// @return 開始条件が設定されたビルダ.
  /// @throws NullPointerException `start` が `null` の場合.
  public final StartBuilder start(ColumnStart start) {
    return new StartBuilder(start);
  }

  /// 次サイクルにおけるキーを設定する.
  ///
  /// @param nextKey 次サイクルにおけるキー.
  /// @return 次サイクルにおけるキーが設定されたビルダ.
  /// @throws NullPointerException `nextKey` が `null` の場合.
  public final NextKeyBuilder nextKey(ColumnKey nextKey) {
    return new NextKeyBuilder(nextKey);
  }

  /// 指定された次サイクルにおけるキーと開始条件を設定する.
  ///
  /// @param start 開始条件.
  /// @param nextKey 次サイクルにおけるキー.
  /// @return ビルダのインスタンス.
  abstract B end(ColumnStart start, ColumnKey nextKey);

  /// 指定された次サイクルにおけるキーと開始条件を設定する.
  ///
  /// @return ビルダのインスタンス.
  private B end() {
    return this.end(this.start, this.nextKey);
  }

  /// 開始条件を設定するビルダ.
  public final class StartBuilder {

    /// 開始条件を設定するビルダのインスタンスを生成する.
    ///
    /// @param start 開始条件.
    /// @throws NullPointerException `start` が `null` の場合.
    private StartBuilder(ColumnStart start) {
      ColumnEntryBuilder.this.start = Preconditions.checkNotNull(start);
    }

    /// 次サイクルにおけるキーを設定する.
    ///
    /// @param nextKey 次サイクルにおけるキー.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `nextKey` が `null` の場合.
    public B nextKey(ColumnKey nextKey) {
      ColumnEntryBuilder.this.nextKey = Preconditions.checkNotNull(nextKey);
      return ColumnEntryBuilder.this.end();
    }

  } // StartBuilder

  /// 次サイクルにおけるキーを設定するビルダ.
  public final class NextKeyBuilder {

    /// 次サイクルにおけるキーを設定するビルダのインスタンスを生成する.
    ///
    /// @param nextKey 次サイクルにおけるキー.
    /// @throws NullPointerException `nextKey` が `null` の場合.
    private NextKeyBuilder(ColumnKey nextKey) {
      ColumnEntryBuilder.this.nextKey = Preconditions.checkNotNull(nextKey);
    }

    /// 開始条件を設定する.
    ///
    /// @param start 開始条件.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `start` が `null` の場合.
    public B start(ColumnStart start) {
      ColumnEntryBuilder.this.start = Preconditions.checkNotNull(start);
      return ColumnEntryBuilder.this.end();
    }

  } // NextKeyBuilder

}
