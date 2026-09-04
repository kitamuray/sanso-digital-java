/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

/// 流体モデル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class FluidModel {

  /// 組成.
  private final Composition composition;

  /// 流体モデルのインスタンスを生成する.
  ///
  /// @param composition 組成.
  FluidModel(Composition composition) {
    this.composition = composition;
  }

  /// 組成を返す.
  ///
  /// @return 組成.
  final Composition composition() {
    return this.composition;
  }

}
