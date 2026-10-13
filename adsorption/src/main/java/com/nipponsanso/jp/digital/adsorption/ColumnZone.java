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

/// 吸着筒の充填層領域.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class ColumnZone {

  /// 吸着筒の領域のビルダ.
  public static final class Builder {

    /// 区間.
    private Section section;

    /// 吸着剤.
    private Adsorbent adsorbent;

    /// 位置番号とモニタのマップ.
    private final Map<String, IndexMonitor> indexMonitorMap = new HashMap<>();

    /// 吸着筒の領域のビルダのインスタンスを生成する.
    private Builder() {}

    /// 区間を指定する.
    ///
    /// @param section 区間.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `section` が `null` の場合.
    /// @throws IllegalStateException `section` の分割数が小さい場合.
    public Builder section(Section section) {
      Preconditions.checkNotNull(section);

      int maxIndex = this.indexMonitorMap.values().stream()
          .mapToInt(IndexMonitor::index)
          .max().orElse(0);

      Preconditions.checkArgument(maxIndex < section.number());

      this.section = section;
      return this;
    }

    /// 吸着剤を設定する.
    ///
    /// @param adsorbent 吸着剤.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `adsorbent` が `null` の場合.
    public Builder adsorbent(Adsorbent adsorbent) {
      this.adsorbent = Preconditions.checkNotNull(adsorbent);
      return this;
    }

    /// 出力ファイル名を設定する.
    ///
    /// @param fileName 出力ファイル名.
    /// @return 位置番号とモニタのマップのビルダのインスタンス.
    /// @throws NullPointerException `fileName` が `null` の場合.
    public IndexMonitorMapBuilder<Builder> fileName(String fileName) {
      return new IndexMonitorMapBuilder<>(fileName) {
        @Override
        Map<String, IndexMonitor> indexMonitorMap() {
          return Builder.this.indexMonitorMap;
        }

        @Override
        Builder self() {
          return Builder.this;
        }

        @Override
        int size() {
          return Builder.this.section == null ? Integer.MAX_VALUE : Builder.this.section.number();
        }
      };

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

  } // Builder

  /// 区間.
  private final Section section;

  /// 吸着剤.
  private final Adsorbent adsorbent;

  /// 位置番号とモニタの不変マップ.
  private final Map<String, IndexMonitor> indexMonitorMap;

  /// 吸着筒の領域のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private ColumnZone(Builder builder) {
    this.section = builder.section;
    this.adsorbent = builder.adsorbent;
    this.indexMonitorMap = ImmutableMap.copyOf(builder.indexMonitorMap);
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
    return this.section;
  }

  /// 吸着剤を返す.
  ///
  /// @return 吸着剤.
  public Adsorbent adsorbent() {
    return this.adsorbent;
  }

  /// 位置番号とモニタの不変マップを返す.
  ///
  /// @return 位置番号とモニタの不変マップ.
  Map<String, IndexMonitor> indexMonitorMap() {
    return this.indexMonitorMap;
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
        this.getClass(),
        this.section,
        this.adsorbent,
        this.indexMonitorMap);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("section", this.section)
        .add("adsorbent", this.adsorbent)
        .add("indexMap", this.indexMap())
        .add("monitorMap", this.monitorMap())
        .toString();
  }

}
