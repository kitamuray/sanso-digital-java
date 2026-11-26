/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.Map;

/// 吸着筒のセル.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
abstract class ColumnCell2 implements GridCell, FluidCell2, ShellCell2, AdsorbentCell2, SolverCell {

  /// 数値の位置.
  private enum Position {

    /// 重心座標の位置.
    CENTROID,

    /// 格子長さの位置.
    LENGTH,

    /// 流体領域の体積の位置.
    FLUID_VOLUME,

    /// 容器領域の体積の位置.
    SHELL_VOLUME,

    /// 容器内面の面積の位置.
    INSIDE_AREA,

    /// 容器外面の面積の位置.
    OUTSIDE_AREA,

    /// 流体・吸着質・吸着剤の熱力学的温度の位置.
    TEMPERATURE,

    /// 流体の絶対圧力の位置.
    PRESSURE,

    /// 流体の密度の位置.
    DENSITY,

    /// 流体の比エンタルピの位置.
    SPECIFIC_ENTHALPY,

    /// 流体の定圧比熱の位置.
    SPECIFIC_CONSTANT_PRESSURE_SPECIFIC_HEAT,

    /// 流体の粘性係数の位置.
    VISCOSITY,

    /// 流体の熱伝導率の位置.
    THERMAL_CONDUCTIVITY,

    /// 流体の平均分子量の位置.
    MOLECULAR_WEIGHT,

    /// 容器の熱力学的温度の位置.
    SHELL_TEMPERATURE,

    /// 容器の比熱の位置.
    SHELL_SPECIFIC_HEAT,

    /// 容器の熱伝導率の位置.
    SHELL_THERMAL_CONDUCTIVITY,

    /// 吸着剤の比熱の位置.
    ADSORBENT_SPECIFIC_HEAT,

    /// 離散化係数 <i>a</i><sub><i>p</i></sub> の位置.
    AP,

    /// 離散化係数 <i>a</i><sub><i>w</i></sub> の位置.
    AW,

    /// 離散化係数 <i>a</i><sub><i>e</i></sub> の位置.
    AE,

    /// 離散化係数 <i>S</i><sub><i>p</i></sub> の位置.
    SP,

    /// 離散化係数 <i>S</i><sub><i>c</i></sub> の位置.
    SC,

    /// 作業変数 <i>P</i> の位置.
    P,

    /// 作業変数 <i>Q</i> の位置.
    Q

  } // Position

  /// 数値の位置のオフセット量.
  private final int offset;

  /// 吸着筒のセルの前ステップ値.
  private final Previous previous;

  /// 吸着筒のセルの成分の不変マップ.
  private final Map<ComponentKey, ComponentValue> componentMap;

  /// 容器.
  private final Shell shell;

  /// 吸着剤.
  private final Adsorbent adsorbent;

  /// 吸着筒のセルのインスタンスを生成する.
  ///
  /// @param offset 数値の位置のオフセット量.
  /// @param composition 組成.
  /// @param shell 容器.
  /// @param adsorbent 吸着剤.
  /// @param shape 形状.
  /// @param fromX 始点座標 (m).
  /// @param toX 終点座標 (m).
  ColumnCell2(
      int offset, Composition composition, Shell shell, Adsorbent adsorbent,
      ColumnShape shape, double fromX, double toX) {
    int o = this.offset = offset;
    o += Position.values().length;

    this.previous = new Previous(o);
    o += Previous.number();

    Map<ComponentKey, ComponentValue> map = new EnumMap<>(ComponentKey.class);
    for (ComponentKey componentKey : composition.componentKeySet()) {
      map.put(componentKey, new ComponentValue(o));
      o += ComponentValue.number();
    }
    this.componentMap = Maps.immutableEnumMap(map);

    this.shell = shell;
    this.adsorbent = adsorbent;

    // 要注意 : コンストラクタ内からインスタンスメソッドを利用
    this.setValue(Position.CENTROID, .5 * (fromX + toX));
    this.setValue(Position.LENGTH, toX - fromX);
    this.setValue(Position.FLUID_VOLUME, shape.fluidVolume(fromX, toX));
    this.setValue(Position.SHELL_VOLUME, shape.shellVolume(fromX, toX));
    this.setValue(Position.INSIDE_AREA, shape.insideArea(fromX, toX));
    this.setValue(Position.OUTSIDE_AREA, shape.outsideArea(fromX, toX));
  }

  /// 数値を記憶する配列を返す.
  ///
  /// @return 数値を記憶する配列.
  abstract double[] doubleArray();

  /// 吸着筒のセルにおける数値の数を返す.
  ///
  /// @param composition 組成.
  /// @return 吸着筒のセルにおける数値の数.
  static int number(Composition composition) {
    return Position.values().length
        + Previous.number()
        + composition.componentKeySet().size() * ComponentValue.number();
  }

  /// 指定された数値を返す.
  ///
  /// @param position 数値の位置.
  /// @return 指定された数値.
  private double getValue(Position position) {
    return this.doubleArray()[this.offset + position.ordinal()];
  }

  /// 指定された数値を設定する.
  ///
  /// @param position 数値の位置.
  /// @param value 指定された新な数値.
  private void setValue(Position position, double value) {
    this.doubleArray()[this.offset + position.ordinal()] = value;
  }

  @Override
  public double centroid() {
    return this.getValue(Position.CENTROID);
  }

  @Override
  public double length() {
    return this.getValue(Position.LENGTH);
  }

  @Override
  public double fluidVolume() {
    return this.getValue(Position.FLUID_VOLUME);
  }

  @Override
  public double shellVolume() {
    return this.getValue(Position.SHELL_VOLUME);
  }

  @Override
  public double insideArea() {
    return this.getValue(Position.INSIDE_AREA);
  }

  @Override
  public double outsideArea() {
    return this.getValue(Position.OUTSIDE_AREA);
  }

  @Override
  public double getTemperature() {
    return this.getValue(Position.TEMPERATURE);
  }

  @Override
  public void setTemperature(double temperature) {
    Preconditions.checkArgument(temperature > 0.);
    this.setValue(Position.TEMPERATURE, temperature);
  }

  @Override
  public double getPressure() {
    return this.getValue(Position.PRESSURE);
  }

  @Override
  public void setPressure(double pressure) {
    Preconditions.checkArgument(pressure > 0.);
    this.setValue(Position.PRESSURE, pressure);
  }

  @Override
  public double getDensity() {
    return this.getValue(Position.DENSITY);
  }

  @Override
  public void setDensity(double density) {
    Preconditions.checkArgument(density > 0.);
    this.setValue(Position.DENSITY, density);
  }

  @Override
  public double getSpecificEnthalpy() {
    return this.getValue(Position.SPECIFIC_ENTHALPY);
  }

  @Override
  public void setSpecificEnthalpy(double specificEnthalpy) {
    this.setValue(Position.SPECIFIC_ENTHALPY, specificEnthalpy);
  }

  @Override
  public double getSpecificConstantPressureSpecificHeat() {
    return this.getValue(Position.SPECIFIC_CONSTANT_PRESSURE_SPECIFIC_HEAT);
  }

  @Override
  public void setSpecificConstantPressureSpecificHeat(double specificConstantPressureSpecificHeat) {
    Preconditions.checkArgument(specificConstantPressureSpecificHeat > 0.);
    this.setValue(
        Position.SPECIFIC_CONSTANT_PRESSURE_SPECIFIC_HEAT,
        specificConstantPressureSpecificHeat);
  }

  @Override
  public double getViscosity() {
    return this.getValue(Position.VISCOSITY);
  }

  @Override
  public void setViscosity(double viscosity) {
    Preconditions.checkArgument(viscosity > 0.);
    this.setValue(Position.VISCOSITY, viscosity);
  }

  @Override
  public double getThermalConductivity() {
    return this.getValue(Position.THERMAL_CONDUCTIVITY);
  }

  @Override
  public void setThermalConductivity(double thermalConductivity) {
    Preconditions.checkArgument(thermalConductivity > 0.);
    this.setValue(Position.THERMAL_CONDUCTIVITY, thermalConductivity);
  }

  @Override
  public double getMolecularWeight() {
    return this.getValue(Position.MOLECULAR_WEIGHT);
  }

  @Override
  public void setMolecularWeight(double molecularWeight) {
    this.setValue(Position.MOLECULAR_WEIGHT, molecularWeight);
  }

  @Override
  public double shellDensity() {
    return this.shell.density();
  }

  @Override
  public double getShellTemperature() {
    return this.getValue(Position.SHELL_TEMPERATURE);
  }

  @Override
  public void setShellTemperature(double shellTemperature) {
    Preconditions.checkArgument(shellTemperature > 0.);
    this.setValue(Position.SHELL_TEMPERATURE, shellTemperature);
  }

  @Override
  public double getShellSpecificHeat() {
    return this.getValue(Position.SHELL_SPECIFIC_HEAT);
  }

  @Override
  public void setShellSpecificHeat(double shellSpecificHeat) {
    Preconditions.checkArgument(shellSpecificHeat > 0.);
    this.setValue(Position.SHELL_SPECIFIC_HEAT, shellSpecificHeat);
  }

  @Override
  public double getShellThermalConductivity() {
    return this.getValue(Position.SHELL_THERMAL_CONDUCTIVITY);
  }

  @Override
  public void setShellThermalConductivity(double shellThermalConductivity) {
    Preconditions.checkArgument(shellThermalConductivity > 0.);
    this.setValue(Position.SHELL_THERMAL_CONDUCTIVITY, shellThermalConductivity);
  }

  @Override
  public double particleDiameter() {
    return this.adsorbent.diameter();
  }

  @Override
  public double voidFraction() {
    return this.adsorbent.voidFraction();
  }

  @Override
  public double bulkDensity() {
    return this.adsorbent.bulkDensity();
  }

  @Override
  public double getAdsorbentSpecificHeat() {
    return this.getValue(Position.ADSORBENT_SPECIFIC_HEAT);
  }

  @Override
  public void setAdsorbentSpecificHeat(double adsorbentSpecificHeat) {
    Preconditions.checkArgument(adsorbentSpecificHeat > 0.);
    this.setValue(Position.ADSORBENT_SPECIFIC_HEAT, adsorbentSpecificHeat);
  }

  @Override
  public double getAp() {
    return this.getValue(Position.AP);
  }

  @Override
  public void setAp(double ap) {
    this.setValue(Position.AP, ap);
  }

  @Override
  public double getAw() {
    return this.getValue(Position.AW);
  }

  @Override
  public void setAw(double aw) {
    this.setValue(Position.AW, aw);
  }

  @Override
  public double getAe() {
    return this.getValue(Position.AE);
  }

  @Override
  public void setAe(double ae) {
    this.setValue(Position.AE, ae);
  }

  @Override
  public double getSp() {
    return this.getValue(Position.SP);
  }

  @Override
  public void setSp(double sp) {
    this.setValue(Position.SP, sp);
  }

  @Override
  public double getSc() {
    return this.getValue(Position.SC);
  }

  @Override
  public void setSc(double sc) {
    this.setValue(Position.SC, sc);
  }

  @Override
  public double getP() {
    return this.getValue(Position.P);
  }

  @Override
  public void setP(double p) {
    this.setValue(Position.P, p);
  }

  @Override
  public double getQ() {
    return this.getValue(Position.Q);
  }

  @Override
  public void setQ(double q) {
    this.setValue(Position.Q, q);
  }

  @Override
  public Previous previous() {
    return this.previous;
  }

  @Override
  public Map<ComponentKey, ComponentValue> componentMap() {
    return this.componentMap;
  }

  /// 永続化クラスのインスタンスを生成して返す.
  ///
  /// @return 永続化クラスのインスタンス.
  ColumnCellEntity2 newEntity() {
    return new ColumnCellEntity2(
        this.getTemperature(),
        this.getPressure(),
        this.getDensity(),
        this.getSpecificEnthalpy(),
        this.getSpecificConstantPressureSpecificHeat(),
        this.getViscosity(),
        this.getThermalConductivity(),
        this.getShellTemperature(),
        this.getShellSpecificHeat(),
        this.getShellThermalConductivity(),
        this.getAdsorbentSpecificHeat(),
        this.previous.newEntity(),
        this.componentMap.entrySet().stream()
            .collect(
                Maps.toImmutableEnumMap(
                    Map.Entry::getKey,
                    entry -> entry.getValue().newEntity())));
  }

  /// 永続化クラスのインスタンスを反映する.
  ///
  /// @param entity 永続化クラスのインスタンス.
  /// @throws NullPointerException `entity` またはそれが構成する要素が `null` の場合.
  /// @throws IllegalArgumentException `entity` を構成する要素の値が範囲外の場合.
  void accept(ColumnCellEntity2 entity) {
    Preconditions.checkNotNull(entity);
    Preconditions.checkNotNull(entity.previous());
    Preconditions.checkNotNull(entity.componentMap());
    Preconditions.checkArgument(this.componentMap.keySet().equals(entity.componentMap().keySet()));
    entity.componentMap().values().forEach(Preconditions::checkNotNull);

    this.setTemperature(entity.temperature());
    this.setPressure(entity.pressure());
    this.setDensity(entity.density());
    this.setSpecificEnthalpy(entity.specificEnthalpy());
    this.setSpecificConstantPressureSpecificHeat(entity.specificConstantPressureSpecificHeat());
    this.setViscosity(entity.viscosity());
    this.setThermalConductivity(entity.thermalConductivity());
    this.setShellTemperature(entity.shellTemperature());
    this.setShellSpecificHeat(entity.shellSpecificHeat());
    this.setShellThermalConductivity(entity.thermalConductivity());
    this.setAdsorbentSpecificHeat(entity.adsorbentSpecificHeat());
    this.previous.accept(entity.previous());
    this.componentMap.forEach((key, value) -> value.accept(entity.componentMap().get(key)));
  }

  /// 吸着筒のセルの前ステップ値.
  final class Previous implements FluidCell2.Previous, ShellCell2.Previous, AdsorbentCell2.Previous {

    /// 数値の位置.
    private enum Position {

      /// 流体・吸着質・吸着剤の熱力学的温度の位置.
      TEMPERATURE,

      /// 流体の密度の位置.
      DENSITY,

      /// 流体の比エンタルピの位置.
      SPECIFIC_ENTHALPY,

      /// 流体の定圧比熱の位置.
      SPECIFIC_CONSTANT_PRESSURE_SPECIFIC_HEAT,

      /// 容器の熱力学的温度の位置.
      SHELL_TEMPERATURE,

      /// 容器の比熱の位置.
      SHELL_SPECIFIC_HEAT,

      /// 吸着剤の比熱の位置.
      ADSORBENT_SPECIFIC_HEAT

    } // Previous.Position

    /// 数値の位置のオフセット量.
    private final int offset;

    /// 吸着筒のセルの前ステップ値のインスタンスを生成する.
    ///
    /// @param offset 数値の位置のオフセット量.
    private Previous(int offset) {
      this.offset = offset;
    }

    /// 吸着筒のセルの前ステップ値における数値の数を返す.
    ///
    /// @return 吸着筒のセルの前ステップ値における数値の数.
    private static int number() {
      return Position.values().length;
    }

    /// 指定された数値を返す.
    ///
    /// @param position 数値の位置.
    /// @return 指定された数値.
    private double getValue(Position position) {
      return ColumnCell2.this.doubleArray()[this.offset + position.ordinal()];
    }

    /// 指定された数値を設定する.
    ///
    /// @param position 数値の位置.
    /// @param value 指定された新な数値.
    private void setValue(Position position, double value) {
      ColumnCell2.this.doubleArray()[this.offset + position.ordinal()] = value;
    }

    @Override
    public double getTemperature() {
      return this.getValue(Position.TEMPERATURE);
    }

    @Override
    public void setTemperature(double temperature) {
      Preconditions.checkArgument(temperature > 0.);
      this.setValue(Position.TEMPERATURE, temperature);
    }
    @Override
    public double getDensity() {
      return this.getValue(Position.DENSITY);
    }

    @Override
    public void setDensity(double density) {
      Preconditions.checkArgument(density > 0.);
      this.setValue(Position.DENSITY, density);
    }

    @Override
    public double getSpecificEnthalpy() {
      return this.getValue(Position.SPECIFIC_ENTHALPY);
    }

    @Override
    public void setSpecificEnthalpy(double specificEnthalpy) {
      this.setValue(Position.SPECIFIC_ENTHALPY, specificEnthalpy);
    }

    @Override
    public double getSpecificConstantPressureSpecificHeat() {
      return this.getValue(Position.SPECIFIC_CONSTANT_PRESSURE_SPECIFIC_HEAT);
    }

    @Override
    public void setSpecificConstantPressureSpecificHeat(double specificConstantPressureSpecificHeat) {
      Preconditions.checkArgument(specificConstantPressureSpecificHeat > 0.);
      this.setValue(
          Position.SPECIFIC_CONSTANT_PRESSURE_SPECIFIC_HEAT,
          specificConstantPressureSpecificHeat);
    }

    @Override
    public double getShellTemperature() {
      return this.getValue(Position.SHELL_TEMPERATURE);
    }

    @Override
    public void setShellTemperature(double shellTemperature) {
      Preconditions.checkArgument(shellTemperature > 0.);
      this.setValue(Position.SHELL_TEMPERATURE, shellTemperature);
    }

    @Override
    public double getShellSpecificHeat() {
      return this.getValue(Position.SHELL_SPECIFIC_HEAT);
    }

    @Override
    public void setShellSpecificHeat(double shellSpecificHeat) {
      Preconditions.checkArgument(shellSpecificHeat > 0.);
      this.setValue(Position.SHELL_SPECIFIC_HEAT, shellSpecificHeat);
    }

    @Override
    public double getAdsorbentSpecificHeat() {
      return this.getValue(Position.ADSORBENT_SPECIFIC_HEAT);
    }

    @Override
    public void setAdsorbentSpecificHeat(double adsorbentSpecificHeat) {
      Preconditions.checkArgument(adsorbentSpecificHeat > 0.);
      this.setValue(Position.ADSORBENT_SPECIFIC_HEAT, adsorbentSpecificHeat);
    }

    /// 永続化クラスのインスタンスを生成して返す.
    ///
    /// @return 永続化クラスのインスタンス.
    private ColumnCellEntity2.Previous newEntity() {
      return new ColumnCellEntity2.Previous(
          this.getTemperature(),
          this.getDensity(),
          this.getSpecificEnthalpy(),
          this.getSpecificConstantPressureSpecificHeat(),
          this.getShellTemperature(),
          this.getShellSpecificHeat(),
          this.getAdsorbentSpecificHeat());
    }

    /// 永続化クラスのインスタンスを反映する.
    ///
    /// @param entity 永続化クラスのインスタンス.
    /// @throws IllegalArgumentException `entity` を構成する要素の値が範囲外の場合.
    private void accept(ColumnCellEntity2.Previous entity) {
      this.setTemperature(entity.temperature());
      this.setDensity(entity.density());
      this.setSpecificEnthalpy(entity.specificEnthalpy());
      this.setSpecificConstantPressureSpecificHeat(entity.specificConstantPressureSpecificHeat());
      this.setShellTemperature(entity.shellTemperature());
      this.setShellSpecificHeat(entity.shellSpecificHeat());
      this.setAdsorbentSpecificHeat(entity.adsorbentSpecificHeat());
    }

  } // Previous

  /// 吸着筒のセルの成分値.
  final class ComponentValue implements FluidCell2.ComponentValue, AdsorbentCell2.ComponentValue {

    /// 数値の位置.
    private enum Position {

      /// 流体成分の質量分率の位置.
      FRACTION,

      /// 流体成分の物質拡散係数の位置.
      DIFFUSIVITY,

      /// 流体成分のモル分率の位置.
      MOLAR_FRACTION,

      /// 流体成分の粘性係数の位置.
      VISCOSITY,

      /// 吸着質成分の吸着量の位置.
      MOLAR_QUANTITY,

      /// 吸着質成分のモル定積比熱の位置.
      MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT

    } // ComponentValue.Position

    /// 数値の位置のオフセット量.
    private final int offset;

    /// 吸着筒のセルの成分値の前ステップ値.
    private final Previous previous;

    /// 吸着筒のセルの成分値のインスタンスを生成する.
    ///
    /// @param offset 数値の位置のオフセット量.
    private ComponentValue(int offset) {
      this.offset = offset;
      this.previous = new Previous(this.offset + Previous.number());
    }

    /// 吸着筒のセルの成分値における数値の数を返す.
    ///
    /// @return 吸着筒のセルの成分値における数値の数.
    private static int number() {
      return Position.values().length + Previous.number();
    }

    /// 指定された数値を返す.
    ///
    /// @param position 数値の位置.
    /// @return 指定された数値.
    private double getValue(Position position) {
      return ColumnCell2.this.doubleArray()[this.offset + position.ordinal()];
    }

    /// 指定された数値を設定する.
    ///
    /// @param position 数値の位置.
    /// @param value 指定された新な数値.
    private void setValue(Position position, double value) {
      ColumnCell2.this.doubleArray()[this.offset + position.ordinal()] = value;
    }

    @Override
    public double getFraction() {
      return this.getValue(Position.FRACTION);
    }

    @Override
    public void setFraction(double fraction) {
      Preconditions.checkArgument(0. <= fraction && fraction <= 1.);
      this.setValue(Position.FRACTION, fraction);
    }

    @Override
    public double getDiffusivity() {
      return this.getValue(Position.DIFFUSIVITY);
    }

    @Override
    public void setDiffusivity(double diffusivity) {
      Preconditions.checkArgument(diffusivity > 0.);
      this.setValue(Position.DIFFUSIVITY, diffusivity);
    }

    @Override
    public double getMolarFraction() {
      return this.getValue(Position.MOLAR_FRACTION);
    }

    @Override
    public void setMolarFraction(double molarFraction) {
      this.setValue(Position.MOLAR_FRACTION, molarFraction);
    }

    @Override
    public double getViscosity() {
      return this.getValue(Position.VISCOSITY);
    }

    @Override
    public void setViscosity(double viscosity) {
      this.setValue(Position.VISCOSITY, viscosity);
    }

    @Override
    public double getMolarQuantity() {
      return this.getValue(Position.MOLAR_QUANTITY);
    }

    @Override
    public void setMolarQuantity(double molarQuantity) {
      Preconditions.checkArgument(molarQuantity > 0.);
      this.setValue(Position.MOLAR_QUANTITY, molarQuantity);
    }

    @Override
    public double getMolarSpecificConstantVolumeSpecificHeat() {
      return this.getValue(Position.MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT);
    }

    @Override
    public void setMolarSpecificConstantVolumeSpecificHeat(
        double molarSpecificConstantVolumeSpecificHeat) {
        this.setValue(
            Position.MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT,
            molarSpecificConstantVolumeSpecificHeat);
    }

    /// 永続化クラスのインスタンスを生成して返す.
    ///
    /// @return 永続化クラスのインスタンス.
    private ColumnCellEntity2.ComponentValue newEntity() {
      return new ColumnCellEntity2.ComponentValue(
          this.getFraction(),
          this.getDiffusivity(),
          this.getMolarQuantity(),
          this.getMolarSpecificConstantVolumeSpecificHeat(),
          this.previous.newEntity());
    }

    /// 永続化クラスのインスタンスを反映する.
    ///
    /// @param entity 永続化クラスのインスタンス.
    /// @throws IllegalArgumentException `entity` を構成する要素の値が範囲外の場合.
    private void accept(ColumnCellEntity2.ComponentValue entity) {
      this.setFraction(entity.fraction());
      this.setDiffusivity(entity.diffusivity());
      this.setMolarQuantity(entity.molarQuantity());
      this.setMolarSpecificConstantVolumeSpecificHeat(
          entity.molarSpecificConstantVolumeSpecificHeat());
      this.previous.accept(entity.previous());
    }

    @Override
    public Previous previous() {
      return this.previous;
    }

    /// 吸着筒のセルの成分値の前ステップ値.
    final class Previous
        implements FluidCell2.ComponentValue.Previous, AdsorbentCell2.ComponentValue.Previous {

      /// 数値の位置.
      private enum Position {

        /// 流体成分の質量分率の位置.
        FRACTION,

        /// 吸着質成分の吸着量の位置.
        MOLAR_QUANTITY,

        /// 吸着質成分のモル定積比熱の位置.
        MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT

      } // ComponentValue.Previous.Position

      /// 数値の位置のオフセット量.
      private final int offset;

      /// 吸着筒のセルの成分値の前ステップ値のインスタンスを生成する.
      ///
      /// @param offset 数値の位置のオフセット量.
      private Previous(int offset) {
        this.offset = offset;
      }

      /// 吸着筒のセルの成分値の前ステップ値における数値の数を返す.
      ///
      /// @return 吸着筒のセルの成分値の前ステップ値における数値の数.
      private static int number() {
        return Position.values().length;
      }

      /// 指定された数値を返す.
      ///
      /// @param position 数値の位置.
      /// @return 指定された数値.
      private double getValue(Position position) {
        return ColumnCell2.this.doubleArray()[this.offset + position.ordinal()];
      }

      /// 指定された数値を設定する.
      ///
      /// @param position 数値の位置.
      /// @param value 指定された新な数値.
      private void setValue(Position position, double value) {
        ColumnCell2.this.doubleArray()[this.offset + position.ordinal()] = value;
      }

      @Override
      public double getFraction() {
        return this.getValue(Position.FRACTION);
      }

      @Override
      public void setFraction(double fraction) {
        Preconditions.checkArgument(0 <= fraction && fraction <= 1.);
        this.setValue(Position.FRACTION, fraction);
      }

      @Override
      public double getMolarQuantity() {
        return this.getValue(Position.MOLAR_QUANTITY);
      }

      @Override
      public void setMolarQuantity(double molarQuantity) {
        Preconditions.checkArgument(molarQuantity > 0.);
        this.setValue(Position.MOLAR_QUANTITY, molarQuantity);
      }

      @Override
      public double getMolarSpecificConstantVolumeSpecificHeat() {
        return this.getValue(Position.MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT);
      }

      @Override
      public void setMolarSpecificConstantVolumeSpecificHeat(
          double molarSpecificConstantVolumeSpecificHeat) {
        this.setValue(
            Position.MOLAR_SPECIFIC_CONSTANT_VOLUME_SPECIFIC_HEAT,
            molarSpecificConstantVolumeSpecificHeat);
      }

      /// 永続化クラスのインスタンスを生成して返す.
      ///
      /// @return 永続化クラスのインスタンス.
      private ColumnCellEntity2.ComponentValue.Previous newEntity() {
        return new ColumnCellEntity2.ComponentValue.Previous(
            this.getFraction(),
            this.getMolarQuantity(),
            this.getMolarSpecificConstantVolumeSpecificHeat());
      }

      /// 永続化クラスのインスタンスを反映する.
      ///
      /// @param entity 永続化クラスのインスタンス.
      /// @throws IllegalArgumentException `entity` を構成する要素の値が範囲外の場合.
      private void accept(ColumnCellEntity2.ComponentValue.Previous entity) {
        this.setFraction(entity.fraction());
        this.setMolarQuantity(entity.molarQuantity());
        this.setMolarSpecificConstantVolumeSpecificHeat(
            entity.molarSpecificConstantVolumeSpecificHeat());
      }

    } // ComponentValue.Previous

  } // ComponentValue

}
