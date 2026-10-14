/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

/// 低圧下の理想気体の流体.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class LowPressureIdealGasFluid extends Fluid {

  /// 低圧下の理想気体の流体のインスタンスを生成する.
  private LowPressureIdealGasFluid() {}

  /// {@inheritDoc}
  ///
  /// @param composition {@inheritDoc}
  /// @return {@inheritDoc}
  @Override
  LowPressureIdealGasFluidService newService(Composition composition) {
    return new LowPressureIdealGasFluidService(composition);
  }

  /// 唯一のインスタンス.
  private static final LowPressureIdealGasFluid INSTANCE = new LowPressureIdealGasFluid();

  /// 低圧下の理想気体の流体のインスタンスを返す.
  ///
  /// @return 低圧下の理想気体の流体のインスタンス.
  public static LowPressureIdealGasFluid getInstance() {
    return INSTANCE;
  }

}
