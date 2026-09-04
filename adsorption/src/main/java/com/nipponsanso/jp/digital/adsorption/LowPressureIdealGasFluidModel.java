/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

/// 低圧下理想気体の流体モデル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
final class LowPressureIdealGasFluidModel extends FluidModel {

  /// 低圧下理想気体の流体モデルのインスタンスを生成する.
  ///
  /// @param composition 組成.
  LowPressureIdealGasFluidModel(Composition composition){
    super(composition);
  }

}
