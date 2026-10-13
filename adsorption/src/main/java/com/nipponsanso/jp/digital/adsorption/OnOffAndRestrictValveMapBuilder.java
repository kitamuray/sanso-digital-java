/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.Map;

/// On-Off 弁と調節弁の組み合わせのマップのビルダ.
///
/// @param <B> ビルダの型.
/// @since 1.1
/// @author KITAMURA Yuichiro
public abstract class OnOffAndRestrictValveMapBuilder<B> {

  /// 系統のキー.
  private final LineKey lineKey;

  /// On-Off 弁.
  private OnOffValve onOffValve;

  /// 調節弁.
  private RestrictValve restrictValve;

  /// On-Off 弁と調節弁の組み合わせのマップのビルダのインスタンスを生成する.
  ///
  /// @param lineKey 系統のキー.
  /// @throws NullPointerException `lineKey` が `null` の場合.
  OnOffAndRestrictValveMapBuilder(LineKey lineKey) {
    this.lineKey = Preconditions.checkNotNull(lineKey);
  }

  /// On-Off 弁と調節弁の組み合わせの可変マップを返す.
  ///
  /// @return On-Off 弁と調節弁の組み合わせの可変マップ.
  abstract Map<LineKey, OnOffAndRestrictValve> onOffAndRestrictValveMap();

  /// ビルダのインスタンスを返す.
  ///
  /// @return ビルダのインスタンス.
  abstract B self();

  /// マップを更新して戻る.
  ///
  /// @return ビルダのインスタンス.
  private B end() {
    this.onOffAndRestrictValveMap()
        .put(this.lineKey, new OnOffAndRestrictValve(this.onOffValve, this.restrictValve));
    return this.self();
  }

  /// On-Off 弁を設定する.
  ///
  /// @param onOffValve On-Off 弁.
  /// @return On-Off 弁が設定済のビルダのインスタンス.
  /// @throws NullPointerException `onOffValve` が `null` の場合.
  public OnOffValveBuilder onOffValve(OnOffValve onOffValve) {
    this.onOffValve = Preconditions.checkNotNull(onOffValve);
    return new OnOffValveBuilder();
  }

  /// 調節弁を設定する.
  ///
  /// @param restrictValve 調節弁.
  /// @return 調節弁が設定済のビルダのインスタンス.
  /// @throws NullPointerException `restrictValve` が `null` の場合.
  public RestrictValveBuilder restrictValve(RestrictValve restrictValve) {
    this.restrictValve = Preconditions.checkNotNull(restrictValve);
    return new RestrictValveBuilder();
  }

  /// On-Off 弁が設定済のビルダ.
  public final class OnOffValveBuilder {

    /// On-Off 弁が設定済のビルダのインスタンスを生成する.
    private OnOffValveBuilder() {}

    /// 調節弁を設定する.
    ///
    /// @param restrictValve 調節弁.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `restrictValve` が `null` の場合.
    public B restrictValve(RestrictValve restrictValve) {
      OnOffAndRestrictValveMapBuilder.this.restrictValve
          = Preconditions.checkNotNull(restrictValve);
      return OnOffAndRestrictValveMapBuilder.this.end();
    }

  } // OnOffValveBuilder

  /// 調節弁が設定済のビルダ.
  public final class RestrictValveBuilder {

    /// 調節弁が設定済のビルダのインスタンスを生成する.
    private RestrictValveBuilder() {}

    /// On-Off 弁を設定する.
    ///
    /// @param onOffValve On-Off 弁.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `onOffValve` が `null` の場合.
    public B onOffValve(OnOffValve onOffValve) {
      OnOffAndRestrictValveMapBuilder.this.onOffValve
          = Preconditions.checkNotNull(onOffValve);
      return OnOffAndRestrictValveMapBuilder.this.end();
    }

  } // RestrictValveBuilder

}
