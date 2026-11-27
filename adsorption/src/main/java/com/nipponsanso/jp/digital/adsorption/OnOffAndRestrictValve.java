/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.MoreObjects;
import java.util.Objects;

/// On-Off 弁と調節弁の連結.
///
/// このクラスのインスタンスは不変である.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
final class OnOffAndRestrictValve extends Valve {

  /// On-Off 弁.
  private final OnOffValve onOffValve;

  /// 調節弁.
  private final RestrictValve restrictValve;

  /// CV 値.
  private final double cvValue;

  /// On-Off 弁と調節弁の連結のインスタンスを生成する.
  ///
  /// @param onOffValve On-Off 弁.
  /// @param restrictValve 調節弁.
  OnOffAndRestrictValve(OnOffValve onOffValve, RestrictValve restrictValve) {
    this.onOffValve = onOffValve;
    this.restrictValve = restrictValve;

    this.cvValue = 1. / (1. / this.onOffValve.cvValue() + 1. / this.restrictValve.cvValue());
  }

  /// On-Off 弁を返す.
  ///
  /// @return On-Off 弁.
  OnOffValve onOffValve() {
    return this.onOffValve;
  }

  /// 調節弁を返す.
  ///
  /// @return 調節弁.
  RestrictValve restrictValve() {
    return this.restrictValve;
  }

  @Override
  double cvValue() {
    return this.cvValue;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    OnOffAndRestrictValve that = (OnOffAndRestrictValve) o;

    return this.onOffValve.equals(that.onOffValve)
        && this.restrictValve.equals(that.restrictValve);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.onOffValve,
        this.restrictValve);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("onOffValve", this.onOffValve)
        .add("restrictValve", this.restrictValve)
        .toString();
  }

}
