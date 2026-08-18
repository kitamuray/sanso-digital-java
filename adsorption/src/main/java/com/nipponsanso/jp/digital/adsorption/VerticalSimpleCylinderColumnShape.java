/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/// 竪型単純円筒形状でモデル化された吸着筒の形状.
///
/// このクラスのインスタンスは不変（immutable）である.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class VerticalSimpleCylinderColumnShape extends ColumnShape {

  private static final Logger LOGGER =
      LogManager.getLogger(VerticalSimpleCylinderColumnShape.class);

  /// 竪型単純円筒形状でモデル化された吸着筒の形状のビルダ.
  public static final class Builder {

    /// 内径 (m).
    private double insideDiameter;

    /// 肉厚 (m).
    private double thickness;

    /// 竪型単純円筒形状でモデル化された吸着筒の形状のビルダのインスタンスを生成する.
    private Builder() {}

    /// 内径を設定する.
    ///
    /// @param insideDiameter 内径 (m).
    /// @return ビルダのインスタンス自身.
    /// @throws IllegalArgumentException `insideDiameter` が 0 以下の場合
    ///     (`insideDiameter <= 0`).
    public Builder insideDiameter(double insideDiameter) {
      Preconditions.checkArgument(insideDiameter > 0.);

      this.insideDiameter = insideDiameter;

      return this;
    }

    /// 肉厚を設定する.
    ///
    /// @param thickness 肉厚 (m).
    /// @return ビルダのインスタンス自身.
    /// @throws IllegalArgumentException `thickness` が 0 以下の場合 (`thickness <= 0`).
    public Builder thickness(double thickness) {
      Preconditions.checkArgument(thickness > 0.);

      this.thickness = thickness;

      return this;
    }

    /// 竪型単純円筒形状でモデル化された吸着筒の形状のインスタンスを生成して返す.
    ///
    /// @return 竪型単純円筒形状でモデル化された吸着筒の形状のインスタンス.
    /// @throws IllegalStateException ビルダの状態が不完全な場合.
    public VerticalSimpleCylinderColumnShape build() {
      Preconditions.checkState(this.insideDiameter > 0.);
      Preconditions.checkState(this.thickness > 0.);

      return new VerticalSimpleCylinderColumnShape(this);
    }

  } // Builder

  /// 内径 (m).
  private final double insideDiameter;

  /// 肉厚 (m).
  private final double thickness;

  /// 流体領域の断面積 (m<sup>2</sup>).
  private final double fluidArea;

  /// 容器領域の断面積 (m<sup>2</sup>).
  private final double shellArea;

  /// 容器内面の断面長さ (m).
  private final double insideLength;

  /// 容器外面の断面長さ (m).
  private final double outsideLength;

  /// 竪型単純円筒形状でモデル化された吸着筒の形状のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private VerticalSimpleCylinderColumnShape(Builder builder) {
    this.insideDiameter = builder.insideDiameter;
    this.thickness = builder.thickness;

    LOGGER.debug("insideDiameter = {} (m)", this.insideDiameter);
    LOGGER.debug("thickness = {} (m)", this.thickness);

    final double outsideDiameter = this.insideDiameter + 2. * this.thickness;

    this.fluidArea = 0.25 * Math.PI * this.insideDiameter * this.insideDiameter;
    this.shellArea =
        0.25 * Math.PI
            * (outsideDiameter * outsideDiameter - this.insideDiameter * this.insideDiameter);
    this.insideLength = Math.PI * this.insideDiameter;
    this.outsideLength = Math.PI * outsideDiameter;
  }

  /// ビルダのインスタンスを生成して返す.
  ///
  /// @return ビルダのインスタンス.
  public static Builder builder() {
    return new Builder();
  }

  /// 内径を返す.
  ///
  /// @return 内径 (m).
  public double insideDiameter() {
    return insideDiameter;
  }

  /// 肉厚を返す.
  ///
  /// @return 肉厚 (m).
  public double thickness() {
    return thickness;
  }

  /// {@inheritDoc}
  ///
  /// @param fromX {@inheritDoc}
  /// @param toX {@inheritDoc}
  /// @return {@inheritDoc}
  @Override
  double fluidVolume(double fromX, double toX) {
    return this.fluidArea * (toX - fromX);
  }

  /// {@inheritDoc}
  ///
  /// @param fromX {@inheritDoc}
  /// @param toX {@inheritDoc}
  /// @return {@inheritDoc}
  @Override
  double shellVolume(double fromX, double toX) {
    return this.shellArea * (toX - fromX);
  }

  /// {@inheritDoc}
  ///
  /// @param fromX {@inheritDoc}
  /// @param toX {@inheritDoc}
  /// @return {@inheritDoc}
  @Override
  double insideArea(double fromX, double toX) {
    return this.insideLength * (toX - fromX);
  }

  /// {@inheritDoc}
  ///
  /// @param fromX {@inheritDoc}
  /// @param toX {@inheritDoc}
  /// @return {@inheritDoc}
  @Override
  double outsideArea(double fromX, double toX) {
    return this.outsideLength * (toX - fromX);
  }

  /// {@inheritDoc}
  ///
  /// @param coordinate {@inheritDoc}
  /// @return {@inheritDoc}
  @Override
  double fluidArea(double coordinate) {
    return this.fluidArea;
  }

  /// {@inheritDoc}
  ///
  /// @param coordinate {@inheritDoc}
  /// @return {@inheritDoc}
  @Override
  double shellArea(double coordinate) {
    return this.shellArea;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    VerticalSimpleCylinderColumnShape that = (VerticalSimpleCylinderColumnShape) o;

    return Double.compare(this.insideDiameter, that.insideDiameter) == 0
        && Double.compare(this.thickness, that.thickness) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.insideDiameter,
        this.thickness);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("insideDiameter", this.insideDiameter)
        .add("thickness", this.thickness)
        .toString();
  }

}
