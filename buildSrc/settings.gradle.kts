/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

dependencyResolutionManagement {
    versionCatalogs {
        create("libs", { from(files("../gradle/libs.versions.toml")) })
    }
}

rootProject.name = "buildSrc"
