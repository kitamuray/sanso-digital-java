/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

plugins {
    id("buildlogic.java-application-conventions")
}

dependencies {
    implementation(project(":base"))
}

application {
    mainClass = "com.nipponsanso.jp.digital.adsorption.Main"
}
