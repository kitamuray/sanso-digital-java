/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

/// 弁のスケルトン実装.
///
/// @see <a href="https://www.fujikin.co.jp/ja/support/calculator/">
///     フジキン Cv 値計算・流量計算ツール</a>
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class Valve {

  /// CV 値を返す.
  ///
  /// @return CV 値.
  abstract double cvValue();

  /// 流量を計算する最小差圧 (Pa).
  private static final double DELTA_PRESSURE = 1e-6;

  /**
   * 摂氏15度, 標準大気圧における空気の密度 (kg m<sup>-3</sup>).
   */
  private static final double AIR_DENSITY = 1.2252;

  /// 弁の流量を返す.
  ///
  /// 二次側が一次側よりも高圧の場合, 弁の流量は負数となる.
  ///
  /// @param upCell 一次側の流体セル.
  /// @param downCell 二次側の流体セル.
  /// @return 弁の流量 (kg s<sup>-1</sup>).
  double flowRate(FluidCell upCell, FluidCell downCell) {
    double deltaPressure = upCell.getPressure() - downCell.getPressure();

    if (Math.abs(deltaPressure) < DELTA_PRESSURE) {
      return 0.;
    } else if (deltaPressure > 0.) {
      return this.flowRate2(upCell, downCell);
    } else {
      return - this.flowRate2(downCell, upCell);
    }
  }

  /// 弁の流量を返す.
  ///
  /// @param upCell 一次側（高圧側）の流体セル.
  /// @param downCell 二次側（低圧側）の流体セル.
  /// @return 弁の流量 (kg s<sup>-1</sup>).
  private double flowRate2(FluidCell upCell, FluidCell downCell) {
    final double p1 = upCell.getPressure() * 1e-6; // Pa -> MPa
    final double p2 = downCell.getPressure() * 1e-6; // Pa -> MPa
    final double g = upCell.getDensity() / AIR_DENSITY;
    double q = p2 > 0.5 * p1
        ? 4140. * this.cvValue() / Math.sqrt(g * upCell.getTemperature() / (p1 - p2) / p2)
        : 2070. * p1 * this.cvValue() / Math.sqrt(g * upCell.getTemperature());

    final double normalDensity = upCell.getDensity()
        * 101325. / upCell.getPressure()
        * upCell.getTemperature() / (15. + 273.15);

    return (q / 3600.) * normalDensity;
  }

}
