/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doAnswer;
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

    private static final class ComponentValue extends ShellCell.ComponentValue {

      private final double[] doubleArray = new double[ShellCell.ComponentValue.SIZE];

      @Override
      int offset() {
        return 0;
      }

      @Override
      double[] doubleArray() {
        return this.doubleArray;
      }

      @Override
      Previous previous() {
        return null;
      }

      private static final class Previous extends ShellCell.ComponentValue.Previous {

        private final double[] doubleArray = new double[ShellCell.ComponentValue.Previous.SIZE];

        @Override
        int offset() {
          return 0;
        }

        @Override
        double[] doubleArray() {
          return this.doubleArray;
        }

      } // Previous

    } // ComponentValue

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

  static void initMock(ShellCellEntity<?, ?, ?> entity) {
    FluidCellTest.initMock(entity);

    final double shellTemperature = 303.15;
    doReturn(shellTemperature).when(entity).getShellTemperature();
    doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(shellTemperature))
        .when(entity)
        .setShellTemperature(anyDouble());

    final double shellSpecificHeat = 640.;
    doReturn(shellSpecificHeat).when(entity).getShellSpecificHeat();
    doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(shellSpecificHeat))
        .when(entity)
        .setShellSpecificHeat(anyDouble());

    final double shellThermalConductivity = 0.2;
    doReturn(shellThermalConductivity).when(entity).getShellThermalConductivity();
    doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(shellThermalConductivity))
        .when(entity)
        .setShellThermalConductivity(anyDouble());
  }

  @Mock
  private ShellCellEntity<?, ?, ?> entity;

  /// [ShellCell#read(ShellCellEntity)] と [ShellCell#write(ShellCellEntity)] のテスト.
  @Test
  void testEntity() {
    initMock(this.entity);

    LocalCell cell = new LocalCell(this.shell);
    cell.read(this.entity);
    cell.write(this.entity);
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

    static void initMock(ShellCellEntity.Previous entity) {
      FluidCellTest.PreviousTest.initMock(entity);

      final double shellTemperature = 303.15;
      doReturn(shellTemperature).when(entity).getShellTemperature();
      doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(shellTemperature))
          .when(entity)
          .setShellTemperature(anyDouble());

      final double shellSpecificHeat = 640.;
      doReturn(shellSpecificHeat).when(entity).getShellSpecificHeat();
      doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(shellSpecificHeat))
          .when(entity)
          .setShellSpecificHeat(anyDouble());
    }

    @Mock
    private ShellCellEntity.Previous entity;

    /// [ShellCell.Previous#read(ShellCellEntity.Previous)] と
    /// [ShellCell.Previous#write(ShellCellEntity.Previous)] のテスト.
    @Test
    void testEntity() {
      initMock(this.entity);

      LocalCell.Previous previous = new LocalCell.Previous();
      previous.read(this.entity);
      previous.write(this.entity);
    }

  } // PreviousTest

  /// [ShellCell.ComponentValue] のテスト.
  @Nested
  class ComponentValueTest {

    @Mock
    private ShellCellEntity.ComponentValue<?> entity;

    /// [ShellCell.ComponentValue#read(ShellCellEntity.ComponentValue)] と
    /// [ShellCell.ComponentValue#write(ShellCellEntity.ComponentValue)] のテスト.
    @Test
    void testEntity() {
      FluidCellTest.ComponentValueTest.initMock(this.entity);

      LocalCell.ComponentValue componentValue = new LocalCell.ComponentValue();
      componentValue.read(this.entity);
      componentValue.write(this.entity);
    }

    /// [ShellCell.ComponentValue.Previous] のテスト.
    @Nested
    class PreviousTest {

      @Mock
      private ShellCellEntity.ComponentValue.Previous entity;

      /// [ShellCell.ComponentValue.Previous#read(ShellCellEntity.ComponentValue.Previous)] と
      /// [ShellCell.ComponentValue.Previous#write(ShellCellEntity.ComponentValue.Previous)] のテスト.
      @Test
      void testEntity() {
        FluidCellTest.ComponentValueTest.PreviousTest.initMock(this.entity);

        LocalCell.ComponentValue.Previous previous = new LocalCell.ComponentValue.Previous();
        previous.read(this.entity);
        previous.write(this.entity);
      }

    } // PreviousTest

  } // ComponentValueTest

}
