# PyCharm 版 · 码字计时器 · 摸鱼休息提醒

本插件 = 码字计数 + 时间计数 + 固定时间休息提醒 + 强打字动效 + 幽默文案，**功能与 IDEA 版完全一致**。
编译目标：`PyCharm Community 2021.3+`（`build.gradle.kts` 里 `type.set("PC")`）。

## 构建 & 安装（本地从磁盘装）
1. 用 PyCharm 打开本文件夹，作为 Gradle 项目导入（会自动下载 PyCharm SDK）。
2. Gradle 工具窗 → `Tasks → intellij → buildPlugin`，产物在 `build/distributions/*.zip`。
3. `Settings → Plugins → ⚙️ → Install Plugin from Disk…` 选该 zip，重启。
4. 右侧工具窗「码字计时器」即可看到数字、动效与休息倒计时。

设置项：`Settings → 码字计时 & 休息提醒`（休息间隔 / 贪睡 / 每日目标 / 开关）。
完整说明见根目录 `README.md`。

> 构建时若报 Gradle/JDK 相关错误：Gradle 已配 8.12；**推荐 Gradle JVM 指向 JDK 11 或更高**（本机有 JDK 21 也完全可用——JDK 21 可产出 Java 11 字节码）。首次构建会自动下载对应 IDE 的 SDK。
