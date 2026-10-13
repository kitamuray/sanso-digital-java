/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.Map;

/// 吸着筒のエントリのマップのビルダ.
///
/// @param <B> ビルダの型.
/// @since 1.1
/// @author KITAMURA Yuichiro
public abstract class ColumnEntryMapBuilder<B> {

  /// 吸着筒のキー.
  private final ColumnKey columnKey;

  /// 開始条件.
  private ColumnStart start;

  /// 次サイクルにおけるキー.
  private ColumnKey nextKey;

  /// 吸着筒のエントリのマップのビルダのインスタンスを生成する.
  ///
  /// @param columnKey 吸着筒のキー.
  /// @throws NullPointerException `columnKey` が `null` の場合.
  ColumnEntryMapBuilder(ColumnKey columnKey) {
    this.columnKey = Preconditions.checkNotNull(columnKey);
  }

  /// 吸着筒のエントリの可変マップを返す.
  ///
  /// @return 吸着筒のエントリの可変マップ.
  abstract Map<ColumnKey, ColumnEntry> columnEntryMap();

  /// ビルダのインスタンスを返す.
  ///
  /// @return ビルダのインスタンス.
  abstract B self();

  /// マップを更新して戻る.
  ///
  /// @return ビルダのインスタンス.
  private B end() {
    this.columnEntryMap().put(this.columnKey, new ColumnEntry(this.start, this.nextKey));
    return this.self();
  }

  /// 開始条件を設定する.
  ///
  /// @param start 開始条件.
  /// @return 開始条件が設定済のビルダのインスタンス.
  /// @throws NullPointerException `start` が `null` の場合.
  public StartBuilder start(ColumnStart start) {
    this.start = Preconditions.checkNotNull(start);
    return new StartBuilder();
  }

  /// 次サイクルにおけるキーを設定する.
  ///
  /// @param nextKey 次サイクルにおけるキー.
  /// @return 次サイクルにおけるキーが設定済のビルダのインスタンス.
  /// @throws NullPointerException `nextKey` が `null` の場合.
  public NextKeyBuilder nextKey(ColumnKey nextKey) {
    this.nextKey = Preconditions.checkNotNull(nextKey);
    return new NextKeyBuilder();
  }

  /// 開始条件が設定済のビルダ.
  public final class StartBuilder {

    /// 開始条件が設定済のビルダのインスタンスを生成する.
    private StartBuilder() {}

    /// 次サイクルにおけるキーを設定する.
    ///
    /// @param nextKey 次サイクルにおけるキー.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `nextKey` が `null` の場合.
    public B nextKey(ColumnKey nextKey) {
      ColumnEntryMapBuilder.this.nextKey = Preconditions.checkNotNull(nextKey);
      return ColumnEntryMapBuilder.this.end();
    }

  } // StartBuilder

  /// 次サイクルにおけるキーが設定済のビルダ.
  public final class NextKeyBuilder {

    /// 次サイクルにおけるキーが設定済のビルダのインスタンスを生成する.
    private NextKeyBuilder() {}

    /// 開始条件を設定する.
    ///
    /// @param start 開始条件.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `start` が `null` の場合.
    public B start(ColumnStart start) {
      ColumnEntryMapBuilder.this.start = Preconditions.checkNotNull(start);
      return ColumnEntryMapBuilder.this.end();
    }

  } // NextKeyBuilder

}
