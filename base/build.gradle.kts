/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

plugins {
    id("buildlogic.java-library-conventions")
}

group = "com.nipponsanso.jp.digital"
version = "1.1.0-SNAPSHOT"

dependencies {
    testImplementation(platform(libs.assertj.bom))
    testImplementation(libs.assertj.core)
    implementation(platform(libs.guava.bom))
    implementation(libs.guava)
    testImplementation(libs.guava.testlib)
    testImplementation(platform((libs.junit.bom)))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.javadoc {
    options.overview = "overview.html"
}
