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

/// 吸着筒の空筒空間.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class ColumnSpace {

  /// 吸着筒の空筒空間のビルダ.
  public static final class Builder {

    /// 長さ (m).
    private double length;

    /// モニタのマップ.
    private final Map<String, Monitor> monitorMap = new HashMap<>();

    /// 吸着筒の空筒空間のビルダのインスタンスを生成する.
    private Builder() {}

    /// 長さを設定する.
    ///
    /// @param length 長さ (m).
    /// @return ビルダのインスタンス自身.
    /// @throws IllegalArgumentException`length` が 0 以下の場合 (`length <= 0`).
    public Builder length(double length) {
      Preconditions.checkArgument(length > 0.);

      this.length = length;

      return this;
    }

    /// ファイル名を設定する.
    ///
    /// @param fileName ファイル名.
    /// @return モニタを設定するビルダのインスタンス.
    /// @throws NullPointerException `fileName` が `null` の場合.
    public MonitorBuilder fileName(String fileName) {
      return new MonitorBuilder(fileName);
    }

    /// 吸着筒の空筒空間のインスタンスを生成して返す.
    ///
    /// @return 吸着筒の空筒空間のインスタンス.
    /// @throws IllegalStateException ビルダの状態が不完全な場合.
    public ColumnSpace build() {
      Preconditions.checkState(this.length > 0.);

      return new ColumnSpace(this);
    }

    /// モニタを設定するビルダ.
    public final class MonitorBuilder {

      /// ファイル名.
      private final String fileName;

      /// モニタを設定するビルダのインスタンスを生成する.
      ///
      /// @param fileName ファイル名.
      /// @throws NullPointerException`fileName` が `null` の場合.
      private MonitorBuilder(String fileName) {
        this.fileName = Preconditions.checkNotNull(fileName);
      }

      /// モニタを設定する.
      ///
      /// @param monitor モニタ.
      /// @return ビルダのインスタンス.
      /// @throws NullPointerException`monitor` が `null` の場合.
      /// @throws IllegalArgumentException `monitor` が適切なモニタではない場合.
      public Builder monitor(Monitor monitor) {
        Preconditions.checkNotNull(monitor);
        Preconditions.checkArgument(
            monitor instanceof Monitor.FluidCellMonitor
                || monitor instanceof Monitor.ShellCellMonitor);

        Builder.this.monitorMap.put(this.fileName, monitor);

        return Builder.this;
      }

    } // MonitorBuilder

  } // Builder

  /// 長さ (m).
  private final double length;

  /// モニタの不変マップ.
  private final Map<String, Monitor> monitorMap;

  /// 吸着筒の空筒空間のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private ColumnSpace(Builder builder) {
    this.length = builder.length;
    this.monitorMap = ImmutableMap.copyOf(builder.monitorMap);
  }

  /// ビルダのインスタンスを生成して返す.
  ///
  /// @return ビルダのインスタンス.
  public static Builder builder() {
    return new Builder();
  }

  /// 長さを返す.
  ///
  /// @return 長さ (m).
  public double length() {
    return this.length;
  }

  /// モニタの不変マップを返す.
  ///
  /// @return モニタの不変マップ.
  public Map<String, Monitor> monitorMap() {
    return this.monitorMap;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    ColumnSpace that = (ColumnSpace) o;

    return Double.compare(this.length, that.length) == 0
        && this.monitorMap.equals(that.monitorMap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.length,
        this.monitorMap);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("length", this.length)
        .add("monitorMap", this.monitorMap)
        .toString();
  }

}
