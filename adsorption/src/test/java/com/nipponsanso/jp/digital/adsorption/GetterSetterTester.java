/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;

/// Getter メソッドと Setter メソッドのテスト.
///
/// @param <T> テスト対象の型.
/// @since 1.0
/// @author KITAMURA Yuichiro
final class GetterSetterTester<T> {

  /// テスト対象のオブジェクトを生成するオペレーション.
  private final Supplier<T> supplier;

  /// テスタのインスタンスを生成する.
  ///
  /// @param supplier テスト対象のオブジェクトを生成するオペレーション.
  private GetterSetterTester(Supplier<T> supplier) {
    this.supplier = supplier;
  }

  /// テスト対象のオブジェクトを生成するオペレーションを設定する.
  ///
  /// @param <T> テスト対象の型.
  /// @param supplier テスト対象のオブジェクトを生成するオペレーション.
  /// @return Getter メソッドと Setter メソッドのテストのインスタンス.
  static <T> GetterSetterTester<T> supplier(Supplier<T> supplier) {
    return new GetterSetterTester<>(supplier);
  }

  /// 参照型の getter メソッドを設定する.
  ///
  /// @param <E> 参照型の型.
  /// @param getter Getter メソッド.
  /// @return Setter メソッドを設定するテスタのインスタンス.
  <E> ObjectTester<E>.SetterTester getter(Function<T, E> getter) {
    return new ObjectTester<>(getter).new SetterTester();
  }

  /// プリミティブ double 型の getter メソッドを設定する.
  ///
  /// @param getter Getter メソッド.
  /// @return Setter メソッドを設定するテスタのインスタンス.
  DoubleTester.SetterTester getter(ToDoubleFunction<T> getter) {
    return new DoubleTester(getter).new SetterTester();
  }

  /// 参照型を対象とするテスタ.
  final class ObjectTester<E> {

    /// Getter メソッド.
    private final Function<T, E> getter;

    /// Setter メソッド.
    private BiConsumer<T, E> setter;

    /// 正常値.
    private E validValue;

    /// 例外の実行クラスと異常値のマップ.
    private final Map<Class<? extends Throwable>, List<E>> invalidValueListMap = new HashMap<>();

    /// 参照型を対象とするテスタのインスタンスを生成する.
    ///
    /// @param getter Getter メソッド.
    private ObjectTester(Function<T, E> getter) {
      this.getter = getter;
    }

    /// Setter メソッドを設定するテスタ.
    final class SetterTester {

      /// Setter メソッドを設定するテスタのインスタンスを生成する.
      private SetterTester() {}

      /// Setter メソッドを設定する.
      ///
      /// @param setter Setter メソッド.
      /// @return 初期値を設定するテスタのインスタンス.
      ValidValueTester setter(BiConsumer<T, E> setter) {
        ObjectTester.this.setter = setter;
        return new ValidValueTester();
      }

    } // SetterTester

    /// 正常値を設定するテスタ.
    final class ValidValueTester {

      /// 正常値を設定するテスタのインスタンスを生成する.
      private ValidValueTester() {}

      /// 正常値を設定する.
      ///
      /// @param validValue 正常値.
      /// @return 例外クラスと異常値のリストのマップを設定するテスタのインスタンス.
      InvalidValueListMapTester valid(E validValue) {
        ObjectTester.this.validValue = validValue;
        return new InvalidValueListMapTester();
      }

    } // ValidValueTester

    /// 例外の実行クラスと異常値のリストのマップを設定するテスタ.
    final class InvalidValueListMapTester {

      /// 例外クラス.
      private Class<? extends Throwable> clazz;

      /// 例外クラスと異常値のリストのマップを設定するテスタのインスタンスを生成する.
      private InvalidValueListMapTester() {}

      /// テストを実行する.
      void test() {
        T object = GetterSetterTester.this.supplier.get();

        // 異常値の検査 -> テスト失敗を想定し, stream ではなく loop を使用
        for (Map.Entry<Class<? extends Throwable>, List<E>> entry
            : ObjectTester.this.invalidValueListMap.entrySet()) {
          for (E invalidValue : entry.getValue()) {
            assertThatThrownBy(() -> ObjectTester.this.setter.accept(object, invalidValue))
                .isInstanceOf(entry.getKey());
          }
        }

        // 正常値の検査
        ObjectTester.this.setter.accept(object, ObjectTester.this.validValue);
        assertThat(ObjectTester.this.getter.apply(object))
            .isEqualTo(ObjectTester.this.validValue);
      }

      /// 例外の実行クラスを設定する.
      ///
      /// @param clazz 例外の実行クラス.
      /// @return 異常値を設定するビルダのインスタンス.
      InvalidValueTester thrown(
          @SuppressWarnings("SameParameterValue") Class<? extends Throwable> clazz) {
        this.clazz = clazz;
        return new InvalidValueTester();
      }

      /// 異常値を設定するテスタ.
      final class InvalidValueTester {

        /// 異常値を設定するテスタのインスタンスを生成する.
        private InvalidValueTester() {}

        /// 異常値を設定する.
        ///
        /// @param invalidValue 異常値.
        /// @return 例外クラスと異常値のリストのマップを設定するテスタのインスタンス.
        InvalidValueListMapTester value(@SuppressWarnings("SameParameterValue") E invalidValue) {
          ObjectTester.this.invalidValueListMap.putIfAbsent(
              InvalidValueListMapTester.this.clazz,
              new ArrayList<>());
          ObjectTester.this.invalidValueListMap
              .get(InvalidValueListMapTester.this.clazz)
              .add(invalidValue);
          return InvalidValueListMapTester.this;
        }

      } // InvalidValueTester

    } // InvalidValueListMapTester

  } // ObjectTester

  /// プリミティブ double 型を対象とするテスタ.
  final class DoubleTester {

    /// Getter メソッド.
    private final ToDoubleFunction<T> getter;

    /// Setter メソッド.
    private ObjDoubleConsumer<T> setter;

    /// 正常値.
    private double validValue;

    /// 例外の実行クラスと異常値のマップ.
    private final Map<Class<? extends Throwable>, List<Double>> invalidValueListMap =
        new HashMap<>();

    /// プリミティブ double 型を対象とするテスタのインスタンスを生成する.
    ///
    /// @param getter Getter メソッド.
    private DoubleTester(ToDoubleFunction<T> getter) {
      this.getter = getter;
    }

    /// Setter メソッドを設定するテスタ.
    final class SetterTester {

      /// Setter メソッドを設定するテスタのインスタンスを生成する.
      private SetterTester() {}

      /// Setter メソッドを設定する.
      ///
      /// @param setter Setter メソッド.
      /// @return 初期値を設定するテスタのインスタンス.
      ValidValueTester setter(ObjDoubleConsumer<T> setter) {
        DoubleTester.this.setter = setter;
        return new ValidValueTester();
      }

    } // SetterTester

    /// 正常値を設定するテスタ.
    final class ValidValueTester {

      /// 正常値を設定するテスタのインスタンスを生成する.
      private ValidValueTester() {}

      /// 正常値を設定する.
      ///
      /// @param validValue 正常値.
      /// @return 例外クラスと異常値のリストのマップを設定するテスタのインスタンス.
      InvalidValueListMapTester valid(double validValue) {
        DoubleTester.this.validValue = validValue;
        return new InvalidValueListMapTester();
      }

    } // ValidValueTester

    /// 例外の実行クラスと異常値のリストのマップを設定するテスタ.
    final class InvalidValueListMapTester {

      /// 例外クラス.
      private Class<? extends Throwable> clazz;

      /// 例外クラスと異常値のリストのマップを設定するテスタのインスタンスを生成する.
      private InvalidValueListMapTester() {}

      /// テストを実行する.
      void test() {
        T object = GetterSetterTester.this.supplier.get();

        // 異常値の検査 -> テスト失敗を想定し, stream ではなく loop を使用
        for (Map.Entry<Class<? extends Throwable>, List<Double>> entry
            : DoubleTester.this.invalidValueListMap.entrySet()) {
          for (Double invalidValue : entry.getValue()) {
            assertThatThrownBy(() -> DoubleTester.this.setter.accept(object, invalidValue))
                .isInstanceOf(entry.getKey());
          }
        }

        // 正常値の検査
        DoubleTester.this.setter.accept(object, DoubleTester.this.validValue);
        assertThat(DoubleTester.this.getter.applyAsDouble(object))
            .isEqualTo(DoubleTester.this.validValue);
      }

      /// 例外の実行クラスを設定する.
      ///
      /// @param clazz 例外の実行クラス.
      /// @return 異常値を設定するビルダのインスタンス.
      InvalidValueTester thrown(
          @SuppressWarnings("SameParameterValue") Class<? extends Throwable> clazz) {
        this.clazz = clazz;
        return new InvalidValueTester();
      }

      /// 異常値を設定するテスタ.
      final class InvalidValueTester {

        /// 異常値を設定するテスタのインスタンスを生成する.
        private InvalidValueTester() {}

        /// 異常値を設定する.
        ///
        /// @param value 異常値.
        /// @return 例外クラスと異常値のリストのマップを設定するテスタのインスタンス.
        InvalidValueListMapTester value(@SuppressWarnings("SameParameterValue") double value) {
          DoubleTester.this.invalidValueListMap.putIfAbsent(
              InvalidValueListMapTester.this.clazz,
              new ArrayList<>());
          DoubleTester.this.invalidValueListMap
              .get(InvalidValueListMapTester.this.clazz)
              .add(value);
          return InvalidValueListMapTester.this;
        }

      } // InvalidValueTester

    } // InvalidValueListMapTester

  } // DoubleTester

}
