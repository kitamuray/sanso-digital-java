/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.google.common.base.MoreObjects;
import com.google.common.testing.EqualsTester;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [Column] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class ColumnTest {

  /// [Column.Builder] のテスト.
  @Nested
  class BuilderTest {

    /// [Column.Builder#shape(ColumnShape)] のテスト.
    @Test
    void testShapeColumnShape() {
      assertThatThrownBy(() -> Column.builder().shape(null))
          .isInstanceOf(NullPointerException.class);
    }

    /// [Column.Builder#shape()] のテスト.
    @Test
    void testShellShell() {
      assertThatThrownBy(() -> Column.builder().shell(null))
          .isInstanceOf(NullPointerException.class);
    }

    @Mock
    private ColumnSpace space;

    /// [Column.Builder#space(ColumnSpace)] のテスト.
    @Test
    void testSpaceColumnSpace() {
      assertThatThrownBy(() -> Column.builder().space(null))
          .isInstanceOf(NullPointerException.class);
      assertThatThrownBy(() -> Column.builder().space(this.space).space(this.space))
          .isInstanceOf(IllegalStateException.class);
    }

    @Mock
    private ColumnZone zone;

    /// [Column.Builder#zone(ColumnZone)] のテスト.
    @Test
    void testZoneColumnZone() {
      assertThatThrownBy(() -> Column.builder().zone(null))
          .isInstanceOf(NullPointerException.class);
      assertThatThrownBy(() -> Column.builder().zone(this.zone))
          .isInstanceOf(IllegalStateException.class);
    }

    @Mock
    private ColumnShape shape;

    @Mock
    private Shell shell;

    /// [Column.Builder#build()] のテスト.
    @Test
    void testBuild() {
      assertThatThrownBy(
          () -> Column.builder()
              .shell(this.shell)
              .space(this.space)
              .zone(this.zone)
              .space(this.space)
              .build())
          .isInstanceOf(IllegalStateException.class);
      assertThatThrownBy(
          () -> Column.builder()
              .shape(this.shape)
              .space(this.space)
              .zone(this.zone)
              .space(this.space)
              .build())
          .isInstanceOf(IllegalStateException.class);
      assertThatThrownBy(
          () -> Column.builder()
              .shape(this.shape)
              .shell(this.shell)
              .space(this.space)
              .zone(this.zone)
              .build())
          .isInstanceOf(IllegalStateException.class);
    }

  } // BuilderTest

  @Mock
  private ColumnShape shape;

  @Mock
  private Shell shell;

  @Mock
  private ColumnSpace bottomSpace;

  @Mock
  private ColumnZone zone0;

  @Mock
  private ColumnZone zone1;

  @Mock
  private ColumnSpace topSpace;

  /// [Column#shape()] のテスト.
  @Test
  void testShape() {
    assertThat(
        Column.builder()
            .shape(this.shape)
            .origin(0.123)
            .shell(this.shell)
            .space(this.bottomSpace)
            .zone(this.zone0)
            .zone(this.zone1)
            .space(this.topSpace)
            .build()
            .shape())
        .isSameAs(this.shape);
  }

  /// [Column#origin()] のテスト.
  @Test
  void testOrigin() {
    assertThat(
        Column.builder()
            .shape(this.shape)
            .origin(0.123)
            .shell(this.shell)
            .space(this.bottomSpace)
            .zone(this.zone0)
            .zone(this.zone1)
            .space(this.topSpace)
            .build()
            .origin())
        .isEqualTo(0.123);
  }

  /// [Column#shell()] のテスト.
  @Test
  void testShell() {
    assertThat(
        Column.builder()
            .shape(this.shape)
            .origin(0.123)
            .shell(this.shell)
            .space(this.bottomSpace)
            .zone(this.zone0)
            .zone(this.zone1)
            .space(this.topSpace)
            .build()
            .shell())
        .isSameAs(this.shell);
  }

  /// [Column#spaceList()] のテスト.
  @Test
  void testSpaceList() {
    assertThat(
        Column.builder()
            .shape(this.shape)
            .origin(0.123)
            .shell(this.shell)
            .space(this.bottomSpace)
            .zone(this.zone0)
            .zone(this.zone1)
            .space(this.topSpace)
            .build()
            .spaceList())
        .isEqualTo(List.of(this.bottomSpace, this.topSpace));
  }

  /// [Column#zoneListList()] のテスト.
  @Test
  void testZoneListList() {
    assertThat(
        Column.builder()
            .shape(this.shape)
            .origin(0.123)
            .shell(this.shell)
            .space(this.bottomSpace)
            .zone(this.zone0)
            .zone(this.zone1)
            .space(this.topSpace)
            .build()
            .zoneListList())
        .isEqualTo(List.of(List.of(this.zone0, this.zone1)));
  }

  @Mock
  private ColumnShape shape2;

  @Mock
  private Shell shell2;

  /// [Column#equals(Object)] と [Column#hashCode()] のテスト.
  @Test
  void testEquals() {
    EqualsTester tester = new EqualsTester();

    Stream.<Supplier<Column>>of(
            () -> Column.builder()
                .shape(this.shape)
                .origin(0.123)
                .shell(this.shell)
                .space(this.bottomSpace)
                .zone(this.zone0)
                .zone(this.zone1)
                .space(this.topSpace)
                .build(),
            () -> Column.builder()
                .shape(this.shape2)
                .origin(0.123)
                .shell(this.shell)
                .space(this.bottomSpace)
                .zone(this.zone0)
                .zone(this.zone1)
                .space(this.topSpace)
                .build(),
            () -> Column.builder()
                .shape(this.shape)
                .origin(0.234)
                .shell(this.shell)
                .space(this.bottomSpace)
                .zone(this.zone0)
                .zone(this.zone1)
                .space(this.topSpace)
                .build(),
            () -> Column.builder()
                .shape(this.shape)
                .origin(0.123)
                .shell(this.shell2)
                .space(this.bottomSpace)
                .zone(this.zone0)
                .zone(this.zone1)
                .space(this.topSpace)
                .build(),
            () -> Column.builder()
                .shape(this.shape)
                .origin(0.123)
                .shell(this.shell)
                .space(this.topSpace)
                .zone(this.zone0)
                .zone(this.zone1)
                .space(this.topSpace)
                .build(),
            () -> Column.builder()
                .shape(this.shape)
                .origin(0.123)
                .shell(this.shell)
                .space(this.bottomSpace)
                .zone(this.zone0)
                .zone(this.zone0)
                .space(this.topSpace)
                .build(),
            () -> Column.builder()
                .shape(this.shape)
                .origin(0.123)
                .shell(this.shell)
                .space(this.bottomSpace)
                .zone(this.zone0)
                .zone(this.zone1)
                .space(this.bottomSpace)
                .build())
        .map(supplier -> Stream.generate(supplier).limit(3).toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

  /// [Column#toString()]
  @Test
  void testToString() {
    assertThat(
        Column.builder()
            .shape(this.shape)
            .origin(0.123)
            .shell(this.shell)
            .space(this.bottomSpace)
            .zone(this.zone0)
            .zone(this.zone1)
            .space(this.topSpace)
            .build())
        .hasToString(
            MoreObjects.toStringHelper(Column.class)
                .add("shape", this.shape)
                .add("origin", 0.123)
                .add("shell", this.shell)
                .add("spaceList", List.of(this.bottomSpace, this.topSpace))
                .add("zoneListList", List.of(List.of(this.zone0, this.zone1)))
                .toString());
  }

}
