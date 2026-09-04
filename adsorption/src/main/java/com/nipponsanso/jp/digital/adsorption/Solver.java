/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import tools.jackson.databind.ObjectMapper;

/// ソルバ.
///
/// @param <S> ソルバの型.
/// @since 1.1
/// @author KITAMURA Yuichiro
abstract class Solver<S extends Solver<S>> {

  /// JSON オブジェクト・マッパ.
  static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  /// 繰り返し計算回数の上限のデフォルト値.
  public static final int MAX_ITERATION = 1000;

  /// 成分のキー.
  private ComponentKey componentKey;

  /// 繰り返し計算回数の上限.
  private int maxIteration = MAX_ITERATION;

  /// ソルバのインスタンスを生成する.
  Solver() {}

  /// ソルバのインスタンスを返す.
  ///
  /// @return ソルバのインスタンス.
  abstract S self();

  /// 成分のキーを設定する.
  ///
  /// @param componentKey 成分のキー.
  /// @throws NullPointerException `componentKey` が `null` の場合.
  public final S componentKey(ComponentKey componentKey) {
    this.componentKey = componentKey;
    return this.self();
  }

  /// 成分のキーを返す.
  ///
  /// @return 成分のキー.
  final ComponentKey componentKey() {
    return this.componentKey;
  }

  /// 繰り返し計算回数の上限を設定する.
  ///
  /// @param maxIteration 繰り返し計算回数の上限.
  /// @throws IllegalArgumentException `maxIteration` が 0 以下の場合 (`maxIteration <= 0`).
  public final S maxIteration(int maxIteration) {
    Preconditions.checkArgument(maxIteration > 0);
    this.maxIteration = maxIteration;
    return this.self();
  }

  /// 繰り返し計算回数の上限を返す.
  ///
  /// @return 繰り返し計算回数の上限.
  final int maxIteration() {
    return this.maxIteration;
  }

}
