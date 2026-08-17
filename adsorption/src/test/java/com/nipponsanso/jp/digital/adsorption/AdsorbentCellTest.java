/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

    } // PreviousTest

  } // ComponentValueTest

}
