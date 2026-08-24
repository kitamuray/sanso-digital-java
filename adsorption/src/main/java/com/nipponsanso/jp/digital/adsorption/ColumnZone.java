/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;

/// 吸着筒の充填層領域.
///
/// @param section 区間.
/// @param adsorbent 吸着剤.
/// @param indexMap 位置番号の不変マップ.
/// @param monitorMap モニタの不変マップ.
public record ColumnZone(
    Section section,
    Adsorbent adsorbent,
    Map<String, Integer> indexMap,
    Map<String, Monitor> monitorMap) {

  /// 吸着筒の領域のビルダ.
  public static final class Builder {

    /// 区間.
    private Section section;

    /// 吸着剤.
    private Adsorbent adsorbent;

    /// 位置番号のマップ.
    ///
    /// keySet は [#monitorMap] に等しい.
    private final Map<String, Integer> indexMap = new HashMap<>();

    /// モニタのマップ.
    ///
    /// keySet は [#indexMap] に等しい.
    private final Map<String, Monitor> monitorMap = new HashMap<>();

    /// 吸着筒の領域のビルダのインスタンスを生成する.
    private Builder() {}

    /// 区間を指定する.
    ///
    /// @param section 区間.
    /// @throws NullPointerException `section` が `null` の場合.
    /// @throws IllegalStateException `section` の分割数が小さい場合.
    public Builder section(Section section) {
      Preconditions.checkNotNull(section);
      if (!this.indexMap.isEmpty()) {
        Preconditions.checkArgument(
            section.number() > this.indexMap.values().stream()
                .mapToInt(Integer::intValue)
                .max().getAsInt());
      }

      this.section = section;
      return this;
    }

    /// 吸着剤を設定する.
    ///
    /// @param adsorbent 吸着剤.
    /// @throws NullPointerException `adsorbent` が `null` の場合.
    public Builder adsorbent(Adsorbent adsorbent) {
      this.adsorbent = Preconditions.checkNotNull(adsorbent);
      return this;
    }

    /// 出力ファイル名を設定する.
    ///
    /// @param fileName 出力ファイル名.
    /// @return [IndexMonitor] のマップのビルダのインスタンス.
    /// @throws NullPointerException `fileName` が `null` の場合.
    public IndexMonitorBuilder fileName(String fileName) {
      return new IndexMonitorBuilder(fileName);
    }

    /// 吸着筒の領域のインスタンスを生成して返す.
    ///
    /// @return 吸着筒の領域のインスタンス.
    /// @throws IllegalStateException ビルダが不完全な場合.
    public ColumnZone build() {
      Preconditions.checkState(this.section != null);
      Preconditions.checkState(this.adsorbent != null);

      return new ColumnZone(this);
    }

    /// [IndexMonitor] のマップのビルダ.
    public final class IndexMonitorBuilder {

      /// 出力ファイル名.
      private final String fileName;

      /// [IndexMonitor] のマップのビルダのインスタンスを生成する.
      ///
      /// @param fileName 出力ファイル名.
      /// @throws NullPointerException `fileName` が `null` の場合.
      private IndexMonitorBuilder(String fileName) {
        this.fileName = Preconditions.checkNotNull(fileName);
      }

      /// 位置番号を設定する.
      ///
      /// @param index 位置番号.
      /// @return モニタを設定するビルダのインスタンス.
      /// @throws IndexOutOfBoundsException `index` が範囲外の場合.
      public MonitorBuilder index(int index) {
        return new MonitorBuilder(index);
      }

      /// モニタを設定する.
      ///
      /// @param monitor モニタ.
      /// @return 位置番号を設定するビルダのインスタンス.
      /// @throws NullPointerException `monitor` が `null` の場合.
      public IndexBuilder monitor(Monitor monitor) {
        return new IndexBuilder(monitor);
      }

      /// モニタを設定するビルダ.
      public final class MonitorBuilder {

        /// 位置番号.
        private final int index;

        /// モニタを設定するビルダのインスタンスを生成する.
        ///
        /// @param index 位置番号.
        /// @throws IndexOutOfBoundsException `index` が範囲外の場合.
        private MonitorBuilder(int index) {
          if (Builder.this.section == null) {
            Preconditions.checkElementIndex(index, Integer.MAX_VALUE);
          } else {
            Preconditions.checkElementIndex(index, Builder.this.section.number());
          }

          this.index = index;
        }

        /// モニタを設定する.
        ///
        /// @param monitor モニタ.
        /// @return ビルダのインスタンス.
        /// @throws NullPointerException `monitor` が `null` の場合.
        public Builder monitor(Monitor monitor) {
          Preconditions.checkNotNull(monitor);

          Builder.this.indexMap.put(IndexMonitorBuilder.this.fileName, this.index);
          Builder.this.monitorMap.put(IndexMonitorBuilder.this.fileName, monitor);

          return Builder.this;
        }

      } // MonitorBuilder

      /// 位置番号を設定するビルダ.
      public final class IndexBuilder {

        /// モニタ.
        private final Monitor monitor;

        /// 位置番号を設定するビルダのインスタンスを生成する.
        ///
        /// @param monitor モニタ.
        /// @throws NullPointerException `monitor` が `null` の場合.
        private IndexBuilder(Monitor monitor) {
          this.monitor = Preconditions.checkNotNull(monitor);
        }

        /// 位置番号を設定する.
        ///
        /// @param index 位置番号.
        /// @throws IndexOutOfBoundsException `index` が範囲外の場合.
        public Builder index(int index) {
          if (Builder.this.section == null) {
            Preconditions.checkElementIndex(index, Integer.MAX_VALUE);
          } else {
            Preconditions.checkElementIndex(index, Builder.this.section.number());
          }

          Builder.this.indexMap.put(IndexMonitorBuilder.this.fileName, index);
          Builder.this.monitorMap.put(IndexMonitorBuilder.this.fileName, this.monitor);

          return Builder.this;
        }

      } // IndexBuilder

    } // IndexMonitorBuilder

  } // Builder

  /// 吸着筒の領域のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private ColumnZone(Builder builder) {
    this(builder.section, builder.adsorbent, builder.indexMap, builder.monitorMap);
  }

  /// ビルダのインスタンスを生成して返す.
  ///
  /// @return ビルダのインスタンス.
  public static Builder builder() {
    return new Builder();
  }

}
