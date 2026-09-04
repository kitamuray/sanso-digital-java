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
    this(process, null);
  }

  /// {@inheritDoc}
  ///
  /// @return {@inheritDoc}
  @Override
  BasicPsaSolver self() {
    return this;
  }

  public BasicPsaSolver write(String fileName) {
    Preconditions.checkNotNull(fileName);

    BasicPsaModelEntity entity = new BasicPsaModelEntity();
    this.model.write(entity);
    OBJECT_MAPPER.writeValue(new File(fileName), entity);

    return this;
  }

  public BasicPsaSolver read(String fileName) {
    Preconditions.checkNotNull(fileName);

    BasicPsaModelEntity entity
        = OBJECT_MAPPER.readValue(new File(fileName), BasicPsaModelEntity.class);
    this.model.read(entity);

    return this;
  }

}
