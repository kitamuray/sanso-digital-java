/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.Map;

/// 状態のマップのビルダ.
///
/// @param <B> ビルダの型.
/// @param <S> 状態の型.
public abstract class StateMapBuilder<B, S> {

  /// 吸着筒のキー.
  private final ColumnKey columnKey;

  /// 状態.
  private S state;

  /// 状態のマップのビルダのインスタンスを生成する.
  ///
  /// @param columnKey 吸着筒のキー.
  /// @throws NullPointerException `columnKey` が `null` の場合.
  StateMapBuilder(ColumnKey columnKey) {
    this.columnKey = Preconditions.checkNotNull(columnKey);
  }

  /// 状態の可変マップを返す.
  ///
  /// @return 状態の可変マップ.
  abstract Map<ColumnKey, S> stateMap();

  /// ビルダのインスタンスを返す.
  ///
  /// @return ビルダのインスタンス.
  abstract B self();

  /// 状態のマップを更新する.
  ///
  /// @return ビルダのインスタンス.
  private B end() {
    this.stateMap().put(this.columnKey, this.state);
    return this.self();
  }

  /// 状態を設定する.
  ///
  /// @param state 状態.
  /// @return ビルダのインスタンス.
  /// @throws NullPointerException `state` が `null` の場合.
  public final B state(S state) {
    this.state = Preconditions.checkNotNull(state);
    return this.end();
  }

}
