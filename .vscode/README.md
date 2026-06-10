# VS Code Android 调试配置指南

## 📋 前提条件

### 1. 安装 Android SDK

如果您还没有安装 Android SDK，请通过以下方式之一安装：

#### 方案 A：通过 Android Studio 安装（推荐）
1. 下载并安装 [Android Studio](https://developer.android.com/studio)
2. 打开 Android Studio → SDK Manager
3. 记下 SDK 路径（通常在 `C:\Users\<用户名>\AppData\Local\Android\Sdk`）

#### 方案 B：通过命令行工具安装
1. 下载 [Android 命令行工具](https://developer.android.com/studio#command-tools)
2. 解压到 `C:\Android\Sdk`
3. 运行 `sdkmanager` 安装必要组件

### 2. 设置环境变量

在 Windows 中设置以下环境变量：

```
ANDROID_HOME = C:\Users\<您的用户名>\AppData\Local\Android\Sdk
```

并将其添加到 PATH：

```
%ANDROID_HOME%\platform-tools
%ANDROID_HOME%\tools
```

### 3. 安装 VS Code 扩展

打开 VS Code，安装以下扩展（按 Ctrl+Shift+X 搜索）：

| 扩展名 | 说明 |
|--------|------|
| `vscjava.vscode-java-pack` | Java 扩展包 |
| `fwcd.kotlin` | Kotlin 语言支持 |
| `adelphes.android-dev-ext` | Android 开发扩展 |
| `vscjava.vscode-gradle` | Gradle 支持 |
| `ms-vscode.vscode-android-webview-debug` | Android WebView 调试 |

## 🚀 配置步骤

### 步骤 1：配置 Android SDK 路径

编辑 `.vscode/settings.json`，添加您的 Android SDK 路径：

```json
{
    "android.sdkPath": "C:\\Users\\<您的用户名>\\AppData\\Local\\Android\\Sdk"
}
```

### 步骤 2：连接 Android 设备

#### 使用实体设备：
1. 启用开发者选项（设置 → 关于手机 → 连续点击版本号 7 次）
2. 启用 USB 调试（开发者选项 → USB 调试）
3. 通过 USB 连接电脑
4. 在终端运行：`adb devices` 确认设备已连接

#### 使用模拟器：
1. 打开 Android Studio → Device Manager
2. 创建虚拟设备（推荐 Pixel 系列）
3. 启动模拟器

### 步骤 3：启动调试

1. 按 `F5` 或点击左侧调试图标
2. 选择 "Android App (Debug)" 配置
3. 选择设备
4. 开始调试！

## 🐛 常见问题

### 问题 1：找不到 Android SDK

**解决方案：**
```bash
# 检查 SDK 路径
echo %ANDROID_HOME%

# 如果未设置，手动设置
setx ANDROID_HOME "C:\Users\<用户名>\AppData\Local\Android\Sdk"
```

### 问题 2：adb 命令不可用

**解决方案：**
```bash
# 检查 adb 是否安装
adb --version

# 如果未找到，添加 platform-tools 到 PATH
# 路径：%ANDROID_HOME%\platform-tools
```

### 问题 3：Gradle 构建失败

**解决方案：**
```bash
# 清理并重新构建
./gradlew clean
./gradlew build

# 或者使用 VS Code 任务
# Ctrl+Shift+P → Tasks: Run Task → Build Android Debug APK
```

### 问题 4：调试器无法附加

**解决方案：**
1. 确保应用已安装到设备
2. 检查包名是否正确（`kittoku.mvc`）
3. 尝试先手动启动应用，然后使用 "Attach" 配置

## 📱 调试快捷键

| 快捷键 | 功能 |
|--------|------|
| `F5` | 启动调试 |
| `Ctrl+F5` | 启动（不调试） |
| `Shift+F5` | 停止调试 |
| `F9` | 切换断点 |
| `F10` | 单步跳过 |
| `F11` | 单步进入 |
| `Shift+F11` | 单步跳出 |

## 🔍 调试技巧

### 1. 查看 Logcat 日志

在 VS Code 终端中运行：
```bash
adb logcat -s kittoku.mvc:D
```

### 2. 使用条件断点

在代码行号处右键 → "Add Conditional Breakpoint"，输入条件表达式：

```kotlin
// 例如，只在特定条件下中断
bridge.status == Status.CONNECTED
```

### 3. 监视表达式

在调试面板中添加监视表达式：
- `bridge.status`
- `clientPassword`
- `controller.job`

### 4. 网络调试

如果需要调试网络请求，可以使用：
```bash
# 查看网络连接
adb shell netstat

# 抓包（需要 root）
adb shell tcpdump -i any -w /sdcard/capture.pcap
```

## 📚 参考文档

- [VS Code Android 调试](https://code.visualstudio.com/docs/java/java-android)
- [Android 开发者文档](https://developer.android.com/studio/debug)
- [Kotlin 调试指南](https://kotlinlang.org/docs/debugging.html)

## 🆘 获取帮助

如果遇到问题：

1. 检查 VS Code 输出面板（View → Output → Android）
2. 查看 Gradle 构建日志
3. 运行 `adb logcat` 查看设备日志
4. 在终端运行 `./gradlew --info` 获取详细构建信息
