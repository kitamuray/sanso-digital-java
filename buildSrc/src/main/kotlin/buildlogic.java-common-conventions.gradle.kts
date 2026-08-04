/*
 * Copyright (c)  2025, 2026  TAIYO NIPPON SANSO CORPORATION
 * Copyright (c)  2026        Nippon Sanso Corporation
 */

plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    constraints {
        implementation("org.apache.commons:commons-text:1.14.0")
    }

    testImplementation("org.junit.jupiter:junit-jupiter:6.0.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.javadoc {
    options.encoding = "UTF-8"

    // MathJax ... LaTeX 形式で数式を記述
    // Mermaid ... Mermaid 記法で図を記述
    // Prism   ... プログラムコードのシンタックス・ハイライト
    options.header = "<script type=\"text/javascript\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js\"></script>" +
            "<script type=\"module\">import mermaid from 'https://cdn.jsdelivr.net/npm/mermaid@11/dist/mermaid.esm.min.mjs'; mermaid.initialize({ startOnLoad: true });</script>" +
            "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/prismjs@1/themes/prism.min.css\" />" +
            "<script src=\"https://cdn.jsdelivr.net/npm/prismjs@1/components/prism-core.min.js\"></script>" +
            "<script src=\"https://cdn.jsdelivr.net/npm/prismjs@1/plugins/autoloader/prism-autoloader.min.js\"></script>"

    (options as StandardJavadocDocletOptions).charSet = "UTF-8"
    (options as StandardJavadocDocletOptions).links("https://docs.oracle.com/javase/jp/25/docs/api/")
    (options as StandardJavadocDocletOptions).addBooleanOption("-allow-script-in-comments", true)
}
