/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

/// 基本 PSA サービス.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class BasicPsaService {

  /// 基本 PSA サービスのインスタンスを生成する.
  BasicPsaService() {}

  /// 初期化を行う.
  ///
  /// @param process プロセス.
  /// @param model モデル.
  void initialize(BasicPsaProcess process, BasicPsaModel model) {}

}
