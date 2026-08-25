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

/// 貯槽.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class Tank {

  private static final Logger LOGGER = LogManager.getLogger(Tank.class);

  /// 容積 (m<sup>3</sup>).
  private final double volume;

  /// 貯槽のインスタンスを生成する.
  ///
  /// @param volume 容積 (m<sup>3</sup>).
  /// @throws IllegalArgumentException `volume` が 0 以下の場合 (`volume <= 0`).
  private Tank(double volume) {
    Preconditions.checkArgument(volume > 0.);

    this.volume = volume;

    LOGGER.debug("volume = {}", this.volume);
  }

  /// 貯槽のインスタンスを生成して返す.
  ///
  /// @param volume 容積 (m<sup>3</sup>).
  /// @return 貯槽のインスタンス.
  /// @throws IllegalArgumentException `volume` が 0 以下の場合 (`volume <= 0`).
  public static Tank volumeOf(double volume) {
    return new Tank(volume);
  }

  /// 容積を返す.
  ///
  /// @return 容積 (m<sup>3</sup>).
  public double volume() {
    return this.volume;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    Tank that = (Tank) o;

    return Double.compare(this.volume, that.volume) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.volume);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("volume", this.volume)
        .toString();
  }

}
