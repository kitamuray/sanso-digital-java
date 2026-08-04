/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

///
/// 吸着プロセスの解析実行.
///
/// @since 1.0
/// @author KITAMURA Yuichiro
///
public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    private Main() {}

    ///
    /// 吸着プロセスの解析を実行する.
    ///
    /// @param args コマンドライン引数.
    ///
    static void main(String[] args) {
        LOGGER.info(
            "java.version, vendor, home = {}, {}, {}",
            System.getProperty("java.version"),
            System.getProperty("java.vendor"),
            System.getProperty("java.home"));
        LOGGER.info(
            "os.name, arch, version = {}, {}, {}",
            System.getProperty("os.name"),
            System.getProperty("os.arch"),
            System.getProperty("os.version"));
        LOGGER.info(
            "user.dir = {}",
            System.getProperty("user.dir"));
        LOGGER.info("args = {}", Arrays.asList(args));
    }

}
