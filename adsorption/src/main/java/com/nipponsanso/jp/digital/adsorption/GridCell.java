/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

/// 格子セル.
///
/// @since 1.1
/// @author KITAMURA Yuichiro
interface GridCell {

  /// 重心座標を返す.
  ///
  /// @return 重心座標 (m).
  double centroid();

  /// 長さを返す.
  ///
  /// @return 長さ (m).
  double length();

  /// 流体領域の体積を返す.
  ///
  /// @return 流体領域の体積 (m<sup>3</sup>).
  double fluidVolume();

  /// 容器領域の体積を返す.
  ///
  /// @return 容器領域の体積 (m<sup>3</sup>).
  double shellVolume();

  /// 容器内面の面積を返す.
  ///
  /// @return 容器内面の面積 (m<sup>2</sup>).
  double insideArea();

  /// 容器外面の面積を返す.
  ///
  /// @return 容器外面の面積 (m<sup>2</sup>).
  double outsideArea();

}
