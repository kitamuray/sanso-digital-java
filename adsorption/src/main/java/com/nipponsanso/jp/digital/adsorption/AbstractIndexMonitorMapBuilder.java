/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.Map;

/// 位置番号とモニタのマップのビルダのスケルトン実装.
///
/// @param <B> ビルダの型.
/// @since 1.1
/// @author KITAMURA Yuichiro
abstract class AbstractIndexMonitorMapBuilder<B> extends AbstractIndexMonitorBuilder<B> {

  /// 出力ファイル名.
  private final String fileName;

  /// 位置番号とモニタのマップのビルダのインスタンスを生成する.
  ///
  /// @param fileName 出力ファイル名.
  /// @throws NullPointerException `fileName` が `null` の場合.
  AbstractIndexMonitorMapBuilder(String fileName) {
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

  /// {@inheritDoc}
  ///
  /// @param index {@inheritDoc}
  /// @param monitor {@inheritDoc}
  /// @return {@inheritDoc}
  @Override
  final B end(int index, Monitor monitor) {
    this.indexMonitorMap().put(this.fileName, new IndexMonitor(index, monitor));
    return this.self();
  }

}
