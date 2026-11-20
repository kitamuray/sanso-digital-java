/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/// 基本 PSA の状態.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class BasicPsaState {

  private static final Logger LOGGER = LogManager.getLogger(BasicPsaState.class);

  /// 基本 PSA の状態のビルダ.
  public static final class Builder {

    /// 原料供給弁が開であれば `true`.
    private boolean isFeedValve;

    /// 製品払出弁が開であれば `true`.
    private boolean isProductValve;

    /// 充圧弁が開であれば `true`.
    private boolean isPressurizationValve;

    /// リンス弁が開であれば `true`.
    private boolean isRinseValve;

    /// 真空排気弁が開であれば `true`.
    private boolean isVacuumValve;

    /// 脱圧弁が開である系統キーのセット.
    private final Set<LineKey> blowValveSet = EnumSet.noneOf(LineKey.class);

    /// 上部均圧弁が開である系統キーのセット.
    private final Set<LineKey> upperEqualizationValveSet = EnumSet.noneOf(LineKey.class);

    /// 下部均圧弁が開である系統キーのセット.
    private final Set<LineKey> lowerEqualizationValveSet = EnumSet.noneOf(LineKey.class);

    /// 基本 PSA の状態のビルダのインスタンスを生成する.
    private Builder() {}

    /// 原料供給弁を開へ設定する.
    ///
    /// @return ビルダのインスタンス.
    public Builder feedValve() {
      this.isFeedValve = true;
      return this;
    }

    /// 製品払出弁を開へ設定する.
    ///
    /// @return ビルダのインスタンス.
    public Builder productValve() {
      this.isProductValve = true;
      return this;
    }

    /// 充圧弁を開へ設定する.
    ///
    /// @return ビルダのインスタンス.
    public Builder pressurizationValve() {
      this.isPressurizationValve = true;
      return this;
    }

    /// リンス弁を開へ設定する.
    ///
    /// @return ビルダのインスタンス.
    public Builder rinseValve() {
      this.isRinseValve = true;
      return this;
    }

    /// 真空排気弁を開へ設定する.
    ///
    /// @return ビルダのインスタンス.
    public Builder vacuumValve() {
      this.isVacuumValve = true;
      return this;
    }

    /// 脱圧弁が開である系統キーを設定する.
    ///
    /// @param lineKey 系統のキー.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `lineKey` が `null` の場合.
    public Builder blowValve(LineKey lineKey) {
      this.blowValveSet.add(lineKey); // null -> NullPointerException
      return this;
    }

    /// 上部均圧弁が開である系統キーを設定する.
    ///
    /// @param lineKey 系統のキー.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `lineKey` が `null` の場合.
    public Builder upperEqualizationValve(LineKey lineKey) {
      this.upperEqualizationValveSet.add(lineKey); // null -> NullPointerException
      return this;
    }

    /// 下部均圧弁が開である系統キーを設定する.
    ///
    /// @param lineKey 系統のキー.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `lineKey` が `null` の場合.
    public Builder lowerEqualizationValve(LineKey lineKey) {
      this.lowerEqualizationValveSet.add(lineKey); // null -> NullPointerException
      return this;
    }

    /// 状態のインスタンスを生成して返す.
    ///
    /// @return 状態のインスタンス.
    public BasicPsaState build() {
      return new BasicPsaState(this);
    }

  } // Builder

  /// 原料供給弁が開であれば `true`.
  private final boolean isFeedValve;

  /// 製品払出弁が開であれば `true`.
  private final boolean isProductValve;

  /// 充圧弁が開であれば `true`.
  private final boolean isPressurizationValve;

  /// リンス弁が開であれば `true`.
  private final boolean isRinseValve;

  /// 真空排気弁が開であれば `true`.
  private final boolean isVacuumValve;

  /// 脱圧弁が開である系統キーの不変セット.
  private final Set<LineKey> blowValveSet;

  /// 上部均圧弁が開である系統キーの不変セット.
  private final Set<LineKey> upperEqualizationValveSet;

  /// 下部均圧弁が開である系統キーの不変セット.
  private final Set<LineKey> lowerEqualizationValveSet;

  /// 基本 PSA の状態のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private BasicPsaState(Builder builder) {
    this.isFeedValve = builder.isFeedValve;
    this.isProductValve = builder.isProductValve;
    this.isPressurizationValve = builder.isPressurizationValve;
    this.isRinseValve = builder.isRinseValve;
    this.isVacuumValve = builder.isVacuumValve;
    this.blowValveSet = Sets.immutableEnumSet(builder.blowValveSet);
    this.upperEqualizationValveSet = Sets.immutableEnumSet(builder.upperEqualizationValveSet);
    this.lowerEqualizationValveSet = Sets.immutableEnumSet(builder.lowerEqualizationValveSet);

    LOGGER.debug("isFeedValve = {}", this.isFeedValve);
    LOGGER.debug("isProductValve = {}", this.isProductValve);
    LOGGER.debug("isPressurizationValve = {}", this.isPressurizationValve);
    LOGGER.debug("isRinseValve = {}", this.isRinseValve);
    LOGGER.debug("isVacuumValve = {}", this.isVacuumValve);
    LOGGER.debug("blowValveSet = {}", this.blowValveSet);
    LOGGER.debug("upperEqualizationValveSet = {}", this.upperEqualizationValveSet);
    LOGGER.debug("lowerEqualizationValveSet = {}", this.lowerEqualizationValveSet);
  }

  /// ビルダのインスタンスを生成して返す.
  ///
  /// @return ビルダのインスタンス.
  public static Builder builder() {
    return new Builder();
  }

  /// 原料供給弁が開であれば `true` を返す.
  ///
  /// @return 原料供給弁が開であれば `true`.
  public boolean isFeedValve() {
    return this.isFeedValve;
  }

  /// 製品払出弁が開であれば `true` を返す.
  ///
  /// @return 製品払出弁が開であれば `true`.
  public boolean isProductValve() {
    return this.isProductValve;
  }

  /// 充圧弁が開であれば `true` を返す.
  ///
  /// @return 充圧弁が開であれば `true`.
  public boolean isPressurizationValve() {
    return this.isPressurizationValve;
  }

  /// リンス弁が開であれば `true` を返す.
  ///
  /// @return リンス弁が開であれば `true`.
  public boolean isRinseValve() {
    return this.isRinseValve;
  }

  /// 真空排気弁が開であれば `true` を返す.
  ///
  /// @return 真空排気弁が開であれば `true`.
  public boolean isVacuumValve() {
    return this.isVacuumValve;
  }

  /// 脱圧弁が開である系統のキーの不変セットを返す.
  ///
  /// @return 脱圧弁が開である系統のキーの不変セット.
  public Set<LineKey> blowValveSet() {
    return this.blowValveSet;
  }

  /// 上部均圧弁が開である系統のキーの不変セットを返す.
  ///
  /// @return 上部均圧弁が開である系統のキーの不変セット.
  public Set<LineKey> upperEqualizationValveSet() {
    return this.upperEqualizationValveSet;
  }

  /// 下部均圧弁が開である系統のキーの不変セットを返す.
  ///
  /// @return 下部均圧弁が開である系統のキーの不変セット.
  public Set<LineKey> lowerEqualizationValveSet() {
    return this.lowerEqualizationValveSet;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    BasicPsaState that = (BasicPsaState) o;

    return that.isFeedValve == this.isFeedValve
        && that.isProductValve == this.isProductValve
        && that.isPressurizationValve == this.isPressurizationValve
        && that.isRinseValve == this.isRinseValve
        && that.isVacuumValve == this.isVacuumValve
        && this.blowValveSet.equals(that.blowValveSet)
        && this.upperEqualizationValveSet.equals(that.upperEqualizationValveSet)
        && this.lowerEqualizationValveSet.equals(that.lowerEqualizationValveSet);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.isFeedValve,
        this.isProductValve,
        this.isPressurizationValve,
        this.isRinseValve,
        this.isVacuumValve,
        this.blowValveSet,
        this.upperEqualizationValveSet,
        this.lowerEqualizationValveSet);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("isFeedValve", this.isFeedValve)
        .add("isProductValve", this.isProductValve)
        .add("isPressurizationValve", this.isPressurizationValve)
        .add("isRinseValve", this.isRinseValve)
        .add("isVacuumValve", this.isVacuumValve)
        .add("blowValveSet", this.blowValveSet)
        .add("upperEqualizationValveSet", this.upperEqualizationValveSet)
        .add("lowerEqualizationValveSet", this.lowerEqualizationValveSet)
        .toString();
  }

}
