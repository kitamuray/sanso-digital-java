/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

/// ソルバのセル.
///
/// @since 1.1
/// @author KITAMURA Yuichiro
interface SolverCell {

  /// 当該セルに対する離散化係数 <i>a</i><sub><i>p</i></sub> を返す.
  ///
  /// @return 当該セルに対する離散化係数 <i>a</i><sub><i>p</i></sub>.
  double getAp();

  /// 当該セルに対する離散化係数 <i>a</i><sub><i>p</i></sub> を設定する.
  ///
  /// @param ap 当該セルに対する新たな離散化係数 <i>a</i><sub><i>p</i></sub>.
  void setAp(double ap);

  /// 西側セルに対する離散化係数 <i>a</i><sub><i>w</i></sub> を返す.
  ///
  /// @return 西側セルに対する離散化係数 <i>a</i><sub><i>w</i></sub>.
  double getAw();

  /// 西側セルに対する離散化係数 <i>a</i><sub><i>w</i></sub> を設定する.
  ///
  /// @param aw 西側セルに対する新たな離散化係数 <i>a</i><sub><i>w</i></sub>.
  void setAw(double aw);

  /// 西側セルに対する離散化係数 <i>a</i><sub><i>e</i></sub> を返す.
  ///
  /// @return 西側セルに対する離散化係数 <i>a</i><sub><i>e</i></sub>.
  double getAe();

  /// 西側セルに対する離散化係数 <i>a</i><sub><i>e</i></sub> を設定する.
  ///
  /// @param ae 西側セルに対する新たな離散化係数 <i>a</i><sub><i>e</i></sub>.
  void setAe(double ae);

  /// 生成項比例係数 <i>S</i><sub><i>p</i></sub> を返す.
  ///
  /// @return 生成項比例係数 <i>S</i><sub><i>p</i></sub>.
  double getSp();

  /// 生成項比例係数 <i>S</i><sub><i>p</i></sub> を設定する.
  ///
  /// @param sp 新たな生成項比例係数 <i>S</i><sub><i>p</i></sub>.
  void setSp(double sp);

  /// 生成項定数係数 <i>S</i><sub><i>c</i></sub> を返す.
  ///
  /// @return 生成項定数係数 <i>S</i><sub><i>c</i></sub>.
  double getSc();

  /// 生成項定数係数 <i>S</i><sub><i>c</i></sub> を設定する.
  ///
  /// @param sc 新たな生成項定数係数 <i>S</i><sub><i>c</i></sub>.
  void setSc(double sc);

}
