/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/// 流出入境界条件.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class Port {

  private static final Logger LOGGER = LogManager.getLogger(Port.class);

  /// 流出入境界条件のビルダ.
  ///
  /// @param <B> ビルダの型.
  abstract static class Builder<B extends Builder<B>> {

    /// 流体の絶対圧力 (Pa).
    private double pressure = 101325.;

    /// モニタの可変マップ.
    public final Map<String, Monitor> monitorMap = new HashMap<>();

    /// 流出入境界条件のビルダのインスタンスを生成する.
    Builder() {}

    /// ビルダのインスタンスを返す.
    ///
    /// @return ビルダのインスタンス.
    abstract B self();

    /// 流体の絶対圧力を設定する.
    ///
    /// @param pressure 流体の絶対圧力 (Pa).
    /// @return ビルダのインスタンス.
    /// @throws IllegalArgumentException `pressure` が 0 以下の場合 (`pressure <= 0`).
    public B pressure(double pressure) {
      Preconditions.checkArgument(pressure > 0.);
      this.pressure = pressure;
      return this.self();
    }

    /// モニタの出力先ファイル名を設定する.
    ///
    /// @param fileName モニタの出力先ファイル名.
    /// @return モニタのマップのビルダのインスタンス.
    /// @throws NullPointerException `fileName` が `null` の場合.
    public MonitorMapBuilder<B> fileName(String fileName) {
      return new MonitorMapBuilder<>(fileName) {
        @Override
        Map<String, Monitor> monitorMap() {
          return Builder.this.monitorMap;
        }

        @Override
        B self() {
          return Builder.this.self();
        }
      };
    }

    /// 流出入境界条件のインスタンスを生成して返す.
    ///
    /// @return 流出入境界条件のインスタンス.
    public abstract Port build();

  } // Builder

  /// 流体の絶対圧力 (Pa).
  private final double pressure;

  /// モニタの不変マップ.
  private final Map<String, Monitor> monitorMap;

  /// 流出入境界条件のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  Port(Builder<?> builder) {
    this.pressure = builder.pressure;
    this.monitorMap = ImmutableMap.copyOf(builder.monitorMap);

    LOGGER.debug("pressure = {} (Pa)", this.pressure);
    LOGGER.debug("monitorMap = {}", this.monitorMap);
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

  abstract void set(FluidCell2 cell);

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    Port that = (Port) o;

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

}
