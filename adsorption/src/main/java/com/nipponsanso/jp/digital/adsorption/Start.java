/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.Optional;

/// 開始条件のスケルトン実装.
///
/// このクラスは可変(mutable)フィールドを有しない.
/// 継承した具象クラスは不変(immutable)でなければならない.
///
/// @since 1.1
/// @author KITAMURA Yuihchiro
abstract class Start {

  /// 開始条件のビルダのスケルトン実装.
  ///
  /// @param <B> ビルダの型.
  static abstract class Builder<B extends Builder<B>> {

    /// 成分のキー.
    private ComponentKey componentKey;

    /// 開始条件のビルダのインスタンスを生成する.
    Builder() {}

    /// ビルダのインスタンスを返す.
    ///
    /// @return ビルダのインスタンス.
    abstract B self();

    /// 成分のキーを設定する.
    ///
    /// @param componentKey 成分のキー.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `componentKey` が `null` の場合.
    public final B componentKey(ComponentKey componentKey) {
      this.componentKey = Preconditions.checkNotNull(componentKey);
      return this.self();
    }

    /// 設定済みの成分のキーを返す.
    ///
    /// @return 成分のキーが設定されていればそれを含む [Optional],
    ///     未設定であれば [Optional#empty() ].
    final Optional<ComponentKey> currentComponentKey() {
      return Optional.ofNullable(this.componentKey);
    }

    /// 開始条件のインスタンスを生成して返す.
    ///
    /// @return 開始条件のインスタンス.
    abstract Start build();

  } // Builder

  /// 開始条件のインスタンスを生成する.
  ///
  /// @param builder 開始条件のビルダ.
  Start(Builder<?> builder) {}

  /// 指定されたセルへ開始条件を設定する.
  ///
  /// @param cell セル.
  void start(Cell cell) {}

}
