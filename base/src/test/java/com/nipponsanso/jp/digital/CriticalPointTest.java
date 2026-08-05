/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

///
/// [CriticalPoint] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
///
class CriticalPointTest {

  ///
  /// [CriticalPoint#CriticalPoint(double, double, double)] のテスト.
  ///
  @Test
  void testCriticalPointDoubleDoubleDouble() {
    assertThatThrownBy(() -> new CriticalPoint(0., 3.3958e6, 1. /  11184.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> new CriticalPoint(126.19, 0., 1. /  11184.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> new CriticalPoint(126.19, 3.3958e6, 0.))
        .isInstanceOf(IllegalArgumentException.class);
  }

}
