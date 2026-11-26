/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.nipponsanso.jp.digital.BlancDiffusivity;
import com.nipponsanso.jp.digital.ChapmanEnskogDiffusivity;
import com.nipponsanso.jp.digital.ChapmanEnskogViscosity;
import com.nipponsanso.jp.digital.MasonSaxenaThermalConductivity;
import com.nipponsanso.jp.digital.ModifiedEuckenThermalConductivity;
import com.nipponsanso.jp.digital.PhysicalConstants;
import com.nipponsanso.jp.digital.WilkeViscosity;

/// 低圧下理想気体の流体モデル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
final class LowPressureIdealGasFluidService extends FluidService {

  /// 低圧下理想気体の流体モデルのインスタンスを生成する.
  ///
  /// @param composition 組成.
  LowPressureIdealGasFluidService(Composition composition){
    super(composition);
  }

  @Override
  double density(FluidCell cell) {
    return cell.getPressure()
        * cell.getMolecularWeight()
        / PhysicalConstants.MOLAR_GAS_CONSTANT
        / cell.getTemperature();
  }

  @Override
  double pressure(FluidCell cell) {
    return cell.getDensity()
        / cell.getMolecularWeight()
        * PhysicalConstants.MOLAR_GAS_CONSTANT
        * cell.getTemperature();
  }

  @Override
  double specificConstantPressureSpecificHeat(FluidCell cell) {
    double molecularWeight   = cell.getMolecularWeight();
    double molarSpecificHeat = this.molarSpecificConstantPressureSpecificHeat(cell);

    return molarSpecificHeat / (molecularWeight * 1e-3);
  }

  /// 多成分物質のモル定圧比熱を返す.
  ///
  /// @param cell セル.
  /// @return 多成分物質のモル定圧比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  private double molarSpecificConstantPressureSpecificHeat(FluidCell cell) {
    return cell.componentMap().entrySet().stream()
        .mapToDouble(entry -> {
          // 式が長くなるため文で記述
          double molarFraction = entry.getValue().getMolarFraction();
          double molarSpecificHeat
              = this.molarSpecificConstantPressureSpecificHeat(cell, entry.getKey());
          return molarFraction * molarSpecificHeat;
        })
        .sum();
  }

  /// 純成分物質のモル定圧比熱を返す.
  ///
  /// @param cell セル.
  /// @param componentKey 成分のキー.
  /// @return 純成分物質のモル定圧比熱 (J mol<sup>-1</sup> K<sup>-1</sup>).
  private double molarSpecificConstantPressureSpecificHeat(
      FluidCell cell, ComponentKey componentKey) {
    return componentKey.thermodynamicFunction()
        .molarSpecificConstantPressureSpecificHeat(cell.getTemperature());
  }

  @Override
  double specificEnthalpy(FluidCell cell) {
    double molecularWeight       = cell.getMolecularWeight();
    double molarSpecificEnthalpy = this.molarSpecificEnthalpy(cell);

    return molarSpecificEnthalpy / (molecularWeight * 1e-3);
  }

  /// 多成分物質のモル比エンタルピを返す.
  ///
  /// @param cell セル.
  /// @return 多成分物質のモル比エンタルピ (J mol<sup>-1</sup>)
  private double molarSpecificEnthalpy(FluidCell cell) {
    return cell.componentMap().entrySet().stream()
        .mapToDouble(entry -> {
          // 式が長くなるため文で記述
          double molarFraction         = entry.getValue().getMolarFraction();
          double molarSpecificEnthalpy = this.molarSpecificEnthalpy(cell, entry.getKey());
          return molarFraction * molarSpecificEnthalpy;
        })
        .sum();
  }

  /// 純成分物質のモル比エンタルピを返す.
  ///
  /// @param cell セル.
  /// @param componentKey 成分のキー.
  /// @return 純成分物質のモル比エンタルピ (J mol<sup>-1</sup>).
  private double molarSpecificEnthalpy(FluidCell cell, ComponentKey componentKey) {
    return componentKey.thermodynamicFunction()
        .molarSpecificEnthalpy(cell.getTemperature());
  }

  @Override
  double viscosity(FluidCell cell) {
    return WilkeViscosity.calculate(
        cell.componentMap().keySet(),
        ComponentKey::molecularWeight,
        componentKey -> cell.componentMap().get(componentKey).getMolarFraction(),
        componentKey -> this.viscosity(cell, componentKey));
  }

  /// 純成分物質の粘性係数を返す.
  ///
  /// @param cell セル.
  /// @param componentKey 成分のキー.
  /// @return 純成分物質の粘性係数 (Pa s).
  private double viscosity(FluidCell cell, ComponentKey componentKey) {
    return ChapmanEnskogViscosity.calculate(
        cell.getTemperature(),
        componentKey.molecularWeight(),
        componentKey.lennardJonesPotential());
  }

  @Override
  double thermalConductivity(FluidCell cell) {
    return MasonSaxenaThermalConductivity.calculate(
        cell.componentMap().keySet(),
        ComponentKey::molecularWeight,
        componentKey -> cell.componentMap().get(componentKey).getFraction(),
        componentKey -> this.viscosity(cell, componentKey),
        componentKey -> this.thermalConductivity(cell, componentKey));
  }

  /// 純成分物質の熱伝導率を返す.
  ///
  /// @param cell セル.
  /// @param componentKey 成分のキー.
  /// @return 純成分物質の熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>).
  private double thermalConductivity(FluidCell cell, ComponentKey componentKey) {
    return ModifiedEuckenThermalConductivity.calculate(
        this.viscosity(cell, componentKey),
        this.molarSpecificConstantVolumeSpecificHeat(cell, componentKey),
        componentKey.molecularWeight());
  }

  /// 純成分物質のモル定積比熱を返す.
  ///
  /// @param cell セル.
  /// @param componentKey 成分のキー.
  /// @return 純成分物質のモル定積比熱 (J mol<sup>-1</sup> K<sup>-1</sup>).
  private double molarSpecificConstantVolumeSpecificHeat(
      FluidCell cell, ComponentKey componentKey) {
    return this.molarSpecificConstantPressureSpecificHeat(cell, componentKey)
        - PhysicalConstants.MOLAR_GAS_CONSTANT;
  }

  double diffusivity(FluidCell cell, ComponentKey componentKey) {
    return BlancDiffusivity.calculate(
        cell.componentMap().keySet(),
        componentKey,
        i -> cell.componentMap().get(i).getFraction(),
        (i, j) -> this.diffusivity(cell, i, j));
  }

  /// 二成分物質の物質拡散係数を返す.
  ///
  /// @param cell セル.
  /// @param i 成分 _i_ のキー.
  /// @param j 成分 _j_ のキー.
  /// @return 二成分物質の物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
  private double diffusivity(FluidCell cell, ComponentKey i, ComponentKey j) {
    return ChapmanEnskogDiffusivity.calculate(
        cell.getTemperature(),
        cell.getPressure(),
        i.molecularWeight(),
        j.molecularWeight(),
        i.lennardJonesPotential(),
        j.lennardJonesPotential());
  }

}
