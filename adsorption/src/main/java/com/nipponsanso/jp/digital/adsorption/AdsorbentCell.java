/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.Map;

/// 吸着剤を含むセルのスケルトン実装.
///
/// - 解析対象毎に継承された具象クラスの実装を想定
/// - 流体・容器・吸着質・吸着剤を含むセルの従属変数をユーザ管轄外で管理
/// - 流体・容器については親クラス, 吸着質・吸着剤は本クラスが担当
/// - 不変情報は record 式, 可変情報は bean 式でアクセス
/// - 永続化オブジェクトへの読み書きを用意
/// - 内部実装では参照の空間的局所性を意識. 具体的には従属変数をメモリ上で連続的に配置
///
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class AdsorbentCell extends ShellCell {

  /// 吸着剤.
  private final Adsorbent adsorbent;

  /// 吸着剤の比熱の位置.
  private static final int ADSORBENT_SPECIFIC_HEAT = ShellCell.SIZE;

  /// 変数の数.
  static final int SIZE = ADSORBENT_SPECIFIC_HEAT + 1;

  /// 吸着剤を含むセルのインスタンスを生成する.
  ///
  /// @param adsorbent 吸着剤.
  /// @param shell 容器.
  AdsorbentCell(Adsorbent adsorbent, Shell shell) {
    super(shell);
    this.adsorbent = adsorbent;
  }

  /// 粒子径を返す.
  ///
  /// @return diameter 粒子径 (m).
  double particleDiameter() {
    return this.adsorbent.diameter();
  }

  /// 空隙率を返す.
  ///
  ///@return 空隙率 (-).
  double voidFraction() {
    return this.adsorbent.voidFraction();
  }

  /// 嵩密度を返す.
  ///
  /// @return 嵩密度 (kg m<sup>-1</sup>)
  double bulkDensity() {
    return this.adsorbent.bulkDensity();
  }

  /// 吸着剤の比熱を返す.
  ///
  /// @return 吸着剤の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  double getAdsorbentSpecificHeat() {
    return this.doubleArray()[this.offset() + ADSORBENT_SPECIFIC_HEAT];
  }

  /// 吸着剤の比熱を変更する.
  ///
  /// @param adsorbentSpecificHeat 吸着剤の新たな比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
  /// @throws IllegalArgumentException `adsorbentSpecificHeat` が 0 以下の場合
  ///     (`adsorbentSpecificHeat <= 0`).
  void setAdsorbentSpecificHeat(double adsorbentSpecificHeat) {
    Preconditions.checkArgument(adsorbentSpecificHeat > 0.);
    this.doubleArray()[this.offset() + ADSORBENT_SPECIFIC_HEAT] = adsorbentSpecificHeat;
  }

  /// 永続化オブジェクトへ情報を書き込む.
  ///
  /// @param entity 永続化オブジェクト.
  void write(AdsorbentCellEntity<?, ?, ?> entity) {
    super.write(entity);

    entity.setAdsorbentSpecificHeat(this.getAdsorbentSpecificHeat());
  }

  /// 永続化オブジェクトから情報を読み込む.
  ///
  /// @param entity 永続化オブジェクト.
  void read(AdsorbentCellEntity<?, ?, ?> entity) {
    super.read(entity);

    this.setAdsorbentSpecificHeat(entity.getAdsorbentSpecificHeat());
  }

  @Override
  abstract Previous previous();

  @Override
  abstract Map<ComponentKey, ? extends ComponentValue> componentMap();

  /// 吸着剤を含むセルの前ステップ値のスケルトン実装.
  abstract static class Previous extends ShellCell.Previous {

    /// 吸着剤の比熱の位置.
    private static final int ADSORBENT_SPECIFIC_HEAT = ShellCell.Previous.SIZE;

    /// 変数の数.
    static final int SIZE = ADSORBENT_SPECIFIC_HEAT + 1;

    /// 吸着剤を含むセルの前ステップ値のインスタンスを生成する.
    Previous() {}

    /// 吸着剤の比熱を返す.
    ///
    /// @return 吸着剤の比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    double getAdsorbentSpecificHeat() {
      return this.doubleArray()[this.offset() + ADSORBENT_SPECIFIC_HEAT];
    }

    /// 吸着剤の比熱を変更する.
    ///
    /// @param adsorbentSpecificHeat 吸着剤の新たな比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    /// @throws IllegalArgumentException `adsorbentSpecificHeat` が 0 以下の場合
    ///     (`adsorbentSpecificHeat <= 0`).
    void setAdsorbentSpecificHeat(double adsorbentSpecificHeat) {
      Preconditions.checkArgument(adsorbentSpecificHeat > 0.);
      this.doubleArray()[this.offset() + ADSORBENT_SPECIFIC_HEAT] = adsorbentSpecificHeat;
    }

    /// 永続化オブジェクトへ情報を書き込む.
    ///
    /// @param entity 永続化オブジェクト.
    void write(AdsorbentCellEntity.Previous entity) {
      super.write(entity);

      entity.setAdsorbentSpecificHeat(this.getAdsorbentSpecificHeat());
    }

    /// 永続化オブジェクトから情報を読み込む.
    ///
    /// @param entity 永続化オブジェクト.
    void read(AdsorbentCellEntity.Previous entity) {
      super.read(entity);

      this.setAdsorbentSpecificHeat(entity.getAdsorbentSpecificHeat());
    }

  } // Previous

  /// 吸着剤を含むセルの成分値のスケルトン実装.
  abstract static class ComponentValue extends ShellCell.ComponentValue {

    /// 吸着質成分のモル吸着量の位置.
    private static final int MOLAR_QUANTITY = ShellCell.ComponentValue.SIZE;

    /// 吸着質成分のモル定積比熱の位置.
    private static final int MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT
        = MOLAR_QUANTITY + 1;

    /// 対象となる変数の数.
    static int SIZE = MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT + 1;

    /// 吸着剤を含むセルの成分値のインスタンスを生成する.
    ComponentValue() {}

    /// モル吸着量を返す.
    ///
    /// @return モル吸着量 (mol kg<sup>-1</sup>).
    double getMolarQuantity() {
      return this.doubleArray()[this.offset() + MOLAR_QUANTITY];
    }

    /// モル吸着量を変更する.
    ///
    /// @param molarQuantity 新たなモル吸着量 (mol kg<sup>-1</sup>).
    /// @throws IllegalArgumentException `molarQuantity` が 0 以下の場合
    ///     (`molarQuantity <= 0`).
    void setMolarQuantity(double molarQuantity) {
      Preconditions.checkArgument(molarQuantity > 0.);
      this.doubleArray()[this.offset() + MOLAR_QUANTITY] = molarQuantity;
    }

    /// モル定積比熱を返す.
    ///
    /// @return モル定積比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
    double getMolarSpecificConstantVolumeSpecificHeat() {
      return this.doubleArray()[this.offset() + MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT];
    }

    /// モル定積比熱を変更する.
    ///
    /// @param molarSpecificConstantVolumeSpecificHeat 新たなモル定積比熱
    ///                                                (J kg<sup>-1</sup> K<sup>-1</sup>>).
    /// @throws IllegalArgumentException `molarSpecificConstantVolumeSpecificHeat`
    ///     が 0 以下の場合 (`molarSpecificConstantVolumeSpecificHeat <= 0`).
    void setMolarSpecificConstantVolumeSpecificHeat(
        double molarSpecificConstantVolumeSpecificHeat) {
      Preconditions.checkArgument(molarSpecificConstantVolumeSpecificHeat > 0.);
      this.doubleArray()[this.offset() + MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT]
          = molarSpecificConstantVolumeSpecificHeat;
    }

    /// 永続化オブジェクトへ情報を書き込む.
    ///
    /// @param entity 永続化オブジェクト.
    void write(AdsorbentCellEntity.ComponentValue<?> entity) {
      super.write(entity);

      entity.setMolarQuantity(this.getMolarQuantity());
      entity.setMolarSpecificConstantVolumeSpecificHeat(
          this.getMolarSpecificConstantVolumeSpecificHeat());
    }

    /// 永続化オブジェクトから情報を読み込む.
    ///
    /// @param entity 永続化オブジェクト.
    void read(AdsorbentCellEntity.ComponentValue<?> entity) {
      super.read(entity);

      this.setMolarQuantity(entity.getMolarQuantity());
      this.setMolarSpecificConstantVolumeSpecificHeat(
          entity.getMolarSpecificConstantVolumeSpecificHeat());
    }

    @Override
    abstract Previous previous();

    /// 吸着剤を含むセルの成分値の前ステップ値のスケルトン実装.
    abstract static class Previous extends ShellCell.ComponentValue.Previous {

      /// 吸着質成分のモル吸着量の位置.
      private static final int MOLAR_QUANTITY = ShellCell.ComponentValue.Previous.SIZE;

      /// 吸着質成分のモル定積比熱の位置.
      private static final int MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT
          = MOLAR_QUANTITY + 1;

      /// 対象となる変数の数.
      static int SIZE = MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT + 1;

      /// 吸着剤を含むセルの成分値の前ステップ値のインスタンスを生成する.
      Previous() {}

      /// モル吸着量を返す.
      ///
      /// @return モル吸着量 (mol kg<sup>-1</sup>).
      double getMolarQuantity() {
        return this.doubleArray()[this.offset() + MOLAR_QUANTITY];
      }

      /// モル吸着量を変更する.
      ///
      /// @param molarQuantity 新たなモル吸着量 (mol kg<sup>-1</sup>).
      /// @throws IllegalArgumentException `molarQuantity` が 0 以下の場合
      ///     (`molarQuantity <= 0`).
      void setMolarQuantity(double molarQuantity) {
        Preconditions.checkArgument(molarQuantity > 0.);
        this.doubleArray()[this.offset() + MOLAR_QUANTITY] = molarQuantity;
      }

      /// モル定積比熱を返す.
      ///
      /// @return モル定積比熱 (J kg<sup>-1</sup> K<sup>-1</sup>).
      double getMolarSpecificConstantVolumeSpecificHeat() {
        return this.doubleArray()[this.offset() + MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT];
      }

      /// モル定積比熱を変更する.
      ///
      /// @param molarSpecificConstantVolumeSpecificHeat 新たなモル定積比熱
      ///                                                (J kg<sup>-1</sup> K<sup>-1</sup>>).
      /// @throws IllegalArgumentException `molarSpecificConstantVolumeSpecificHeat`
      ///     が 0 以下の場合 (`molarSpecificConstantVolumeSpecificHeat <= 0`).
      void setMolarSpecificConstantVolumeSpecificHeat(
          double molarSpecificConstantVolumeSpecificHeat) {
        Preconditions.checkArgument(molarSpecificConstantVolumeSpecificHeat > 0.);
        this.doubleArray()[this.offset() + MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT]
            = molarSpecificConstantVolumeSpecificHeat;
      }

      /// 永続化オブジェクトへ情報を書き込む.
      ///
      /// @param entity 永続化オブジェクト.
      void write(AdsorbentCellEntity.ComponentValue.Previous entity) {
        super.write(entity);

        entity.setMolarQuantity(this.getMolarQuantity());
        entity.setMolarSpecificConstantVolumeSpecificHeat(
            this.getMolarSpecificConstantVolumeSpecificHeat());
      }

      /// 永続化オブジェクトから情報を読み込む.
      ///
      /// @param entity 永続化オブジェクト.
      void read(AdsorbentCellEntity.ComponentValue.Previous entity) {
        super.read(entity);

        this.setMolarQuantity(entity.getMolarQuantity());
        this.setMolarSpecificConstantVolumeSpecificHeat(
            entity.getMolarSpecificConstantVolumeSpecificHeat());
      }

    } // Previous

  } // ComponentValue

}
