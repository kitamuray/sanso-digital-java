/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/// 基本 PSA モデルの永続化クラス.
///
/// @param columnModelEntityMap 吸着筒モデルの永続化オブジェクトのマップ.
/// @param productTankModelEntity 製品槽モデルの永続化オブジェクト.
public record BasicPsaModelEntity2(
    @JsonProperty("column") Map<ColumnKey, ColumnModelEntity2> columnModelEntityMap,
    @JsonProperty("productTank") TankModelEntity2 productTankModelEntity) {

}
