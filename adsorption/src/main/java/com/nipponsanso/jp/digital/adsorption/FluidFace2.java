/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import java.util.Map;

/// 流体を含むフェイス.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
interface FluidFace2 extends Face2 {

  /// 流体を含むフェイスの成分の不変マップを返す.
  ///
  /// @return 流体を含むフェイスの成分の不変マップ.
  @Override
  Map<ComponentKey, ? extends ComponentValue> componentValue();

  /// 流体を含むフェイスの成分値.
  interface ComponentValue extends Face2.ComponentValue {

  } // ComponentValue

}
