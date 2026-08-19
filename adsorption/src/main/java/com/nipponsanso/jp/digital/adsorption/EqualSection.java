/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.MoreObjects;
import java.util.stream.DoubleStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/// 等分割区間.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class EqualSection extends Section {

  private static final Logger LOGGER = LogManager.getLogger(EqualSection.class);

  /// 等分割区間のビルダ.
  public static final class Builder extends Section.Builder<Builder> {

    /// 等分割区間のビルダのインスタンスを生成する.
    private Builder() {}

    /// {@inheritDoc}
    ///
    /// @return {@inheritDoc}
    @Override
    Builder self() {
      return this;
    }

    /// {@inheritDoc}
    ///
    /// @return {@inheritDoc}
    @Override
    public EqualSection build() {
      this.validate();
      return new EqualSection(this);
    }

  } // Builder

  /// 線分長さの配列.
  private final double[] segments;

  /// 等分割区間のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private EqualSection(Builder builder) {
    super(builder);

    final double segment = this.length() / this.number();
    this.segments = DoubleStream.generate(() -> segment)
        .limit(this.number())
        .toArray();

    LOGGER.debug("segments = {}", this.segments);
  }

  /// ビルダのインスタンスを生成して返す.
  ///
  /// @return ビルダのインスタンス.
  public static Builder builder() {
    return new Builder();
  }

  /// {@inheritDoc}
  ///
  /// @return {@inheritDoc}
  @Override
  public double[] segments() {
    return this.segments.clone();
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("length", this.length())
        .add("number", this.number())
        .toString();
  }

}
