/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital;

import com.google.common.base.Preconditions;

///
/// レナード-ジョーンズ・ポテンシャル.
///
/// @param sigma 衝突直径 _&sigma;_ (&Aring;).
/// @param epsilonK ポテンシャルの深さ _&epsilon;_ / _k_ (K).
/// @since 1.0
/// @author KITAMURA Yuichiro
///
public record LennardJonesPotential(double sigma, double epsilonK) {

  ///
  /// レナード-ジョーンズ・ポテンシャルのインスタンスを生成する.
  ///
  /// @param sigma 衝突直径 _&sigma;_ (&Aring;).
  /// @param epsilonK ポテンシャルの深さ _&epsilon;_ / _k_ (K).
  /// @throws IllegalArgumentException `sigma` または `epsilonK` のいずれかが 0 以下の場合
  ///                                  (`sigma <= 0 || epsilonK`).
  ///
  public LennardJonesPotential {
    Preconditions.checkArgument(sigma > 0.);
    Preconditions.checkArgument(epsilonK > 0.);
  }

}
