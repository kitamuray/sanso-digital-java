/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;

/// 吸着筒.
///
/// @param shape 形状.
/// @param origin 原点座標 (m).
/// @param shell 容器.
/// @param spaceList 空間の不変リスト.
/// @param zoneListList 領域の不変リストの不変リスト.
public record Column(
    ColumnShape shape,
    double origin,
    Shell shell,
    List<ColumnSpace> spaceList,
    List<List<ColumnZone>> zoneListList) {

  /// 吸着筒のビルダ.
  public static final class Builder {

    /// 形状.
    private ColumnShape shape;

    /// 原点座標 (m).
    private double origin;

    /// 容器.
    private Shell shell;

    /// 空間のリスト.
    private final List<ColumnSpace> spaceList = new ArrayList<>();

    /// 領域のリストのリスト.
    private final List<List<ColumnZone>> zoneListList = new ArrayList<>();

    /// 吸着筒のビルダのインスタンスを生成する.
    private Builder() {}

    /// 形状を設定する.
    ///
    /// @param shape 形状.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `shape` が `null` の場合.
    public Builder shape(ColumnShape shape) {
      this.shape = Preconditions.checkNotNull(shape);
      return this;
    }

    /// 原点座標を設定する.
    ///
    /// @param origin 原点座標 (m).
    /// @return ビルダのインスタンス.
    public Builder origin(double origin) {
      this.origin = origin;
      return this;
    }

    /// 容器を設定する.
    ///
    /// @param shell 容器.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `shell` が `null` の場合.
    public Builder shell(Shell shell) {
      this.shell = Preconditions.checkNotNull(shell);
      return this;
    }

    /// 空間を追加する.
    ///
    /// @param space 空間.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `space` が `null` の場合.
    /// @throws IllegalStateException ビルダが空間を追加出来ない状態の場合.
    public Builder space(ColumnSpace space) {
      Preconditions.checkNotNull(space);
      Preconditions.checkState(this.zoneListList.size() == this.spaceList.size());

      this.spaceList.add(space);
      return this;
    }

    /// 領域を追加する.
    ///
    /// @param zone 領域.
    /// @return ビルダのインスタンス.
    /// @throws NullPointerException `zone` が `null` の場合.
    /// @throws IllegalStateException ビルダが領域を追加出来ない状態の場合.
    public Builder zone(ColumnZone zone) {
      Preconditions.checkNotNull(zone);
      Preconditions.checkState(!this.spaceList.isEmpty());

      if (this.zoneListList.size() < this.spaceList.size()) {
        this.zoneListList.add(new ArrayList<>());
      }

      this.zoneListList.getLast().add(zone);
      return this;
    }

    /// 吸着筒のインスタンスを生成して返す.
    ///
    /// @return 吸着筒のインスタンス.
    /// @throws IllegalStateException ビルダが不完全な場合.
    public Column build() {
      Preconditions.checkState(this.shape != null);
      Preconditions.checkState(this.shell != null);
      Preconditions.checkState(this.spaceList.size() >= 2);
      Preconditions.checkState(this.zoneListList.size() < this.spaceList.size());

      return new Column(this);
    }

  } // Builder

  /// 吸着筒のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private Column(Builder builder) {
    this(
        builder.shape,
        builder.origin,
        builder.shell,
        builder.spaceList,
        builder.zoneListList);
  }

  /// 吸着筒のビルダのインスタンスを生成して返す.
  ///
  /// @return 吸着筒のビルダのインスタンス.
  public static Builder builder() {
    return new Builder();
  }

}
