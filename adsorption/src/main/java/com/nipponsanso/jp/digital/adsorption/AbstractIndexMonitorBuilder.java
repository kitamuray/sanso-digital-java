/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;

/// 位置番号とモニタのビルダのスケルトン実装.
///
/// @param <B> ビルダの型.
/// @since 1.1
/// @author KITAMURA Yuichiro
abstract class AbstractIndexMonitorBuilder<B> {

  /// 位置番号.
  private int index;

  /// モニタ.
  private Monitor monitor;

  /// 位置番号とモニタのビルダのインスタンスを生成する.
  AbstractIndexMonitorBuilder() {}

  /// 位置番号を設定する.
  ///
  /// @param index 位置番号.
  /// @return 位置番号を設定するビルダのインスタンス.
  /// @throws IndexOutOfBoundsException `index` が範囲外の場合.
  public final IndexBuilder index(int index) {
    return new IndexBuilder(index);
  }

  /// モニタを設定する.
  ///
  /// @param monitor モニタ.
  /// @return モニタを設定するビルダのインスタンス.
  /// @throws NullPointerException `monitor` が `null` の場合.
  public final MonitorBuilder monitor(Monitor monitor) {
    return new MonitorBuilder(monitor);
  }

  /// 指定された位置番号とモニタを設定する.
  ///
  /// @param index 位置番号.
  /// @param monitor モニタ.
  /// @return ビルダのインスタンス.
  abstract B end(int index, Monitor monitor);

  /// 位置番号とモニタを設定する.
  ///
  /// @return ビルダのインスタンス.
  private B end() {
    return this.end(this.index, this.monitor);
  }

  /// 位置番号の上限を返す.
  ///
  /// デフォルト実装では [Integer#MAX_VALUE] を返す.
  ///
  /// @return 位置番号の上限.
  int size() {
    return Integer.MAX_VALUE;
  }

  /// 位置番号を設定するビルダ.
  public final class IndexBuilder {

    /// 位置番号を設定するビルダのインスタンスを生成する.
    ///
    /// @param index 位置番号.
    /// @throws IndexOutOfBoundsException `index` が範囲外の場合.
    private IndexBuilder(int index) {
      AbstractIndexMonitorBuilder.this.index
          = Preconditions.checkElementIndex(index, AbstractIndexMonitorBuilder.this.size());
    }

    /// モニタを設定する.
    ///
    /// @param monitor モニタ.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `monitor` が `null` の場合.
    public B monitor(Monitor monitor) {
      AbstractIndexMonitorBuilder.this.monitor = Preconditions.checkNotNull(monitor);
      return AbstractIndexMonitorBuilder.this.end();
    }

  } // IndexBuilder

  /// モニタを設定するビルダ.
  public final class MonitorBuilder {

    /// モニタを設定するビルダのインスタンスを生成する.
    ///
    /// @param monitor モニタ.
    /// @throws NullPointerException `monitor` が `null` の場合.
    private MonitorBuilder(Monitor monitor) {
      AbstractIndexMonitorBuilder.this.monitor = Preconditions.checkNotNull(monitor);
    }

    /// 位置番号を設定する.
    ///
    /// @param index 位置番号.
    /// @return ビルダのインスタンス.
    /// @throws IndexOutOfBoundsException `index` が範囲外の場合.
    public B index(int index) {
      AbstractIndexMonitorBuilder.this.index
          = Preconditions.checkElementIndex(index, AbstractIndexMonitorBuilder.this.size());
      return AbstractIndexMonitorBuilder.this.end();
    }

  } // MonitorBuilder

}
