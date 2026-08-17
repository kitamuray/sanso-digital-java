/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/// 組成.
///
/// このクラスのインスタンスは不変（immutable）である.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class Composition {

  private static final Logger LOGGER = LogManager.getLogger(Composition.class);

  /// 組成のビルダ.
  public static final class Builder {

    /// 主成分のキー.
    private ComponentKey majorComponentKey;

    /// 副成分のキーのセット.
    private final Set<ComponentKey> minorComponentKeySet = EnumSet.noneOf(ComponentKey.class);

    /// 組成のビルダのインスタンスを生成する.
    private Builder() {}

    /// 主成分のキーを設定する.
    ///
    /// @param majorComponentKey 主成分のキー.
    /// @return ビルダのインスタンス自身.
    /// @throws NullPointerException `majorComponentKey` が `null` の場合.
    /// @throws IllegalArgumentException `majorComponentKey` が副成分として設定済の場合.
    public Builder majorComponentKey(ComponentKey majorComponentKey) {
      Preconditions.checkNotNull(majorComponentKey);
      Preconditions.checkArgument(!this.minorComponentKeySet.contains(majorComponentKey));

      this.majorComponentKey = majorComponentKey;

      return this;
    }

    /// 副成分のキーを追加する.
    ///
    /// @param minorComponentKey 副成分のキー.
    /// @return ビルダのインスタンス自身.
    /// @throws NullPointerException `minorComponentKey` が `null` の場合.
    /// @throws IllegalArgumentException `minorComponentKey` が主成分または副成分として設定済の場合.
    public Builder minorComponentKey(ComponentKey minorComponentKey) {
      Preconditions.checkNotNull(minorComponentKey);
      Preconditions.checkArgument(minorComponentKey != this.majorComponentKey);
      Preconditions.checkArgument(!this.minorComponentKeySet.contains(minorComponentKey));

      this.minorComponentKeySet.add(Preconditions.checkNotNull(minorComponentKey));

      return this;
    }

    /// 組成のインスタンスを生成して返す.
    ///
    /// @return 組成のインスタンス.
    /// @throws IllegalStateException 主成分のキーが未設定の場合.
    public Composition build() {
      Preconditions.checkState(this.majorComponentKey != null);

      return new Composition(this);
    }

  } // Builder

  /// 主成分のキー.
  private final ComponentKey majorComponentKey;

  /// 副成分のキーの不変セット.
  private final Set<ComponentKey> minorComponentKeySet;

  /// 全成分のキーの不変セット.
  private final Set<ComponentKey> componentKeySet;

  /// 組成のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private Composition(Builder builder) {
    this.majorComponentKey = builder.majorComponentKey;
    this.minorComponentKeySet = Sets.immutableEnumSet(builder.minorComponentKeySet);

    LOGGER.debug("majorComponentKey = {}", this.majorComponentKey);
    LOGGER.debug("minorComponentKeySet = {}", this.minorComponentKeySet);

    this.componentKeySet =
        Stream.concat(Stream.of(this.majorComponentKey), this.minorComponentKeySet.stream())
            .collect(Sets.toImmutableEnumSet());

    LOGGER.trace("componentKey = {}", this.componentKeySet);
  }

  /// ビルダのインスタンスを生成して返す.
  ///
  /// @return ビルダのインスタンス.
  public static Builder builder() {
    return new Builder();
  }

  /// 主成分のキーを返す.
  ///
  /// @return 主成分のキー.
  public ComponentKey majorComponentKey() {
    return this.majorComponentKey;
  }

  /// 副成分のキーの不変セットを返す.
  ///
  /// @return 副成分のキーの不変セット.
  public Set<ComponentKey> minorComponentKeySet() {
    return this.minorComponentKeySet;
  }

  /// 全成分のキーの不変セットを返す.
  ///
  /// @return 全成分のキーの不変セット.
  public Set<ComponentKey> componentKeySet() {
    return this.componentKeySet;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    Composition that = (Composition) o;

    return that.majorComponentKey == this.majorComponentKey
        && this.minorComponentKeySet.equals(that.minorComponentKeySet);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.getClass(),
        this.majorComponentKey,
        this.minorComponentKeySet);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("majorComponentKey", this.majorComponentKey)
        .add("minorComponentKeySet", this.minorComponentKeySet)
        .toString();
  }

}
