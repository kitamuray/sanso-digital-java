/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

/// 基本 PSA の初期化サービス.
///
/// @since 1.1
/// @author KITAMURA Yuichiro
final class InitializeBasicPsaService extends BasicPsaService {

  /// 基本 PSA の初期化サービスのインスタンスを生成する.
  private InitializeBasicPsaService() {}

  /// 唯一のインスタンス.
  private static final InitializeBasicPsaService INSTANCE = new InitializeBasicPsaService();

  /// 基本 PSA の初期化サービスのインスタンスを返す.
  ///
  /// @return 基本 PSA の初期化サービスのインスタンス.
  static InitializeBasicPsaService getInstance() {
    return INSTANCE;
  }

  @Override
  void initialize(BasicPsaProcess process, BasicPsaModel model) {
    super.initialize(process, model);
  }

  private void initializeProductTank(BasicPsaProcess process, BasicPsaModel model) {
    process.productTankStart().start(model.productTankModel().cell());
    this.initialize(model.fluidModel(), model.productTankModel().cell());
  }

  /// 流体セルを初期化する.
  ///
  /// @param model 流体モデル.
  /// @param cell 流体セル.
  private void initialize(FluidModel model, FluidCell cell) {
    // 熱力学的温度, 絶対圧力, 副成分の質量分率が設定されている

    model.update(cell);

    // 主成分の質量分率, 平均分子量, 全成分のモル分率が設定されている

    cell.setDensity(model.density(cell));
    cell.setSpecificConstantPressureSpecificHeat(model.specificConstantPressureSpecificHeat(cell));
    cell.setSpecificEnthalpy(model.specificEnthalpy(cell));
    cell.setViscosity(model.viscosity(cell));
    cell.setThermalConductivity(model.thermalConductivity(cell));
    cell.componentMap().forEach(
        (componentKey, componentValue) -> componentValue.setDiffusivity(
            model.diffusivity(cell, componentKey)));

    cell.previous().setTemperature(cell.getTemperature());
    cell.previous().setDensity(cell.getDensity());
    cell.previous().setSpecificConstantPressureSpecificHeat(
        cell.getSpecificConstantPressureSpecificHeat());
    cell.previous().setSpecificEnthalpy(cell.getSpecificEnthalpy());
    cell.componentMap().values().forEach(
        componentValue -> componentValue.previous().setFraction(componentValue.getFraction()));
  }

  /// 容器セルを初期化する.
  ///
  /// @param model 容器モデル.
  /// @param cell 容器セル.
  private void initialize(ShellModel model, ShellCell cell) {
    cell.setShellSpecificHeat(model.shellSpecificHeat(cell));
    cell.setShellThermalConductivity(model.shellThermalConductivity(cell));

    cell.previous().setShellTemperature(cell.getShellTemperature());
    cell.previous().setShellSpecificHeat(cell.getShellSpecificHeat());
  }

  /// 吸着剤セルを初期化する.
  ///
  /// @param model 吸着剤モデル.
  /// @param cell 吸着剤セル.
  private void initialize(AdsorbentModel model, AdsorbentCell cell) {
    cell.setAdsorbentSpecificHeat(model.adsorbentSpecificHeat(cell));
    cell.componentMap().forEach(
        (componentKey, componentValue) -> componentValue.setMolarSpecificConstantVolumeSpecificHeat(
            model.molarSpecificConstantVolumeSpecificHeat(cell, componentKey)));

    cell.previous().setAdsorbentSpecificHeat(cell.getAdsorbentSpecificHeat());
    cell.componentMap().values().forEach(
        componentValue -> componentValue.previous().setMolarSpecificConstantVolumeSpecificHeat(
            componentValue.getMolarSpecificConstantVolumeSpecificHeat()));
  }

}
