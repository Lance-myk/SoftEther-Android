@echo off
echo ==========================================
echo Setting Android Environment Variables
echo ==========================================
echo.

set ANDROID_HOME=C:\Users\Administrator\AppData\Local\Android\Sdk
set ANDROID_SDK_ROOT=%ANDROID_HOME%
set JAVA_HOME=C:\Program Files\Android\Android Studio\jbr

:: Add to PATH
set PATH=%JAVA_HOME%\bin;%ANDROID_HOME%\platform-tools;%ANDROID_HOME%\cmdline-tools\latest\bin;%PATH%

echo Environment variables set:
echo   ANDROID_HOME=%ANDROID_HOME%
echo   ANDROID_SDK_ROOT=%ANDROID_SDK_ROOT%
echo   JAVA_HOME=%JAVA_HOME%
echo.

:: Verify
echo Verifying installation...
echo.

echo Java version:
java -version 2^>nul
if errorlevel 1 (
    echo   Java not found in PATH
) else (
    echo   OK
)

echo.
echo ADB version:
adb --version 2^>nul
if errorlevel 1 (
    echo   ADB not found in PATH
) else (
    echo   OK
)

echo.
echo ==========================================
echo Done! You can now use Android tools.
echo ==========================================
echo.
pause
