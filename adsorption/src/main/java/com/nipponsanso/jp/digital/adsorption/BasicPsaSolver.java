/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.io.File;
import java.util.List;

/// 基本 PSA のソルバ.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class BasicPsaSolver extends AdsorbentSolver<BasicPsaSolver> {

  /// プロセス.
  private final BasicPsaProcess process;

  /// モデル.
  private final BasicPsaModel model;

  /// フェーズの不変リスト.
  private final List<BasicPsaService> serviceList;

  /// 基本 PSA のサービスのインスタンスを生成する.
  ///
  /// @param process プロセス.
  /// @param services サービス一式.
  @VisibleForTesting
  BasicPsaSolver(BasicPsaProcess process, BasicPsaService... services) {
    this.process = process;
    this.model = new BasicPsaModel(this.process);
    this.serviceList = ImmutableList.copyOf(services);
  }

  /// 基本 PSA ソルバのインスタンスを生成する.
  ///
  /// @param process プロセス.
  BasicPsaSolver(BasicPsaProcess process) {
    this(
        process,
        InitializeBasicPsaService.getInstance());
  }

  @Override
  BasicPsaSolver self() {
    return this;
  }

  /// 指定されたファイルへ計算結果を書き込む.
  ///
  /// @param fileName ファイル名.
  /// @return ソルバのインスタンス.
  /// @throws NullPointerException `fileName` が `null` の場合.
  public BasicPsaSolver write(String fileName) {
    Preconditions.checkNotNull(fileName);

    BasicPsaModelEntity entity = this.model.newEntity();
    OBJECT_MAPPER.writeValue(new File(fileName), entity);

    return this;
  }

  /// 指定されたファイルから計算結果を読み込む.
  ///
  /// @param fileName ファイル名.
  /// @return ソルバのインスタンス.
  /// @throws NullPointerException `fileName` が `null` の場合.
  public BasicPsaSolver read(String fileName) {
    Preconditions.checkNotNull(fileName);

    BasicPsaModelEntity entity
        = OBJECT_MAPPER.readValue(new File(fileName), BasicPsaModelEntity.class);
    this.model.accept(entity);

    return this;
  }

  /// 履歴情報を初期化する.
  ///
  /// @return ソルバのインスタンス.
  public BasicPsaSolver clearHistories() {
    this.model.clearHistories();
    return this;
  }

  /// 履歴情報を出力する.
  ///
  /// @return ソルバのインスタンス.
  public BasicPsaSolver flushHistories() {
    this.model.flushHistories();
    return this;
  }

}
