/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

/// 貯槽モデル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
final class TankModel {

  /// セル.
  private final TankCell cell;

  /// 貯槽モデルのインスタンスを生成する.
  ///
  /// @param tank 貯槽.
  /// @param composition 組成.
  TankModel(Tank tank, Composition composition) {
    this.cell = new TankCell(composition);
  }

  /// セルを返す.
  TankCell cell() {
    return this.cell;
  }

  /// 指定された永続化オブジェクトへ情報を書き込む.
  ///
  /// @param entity 永続化オブジェクト.
  void write(TankModelEntity entity) {
    this.cell.write(entity.getCellEntity());
  }

  /// 指定された永続化オブジェクトから情報を読み出す.
  ///
  /// @param entity 永続化オブジェクト.
  void read(TankModelEntity entity) {
    this.cell.read(entity.getCellEntity());
  }

}
