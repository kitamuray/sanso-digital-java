/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/// 基本 PSA の工程.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class BasicPsaSchedule {

  private static final Logger LOGGER = LogManager.getLogger(BasicPsaSchedule.class);

  /// 基本 PSA の工程のビルダ.
  public static final class Builder {

    /// 区間.
    private Section section;

    /// 状態のマップ.
    private final Map<ColumnKey, BasicPsaState> stateMap = new EnumMap<>(ColumnKey.class);

    /// 位置番号とモニタのマップ.
    private final Map<String, IndexMonitor> indexMonitorMap = new HashMap<>();

    /// 基本 PSA の工程のビルダのインスタンスを生成する.
    private Builder() {}

    /// 区間を設定する.
    ///
    /// @param section 区間.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `section` が `null` の場合.
    /// @throws IllegalArgumentException `section` の分割数が小さい場合.
    public Builder section(Section section) {
      Preconditions.checkNotNull(section);

      int maxIndex = this.indexMonitorMap.values().stream()
          .mapToInt(IndexMonitor::index)
          .max().orElse(0);
      Preconditions.checkArgument(maxIndex < section.number());

      this.section = section;
      return this;
    }

    /// 状態のマップのビルダのインスタンスを生成して返す.
    ///
    /// @param columnKey 吸着筒のキー.
    /// @return 状態のマップのビルダのインスタンス.
    /// @throws NullPointerException `columnKey` が `null` の場合.
    public StateMapBuilder column(ColumnKey columnKey) {
      return new StateMapBuilder(columnKey);
    }

    private int minSize() {
      return this.section == null ? Integer.MAX_VALUE : this.section.number();
    }

    /// 工程のインスタンスを生成して返す.
    ///
    /// @return 工程のインスタンス.
    /// @throws IllegalStateException ビルダが不完全な場合.
    public BasicPsaSchedule build() {
      Preconditions.checkState(this.section != null);

      return new BasicPsaSchedule(this);
    }

    /// 状態のマップのビルダ.
    public final class StateMapBuilder {

      /// 吸着筒のキー.
      private final ColumnKey columnKey;

      /// 状態のマップのビルダのインスタンスを生成する.
      ///
      /// @param columnKey 吸着筒のキー.
      /// @throws NullPointerException `columnKey` が `null` の場合.
      private StateMapBuilder(ColumnKey columnKey) {
        this.columnKey = Preconditions.checkNotNull(columnKey);
      }

      /// 状態を設定する.
      ///
      /// @param state 状態.
      /// @return ビルダのインスタンス.
      /// @throws NullPointerException `state` が `null` の場合.
      public Builder state(BasicPsaState state) {
        Builder.this.stateMap.put(
            this.columnKey,
            Preconditions.checkNotNull(state));
        return Builder.this;
      }

    } // StateMapBuilder

    /// 位置番号とモニタのマップのビルダ.
    public final class IndexMonitorMapBuilder {

      /// ファイル名.
      private final String fileName;

      /// 位置番号とモニタのマップのビルダのインスタンスを生成する.
      ///
      /// @param fileName ファイル名.
      /// @throws NullPointerException `fileName` が `null` の場合.
      private IndexMonitorMapBuilder(String fileName) {
        this.fileName = Preconditions.checkNotNull(fileName);
      }

      /// 位置番号が設定されたビルダ.
      public final class IndexBuilder {

        /// 位置番号.
        private final int index;

        /// 位置番号が設定されたビルダのインスタンスを生成する.
        ///
        /// @param index 位置番号.
        /// @throws IllegalArgumentException `index` が 0 未満か, 指定済み区間の分割数以上の場合.
        private IndexBuilder(int index) {
          Preconditions.checkArgument(0 <= index && index < Builder.this.minSize());

          this.index = index;
        }

        /// モニタを設定する.
        ///
        /// @param monitor モニタ.
        /// @return ビルダのインスタンス.
        /// @throws NullPointerException `monitor` が `null` の場合.
        public Builder monitor(Monitor monitor) {
          Builder.this.indexMonitorMap.put(
              IndexMonitorMapBuilder.this.fileName,
              new IndexMonitor(
                  this.index,
                  Preconditions.checkNotNull(monitor)));
          return Builder.this;
        }

      } // IndexBuilder

      /// モニタが設定されたビルダ.
      public final class MonitorBuilder {

        /// モニタ.
        private final Monitor monitor;

        /// モニタが設定されたビルダのインスタンスを生成する.
        ///
        /// @param monitor モニタ.
        /// @throws NullPointerException `monitor` が `null` の場合.
        private MonitorBuilder(Monitor monitor) {
          this.monitor = Preconditions.checkNotNull(monitor);
        }

        /// 位置番号を設定する.
        ///
        /// @param index 位置番号.
        /// @return ビルダのインスタンス.
        /// @throws IllegalArgumentException `index` が 0 未満か, 指定済み区間の分割数以上の場合.
        public Builder index(int index) {
          Preconditions.checkArgument(0 <= index && index <= Builder.this.minSize());

          Builder.this.indexMonitorMap.put(
              IndexMonitorMapBuilder.this.fileName,
              new IndexMonitor(index, this.monitor));

          return Builder.this;
        }

      } // MonitorBuilder

    } // IndexMonitorMapBuilder

  } // Builder

  /// 区間.
  private final Section section;

  /// 状態の不変マップ.
  private final Map<ColumnKey, BasicPsaState> stateMap;

  /// 位置番号とモニタの不変マップ.
  private final Map<String, IndexMonitor> indexMonitorMap;

  /// 原料供給弁が開である吸着筒のキーの不変セット.
  private final Set<ColumnKey> feedValveSet;

  /// 製品払出弁が開である吸着筒のキーの不変セット.
  private final Set<ColumnKey> productValveSet;

  /// 充圧弁が開である吸着筒のキーの不変セット.
  private final Set<ColumnKey> pressurizationValveSet;

  /// リンス弁が開である吸着筒のキーの不変セット.
  private final Set<ColumnKey> rinseValveSet;

  /// 真空排気弁が開である吸着筒のキーの不変セット.
  private final Set<ColumnKey> vacuumValveSet;

  /// 脱圧弁が開である吸着筒のキーの不変セットの不変マップ.
  private final Map<LineKey, Set<ColumnKey>> blowValveSetMap;

  /// 上部均圧弁が開である吸着筒のキーの不変セットの不変マップ.
  private final Map<LineKey, Set<ColumnKey>> upperEqualizationValveSetMap;

  /// 下部均圧弁が開である吸着筒のキーの不変セットの不変マップ.
  private final Map<LineKey, Set<ColumnKey>> lowerEqualizationValveSetMap;

  /// 工程のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  @SuppressWarnings("DataFlowIssue")
  private BasicPsaSchedule(Builder builder) {
    this.section = builder.section;
    this.stateMap = Maps.immutableEnumMap(builder.stateMap);
    this.indexMonitorMap = Map.copyOf(builder.indexMonitorMap);

    LOGGER.debug("section = {}", this.section);
    LOGGER.debug("stateMap = {}", this.stateMap);
    LOGGER.debug("indexMonitorMap = {}", this.indexMonitorMap);

    this.feedValveSet = newSet(this.stateMap, BasicPsaState::isFeedValve);
    this.productValveSet = newSet(this.stateMap, BasicPsaState::isProductValve);
    this.pressurizationValveSet = newSet(this.stateMap, BasicPsaState::isPressurizationValve);
    this.rinseValveSet = newSet(this.stateMap, BasicPsaState::isRinseValve);
    this.vacuumValveSet = newSet(this.stateMap, BasicPsaState::isVacuumValve);

    this.blowValveSetMap = newMap(this.stateMap, BasicPsaState::blowValveSet);
    this.upperEqualizationValveSetMap
        = newMap(this.stateMap, BasicPsaState::upperEqualizationValveSet);
    this.lowerEqualizationValveSetMap
        = newMap(this.stateMap, BasicPsaState::lowerEqualizationValveSet);

    LOGGER.debug("feedValveSet = {}", this.feedValveSet);
    LOGGER.debug("productValveSet = {}", this.productValveSet);
    LOGGER.debug("pressurizationValveSet = {}", this.pressurizationValveSet);
    LOGGER.debug("rinseValveSet = {}", this.rinseValveSet);
    LOGGER.debug("vacuumValveSet = {}", this.vacuumValveSet);
    LOGGER.debug("blowValveSetMap = {}", this.blowValveSetMap);
    LOGGER.debug("upperEqualizationValveSetMap = {}", this.upperEqualizationValveSetMap);
    LOGGER.debug("lowerEqualizationValveSetMap = {}", this.lowerEqualizationValveSetMap);
  }

  /// 吸着筒のキーの不変セットを生成して返す.
  ///
  /// @param stateMap 状態のマップ.
  /// @param test 状態を引数に取り論理値を返すオペレーション.
  /// @return 吸着筒のキーの不変セット.
  private static Set<ColumnKey> newSet(
      Map<ColumnKey, BasicPsaState> stateMap, Predicate<BasicPsaState> test) {
    return stateMap.entrySet().stream()
        .filter(entry -> test.apply(entry.getValue()))
        .map(Map.Entry::getKey)
        .collect(Sets.toImmutableEnumSet());
  }

  /// 吸着筒のキーの不変セットの不変マップを生成して返す.
  ///
  /// @param stateMap 状態のマップ.
  /// @param function 状態を引数に取りセットを返すオペレーション.
  /// @return 吸着筒のキーの不変セットの不変マップ.
  private static Map<LineKey, Set<ColumnKey>> newMap(
      Map<ColumnKey, BasicPsaState> stateMap, Function<BasicPsaState, Set<LineKey>> function) {
    Map<LineKey, Set<ColumnKey>> map = new EnumMap<>(LineKey.class);

    stateMap.forEach((columnKey, state) -> function.apply(state).forEach(lineKey -> {
      map.putIfAbsent(lineKey, EnumSet.noneOf(ColumnKey.class));
      map.get(lineKey).add(columnKey);
    }));

    return map.entrySet().stream()
        .collect(
            Maps.toImmutableEnumMap(
                Map.Entry::getKey,
                entry -> Sets.immutableEnumSet(entry.getValue())));
  }

  /// 区間を返す.
  ///
  /// @return 区間.
  public Section section() {
    return this.section;
  }

  /// 状態の不変マップを返す.
  ///
  /// @return 状態の不変マップ.
  public Map<ColumnKey, BasicPsaState> stateMap() {
    return this.stateMap;
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

  /// 原料供給弁が開である吸着筒のキーの不変セットを返す.
  ///
  /// @return 原料供給弁が開である吸着筒のキーの不変セット.
  Set<ColumnKey> feedValveSet() {
    return this.feedValveSet;
  }

  /// 製品払出弁が開である吸着筒のキーの不変セットを返す.
  ///
  /// @return 製品払出弁が開である吸着筒のキーの不変セット.
  Set<ColumnKey> productValveSet() {
    return this.productValveSet;
  }

  /// 充圧弁が開である吸着筒のキーの不変セットを返す.
  ///
  /// @return 充圧弁が開である吸着筒のキーの不変セット.
  Set<ColumnKey> pressurizationValveSet() {
    return this.pressurizationValveSet;
  }

  /// リンス弁が開である吸着筒のキーの不変セットを返す.
  ///
  /// @return リンス弁が開である吸着筒のキーの不変セット.
  Set<ColumnKey> rinseValveSet() {
    return this.rinseValveSet;
  }

  /// 真空排気弁が開である吸着筒のキーの不変セットを返す.
  ///
  /// @return 真空排気弁が開である吸着筒のキーの不変セット.
  Set<ColumnKey> vacuumValveSet() {
    return this.vacuumValveSet;
  }

  /// 脱圧弁が開である吸着筒のキーの不変セットの不変マップを返す.
  ///
  /// @return 脱圧弁が開である吸着筒のキーの不変セットの不変マップ.
  Map<LineKey, Set<ColumnKey>> blowValveSetMap() {
    return this.blowValveSetMap;
  }

  /// 上部均圧弁が開である吸着筒のキーの不変セットの不変マップを返す.
  ///
  /// @return 上部均圧弁が開である吸着筒のキーの不変セットの不変マップ.
  Map<LineKey, Set<ColumnKey>> upperEqualizationValveSetMap() {
    return this.upperEqualizationValveSetMap;
  }

  /// 下部均圧弁が開である吸着筒のキーの不変セットの不変マップを返す.
  ///
  /// @return 下部均圧弁が開である吸着筒のキーの不変セットの不変マップ.
  Map<LineKey, Set<ColumnKey>> lowerEqualizationValveSetMap() {
    return this.lowerEqualizationValveSetMap;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    BasicPsaSchedule that = (BasicPsaSchedule) o;

    return this.section.equals(that.section)
        && this.stateMap.equals(that.stateMap)
        && this.indexMonitorMap.equals(that.indexMonitorMap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.section,
        this.stateMap,
        this.indexMonitorMap);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("section", this.section)
        .add("stateMap", this.stateMap)
        .add("indexMap", this.indexMap())
        .add("monitorMap", this.monitorMap())
        .toString();
  }

}
