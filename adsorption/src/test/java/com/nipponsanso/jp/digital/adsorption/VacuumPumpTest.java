/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.google.common.base.MoreObjects;
import com.google.common.testing.EqualsTester;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/// [VacuumPump] のテスト.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
@ExtendWith(MockitoExtension.class)
class VacuumPumpTest {

  /// [VacuumPump.Builder] をテスト.
  @Test
  void testBuilder() {
    assertThatThrownBy(() -> VacuumPump.builder().maxVolumeFlowRate(0.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> VacuumPump.builder().minPressure(0.))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> VacuumPump.builder().fileName(null))
        .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> VacuumPump.builder().fileName("test").monitor(null))
        .isInstanceOf(NullPointerException.class);

    assertThatThrownBy(
        () -> VacuumPump.builder()
            .minPressure(1e-3)
            .build()) // maxVolumeFlowRate が未指定
        .isInstanceOf(IllegalStateException.class);
    assertThatThrownBy(
        () -> VacuumPump.builder()
            .maxVolumeFlowRate(1.)
            .build()) // minPressure が未指定
        .isInstanceOf(IllegalStateException.class);
  }

  @Mock
  private Monitor monitor;

  /// [VacuumPump] をテスト.
  @Test
  void test() {
    final double maxVolumeFlowRate = 1.;
    final double minPressure = 1e-3;
    final String fileName = "test1";

    assertThat(
        VacuumPump.builder()
            .maxVolumeFlowRate(maxVolumeFlowRate)
            .minPressure(minPressure)
            .fileName(fileName).monitor(this.monitor)
            .build())
        .returns(maxVolumeFlowRate, VacuumPump::maxVolumeFlowRate)
        .returns(minPressure, VacuumPump::minPressure)
        .returns(
            Map.of(fileName, this.monitor),
            VacuumPump::monitorMap)
        .hasToString(
            MoreObjects.toStringHelper(VacuumPump.class)
                .add("maxVolumeFlowRate", maxVolumeFlowRate)
                .add("minPressure", minPressure)
                .add("monitorMap", Map.of(fileName, this.monitor))
                .toString());

    EqualsTester tester = new EqualsTester();

    Stream.<Supplier<VacuumPump>>of(
            () -> VacuumPump.builder()
                .maxVolumeFlowRate(1.)
                .minPressure(1e-3)
                .build(),
            () -> VacuumPump.builder()
                .maxVolumeFlowRate(2.)
                .minPressure(1e-3)
                .build(),
            () -> VacuumPump.builder()
                .maxVolumeFlowRate(1.)
                .minPressure(2e-3)
                .build(),
            () -> VacuumPump.builder()
                .maxVolumeFlowRate(1.)
                .minPressure(1e-3)
                .fileName("test").monitor(this.monitor)
                .build())
        .map(supplier -> Stream.generate(supplier).limit(3).toArray())
        .forEach(tester::addEqualityGroup);

    tester.testEquals();
  }

}
