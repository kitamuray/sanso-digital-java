/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/// 吸着筒.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class Column {

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

      return new Column(this);
    }

  } // Builder

  /// 形状.
  private final ColumnShape shape;

  /// 原点座標 (m).
  private final double origin;

  /// 容器.
  private final Shell shell;

  /// 空間の不変リスト.
  private final List<ColumnSpace> spaceList;

  /// 領域の不変リストの不変リスト.
  private final List<List<ColumnZone>> zoneListList;

  private Column(Builder builder) {
    this.shape = builder.shape;
    this.origin = builder.origin;
    this.shell = builder.shell;
    this.spaceList = ImmutableList.copyOf(builder.spaceList);
    this.zoneListList = builder.zoneListList.stream()
        .map(ImmutableList::copyOf)
        .collect(ImmutableList.toImmutableList());
  }

  /// 吸着筒のビルダのインスタンスを生成して返す.
  ///
  /// @return 吸着筒のビルダのインスタンス.
  public static Builder builder() {
    return new Builder();
  }

  /// 形状を返す.
  ///
  /// @return 形状.
  public ColumnShape shape() {
    return this.shape;
  }

  /// 原点座標を返す.
  ///
  /// @return 原点座標 (m).
  public double origin() {
    return this.origin;
  }

  /// 容器を返す.
  ///
  /// @return 容器.
  public Shell shell() {
    return this.shell;
  }

  /// 空間の不変リストを返す.
  ///
  /// @return 空間の不変リスト.
  public List<ColumnSpace> spaceList() {
    return this.spaceList;
  }

  /// 領域の不変リストの不変リストを返す.
  ///
  /// @return 領域の不変リストの不変リスト.
  public List<List<ColumnZone>> zoneListList() {
    return this.zoneListList;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    Column that = (Column) o;

    return Double.compare(origin, that.origin) == 0
        && this.shape.equals(that.shape)
        && this.shell.equals(that.shell)
        && this.spaceList.equals(that.spaceList)
        && this.zoneListList.equals(that.zoneListList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.shape,
        this.origin,
        this.shell,
        this.spaceList,
        this.zoneListList);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("shape", this.shape)
        .add("origin", this.origin)
        .add("shell", this.shell)
        .add("spaceList", this.spaceList)
        .add("zoneListList", this.zoneListList)
        .toString();
  }

}
