/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/// 区間.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public abstract class Section {

  private static final Logger LOGGER = LogManager.getLogger(Section.class);

  /// 区間のビルダ.
  ///
  /// @param <B> ビルダの型.
  public abstract static class Builder<B extends Builder<B>> {

    /// 長さ.
    private double length;

    /// 分割数.
    private int number;

    /// 区間のビルダのインスタンスを生成する.
    Builder() {}

    /// 長さを設定する.
    ///
    /// @param length 長さ.
    /// @return ビルダのインスタンス.
    public final B length(double length) {
      Preconditions.checkArgument(length > 0.);
      this.length = length;
      return this.self();
    }

    /// 分割数を設定する.
    ///
    /// @param number 分割数.
    /// @return ビルダのインスタンス.
    public final B number(int number) {
      Preconditions.checkArgument(number > 0);
      this.number = number;
      return this.self();
    }

    /// ビルダのインスタンス自身を返す.
    ///
    /// @return ビルダのインスタンス自身.
    abstract B self();

    /// インスタンスフィールド値の妥当性を検査する.
    ///
    /// @throws IllegalStateException インスタンスフィールド値が妥当ではない場合.
    void validate() {
      Preconditions.checkState(this.length > 0.);
      Preconditions.checkState(this.number > 0);
    }

    /// 区間のインスタンスを生成して返す.
    ///
    /// @return 区間のインスタンス.
    public abstract Section build();

  } // Builder

  /// 長さ.
  private final double length;

  /// 分割数.
  private final int number;

  /// 区間のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  Section(Builder<?> builder) {
    this.length = builder.length;
    this.number = builder.number;

    LOGGER.debug("length = {}", this.length);
    LOGGER.debug("number = {}", this.number);
  }

  /// 長さを返す.
  ///
  /// @return 長さ.
  public final double length() {
    return this.length;
  }

  /// 分割数を返す.
  ///
  /// @return 分割数.
  public final int number() {
    return this.number;
  }

  /// 線分長さの配列を返す.
  ///
  /// このメソッドに副作用は無い.
  ///
  /// @return 線分長さの配列.
  public abstract double[] segments();

  /// 線分長さの不変リストを返す.
  ///
  /// このメソッドに副作用は無い.
  ///
  /// @return 線分長さの不変リスト.
  public final List<Double> segmentList() {
    return Arrays.stream(this.segments())
        .boxed()
        .collect(ImmutableList.toImmutableList());
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    Section that = (Section) o;

    return Double.compare(this.length, that.length) == 0
        && that.number == this.number;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.length,
        this.number);
  }

}
