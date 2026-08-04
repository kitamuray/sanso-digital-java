/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital;

///
/// 物理定数.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
///
public final class PhysicalConstants {

    private PhysicalConstants() {}

    ///
    /// ボルツマン定数 _k_<sub>B</sub> (J K<sup>-1</sup>).
    ///
    /// [定義値](https://physics.nist.gov/cgi-bin/cuu/Value?k)である.
    ///
    public static final double BOLTZMANN_CONSTANT = 1.380_649e-23;

    ///
    /// アボガドロ定数 _N_<sub>A</sub> (mol<sup>-1</sup>).
    ///
    /// [定義値](https://physics.nist.gov/cgi-bin/cuu/Value?na)である.
    ///
    public static final double AVOGADRO_CONSTANT = 6.022_140_76e+23;

    ///
    /// モル気体定数 _R_ (J mol<sup>-1</sup> K<sup>-1</sup>).
    ///
    /// [定義値](https://physics.nist.gov/cgi-bin/cuu/Value?r)である.
    ///
    public static final double MOLAR_GAS_CONSTANT = BOLTZMANN_CONSTANT * AVOGADRO_CONSTANT;

}
