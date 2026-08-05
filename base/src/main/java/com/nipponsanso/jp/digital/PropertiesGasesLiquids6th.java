/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital;

import com.google.common.base.Preconditions;

/// The Properties of Gases and Liquids 6th Edition 掲載のデータ.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
public final class PropertiesGasesLiquids6th {

  private PropertiesGasesLiquids6th() {}

  /// アルゴン Ar の分子量.
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final double ARGON_MOLECULAR_WEIGHT = 39.948;

  /// 水素 H<sub>2</sub> の分子量.
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final double HYDROGEN_MOLECULAR_WEIGHT = 2.016;

  /// 水蒸気 H<sub>2</sub>O の分子量.
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final double WATER_MOLECULAR_WEIGHT = 18.015;

  /// アンモニア NH<sub>3</sub> の分子量.
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final double AMMONIA_MOLECULAR_WEIGHT = 17.031;

  /// ヘリウム He の分子量.
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final double HELIUM_MOLECULAR_WEIGHT = 4.003;

  /// 窒素 N<sub>2</sub> の分子量.
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final double NITROGEN_MOLECULAR_WEIGHT = 28.013;

  /// ネオン Ne の分子量.
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final double NEON_MOLECULAR_WEIGHT = 20.180;

  /// 酸素 O<sub>2</sub> の分子量.
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final double OXYGEN_MOLECULAR_WEIGHT = 31.999;

  /// アルゴン Ar の偏心因子 (-).
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final double ARGON_ACENTRIC_FACTOR = -0.004;

  /// 水素 H<sub>2</sub> の偏心因子 (-).
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final double HYDROGEN_ACENTRIC_FACTOR = -0.216;

  /// 水蒸気 H<sub>2</sub>O の偏心因子 (-).
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final double WATER_ACENTRIC_FACTOR = 0.345;

  /// アンモニア NH<sub>3</sub> の偏心因子 (-).
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final double AMMONIA_ACENTRIC_FACTOR = 0.253;

  /// ヘリウム He の偏心因子 (-).
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final double HELIUM_ACENTRIC_FACTOR = -0.390;

  /// 窒素 N<sub>2</sub> の偏心因子 (-).
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final double NITROGEN_ACENTRIC_FACTOR = 0.038;

  /// ネオン Ne の偏心因子 (-).
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final double NEON_ACENTRIC_FACTOR = -0.040;

  /// 酸素 O<sub>2</sub> の偏心因子 (-).
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final double OXYGEN_ACENTRIC_FACTOR = 0.022;

  /// 水素 H<sub>2</sub> の臨界点.
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final CriticalPoint HYDROGEN_CRITICAL_POINT =
      new CriticalPoint(33.19, 1_313e3, 64.15e-6);

  /// アルゴン Ar の臨界点.
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final CriticalPoint ARGON_CRITICAL_POINT =
      new CriticalPoint(150.86, 4_898e3, 74.59e-6);

  /// 水蒸気 H<sub>2</sub>O の臨界点.
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final CriticalPoint WATER_CRITICAL_POINT =
      new CriticalPoint(647.10, 22_064e3, 55.95e-6);

  /// アンモニア NH<sub>3</sub> の臨界点.
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final CriticalPoint AMMONIA_CRITICAL_POINT =
      new CriticalPoint(405.65, 11_280e3, 72.47e-6);

  /// ヘリウム He の臨界点.
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final CriticalPoint HELIUM_CRITICAL_POINT =
      new CriticalPoint(5.20, 228e3, 57.30e-6);

  /// 窒素 N<sub>2</sub> の臨界点.
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final CriticalPoint NITROGEN_CRITICAL_POINT =
      new CriticalPoint(126.20, 3_400e3, 89.21e-6);

  /// ネオン Ne の臨界点.
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final CriticalPoint NEON_CRITICAL_POINT =
      new CriticalPoint(44.40, 2_653e3, 41.70e-6);

  /// 酸素 O<sub>2</sub> の臨界点.
  ///
  /// pp.692-705, TABLE A.1 より引用.
  public static final CriticalPoint OXYGEN_CRITICAL_POINT =
      new CriticalPoint(154.58, 5_043e3, 73.40e-6);

  /// アルゴン Ar の双極子モーメント (D).
  ///
  /// pp.706-719, TABLE A.2 より引用.
  public static final double ARGON_DIPOLE_MOMENT = 0.;

  /// 水素 H<sub>2</sub> の双極子モーメント (D).
  ///
  /// pp.706-719, TABLE A.2 より引用.
  public static final double HYDROGEN_DIPOLE_MOMENT = 0.;

  /// 水蒸気 H<sub>2</sub>O の双極子モーメント (D).
  ///
  /// pp.706-719, TABLE A.2 より引用.
  public static final double WATER_DIPOLE_MOMENT = 1.85;

  /// アンモニア NH<sub>3</sub> の双極子モーメント (D).
  ///
  /// pp.706-719, TABLE A.2 より引用.
  public static final double AMMONIA_DIPOLE_MOMENT = 1.47;

  /// ヘリウム He の双極子モーメント (D).
  ///
  /// pp.706-719, TABLE A.2 より引用.
  public static final double HELIUM_DIPOLE_MOMENT = 0.;

  /// 窒素 N<sub>2</sub> の双極子モーメント (D).
  ///
  /// pp.706-719, TABLE A.2 より引用.
  public static final double NITROGEN_DIPOLE_MOMENT = 0.;

  /// ネオン Ne の双極子モーメント (D).
  ///
  /// pp.706-719, TABLE A.2 より引用.
  public static final double NEON_DIPOLE_MOMENT = 0.;

  /// 酸素 O<sub>2</sub> の双極子モーメント (D).
  ///
  /// pp.706-719, TABLE A.2 より引用.
  public static final double OXYGEN_DIPOLE_MOMENT = 0.;

  /// アルゴン Ar の熱力学関数.
  ///
  /// pp.720-733, Table A.3 より引用.
  /// [ThermodynamicFunction#molarSpecificEntropy(double)] は実装されていない.
  public static final ThermodynamicFunction ARGON_THERMODYNAMIC_FUNCTION =
      new InnerThermodynamicFunction1(100., 1500., 20786., 0., 0., 0., 0., 0.);

  /// 水素 H<sub>2</sub> の熱力学関数.
  ///
  /// pp.720-733, Table A.3 より引用.
  /// [ThermodynamicFunction#molarSpecificEntropy(double)] は実装されていない.
  public static final ThermodynamicFunction HYDROGEN_THERMODYNAMIC_FUNCTION =
      new InnerThermodynamicFunction2(250., 1500., 27617., 9560., 2466., 3760., 567.6, 0.);

  /// 水蒸気 H<sub>2</sub>O の熱力学関数.
  ///
  /// pp.720-733, Table A.3 より引用.
  /// [ThermodynamicFunction#molarSpecificEntropy(double)] は実装されていない.
  public static final ThermodynamicFunction WATER_THERMODYNAMIC_FUNCTION =
      new InnerThermodynamicFunction2(100., 2273.1, 33363., 26790., 2610.5, 8896., 1169., 0.);

  /// アンモニア NH<sub>3</sub> の熱力学関数.
  ///
  /// pp.720-733, Table A.3 より引用.
  /// [ThermodynamicFunction#molarSpecificEntropy(double)] は実装されていない.
  public static final ThermodynamicFunction AMMONIA_THERMODYNAMIC_FUNCTION =
      new InnerThermodynamicFunction2(100., 1500., 33427., 48980., 2036., 22560., 882., 0.);

  /// ヘリウム He の熱力学関数.
  ///
  /// pp.720-733, Table A.3 より引用.
  /// [ThermodynamicFunction#molarSpecificEntropy(double)] は実装されていない.
  public static final ThermodynamicFunction HELIUM_THERMODYNAMIC_FUNCTION =
      new InnerThermodynamicFunction1(100., 1500., 20786., 0., 0., 0., 0., 0.);

  /// 窒素 N<sub>2</sub> の熱力学関数.
  ///
  /// pp.720-733, Table A.3 より引用.
  /// [ThermodynamicFunction#molarSpecificEntropy(double)] は実装されていない.
  public static final ThermodynamicFunction NITROGEN_THERMODYNAMIC_FUNCTION =
      new InnerThermodynamicFunction2(50., 1500., 29105., 8614.9, 1701.6, 103.47, 909.79, 0.);

  /// ネオン Ne の熱力学関数.
  ///
  /// pp.720-733, Table A.3 より引用.
  /// [ThermodynamicFunction#molarSpecificEntropy(double)] は実装されていない.
  public static final ThermodynamicFunction NEON_THERMODYNAMIC_FUNCTION =
      new InnerThermodynamicFunction1(100., 1500., 20786., 0., 0., 0., 0., 0.);

  /// 酸素 O<sub>2</sub> の熱力学関数.
  ///
  /// pp.720-733, Table A.3 より引用.
  /// [ThermodynamicFunction#molarSpecificEntropy(double)] は実装されていない.
  public static final ThermodynamicFunction OXYGEN_THERMODYNAMIC_FUNCTION =
      new InnerThermodynamicFunction2(50., 1500., 29103., 10040., 2526.5, 9356., 1153.8, 0.);

  /// アルゴン Ar のレナード-ジョーンズ・ポテンシャル.
  ///
  /// pp.749-751, Appendix B より引用.
  public static final LennardJonesPotential ARGON_LENNARD_JONES_POTENTIAL =
      new LennardJonesPotential(3.542, 93.3);

  /// ヘリウム He のレナード-ジョーンズ・ポテンシャル.
  ///
  /// pp.749-751, Appendix B より引用.
  public static final LennardJonesPotential HELIUM_LENNARD_JONES_POTENTIAL =
      new LennardJonesPotential(2.551, 10.22);

  /// ネオン Ne のレナード-ジョーンズ・ポテンシャル.
  ///
  /// pp.749-751, Appendix B より引用.
  public static final LennardJonesPotential NEON_LENNARD_JONES_POTENTIAL =
      new LennardJonesPotential(2.820, 32.8);

  /// 水素 H<sub>2</sub> のレナード-ジョーンズ・ポテンシャル.
  ///
  /// pp.749-751, Appendix B より引用.
  public static final LennardJonesPotential HYDROGEN_LENNARD_JONES_POTENTIAL =
      new LennardJonesPotential(2.827, 59.7);

  /// 水蒸気 H<sub>2</sub>O のレナード-ジョーンズ・ポテンシャル.
  ///
  /// pp.749-751, Appendix B より引用.
  public static final LennardJonesPotential WATER_LENNARD_JONES_POTENTIAL =
      new LennardJonesPotential(2.641, 809.1);

  /// アンモニア NH<sub>3</sub> のレナード-ジョーンズ・ポテンシャル.
  ///
  /// pp.749-751, Appendix B より引用.
  public static final LennardJonesPotential AMMONIA_LENNARD_JONES_POTENTIAL =
      new LennardJonesPotential(2.900, 558.3);

  /// 窒素 N<sub>2</sub> のレナード-ジョーンズ・ポテンシャル.
  ///
  /// pp.749-751, Appendix B より引用.
  public static final LennardJonesPotential NITROGEN_LENNARD_JONES_POTENTIAL =
      new LennardJonesPotential(3.798, 71.4);

  /// 酸素 O<sub>2</sub> のレナード-ジョーンズ・ポテンシャル.
  ///
  /// pp.749-751, Appendix B より引用.
  public static final LennardJonesPotential OXYGEN_LENNARD_JONES_POTENTIAL =
      new LennardJonesPotential(3.467, 106.7);

  /// 熱力学関数の内部実装(1).
  ///
  /// p.689, (A-1) 式の実装. このクラスは不変(immutable)である.
  ///
  /// @param minTemperature 最低温度 (K).
  /// @param maxTemperature 最高温度 (K).
  /// @param a 係数 _A_.
  /// @param b 係数 _B_.
  /// @param c 係数 _C_.
  /// @param d 係数 _D_.
  /// @param e 係数 _E_.
  /// @param molarSpecificEnthalpy0 モル比エンタルピの積分定数 (J mol<sup>-1</sup>).
  private record InnerThermodynamicFunction1(
      double minTemperature,
      double maxTemperature,
      @SuppressWarnings("checkstyle:MemberName") double a,
      @SuppressWarnings("checkstyle:MemberName") double b,
      @SuppressWarnings("checkstyle:MemberName") double c,
      @SuppressWarnings("checkstyle:MemberName") double d,
      @SuppressWarnings("checkstyle:MemberName") double e,
      double molarSpecificEnthalpy0) implements ThermodynamicFunction {

    @Override
    public double molarSpecificConstantPressureSpecificHeat(double temperature) {
      Preconditions.checkArgument(
          this.minTemperature <= temperature && temperature <= this.maxTemperature);

      return (this.a
          + temperature * (this.b
          + temperature * (this.c
          + temperature * (this.d
          + temperature * this.e)))) * 1e-3;
    }

    @Override
    public double molarSpecificEnthalpy(double temperature) {
      Preconditions.checkArgument(
          this.minTemperature <= temperature && temperature <= this.maxTemperature);

      return this.molarSpecificEnthalpy0
          + temperature * (this.a
          + temperature * (this.b / 2.
          + temperature * (this.c / 3.
          + temperature * (this.d / 4.
          + temperature * this.e / 5.)))) * 1e-3;
    }

  }

  /// 理想気体の比熱の内部実装2.
  ///
  /// p.689, (A-2) 式の実装.
  ///
  /// @param minTemperature 最低温度 (K).
  /// @param maxTemperature 最高温度 (K).
  /// @param a 係数 _A_.
  /// @param b 係数 _B_.
  /// @param c 係数 _C_.
  /// @param d 係数 _D_.
  /// @param e 係数 _E_.
  /// @param molarSpecificEnthalpy0 モル比エンタルピの積分定数 (J mol<sup>-1</sup>).
  private record InnerThermodynamicFunction2(
      double minTemperature,
      double maxTemperature,
      @SuppressWarnings("checkstyle:MemberName") double a,
      @SuppressWarnings("checkstyle:MemberName") double b,
      @SuppressWarnings("checkstyle:MemberName") double c,
      @SuppressWarnings("checkstyle:MemberName") double d,
      @SuppressWarnings("checkstyle:MemberName") double e,
      double molarSpecificEnthalpy0) implements ThermodynamicFunction {

    @Override
    public double molarSpecificConstantPressureSpecificHeat(double temperature) {
      Preconditions.checkArgument(
          this.minTemperature <= temperature && temperature <= this.maxTemperature);

      final double r = 1. / temperature;
      final double ct = this.c * r;
      final double et = this.e * r;
      final double cts = ct / Math.sinh(ct);
      final double etc = et / Math.cosh(et);

      return (this.a + this.b * cts * cts + this.d * etc * etc) * 1e-3;
    }

    @Override
    public double molarSpecificEnthalpy(double temperature) {
      Preconditions.checkArgument(
          this.minTemperature <= temperature && temperature <= this.maxTemperature);

      final double r = 1. / temperature;

      return this.molarSpecificEnthalpy0
          + (this.a * temperature
          + this.b * this.c / Math.tanh(this.c * r)
          - this.d * this.e * Math.tanh(this.e * r)) * 1e-3;
    }

  }

}
