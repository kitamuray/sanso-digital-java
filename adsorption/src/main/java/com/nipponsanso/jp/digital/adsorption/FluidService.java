/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

/// 流体サービス.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class FluidService {

  /// 組成.
  private final Composition composition;

  /// 流体モデルのインスタンスを生成する.
  ///
  /// @param composition 組成.
  FluidService(Composition composition) {
    this.composition = composition;
  }

  /// 組成を返す.
  ///
  /// @return 組成.
  final Composition composition() {
    return this.composition;
  }

  /// 副成分の質量分率を基準に正規化する.
  ///
  /// 正規化の対象は主成分の質量分率, 平均分子量, 全成分のモル分率である.
  ///
  /// @param cell セル.
  void update(FluidCell2 cell) {
    this.composition.updateMajorFraction(cell);
    this.composition.updateMolecularWeight(cell);
    this.composition.updateMolarFractions(cell);
  }

  /// 流体の密度を返す.
  ///
  /// @param cell セル.
  /// @return 流体の密度 (kg m<sup>-3</sup>).
  abstract double density(FluidCell2 cell);

  /// 流体の絶対圧力を返す.
  ///
  /// @param cell セル.
  /// @return 流体の絶対圧力 (Pa).
  abstract double pressure(FluidCell2 cell);

  /// 流体の定圧比熱を返す.
  ///
  /// @param cell セル.
  /// @return 流体の定圧比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  abstract double specificConstantPressureSpecificHeat(FluidCell2 cell);

  /// 流体の比エンタルピを返す.
  ///
  /// @param cell セル.
  /// @return 流体の比エンタルピ (J kg<sup>-1</sup>).
  abstract double specificEnthalpy(FluidCell2 cell);

  /// 流体の粘性係数を返す.
  ///
  /// @param cell セル.
  /// @return 流体の粘性係数 (Pa s).
  abstract double viscosity(FluidCell2 cell);

  /// 流体の熱伝導率を返す.
  ///
  /// @param cell セル.
  /// @return 流体の熱伝導率 (W m<sup>-1</sup> K<sup>-1</sup>).
  abstract double thermalConductivity(FluidCell2 cell);

  /// 流体成分の物質拡散係数を返す.
  ///
  /// @param cell セル.
  /// @param componentKey 成分のキー.
  /// @return 流体成分の物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
  abstract double diffusivity(FluidCell2 cell, ComponentKey componentKey);

}
