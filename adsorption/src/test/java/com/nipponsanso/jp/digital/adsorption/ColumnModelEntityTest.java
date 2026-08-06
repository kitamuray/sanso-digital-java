/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.google.common.base.MoreObjects;
import com.google.common.testing.EqualsTester;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

/// [ColumnModelEntity] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
class ColumnModelEntityTest {

  /// [ColumnModelEntity#getCellEntityList()] と [ColumnModelEntity#setCellEntityList(List)]
  /// のテスト.
  @Test
  void testCellEntityList() {
    ColumnModelEntity entity = new ColumnModelEntity();

    assertThat(entity.getCellEntityList()).isEmpty();

    assertThatThrownBy(() -> entity.setCellEntityList(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> entity.setCellEntityList(Collections.singletonList(null)))
        .isInstanceOf(NullPointerException.class);

    final int size = 10;
    entity.setCellEntityList(Stream.generate(ColumnCellEntity::new).limit(size).toList());
    assertThat(entity.getCellEntityList().size()).isEqualTo(size);
  }

  /// [ColumnModelEntity#equals(Object)] と [ColumnModelEntity#hashCode()] のテスト.
  @Test
  void testEquals() {
    EqualsTester tester = new EqualsTester();

    Stream.<Consumer<ColumnModelEntity>>of(
            _ -> {},
            entity -> entity.setCellEntityList(Collections.singletonList(new ColumnCellEntity())))
        .map(consumer -> Stream.generate(ColumnModelEntity::new)
            .limit(3)
            .peek(consumer)
            .toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

  /// [ColumnModelEntity#toString()] のテスト.
  @Test
  void testToString() {
    List<ColumnCellEntity> cellEntityList = Collections.singletonList(new ColumnCellEntity());
    ColumnModelEntity entity = new ColumnModelEntity();
    entity.setCellEntityList(cellEntityList);

    assertThat(entity)
        .hasToString(
            MoreObjects.toStringHelper(ColumnModelEntity.class)
                .add("cellEntityList", cellEntityList)
                .toString());
  }

}
