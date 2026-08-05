/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.testing.EqualsTester;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/// [FluidCellEntity] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
class FluidCellEntityTest {

  /// 具象クラスの内部実装.
  private static final class LocalCellEntity extends FluidCellEntity<
      LocalCellEntity.Previous,
      LocalCellEntity.ComponentValue,
      LocalCellEntity.ComponentValue.Previous> {

    private LocalCellEntity() {
      super(new Previous());
    }

    private static final class Previous extends FluidCellEntity.Previous {

      private Previous() {}

    } // Previous

    private static final class ComponentValue
        extends FluidCellEntity.ComponentValue<ComponentValue.Previous> {

      private ComponentValue() {
        super(new Previous());
      }

      private static final class Previous extends FluidCellEntity.ComponentValue.Previous {

        private Previous() {}

      } // LocalCellEntity.ComponentValue.Previous

    } // LocalCellEntity.ComponentValue

  } // LocalCellEntity

  /// Getter メソッドおよび Setter メソッドのテスト.
  ///
  ///   - [FluidCellEntity#getTemperature()],
  ///     [FluidCellEntity#setTemperature(double)]
  ///   - [FluidCellEntity#getPressure()],
  ///     [FluidCellEntity#setPressure(double)]
  ///   - [FluidCellEntity#getDensity()],
  ///     [FluidCellEntity#setDensity(double)]
  ///   - [FluidCellEntity#getSpecificEnthalpy()],
  ///     [FluidCellEntity#setSpecificEnthalpy(double)]
  ///   - [FluidCellEntity#getSpecificConstantPressureSpecificHeat()],
  ///     [FluidCellEntity#setSpecificConstantPressureSpecificHeat(double)]
  ///   - [FluidCellEntity#getViscosity()],
  ///     [FluidCellEntity#setViscosity(double)]
  ///   - [FluidCellEntity#getThermalConductivity()],
  ///     [FluidCellEntity#setThermalConductivity(double)]
  ///
  @Test
  void testGetDoubleAndSetDouble() {
    GetterSetterTester
        .supplier(LocalCellEntity::new)
        .getter(LocalCellEntity::getTemperature)
        .setter(LocalCellEntity::setTemperature)
        .valid(298.15)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester
        .supplier(LocalCellEntity::new)
        .getter(LocalCellEntity::getPressure)
        .setter(LocalCellEntity::setPressure)
        .valid(101325.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester
        .supplier(LocalCellEntity::new)
        .getter(LocalCellEntity::getDensity)
        .setter(LocalCellEntity::setDensity)
        .valid(1.2)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester
        .supplier(LocalCellEntity::new)
        .getter(LocalCellEntity::getSpecificEnthalpy)
        .setter(LocalCellEntity::setSpecificEnthalpy)
        .valid(1e5)
        .test();
    GetterSetterTester
        .supplier(LocalCellEntity::new)
        .getter(LocalCellEntity::getSpecificConstantPressureSpecificHeat)
        .setter(LocalCellEntity::setSpecificConstantPressureSpecificHeat)
        .valid(1000.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester
        .supplier(LocalCellEntity::new)
        .getter(LocalCellEntity::getViscosity)
        .setter(LocalCellEntity::setViscosity)
        .valid(18e-6)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester
        .supplier(LocalCellEntity::new)
        .getter(LocalCellEntity::getThermalConductivity)
        .setter(LocalCellEntity::setThermalConductivity)
        .valid(0.02)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
  }

  /// [FluidCellEntity#equals(Object)] と [FluidCellEntity#hashCode()] のテスト.
  @Test
  void testEquals() {
    EqualsTester tester = new EqualsTester();

    Stream.<Consumer<LocalCellEntity>>of(
            _ -> {},
            entity -> entity.setTemperature(298.15),
            entity -> entity.setPressure(101325.),
            entity -> entity.setDensity(1.2),
            entity -> entity.setSpecificEnthalpy(1e5),
            entity -> entity.setSpecificConstantPressureSpecificHeat(1000.),
            entity -> entity.setViscosity(18e-6),
            entity -> entity.setThermalConductivity(0.02),
            entity -> entity.getPreviousEntity().setTemperature(298.15),
            entity -> entity.setComponentEntityMap(
                Collections.singletonMap(
                    ComponentKey.NITROGEN,
                    new LocalCellEntity.ComponentValue())))
        .map(
            action -> Stream.generate(LocalCellEntity::new)
                .limit(3)
                .peek(action)
                .toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

  /// [FluidCellEntity.Previous] のテスト.
  @Nested
  class PreviousTest {

    /// プリミティブ double 型を対象とした getter および setter のテスト.
    ///
    /// テスト対象のメソッドを以下に示す.
    ///
    ///   - [FluidCellEntity#getTemperature()],
    ///     [FluidCellEntity#setTemperature(double)]
    ///   - [FluidCellEntity#getDensity()],
    ///     [FluidCellEntity#setDensity(double)]
    ///   - [FluidCellEntity#getSpecificEnthalpy()],
    ///     [FluidCellEntity#setSpecificEnthalpy(double)]
    ///   - [FluidCellEntity#getSpecificConstantPressureSpecificHeat()],
    ///     [FluidCellEntity#setSpecificConstantPressureSpecificHeat(double)]
    ///
    @Test
    void testGetDoubleAndSetDouble() {
      GetterSetterTester
          .supplier(LocalCellEntity.Previous::new)
          .getter(LocalCellEntity.Previous::getTemperature)
          .setter(LocalCellEntity.Previous::setTemperature)
          .valid(298.15)
          .thrown(IllegalArgumentException.class).value(0.)
          .test();
      GetterSetterTester
          .supplier(LocalCellEntity.Previous::new)
          .getter(LocalCellEntity.Previous::getDensity)
          .setter(LocalCellEntity.Previous::setDensity)
          .valid(1.2)
          .thrown(IllegalArgumentException.class).value(0.)
          .test();
      GetterSetterTester
          .supplier(LocalCellEntity.Previous::new)
          .getter(LocalCellEntity.Previous::getSpecificEnthalpy)
          .setter(LocalCellEntity.Previous::setSpecificEnthalpy)
          .valid(1e5)
          .test();
      GetterSetterTester
          .supplier(LocalCellEntity.Previous::new)
          .getter(LocalCellEntity.Previous::getSpecificConstantPressureSpecificHeat)
          .setter(LocalCellEntity.Previous::setSpecificConstantPressureSpecificHeat)
          .valid(1000.)
          .thrown(IllegalArgumentException.class).value(0.)
          .test();
    }

    /// [FluidCellEntity.Previous#equals(Object)] と
    /// [FluidCellEntity.Previous#hashCode()] のテスト.
    @Test
    void testEquals() {
      EqualsTester tester = new EqualsTester();

      Stream.<Consumer<FluidCellEntity.Previous>>of(
              _ -> {},
              entity -> entity.setTemperature(298.15),
              entity -> entity.setDensity(1.2),
              entity -> entity.setSpecificEnthalpy(1e5),
              entity -> entity.setSpecificConstantPressureSpecificHeat(1000.))
          .map(
              action -> Stream.generate(LocalCellEntity.Previous::new)
                  .limit(3)
                  .peek(action)
                  .toArray())
          .forEach(tester::addEqualityGroup);

      tester.testEquals();
    }

  } // PreviousTest

  /// [FluidCellEntity.ComponentValue] のテスト.
  @Nested
  class ComponentValueTest {

    /// プリミティブ double 型を対象とした getter および setter のテスト.
    ///
    /// テスト対象のメソッドを以下に示す.
    ///
    ///   - [FluidCellEntity.ComponentValue#getFraction()],
    ///     [FluidCellEntity.ComponentValue#setFraction(double)]
    ///   - [FluidCellEntity.ComponentValue#getDiffusivity()],
    ///     [FluidCellEntity.ComponentValue#setDiffusivity(double)]
    ///
    @Test
    void testGetDoubleAndSetDouble() {
      GetterSetterTester
          .supplier(LocalCellEntity.ComponentValue::new)
          .getter(LocalCellEntity.ComponentValue::getFraction)
          .setter(LocalCellEntity.ComponentValue::setFraction)
          .valid(0.1)
          .thrown(IllegalArgumentException.class).value(-1.)
          .thrown(IllegalArgumentException.class).value(2.)
          .test();
      GetterSetterTester
          .supplier(LocalCellEntity.ComponentValue::new)
          .getter(LocalCellEntity.ComponentValue::getDiffusivity)
          .setter(LocalCellEntity.ComponentValue::setDiffusivity)
          .valid(5e-4)
          .thrown(IllegalArgumentException.class).value(0.)
          .test();
    }

    /// [FluidCellEntity.ComponentValue#equals(Object)] と
    /// [FluidCellEntity.ComponentValue#hashCode()] のテスト.
    @Test
    void testEquals() {
      EqualsTester tester = new EqualsTester();

      Stream.<Consumer<LocalCellEntity.ComponentValue>>of(
              _ -> {},
              entity -> entity.setFraction(0.2),
              entity -> entity.setDiffusivity(5e-4),
              entity -> entity.getPreviousEntity().setFraction(0.2))
          .map(
              action -> Stream.generate(LocalCellEntity.ComponentValue::new)
                  .limit(3)
                  .peek(action)
                  .toArray())
          .forEach(tester::addEqualityGroup);

      tester.testEquals();
    }

    /// [FluidCellEntity.ComponentValue.Previous] のテスト.
    @Nested
    class PreviousTest {

      /// プリミティブ double 型を対象とした getter および setter のテスト.
      ///
      /// テスト対象のメソッドを以下に示す.
      ///
      ///   - [FluidCellEntity.ComponentValue.Previous#getFraction()],
      ///     [FluidCellEntity.ComponentValue.Previous#setFraction(double)]
      ///
      @Test
      void testGetDoubleAndSetDouble() {
        GetterSetterTester
            .supplier(LocalCellEntity.ComponentValue.Previous::new)
            .getter(LocalCellEntity.ComponentValue.Previous::getFraction)
            .setter(LocalCellEntity.ComponentValue.Previous::setFraction)
            .valid(0.1)
            .thrown(IllegalArgumentException.class).value(-1.)
            .thrown(IllegalArgumentException.class).value(2.);
      }

      /// [FluidCellEntity.ComponentValue.Previous#equals(Object)] と
      /// [FluidCellEntity.ComponentValue.Previous#hashCode()] のテスト.
      @Test
      void testEquals() {
        EqualsTester tester = new EqualsTester();

        Stream.<Consumer<FluidCellEntity.ComponentValue.Previous>>of(
                _ -> {},
                entity -> entity.setFraction(0.2))
            .map(
                action -> Stream.generate(LocalCellEntity.ComponentValue.Previous::new)
                    .limit(3)
                    .peek(action)
                    .toArray())
            .forEach(tester::addEqualityGroup);

        tester.testEquals();
      }

    } // ComponentValueTest.PreviousTest

  } // ComponentValueTest

}
