/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [ShellCell] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class ShellCellTest {

  /// 容器を含むセルの内部実装.
  private static final class LocalCell extends ShellCell {

    private final double[] doubleArray = new double[ShellCell.SIZE];

    public LocalCell(Shell shell) {
      super(shell);
    }

    @Override
    public int offset() {
      return 0;
    }

    @Override
    public double[] doubleArray() {
      return this.doubleArray;
    }

    @Override
    public ShellCell.Previous previous() {
      return null;
    }

    @Override
    public Map<ComponentKey, ? extends ShellCell.ComponentValue> componentMap() {
      return Map.of();
    }

    private static final class Previous extends ShellCell.Previous {

      private final double[] doubleArray = new double[ShellCell.Previous.SIZE];

      @Override
      public int offset() {
        return 0;
      }

      @Override
      public double[] doubleArray() {
        return this.doubleArray;
      }

    } // Previous

  } // ShellCell

  @Mock
  private Shell shell;

  /// [ShellCell] の Getter と Setter をテスト.
  @Test
  void testGetterAndSetter() {
    final double density = 7900.;
    doReturn(density).when(this.shell).density();

    assertThat(new LocalCell(this.shell).shellDensity()).isEqualTo(density);

    GetterSetterTester.supplier(() -> new LocalCell(this.shell))
        .getter(ShellCell::getShellTemperature)
        .setter(ShellCell::setShellTemperature)
        .valid(303.15)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(() -> new LocalCell(this.shell))
        .getter(ShellCell::getShellSpecificHeat)
        .setter(ShellCell::setShellSpecificHeat)
        .valid(640.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(() -> new LocalCell(this.shell))
        .getter(ShellCell::getShellThermalConductivity)
        .setter(ShellCell::setShellThermalConductivity)
        .valid(0.2)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
  }

  /// [ShellCell.Previous] のテスト.
  @Nested
  class PreviousTest {

    /// [ShellCell.Previous] の Getter と Setter をテスト.
    @Test
    void testGetterAndSetter() {
      GetterSetterTester.supplier(LocalCell.Previous::new)
          .getter(ShellCell.Previous::getShellTemperature)
          .setter(ShellCell.Previous::setShellTemperature)
          .valid(303.15)
          .thrown(IllegalArgumentException.class).value(0.)
          .test();
      GetterSetterTester.supplier(LocalCell.Previous::new)
          .getter(ShellCell.Previous::getShellSpecificHeat)
          .setter(ShellCell.Previous::setShellSpecificHeat)
          .valid(640.)
          .thrown(IllegalArgumentException.class).value(0.)
          .test();
    }

  } // PreviousTest

}
