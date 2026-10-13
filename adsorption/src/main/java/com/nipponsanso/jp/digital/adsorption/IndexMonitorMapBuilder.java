/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.Map;

/// 位置番号とモニタのマップのビルダ.
///
/// @param <B> ビルダの型.
/// @since 1.1
/// @author KITAMURA Yuichiro
public abstract class IndexMonitorMapBuilder<B> {

  /// 出力先のファイル名.
  private final String fileName;

  /// 位置番号.
  private int index;

  /// モニタ.
  private Monitor monitor;

  /// 位置番号とモニタのマップのビルダのインスタンスを生成する.
  ///
  /// @param fileName 出力先のファイル名.
  /// @throws NullPointerException `fileName` が `null` の場合.
  IndexMonitorMapBuilder(String fileName) {
    this.fileName = Preconditions.checkNotNull(fileName);
  }

  /// 位置番号とモニタの可変マップを返す.
  ///
  /// @return 位置番号とモニタの可変マップ.
  abstract Map<String, IndexMonitor> indexMonitorMap();

  /// ビルダのインスタンスを返す.
  ///
  /// @return ビルダのインスタンス.
  abstract B self();

  /// 位置番号の上限を返す.
  ///
  /// @return 位置番号の上限.
  abstract int size();

  /// マップへの登録を終える.
  ///
  /// @return ビルダのインスタンス.
  private B end() {
    this.indexMonitorMap().put(this.fileName, new IndexMonitor(this.index, this.monitor));
    return this.self();
  }

  /// 位置番号を設定する.
  ///
  /// @param index 位置番号.
  /// @return 位置番号が設定済のビルダのインスタンス.
  /// @throws IndexOutOfBoundsException `index` が範囲外の場合.
  public IndexBuilder index(int index) {
    this.index = Preconditions.checkElementIndex(index, this.size());
    return new IndexBuilder();
  }

  /// モニタを設定する.
  ///
  /// @param monitor モニタ.
  /// @return モニタが設定済のビルダのインスタンス.
  /// @throws NullPointerException `monitor` が `null` の場合.
  public MonitorBuilder monitor(Monitor monitor) {
    this.monitor = Preconditions.checkNotNull(monitor);
    return new MonitorBuilder();
  }

  /// 位置番号が設定済のビルダ.
  public final class IndexBuilder {

    /// 位置番号が設定済のビルダのインスタンスを生成する.
    private IndexBuilder() {}

    /// モニタを設定する.
    ///
    /// @param monitor モニタ.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `monitor` が `null` の場合.
    public B monitor(Monitor monitor) {
      IndexMonitorMapBuilder.this.monitor = Preconditions.checkNotNull(monitor);
      return IndexMonitorMapBuilder.this.end();
    }

  } // IndexBuilder

  /// モニタが設定済のビルダ.
  public final class MonitorBuilder {

    /// モニタが設定済のビルダのインスタンスを生成する.
    private MonitorBuilder() {}

    /// 位置番号を設定する.
    ///
    /// @param index 位置番号.
    /// @return ビルダのインスタンス.
    /// @throws IndexOutOfBoundsException `index` が範囲外の場合.
    public B index(int index) {
      IndexMonitorMapBuilder.this.index
          = Preconditions.checkElementIndex(index, IndexMonitorMapBuilder.this.size());
      return IndexMonitorMapBuilder.this.end();
    }

  } // MonitorBuilder

}
