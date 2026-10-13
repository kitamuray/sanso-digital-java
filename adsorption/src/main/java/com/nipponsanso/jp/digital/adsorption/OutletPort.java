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

/// 流出境界条件.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class OutletPort extends Port {

  private static final Logger LOGGER = LogManager.getLogger(OutletPort.class);

  /// 流出境界条件のビルダ.
  public static final class Builder {

    /// 流体の絶対圧力 (Pa).
    private double pressure;

    /// モニタの可変マップ.
    private final Map<String, Monitor> monitorMap = new HashMap<>();

    /// 流出境界条件のビルダのインスタンスを生成する.
    private Builder() {}

    /// 流体の絶対圧力を設定する.
    ///
    /// @param pressure 流体の絶対圧力 (Pa).
    /// @return ビルダのインスタンス.
    /// @throws IllegalArgumentException `pressure` が 0 以下の場合 (`pressure <= 0`).
    public Builder pressure(double pressure) {
      Preconditions.checkArgument(pressure > 0.);
      this.pressure = pressure;
      return this;
    }

    /// モニタの出力先ファイル名を設定する.
    ///
    /// @param fileName モニタの出力先ファイル名.
    /// @return モニタを設定するビルダのインスタンス.
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

    /// 流出境界条件のインスタンスを生成して返す.
    ///
    /// @return 流出境界条件のインスタンス.
    /// @throws IllegalStateException ビルダが不完全な場合.
    public OutletPort build() {
      Preconditions.checkState(this.pressure > 0.);
      return new OutletPort(this);
    }

  } // Builder

  /// 流体の絶対圧力 (Pa).
  private final double pressure;

  /// モニタの不変マップ.
  private final Map<String, Monitor> monitorMap;

  /// 流出条件のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private OutletPort(Builder builder) {
    this.pressure = builder.pressure;
    this.monitorMap = ImmutableMap.copyOf(builder.monitorMap);

    LOGGER.debug("pressure = {} (Pa)", this.pressure);
    LOGGER.debug("monitorMap = {}", this.monitorMap);
  }

  /// ビルダのインスタンスを生成して返す.
  ///
  /// @return ビルダのインスタンス.
  public static Builder builder() {
    return new Builder();
  }

  /// 流体の絶対圧力を返す.
  ///
  /// @return 流体の絶対圧力 (Pa).
  public double pressure() {
    return this.pressure;
  }

  /// モニタの不変マップを返す.
  ///
  /// @return モニタの不変マップ.
  public Map<String, Monitor> monitorMap() {
    return this.monitorMap;
  }

  void set(PortCell cell) {
    cell.setPressure(this.pressure);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    OutletPort that = (OutletPort) o;

    return Double.compare(this.pressure, that.pressure) == 0
        && this.monitorMap.equals(that.monitorMap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.pressure,
        this.monitorMap);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("pressure", this.pressure)
        .add("monitorMap", this.monitorMap)
        .toString();
  }

}
