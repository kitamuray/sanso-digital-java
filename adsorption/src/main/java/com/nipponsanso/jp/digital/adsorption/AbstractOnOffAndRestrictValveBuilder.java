/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;

/// On-Off 弁と調節弁の連結のビルダのスケルトン実装.
///
/// @param <B> ビルダの型.
/// @since 1.1
/// @author KITAMURA Yuichiro
abstract class AbstractOnOffAndRestrictValveBuilder<B> {

  /// On-Off 弁.
  private OnOffValve onOffValve;

  /// 調節弁.
  private RestrictValve restrictValve;

  /// On-Off 弁と調節弁の連結のビルダのインスタンスを生成する.
  AbstractOnOffAndRestrictValveBuilder() {}

  /// 指定された On-Off 弁と調節弁を反映する.
  ///
  /// @param onOffValve On-Off 弁.
  /// @param restrictValve 調節弁.
  /// @return ビルダのインスタンス.
  abstract B end(OnOffValve onOffValve, RestrictValve restrictValve);

  /// 指定された On-Off 弁と調節弁を反映する.
  ///
  /// @return 指定された On-Off 弁と調節弁を反映する.
  private B end() {
    return this.end(this.onOffValve, this.restrictValve);
  }

  /// On-Off 弁を設定する.
  ///
  /// @param onOffValve On-Off 弁.
  /// @return On-Off 弁を先に設定するビルダ.
  /// @throws NullPointerException `onOffValve` が `null` の場合.
  public final OnOffValveBuilder onOffValve(OnOffValve onOffValve) {
    return new OnOffValveBuilder(onOffValve);
  }

  /// 調節弁を設定する.
  ///
  /// @param restrictValve 調節弁.
  /// @return 調節弁を先に設定するビルダ.
  /// @throws NullPointerException `restrictValve` が `null` の場合.
  public final RestrictValveBuilder restrictValve(RestrictValve restrictValve) {
    return new RestrictValveBuilder(restrictValve);
  }

  /// On-Off 弁を先に設定するビルダ.
  public final class OnOffValveBuilder {

    /// On-Off 弁を先に設定するビルダのインスタンスを生成する.
    ///
    /// @param onOffValve On-Off 弁.
    /// @throws NullPointerException `onOffValve` が `null` の場合.
    private OnOffValveBuilder(OnOffValve onOffValve) {
      AbstractOnOffAndRestrictValveBuilder.this.onOffValve = Preconditions.checkNotNull(onOffValve);
    }

    /// 調節弁を設定する.
    ///
    /// @param restrictValve 調節弁.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `restrictValve` が `null` の場合.
    public B restrictValve(RestrictValve restrictValve) {
      AbstractOnOffAndRestrictValveBuilder.this.restrictValve = Preconditions.checkNotNull(restrictValve);
      return AbstractOnOffAndRestrictValveBuilder.this.end();
    }

  } // OnOfValveBuilder

  /// 調節弁を先に設定するビルダ.
  public final class RestrictValveBuilder {

    /// 調節弁を先に設定するビルダのインスタンスを生成する.
    ///
    /// @param restrictValve 調節弁.
    /// @throws NullPointerException `restrictValve` が `null` の場合.
    private RestrictValveBuilder(RestrictValve restrictValve) {
      AbstractOnOffAndRestrictValveBuilder.this.restrictValve = Preconditions.checkNotNull(restrictValve);
    }

    /// On-Off 弁を設定する.
    /// @param onOffValve On-Off 弁.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `onOffValve` が `null` の場合.
    public B onOffValve(OnOffValve onOffValve) {
      AbstractOnOffAndRestrictValveBuilder.this.onOffValve = Preconditions.checkNotNull(onOffValve);
      return AbstractOnOffAndRestrictValveBuilder.this.end();
    }

  } // RestrictValveBuilder

}
