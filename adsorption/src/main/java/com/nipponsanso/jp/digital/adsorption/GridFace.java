/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

interface GridFace {

  /// 座標を返す.
  ///
  /// @return 座標 (m).
  double coordinate();

  /// 流体領域の断面積を返す.
  ///
  /// @return 流体領域の断面積 (m<sup>2</sup>).
  double fluidArea();

  /// 容器領域の断面積を返す.
  ///
  /// @return 容器領域の断面積 (m<sup>2</sup>).
  double shellArea();

}
