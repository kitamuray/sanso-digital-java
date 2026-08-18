/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

/// 吸着筒の形状.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public abstract class ColumnShape {

  // クラス（インターフェース）を public,
  // インスタンス・メソッドを package-private とするために,
  // インターフェースではなく抽象クラスとした.

  /// 吸着筒の形状のインスタンスを生成する.
  ColumnShape() {}

  /// 指定された座標区間における流体領域の体積を返す.
  ///
  /// @param fromX 座標区間の始点 (m).
  /// @param toX 座標区間の終点 (m).
  /// @return 指定された座標区間における流体領域の体積 (m<sup>3</sup>).
  abstract double fluidVolume(double fromX, double toX);

  /// 指定された座標区間における容器領域の体積を返す.
  ///
  /// @param fromX 座標区間の始点 (m).
  /// @param toX 座標区間の終点 (m).
  /// @return 指定された座標区間における容器領域の体積 (m<sup>3</sup>).
  abstract double shellVolume(double fromX, double toX);

  /// 指定された座標区間における容器内面の面積を返す.
  ///
  /// @param fromX 座標区間の始点 (m).
  /// @param toX 座標区間の終点 (m).
  /// @return 指定された座標区間における容器内面の面積 (m<sup>2</sup>).
  abstract double insideArea(double fromX, double toX);

  /// 指定された座標区間における容器外面の面積を返す.
  ///
  /// @param fromX 座標区間の始点 (m).
  /// @param toX 座標区間の終点 (m).
  /// @return 指定された座標区間における容器外面の面積 (m<sup>2</sup>).
  abstract double outsideArea(double fromX, double toX);

  /// 指定された座標における流体領域の断面積を返す.
  ///
  /// @param coordinate 座標 (m).
  /// @return 指定された座標における流体領域の断面積 (m<sup>2</sup>).
  abstract double fluidArea(double coordinate);

  /// 指定された座標における容器領域の断面積を返す.
  ///
  /// @param coordinate 座標 (m).
  /// @return 指定された座標における容器領域の断面積 (m<sup>2</sup>).
  abstract double shellArea(double coordinate);

}
