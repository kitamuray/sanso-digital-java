/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;

/// On-Off 弁と調節弁の連結のマップのビルダのスケルトン実装.
///
/// @param <B> 最上位のビルダの型.
/// @param <K> マップのキーの型.
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class OnOffAndRestrictValveMapBuilder<B, K>
    extends MapBuilder<B, K, OnOffAndRestrictValve> {

  /// On-Off 弁と調節弁の連結のマップのビルダのインスタンスを生成する.
  ///
  /// @param key キー.
  /// @throws NullPointerException `key` が `null` の場合.
  OnOffAndRestrictValveMapBuilder(K key) {
    super(key);
  }

  /// On-Off 弁が設定されたビルダのインスタンスを生成して返す.
  ///
  /// @param onOffValve On-Off 弁.
  /// @return On-Off 弁が設定されたビルダのインスタンス.
  /// @throws NullPointerException `onOffValve` が `null` の場合.
  public OnOffValveBuilder onOffValve(OnOffValve onOffValve) {
    return new OnOffValveBuilder(onOffValve);
  }

  /// 調節弁が設定されたビルダのインスタンスを生成して返す.
  ///
  /// @param restrictValve 調節弁.
  /// @return 調節弁が設定されたビルダのインスタンス.
  /// @throws NullPointerException `restrictValve` が `null` の場合.
  public RestrictValveBuilder restrictValve(RestrictValve restrictValve) {
    return new RestrictValveBuilder(restrictValve);
  }

  /// On-Off 弁が設定されたビルダ.
  public final class OnOffValveBuilder {

    /// On-Off 弁.
    private final OnOffValve onOffValve;

    /// On-Off 弁が設定されたビルダのインスタンスを生成する.
    ///
    /// @param onOffValve On-Off 弁.
    /// @throws NullPointerException `onOffValve` が `null` の場合.
    private OnOffValveBuilder(OnOffValve onOffValve) {
      this.onOffValve = Preconditions.checkNotNull(onOffValve);
    }

    /// 調節弁を設定する.
    ///
    /// @param restrictValve 調節弁.
    /// @return 最上位のビルダのインスタンス.
    public B restrictValve(RestrictValve restrictValve) {
      return OnOffAndRestrictValveMapBuilder.this.put(
          new OnOffAndRestrictValve(
              this.onOffValve,
              Preconditions.checkNotNull(restrictValve)));
    }

  } // OnOffValveBuilder

  /// 調節弁が設定されたビルダ.
  public final class RestrictValveBuilder {

    /// 調節弁.
    private final RestrictValve restrictValve;

    /// 調節弁が設定されたビルダのインスタンスを生成する.
    ///
    /// @param restrictValve 調節弁.
    /// @throws NullPointerException `restrictValve` が `null` の場合.
    private RestrictValveBuilder(RestrictValve restrictValve) {
      this.restrictValve = Preconditions.checkNotNull(restrictValve);
    }

    /// On-Off 弁を設定する.
    ///
    /// @param onOffValve On-Off 弁.
    /// @return 最上位のビルダのインスタンス.
    /// @throws NullPointerException `onOffValve` が `null` の場合.
    public B onOffValve(OnOffValve onOffValve) {
      return OnOffAndRestrictValveMapBuilder.this.put(
          new OnOffAndRestrictValve(
              Preconditions.checkNotNull(onOffValve),
              this.restrictValve));
    }

  } // RestrictValveBuilder

} // OnOffAndRestrictValveMapBuilder
