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
    implementation(platform(libs.log4j.bom))
    implementation(libs.log4j.api)
    implementation(libs.log4j.core)
}

application {
    mainClass = "com.nipponsanso.jp.digital.adsorption.Main"
}

tasks.javadoc {
    options.overview = "overview.html"
}
