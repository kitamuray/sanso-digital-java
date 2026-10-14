/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

/// 流体.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public abstract class Fluid {

  /// 流体のインスタンスを生成する.
  Fluid() {}

  /// 流体サービスのインスタンスを生成して返す.
  ///
  /// @param composition 組成.
  /// @return 流体サービスのインスタンス.
  abstract FluidService newService(Composition composition);

}
