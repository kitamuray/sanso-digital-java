/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption.internal;

import com.nipponsanso.jp.digital.adsorption.ComponentKey;
import java.util.Map;

/// セル
///
/// @since 1.1
/// @author KITAMURA Yuichiro
public abstract class Cell {

  /// 変数の数.
  public static final int SIZE = 0;

  /// セルのインスタンスを生成する.
  public Cell() {}

  /// オフセット値を返す.
  ///
  /// @return オフセット値.
  public abstract int offset();

  /// プリミティブ double 型の値を保持するための配列を返す.
  ///
  /// @return プリミティブ double 型の値を保持するための配列.
  public abstract double[] doubleArray();

  /// 前ステップ値を返す.
  ///
  /// @return 前ステップ値.
  public abstract Previous previous();

  /// 成分の不変マップを返す.
  ///
  /// @return 成分の不変マップ.
  public abstract Map<ComponentKey, ? extends ComponentValue> componentMap();

  /// セルの前ステップ値.
  public abstract static class Previous {

    /// 変数の数.
    public static final int SIZE = 0;

    /// セルの前ステップ値のインスタンスを生成する.
    public Previous() {}

    /// オフセット値を返す.
    ///
    /// @return オフセット値.
    public abstract int offset();

    /// プリミティブ double 型の値を保持するための配列を返す.
    ///
    /// @return プリミティブ double 型の値を保持するための配列.
    public abstract double[] doubleArray();

  } // Previous

  /// セルの成分値.
  public abstract static class ComponentValue {

    /// 変数の数.
    public static final int SIZE = 0;

    /// セルの成分値のインスタンスを生成する.
    public ComponentValue() {}

    /// オフセット値を返す.
    ///
    /// @return オフセット値.
    public abstract int offset();

    /// プリミティブ double 型の値を保持するための配列を返す.
    ///
    /// @return プリミティブ double 型の値を保持するための配列.
    public abstract double[] doubleArray();

    /// 前ステップ値を返す.
    ///
    /// @return 前ステップ値.
    public abstract Previous previous();

    /// セルの成分値の前ステップ値.
    public abstract static class Previous {

      /// 変数の数.
      public static final int SIZE = 0;

      /// セルの成分値の前ステップ値のインスタンスを生成する.
      public Previous() {}

      /// オフセット値を返す.
      ///
      /// @return オフセット値.
      public abstract int offset();

      /// プリミティブ double 型の値を保持するための配列を返す.
      ///
      /// @return プリミティブ double 型の値を保持するための配列.
      public abstract double[] doubleArray();

    } // Previous

  } // ComponentValue

}
