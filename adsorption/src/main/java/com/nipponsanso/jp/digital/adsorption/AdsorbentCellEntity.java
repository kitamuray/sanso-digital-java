/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.Objects;

/// 吸着剤セルの永続化クラスのスケルトン実装.
///
/// @param <P> 前ステップ値の永続化クラスの型.
/// @param <C> 成分値の永続化クラスの型.
/// @param <Q> 成分値の前ステップ値の永続化クラスの型.
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class AdsorbentCellEntity<
    P extends AdsorbentCellEntity.Previous,
    C extends AdsorbentCellEntity.ComponentValue<Q>,
    Q extends AdsorbentCellEntity.ComponentValue.Previous> extends ShellCellEntity<P, C, Q> {

  /// 吸着剤の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  private double adsorbentSpecificHeat = Double.MIN_VALUE;

  /// 吸着剤セルのインスタンスを生成する.
  ///
  /// @param previous 前ステップ値の永続化オブジェクトを生成するオペレーション.
  AdsorbentCellEntity(P previous) {
    super(previous);
  }

  /// 吸着剤の比熱を返す.
  ///
  /// @return 吸着剤の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  public double getAdsorbentSpecificHeat() {
    return this.adsorbentSpecificHeat;
  }

  /// 吸着剤の比熱を設定する.
  ///
  /// @param adsorbentSpecificHeat 吸着剤の新たな比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  /// @throws IllegalArgumentException `adsorbentSpecificHeat` が 0 以下の場合
  ///     (`adsorbentSpecificHeat <= 0`).
  public void setAdsorbentSpecificHeat(double adsorbentSpecificHeat) {
    Preconditions.checkArgument(adsorbentSpecificHeat > 0.);

    this.adsorbentSpecificHeat = adsorbentSpecificHeat;
  }

  @Override
  public boolean equals(Object o) {
    if (!super.equals(o)) {
      return false;
    }

    AdsorbentCellEntity<?, ?, ?> that = (AdsorbentCellEntity<?, ?, ?>) o;

    return Double.compare(this.adsorbentSpecificHeat, that.adsorbentSpecificHeat) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.adsorbentSpecificHeat);
  }

  /// 吸着剤セルの前ステップ値の永続化クラス.
  abstract static class Previous extends ShellCellEntity.Previous {

    /// 吸着剤の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    private double adsorbentSpecificHeat = Double.MIN_VALUE;

    /// 吸着剤セルの前ステップ値のインスタンスを生成する.
    Previous() {}

    /// 吸着剤の比熱を返す.
    ///
    /// @return 吸着剤の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    public double getAdsorbentSpecificHeat() {
      return this.adsorbentSpecificHeat;
    }

    /// 吸着剤の比熱を設定する.
    ///
    /// @param adsorbentSpecificHeat 吸着剤の新たな比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    /// @throws IllegalArgumentException `adsorbentSpecificHeat` が 0 以下の場合
    ///     (`adsorbentSpecificHeat <= 0`).
    public void setAdsorbentSpecificHeat(double adsorbentSpecificHeat) {
      Preconditions.checkArgument(adsorbentSpecificHeat > 0.);

      this.adsorbentSpecificHeat = adsorbentSpecificHeat;
    }

    @Override
    public boolean equals(Object o) {
      if (!super.equals(o)) {
        return false;
      }

      Previous that = (Previous) o;

      return Double.compare(this.adsorbentSpecificHeat, that.adsorbentSpecificHeat) == 0;
    }

    @Override
    public int hashCode() {
      return Objects.hash(
          super.hashCode(),
          this.adsorbentSpecificHeat);
    }

  } // Previous

  /// 吸着剤セルの成分値.
  ///
  /// @param <Q> 前ステップ値の永続化クラスの型.
  abstract static class ComponentValue<Q extends ComponentValue.Previous>
      extends ShellCellEntity.ComponentValue<Q> {

    /// モル吸着量 (mol kg<sup>-1</sup>).
    private double molarQuantity = Double.MIN_VALUE;

    /// モル定積比熱 (J kg<sup>-1</sup> mol<sup>-1</sup>).
    private double molarSpecificConstantVolumeSpecificHeat = Double.MIN_VALUE;

    /// 吸着剤セルの成分値のインスタンスを生成する.
    ///
    /// @param previous 前ステップ値の永続化オブジェクト.
    ComponentValue(Q previous) {
      super(previous);
    }

    /// モル吸着量を返す.
    ///
    /// @return モル吸着量 (mol kg<sup>-1</sup>).
    public double getMolarQuantity() {
      return this.molarQuantity;
    }

    /// モル吸着量を設定する.
    ///
    /// @param molarQuantity 新たなモル吸着量 (mol kg<sup>-1</sup>).
    /// @throws IllegalArgumentException `molarQuantity` が 0 以下の場合 (`molarQuantity <= 0`).
    public void setMolarQuantity(double molarQuantity) {
      Preconditions.checkArgument(molarQuantity > 0.);

      this.molarQuantity = molarQuantity;
    }

    /// モル定積比熱を返す.
    ///
    /// @return モル定積比熱 (J mol<sup>-1</sup>).
    public double getMolarSpecificConstantVolumeSpecificHeat() {
      return this.molarSpecificConstantVolumeSpecificHeat;
    }

    /// モル定積比熱を設定する.
    ///
    /// @param molarSpecificConstantVolumeSpecificHeat 新たなモル定積比熱 (J mol<sup>-1</sup>).
    /// @throws IllegalArgumentException `molarSpecificConstantVolumeSpecificHeat` が 0 以下の場合
    ///     (`molarSpecificConstantVolumeSpecificHeat <= 0`).
    public void setMolarSpecificConstantVolumeSpecificHeat(
        double molarSpecificConstantVolumeSpecificHeat) {
      Preconditions.checkArgument(molarSpecificConstantVolumeSpecificHeat > 0.);

      this.molarSpecificConstantVolumeSpecificHeat = molarSpecificConstantVolumeSpecificHeat;
    }

    @Override
    public boolean equals(Object o) {
      if (!super.equals(o)) {
        return false;
      }

      ComponentValue<?> that = (ComponentValue<?>) o;

      return Double.compare(this.molarQuantity, that.molarQuantity) == 0
          && Double.compare(
              this.molarSpecificConstantVolumeSpecificHeat,
              that.molarSpecificConstantVolumeSpecificHeat) == 0;
    }

    @Override
    public int hashCode() {
      return Objects.hash(
          super.hashCode(),
          this.molarQuantity,
          this.molarSpecificConstantVolumeSpecificHeat);
    }

    /// 吸着剤セルの成分値の前ステップ値の永続化クラス.
    abstract static class Previous extends ShellCellEntity.ComponentValue.Previous {

      /// モル吸着量 (mol kg<sup>-1</sup>).
      private double molarQuantity = Double.MIN_VALUE;

      /// モル定積比熱 (J kg<sup>-1</sup> mol<sup>-1</sup>).
      private double molarSpecificConstantVolumeSpecificHeat = Double.MIN_VALUE;

      /// 吸着剤セルの成分値の前ステップ値の永続化クラスのインスタンスを生成する.
      Previous() {}

      /// モル吸着量を返す.
      ///
      /// @return モル吸着量 (mol kg<sup>-1</sup>).
      public double getMolarQuantity() {
        return this.molarQuantity;
      }

      /// モル吸着量を設定する.
      ///
      /// @param molarQuantity 新たなモル吸着量 (mol kg<sup>-1</sup>).
      /// @throws IllegalArgumentException `molarQuantity` が 0 以下の場合 (`molarQuantity <= 0`).
      public void setMolarQuantity(double molarQuantity) {
        Preconditions.checkArgument(molarQuantity > 0.);

        this.molarQuantity = molarQuantity;
      }

      /// モル定積比熱を返す.
      ///
      /// @return モル定積比熱 (J mol<sup>-1</sup>).
      public double getMolarSpecificConstantVolumeSpecificHeat() {
        return this.molarSpecificConstantVolumeSpecificHeat;
      }

      /// モル定積比熱を設定する.
      ///
      /// @param molarSpecificConstantVolumeSpecificHeat 新たなモル定積比熱 (J mol<sup>-1</sup>).
      /// @throws IllegalArgumentException `molarSpecificConstantVolumeSpecificHeat` が 0 以下の場合
      ///     (`molarSpecificConstantVolumeSpecificHeat <= 0`).
      public void setMolarSpecificConstantVolumeSpecificHeat(
          double molarSpecificConstantVolumeSpecificHeat) {
        Preconditions.checkArgument(molarSpecificConstantVolumeSpecificHeat > 0.);

        this.molarSpecificConstantVolumeSpecificHeat = molarSpecificConstantVolumeSpecificHeat;
      }

      @Override
      public boolean equals(Object o) {
        if (!super.equals(o)) {
          return false;
        }

        Previous that = (Previous) o;

        return Double.compare(this.molarQuantity, that.molarQuantity) == 0
            && Double.compare(
                this.molarSpecificConstantVolumeSpecificHeat,
                that.molarSpecificConstantVolumeSpecificHeat) == 0;
      }

      @Override
      public int hashCode() {
        return Objects.hash(
            super.hashCode(),
            this.molarQuantity,
            this.molarSpecificConstantVolumeSpecificHeat);
      }

    } // ComponentValue.Previous

  } // ComponentValue

}
