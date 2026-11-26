/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.fasterxml.jackson.annotation.JsonProperty;

/// 貯槽モデルの永続化クラス.
///
/// @param cellEntity セルの永続化オブジェクト.
public record TankModelEntity(@JsonProperty("cell") TankCellEntity cellEntity) {

}
