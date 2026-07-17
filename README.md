# 码字计时器 · 摸鱼休息提醒（Typing & Time Counter）

> 一个蹲在 IDE 里陪你肝代码的小工具：码字计数 + 时间计时 + 固定时间幽默休息提醒 + 强打字动效。
> 新人开发者的第一个小作品（v0.5，2026-07-17），后续会持续打磨更新，欢迎试用与反馈～ 🌱

<!-- ==================== 项目状态徽章 ==================== -->
![Version](https://img.shields.io/badge/version-v0.5-blue?style=flat-square)![License](https://img.shields.io/badge/license-MIT-yellow?style=flat-square&logo=opensourceinitiative)![Platform](https://img.shields.io/badge/platform-IntelliJ-green?style=flat-square&logo=intellijidea)![Java](https://img.shields.io/badge/java-11%20target-orange?style=flat-square&logo=openjdk)![Gradle](https://img.shields.io/badge/gradle-8.12-blueviolet?style=flat-square&logo=gradle)![IDEA](https://img.shields.io/badge/IDEA-2021.3--2026.2-informational?style=flat-square)![PyCharm](https://img.shields.io/badge/PyCharm-2021.3--2026.2-informational?style=flat-square)![Code size](https://img.shields.io/github/languages/code-size/PiggyCoderHub/typing-time-counter?style=flat-square)![Repo size](https://img.shields.io/github/repo-size/PiggyCoderHub/typing-time-counter?style=flat-square)![Last commit](https://img.shields.io/github/last-commit/PiggyCoderHub/typing-time-counter?style=flat-square)![Stars](https://img.shields.io/github/stars/PiggyCoderHub/typing-time-counter?style=flat-square)![Issues](https://img.shields.io/github/issues/PiggyCoderHub/typing-time-counter?style=flat-square)

<!-- ==================== 多语言入口 ==================== -->
[![🇨🇳 中文](https://img.shields.io/badge/🇨🇳-中文-blue?style=flat-square)](#)[![🇺🇸 English](https://img.shields.io/badge/🇺🇸-English-blue?style=flat-square)](#english-version)
<!-- ==================== 中文版 ==================== -->

支持 **PyCharm** 与 **IntelliJ IDEA**（以及所有 IntelliJ 系 IDE），兼容 2021.3 ~ 2026.2。

## ✨ 功能亮点

[![Typing Counter](https://img.shields.io/badge/-码字计数-blue?style=for-the-badge)](#)[![Break Reminder](https://img.shields.io/badge/-休息提醒-red?style=for-the-badge)](#)[![Typing Effects](https://img.shields.io/badge/-强打字动效-purple?style=for-the-badge)](#)[![Fun Messages](https://img.shields.io/badge/-全程不枯燥-yellowgreen?style=for-the-badge)](#)

- 📊 **码字计数**：实时统计今日 / 累计码了多少字、肝了多久、手速（CPM）。
- ⏰ **幽默休息提醒**：码满设定时长，弹出沙雕气泡催你起来走走，拯救颈椎和屁股；可「已休息」打卡，也能「再战一会」贪睡几分钟。
- 🎆 **强打字动效**：专属「码字舞台」把每次按键变成上飘光点、流动弹幕与炸裂大字——打工也能打出仪式感，键盘敲出蹦迪味。
- 🤫 **全程不枯燥**：状态栏和提醒文案全是梗，陪你笑着重构人生（和代码）。
- 💾 **纯本地**：数据只存在你本地 IDE 配置里，绝不上传，社恐友好。

## 📦 安装

[![Disk Install](https://img.shields.io/badge/-从磁盘安装-informational?style=flat-square)](#方式一从磁盘安装自己构建)[![Release](https://img.shields.io/badge/-Release_下载-brightgreen?style=flat-square)](https://github.com/PiggyCoderHub/typing-time-counter/releases)

### 方式一：从磁盘安装（自己构建）
1. 用 PyCharm / IDEA 打开本仓库的 `Pycharm/` 或 `IDEA/` 文件夹，作为 Gradle 项目导入。
2. Gradle 工具窗 → `Tasks → intellij → buildPlugin`，产物在 `build/distributions/*.zip`。
3. `Settings → Plugins → ⚙️ → Install Plugin from Disk…` 选该 zip，重启。

### 方式二：从 GitHub Release 下载
到本仓库的 **Releases** 页面下载 zip，按方式一第 3 步安装。

## 🛠 本地构建

```bash
# IDEA 版
cd IDEA && ./gradlew buildPlugin
# PyCharm 版
cd Pycharm && ./gradlew buildPlugin
```

> 需要 JDK 21 来跑 Gradle（编译目标为 Java 11），首次会下载对应 IDE 的 SDK。

## 📁 仓库结构

| 路径 | 说明 |
| --- | --- |
| `Pycharm/` | PyCharm 版插件工程（id: `com.coder.typing-time-counter`） |
| `IDEA/` | IntelliJ IDEA 版插件工程（id: `com.coder.typing-time-counter-idea`） |
| `docs/` | 用户使用说明书、需求文档 |
| `(仓库根)` | `README.md` / `LICENSE` / `.gitignore` / `CONTRIBUTING.md` / `CHANGELOG.md` / `.github/` |

## 🏷 资源直达

[![文档](https://img.shields.io/badge/-用户使用说明书-blue?style=flat-square)](docs/用户使用说明书.md)[![需求](https://img.shields.io/badge/-需求文档-orange?style=flat-square)](docs/需求文档.md)[![贡献指南](https://img.shields.io/badge/-贡献指南-green?style=flat-square)](CONTRIBUTING.md)[![更新日志](https://img.shields.io/badge/-更新日志-lightgrey?style=flat-square)](CHANGELOG.md)[![上传GitHub](https://img.shields.io/badge/-上传GitHub-red?style=flat-square)](上传说明.md)

---

## English Version

> A tiny sidekick that sits in your IDE and counts every keystroke while reminding you to take fun breaks.

Supports **PyCharm**, **IntelliJ IDEA**, and all IntelliJ-based IDEs. Compatible with builds 213 ~ 263.* (2021.3 – 2026.2).

## ✨ Features

[![Typing Counter](https://img.shields.io/badge/-Typing_Counter-blue?style=for-the-badge)](#)[![Break Reminder](https://img.shields.io/badge/-Break_Reminder-red?style=for-the-badge)](#)[![Punchy Effects](https://img.shields.io/badge/-Typing_Effects-purple?style=for-the-badge)](#)[![Never Boring](https://img.shields.io/badge/-Never_Boring-yellowgreen?style=for-the-badge)](#)

- 📊 **Typing counter**: today's / total characters, active time, and speed (CPM).
- ⏰ **Funny break reminder**: cheeky bubble nudges you to stand up after grinding too long.
- 🎆 **Punchy typing effects**: a "Typing Stage" turns keystrokes into sparks, danmaku, and bursting text.
- 🤫 **Never boring**: status bar and reminders full of jokes and memes.
- 💾 **100% local**: all data stays in your IDE config, never uploaded.

## 📄 Open Source
[MIT License](LICENSE) · Copyright © 2026 琼台师范学院万象大模型研究社 Piggy

## 👤 Author
琼台师范学院万象大模型研究社 Piggy · v0.5 · 2026-07-17

