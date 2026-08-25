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

/// 流入境界条件.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class InletPort {

  private static final Logger LOGGER = LogManager.getLogger(InletPort.class);

  /// 流入境界条件のビルダ.
  public static final class Builder {

    /// 流体の熱力学的温度 (K).
    private double temperature = 298.15;

    /// 流体の絶対圧力 (Pa).
    private double pressure = 101325.;

    /// 流体成分のキー.
    private ComponentKey componentKey;

    /// 流体成分の質量分率 (-) のマップ.
    private final Map<ComponentKey, Double> fractionMap = new EnumMap<>(ComponentKey.class);

    /// 流入境界条件のビルダのインスタンスを生成する.
    private Builder() {}

    /// 流体の熱力学的温度を設定する.
    ///
    /// @param temperature 流体の熱力学的温度 (K).
    /// @return ビルダのインスタンス.
    /// @throws IllegalArgumentException `temperature` が 0 以下の場合 (`temperature <= 0`).
    public Builder temperature(double temperature) {
      Preconditions.checkArgument(temperature > 0.);

      this.temperature = temperature;
      return this;
    }

    /// 流体の絶対圧力を設定する.
    ///
    /// @param pressure 流体の絶対圧力 (Pa).
    /// @return ビルダのインスタンス.
    /// @throws IllegalArgumentException `pressure` が 0 以下の場合
    public Builder pressure(double pressure) {
      Preconditions.checkArgument(pressure > 0.);

      this.pressure = pressure;
      return this;
    }

    /// 流体成分のキーを設定する.
    ///
    /// @param componentKey 流体成分のキー.
    /// @return ビルダのインスタンス.
    /// @throws IllegalStateException 流体成分のキーが設定済みの場合.
    /// @throws NullPointerException `componentKey` が `null` の場合.
    public Builder componentKey(ComponentKey componentKey) {
      Preconditions.checkState(this.componentKey == null);
      Preconditions.checkNotNull(componentKey);

      this.componentKey = componentKey;
      return this;
    }

    /// 流体成分の質量分率を設定する.
    ///
    /// @param fraction 流体成分の質量分率 (-).
    /// @return ビルダのインスタンス.
    /// @throws IllegalStateException 流体成分のキーが未設定の場合.
    /// @throws IllegalArgumentException `fraction` が 0 未満か, 既設定値との総和が 1 を超える場合.
    public Builder fraction(double fraction) {
      Preconditions.checkState(this.componentKey != null);
      Preconditions.checkArgument(
          0. <= fraction
              && fraction + this.fractionMap.entrySet().stream()
              .filter(entry -> entry.getKey() != this.componentKey)
              .map(Map.Entry::getValue)
              .mapToDouble(x -> x)
              .sum() <= 1.);

      this.fractionMap.put(this.componentKey, fraction);
      this.componentKey = null;
      return this;
    }

    /// 流入境界条件のインスタンスを生成して返す.
    ///
    /// @return 流入境界条件のインスタンス.
    public InletPort build() {
      return new InletPort(this);
    }

  } // Builder

  /// 流体の熱力学的温度 (K).
  private final double temperature;

  /// 流体の絶対圧力 (Pa).
  private final double pressure;

  /// 流体成分の質量分率 (-) の不変マップ.
  private final Map<ComponentKey, Double> fractionMap;

  /// 流入境界条件のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private InletPort(Builder builder) {
    this.temperature = builder.temperature;
    this.pressure = builder.pressure;
    this.fractionMap = Maps.immutableEnumMap(builder.fractionMap);

    LOGGER.debug("temperature = {}", this.temperature);
    LOGGER.debug("pressure = {}", this.pressure);
    LOGGER.debug("fractionMap = {}", this.fractionMap);
  }

  /// 流体の熱力学的温度を返す.
  ///
  /// @return 流体の熱力学的温度 (K).
  public double temperature() {
    return this.temperature;
  }

  /// 流体の絶対圧力を返す.
  ///
  /// @return 流体の絶対圧力 (Pa).
  public double pressure() {
    return this.pressure;
  }

  /// 流体成分の質量分率の不変マップを返す.
  ///
  /// @return 流体成分の質量分率 (-) の不変マップ.
  public Map<ComponentKey, Double> fractionMap() {
    return this.fractionMap;
  }

}
