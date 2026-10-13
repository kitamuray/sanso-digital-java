/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/// 流入境界条件.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class InletPort extends Port {

  private static final Logger LOGGER = LogManager.getLogger(InletPort.class);

  /// 流入境界条件のビルダ.
  public static final class Builder {

    /// 流体の熱力学的温度 (K).
    private double temperature = 298.15;

    /// 流体の絶対圧力 (Pa).
    private double pressure = 101325.;

    /// 流体成分の質量分率 (-) のマップ.
    private final Map<ComponentKey, Double> fractionMap = new EnumMap<>(ComponentKey.class);

    /// モニタのマップ.
    private final Map<String, Monitor> monitorMap = new HashMap<>();

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
    /// @throws NullPointerException `componentKey` が `null` の場合.
    public FractionMapBuilder componentKey(ComponentKey componentKey) {
      return new FractionMapBuilder(componentKey);
    }

    /// モニタ出力先のファイル名を設定する.
    ///
    /// @param fileName モニタ出力先のファイル名.
    /// @return モニタのマップのビルダのインスタンス.
    /// @throws NullPointerException `fileName` が `null` の場合.
    public MonitorMapBuilder<Builder> fileName(String fileName) {
      return new MonitorMapBuilder<>(fileName) {
        @Override
        Map<String, Monitor> monitorMap() {
          return Builder.this.monitorMap;
        }

        @Override
        Builder self() {
          return Builder.this;
        }
      };
    }

    /// 流入境界条件のインスタンスを生成して返す.
    ///
    /// @return 流入境界条件のインスタンス.
    public InletPort build() {
      return new InletPort(this);
    }

    /// 質量分率のマップのビルダ.
    public final class FractionMapBuilder extends AbstractFractionMapBuilder<Builder> {

      /// 質量分率のマップのビルダを生成する.
      ///
      /// @param componentKey 成分のキー.
      /// @throws NullPointerException `componentKey` が `null` の場合.
      private FractionMapBuilder(ComponentKey componentKey) {
        super(componentKey);
      }

      /// {@inheritDoc}
      ///
      /// @return {@inheritDoc}
      @Override
      Map<ComponentKey, Double> fractionMap() {
        return Builder.this.fractionMap;
      }

      /// {@inheritDoc}
      ///
      /// @return {@inheritDoc}
      @Override
      Builder self() {
        return Builder.this;
      }

    } // FractionMapBuilder

  } // Builder

  /// 流体の熱力学的温度 (K).
  private final double temperature;

  /// 流体の絶対圧力 (Pa).
  private final double pressure;

  /// 流体成分の質量分率 (-) の不変マップ.
  private final Map<ComponentKey, Double> fractionMap;

  /// モニタの不変マップ.
  private final Map<String, Monitor> monitorMap;

  /// 流入境界条件のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private InletPort(Builder builder) {
    this.temperature = builder.temperature;
    this.pressure = builder.pressure;
    this.fractionMap = Maps.immutableEnumMap(builder.fractionMap);
    this.monitorMap = ImmutableMap.copyOf(builder.monitorMap);

    LOGGER.debug("temperature = {}", this.temperature);
    LOGGER.debug("pressure = {}", this.pressure);
    LOGGER.debug("fractionMap = {}", this.fractionMap);
    LOGGER.debug("monitorMap = {}", this.monitorMap);
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

  /// モニタの不変マップを返す.
  ///
  /// @return モニタの不変マップ.
  public Map<String, Monitor> monitorMap() {
    return this.monitorMap;
  }

  void set(PortCell cell) {
    cell.setTemperature(this.temperature);
    cell.setPressure(this.pressure);
    cell.componentMap().forEach(
        (componentKey, componentValue) -> componentValue.setFraction(
            this.fractionMap.getOrDefault(componentKey, 0.)));
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    InletPort that = (InletPort) o;

    return Double.compare(this.temperature, that.temperature) == 0
        && Double.compare(this.pressure, that.pressure) == 0
        && this.fractionMap.equals(that.fractionMap)
        && this.monitorMap.equals(that.monitorMap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.temperature,
        this.pressure,
        this.fractionMap,
        this.monitorMap);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("temperature", this.temperature)
        .add("pressure", this.pressure)
        .add("fractionMap", this.fractionMap)
        .add("monitorMap", this.monitorMap)
        .toString();
  }

}
