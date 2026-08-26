/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/// 基本 PSA のプロセス.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class BasicPsaProcess {

  private static final Logger LOGGER = LogManager.getLogger(BasicPsaProcess.class);

  /// 基本 PSA のプロセスのビルダ.
  public static final class Builder {

    /// 組成.
    private Composition composition;

    /// 流体.
    private Fluid fluid;

    /// 吸着筒.
    private Column column;

    /// 製品槽.
    private Tank productTank;

    /// 原料供給境界.
    private InletPort feedPort;

    /// 製品払出境界.
    private OutletPort productPort;

    /// 大気放出境界.
    private OutletPort exhaustPort;

    /// 真空ポンプ.
    private VacuumPump vacuumPump;

    /// 原料供給主弁.
    private FlowControlValve mainFeedValve;

    /// 製品払出主弁.
    private FlowControlValve mainProductValve;

    /// 充圧主弁.
    private RestrictValve mainPressurizationValve;

    /// リンス主弁.
    private FlowControlValve mainRinseValve;

    /// 原料供給弁.
    private OnOffValve feedValve;

    /// 製品払出弁.
    private OnOffValve productValve;

    /// 充圧弁.
    private OnOffValve pressurizationValve;

    /// リンス弁.
    private OnOffValve rinseValve;

    /// 真空排気弁.
    private OnOffValve vacuumValve;

    /// 脱圧弁のマップ.
    private final Map<LineKey, OnOffAndRestrictValve> blowValveMap = new EnumMap<>(LineKey.class);

    /// 上部均圧弁のマップ.
    private final Map<LineKey, OnOffAndRestrictValve> upperEqualizationValveMap
        = new EnumMap<>(LineKey.class);

    /// 下部均圧弁のマップ.
    private final Map<LineKey, OnOffAndRestrictValve> lowerEqualizationValveMap
        = new EnumMap<>(LineKey.class);

    /// 基本 PSA のプロセスのビルダのインスタンスを生成する.
    private Builder() {}

    /// 組成を設定する.
    ///
    /// @param composition 組成.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `composition` が `null` の場合.
    public Builder composition(Composition composition) {
      this.composition = Preconditions.checkNotNull(composition);
      return this;
    }

    /// 流体を設定する.
    ///
    /// @param fluid 流体.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `fluid` が `null` の場合.
    public Builder fluid(Fluid fluid) {
      this.fluid = Preconditions.checkNotNull(fluid);
      return this;
    }

    /// 吸着筒を設定する.
    ///
    /// @param column 吸着筒.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `column` が `null` の場合.
    public Builder column(Column column) {
      this.column = Preconditions.checkNotNull(column);
      return this;
    }

    /// 製品槽を設定する.
    ///
    /// @param productTank 製品槽.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `productTank` が `null` の場合.
    public Builder productTank(Tank productTank) {
      this.productTank = Preconditions.checkNotNull(productTank);
      return this;
    }

    /// 原料供給境界を設定する.
    ///
    /// @param feedPort 原料供給境界.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `feedPort` が `null` の場合.
    public Builder feedPort(InletPort feedPort) {
      this.feedPort = Preconditions.checkNotNull(feedPort);
      return this;
    }

    /// 製品払出境界を設定する.
    ///
    /// @param productPort 製品払出境界.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `productPort` が `null` の場合.
    public Builder productPort(OutletPort productPort) {
      this.productPort = Preconditions.checkNotNull(productPort);
      return this;
    }

    /// 大気放出境界を設定する.
    ///
    /// @param exhaustPort 大気放出境界.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `exhaustPort` が `null` の場合.
    public Builder exhaustPort(OutletPort exhaustPort) {
      this.exhaustPort = Preconditions.checkNotNull(exhaustPort);
      return this;
    }

    /// 真空ポンプを設定する.
    ///
    /// @param vacuumPump 真空ポンプ.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `vacuumPump` が `null` の場合.
    public Builder vacuumPump(VacuumPump vacuumPump) {
      this.vacuumPump = Preconditions.checkNotNull(vacuumPump);
      return this;
    }

    /// 原料供給主弁を設定する.
    ///
    /// @param mainFeedValve 原料供給主弁.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `mainFeedValve` が `null` の場合.
    public Builder mainFeedValve(FlowControlValve mainFeedValve) {
      this.mainFeedValve = Preconditions.checkNotNull(mainFeedValve);
      return this;
    }

    /// 製品払出主弁を設定する.
    ///
    /// @param mainProductValve 製品払出主弁.
    /// @return ビルダのインスタンス.
    public Builder mainProductValve(FlowControlValve mainProductValve) {
      this.mainProductValve = Preconditions.checkNotNull(mainProductValve);
      return this;
    }

    /// 充圧主弁を返す.
    ///
    /// @param mainPressurizationValve 充圧主弁.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `mainPressurizationValve` が `null` の場合.
    public Builder mainPressurizationValve(RestrictValve mainPressurizationValve) {
      this.mainPressurizationValve = Preconditions.checkNotNull(mainPressurizationValve);
      return this;
    }

    /// リンス主弁を返す.
    ///
    /// @param mainRinseValve リンス主弁.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `mainRinseValve` が `null` の場合.
    public Builder mainRinseValve(FlowControlValve mainRinseValve) {
      this.mainRinseValve = Preconditions.checkNotNull(mainRinseValve);
      return this;
    }

    /// 原料供給弁を返す.
    ///
    /// @param feedValve 原料供給弁.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `feedValve` が `null` の場合.
    public Builder feedValve(OnOffValve feedValve) {
      this.feedValve = Preconditions.checkNotNull(feedValve);
      return this;
    }

    /// 製品払出弁を設定する.
    ///
    /// @param productValve 製品払出弁.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `productValve` が `null` の場合.
    public Builder productValve(OnOffValve productValve) {
      this.productValve = Preconditions.checkNotNull(productValve);
      return this;
    }

    /// 充圧弁を設定する.
    ///
    /// @param pressurizationValve 充圧弁.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `pressurizationValve` が `null` の場合.
    public Builder pressurizationValve(OnOffValve pressurizationValve) {
      this.pressurizationValve = Preconditions.checkNotNull(pressurizationValve);
      return this;
    }

    /// リンス弁を設定する.
    ///
    /// @param rinseValve リンス弁.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `rinseValve` が `null` の場合.
    public Builder rinseValve(OnOffValve rinseValve) {
      this.rinseValve = Preconditions.checkNotNull(rinseValve);
      return this;
    }

    /// 真空排気弁を設定する.
    ///
    /// @param vacuumValve 真空排気弁.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `vacuumValve` が `null` の場合.
    public Builder vacuumValve(OnOffValve vacuumValve) {
      this.vacuumValve = Preconditions.checkNotNull(vacuumValve);
      return this;
    }

    /// 脱圧弁のビルダのインスタンスを生成して返す.
    ///
    /// @param lineKey 脱圧系統のキー.
    /// @return 脱圧弁のビルダのインスタンス.
    /// @throws NullPointerException `lineKey` が `null` の場合.
    public BlowValveBuilder blow(LineKey lineKey) {
      return new BlowValveBuilder(lineKey);
    }

    /// 上部均圧弁のビルダのインスタンスを生成して返す.
    ///
    /// @param lineKey 上部均圧系統のキー.
    /// @return 上部均圧弁のビルダのインスタンス.
    /// @throws NullPointerException `lineKey` が `null` の場合.
    public UpperEqualizationValveBuilder upperEqualization(LineKey lineKey) {
      return new UpperEqualizationValveBuilder(lineKey);
    }

    /// 下部均圧弁のビルダのインスタンスを生成して返す.
    ///
    /// @param lineKey 下部均圧系統のキー.
    /// @return 下部均圧弁のビルダのインスタンス.
    /// @throws NullPointerException `lineKey` が `null` の場合.
    public LowerEqualizationValveBuilder lowerEqualization(LineKey lineKey) {
      return new LowerEqualizationValveBuilder(lineKey);
    }

    /// 基本 PSA のプロセスのインスタンスを生成して返す.
    ///
    /// @return 基本 PSA のプロセスのインスタンス.
    /// @throws IllegalStateException ビルダが不完全な場合.
    public BasicPsaProcess build() {
      Preconditions.checkState(this.composition != null);
      Preconditions.checkState(this.fluid != null);
      Preconditions.checkState(this.column != null);
      Preconditions.checkState(this.productTank != null);
      Preconditions.checkState(this.feedPort != null);
      Preconditions.checkState(this.productPort != null);
      Preconditions.checkState(this.exhaustPort != null);
      Preconditions.checkState(this.vacuumPump != null);
      Preconditions.checkState(this.mainFeedValve != null);
      Preconditions.checkState(this.mainProductValve != null);
      Preconditions.checkState(this.mainPressurizationValve != null);
      Preconditions.checkState(this.mainRinseValve != null);
      Preconditions.checkState(this.feedValve != null);
      Preconditions.checkState(this.productValve != null);
      Preconditions.checkState(this.pressurizationValve != null);
      Preconditions.checkState(this.rinseValve != null);
      Preconditions.checkState(this.vacuumValve != null);
      Preconditions.checkState(!this.blowValveMap.isEmpty());
      // upperEqualizationValveMap は empty を許容
      // lowerEqualizationValveMap は empty を許容

      return new BasicPsaProcess(this);
    }

    /// 脱圧弁のビルダ.
    public final class BlowValveBuilder
        extends OnOffAndRestrictValveMapBuilder<Builder, LineKey> {

      /// 脱圧弁のビルダのインスタンスを生成する.
      ///
      /// @param lineKey 系統のキー.
      /// @throws NullPointerException `lineKey` が `null` の場合.
      private BlowValveBuilder(LineKey lineKey) {
        super(lineKey);
      }

      /// {@inheritDoc}
      ///
      /// @return {@inheritDoc}
      @Override
      Builder self() {
        return Builder.this;
      }

      /// {@inheritDoc}
      ///
      /// @return {@inheritDoc}
      @Override
      Map<LineKey, OnOffAndRestrictValve> map() {
        return Builder.this.blowValveMap;
      }

    } // BlowValveBuilder

    /// 上部均圧弁のビルダ.
    public final class UpperEqualizationValveBuilder
        extends OnOffAndRestrictValveMapBuilder<Builder, LineKey> {

      /// 上部均圧弁のビルダのインスタンスを生成する.
      ///
      /// @param lineKey 系統のキー.
      /// @throws NullPointerException `lineKey` が `null` の場合.
      private UpperEqualizationValveBuilder(LineKey lineKey) {
        super(lineKey);
      }

      /// {@inheritDoc}
      ///
      /// @return {@inheritDoc}
      @Override
      Builder self() {
        return Builder.this;
      }

      /// {@inheritDoc}
      ///
      /// @return {@inheritDoc}
      @Override
      Map<LineKey, OnOffAndRestrictValve> map() {
        return Builder.this.upperEqualizationValveMap;
      }

    } // UpperEqualizationValveBuilder

    /// 下部均圧弁のビルダ.
    public final class LowerEqualizationValveBuilder
        extends OnOffAndRestrictValveMapBuilder<Builder, LineKey> {

      /// 下部均圧弁のビルダのインスタンスを生成する.
      ///
      /// @param linekey 系統のキー.
      /// @throws NullPointerException `lineKey` が `null` の場合.
      private LowerEqualizationValveBuilder(LineKey linekey) {
        super(linekey);
      }

      /// {@inheritDoc}
      ///
      /// @return {@inheritDoc}
      @Override
      Builder self() {
        return Builder.this;
      }

      /// {@inheritDoc}
      ///
      /// @return {@inheritDoc}
      @Override
      Map<LineKey, OnOffAndRestrictValve> map() {
        return Builder.this.lowerEqualizationValveMap;
      }

    } // LowerEqualizationValveBuilder

  } // Builder

  /// 組成.
  private final Composition composition;

  /// 流体.
  private final Fluid fluid;

  /// 吸着筒.
  private final Column column;

  /// 製品槽.
  private final Tank productTank;

  /// 原料供給境界.
  private final InletPort feedPort;

  /// 製品払出境界.
  private final OutletPort productPort;

  /// 大気放出境界.
  private final OutletPort exhaustPort;

  /// 真空ポンプ.
  private final VacuumPump vacuumPump;

  /// 原料供給主弁.
  private final FlowControlValve mainFeedValve;

  /// 製品払出主弁.
  private final FlowControlValve mainProductValve;

  /// 充圧主弁.
  private final RestrictValve mainPressurizationValve;

  /// リンス主弁.
  private final FlowControlValve mainRinseValve;

  /// 原料供給弁.
  private final OnOffValve feedValve;

  /// 製品払出弁.
  private final OnOffValve productValve;

  /// 充圧弁.
  private final OnOffValve pressurizationValve;

  /// リンス弁.
  private final OnOffValve rinseValve;

  /// 真空排気弁.
  private final OnOffValve vacuumValve;

  /// 脱圧弁の不変マップ.
  private final Map<LineKey, OnOffAndRestrictValve> blowValveMap;

  /// 上部均圧弁の不変マップ.
  private final Map<LineKey, OnOffAndRestrictValve> upperEqualizationValveMap;

  /// 下部均圧弁の不変マップ.
  private final Map<LineKey, OnOffAndRestrictValve> lowerEqualizationValveMap;

  /// 基本 PSA のプロセスのインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private BasicPsaProcess(Builder builder) {
    this.composition = builder.composition;
    this.fluid = builder.fluid;
    this.column = builder.column;
    this.productTank = builder.productTank;
    this.feedPort = builder.feedPort;
    this.productPort = builder.productPort;
    this.exhaustPort = builder.exhaustPort;
    this.vacuumPump = builder.vacuumPump;
    this.mainFeedValve = builder.mainFeedValve;
    this.mainProductValve = builder.mainProductValve;
    this.mainPressurizationValve = builder.mainPressurizationValve;
    this.mainRinseValve = builder.mainRinseValve;
    this.feedValve = builder.feedValve;
    this.productValve = builder.productValve;
    this.pressurizationValve = builder.pressurizationValve;
    this.rinseValve = builder.rinseValve;
    this.vacuumValve = builder.vacuumValve;
    this.blowValveMap = Maps.immutableEnumMap(builder.blowValveMap);
    this.upperEqualizationValveMap = Maps.immutableEnumMap(builder.upperEqualizationValveMap);
    this.lowerEqualizationValveMap = Maps.immutableEnumMap(builder.lowerEqualizationValveMap);

    LOGGER.debug("composition = {}", this.composition);
    LOGGER.debug("fluid = {}", this.fluid);
    LOGGER.debug("column = {}", this.column);
    LOGGER.debug("productTank = {}", this.productTank);
    LOGGER.debug("feedPort = {}", this.feedPort);
    LOGGER.debug("productPort = {}", this.productPort);
    LOGGER.debug("exhaustPort = {}", this.exhaustPort);
    LOGGER.debug("vacuumPump = {}", this.vacuumPump);
    LOGGER.debug("mainFeedValve = {}", this.mainFeedValve);
    LOGGER.debug("mainProductValve = {}", this.mainProductValve);
    LOGGER.debug("mainPressurizationValve = {}", this.mainPressurizationValve);
    LOGGER.debug("mainRinseValve = {}", this.mainRinseValve);
    LOGGER.debug("feedValve = {}", this.feedValve);
    LOGGER.debug("productValve = {}", this.productValve);
    LOGGER.debug("pressurizationValve = {}", this.pressurizationValve);
    LOGGER.debug("rinseValve = {}", this.rinseValve);
    LOGGER.debug("vacuumValve = {}", this.vacuumValve);
    LOGGER.debug("blowValveMap = {}", this.blowValveMap);
    LOGGER.debug("upperEqualizationValveMap = {}", this.upperEqualizationValveMap);
    LOGGER.debug("lowerEqualizationValveMap = {}", this.lowerEqualizationValveMap);
  }

  /// 組成を返す.
  ///
  /// @return 組成.
  public Composition composition() {
    return this.composition;
  }

  /// 流体を返す.
  ///
  /// @return 流体.
  public Fluid fluid() {
    return this.fluid;
  }

  /// 吸着筒を返す.
  ///
  /// @return 吸着筒.
  public Column column() {
    return this.column;
  }

  /// 製品槽を返す.
  ///
  /// @return 製品槽.
  public Tank productTank() {
    return this.productTank;
  }

  /// 原料供給境界を返す.
  ///
  /// @return 原料供給境界.
  public InletPort feedPort() {
    return this.feedPort;
  }

  /// 製品払出境界を返す.
  ///
  /// @return 製品払出境界.
  public OutletPort productPort() {
    return this.productPort;
  }

  /// 大気放出境界を返す.
  ///
  /// @return 大気放出境界.
  public OutletPort exhaustPort() {
    return this.exhaustPort;
  }

  /// 真空ポンプを返す.
  ///
  /// @return 真空ポンプ.
  public VacuumPump vacuumPump() {
    return this.vacuumPump;
  }

  /// 原料供給主弁を返す.
  ///
  /// @return 原料供給主弁.
  public FlowControlValve mainFeedValve() {
    return this.mainFeedValve;
  }

  /// 製品払出主弁を返す.
  ///
  /// @return 製品払出主弁.
  public FlowControlValve mainProductValve() {
    return this.mainProductValve;
  }

  /// 充圧主弁を返す.
  ///
  /// @return 充圧主弁.
  public RestrictValve mainPressurizationValve() {
    return this.mainPressurizationValve;
  }

  /// リンス主弁を返す.
  ///
  /// @return リンス主弁.
  public FlowControlValve mainRinseValve() {
    return this.mainRinseValve;
  }

  /// 原料供給弁を返す.
  ///
  /// @return 原料供給弁.
  public OnOffValve feedValve() {
    return this.feedValve;
  }

  /// 製品払出弁を返す.
  ///
  /// @return 製品払出弁.
  public OnOffValve productValve() {
    return this.productValve;
  }

  /// 充圧弁を返す.
  ///
  /// @return 充圧弁.
  public OnOffValve pressurizationValve() {
    return this.pressurizationValve;
  }

  /// リンス弁を返す.
  ///
  /// @return リンス弁.
  public OnOffValve rinseValve() {
    return this.rinseValve;
  }

  /// 真空排気弁を返す.
  ///
  /// @return 真空排気弁.
  public OnOffValve vacuumValve() {
    return this.vacuumValve;
  }

  /// 脱圧弁の不変マップを返す.
  ///
  /// @return 脱圧弁の不変マップ.
  Map<LineKey, OnOffAndRestrictValve> blowValveMap() {
    return this.blowValveMap;
  }

  /// 脱圧弁を構成する On-Off 弁の不変マップを返す.
  ///
  /// @return 脱圧弁を構成する On-Off 弁の不変マップ.
  public Map<LineKey, OnOffValve> onOffBlowValveMap() {
    return this.blowValveMap.entrySet().stream()
        .collect(
            Maps.toImmutableEnumMap(
                Map.Entry::getKey,
                entry -> entry.getValue().onOffValve()));
  }

  /// 脱圧弁を構成する On-Off 弁の不変マップを返す.
  ///
  /// @return 脱圧弁を構成する On-Off 弁の不変マップ.
  public Map<LineKey, RestrictValve> restrictBlowValveMap() {
    return this.blowValveMap.entrySet().stream()
        .collect(
            Maps.toImmutableEnumMap(
                Map.Entry::getKey,
                entry -> entry.getValue().restrictValve()));
  }

  /// 上部均圧弁の不変マップを返す.
  ///
  /// @return 上部均圧弁の不変マップ.
  Map<LineKey, OnOffAndRestrictValve> upperEqualizationValveMap() {
    return this.upperEqualizationValveMap;
  }

  /// 上部均圧弁を構成する On-Off 弁の不変マップを返す.
  ///
  /// @return 上部均圧弁を構成する On-Off 弁の不変マップ.
  public Map<LineKey, OnOffValve> onOffUpperEqualizationValveMap() {
    return upperEqualizationValveMap.entrySet().stream()
        .collect(
            Maps.toImmutableEnumMap(
                Map.Entry::getKey,
                entry -> entry.getValue().onOffValve()));
  }

  /// 上部均圧弁を構成する調節弁の不変マップを返す.
  ///
  /// @return 上部均圧弁を構成する調節弁の不変マップ.
  public Map<LineKey, RestrictValve> restrictUpperEqualizationValveMap() {
    return upperEqualizationValveMap.entrySet().stream()
        .collect(
            Maps.toImmutableEnumMap(
                Map.Entry::getKey,
                entry -> entry.getValue().restrictValve()));
  }

  /// 下部均圧弁の不変マップを返す.
  ///
  /// @return 下部均圧弁の不変マップ.
  Map<LineKey, OnOffAndRestrictValve> lowerEqualizationValveMap() {
    return this.lowerEqualizationValveMap;
  }

  /// 下部均圧弁を構成する On-Off 弁の不変マップを返す.
  ///
  /// @return 下部均圧弁を構成する On-Off 弁の不変マップ.
  public Map<LineKey, OnOffValve> onOffLowerEqualizationValveMap() {
    return lowerEqualizationValveMap.entrySet().stream()
        .collect(
            Maps.toImmutableEnumMap(
                Map.Entry::getKey,
                entry -> entry.getValue().onOffValve()));
  }

  /// 下部均圧弁を構成する調節弁の不変マップを返す.
  ///
  /// @return 下部均圧弁を構成する調節弁の不変マップ.
  public Map<LineKey, RestrictValve> restrictLowerEqualizationValveMap() {
    return lowerEqualizationValveMap.entrySet().stream()
        .collect(
            Maps.toImmutableEnumMap(
                Map.Entry::getKey,
                entry -> entry.getValue().restrictValve()));
  }

}
