/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital;

import com.google.common.base.Preconditions;

///
/// 伝熱工学資料改定第 5 版掲載のデータ.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
///
public class JsmeDataBookHeatTransfer5th {

  private JsmeDataBookHeatTransfer5th() {}

  ///
  /// ヘリウム・ネオン系混合気体の物質拡散係数を返す.
  ///
  /// @param temperature 温度 (K).
  /// @param pressure 圧力 (Pa).
  /// @return 物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
  /// @throws IllegalArgumentException `temperature` または `pressure` が範囲外の場合.
  ///
  public static double heliumNeonDiffusivity(double temperature, double pressure) {
    return diffusivity(temperature, pressure, 25.41e-3, 1.509, 0.212e8, 1.87, 0., 65., 1e4);
  }

  ///
  /// ヘリウム・水素系混合気体の物質拡散係数を返す.
  ///
  /// @param temperature 温度 (K).
  /// @param pressure 圧力 (Pa).
  /// @return 物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
  /// @throws IllegalArgumentException `temperature` または `pressure` が範囲外の場合.
  ///
  public static double heliumHydrogenDiffusivity(double temperature, double pressure) {
    return diffusivity(temperature, pressure, 27.0e-3, 1.510, 0.0534e8, 0., 0., 90., 1e4);
  }

  ///
  /// ヘリウム・窒素系混合気体の物質拡散係数を返す.
  ///
  /// @param temperature 温度 (K).
  /// @param pressure 圧力 (Pa).
  /// @return 物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
  /// @throws IllegalArgumentException `temperature` または `pressure` が範囲外の場合.
  ///
  public static double heliumNitrogenDiffusivity(double temperature, double pressure) {
    return diffusivity(temperature, pressure, 15.8e-3, 1.524, 0.265e8, 0., 0., 77., 1e4);
  }

  ///
  /// 水素・窒素系混合気体の物質拡散係数を返す.
  ///
  /// @param temperature 温度 (K).
  /// @param pressure 圧力 (Pa).
  /// @return 物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
  /// @throws IllegalArgumentException `temperature` または `pressure` が範囲外の場合.
  ///
  public static double hydrogenNitrogenDiffusivity(double temperature, double pressure) {
    return diffusivity(temperature, pressure, 15.39e-3, 1.548, 0.316e8, -2.80, 1067., 65., 1e4);
  }

  ///
  /// ネオン・水素系混合気体の物質拡散係数を返す.
  ///
  /// @param temperature 温度 (K).
  /// @param pressure 圧力 (Pa).
  /// @return 物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
  /// @throws IllegalArgumentException `temperature` または `pressure` が範囲外の場合.
  ///
  public static double neonHydrogenDiffusivity(double temperature, double pressure) {
    return diffusivity(temperature, pressure, 5.95e-5, 1.731, 0., 90., 1e4);
  }

  ///
  /// ネオン・窒素系混合気体の物質拡散係数を返す.
  ///
  /// @param temperature 温度 (K).
  /// @param pressure 圧力 (Pa).
  /// @return 物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
  /// @throws IllegalArgumentException `temperature` または `pressure` が範囲外の場合.
  ///
  public static double neonNitrogenDiffusivity(double temperature, double pressure) {
    return diffusivity(temperature, pressure, 1.59e-5, 1.743, 0., 293., 1e4);
  }

  ///
  /// アルゴン・窒素系混合気体の物質拡散係数を返す.
  ///
  /// @param temperature 温度 (K).
  /// @param pressure 圧力 (Pa).
  /// @return 物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
  /// @throws IllegalArgumentException `temperature` または `pressure` が範囲外の場合.
  public static double argonNitrogenDiffusivity(double temperature, double pressure) {
    return diffusivity(temperature, pressure, 0.904e-5, 1.752, 0., 244., 1e4);
  }

  ///
  /// アルゴン・酸素系混合気体の物質拡散係数を返す.
  ///
  /// @param temperature 温度 (K).
  /// @param pressure 圧力 (Pa).
  /// @return 物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
  /// @throws IllegalArgumentException `temperature` または `pressure` が範囲外の場合.
  ///
  public static double argonOxygenDiffusivity(double temperature, double pressure) {
    return diffusivity(temperature, pressure, 0.977e-5, 1.736, 0., 243., 1e4);
  }

  ///
  /// アルゴン・空気系混合気体の物質拡散係数を返す.
  ///
  /// @param temperature 温度 (K).
  /// @param pressure 圧力 (Pa).
  /// @return 物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
  /// @throws IllegalArgumentException `temperature` または `pressure` が範囲外の場合.
  ///
  public static double argonAirDiffusivity(double temperature, double pressure) {
    return diffusivity(temperature, pressure, 0.917e-5, 1.749, 0., 244., 1e4);
  }

  ///
  /// 窒素・酸素系混合気体の物質拡散係数を返す.
  ///
  /// @param temperature 温度 (K).
  /// @param pressure 圧力 (Pa).
  /// @return 物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
  /// @throws IllegalArgumentException `temperature` または `pressure` が範囲外の場合.
  ///
  public static double nitrogenOxygenDiffusivity(double temperature, double pressure) {
    return diffusivity(temperature, pressure, 1.13e-5, 1.724, 0., 285., 1e4);
  }

  ///
  /// 窒素・水蒸気系混合気体の物質拡散係数を返す.
  ///
  /// @param temperature 温度 (K).
  /// @param pressure 圧力 (Pa).
  /// @return 物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
  /// @throws IllegalArgumentException `temperature` または `pressure` が範囲外の場合.
  ///
  public static double nitrogenWaterDiffusivity(double temperature, double pressure) {
    return diffusivity(temperature, pressure, 0.187e-5, 2.072, 0., 282., 373);
  }

  ///
  /// p.332, (1)式.
  ///
  /// @param temperature 温度 (K).
  /// @param pressure 圧力 (Pa).
  /// @param a 係数 _A_.
  /// @param s 係数 _s_.
  /// @param phi0k 係数 _&phi_<sup>&#9675;</sup> / _k_.
  /// @param ss 係数 _S_.
  /// @param sd 係数 _S_'.
  /// @param minTemperature 下限温度 (K).
  /// @param maxTemperature 上限温度 (K).
  /// @return 物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
  ///
  private static double diffusivity(
      double temperature, double pressure,
      double a, double s, double phi0k, double ss, double sd,
      double minTemperature, @SuppressWarnings("SameParameterValue") double maxTemperature) {
    Preconditions.checkArgument(
        minTemperature <= temperature && temperature <= maxTemperature && pressure > 0.);

    final double r = 1. / temperature;
    final double lnPhi0kT = Math.log(phi0k * r);

    final double rhs
        = Math.log(a)
        + s * Math.log(temperature)
        - Math.log(lnPhi0kT * lnPhi0kT) - r * (ss + r * sd);

    return Math.exp(rhs) / (pressure / 101325.) * 1e-4;
  }

  ///
  /// p.332, (2)式.
  ///
  /// @param temperature 温度 (K).
  /// @param pressure 圧力 (Pa).
  /// @param a 係数 _A_.
  /// @param s 係数 _s_.
  /// @param ss 係数 _S_ .
  /// @param minTemperature 下限温度 (K).
  /// @param maxTemperature 上限温度 (K).
  /// @return 物質拡散係数 (m<sup>2</sup> s<sup>-1</sup>).
  ///
  private static double diffusivity(
      double temperature, double pressure,
      double a, double s, @SuppressWarnings("SameParameterValue") double ss,
      double minTemperature, double maxTemperature) {
    Preconditions.checkArgument(
        minTemperature <= temperature && temperature <= maxTemperature && pressure > 0.);

    final double rhs = Math.log(a) + s * Math.log(temperature) - ss / temperature;

    return Math.exp(rhs) / (pressure / 101325.) * 1e-4;
  }

}
