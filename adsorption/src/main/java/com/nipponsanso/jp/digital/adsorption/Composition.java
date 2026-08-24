/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Stream;

/// 組成.
///
/// @param majorComponentKey 主成分のキー.
/// @param minorComponentKeySet 副成分のキーの不変セット.
/// @param componentKeySet 全成分のキーの不変セット.
public record Composition(
    ComponentKey majorComponentKey,
    Set<ComponentKey> minorComponentKeySet,
    Set<ComponentKey> componentKeySet) {

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

  /// 組成のインスタンスを生成する.
  ///
  /// @param builder ビルダ.
  private Composition(Builder builder) {
    this(
        builder.majorComponentKey,
        builder.minorComponentKeySet,
        Stream.concat(
            Stream.of(
                builder.majorComponentKey),
                builder.minorComponentKeySet.stream())
            .collect(Sets.toImmutableEnumSet()));
  }

  /// ビルダのインスタンスを生成して返す.
  ///
  /// @return ビルダのインスタンス.
  public static Builder builder() {
    return new Builder();
  }

}
