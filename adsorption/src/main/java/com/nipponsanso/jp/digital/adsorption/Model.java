/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.function.Supplier;

/// モデル.
///
/// @param <M> モデルの型.
/// @param <E> モデルの永続化クラスの型.
/// @param <S> 工程の型.
/// @since 1.0
/// @author KITAMURA Yuichiro
public abstract class Model<M extends Model<M, E, S>, E, S> {

  /// 繰り返し計算回数上限.
  private int maxIteration = 1000;

  /// モデルのインスタンスを返す.
  ///
  /// @return モデルのインスタンス.
  abstract M self();

  /// モデルの永続化クラスの実行クラスを返す.
  ///
  /// @return モデルの永続化クラスの実行クラス.
  abstract Class<E> entityClass();

  /// モデルの永続化クラスのインスタンスを生成するオペレーションを返す.
  ///
  /// @return モデルの永続化クラスのインスタンスを生成するオペレーション.
  abstract Supplier<E> entitySupplier();

  /// 繰り返し計算回数の上限を設定する.
  ///
  /// @param maxIteration 繰り返し計算回数の上限.
  /// @return モデルのインスタンス.
  /// @throws IllegalArgumentException `maxIteration` が 0 以下の場合 (`maxIteration <= 0`).
  public M maxIteration(int maxIteration) {
    Preconditions.checkArgument(maxIteration > 0);
    this.maxIteration = maxIteration;
    return this.self();
  }

  public abstract M run(S schedule);

}
