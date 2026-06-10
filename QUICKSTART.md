# 🚀 快速开始 - VS Code Android 调试

## 📋 您需要做的步骤

### 步骤 1：下载并安装 Android SDK

1. 双击运行项目根目录下的 **`download_sdk.bat`**
   - 会自动下载 Android SDK 命令行工具
   - 下载完成后会显示 "下载完成！"

2. 双击运行 **`setup_android_sdk.bat`**
   - 安装 platform-tools、Android 35 平台、构建工具
   - 设置环境变量 `ANDROID_HOME`
   - 按提示操作即可

3. **重启 VS Code**（使环境变量生效）

### 步骤 2：安装 VS Code 扩展

按 `Ctrl+Shift+X` 打开扩展市场，搜索并安装：

```
vscjava.vscode-java-pack    ← Java 扩展包（包含调试器）
fwcd.kotlin                  ← Kotlin 语言支持
vscjava.vscode-gradle        ← Gradle 支持
```

### 步骤 3：连接设备

**使用实体手机：**
1. 手机设置 → 关于手机 → 连续点击"版本号"7次 → 启用开发者选项
2. 开发者选项 → 开启"USB 调试"
3. 用 USB 线连接电脑
4. 手机上允许调试

**或使用模拟器：**
```bash
# 在命令行创建模拟器
C:\Android\Sdk\cmdline-tools\latest\bin\avdmanager.bat create avd -n test -k "system-images;android-35;google_apis;x86_64"

# 启动模拟器
C:\Android\Sdk\emulator\emulator.exe -avd test
```

### 步骤 4：开始调试

1. 按 `F5` 或点击左侧调试图标
2. 选择 **"Android App (Debug)"**
3. 选择您的设备
4. 开始调试！

---

## 🐛 常见问题

### 问题 1：adb 命令找不到

**解决：**
```bash
# 检查环境变量
echo %ANDROID_HOME%

# 应该输出: C:\Android\Sdk
# 如果没有，手动添加环境变量
```

### 问题 2：Gradle 构建失败

**解决：**
```bash
# 在项目目录下运行
gradlew.bat clean
gradlew.bat assembleDebug
```

### 问题 3：设备无法识别

**解决：**
```bash
# 检查设备是否连接
C:\Android\Sdk\platform-tools\adb.exe devices

# 如果列表为空，尝试
adb.exe kill-server
adb.exe start-server
```

### 问题 4：Java 版本不匹配

**解决：**
- 确保安装了 Java 17 或更高版本
- 在 `.vscode/settings.json` 中设置 `java.home`

---

## 📱 调试快捷键

| 快捷键 | 功能 |
|--------|------|
| `F5` | 启动调试 |
| `Shift+F5` | 停止调试 |
| `F9` | 切换断点 |
| `F10` | 单步跳过 |
| `F11` | 单步进入 |
| `Ctrl+Shift+D` | 打开调试面板 |

---

## 🔍 查看日志

在 VS Code 终端中运行：
```bash
# 查看应用日志
C:\Android\Sdk\platform-tools\adb.exe logcat -s kittoku.mvc:D

# 查看所有日志
C:\Android\Sdk\platform-tools\adb.exe logcat
```

---

## ✅ 验证清单

- [ ] 运行了 `download_sdk.bat`
- [ ] 运行了 `setup_android_sdk.bat`
- [ ] 重启了 VS Code
- [ ] 安装了 3 个 VS Code 扩展
- [ ] 连接了 Android 设备
- [ ] 按 F5 可以启动调试

---

## 🆘 需要帮助？

如果遇到问题：

1. 查看 VS Code 输出面板：`Ctrl+Shift+U` → 选择 "Android"
2. 查看 Gradle 日志：终端运行 `gradlew.bat assembleDebug --info`
3. 查看设备日志：运行 `adb.exe logcat`

或者随时问我！
