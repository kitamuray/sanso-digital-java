/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/// On-Off 弁.
///
/// このクラスのインスタンスは不変（immutable）である.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class OnOffValve extends Valve {

  private static final Logger LOGGER = LogManager.getLogger(OnOffValve.class);

  /// CV 値.
  private final double cvValue;

  /// On-Off 弁のインスタンスを生成する.
  ///
  /// @param cvValue CV 値.
  /// @throws IllegalArgumentException `cvValue` が 0 以下の場合 (`cvValue <= 0`).
  private OnOffValve(double cvValue) {
    Preconditions.checkArgument(cvValue > 0.);

    this.cvValue = cvValue;

    LOGGER.debug("cvValue = {}", this.cvValue);
  }

  /// On-Off 弁のインスタンスを生成して返す.
  ///
  /// @param cvValue CV 値.
  /// @return On-Off 弁のインスタンス.
  /// @throws IllegalArgumentException `cvValue` が 0 以下の場合 (`cvValue <= 0`).
  public static OnOffValve cvValueOf(double cvValue) {
    return new OnOffValve(cvValue);
  }

  /// {@inheritDoc}
  ///
  /// @return {@inheritDoc}
  @Override
  public double cvValue() {
    return this.cvValue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    OnOffValve that = (OnOffValve) o;

    return Double.compare(this.cvValue, that.cvValue) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.cvValue);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("cvValue", this.cvValue)
        .toString();
  }

}
