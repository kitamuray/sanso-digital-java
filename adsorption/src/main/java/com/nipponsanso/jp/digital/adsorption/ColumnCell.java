/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.Map;

/// 吸着筒のセルのスケルトン実装.
///
/// - 前ステップ値・成分マップのメソッドを実装, 吸着筒毎に具象クラスの実装を想定
/// - 幾何情報・離散化係数・流体・容器・吸着質・吸着剤を含むセルの従属変数をユーザ管轄外で管理
/// - 幾何情報・離散化係数については親クラス, 流体・容器・吸着質・吸着剤は本クラスが担当
/// - 不変情報は record 式, 可変情報は bean 式でアクセス
/// - 永続化オブジェクトへの読み書きを用意
/// - 内部実装では参照の空間的局所性を意識. 具体的には従属変数をメモリ上で連続的に配置
///
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class ColumnCell extends AdsorbentCell implements GridCell, SolverCell {

  // 実装上の留意事項：
  //   幾何情報は不変情報だが, セル毎に異なる情報であるため,
  //   他の従属変数と同様に参照の空間的局所性を意識した.

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

    this.doubleArray()[this.offset + CENTROID] = 0.5 * (fromX + toX);
    this.doubleArray()[this.offset + LENGTH] = toX - fromX;
    this.doubleArray()[this.offset + FLUID_VOLUME] = shape.fluidVolume(fromX, toX);
    this.doubleArray()[this.offset + SHELL_VOLUME] = shape.shellVolume(fromX, toX);
    this.doubleArray()[this.offset + INSIDE_AREA] = shape.insideArea(fromX, toX);
    this.doubleArray()[this.offset + OUTSIDE_AREA] = shape.outsideArea(fromX, toX);

    this.previous = new Previous();

    Map<ComponentKey, ComponentValue> componentMap = new EnumMap<>(ComponentKey.class);
    offset += size();
    for (var componentKey : composition.componentKeySet()) {
      componentMap.put(componentKey, new ComponentValue(offset));
      offset += ComponentValue.size();
    }

    this.componentMap = Maps.immutableEnumMap(componentMap);
  }

  @Override
  public double centroid() {
    return this.doubleArray()[this.offset() + CENTROID];
  }

  @Override
  public double length() {
    return this.doubleArray()[this.offset() + LENGTH];
  }

  @Override
  public double fluidVolume() {
    return this.doubleArray()[this.offset() + FLUID_VOLUME];
  }

  @Override
  public double shellVolume() {
    return this.doubleArray()[this.offset() + SHELL_VOLUME];
  }

  @Override
  public double insideArea() {
    return this.doubleArray()[this.offset() + INSIDE_AREA];
  }

  @Override
  public double outsideArea() {
    return this.doubleArray()[this.offset() + OUTSIDE_AREA];
  }

  @Override
  public double getAp() {
    return this.doubleArray()[this.offset() + AP];
  }

  @Override
  public void setAp(double ap) {
    this.doubleArray()[this.offset() + AP] = ap;
  }

  @Override
  public double getAw() {
    return this.doubleArray()[this.offset() + AW];
  }

  @Override
  public void setAw(double aw) {
    this.doubleArray()[this.offset() + AW] = aw;
  }

  @Override
  public double getAe() {
    return this.doubleArray()[this.offset() + AE];
  }

  @Override
  public void setAe(double ae) {
    this.doubleArray()[this.offset() + AE] = ae;
  }

  @Override
  public double getSp() {
    return this.doubleArray()[this.offset() + SP];
  }

  @Override
  public void setSp(double sp) {
    this.doubleArray()[this.offset() + SP] = sp;
  }

  @Override
  public double getSc() {
    return this.doubleArray()[this.offset() + SC];
  }

  @Override
  public void setSc(double sc) {
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

  @Override
  Previous previous() {
    return this.previous;
  }

  @Override
  Map<ComponentKey, ComponentValue> componentMap() {
    return this.componentMap;
  }

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

  /// 吸着筒セルの前ステップ値のスケルトン実装.
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

    @Override
    int offset() {
      return ColumnCell.this.offset() + ColumnCell.SIZE;
    }

    @Override
    double[] doubleArray() {
      return ColumnCell.this.doubleArray();
    }

  } // Previous

  /// 吸着筒セルの成分値のスケルトン実装.
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

    @Override
    Previous previous() {
      return this.previous;
    }

    @Override
    int offset() {
      return this.offset;
    }

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

    /// 吸着筒セルの成分値の前ステップ値のスケルトン実装.
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

      @Override
      int offset() {
        return ComponentValue.this.offset() + ComponentValue.SIZE;
      }

      @Override
      double[] doubleArray() {
        return ColumnCell.this.doubleArray();
      }

    } // Previous

  } // ComponentValue

}
