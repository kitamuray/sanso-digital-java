/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/// [FluidFace] のテスト.
///
/// @since 1.1
/// @author KITAMURA Yuichiro
class FluidFaceTest {

  private static final class LocalFace extends FluidFace {

    private final double[] doubleArray = new double[FluidFace.SIZE];

    @Override
    int offset() {
      return 0;
    }

    @Override
    double[] doubleArray() {
      return this.doubleArray;
    }

    @Override
    Map<ComponentKey, ? extends ComponentValue> componentMap() {
      return Collections.emptyMap();
    }

    private static final class ComponentValue extends FluidFace.ComponentValue {

      private final double[] doubleArray = new double[FluidFace.ComponentValue.SIZE];

      @Override
      int offset() {
        return 0;
      }

      @Override
      double[] doubleArray() {
        return this.doubleArray;
      }

    } // ComponentValue

  } // LocalFace

  /// Getter と Setter のテスト.
  @Test
  void testGetterAndSetter() {
    GetterSetterTester.supplier(LocalFace::new)
        .getter(FluidFace::getMassFlow)
        .setter(FluidFace::setMassFlow)
        .valid(1.2)
        .test();
    GetterSetterTester.supplier(LocalFace::new)
        .getter(FluidFace::getEnergyFlow)
        .setter(FluidFace::setEnergyFlow)
        .valid(2.3)
        .test();
  }

  /// [FluidFace.ComponentValue] のテスト.
  @Nested
  class ComponentValueTest {

    /// Getter と Setter のテスト.
    @Test
    void testGetterAndSetter() {
      GetterSetterTester.supplier(LocalFace.ComponentValue::new)
          .getter(FluidFace.ComponentValue::getSpeciesFlow)
          .setter(FluidFace.ComponentValue::setSpeciesFlow)
          .valid(3.4)
          .test();
    }

  }

}
