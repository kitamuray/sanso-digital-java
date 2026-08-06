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

/// [AdsorbentCellEntity] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
class AdsorbentCellEntityTest {

  /// 具象クラスの内部実装.
  private static final class LocalCellEntity extends AdsorbentCellEntity<
      LocalCellEntity.Previous,
      LocalCellEntity.ComponentValue,
      LocalCellEntity.ComponentValue.Previous> {

    private LocalCellEntity() {
      super(new Previous());
    }

    private static final class Previous extends AdsorbentCellEntity.Previous {

      private Previous() {}

    } // Previous

    private static final class ComponentValue
        extends AdsorbentCellEntity.ComponentValue<ComponentValue.Previous> {

      private ComponentValue() {
        super(new Previous());
      }

      private static final class Previous extends AdsorbentCellEntity.ComponentValue.Previous {

        private Previous() {}

      } // Previous

    } // ComponentValue

  } // LocalCellEntity

  /// Getter メソッドと Setter メソッドのテスト.
  ///
  ///   - [AdsorbentCellEntity#getAdsorbentSpecificHeat()],
  ///     [AdsorbentCellEntity#setAdsorbentSpecificHeat(double)]
  ///
  @Test
  void testGetterAndSetter() {
    GetterSetterTester
        .supplier(LocalCellEntity::new)
        .getter(LocalCellEntity::getAdsorbentSpecificHeat)
        .setter(LocalCellEntity::setAdsorbentSpecificHeat)
        .valid(800.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
  }

  /// [AdsorbentCellEntity#equals(Object)] と
  /// [AdsorbentCellEntity#hashCode()] のテスト.
  @Test
  void testEquals() {
    EqualsTester tester = new EqualsTester();

    Stream.<Consumer<LocalCellEntity>>of(
            _ -> {},
            entity -> entity.setAdsorbentSpecificHeat(800.),
            entity -> entity.getPreviousEntity().setAdsorbentSpecificHeat(800.),
            entity -> entity.setComponentEntityMap(
                Collections.singletonMap(
                    ComponentKey.NITROGEN,
                    new LocalCellEntity.ComponentValue())),
            entity -> entity.setTemperature(298.15)) // 親クラス
        .map(
            action -> Stream.generate(LocalCellEntity::new)
                .limit(3)
                .peek(action)
                .toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

  /// [AdsorbentCellEntity.Previous] のテスト.
  @Nested
  class PreviousTest {

    /// Getter メソッドと Setter メソッドのテスト.
    ///
    ///   - [AdsorbentCellEntity.Previous#getAdsorbentSpecificHeat()],
    ///     [AdsorbentCellEntity.Previous#setAdsorbentSpecificHeat(double)]
    ///
    @Test
    void testGetterAndSetter() {
      GetterSetterTester
          .supplier(LocalCellEntity.Previous::new)
          .getter(LocalCellEntity.Previous::getAdsorbentSpecificHeat)
          .setter(LocalCellEntity.Previous::setAdsorbentSpecificHeat)
          .valid(800.)
          .thrown(IllegalArgumentException.class).value(0.)
          .test();
    }

    /// [AdsorbentCellEntity.Previous#equals(Object)] と
    /// [AdsorbentCellEntity.Previous#hashCode()] のテスト.
    @Test
    void testEquals() {
      EqualsTester tester = new EqualsTester();

      Stream.<Consumer<LocalCellEntity.Previous>>of(
              _ -> {},
              entity -> entity.setAdsorbentSpecificHeat(800.),
              entity -> entity.setTemperature(298.15)) // 親クラス
          .map(
              action -> Stream.generate(LocalCellEntity.Previous::new)
                  .limit(3)
                  .peek(action)
                  .toArray())
          .forEach(tester::addEqualityGroup);

      tester.testEquals();
    }

  } // PreviousTest

  /// [AdsorbentCellEntity.ComponentValue] のテスト.
  @Nested
  class ComponentValueTest {

    /// Getter メソッドと Setter メソッドのテスト.
    ///
    ///   - [AdsorbentCellEntity.ComponentValue#getMolarQuantity()],
    ///     [AdsorbentCellEntity.ComponentValue#setMolarQuantity(double)]
    ///   - [AdsorbentCellEntity.ComponentValue#getMolarSpecificConstantVolumeSpecificHeat()],
    ///     [`#setMolarSpecificConstantVolumeSpecificHeat(double)`][AdsorbentCellEntity.ComponentValue]
    ///
    @Test
    void testGetterAndSetter() {
      GetterSetterTester
          .supplier(LocalCellEntity.ComponentValue::new)
          .getter(LocalCellEntity.ComponentValue::getMolarQuantity)
          .setter(LocalCellEntity.ComponentValue::setMolarQuantity)
          .valid(100.)
          .thrown(IllegalArgumentException.class).value(0.)
          .test();
      GetterSetterTester
          .supplier(LocalCellEntity.ComponentValue::new)
          .getter(LocalCellEntity.ComponentValue::getMolarSpecificConstantVolumeSpecificHeat)
          .setter(LocalCellEntity.ComponentValue::setMolarSpecificConstantVolumeSpecificHeat)
          .valid(990.)
          .thrown(IllegalArgumentException.class).value(0.)
          .test();
    }

    @Test
    void testEquals() {
      EqualsTester tester = new EqualsTester();

      Stream.<Consumer<LocalCellEntity.ComponentValue>>of(
              _ -> {},
              entity -> entity.setMolarQuantity(100.),
              entity -> entity.setMolarSpecificConstantVolumeSpecificHeat(990.),
              entity -> entity.getPreviousEntity().setMolarQuantity(100.),
              entity -> entity.setFraction(0.2)) // 親クラス
          .map(
              action -> Stream.generate(LocalCellEntity.ComponentValue::new)
                  .limit(3)
                  .peek(action)
                  .toArray())
          .forEach(tester::addEqualityGroup);

      tester.testEquals();
    }

    /// [AdsorbentCellEntity.ComponentValue.Previous] のテスト.
    @Nested
    class PreviousTest {

      /// Getter メソッドと Setter メソッドのテスト.
      ///
      ///   - [AdsorbentCellEntity.ComponentValue.Previous#getMolarQuantity()],
      ///     [AdsorbentCellEntity.ComponentValue.Previous#setMolarQuantity(double)]
      ///   - [`#getMolarSpecificConstantVolumeSpecificHeat()`][AdsorbentCellEntity.ComponentValue.Previous],
      ///     [`#setMolarSpecificConstantVolumeSpecificHeat(double)`][AdsorbentCellEntity.ComponentValue.Previous]
      ///
      @Test
      void testGetterAndSetter() {
        GetterSetterTester
            .supplier(LocalCellEntity.ComponentValue.Previous::new)
            .getter(LocalCellEntity.ComponentValue.Previous::getMolarQuantity)
            .setter(LocalCellEntity.ComponentValue.Previous::setMolarQuantity)
            .valid(100.)
            .thrown(IllegalArgumentException.class).value(0.)
            .test();
        GetterSetterTester
            .supplier(LocalCellEntity.ComponentValue.Previous::new)
            .getter(
                AdsorbentCellEntity.ComponentValue.Previous
                    ::getMolarSpecificConstantVolumeSpecificHeat)
            .setter(
                AdsorbentCellEntity.ComponentValue.Previous
                    ::setMolarSpecificConstantVolumeSpecificHeat)
            .valid(990.)
            .thrown(IllegalArgumentException.class).value(0.)
            .test();
      }

      /// [AdsorbentCellEntity.ComponentValue.Previous#equals(Object)] と
      /// [AdsorbentCellEntity.ComponentValue.Previous#hashCode()] のテスト.
      @Test
      void testEquals() {
        EqualsTester tester = new EqualsTester();

        Stream.<Consumer<LocalCellEntity.ComponentValue.Previous>>of(
                _ -> {},
                entity -> entity.setMolarQuantity(100.),
                entity -> entity.setMolarSpecificConstantVolumeSpecificHeat(990.),
                entity -> entity.setFraction(0.2)) // 親クラス
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
