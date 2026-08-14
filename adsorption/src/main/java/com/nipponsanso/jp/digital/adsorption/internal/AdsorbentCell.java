/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption.internal;

import com.google.common.base.Preconditions;
import com.nipponsanso.jp.digital.adsorption.Adsorbent;
import com.nipponsanso.jp.digital.adsorption.ComponentKey;
import com.nipponsanso.jp.digital.adsorption.Shell;
import java.util.Map;

/// 吸着剤を含むセル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public abstract class AdsorbentCell extends ShellCell {

  /// 吸着剤.
  private final Adsorbent adsorbent;

  /// 吸着剤の比熱の位置.
  private static final int ADSORBENT_SPECIFIC_HEAT = ShellCell.SIZE;

  /// 変数の数.
  public static final int SIZE = ADSORBENT_SPECIFIC_HEAT + 1;

  /// 吸着剤を含むセルのインスタンスを生成する.
  ///
  /// @param adsorbent 吸着剤.
  /// @param shell 容器.
  public AdsorbentCell(Adsorbent adsorbent, Shell shell) {
    super(shell);
    this.adsorbent = adsorbent;
  }

  /// 粒子径を返す.
  ///
  /// @return diameter 粒子径 (m).
  public double particleDiameter() {
    return this.adsorbent.diameter();
  }

  /// 空隙率を返す.
  ///
  ///@return 空隙率 (-).
  public double voidFraction() {
    return this.adsorbent.voidFraction();
  }

  /// 嵩密度を返す.
  ///
  /// @return 嵩密度 (kg m<sup>-1</sup>)
  public double bulkDensity() {
    return this.adsorbent.bulkDensity();
  }

  /// 吸着剤の比熱を返す.
  ///
  /// @return 吸着剤の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  public double getAdsorbentSpecificHeat() {
    return this.doubleArray()[this.offset() + ADSORBENT_SPECIFIC_HEAT];
  }

  /// 吸着剤の比熱を変更する.
  ///
  /// @param adsorbentSpecificHeat 吸着剤の新たな比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  /// @throws IllegalArgumentException `adsorbentSpecificHeat` が 0 以下の場合
  ///     (`adsorbentSpecificHeat <= 0`).
  public void setAdsorbentSpecificHeat(double adsorbentSpecificHeat) {
    Preconditions.checkArgument(adsorbentSpecificHeat > 0.);
    this.doubleArray()[this.offset() + ADSORBENT_SPECIFIC_HEAT] = adsorbentSpecificHeat;
  }

  /// {@inheritDoc}
  ///
  /// @return {@inheritDoc}
  @Override
  public abstract Previous previous();

  /// {@inheritDoc}
  ///
  /// @return {@inheritDoc}
  @Override
  public abstract Map<ComponentKey, ? extends ComponentValue> componentMap();

  /// 吸着剤を含むセルの前ステップ値.
  public abstract static class Previous extends ShellCell.Previous {

    /// 吸着剤の比熱の位置.
    private static final int ADSORBENT_SPECIFIC_HEAT = ShellCell.Previous.SIZE;

    /// 変数の数.
    public static final int SIZE = ADSORBENT_SPECIFIC_HEAT + 1;

    /// 吸着剤を含むセルの前ステップ値のインスタンスを生成する.
    public Previous() {}

    /// 吸着剤の比熱を返す.
    ///
    /// @return 吸着剤の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    public double getAdsorbentSpecificHeat() {
      return this.doubleArray()[this.offset() + ADSORBENT_SPECIFIC_HEAT];
    }

    /// 吸着剤の比熱を変更する.
    ///
    /// @param adsorbentSpecificHeat 吸着剤の新たな比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    /// @throws IllegalArgumentException `adsorbentSpecificHeat` が 0 以下の場合
    ///     (`adsorbentSpecificHeat <= 0`).
    public void setAdsorbentSpecificHeat(double adsorbentSpecificHeat) {
      Preconditions.checkArgument(adsorbentSpecificHeat > 0.);
      this.doubleArray()[this.offset() + ADSORBENT_SPECIFIC_HEAT] = adsorbentSpecificHeat;
    }

  } // Previous

  /// 吸着剤を含むセルの成分値.
  public abstract static class ComponentValue extends ShellCell.ComponentValue {

    /// 吸着質成分のモル吸着量の位置.
    private static final int MOLAR_QUANTITY = ShellCell.ComponentValue.SIZE;

    /// 吸着質成分のモル定積比熱の位置.
    private static final int MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT
        = MOLAR_QUANTITY + 1;

    /// 対象となる変数の数.
    public static int SIZE = MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT + 1;

    /// 吸着剤を含むセルの成分値のインスタンスを生成する.
    public ComponentValue() {}

    /// モル吸着量を返す.
    ///
    /// @return モル吸着量 (mol kg<sup>-1</sup>).
    public double getMolarQuantity() {
      return this.doubleArray()[this.offset() + MOLAR_QUANTITY];
    }

    /// モル吸着量を変更する.
    ///
    /// @param molarQuantity 新たなモル吸着量 (mol kg<sup>-1</sup>).
    /// @throws IllegalArgumentException `molarQuantity` が 0 以下の場合
    ///     (`molarQuantity <= 0`).
    public void setMolarQuantity(double molarQuantity) {
      Preconditions.checkArgument(molarQuantity > 0.);
      this.doubleArray()[this.offset() + MOLAR_QUANTITY] = molarQuantity;
    }

    /// モル定積比熱を返す.
    ///
    /// @return モル定積比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    public double getMolarSpecificConstantVolumeSpecificHeat() {
      return this.doubleArray()[this.offset() + MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT];
    }

    /// モル定積比熱を変更する.
    ///
    /// @param molarSpecificConstantVolumeSpecificHeat 新たなモル定積比熱
    ///                                                (J kg<sup>-1</sup> K<sup>-1</sup>>).
    /// @throws IllegalArgumentException `molarSpecificConstantVolumeSpecificHeat`
    ///     が 0 以下の場合 (`molarSpecificConstantVolumeSpecificHeat <= 0`).
    public void setMolarSpecificConstantVolumeSpecificHeat(
        double molarSpecificConstantVolumeSpecificHeat) {
      Preconditions.checkArgument(molarSpecificConstantVolumeSpecificHeat > 0.);
      this.doubleArray()[this.offset() + MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT]
          = molarSpecificConstantVolumeSpecificHeat;
    }

    /// {@inheritDoc}
    ///
    /// @return {@inheritDoc}
    @Override
    public abstract Previous previous();

    /// 吸着剤を含むセルの成分値の前ステップ値.
    public abstract static class Previous extends ShellCell.ComponentValue.Previous {

      /// 吸着質成分のモル吸着量の位置.
      private static final int MOLAR_QUANTITY = ShellCell.ComponentValue.Previous.SIZE;

      /// 吸着質成分のモル定積比熱の位置.
      private static final int MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT
          = MOLAR_QUANTITY + 1;

      /// 対象となる変数の数.
      public static int SIZE = MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT + 1;

      /// 吸着剤を含むセルの成分値の前ステップ値のインスタンスを生成する.
      public Previous() {}

      /// モル吸着量を返す.
      ///
      /// @return モル吸着量 (mol kg<sup>-1</sup>).
      public double getMolarQuantity() {
        return this.doubleArray()[this.offset() + MOLAR_QUANTITY];
      }

      /// モル吸着量を変更する.
      ///
      /// @param molarQuantity 新たなモル吸着量 (mol kg<sup>-1</sup>).
      /// @throws IllegalArgumentException `molarQuantity` が 0 以下の場合
      ///     (`molarQuantity <= 0`).
      public void setMolarQuantity(double molarQuantity) {
        Preconditions.checkArgument(molarQuantity > 0.);
        this.doubleArray()[this.offset() + MOLAR_QUANTITY] = molarQuantity;
      }

      /// モル定積比熱を返す.
      ///
      /// @return モル定積比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
      public double getMolarSpecificConstantVolumeSpecificHeat() {
        return this.doubleArray()[this.offset() + MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT];
      }

      /// モル定積比熱を変更する.
      ///
      /// @param molarSpecificConstantVolumeSpecificHeat 新たなモル定積比熱
      ///                                                (J kg<sup>-1</sup> K<sup>-1</sup>>).
      /// @throws IllegalArgumentException `molarSpecificConstantVolumeSpecificHeat`
      ///     が 0 以下の場合 (`molarSpecificConstantVolumeSpecificHeat <= 0`).
      public void setMolarSpecificConstantVolumeSpecificHeat(
          double molarSpecificConstantVolumeSpecificHeat) {
        Preconditions.checkArgument(molarSpecificConstantVolumeSpecificHeat > 0.);
        this.doubleArray()[this.offset() + MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT]
            = molarSpecificConstantVolumeSpecificHeat;
      }

    } // Previous

  } // ComponentValue

}
