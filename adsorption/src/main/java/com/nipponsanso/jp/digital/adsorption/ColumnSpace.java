/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;

/// 吸着筒の空筒空間.
///
/// @param length 長さ (m).
/// @param monitorMap モニタの不変マップ.
/// @since 1.0
/// @author KITAMURA Yuichiro
public record ColumnSpace(double length, Map<String, Monitor> monitorMap) {

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

  /// 吸着筒の空筒空間のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private ColumnSpace(Builder builder) {
    this(builder.length, Map.copyOf(builder.monitorMap));
  }

  /// ビルダのインスタンスを生成して返す.
  ///
  /// @return ビルダのインスタンス.
  public static Builder builder() {
    return new Builder();
  }

}
