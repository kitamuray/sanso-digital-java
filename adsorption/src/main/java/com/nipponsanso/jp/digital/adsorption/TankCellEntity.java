/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.MoreObjects;

/// 貯槽セルの永続化クラス.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class TankCellEntity extends FluidCellEntity<
    TankCellEntity.Previous,
    TankCellEntity.ComponentValue,
    TankCellEntity.ComponentValue.Previous> {

  /// 貯槽セルの永続化クラスのインスタンスを生成する.
  public TankCellEntity() {
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
        .add("previous", this.getPreviousEntity())
        .add("componentMap", this.getComponentEntityMap())
        .toString();
  }

  /// 貯槽セルの前ステップ値の永続化クラス.
  public static final class Previous extends FluidCellEntity.Previous {

    /// 貯槽セルの前ステップ値の永続化クラスのインスタンスを生成する.
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
          .toString();
    }

  } // Previous

  /// 貯槽セルの成分値の永続化クラス.
  public static final class ComponentValue
      extends FluidCellEntity.ComponentValue<ComponentValue.Previous> {

    /// 貯槽セルの成分値の永続化クラスのインスタンスを生成する.
    public ComponentValue() {
      super(new Previous());
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this)
          .add("fraction", this.getFraction())
          .add("diffusivity", this.getDiffusivity())
          .add("previous", this.getPreviousEntity())
          .toString();
    }

    /// 貯槽セルの成分値の前ステップ値の永続化クラス.
    public static final class Previous extends FluidCellEntity.ComponentValue.Previous {

      /// 貯槽セルの成分値の前ステップ値の永続化クラスのインスタンスを生成する.
      public Previous() {}

      @Override
      public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("fraction", this.getFraction())
            .toString();
      }

    } // ComponentValue.Previous

  } // ComponentValue

}
