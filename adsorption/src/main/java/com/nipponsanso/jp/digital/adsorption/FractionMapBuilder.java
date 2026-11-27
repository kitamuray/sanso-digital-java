/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.Map;

/// 質量分率のマップのビルダのスケルトン実装.
///
/// @param <B> ビルダの型.
/// @since 1.1
/// @author KITAMURA Yuichiro
public abstract class FractionMapBuilder<B> {

  /// 成分のキー.
  private final ComponentKey componentKey;

  /// 質量分率のマップのビルダのインスタンスを生成する.
  ///
  /// @param componentKey 成分のキー.
  /// @throws NullPointerException `componentKey` が `null` の場合.
  FractionMapBuilder(ComponentKey componentKey) {
    this.componentKey = Preconditions.checkNotNull(componentKey);
  }

  /// 質量分率の可変マップを返す.
  ///
  /// マップの値は常に健全性が保障される.
  ///
  /// @return 質量分率の可変マップ.
  abstract Map<ComponentKey, Double> fractionMap();

  /// ビルダのインスタンスを返す.
  ///
  /// @return ビルダのインスタンス.
  abstract B self();

  /// 他成分の質量分率の総計値を返す.
  ///
  /// @return 他成分の質量分率の総計値 (-).
  private double sumFraction() {
    return this.fractionMap().entrySet().stream()
        .filter(entry -> entry.getKey() != this.componentKey)
        .mapToDouble(Map.Entry::getValue)
        .sum();
  }

  /// 質量分率を設定する.
  ///
  /// @param fraction 質量分率 (-).
  /// @return ビルダのインスタンス.
  /// @throws IllegalArgumentException `fraction` が範囲外の場合.
  public final B fraction(double fraction) {
    Preconditions.checkArgument(0. <= fraction && fraction <= (1. - this.sumFraction()));
    this.fractionMap().put(this.componentKey, fraction);
    return this.self();
  }

}
