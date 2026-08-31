/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.Map;

/// マップのビルダのスケルトン実装.
///
/// @param <B> 最上位のビルダの型.
/// @param <K> マップのキーの型.
/// @param <V> マップの値の型.
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class MapBuilder<B, K, V> {

  /// キー.
  private final K key;

  /// マップのビルダのインスタンスを生成する.
  ///
  /// @param key キー.
  /// @throws NullPointerException `key` が `null` の場合.
  MapBuilder(K key) {
    this.key = Preconditions.checkNotNull(key);
  }

  /// マップのキーを返す.
  ///
  /// @return マップのキー.
  final K key() {
    return this.key;
  }

  /// 指定されたマップの値をマップへ追加する.
  ///
  /// @param value マップの値.
  /// @return ビルダのインスタンス.
  @SuppressWarnings("UnusedReturnValue")
  final B put(V value) {
    this.map().put(this.key, value);
    return this.self();
  }

  /// 最上位のビルダのインスタンスを返す.
  ///
  /// @return 最上位のビルダのインスタンス.
  abstract B self();

  /// マップを返す.
  ///
  /// @return マップ.
  abstract Map<K, V> map();

}
