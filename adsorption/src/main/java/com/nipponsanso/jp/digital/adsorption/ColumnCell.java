/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.Map;

/// 吸着筒のセル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class ColumnCell extends AdsorbentCell {

  /// 重心座標 (m) の位置.
  private static final int CENTROID = AdsorbentCell.SIZE;

  /// 長さ (m) の位置.
  private static final int LENGTH = CENTROID + 1;

  /// 流体領域の体積 (m<sup>3</sup>) の位置.
  private static final int FLUID_VOLUME = LENGTH + 1;

  /// 容器領域の体積 (m<sup>3</sup>) の位置.
  private static final int SHELL_VOLUME = FLUID_VOLUME + 1;

  /// 容器内面の面積 (m<sup>2</sup>) の位置.
  private static final int INSIDE_AREA = SHELL_VOLUME + 1;

  /// 容器外面の面積 (m<sup>2</sup>) の位置.
  private static final int OUTSIDE_AREA = INSIDE_AREA + 1;

  /// 当該セルの離散化係数の位置.
  private static final int AP = OUTSIDE_AREA + 1;

  /// 西側セルの離散化係数の位置.
  private static final int AW = AP + 1;

  /// 東側セルの離散化係数の位置.
  private static final int AE = AW + 1;

  /// 生成項比例係数の位置.
  private static final int SP = AE + 1;

  /// 生成項定数係数の位置.
  private static final int SC = SP + 1;

  /// 変数の数.
  static final int SIZE = SC + 1;

  /// オフセット値.
  private final int offset;

  /// 前ステップ値.
  private final Previous previous;

  /// 成分の不変マップ.
  private final Map<ComponentKey, ComponentValue> componentMap;

  /// 吸着筒セルのインスタンスを生成する.
  ///
  /// @param offset オフセット値.
  /// @param shape 形状.
  /// @param fromX 始点座標 (m).
  /// @param toX 終点座標 (m).
  /// @param composition 組成.
  /// @param adsorbent 吸着剤.
  /// @param shell 容器.
  ColumnCell(
      int offset,
      ColumnShape shape, double fromX, double toX,
      Composition composition, Adsorbent adsorbent, Shell shell) {
    super(adsorbent, shell);
    this.offset = offset;

    this.doubleArray()[this.offset() + CENTROID] = 0.5 * (fromX + toX);
    this.doubleArray()[this.offset() + LENGTH] = toX - fromX;
    this.doubleArray()[this.offset() + FLUID_VOLUME] = shape.fluidVolume(fromX, toX);
    this.doubleArray()[this.offset() + SHELL_VOLUME] = shape.shellVolume(fromX, toX);
    this.doubleArray()[this.offset() + INSIDE_AREA] = shape.insideArea(fromX, toX);
    this.doubleArray()[this.offset() + OUTSIDE_AREA] = shape.outsideArea(fromX, toX);

    this.previous = new Previous();

    Map<ComponentKey, ComponentValue> componentMap = new EnumMap<>(ComponentKey.class);
    offset += size();
    for (var componentKey : composition.componentKeySet()) {
      componentMap.put(componentKey, new ComponentValue(offset));
      offset += ComponentValue.size();
    }

    this.componentMap = Maps.immutableEnumMap(componentMap);
  }

  /// 重心座標を返す.
  ///
  /// @return 重心座標 (m).
  double centroid() {
    return this.doubleArray()[this.offset() + CENTROID];
  }

  /// 長さを返す.
  ///
  /// @return 長さ (m).
  double length() {
    return this.doubleArray()[this.offset() + LENGTH];
  }

  /// 流体領域の体積を返す.
  ///
  /// @return 流体領域の体積 (m<sup>3</sup>).
  double fluidVolume() {
    return this.doubleArray()[this.offset() + FLUID_VOLUME];
  }

  /// 容器内面の面積を返す.
  ///
  /// @return 容器内面の面積 (m<sup>2</sup>).
  double insideArea() {
    return this.doubleArray()[this.offset() + INSIDE_AREA];
  }

  /// 容器外面の面積を返す.
  ///
  /// @return 容器外面の面積 (m<sup>2</sup>).
  double outsideArea() {
    return this.doubleArray()[this.offset() + OUTSIDE_AREA];
  }

  /// 当該セルに対する離散化係数 <i>a</i><sub><i>p</i></sub> を返す.
  ///
  /// @return 当該セルに対する離散化係数 <i>a</i><sub><i>p</i></sub>.
  double getAp() {
    return this.doubleArray()[this.offset() + AP];
  }

  /// 当該セルに対する離散化係数 <i>a</i><sub><i>p</i></sub> を設定する.
  ///
  /// @param ap 当該セルに対する新たな離散化係数 <i>a</i><sub><i>p</i></sub>.
  void setAp(double ap) {
    this.doubleArray()[this.offset() + AP] = ap;
  }

  /// 西側セルに対する離散化係数 <i>a</i><sub><i>w</i></sub> を返す.
  ///
  /// @return 西側セルに対する離散化係数 <i>a</i><sub><i>w</i></sub>.
  double getAw() {
    return this.doubleArray()[this.offset() + AW];
  }

  /// 西側セルに対する離散化係数 <i>a</i><sub><i>w</i></sub> を設定する.
  ///
  /// @param aw 西側セルに対する新たな離散化係数 <i>a</i><sub><i>w</i></sub>.
  void setAw(double aw) {
    this.doubleArray()[this.offset() + AW] = aw;
  }

  /// 西側セルに対する離散化係数 <i>a</i><sub><i>e</i></sub> を返す.
  ///
  /// @return 西側セルに対する離散化係数 <i>a</i><sub><i>e</i></sub>.
  double getAe() {
    return this.doubleArray()[this.offset() + AE];
  }

  /// 西側セルに対する離散化係数 <i>a</i><sub><i>e</i></sub> を設定する.
  ///
  /// @param ae 西側セルに対する新たな離散化係数 <i>a</i><sub><i>e</i></sub>.
  void setAe(double ae) {
    this.doubleArray()[this.offset() + AE] = ae;
  }

  /// 生成項比例係数 <i>S</i><sub><i>p</i></sub> を返す.
  ///
  /// @return 生成項比例係数 <i>S</i><sub><i>p</i></sub>.
  double getSp() {
    return this.doubleArray()[this.offset() + SP];
  }

  /// 生成項比例係数 <i>S</i><sub><i>p</i></sub> を設定する.
  ///
  /// @param sp 新たな生成項比例係数 <i>S</i><sub><i>p</i></sub>.
  void setSp(double sp) {
    this.doubleArray()[this.offset() + SP] = sp;
  }

  /// 生成項定数係数 <i>S</i><sub><i>c</i></sub> を返す.
  ///
  /// @return 生成項定数係数 <i>S</i><sub><i>c</i></sub>.
  double getSc() {
    return this.doubleArray()[this.offset() + SC];
  }

  /// 生成項定数係数 <i>S</i><sub><i>c</i></sub> を設定する.
  ///
  /// @param sc 新たな生成項定数係数 <i>S</i><sub><i>c</i></sub>.
  void setSc(double sc) {
    this.doubleArray()[this.offset() + SC] = sc;
  }

  /// 永続化オブジェクトへ情報を再帰的に書き込む.
  ///
  /// @param entity 永続化オブジェクト.
  void write(ColumnCellEntity entity) {
    super.write(entity);

    this.previous.write(entity.getPreviousEntity());
    this.componentMap.forEach(
        (key, value) -> value.write(
            entity.getComponentEntityMap().computeIfAbsent(
                key, _ -> new ColumnCellEntity.ComponentValue())));
  }

  /// 永続化オブジェクトから情報を再帰的に読み込む.
  ///
  /// @param entity 永続化オブジェクト.
  void read(ColumnCellEntity entity) {
    super.read(entity);

    this.previous.read(entity.getPreviousEntity());
    this.componentMap.forEach(
        (key, value) -> value.read(
            entity.getComponentEntityMap().get(key)));
  }

  /// {@inheritDoc}
  ///
  /// @return {@inheritDoc}
  @Override
  Previous previous() {
    return this.previous;
  }

  /// {@inheritDoc}
  ///
  /// @return {@inheritDoc}
  @Override
  Map<ComponentKey, ComponentValue> componentMap() {
    return this.componentMap;
  }

  /// {@inheritDoc}
  ///
  /// @return {@inheritDoc}
  @Override
  int offset() {
    return this.offset;
  }

  /// 成分値以外における変数の数を返す.
  ///
  /// @return 成分値以外における変数の数.
  private static int size() {
    return SIZE + Previous.SIZE;
  }

  /// すべての変数の数を返す.
  ///
  /// @return すべての変数の数.
  static int size(Composition composition) {
    return size() + composition.componentKeySet().size() * ComponentValue.size();
  }

  /// 吸着筒セルの前ステップ値.
  final class Previous extends AdsorbentCell.Previous {

    /// 変数の数.
    private static final int SIZE = AdsorbentCell.Previous.SIZE;

    /// 吸着筒セルの前ステップ値のインスタンスを生成する.
    private Previous() {}

    /// 永続化オブジェクトへ情報を再帰的に書き込む.
    ///
    /// @param entity 永続化オブジェクト.
    private void write(ColumnCellEntity.Previous entity) {
      super.write(entity);
    }

    /// 永続化オブジェクトから情報を再帰的に読み込む.
    ///
    /// @param entity 永続化オブジェクト.
    private void read(ColumnCellEntity.Previous entity) {
      super.read(entity);
    }

    /// {@inheritDoc}
    ///
    /// @return {@inheritDoc}
    @Override
    int offset() {
      return ColumnCell.this.offset() + ColumnCell.SIZE;
    }

    /// {@inheritDoc}
    ///
    /// @return {@inheritDoc}
    @Override
    double[] doubleArray() {
      return ColumnCell.this.doubleArray();
    }

  } // Previous

  /// 吸着筒セルの成分値.
  final class ComponentValue extends AdsorbentCell.ComponentValue {

    /// 変数の数.
    private static final int SIZE = AdsorbentCell.ComponentValue.SIZE;

    /// オフセット値.
    private final int offset;

    /// 前ステップ値.
    private final Previous previous;

    /// 吸着筒セルの成分値のインスタンスを生成する.
    ///
    /// @param offset オフセット値.
    private ComponentValue(int offset) {
      this.offset = offset;
      this.previous = new Previous();
    }

    /// 永続化オブジェクトへ情報を再帰的に書き込む.
    ///
    /// @param entity 永続化オブジェクト.
    private void write(ColumnCellEntity.ComponentValue entity) {
      super.write(entity);

      this.previous.write(entity.getPreviousEntity());
    }

    /// 永続化オブジェクトから情報を再帰的に読み込む.
    ///
    /// @param entity 永続化オブジェクト.
    private void read(ColumnCellEntity.ComponentValue entity) {
      super.read(entity);

      this.previous.read(entity.getPreviousEntity());
    }

    /// {@inheritDoc}
    ///
    /// @return {@inheritDoc}
    @Override
    Previous previous() {
      return this.previous;
    }

    /// {@inheritDoc}
    ///
    /// @return {@inheritDoc}
    @Override
    int offset() {
      return this.offset;
    }

    /// {@inheritDoc}
    ///
    /// @return {@inheritDoc}
    @Override
    double[] doubleArray() {
      return ColumnCell.this.doubleArray();
    }

    /// 成分値全体における変数の数を返す.
    ///
    /// @return 成分値全体における変数の数.
    private static int size() {
      return SIZE + Previous.SIZE;
    }

    /// 吸着筒セルの成分値の前ステップ値.
    final class Previous extends AdsorbentCell.ComponentValue.Previous {

      /// 変数の数.
      private static final int SIZE = AdsorbentCell.ComponentValue.Previous.SIZE;

      /// 吸着筒セルの成分値の前ステップ値のインスタンスを生成する.
      private Previous() {}

      /// 永続化オブジェクトへ情報を再帰的に書き込む.
      ///
      /// @param entity 永続化オブジェクト.
      private void write(ColumnCellEntity.ComponentValue.Previous entity) {
        super.write(entity);
      }

      /// 永続化オブジェクトから情報を再帰的に読み込む.
      ///
      /// @param entity 永続化オブジェクト.
      private void read(ColumnCellEntity.ComponentValue.Previous entity) {
        super.read(entity);
      }

      /// {@inheritDoc}
      ///
      /// @return {@inheritDoc}
      @Override
      int offset() {
        return ComponentValue.this.offset() + ComponentValue.SIZE;
      }

      /// {@inheritDoc}
      ///
      /// @return {@inheritDoc}
      @Override
      double[] doubleArray() {
        return ColumnCell.this.doubleArray();
      }

    } // Previous

  } // ComponentValue

}
