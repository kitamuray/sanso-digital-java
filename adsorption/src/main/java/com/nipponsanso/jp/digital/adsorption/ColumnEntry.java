/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

/// 吸着筒エントリ.
///
/// @param start 開始条件.
/// @param nextKey 次サイクルにおけるキー.
record ColumnEntry(ColumnStart start, ColumnKey nextKey) {

}
