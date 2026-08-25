/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

/// 境界モデル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
final class PortModel {

  /// セル.
  private final PortCell cell;

  /// フェイス.
  ///
  /// 境界外から中への向きを正とする.
  private final PortFace face;

  /// 境界モデルのインスタンスを生成する.
  ///
  /// @param composition 組成.
  PortModel(Composition composition) {
    this.cell = new PortCell(composition);
    this.face = new PortFace(composition);
  }

  /// セルを返す.
  ///
  /// @return セル.
  PortCell cell() {
    return this.cell;
  }

  /// フェイスを返す.
  ///
  /// 境界外から中への向きを正とする.
  ///
  /// @return フェイス.
  PortFace face() {
    return this.face;
  }

}
