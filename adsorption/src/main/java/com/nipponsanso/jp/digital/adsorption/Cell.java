/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import java.util.Map;

/// セル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
interface Cell {

  /// セルの前ステップ値を返す.
  ///
  /// @return セルの前ステップ値.
  Previous previous();

  /// セルの成分の不変マップを返す.
  ///
  /// @return セルの成分の不変マップ.
  Map<ComponentKey, ? extends ComponentValue> componentMap();

  /// セルの前ステップ値.
  interface Previous {

  } // Previous

  /// セルの成分値.
  interface ComponentValue {

    /// セルの成分値の前ステップ値を返す.
    ///
    /// @return セルの成分値の前ステップ値.
    Previous previous();

    /// セルの成分値の前ステップ値.
    interface Previous {

    } // ComponentValue.Previous

  } // ComponentValue

}
