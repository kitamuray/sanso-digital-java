/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/// [ColumnModel] の永続化クラス.
///
/// @param cellEntityList セルの永続化オブジェクトのリスト.
/// @since 1.1
/// @author KITAMURA Yuichiro
public record ColumnModelEntity2(
    @JsonProperty("cells") List<ColumnCellEntity2> cellEntityList) {
}
