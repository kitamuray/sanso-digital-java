/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import java.util.Map;

/// セル
///
/// @since 1.1
/// @author KITAMURA Yuichiro
abstract class Cell {

  /// 変数の数.
  static final int SIZE = 0;

  /// セルのインスタンスを生成する.
  Cell() {}

  /// オフセット値を返す.
  ///
  /// @return オフセット値.
  abstract int offset();

  /// プリミティブ double 型の値を保持するための配列を返す.
  ///
  /// @return プリミティブ double 型の値を保持するための配列.
  abstract double[] doubleArray();

  /// 前ステップ値を返す.
  ///
  /// @return 前ステップ値.
  abstract Previous previous();

  /// 成分の不変マップを返す.
  ///
  /// @return 成分の不変マップ.
  abstract Map<ComponentKey, ? extends ComponentValue> componentMap();

  /// セルの前ステップ値.
  abstract static class Previous {

    /// 変数の数.
    static final int SIZE = 0;

    /// セルの前ステップ値のインスタンスを生成する.
    Previous() {}

    /// オフセット値を返す.
    ///
    /// @return オフセット値.
    abstract int offset();

    /// プリミティブ double 型の値を保持するための配列を返す.
    ///
    /// @return プリミティブ double 型の値を保持するための配列.
    abstract double[] doubleArray();

  } // Previous

  /// セルの成分値.
  abstract static class ComponentValue {

    /// 変数の数.
    static final int SIZE = 0;

    /// セルの成分値のインスタンスを生成する.
    ComponentValue() {}

    /// オフセット値を返す.
    ///
    /// @return オフセット値.
    abstract int offset();

    /// プリミティブ double 型の値を保持するための配列を返す.
    ///
    /// @return プリミティブ double 型の値を保持するための配列.
    abstract double[] doubleArray();

    /// 前ステップ値を返す.
    ///
    /// @return 前ステップ値.
    abstract Previous previous();

    /// セルの成分値の前ステップ値.
    abstract static class Previous {

      /// 変数の数.
      static final int SIZE = 0;

      /// セルの成分値の前ステップ値のインスタンスを生成する.
      Previous() {}

      /// オフセット値を返す.
      ///
      /// @return オフセット値.
      abstract int offset();

      /// プリミティブ double 型の値を保持するための配列を返す.
      ///
      /// @return プリミティブ double 型の値を保持するための配列.
      abstract double[] doubleArray();

    } // Previous

  } // ComponentValue

}
