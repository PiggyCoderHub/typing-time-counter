plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.17.3"
}

group = "com.coder"
version = "0.5"

repositories {
    mavenCentral()
}

intellij {
    version.set("2023.2.5")
    type.set("IC")   // IDEA Community；PyCharm 版改成 "PC"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

// 强制编译器用 UTF-8 读取源码（Windows 默认 GBK，中文注释会乱码报错）
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks {
    buildSearchableOptions {
        enabled = false
    }
    patchPluginXml {
        sinceBuild.set("213")      // 兼容起点：2021.3（覆盖 2022.2+ 用户，JBR 11/17 均可运行）
        untilBuild.set("263.*")    // 兼容终点：2026.2 及附近（当前最新稳定版 2026.2 / build 262）
    }
}
