/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.MoreObjects;

/// 吸着筒セルの永続化クラス.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class ColumnCellEntity extends AdsorbentCellEntity<
    ColumnCellEntity.Previous,
    ColumnCellEntity.ComponentValue,
    ColumnCellEntity.ComponentValue.Previous> {

  /// 吸着筒セルの永続化クラスのインスタンスを生成する.
  public ColumnCellEntity() {
    super(new Previous());
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("temperature", this.getTemperature())
        .add("pressure", this.getPressure())
        .add("density", this.getDensity())
        .add("specificEnthalpy", this.getSpecificEnthalpy())
        .add(
            "specificConstantPressureSpecificHeat",
            this.getSpecificConstantPressureSpecificHeat())
        .add("viscosity", this.getViscosity())
        .add("thermalConductivity", this.getThermalConductivity())
        .add("shellTemperature", this.getShellTemperature())
        .add("shellSpecificHeat", this.getShellSpecificHeat())
        .add("shellThermalConductivity", this.getShellThermalConductivity())
        .add("adsorbentSpecificHeat", this.getAdsorbentSpecificHeat())
        .add("previous", this.getPreviousEntity())
        .add("componentMap", this.getComponentEntityMap())
        .toString();
  }

  /// 吸着筒セルの前ステップ値の永続化クラス.
  public static final class Previous extends AdsorbentCellEntity.Previous {

    /// 吸着筒セルの前ステップ値の永続化クラスのインスタンスを生成する.
    public Previous() {}

    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this)
          .add("temperature", this.getTemperature())
          .add("density", this.getDensity())
          .add("specificEnthalpy", this.getSpecificEnthalpy())
          .add(
              "specificConstantPressureSpecificHeat",
              this.getSpecificConstantPressureSpecificHeat())
          .add("shellTemperature", this.getShellTemperature())
          .add("shellSpecificHeat", this.getShellSpecificHeat())
          .add("adsorbentSpecificHeat", this.getAdsorbentSpecificHeat())
          .toString();
    }

  } // Previous

  /// 吸着筒セルの成分値の永続化クラス.
  public static final class ComponentValue
      extends AdsorbentCellEntity.ComponentValue<ComponentValue.Previous> {

    /// 吸着筒セルの成分値の永続化クラスのインスタンスを生成する.
    public ComponentValue() {
      super(new Previous());
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this)
          .add("fraction", this.getFraction())
          .add("diffusivity", this.getDiffusivity())
          .add("molarQuantity", this.getMolarQuantity())
          .add(
              "molarSpecificConstantVolumeSpecificHeat",
              this.getMolarSpecificConstantVolumeSpecificHeat())
          .add("previous", this.getPreviousEntity())
          .toString();
    }

    /// 吸着筒セルの成分値の前ステップ値の永続化クラス.
    public static final class Previous extends AdsorbentCellEntity.ComponentValue.Previous {

      /// 吸着筒セルの成分値の前ステップ値の永続化クラスのインスタンスを生成する.
      public Previous() {}

      @Override
      public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("fraction", this.getFraction())
            .add("molarQuantity", this.getMolarQuantity())
            .add(
                "molarSpecificConstantVolumeSpecificHeat",
                this.getMolarSpecificConstantVolumeSpecificHeat())
            .toString();
      }

    } // ComponentValue.Previous

  } // ComponentValue

}
