/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.Map;

/// 吸着筒エントリのマップのビルダ.
///
/// @param <B> 最上位ビルダの型.
/// @since 1.1
/// @author KITAMURA Yuichiro
abstract class ColumnEntryMapBuilder<B> extends MapBuilder<B, ColumnKey, ColumnEntry> {

  /// 吸着筒エントリのマップのビルダのインスタンスを生成する.
  ///
  /// @param key 吸着筒のキー.
  /// @throws NullPointerException `key` が `null` の場合.
  ColumnEntryMapBuilder(ColumnKey key) {
    super(key);
  }

  /// 開始条件が設定されたビルダのインスタンスを生成して返す.
  ///
  /// @param start 開始条件.
  /// @return 開始条件が設定されたビルダのインスタンス.
  /// @throws NullPointerException `start` が `null` の場合.
  public StartBuilder start(ColumnStart start) {
    return new StartBuilder(start);
  }

  /// 次サイクルのキーが設定されたビルダのインスタンスを生成して返す.
  ///
  /// @param nextKey 次サイクルのキー.
  /// @return 次サイクルのキーが設定されたビルダのインスタンス.
  /// @throws NullPointerException `nextKey` が `null` の場合.
  public NextKeyBuilder nextKey(ColumnKey nextKey) {
    return new NextKeyBuilder(nextKey);
  }

  /// {@inheritDoc}
  ///
  /// @return {@inheritDoc}
  @Override
  abstract B self();

  /// {@inheritDoc}
  ///
  /// @return {@inheritDoc}
  @Override
  abstract Map<ColumnKey, ColumnEntry> map();

  /// 開始条件が設定されたビルダ.
  public final class StartBuilder {

    /// 開始条件.
    private final ColumnStart start;

    /// 開始条件が設定されたビルダのインスタンスを生成する.
    ///
    /// @param start 開始条件.
    /// @throws NullPointerException `start` が `null` の場合.
    private StartBuilder(ColumnStart start) {
      this.start = Preconditions.checkNotNull(start);
    }

    /// 次サイクルにおけるキーを設定する.
    ///
    /// @param nextKey 次サイクルにおけるキー.
    /// @return 最上位のビルダのインスタンス.
    /// @throws NullPointerException `nextKey` が `null` の場合.
    public B nextKey(ColumnKey nextKey) {
      ColumnEntryMapBuilder.this.put(
          new ColumnEntry(
              this.start,
              Preconditions.checkNotNull(nextKey)));
      return ColumnEntryMapBuilder.this.self();
    }

  } // StartBuilder

  /// 次サイクルにおけるキーが設定されたビルダ.
  public final class NextKeyBuilder {

    /// 次サイクルにおけるキー.
    private final ColumnKey nextKey;

    /// 次サイクルにおけるキーが設定されたビルダ.
    ///
    /// @param nextKey 次サイクルにおけるキー.
    /// @throws NullPointerException `nextKey` が `null` の場合.
    private NextKeyBuilder(ColumnKey nextKey) {
      this.nextKey = Preconditions.checkNotNull(nextKey);
    }

    /// 開始条件を設定する.
    ///
    /// @param start 開始条件.
    /// @return 最上位のビルダのインスタンス.
    /// @throws NullPointerException `start` が `null` の場合.
    public B start(ColumnStart start) {
      ColumnEntryMapBuilder.this.put(
          new ColumnEntry(
              Preconditions.checkNotNull(start),
              this.nextKey));
      return ColumnEntryMapBuilder.this.self();
    }

  } // NextKeyBuilder

}
