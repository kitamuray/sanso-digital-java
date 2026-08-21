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
abstract class Face {

  /// 変数の数.
  static final int SIZE = 0;

  /// フェイスのインスタンスを生成する.
  Face() {}

  /// オフセット値を返す.
  ///
  /// @return オフセット値.
  abstract int offset();

  /// プリミティブ double 型の値を保持するための配列を返す.
  ///
  /// @return プリミティブ double 型の値を保持するための配列.
  abstract double[] doubleArray();

  /// 成分の不変マップを返す.
  ///
  /// @return 成分の不変マップ.
  abstract Map<ComponentKey, ? extends ComponentValue> componentMap();

  /// フェイスの成分値.
  abstract static class ComponentValue {

    /// 変数の数.
    static final int SIZE = 0;

    /// フェイスの成分値のインスタンスを生成する.
    ComponentValue() {}

    /// オフセット値を返す.
    ///
    /// @return オフセット値.
    abstract int offset();

    /// プリミティブ double 型の値を保持するための配列を返す.
    ///
    /// @return プリミティブ double 型の値を保持するための配列.
    abstract double[] doubleArray();

  } // ComponentValue

}
