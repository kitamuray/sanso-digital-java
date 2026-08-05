/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

package com.nipponsanso.jp.digital.adsorption;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Scriptable;

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

        if (args.length != 1) {
            System.err.println("usage: program scriptFile");
            System.exit(1);
        }

        try {
            try (Context cx = Context.enter()) {
                Scriptable scope = new ImporterTopLevel(cx);
                FileReader reader = new FileReader(args[0]);
                cx.evaluateReader(scope, reader, args[0], 1, null);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
