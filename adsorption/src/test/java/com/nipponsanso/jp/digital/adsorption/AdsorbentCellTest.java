/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;

import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [AdsorbentCell] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class AdsorbentCellTest {

  /// 吸着剤を含むセルの内部実装.
  private static final class LocalCell extends AdsorbentCell {

    private final double[] doubleArray = new double[AdsorbentCell.SIZE];

    public LocalCell(Adsorbent adsorbent, Shell shell) {
      super(adsorbent, shell);
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
    public AdsorbentCell.Previous previous() {
      return null;
    }

    @Override
    public Map<ComponentKey, ? extends AdsorbentCell.ComponentValue> componentMap() {
      return Map.of();
    }

    private static final class Previous extends AdsorbentCell.Previous {

      private final double[] doubleArray = new double[AdsorbentCell.Previous.SIZE];

      @Override
      public int offset() {
        return 0;
      }

      @Override
      public double[] doubleArray() {
        return this.doubleArray;
      }

    } // Previous

    private static final class ComponentValue extends AdsorbentCell.ComponentValue {

      private final double[] doubleArray = new double[AdsorbentCell.ComponentValue.SIZE];

      @Override
      public int offset() {
        return 0;
      }

      @Override
      public double[] doubleArray() {
        return this.doubleArray;
      }

      @Override
      public Previous previous() {
        return null;
      }

      private static final class Previous extends AdsorbentCell.ComponentValue.Previous {

        private final double[] doubleArray = new double[AdsorbentCell.ComponentValue.Previous.SIZE];

        @Override
        public int offset() {
          return 0;
        }

        @Override
        public double[] doubleArray() {
          return this.doubleArray;
        }

      } // Previous

    } // ComponentValue

  } // LocalCell

  @Mock
  private Adsorbent adsorbent;

  @Mock
  private Shell shell;

  /// [AdsorbentCell] の Getter と Setter をテスト.
  @Test
  void testGetterAndSetter() {
    final double diameter = 2e-3;
    final double voidFraction = 0.4;
    final double bulkDensity = 810.;

    doReturn(diameter).when(this.adsorbent).diameter();
    doReturn(voidFraction).when(this.adsorbent).voidFraction();
    doReturn(bulkDensity).when(this.adsorbent).bulkDensity();

    assertThat(new LocalCell(this.adsorbent, this.shell).particleDiameter())
        .isEqualTo(diameter);
    assertThat(new LocalCell(this.adsorbent, this.shell).voidFraction())
        .isEqualTo(voidFraction);
    assertThat(new LocalCell(this.adsorbent, this.shell).bulkDensity())
        .isEqualTo(bulkDensity);

    GetterSetterTester.supplier(() -> new LocalCell(this.adsorbent, this.shell))
        .getter(AdsorbentCell::getAdsorbentSpecificHeat)
        .setter(AdsorbentCell::setAdsorbentSpecificHeat)
        .valid(820.)
        .thrown(IllegalArgumentException.class).value(0.)
        .test();
  }

  static void initMock(AdsorbentCellEntity<?, ?, ?> entity) {
    ShellCellTest.initMock(entity);

    final double adsorbentSpecificHeat = 820.;
    doReturn(adsorbentSpecificHeat).when(entity).getAdsorbentSpecificHeat();
    doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(adsorbentSpecificHeat))
        .when(entity)
        .setAdsorbentSpecificHeat(anyDouble());
  }

  @Mock
  private AdsorbentCellEntity<?, ?, ?> entity;

  /// [AdsorbentCell#read(AdsorbentCellEntity)] と
  /// [AdsorbentCell#write(AdsorbentCellEntity)] のテスト.
  @Test
  void testEntity() {
    initMock(this.entity);

    LocalCell cell = new LocalCell(this.adsorbent, this.shell);
    cell.read(this.entity);
    cell.write(this.entity);
  }

  /// [AdsorbentCell.Previous] のテスト.
  @Nested
  class PreviousTest {

    /// [AdsorbentCell.Previous] の Getter と Setter をテスト.
    @Test
    void testGetterAndSetter() {
      GetterSetterTester.supplier(LocalCell.Previous::new)
          .getter(AdsorbentCell.Previous::getAdsorbentSpecificHeat)
          .setter(AdsorbentCell.Previous::setAdsorbentSpecificHeat)
          .valid(820.)
          .thrown(IllegalArgumentException.class).value(0.)
          .test();
    }

    static void initMock(AdsorbentCellEntity.Previous entity) {
      ShellCellTest.PreviousTest.initMock(entity);

      final double adsorbentSpecificHeat = 820.;
      doReturn(adsorbentSpecificHeat).when(entity).getAdsorbentSpecificHeat();
      doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(adsorbentSpecificHeat))
          .when(entity)
          .setAdsorbentSpecificHeat(anyDouble());
    }

    @Mock
    private AdsorbentCellEntity.Previous entity;

    /// [AdsorbentCell.Previous#read(AdsorbentCellEntity.Previous)] と
    /// [AdsorbentCell.Previous#write(AdsorbentCellEntity.Previous)] のテスト.
    @Test
    void testEntity() {
      initMock(this.entity);

      LocalCell.Previous cell = new LocalCell.Previous();
      cell.read(this.entity);
      cell.write(this.entity);
    }

  } // PreviousTest

  /// [AdsorbentCell.ComponentValue] のテスト.
  @Nested
  class ComponentValueTest {

    /// [AdsorbentCell.ComponentValue] の Getter と Setter をテスト.
    @Test
    void testGetterAndSetter() {
      GetterSetterTester.supplier(LocalCell.ComponentValue::new)
          .getter(AdsorbentCell.ComponentValue::getMolarQuantity)
          .setter(AdsorbentCell.ComponentValue::setMolarQuantity)
          .valid(10.)
          .thrown(IllegalArgumentException.class).value(0.)
          .test();
      GetterSetterTester.supplier(LocalCell.ComponentValue::new)
          .getter(AdsorbentCell.ComponentValue::getMolarSpecificConstantVolumeSpecificHeat)
          .setter(AdsorbentCell.ComponentValue::setMolarSpecificConstantVolumeSpecificHeat)
          .valid(12.)
          .thrown(IllegalArgumentException.class).value(0.)
          .test();
    }

    static void initMock(AdsorbentCellEntity.ComponentValue<?> entity) {
      FluidCellTest.ComponentValueTest.initMock(entity);

      final double molarQuantity = 10.;
      doReturn(molarQuantity).when(entity).getMolarQuantity();
      doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(molarQuantity))
          .when(entity)
          .setMolarQuantity(anyDouble());

      final double molarSpecificHeat = 12.;
      doReturn(molarSpecificHeat).when(entity).getMolarSpecificConstantVolumeSpecificHeat();
      doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(molarSpecificHeat))
          .when(entity)
          .setMolarSpecificConstantVolumeSpecificHeat(anyDouble());
    }

    @Mock
    private AdsorbentCellEntity.ComponentValue<?> entity;

    /// [AdsorbentCell.ComponentValue#read(AdsorbentCellEntity.ComponentValue)] と
    /// [AdsorbentCell.ComponentValue#write(AdsorbentCellEntity.ComponentValue)] のテスト.
    @Test
    void testEntity() {
      initMock(this.entity);

      LocalCell.ComponentValue componentValue = new LocalCell.ComponentValue();
      componentValue.read(this.entity);
      componentValue.write(this.entity);
    }

    /// [AdsorbentCell.ComponentValue.Previous] のテスト.
    @Nested
    class PreviousTest {

      /// [AdsorbentCell.ComponentValue.Previous] の Getter と Setter をテスト.
      @Test
      void testGetterAndSetter() {
        GetterSetterTester.supplier(LocalCell.ComponentValue.Previous::new)
            .getter(AdsorbentCell.ComponentValue.Previous::getMolarQuantity)
            .setter(AdsorbentCell.ComponentValue.Previous::setMolarQuantity)
            .valid(10.)
            .thrown(IllegalArgumentException.class).value(0.)
            .test();
        GetterSetterTester.supplier(LocalCell.ComponentValue.Previous::new)
            .getter(AdsorbentCell.ComponentValue.Previous::getMolarSpecificConstantVolumeSpecificHeat)
            .setter(AdsorbentCell.ComponentValue.Previous::setMolarSpecificConstantVolumeSpecificHeat)
            .valid(12.)
            .thrown(IllegalArgumentException.class).value(0.)
            .test();
      }

      static void initMock(AdsorbentCellEntity.ComponentValue.Previous entity) {
        FluidCellTest.ComponentValueTest.PreviousTest.initMock(entity);

        final double molarQuantity = 10.;
        doReturn(molarQuantity).when(entity).getMolarQuantity();
        doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(molarQuantity))
            .when(entity)
            .setMolarQuantity(anyDouble());

        final double molarSpecificHeat = 12.;
        doReturn(molarSpecificHeat).when(entity).getMolarSpecificConstantVolumeSpecificHeat();
        doAnswer(invocation -> assertThat(invocation.getArgument(0, Double.class)).isEqualTo(molarSpecificHeat))
            .when(entity)
            .setMolarSpecificConstantVolumeSpecificHeat(anyDouble());
      }

      @Mock
      private AdsorbentCellEntity.ComponentValue.Previous entity;

      /// [AdsorbentCell.ComponentValue.Previous#read(AdsorbentCellEntity.ComponentValue.Previous)] と
      /// [AdsorbentCell.ComponentValue.Previous#write(AdsorbentCellEntity.ComponentValue.Previous)] のテスト.
      @Test
      void testEntity() {
        initMock(this.entity);

        LocalCell.ComponentValue.Previous previous = new LocalCell.ComponentValue.Previous();
        previous.read(this.entity);
        previous.write(this.entity);
      }

    } // PreviousTest

  } // ComponentValueTest

}
