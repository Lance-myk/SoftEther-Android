@echo off
echo Setting up Android environment variables...

setx ANDROID_HOME "C:\Users\Administrator\AppData\Local\Android\Sdk" /M
setx ANDROID_SDK_ROOT "C:\Users\Administrator\AppData\Local\Android\Sdk" /M
setx JAVA_HOME "C:\Program Files\Android\Android Studio\jbr" /M

setx PATH "%PATH%;C:\Users\Administrator\AppData\Local\Android\Sdk\platform-tools;C:\Program Files\Android\Android Studio\jbr\bin" /M

echo Environment variables set successfully!
echo Please restart VS Code to apply changes.
pause
