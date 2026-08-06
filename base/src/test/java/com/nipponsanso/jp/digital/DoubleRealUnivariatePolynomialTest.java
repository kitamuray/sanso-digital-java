/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/// [DoubleRealUnivariatePolynomial] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
class DoubleRealUnivariatePolynomialTest {

  /// [DoubleRealUnivariatePolynomial#DoubleRealUnivariatePolynomial(double\[\])] のテスト.
  @Test
  void testDoubleRealUnivariatePolynomialDoubleArray() {
    assertThatThrownBy(() -> new DoubleRealUnivariatePolynomial(null))
        .isInstanceOf(NullPointerException.class);
  }

  /// [DoubleRealUnivariatePolynomial#doubleCoefficients()] のテスト.
  @Test
  void testDoubleCoefficients() {
    final double[] doubleCoefficients = {1., 2., 3., 4., 5.};

    assertThat(new DoubleRealUnivariatePolynomial(doubleCoefficients).doubleCoefficients())
        .isEqualTo(doubleCoefficients);
  }

  /// [DoubleRealUnivariatePolynomial#coefficientList()] のテスト.
  @Test
  void testCoefficientList() {
    final double[] doubleCoefficients = {1., 2., 3., 4., 5.};
    final List<Double> coefficientList = List.of(1., 2., 3., 4., 5.);

    assertThat(new DoubleRealUnivariatePolynomial(doubleCoefficients).coefficientList())
        .isEqualTo(coefficientList);
  }

  /// 零次式のテスト.
  @Nested
  class ZerothOrderTest {

    private final double[] doubleCoefficients = {};

    private final DoubleUnaryOperator doubleResult = _ -> 0.;

    /// [DoubleRealUnivariatePolynomial#value(double)] のテスト.
    @ParameterizedTest
    @MethodSource
    void testValueDouble(double x) {
      assertThat(new DoubleRealUnivariatePolynomial(this.doubleCoefficients).value(x))
          .isEqualTo(this.doubleResult.applyAsDouble(x));
    }

    /// [#testValueDouble(double)] の引数を生成する.
    ///
    /// @return [#testValueDouble(double)] の引数ストリーム.
    private static Stream<Double> testValueDouble() { // Java 16 以前
      return Stream.of(1., 2., 3., 4., 5.);
    }

    /// [DoubleRealUnivariatePolynomial#value(Double)] のテスト.
    @ParameterizedTest
    @MethodSource
    void testValueObject(Double x) {
      assertThat(new DoubleRealUnivariatePolynomial(this.doubleCoefficients).value(x))
          .isEqualTo(this.doubleResult.applyAsDouble(x));
    }

    /// [#testValueObject(Double)] の引数を生成する.
    ///
    /// @return [#testValueObject(Double)] の引数ストリーム.
    private static Stream<Double> testValueObject() { // Java 16 以前
      return Stream.of(1., 2., 3., 4., 5.);
    }

    /// [DoubleRealUnivariatePolynomial#derivative()] のテスト.
    @Test
    void testDerivative() {
      assertThat(new DoubleRealUnivariatePolynomial(this.doubleCoefficients).derivative())
          .isEqualTo(new DoubleRealUnivariatePolynomial(new double[0]));
    }

  } // ZerothOrderTest

  /// 一次式のテスト.
  @Nested
  class FirstOrderTest {

    private final double[] doubleCoefficients = {1.};

    private final DoubleUnaryOperator doubleResult = _ -> this.doubleCoefficients[0];

    /// [DoubleRealUnivariatePolynomial#value(double)] のテスト.
    @ParameterizedTest
    @MethodSource
    void testValueDouble(double x) {
      assertThat(new DoubleRealUnivariatePolynomial(this.doubleCoefficients).value(x))
          .isEqualTo(this.doubleResult.applyAsDouble(x));
    }

    /// [#testValueDouble(double)] の引数を生成する.
    ///
    /// @return [#testValueDouble(double)] の引数ストリーム.
    private static Stream<Double> testValueDouble() { // Java 16 以前
      return Stream.of(1., 2., 3., 4., 5.);
    }

    /// [DoubleRealUnivariatePolynomial#value(Double)] のテスト.
    @ParameterizedTest
    @MethodSource
    void testValueObject(Double x) {
      assertThat(new DoubleRealUnivariatePolynomial(this.doubleCoefficients).value(x))
          .isEqualTo(this.doubleResult.applyAsDouble(x));
    }

    /// [#testValueObject(Double)] の引数を生成する.
    ///
    /// @return [#testValueObject(Double)] の引数ストリーム.
    private static Stream<Double> testValueObject() { // Java 16 以前
      return Stream.of(1., 2., 3., 4., 5.);
    }

    /// [DoubleRealUnivariatePolynomial#derivative()] のテスト.
    @Test
    void testDerivative() {
      assertThat(new DoubleRealUnivariatePolynomial(this.doubleCoefficients).derivative())
          .isEqualTo(new DoubleRealUnivariatePolynomial(new double[]{}));
    }

  } // FirstOrderTest

  /// 二次式のテスト.
  @Nested
  class SecondOrderTest {

    private final double[] doubleCoefficients = {1., 2.};

    private final DoubleUnaryOperator doubleResult =
        x -> this.doubleCoefficients[0] + x * this.doubleCoefficients[1];

    /// [DoubleRealUnivariatePolynomial#value(double)] のテスト.
    @ParameterizedTest
    @MethodSource
    void testValueDouble(double x) {
      assertThat(new DoubleRealUnivariatePolynomial(this.doubleCoefficients).value(x))
          .isEqualTo(this.doubleResult.applyAsDouble(x));
    }

    /// [#testValueDouble(double)] の引数を生成する.
    ///
    /// @return [#testValueDouble(double)] の引数ストリーム.
    private static Stream<Double> testValueDouble() { // Java 16 以前
      return Stream.of(1., 2., 3., 4., 5.);
    }

    /// [DoubleRealUnivariatePolynomial#value(Double)] のテスト.
    @ParameterizedTest
    @MethodSource
    void testValueObject(Double x) {
      assertThat(new DoubleRealUnivariatePolynomial(this.doubleCoefficients).value(x))
          .isEqualTo(this.doubleResult.applyAsDouble(x));
    }

    /// [#testValueObject(Double)] の引数を生成する.
    ///
    /// @return [#testValueObject(Double)] の引数ストリーム.
    private static Stream<Double> testValueObject() { // Java 16 以前
      return Stream.of(1., 2., 3., 4., 5.);
    }

    /// [DoubleRealUnivariatePolynomial#derivative()] のテスト.
    @Test
    void testDerivative() {
      assertThat(new DoubleRealUnivariatePolynomial(this.doubleCoefficients).derivative())
          .isEqualTo(new DoubleRealUnivariatePolynomial(new double[]{2.}));
    }

  } // SecondOrderTest

  /// 三次式のテスト.
  @Nested
  class ThirdOrderTest {

    private final double[] doubleCoefficients = {1., 2., 3.};

    private final DoubleUnaryOperator doubleResult =
        x -> this.doubleCoefficients[0] + x * (
            this.doubleCoefficients[1] + x * this.doubleCoefficients[2]);

    /// [DoubleRealUnivariatePolynomial#value(double)] のテスト.
    @ParameterizedTest
    @MethodSource
    void testValueDouble(double x) {
      assertThat(new DoubleRealUnivariatePolynomial(this.doubleCoefficients).value(x))
          .isEqualTo(this.doubleResult.applyAsDouble(x));
    }

    /// [#testValueDouble(double)] の引数を生成する.
    ///
    /// @return [#testValueDouble(double)] の引数ストリーム.
    private static Stream<Double> testValueDouble() { // Java 16 以前
      return Stream.of(1., 2., 3., 4., 5.);
    }

    /// [DoubleRealUnivariatePolynomial#value(Double)] のテスト.
    @ParameterizedTest
    @MethodSource
    void testValueObject(Double x) {
      assertThat(new DoubleRealUnivariatePolynomial(this.doubleCoefficients).value(x))
          .isEqualTo(this.doubleResult.applyAsDouble(x));
    }

    /// [#testValueObject(Double)] の引数を生成する.
    ///
    /// @return [#testValueObject(Double)] の引数ストリーム.
    private static Stream<Double> testValueObject() { // Java 16 以前
      return Stream.of(1., 2., 3., 4., 5.);
    }

    /// [DoubleRealUnivariatePolynomial#derivative()] のテスト.
    @Test
    void testDerivative() {
      assertThat(new DoubleRealUnivariatePolynomial(this.doubleCoefficients).derivative())
          .isEqualTo(new DoubleRealUnivariatePolynomial(new double[]{2., 6.}));
    }

  } // ThirdOrderTest

  /// 四次式のテスト.
  @Nested
  class FourthOrderTest {

    private final double[] doubleCoefficients = {1., 2., 3., 4.};

    private final DoubleUnaryOperator doubleResult =
        x -> this.doubleCoefficients[0] + x * (
            this.doubleCoefficients[1] + x * (
                this.doubleCoefficients[2] + x * this.doubleCoefficients[3]));

    /// [DoubleRealUnivariatePolynomial#value(double)] のテスト.
    @ParameterizedTest
    @MethodSource
    void testValueDouble(double x) {
      assertThat(new DoubleRealUnivariatePolynomial(this.doubleCoefficients).value(x))
          .isEqualTo(this.doubleResult.applyAsDouble(x));
    }

    /// [#testValueDouble(double)] の引数を生成する.
    ///
    /// @return [#testValueDouble(double)] の引数ストリーム.
    private static Stream<Double> testValueDouble() { // Java 16 以前
      return Stream.of(1., 2., 3., 4., 5.);
    }

    /// [DoubleRealUnivariatePolynomial#value(Double)] のテスト.
    @ParameterizedTest
    @MethodSource
    void testValueObject(Double x) {
      assertThat(new DoubleRealUnivariatePolynomial(this.doubleCoefficients).value(x))
          .isEqualTo(this.doubleResult.applyAsDouble(x));
    }

    /// [#testValueObject(Double)] の引数を生成する.
    ///
    /// @return [#testValueObject(Double)] の引数ストリーム.
    private static Stream<Double> testValueObject() { // Java 16 以前
      return Stream.of(1., 2., 3., 4., 5.);
    }

    /// [DoubleRealUnivariatePolynomial#derivative()] のテスト.
    @Test
    void testDerivative() {
      assertThat(new DoubleRealUnivariatePolynomial(this.doubleCoefficients).derivative())
          .isEqualTo(new DoubleRealUnivariatePolynomial(new double[]{2., 6., 12.}));
    }

  } // FourthOrderTest

  /// 五次式のテスト.
  @Nested
  class FifthOrderTest {

    private final double[] doubleCoefficients = {1., 2., 3., 4., 5.};

    private final DoubleUnaryOperator doubleResult =
        x -> this.doubleCoefficients[0] + x * (
            this.doubleCoefficients[1] + x * (
                this.doubleCoefficients[2] + x * (
                    this.doubleCoefficients[3] + x * this.doubleCoefficients[4])));

    /// [DoubleRealUnivariatePolynomial#value(double)] のテスト.
    @ParameterizedTest
    @MethodSource
    void testValueDouble(double x) {
      assertThat(new DoubleRealUnivariatePolynomial(this.doubleCoefficients).value(x))
          .isEqualTo(this.doubleResult.applyAsDouble(x));
    }

    /// [#testValueDouble(double)] の引数を生成する.
    ///
    /// @return [#testValueDouble(double)] の引数ストリーム.
    private static Stream<Double> testValueDouble() { // Java 16 以前
      return Stream.of(1., 2., 3., 4., 5.);
    }

    /// [DoubleRealUnivariatePolynomial#value(Double)] のテスト.
    @ParameterizedTest
    @MethodSource
    void testValueObject(Double x) {
      assertThat(new DoubleRealUnivariatePolynomial(this.doubleCoefficients).value(x))
          .isEqualTo(this.doubleResult.applyAsDouble(x));
    }

    /// [#testValueObject(Double)] の引数を生成する.
    ///
    /// @return [#testValueObject(Double)] の引数ストリーム.
    private static Stream<Double> testValueObject() { // Java 16 以前
      return Stream.of(1., 2., 3., 4., 5.);
    }

    /// [DoubleRealUnivariatePolynomial#derivative()] のテスト.
    @Test
    void testDerivative() {
      assertThat(new DoubleRealUnivariatePolynomial(this.doubleCoefficients).derivative())
          .isEqualTo(new DoubleRealUnivariatePolynomial(new double[]{2., 6., 12., 20.}));
    }

  } // FifthOrderTest

}
