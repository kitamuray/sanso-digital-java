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

/// 開始条件の抽象実装.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class Start {

  /// 開始条件のビルダ.
  ///
  /// @param <B> ビルダの型.
  abstract static class Builder<B extends Builder<B>> {

    /// 流体の熱力学的温度 (K).
    private double temperature;

    /// 流体の絶対圧力 (Pa).
    private double pressure;

    /// 流体成分の質量分率 (-) のマップ.
    ///
    /// 保有する値は健全性が保障されている.
    private final Map<ComponentKey, Double> fractionMap = new EnumMap<>(ComponentKey.class);

    /// ビルダのインスタンスを生成する.
    Builder() {}

    /// 流体の熱力学的温度を設定する.
    ///
    /// @param temperature 流体の熱力学的温度 (K).
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

    /// 流体成分のキーを指定する.
    ///
    /// @param componentKey 流体成分のキー.
    /// @throws NullPointerException `componentKey` が `null` の場合.
    public final FractionMapBuilder<B> componentKey(ComponentKey componentKey) {
      return new FractionMapBuilder<>(componentKey) {

        @Override
        Map<ComponentKey, Double> fractionMap() {
          return Builder.this.fractionMap;
        }

        @Override
        B self() {
          return Builder.this.self();
        }

      };
    }

    /// ビルダのインスタンスを返す.
    ///
    /// @return ビルダのインスタンス.
    abstract B self();

    /// 開始条件のインスタンスを生成して返す.
    ///
    /// @return 開始条件のインスタンス.
    public abstract Start build();

  } // Builder

  /// 流体の熱力学的温度 (K).
  private final double temperature;

  /// 流体の絶対圧力 (Pa).
  private final double pressure;

  /// 流体成分の質量分率 (-) のマップ.
  private final Map<ComponentKey, Double> fractionMap;

  /// 開始条件のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  /// @throws IllegalStateException `builder` が不完全な場合.
  Start(Builder<?> builder) {
    Preconditions.checkState(builder.temperature > 0.);
    Preconditions.checkState(builder.pressure > 0.);

    this.temperature = builder.temperature;
    this.pressure = builder.pressure;
    this.fractionMap = Maps.immutableEnumMap(builder.fractionMap);
  }

  /// 流体の熱力学的温度を返す.
  ///
  /// @return 流体の熱力学的温度 (K).
  public final double temperature() {
    return this.temperature;
  }

  /// 流体の絶対圧力を返す.
  ///
  /// @return 流体の絶対圧力 (Pa).
  public final double pressure() {
    return this.pressure;
  }

  /// 流体成分の質量分率の不変マップを返す.
  ///
  /// @return 流体成分の質量分率 (-) の不変マップ
  public final Map<ComponentKey, Double> fractionMap() {
    return this.fractionMap;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    Start that = (Start) o;
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
