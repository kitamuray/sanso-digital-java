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

/// 吸着筒の領域.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class ColumnZone {

  private static final Logger LOGGER = LogManager.getLogger(ColumnZone.class);

  /// 吸着筒の領域のビルダ.
  public static final class Builder {

    /// 区間.
    private Section section;

    /// 吸着剤.
    private Adsorbent adsorbent;

    /// [IndexMonitor] のマップ.
    private final Map<String, IndexMonitor> indexMonitorMap = new HashMap<>();

    /// 吸着筒の領域のビルダのインスタンスを生成する.
    private Builder() {}

    /// 区間を指定する.
    ///
    /// @param section 区間.
    /// @throws NullPointerException `section` が `null` の場合.
    /// @throws IllegalStateException `section` の分割数が小さい場合.
    public Builder section(Section section) {
      Preconditions.checkNotNull(section);
      if (!this.indexMonitorMap.isEmpty()) {
        Preconditions.checkArgument(
            section.number() > this.indexMonitorMap.values().stream()
                .mapToInt(IndexMonitor::index)
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

          Builder.this.indexMonitorMap.put(
              IndexMonitorBuilder.this.fileName,
              new IndexMonitor(this.index, monitor));

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

          Builder.this.indexMonitorMap.put(
              IndexMonitorBuilder.this.fileName,
              new IndexMonitor(index, this.monitor));

          return Builder.this;
        }

      } // IndexBuilder

    } // IndexMonitorBuilder

  } // Builder

  /// 区間.
  private final Section section;

  /// 吸着剤.
  private final Adsorbent adsorbent;

  /// [IndexMonitor] の不変マップ.
  private final Map<String, IndexMonitor> indexMonitorMap;

  /// 吸着筒の領域のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private ColumnZone(Builder builder) {
    this.section = builder.section;
    this.adsorbent = builder.adsorbent;
    this.indexMonitorMap = ImmutableMap.copyOf(builder.indexMonitorMap);

    LOGGER.debug("section = {}", this.section);
    LOGGER.debug("adsorbent = {}", this.adsorbent);
    LOGGER.debug("indexMonitorMap = {}", this.indexMonitorMap);
  }

  /// ビルダのインスタンスを生成して返す.
  ///
  /// @return ビルダのインスタンス.
  public static Builder builder() {
    return new Builder();
  }

  /// 区間を返す.
  ///
  /// @return 区間.
  public Section section() {
    return section;
  }

  /// 吸着剤を返す.
  ///
  /// @return 吸着剤.
  public Adsorbent adsorbent() {
    return adsorbent;
  }

  /// 位置番号の不変マップを返す.
  ///
  /// @return 位置番号の不変マップ.
  public Map<String, Integer> indexMap() {
    return this.indexMonitorMap.entrySet().stream()
        .collect(
            ImmutableMap.toImmutableMap(
                Map.Entry::getKey,
                entry -> entry.getValue().index()));
  }

  /// モニタの不変マップを返す.
  ///
  /// @return モニタの不変マップ.
  public Map<String, Monitor> monitorMap() {
    return this.indexMonitorMap.entrySet().stream()
        .collect(
            ImmutableMap.toImmutableMap(
                Map.Entry::getKey,
                entry -> entry.getValue().monitor()));
  }

  /// [IndexMonitor] の不変マップを返す.
  ///
  /// @return [IndexMonitor] の不変マップ.
  Map<String, IndexMonitor> indexMonitorMap() {
    return indexMonitorMap;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    ColumnZone that = (ColumnZone) o;

    return this.section.equals(that.section)
        && this.adsorbent.equals(that.adsorbent)
        && this.indexMonitorMap.equals(that.indexMonitorMap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.section,
        this.adsorbent,
        this.indexMonitorMap);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("section", this.section)
        .add("adsorbent", this.adsorbent)
        .add("indexMonitorMap", this.indexMonitorMap)
        .toString();
  }

}
