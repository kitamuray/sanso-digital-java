/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital;

/// 実数一変数多項式.
///
/// @param <N> 変数の型.
/// @param <P> 多項式の型.
/// @since 1.0
/// @author KITAMURA Yuichiro
public interface RealUnivariatePolynomial<
    N extends Number, P extends RealUnivariatePolynomial<N, P>>
    extends UnivariatePolynomial<N, P> {

}
