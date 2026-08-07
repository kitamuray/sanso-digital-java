/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import com.nipponsanso.jp.digital.adsorption.ComponentKey;
import com.nipponsanso.jp.digital.adsorption.GetterSetterTester;
import com.nipponsanso.jp.digital.adsorption.Shell;
import java.util.Map;
import java.util.function.Supplier;
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
  public abstract static class LocalCell extends ShellCell {

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

    public abstract static class Previous extends ShellCell.Previous {

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
    Supplier<ShellCell> supplier = () -> new LocalCell(this.shell) {

      @Override
      public FluidCell.Previous previous() {
        return null;
      }

      @Override
      public Map<ComponentKey, ? extends FluidCell.ComponentValue> componentMap() {
        return Map.of();
      }

    };

    final double shellDensity = 640.;
    doReturn(shellDensity).when(this.shell).density();
    assertThat(supplier.get().shellDensity()).isEqualTo(shellDensity);

    GetterSetterTester.supplier(supplier)
        .getter(ShellCell::getShellTemperature)
        .setter(ShellCell::setShellTemperature)
        .valid(303.15)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
        .getter(ShellCell::getShellSpecificHeat)
        .setter(ShellCell::setShellSpecificHeat)
        .valid(640.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
    GetterSetterTester.supplier(supplier)
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
      Supplier<ShellCell.Previous> supplier = () -> new LocalCell.Previous() {};

      GetterSetterTester.supplier(supplier)
          .getter(ShellCell.Previous::getShellTemperature)
          .setter(ShellCell.Previous::setShellTemperature)
          .valid(303.15)
          .thrown(IllegalArgumentException.class).value(0.)
          .test();
      GetterSetterTester.supplier(supplier)
          .getter(ShellCell.Previous::getShellSpecificHeat)
          .setter(ShellCell.Previous::setShellSpecificHeat)
          .valid(640.)
          .thrown(IllegalArgumentException.class).value(0.)
          .test();
    }

  } // PreviousTest

}
