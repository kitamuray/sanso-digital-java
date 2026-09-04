/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/// 流体の開始条件.
///
/// @since 1.1
/// @author KITAMURA Yuichiro
abstract class FluidStart {

  private static final Logger LOGGER = LogManager.getLogger(FluidStart.class);

  /// 流体の開始条件のビルダ.
  ///
  /// @param <B> ビルダの型.
  abstract static class Builder<B extends Builder<B>> {

    /// 流体の熱力学的温度 (K).
    private double temperature = 298.15;

    /// 流体の絶対圧力 (Pa).
    private double pressure = 101325.;

    /// 成分のキー.
    private ComponentKey componentKey;

    /// 流体成分の質量分率 (-) のマップ.
    private final Map<ComponentKey, Double> fractionMap = new EnumMap<>(ComponentKey.class);

    /// 流体の開始条件のビルダのインスタンスを生成する.
    Builder() {}

    /// 流体の絶対温度を設定する.
    ///
    /// @param temperature 流体の絶対温度 (K).
    /// @return ビルダのインスタンス.
    /// @throws IllegalArgumentException `temperature` が 0 以下の場合 (`temperature <= 0`).
    public final B temperature(double temperature) {
      Preconditions.checkArgument(temperature > 0.);

      this.temperature = temperature;
      return this.self();
    }

    /// 流体の絶対圧力を設定する.
    ///
    /// @param pressure 流体の絶対圧力 (Pa).
    /// @return ビルダのインスタンス.
    /// @throws IllegalArgumentException `pressure` が 0 以下の場合 (`pressure <= 0`).
    public final B pressure(double pressure) {
      Preconditions.checkArgument(pressure > 0.);

      this.pressure = pressure;
      return this.self();
    }

    /// 流体成分の質量分率のマップを設定するビルダのインスタンスを生成して返す.
    ///
    /// @param componentKey 成分のキー.
    /// @throws NullPointerException `componentKey` が `null` の場合.
    public final FractionMapBuilder componentKey(ComponentKey componentKey) {
      return new FractionMapBuilder(componentKey);
    }

    /// ビルダのインスタンスを返す.
    ///
    /// @return ビルダのインスタンス.
    abstract B self();

    /// 流体の開始条件のインスタンスを生成して返す.
    ///
    /// @return 流体の開始条件のインスタンス.
    public abstract FluidStart build();

    /// 流体成分の質量分率のマップを設定するビルダ.
    public final class FractionMapBuilder extends AbstractFractionMapBuilder<B> {

      /// 流体成分の質量分率のマップを設定するビルダのインスタンスを生成する.
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
      B self() {
        return Builder.this.self();
      }

    } // FractionMapBuilder

  } // Builder

  /// 流体の熱力学的温度 (K).
  private final double temperature;

  /// 流体の絶対圧力 (Pa).
  private final double pressure;

  /// 流体成分の質量分率 (-) の不変マップ.
  private final Map<ComponentKey, Double> fractionMap;

  /// 流体の開始条件のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  FluidStart(Builder<?> builder) {
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
  /// 流体成分の質量分率 (-) の不変マップ.
  public Map<ComponentKey, Double> fractionMap() {
    return this.fractionMap;
  }

  /// 指定されたセルへ初期条件を設定する.
  ///
  /// @param cell セル.
  void start(FluidCell cell) {
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

    FluidStart that = (FluidStart) o;

    return Double.compare(this.temperature, that.temperature) == 0
        && Double.compare(this.pressure, that.pressure) == 0
        && this.fractionMap.equals(that.fractionMap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.temperature,
        this.pressure,
        this.fractionMap);
  }

}
