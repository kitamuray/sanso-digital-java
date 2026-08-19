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

/// [EqualSection] のテスト.
///
/// [Section] のテストを兼ねる.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
class EqualSectionTest {

  /// [EqualSection.Builder] のテスト.
  ///
  /// [Section.Builder] のテストを兼ねる.
  @Nested
  class BuilderTest {

    /// [EqualSection.Builder#length(double)] のテスト.
    @Test
    void testLengthDouble() {
      assertThatThrownBy(() -> EqualSection.builder().length(0.))
          .isInstanceOf(IllegalArgumentException.class);
    }

    /// [EqualSection.Builder#number(int)] のテスト.
    @Test
    void testNumberInt() {
      assertThatThrownBy(() -> EqualSection.builder().number(0))
          .isInstanceOf(IllegalArgumentException.class);
    }

    /// [EqualSection.Builder#build()] のテスト.
    @Test
    void testBuild() {
      assertThatThrownBy(() -> EqualSection.builder().length(1.).build())
          .isInstanceOf(IllegalStateException.class);
      assertThatThrownBy(() -> EqualSection.builder().number(10).build())
          .isInstanceOf(IllegalStateException.class);
    }

  } // BuilderTest

  /// [EqualSection#length()] のテスト.
  @Test
  void testLength() {
    final double length = 10.;

    assertThat(
        EqualSection.builder()
            .length(length)
            .number(10)
            .build()
            .length())
        .isEqualTo(length);
  }

  /// [EqualSection#number()] のテスト.
  @Test
  void testNumber() {
    final int number = 10;

    assertThat(
        EqualSection.builder()
            .length(10.)
            .number(number)
            .build()
            .number())
        .isEqualTo(number);
  }

  /// [EqualSection#segments()] のテスト.
  @Test
  void testSegments() {
    assertThat(
        EqualSection.builder()
            .length(10.)
            .number(10)
            .build()
            .segments())
        .isEqualTo(new double[]{1., 1., 1., 1., 1., 1., 1., 1., 1., 1.});
  }

  /// [EqualSection#segmentList()] のテスト.
  @Test
  void testSegmentList() {
    assertThat(
        EqualSection.builder()
            .length(10.)
            .number(10)
            .build()
            .segmentList())
        .isEqualTo(List.of(1., 1., 1., 1., 1., 1., 1., 1., 1., 1.));
  }

  /// [EqualSection#equals(Object)] と [EqualSection#hashCode()] のテスト.
  @Test
  void testEquals() {
    EqualsTester tester = new EqualsTester();

    Stream.<Supplier<EqualSection>>of(
            () -> EqualSection.builder().length(10.).number(10).build(),
            () -> EqualSection.builder().length(20.).number(10).build(),
            () -> EqualSection.builder().length(10.).number(20).build())
        .map(supplier -> Stream.generate(supplier).limit(3).toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

  /// [EqualSection#toString()] のテスト.
  @Test
  void testToString() {
    assertThat(EqualSection.builder().length(10.).number(10).build())
        .hasToString(
            MoreObjects.toStringHelper(EqualSection.class)
                .add("length", 10.)
                .add("number", 10)
                .toString());
  }

}
