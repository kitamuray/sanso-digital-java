/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital;

import com.google.common.base.Preconditions;
import java.util.Set;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;

/// Blanc による多成分気体の物質拡散係混合則.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class BlancDiffusivity {

  private BlancDiffusivity() {}

  /// 多成分気体の物質拡散係数を返す.
  ///
  /// @param <K> 成分のキーの型.
  /// @param componentKeySet 成分のキーのセット.
  /// @param componentKey 成分のキー.
  /// @param molarFractionFunction 成分のキーを引数に取りモル分率 (-) を返す関数.
  /// @param diffusivityFunction 成分のキーのペアを引数に取り二成分気体の物質拡散係数
  ///     (m<sup>2</sup> s<sup>-1</sup>) を返す関数.
  /// @return 指定された成分における物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
  /// @throws NullPointerException `componentKeySet` またはその要素, `componentKey`,
  ///     `molarFractionFunction`, `diffusivityFunction` のいずれかが `null` の場合.
  /// @throws IllegalArgumentException `componentKeySet` の長さが 2 未満の場合.
  ///     `componentKey` が `componentKeySet` に含まれていない場合.
  ///     `molarFractionFunction` が返す値が 0 未満か 1 を超えている場合.
  ///     `diffusivityFunction` が返す値が 0 以下の場合.
  public static <K extends Enum<K>> double calculate(
      Set<K> componentKeySet,
      K componentKey,
      ToDoubleFunction<K> molarFractionFunction,
      ToDoubleBiFunction<K, K> diffusivityFunction) {
    Preconditions.checkNotNull(componentKeySet).forEach(Preconditions::checkNotNull);
    Preconditions.checkNotNull(componentKey);
    Preconditions.checkNotNull(molarFractionFunction);
    Preconditions.checkNotNull(diffusivityFunction);

    Preconditions.checkArgument(componentKeySet.size() >= 2);
    Preconditions.checkArgument(componentKeySet.contains(componentKey));

    final double molarFractionI = molarFractionFunction.applyAsDouble(componentKey);

    Preconditions.checkArgument(0. <= molarFractionI && molarFractionI <= 1.);

    double d = componentKeySet.stream()
        .filter(keyJ -> keyJ != componentKey)
        .mapToDouble(keyJ -> {
          double molarFractionJ = molarFractionFunction.applyAsDouble(keyJ);
          double diffusivityIj  = diffusivityFunction.applyAsDouble(componentKey, keyJ);

          Preconditions.checkArgument(diffusivityIj > 0.);
          Preconditions.checkArgument(0. <= molarFractionJ && molarFractionJ <= 1.);

          return molarFractionJ / diffusivityIj;
        })
        .sum();

    return Math.max((1. - molarFractionI) / Math.max(d, Double.MIN_VALUE), Double.MIN_VALUE);
  }

}
