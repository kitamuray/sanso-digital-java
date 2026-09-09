/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital;

import com.google.common.base.Preconditions;
import java.util.Set;
import java.util.function.ToDoubleFunction;

/// Mason と Saxena の低圧下における多成分気体の熱伝導率混合則.
///
/// The Properties of Gases and Liquid, 6th edition, p.563, eq.(11.6-1).
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class MasonSaxenaThermalConductivity {

  private MasonSaxenaThermalConductivity() {}

  /// 多成分気体の熱伝導率を返す.
  ///
  /// @param <K> 成分のキーの型.
  /// @param componentKeySet 成分のキーのセット.
  /// @param molecularWeightFunction 成分のキーを引数に取り分子量を返す関数.
  /// @param molarFractionFunction 成分のキーを引数に取りモル分率 (-) を返す関数.
  /// @param viscosityFunction 成分のキーを引数に取り純成分気体の粘性係数 (Pa s) を返す関数.
  /// @param thermalConductivityFunction 成分のキーを引数に取り純成分気体の熱伝導率
  ///     (W m<sup>-1</sup> K<sup>-1</sup>) を返す関数.
  /// @return 多成分気体の熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>).
  /// @throws NullPointerException `componentKeySet` またはその要素,
  ///     `molecularWeightFunction`, `molarFractionFunction`, `viscosityFunction`,
  ///     `thermalConductivityFunction` のいずれかが `null` の場合.
  /// @throws IllegalArgumentException `componentKeySet` が空の場合.
  ///     `molecularWeightFunction` が返す値が 0 以下の場合.
  ///     `molarFractionFunction` が返す値が 0 未満か 1 を超えている場合.
  public static <K extends Enum<K>> double calculate(
      Set<K> componentKeySet,
      ToDoubleFunction<K> molecularWeightFunction,
      ToDoubleFunction<K> molarFractionFunction,
      ToDoubleFunction<K> viscosityFunction,
      ToDoubleFunction<K> thermalConductivityFunction) {
    Preconditions.checkNotNull(componentKeySet).forEach(Preconditions::checkNotNull);
    Preconditions.checkNotNull(viscosityFunction);
    Preconditions.checkNotNull(thermalConductivityFunction);

    Preconditions.checkArgument(!componentKeySet.isEmpty());

    return componentKeySet.stream()
        .mapToDouble(keyI -> {
          double molecularWeightI     = molecularWeightFunction.applyAsDouble(keyI);
          double molarFractionI       = molarFractionFunction.applyAsDouble(keyI);
          double viscosityI           = viscosityFunction.applyAsDouble(keyI);
          double thermalConductivityI = thermalConductivityFunction.applyAsDouble(keyI);

          Preconditions.checkArgument(molecularWeightI > 0.);
          Preconditions.checkArgument(0. <= molarFractionI && molarFractionI <= 1.);
          Preconditions.checkArgument(viscosityI > 0.);
          Preconditions.checkArgument(thermalConductivityI > 0.);

          return molarFractionI * thermalConductivityI / componentKeySet.stream()
              .mapToDouble(keyJ -> {
                double molecularWeightJ = molecularWeightFunction.applyAsDouble(keyJ);
                double molarFractionJ   = molarFractionFunction.applyAsDouble(keyJ);
                double viscosityJ       = viscosityFunction.applyAsDouble(keyJ);

                // *J の検査は省略（*I と同じ値が返されると期待）

                double n = 1.
                         + Math.sqrt(viscosityI / viscosityJ)
                         * Math.sqrt(Math.sqrt(molecularWeightJ / molecularWeightI));
                double d = 8. * (1. + molecularWeightI / molecularWeightJ);
                return molarFractionJ * n * n / Math.sqrt(d);
              })
              .sum();
        })
        .sum();
  }

}
