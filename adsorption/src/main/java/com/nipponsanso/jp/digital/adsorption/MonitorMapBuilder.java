/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.Map;

/// モニタのマップのビルダのスケルトン実装.
///
/// 継承された匿名クラスを実装する.
///
/// @param <B> ビルダの型.
/// @since 1.1
/// @author KITAMURA Yuichiro
public abstract class MonitorMapBuilder<B> {

  /// 出力ファイル名.
  private final String fileName;

  /// モニタのマップのビルダのインスタンスを生成する.
  ///
  /// @param fileName 出力ファイル名.
  /// @throws NullPointerException `fileName` が `null` の場合.
  MonitorMapBuilder(String fileName) {
    this.fileName = Preconditions.checkNotNull(fileName);
  }

  /// モニタの可変マップを返す.
  ///
  /// @return モニタの可変マップ.
  abstract Map<String, Monitor> monitorMap();

  /// ビルダのインスタンスを返す.
  ///
  /// @return ビルダのインスタンス.
  abstract B self();

  /// モニタを設定する.
  ///
  /// @param monitor モニタ.
  /// @return ビルダのインスタンス.
  /// @throws NullPointerException `monitor` が `null` の場合.
  public final B monitor(Monitor monitor) {
    this.monitorMap().put(this.fileName, Preconditions.checkNotNull(monitor));
    return this.self();
  }

}
