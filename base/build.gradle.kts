/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

plugins {
    id("buildlogic.java-library-conventions")
}

group = "com.nipponsanso.jp.digital"
version = "1.1.0-SNAPSHOT"

tasks.javadoc {
    options.overview = "overview.html"
}
