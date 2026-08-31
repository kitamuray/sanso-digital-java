/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.Map;

/// On-Off 弁と調節弁の連結のマップのビルダ.
///
/// @param <B> ビルダの型.
/// @since 1.1
/// @author KITAMURA Yuichiro
abstract class OnOffAndRestrictValveMapBuilder<B> extends OnOffAndRestrictValveBuilder<B> {

  /// 系統のキー.
  private final LineKey lineKey;

  /// On-Off 弁と調節弁の連結のマップのビルダのインスタンスを生成する.
  ///
  /// @param lineKey 系統のキー.
  /// @throws NullPointerException `lineKey` が `null` の場合.
  OnOffAndRestrictValveMapBuilder(LineKey lineKey) {
    this.lineKey = Preconditions.checkNotNull(lineKey);
  }

  /// On-Off 弁と調節弁の連結の可変マップを返す.
  ///
  /// @return On-Off 弁と調節弁の連結の可変マップ.
  abstract Map<LineKey, OnOffAndRestrictValve> onOffAndRestrictValveMap();

  /// ビルダのインスタンスを返す.
  ///
  /// @return ビルダのインスタンス.
  abstract B self();

  /// {@inheritDoc}
  ///
  /// @param onOffValve {@inheritDoc}
  /// @param restrictValve 調節弁.
  /// @return ビルダのインスタンス.
  @Override
  final B end(OnOffValve onOffValve, RestrictValve restrictValve) {
    this.onOffAndRestrictValveMap().put(
        this.lineKey,
        new OnOffAndRestrictValve(onOffValve, restrictValve));
    return this.self();
  }

}
