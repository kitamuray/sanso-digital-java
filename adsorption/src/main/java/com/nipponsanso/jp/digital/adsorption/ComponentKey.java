/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.nipponsanso.jp.digital.LennardJonesPotential;
import com.nipponsanso.jp.digital.PropertiesGasesLiquids6th;
import com.nipponsanso.jp.digital.ThermodynamicFunction;

/// 成分のキー.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public enum ComponentKey {

  /// アルゴン Ar.
  ARGON(
      new Builder()
          .molecularWeight(PropertiesGasesLiquids6th.ARGON_MOLECULAR_WEIGHT)
          .thermodynamicFunction(PropertiesGasesLiquids6th.ARGON_THERMODYNAMIC_FUNCTION)
          .lennardJonesPotential(PropertiesGasesLiquids6th.ARGON_LENNARD_JONES_POTENTIAL)),

  /// 水素 H<sub>2</sub>.
  HYDROGEN(
      new Builder()
          .molecularWeight(PropertiesGasesLiquids6th.HYDROGEN_MOLECULAR_WEIGHT)
          .thermodynamicFunction(PropertiesGasesLiquids6th.HYDROGEN_THERMODYNAMIC_FUNCTION)
          .lennardJonesPotential(PropertiesGasesLiquids6th.HYDROGEN_LENNARD_JONES_POTENTIAL)),

  /// 水蒸気 H<sub>2</sub>O.
  WATER(
      new Builder()
          .molecularWeight(PropertiesGasesLiquids6th.WATER_MOLECULAR_WEIGHT)
          .thermodynamicFunction(PropertiesGasesLiquids6th.WATER_THERMODYNAMIC_FUNCTION)
          .lennardJonesPotential(PropertiesGasesLiquids6th.WATER_LENNARD_JONES_POTENTIAL)),

  /// アンモニア NH<sub>3</sub>.
  AMMONIA(
      new Builder()
          .molecularWeight(PropertiesGasesLiquids6th.AMMONIA_MOLECULAR_WEIGHT)
          .thermodynamicFunction(PropertiesGasesLiquids6th.AMMONIA_THERMODYNAMIC_FUNCTION)
          .lennardJonesPotential(PropertiesGasesLiquids6th.AMMONIA_LENNARD_JONES_POTENTIAL)),

  /// ヘリウム He.
  HELIUM(
      new Builder()
          .molecularWeight(PropertiesGasesLiquids6th.HELIUM_MOLECULAR_WEIGHT)
          .thermodynamicFunction(PropertiesGasesLiquids6th.HELIUM_THERMODYNAMIC_FUNCTION)
          .lennardJonesPotential(PropertiesGasesLiquids6th.HELIUM_LENNARD_JONES_POTENTIAL)),

  /// 窒素 N<sub>2</sub>.
  NITROGEN(
      new Builder()
          .molecularWeight(PropertiesGasesLiquids6th.NITROGEN_MOLECULAR_WEIGHT)
          .thermodynamicFunction(PropertiesGasesLiquids6th.NITROGEN_THERMODYNAMIC_FUNCTION)
          .lennardJonesPotential(PropertiesGasesLiquids6th.NITROGEN_LENNARD_JONES_POTENTIAL)),

  /// ネオン Ne.
  NEON(
      new Builder()
          .molecularWeight(PropertiesGasesLiquids6th.NEON_MOLECULAR_WEIGHT)
          .thermodynamicFunction(PropertiesGasesLiquids6th.NEON_THERMODYNAMIC_FUNCTION)
          .lennardJonesPotential(PropertiesGasesLiquids6th.NEON_LENNARD_JONES_POTENTIAL)),

  /// 酸素 O<sub>2</sub>.
  OXYGEN(
      new Builder()
          .molecularWeight(PropertiesGasesLiquids6th.OXYGEN_MOLECULAR_WEIGHT)
          .thermodynamicFunction(PropertiesGasesLiquids6th.OXYGEN_THERMODYNAMIC_FUNCTION)
          .lennardJonesPotential(PropertiesGasesLiquids6th.OXYGEN_LENNARD_JONES_POTENTIAL));

  /// 成分のキーのビルダ.
  ///
  /// 多くの情報を指定するため, 可読性を高めるためにビルダを用いる.
  private static final class Builder {

    /// 分子量.
    private double molecularWeight;

    /// 熱力学関数.
    private ThermodynamicFunction thermodynamicFunction;

    /// レナード-ジョーンズ・ポテンシャル.
    private LennardJonesPotential lennardJonesPotential;

    private Builder() {}

    /// 分子量を設定する.
    ///
    /// @param molecularWeight 分子量.
    /// @return ビルダのインスタンス自身.
    public Builder molecularWeight(double molecularWeight) {
      this.molecularWeight = molecularWeight;
      return this;
    }

    /// 熱力学関数を設定する.
    ///
    /// @param thermodynamicFunction 熱力学関数.
    /// @return ビルダのインスタンス自身.
    public Builder thermodynamicFunction(ThermodynamicFunction thermodynamicFunction) {
      this.thermodynamicFunction = thermodynamicFunction;
      return this;
    }

    /// レナード-ジョーンズ・ポテンシャルを設定する.
    ///
    /// @param lennardJonesPotential レナード-ジョーンズ・ポテンシャル.
    /// @return ビルダのインスタンス自身.
    public Builder lennardJonesPotential(LennardJonesPotential lennardJonesPotential) {
      this.lennardJonesPotential = lennardJonesPotential;
      return this;
    }

  } // Builder

  /// 分子量.
  private final double molecularWeight;

  /// 熱力学関数.
  private final ThermodynamicFunction thermodynamicFunction;

  /// レナード-ジョーンズ・ポテンシャル.
  private final LennardJonesPotential lennardJonesPotential;

  /// 成分のキーのインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  ComponentKey(Builder builder) {
    this.molecularWeight = builder.molecularWeight;
    this.thermodynamicFunction = builder.thermodynamicFunction;
    this.lennardJonesPotential = builder.lennardJonesPotential;
  }

  /// 分子量を返す.
  ///
  /// @return 分子量.
  public double molecularWeight() {
    return this.molecularWeight;
  }

  /// 熱力学関数を返す.
  ///
  /// @return 熱力学関数.
  public ThermodynamicFunction thermodynamicFunction() {
    return this.thermodynamicFunction;
  }

  /// レナード-ジョーンズ・ポテンシャルを返す.
  ///
  /// @return レナード-ジョーンズ・ポテンシャル.
  public LennardJonesPotential lennardJonesPotential() {
    return this.lennardJonesPotential;
  }

}
