/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.collect.Maps;
import java.util.Map;

/// 流体を含むフェイス.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
interface FluidFace extends Face {

  /// 流体の質量流量を返す.
  ///
  /// @return 流体の質量流量 (kg s<sup>-1</sup>).
  double getMassFlow();

  /// 流体の質量流量を設定する.
  ///
  /// @param massFlow 流体の質量流量 (kg s<sup>-1</sup>).
  void setMassFlow(double massFlow);

  /// 流体のエネルギ流量を返す.
  ///
  /// @return 流体のエネルギ流量 (W).
  double getEnergyFlow();

  /// 流体のエネルギ流量を設定する.
  ///
  /// @param energyFlow 流体のエネルギ流量 (W).
  void setEnergyFlow(double energyFlow);

  /// 流体を含むフェイスの成分の不変マップを返す.
  ///
  /// @return 流体を含むフェイスの成分の不変マップ.
  @Override
  Map<ComponentKey, ? extends ComponentValue> componentMap();

  /// 流体を含むフェイスのインスタンスを生成して返す.
  ///
  /// @param composition 組成.
  /// @return 流体を含むフェイスのインスタンス.
  static FluidFace newInstance(Composition composition) {
    return new FluidFace() {

      private double massFlow;

      private double energyFlow;

      private final Map<ComponentKey, ? extends FluidFace.ComponentValue> componentMap
          = composition.componentKeySet().stream()
          .collect(
              Maps.toImmutableEnumMap(
                  componentKey -> componentKey,
                  _ -> ComponentValue.newInstance()));

      @Override
      public double getMassFlow() {
        return this.massFlow;
      }

      @Override
      public void setMassFlow(double massFlow) {
        this.massFlow = massFlow;
      }

      @Override
      public double getEnergyFlow() {
        return this.energyFlow;
      }

      @Override
      public void setEnergyFlow(double energyFlow) {
        this.energyFlow = energyFlow;
      }

      @Override
      public Map<ComponentKey, ? extends ComponentValue> componentMap() {
        return this.componentMap;
      }

    };
  }

  /// 流体を含むフェイスの成分値.
  interface ComponentValue extends Face.ComponentValue {

    /// 流体成分の化学種流量を返す.
    ///
    /// @return 流体成分の化学種流量 (kg s<sup>-1</sup>).
    double getSpeciesFlow();

    /// 流体成分の化学種流量を設定する.
    ///
    /// @param speciesFlow 流体成分の化学種流量 (kg s<sup>-1</sup>).
    void setSpeciesFlow(double speciesFlow);

    /// 流体を含むフェイスの成分値のインスタンスを生成して返す.
    ///
    /// @return 流体を含むフェイスの成分値のインスタンス.
    private static ComponentValue newInstance() {
      return new ComponentValue() {

        private double speciesFlow;

        @Override
        public double getSpeciesFlow() {
          return this.speciesFlow;
        }

        @Override
        public void setSpeciesFlow(double speciesFlow) {
          this.speciesFlow = speciesFlow;
        }

      };
    }

  } // ComponentValue

}
