/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

/// [LennardJonesPotential] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
class LennardJonesPotentialTest {

  /// [LennardJonesPotential#LennardJonesPotential(double, double)] のテスト.
  @Test
  void testLennardJonesPotentialDoubleDouble() {
    assertThatThrownBy(() -> new LennardJonesPotential(0., 71.4))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> new LennardJonesPotential(3.798, 0.))
        .isInstanceOf(IllegalArgumentException.class);
  }

}
