/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.util.Map;

/// 貯槽のセル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
final class TankCell2 extends AbstractFluidCell {

  /// 貯槽.
  private final Tank tank;

  /// 前ステップ値.
  private final Previous previous;

  /// 成分の不変マップ.
  private final Map<ComponentKey, ComponentValue> componentMap;

  /// 貯槽のセルのインスタンスを生成する.
  ///
  /// @param tank 貯槽.
  /// @param composition 組成.
  TankCell2(Tank tank, Composition composition) {
    this.tank = tank;
    this.previous = new Previous();
    this.componentMap = composition.componentKeySet().stream()
        .collect(
            Maps.toImmutableEnumMap(
                componentKey -> componentKey,
                _ -> new ComponentValue()));
  }

  /// 流体が占める体積を返す.
  ///
  /// @return 流体が占める体積 (m<sup>3</sup>).
  double fluidVolume() {
    return this.tank.volume();
  }

  @Override
  public Previous previous() {
    return this.previous;
  }

  @Override
  public Map<ComponentKey, ComponentValue> componentMap() {
    return this.componentMap;
  }

  /// 永続化クラスのインスタンスを生成して返す.
  ///
  /// @return 永続化クラスのインスタンス.
  TankCellEntity2 newEntity() {
    return new TankCellEntity2(
        this.getTemperature(),
        this.getPressure(),
        this.getDensity(),
        this.getSpecificEnthalpy(),
        this.getSpecificConstantPressureSpecificHeat(),
        this.getViscosity(),
        this.getThermalConductivity(),
        this.previous.newEntity(),
        this.componentMap.entrySet().stream()
            .collect(
                Maps.toImmutableEnumMap(
                    Map.Entry::getKey,
                    entry -> entry.getValue().newEntity())));
  }

  /// 永続化クラスのインスタンスを反映する.
  ///
  /// @param entity 永続化クラスのインスタンス.
  /// @throws NullPointerException `entity` またはそれが構成する要素が `null` の場合.
  /// @throws IllegalArgumentException `entity` を構成する要素の値が範囲外の場合.
  void accept(TankCellEntity2 entity) {
    Preconditions.checkNotNull(entity);
    Preconditions.checkNotNull(entity.previous());
    Preconditions.checkNotNull(entity.componentMap());
    Preconditions.checkArgument(this.componentMap.keySet().equals(entity.componentMap().keySet()));
    entity.componentMap().values().forEach(Preconditions::checkNotNull);

    this.setTemperature(entity.temperature());
    this.setPressure(entity.pressure());
    this.setDensity(entity.density());
    this.setSpecificEnthalpy(entity.specificEnthalpy());
    this.setSpecificConstantPressureSpecificHeat(entity.specificConstantPressureSpecificHeat());
    this.setViscosity(entity.viscosity());
    this.setThermalConductivity(entity.thermalConductivity());

    this.previous.accept(entity.previous());
    this.componentMap.forEach((key, value) -> value.accept(entity.componentMap().get(key)));
  }

  /// 貯槽のセルの前ステップ値.
  static final class Previous extends AbstractFluidCell.Previous {

    /// 貯槽のセルの前ステップ値のインスタンスを生成する.
    private Previous() {}

    /// 永続化クラスのインスタンスを生成して返す.
    ///
    /// @return 永続化クラスのインスタンス.
    private TankCellEntity2.Previous newEntity() {
      return new TankCellEntity2.Previous(
          this.getTemperature(),
          this.getDensity(),
          this.getSpecificEnthalpy(),
          this.getSpecificConstantPressureSpecificHeat());
    }

    /// 永続化クラスのインスタンスを反映する.
    ///
    /// @param entity 永続化クラスのインスタンス.
    /// @throws IllegalArgumentException `entity` を構成する要素の値が範囲外の場合.
    private void accept(TankCellEntity2.Previous entity) {
      this.setTemperature(entity.temperature());
      this.setDensity(entity.density());
      this.setSpecificEnthalpy(entity.specificEnthalpy());
      this.setSpecificConstantPressureSpecificHeat(entity.specificConstantPressureSpecificHeat());
    }

  } // Previous

  /// 貯槽のセルの成分値.
  static final class ComponentValue extends AbstractFluidCell.ComponentValue {

    /// 前ステップ値.
    private final Previous previous;

    /// 貯槽のセルの成分値のインスタンスを生成する.
    private ComponentValue() {
      this.previous = new Previous();
    }

    @Override
    public Previous previous() {
      return this.previous;
    }

    /// 永続化クラスのインスタンスを生成して返す.
    ///
    /// @return 永続化クラスのインスタンス.
    private TankCellEntity2.ComponentValue newEntity() {
      return new TankCellEntity2.ComponentValue(
          this.getFraction(),
          this.getDiffusivity(),
          this.previous.newEntity());
    }

    /// 永続化クラスのインスタンスを反映する.
    ///
    /// @param entity 永続化クラスのインスタンス.
    /// @throws IllegalArgumentException `entity` を構成する要素の値が範囲外の場合.
    private void accept(TankCellEntity2.ComponentValue entity) {
      this.setFraction(entity.fraction());
      this.setDiffusivity(entity.diffusivity());
      this.previous.accept(entity.previous());
    }

    /// 貯槽のセルの成分値の前ステップ値.
    static final class Previous extends AbstractFluidCell.ComponentValue.Previous {

      /// 貯槽のセルの成分値の前ステップ値のインスタンスを生成する.
      private Previous() {}

      /// 永続化クラスのインスタンスを生成して返す.
      ///
      /// @return 永続化クラスのインスタンス.
      private TankCellEntity2.ComponentValue.Previous newEntity() {
        return new TankCellEntity2.ComponentValue.Previous(
            this.getFraction());
      }

      /// 永続化クラスのインスタンスを反映する.
      ///
      /// @param entity 永続化クラスのインスタンス.
      /// @throws IllegalArgumentException `entity` を構成する要素の値が範囲外の場合.
      private void accept(TankCellEntity2.ComponentValue.Previous entity) {
        this.setFraction(entity.fraction());
      }

    } // ComponentValue.Previous

  } // ComponentValue

}
