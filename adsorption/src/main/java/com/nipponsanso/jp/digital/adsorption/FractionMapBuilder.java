/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.Map;

/// 質量分率のマップのビルダのスケルトン実装.
///
/// @param <B> 最上位のビルダの型.
/// @param <K> マップのキーの型.
/// @since 1.1
/// @author KITAMURA Yuichiro
abstract class FractionMapBuilder<B, K> extends MapBuilder<B, K, Double> {

  /// 質量分率のマップのビルダのインスタンスを生成する.
  ///
  /// @param key キー.
  /// @throws NullPointerException `key` が `null` の場合.
  FractionMapBuilder(K key) {
    super(key);
  }

  /// 質量分率を設定する.
  ///
  /// @param fraction 質量分率 (-).
  /// @return 最上位のビルダのインスタンス.
  /// @throws IllegalArgumentException `fraction` が 0 未満か, 既設定値総計との和が 1 を超えている場合.
  public B fraction(double fraction) {
    double sum = this.map().entrySet().stream()
        .filter(entry -> entry.getKey() != this.key())
        .map(Map.Entry::getValue)
        .mapToDouble(doubleValue -> doubleValue)
        .sum();

    Preconditions.checkArgument(0. <= fraction && (fraction + sum) <= 1.);

    this.map().put(this.key(), fraction);
    return this.self();
  }

} // FractionMapBuilder
