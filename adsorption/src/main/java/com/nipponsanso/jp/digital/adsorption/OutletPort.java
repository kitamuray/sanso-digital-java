/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/// 流出境界条件.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class OutletPort extends Port {

  /// 流出境界条件のビルダ.
  public static final class Builder extends Port.Builder<Builder> {

    /// 流出境界条件のビルダのインスタンスを生成する.
    private Builder() {}

    @Override
    Builder self() {
      return Builder.this;
    }

    /// 流出境界条件のインスタンスを生成して返す.
    ///
    /// @return 流出境界条件のインスタンス.
    @Override
    public OutletPort build() {
      return new OutletPort(this);
    }

  } // Builder

  /// 流出条件のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private OutletPort(Builder builder) {
    super(builder);
  }

  /// ビルダのインスタンスを生成して返す.
  ///
  /// @return ビルダのインスタンス.
  public static Builder builder() {
    return new Builder();
  }

  @Override
  void set(PortCell cell) {
    cell.setPressure(this.pressure());
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("pressure", this.pressure())
        .add("monitorMap", this.monitorMap())
        .toString();
  }

}
