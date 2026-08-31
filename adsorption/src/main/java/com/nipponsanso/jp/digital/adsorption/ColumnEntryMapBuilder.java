/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.Map;

/// 吸着筒エントリのマップのビルダ.
///
/// @param <B> ビルダの型.
/// @since 1.1
/// @author KITAMURA Yuichiro
abstract class ColumnEntryMapBuilder<B> extends ColumnEntryBuilder<B> {

  /// 吸着筒のキー.
  private final ColumnKey columnKey;

  /// 吸着筒エントリのマップのビルダのインスタンスを生成する.
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

  /// {@inheritDoc}
  ///
  /// @param start {@inheritDoc}
  /// @param nextKey {@inheritDoc}
  /// @return ビルダのインスタンス.
  @Override
  final B end(ColumnStart start, ColumnKey nextKey) {
    this.columnEntryMap().put(this.columnKey, new ColumnEntry(start, nextKey));
    return this.self();
  }

}
