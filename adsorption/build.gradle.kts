/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

plugins {
    id("buildlogic.java-application-conventions")
}

group = "com.nipponsanso.jp.digital"
version = "1.1.0-SNAPSHOT"

dependencies {
    implementation(project(":base"))
    testImplementation(platform(libs.assertj.bom))
    testImplementation(libs.assertj.core)
    implementation(platform(libs.guava.bom))
    implementation(libs.guava)
    testImplementation(libs.guava.testlib)
    testImplementation(platform((libs.junit.bom)))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
    implementation(platform(libs.log4j.bom))
    implementation(libs.log4j.api)
    implementation(libs.log4j.core)
    testImplementation(platform(libs.mockito.bom))
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.junit.jupiter)
    implementation(libs.rhino)
}

application {
    mainClass = "com.nipponsanso.jp.digital.adsorption.Main"
}

tasks.javadoc {
    options.overview = "overview.html"
}
