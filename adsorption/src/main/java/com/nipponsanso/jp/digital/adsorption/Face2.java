/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import java.util.Map;

/// フェイス.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
interface Face2 {

  /// フェイスの成分の不変マップを返す.
  ///
  /// @return フェイスの成分の不変マップ.
  Map<ComponentKey, ? extends ComponentValue> componentMap();

  /// フェイスの成分値.
  interface ComponentValue {

  } // ComponentValue

}
