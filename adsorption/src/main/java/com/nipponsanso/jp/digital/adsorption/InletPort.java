/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.util.EnumMap;
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
  public static final class Builder extends Port.Builder<Builder> {

    /// 流体の熱力学的温度 (K).
    private double temperature = 298.15;

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

    /// 流体成分のキーを設定する.
    ///
    /// @param componentKey 流体成分のキー.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `componentKey` が `null` の場合.
    public FractionMapBuilder<Builder> componentKey(ComponentKey componentKey) {
      return new FractionMapBuilder<>(componentKey) {
        @Override
        Map<ComponentKey, Double> fractionMap() {
          return Builder.this.fractionMap;
        }

        @Override
        Builder self() {
          return Builder.this;
        }
      };
    }

    @Override
    Builder self() {
      return this;
    }

    /// 流入境界条件のインスタンスを生成して返す.
    ///
    /// @return 流入境界条件のインスタンス.
    @Override
    public InletPort build() {
      return new InletPort(this);
    }

  } // Builder

  /// 流体の熱力学的温度 (K).
  private final double temperature;

  /// 流体成分の質量分率 (-) の不変マップ.
  private final Map<ComponentKey, Double> fractionMap;

  /// 流入境界条件のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private InletPort(Builder builder) {
    super(builder);

    this.temperature = builder.temperature;
    this.fractionMap = Maps.immutableEnumMap(builder.fractionMap);

    LOGGER.debug("temperature = {}", this.temperature);
    LOGGER.debug("fractionMap = {}", this.fractionMap);
  }

  /// 流体の熱力学的温度を返す.
  ///
  /// @return 流体の熱力学的温度 (K).
  public double temperature() {
    return this.temperature;
  }

  /// 流体成分の質量分率の不変マップを返す.
  ///
  /// @return 流体成分の質量分率 (-) の不変マップ.
  public Map<ComponentKey, Double> fractionMap() {
    return this.fractionMap;
  }

  @Override
  void set(PortCell cell) {
    cell.setTemperature(this.temperature);
    cell.setPressure(this.pressure());
    cell.componentMap().forEach(
        (componentKey, componentValue) -> componentValue.setFraction(
            this.fractionMap.getOrDefault(componentKey, 0.)));
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    InletPort that = (InletPort) o;

    return Double.compare(this.temperature, that.temperature) == 0
        && this.fractionMap.equals(that.fractionMap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        super.hashCode(),
        this.temperature,
        this.fractionMap);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("temperature", this.temperature)
        .add("pressure", this.pressure())
        .add("fractionMap", this.fractionMap)
        .add("monitorMap", this.monitorMap())
        .toString();
  }

}
