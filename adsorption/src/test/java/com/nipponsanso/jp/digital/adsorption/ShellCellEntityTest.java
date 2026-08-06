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

/// [ShellCellEntity] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
class ShellCellEntityTest {

  /// 具象クラスの内部実装.
  private static final class LocalCellEntity extends ShellCellEntity<
      LocalCellEntity.Previous,
      LocalCellEntity.ComponentValue,
      LocalCellEntity.ComponentValue.Previous> {

    private LocalCellEntity() {
      super(new Previous());
    }

    private static final class Previous extends ShellCellEntity.Previous {

      private Previous() {}

    } // Previous

    private static final class ComponentValue
        extends ShellCellEntity.ComponentValue<ComponentValue.Previous> {

      private ComponentValue() {
        super(new Previous());
      }

      private static final class Previous extends ShellCellEntity.ComponentValue.Previous {

        private Previous() {}

      } // Previous

    } // ComponentValue

  } // LocalCellEntity

  /// Getter メソッドと Setter メソッドのテスト.
  ///
  ///   - [ShellCellEntity#getShellTemperature()],
  ///     [ShellCellEntity#setShellTemperature(double)]
  ///   - [ShellCellEntity#getShellSpecificHeat()],
  ///     [ShellCellEntity#setShellSpecificHeat(double)]
  ///   - [ShellCellEntity#getShellThermalConductivity()],
  ///     [ShellCellEntity#setShellThermalConductivity(double)]
  ///
  @Test
  void testGetterAndSetter() {
    GetterSetterTester
        .supplier(LocalCellEntity::new)
        .getter(LocalCellEntity::getShellTemperature)
        .setter(LocalCellEntity::setShellTemperature)
        .valid(303.15)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester
        .supplier(LocalCellEntity::new)
        .getter(LocalCellEntity::getShellSpecificHeat)
        .setter(LocalCellEntity::setShellSpecificHeat)
        .valid(590.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester
        .supplier(LocalCellEntity::new)
        .getter(LocalCellEntity::getShellThermalConductivity)
        .setter(LocalCellEntity::setShellThermalConductivity)
        .valid(16.7)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
  }

  /// [ShellCellEntity#equals(Object)] と [ShellCellEntity#hashCode()] のテスト.
  @Test
  void testEquals() {
    EqualsTester tester = new EqualsTester();

    Stream.<Consumer<LocalCellEntity>>of(
            _ -> {},
            entity -> entity.setShellTemperature(303.15),
            entity -> entity.setShellSpecificHeat(590.),
            entity -> entity.setShellThermalConductivity(16.7),
            entity -> entity.setTemperature(298.15), // 親クラス
            entity -> entity.getPreviousEntity().setShellTemperature(303.15),
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

  /// [ShellCellEntity.Previous] のテスト.
  @Nested
  class PreviousTest {

    /// Getter メソッドと Setter メソッドのテスト.
    ///
    ///   - [ShellCellEntity.Previous#getShellTemperature()],
    ///     [ShellCellEntity.Previous#setShellTemperature(double)]
    ///   - [ShellCellEntity.Previous#getShellSpecificHeat()],
    ///     [ShellCellEntity.Previous#setShellSpecificHeat(double)]
    ///   - [ShellCellEntity.Previous#getShellThermalConductivity()],
    ///     [ShellCellEntity.Previous#setShellThermalConductivity(double)]
    ///
    @Test
    void testGetterAndSetter() {
      GetterSetterTester
          .supplier(LocalCellEntity.Previous::new)
          .getter(LocalCellEntity.Previous::getShellTemperature)
          .setter(LocalCellEntity.Previous::setShellTemperature)
          .valid(303.15)
          .thrown(IllegalArgumentException.class).value(0.)
          .test();
      GetterSetterTester
          .supplier(LocalCellEntity.Previous::new)
          .getter(LocalCellEntity.Previous::getShellSpecificHeat)
          .setter(LocalCellEntity.Previous::setShellSpecificHeat)
          .valid(590.)
          .thrown(IllegalArgumentException.class).value(0.)
          .test();
    }

    /// [ShellCellEntity.Previous#equals(Object)] と
    /// [ShellCellEntity.Previous#hashCode()] のテスト.
    @Test
    void testEquals() {
      EqualsTester tester = new EqualsTester();

      Stream.<Consumer<ShellCellEntity.Previous>>of(
              _ -> {},
              entity -> entity.setShellTemperature(303.15),
              entity -> entity.setShellSpecificHeat(590.),
              entity -> entity.setTemperature(298.15)) // 親クラス
          .map(
              action -> Stream.generate(LocalCellEntity.Previous::new)
                  .limit(3)
                  .peek(action)
                  .toArray())
          .forEach(tester::addEqualityGroup);

      tester.testEquals();
    }

  }

}
