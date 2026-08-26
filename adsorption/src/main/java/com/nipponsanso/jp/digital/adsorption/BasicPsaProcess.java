/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/// 基本 PSA のプロセス.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class BasicPsaProcess {

  private static final Logger LOGGER = LogManager.getLogger(BasicPsaProcess.class);

  /// 基本 PSA のプロセスのビルダ.
  public static final class Builder {

    /// 組成.
    private Composition composition;

    /// 基本 PSA のプロセスのビルダのインスタンスを生成する.
    private Builder() {}

    /// 組成を設定する.
    ///
    /// @param composition 組成.
    /// @return ビルダのインスタンス.
    public Builder composition(Composition composition) {
      this.composition = Preconditions.checkNotNull(composition);
      return this;
    }

    /// 基本 PSA のプロセスのインスタンスを生成して返す.
    ///
    /// @return 基本 PSA のプロセスのインスタンス.
    /// @throws IllegalStateException ビルダが不完全な場合.
    public BasicPsaProcess build() {
      Preconditions.checkState(this.composition != null);

      return new BasicPsaProcess(this);
    }

  } // Builder

  /// 組成.
  private final Composition composition;

  /// 基本 PSA のプロセスのインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private BasicPsaProcess(Builder builder) {
    this.composition = builder.composition;
  }

  /// 組成を返す.
  ///
  /// @return 組成.
  public Composition composition() {
    return this.composition;
  }

}
