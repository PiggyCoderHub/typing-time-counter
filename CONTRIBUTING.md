# 贡献指南（Contributing）

欢迎一起来养大这个小作品 🌱

## 本地跑起来
1. 用 PyCharm / IDEA 打开本仓库的 `Pycharm/` 或 `IDEA/` 文件夹（识别为 Gradle 项目）。
2. 刷新 Gradle，跑 `buildPlugin`，产物在 `build/distributions/*.zip`。
3. `Settings → Plugins → ⚙️ → Install Plugin from Disk…` 装这个 zip，重启即可看效果。

## 想提 PR？
- Fork 本仓库，从新分支提交（`feat/xxx` 或 `fix/xxx`）。
- 改动后请本地 `buildPlugin` 确认能编过。
- 提交信息尽量说清「做了什么 / 为什么」。
- 新人友好：任何小改进都欢迎，别怕写得不完美 😄

## 代码约定
- 包名统一 `com.coder.typingtime`。
- 中文注释随意，请保持可读。
- PyCharm 版与 IDEA 版功能需保持一致，改一处记得同步另一边。
