/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/// 貯槽.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class Tank {

  private static final Logger LOGGER = LogManager.getLogger(Tank.class);

  /// 貯槽のビルダ.
  public static final class Builder {

    /// 容積 (m<sup>3</sup>).
    private double volume;

    /// モニタのマップ.
    private final Map<String, Monitor> monitorMap = new HashMap<>();

    /// 貯槽のビルダのインスタンスを生成する.
    private Builder() {}

    /// 容積を設定する.
    ///
    /// @param volume 容積 (m<sup>3</sup>).
    /// @return ビルダのインスタンス.
    /// @throws IllegalArgumentException `volume` が 0 以下の場合 (`volume <= 0`).
    public Builder volume(double volume) {
      Preconditions.checkArgument(volume > 0.);
      this.volume = volume;
      return this;
    }

    /// 出力ファイル名を設定する.
    ///
    /// @param fileName 出力ファイル名.
    /// @return モニタのマップのビルダのインスタンス.
    /// @throws NullPointerException `fileName` が `null` の場合.
    public MonitorMapBuilder<Builder> fileName(String fileName) {
      return new MonitorMapBuilder<>(fileName) {
        @Override
        Map<String, Monitor> monitorMap() {
          return Builder.this.monitorMap;
        }

        @Override
        Builder self() {
          return Builder.this;
        }
      };
    }

    /// 貯槽のインスタンスを生成して返す.
    ///
    /// @return 貯槽のインスタンス.
    /// @throws IllegalStateException ビルダが不完全な場合.
    public Tank build() {
      Preconditions.checkState(this.volume > 0.);
      return new Tank(this);
    }

  } // Builder

  /// 容積 (m<sup>3</sup>).
  private final double volume;

  /// モニタの不変マップ.
  private final Map<String, Monitor> monitorMap;

  /// 貯槽のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private Tank(Builder builder) {
    this.volume = builder.volume;
    this.monitorMap = ImmutableMap.copyOf(builder.monitorMap);

    LOGGER.debug("volume = {}", this.volume);
    LOGGER.debug("monitorMap = {}", this.monitorMap);
  }

  /// ビルダのインスタンスを生成して返す.
  ///
  /// @return ビルダのインスタンス.
  public static Builder builder() {
    return new Builder();
  }

  /// 容積を返す.
  ///
  /// @return 容積 (m<sup>3</sup>).
  public double volume() {
    return this.volume;
  }

  /// モニタの不変マップを返す.
  ///
  /// @return モニタの不変マップ.
  public Map<String, Monitor> monitorMap() {
    return this.monitorMap;
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

    return Double.compare(this.volume, that.volume) == 0
        && this.monitorMap.equals(that.monitorMap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.volume,
        this.monitorMap);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("volume", this.volume)
        .add("monitorMap", this.monitorMap)
        .toString();
  }

}
