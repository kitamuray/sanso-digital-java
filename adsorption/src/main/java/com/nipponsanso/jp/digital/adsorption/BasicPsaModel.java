/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.function.Supplier;

/// 基本 PSA モデル.
public class BasicPsaModel extends Model<BasicPsaModel, BasicPsaModelEntity, BasicPsaSchedule> {

  /// プロセス.
  private final BasicPsaProcess process;

  /// 指定されたプロセスで構成されたモデルのインスタンスを生成する.
  ///
  /// @param process プロセス.
  /// @throws NullPointerException `process` が `null` の場合.
  private BasicPsaModel(BasicPsaProcess process) {
    this.process = Preconditions.checkNotNull(process);
  }

  /// {@inheritDoc}
  ///
  /// @return {@inheritDoc}
  @Override
  BasicPsaModel self() {
    return this;
  }

  @Override
  Class<BasicPsaModelEntity> entityClass() {
    return BasicPsaModelEntity.class;
  }

  @Override
  Supplier<BasicPsaModelEntity> entitySupplier() {
    return BasicPsaModelEntity::new;
  }

  @Override
  public BasicPsaModel run(BasicPsaSchedule schedule) {
    return null;
  }

  /// 指定されたプロセスで構成されたモデルのインスタンスを生成して返す.
  ///
  /// @param process プロセス.
  /// @return 指定されたプロセスで構成されたモデルのインスタンス.
  /// @throws NullPointerException `process` が `null` の場合.
  public static BasicPsaModel processOf(BasicPsaProcess process) {
    return new BasicPsaModel(process);
  }

}
