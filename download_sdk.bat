@echo off
echo ==========================================
echo Android SDK Command Line Tools Downloader
echo ==========================================
echo.

set SDK_DIR=C:\Android\Sdk
set CMDLINE_TOOLS_URL=https://dl.google.com/android/repository/commandlinetools-win-11076708_latest.zip
set ZIP_FILE=%TEMP%\cmdline-tools.zip

echo [1/5] Creating directory...
if not exist "%SDK_DIR%\cmdline-tools" mkdir "%SDK_DIR%\cmdline-tools"

echo [2/5] Downloading command line tools...
powershell -Command "Invoke-WebRequest -Uri '%CMDLINE_TOOLS_URL%' -OutFile '%ZIP_FILE%'"

echo [3/5] Extracting...
powershell -Command "Expand-Archive -Path '%ZIP_FILE%' -DestinationPath '%SDK_DIR%\cmdline-tools' -Force"

echo [4/5] Organizing directory structure...
if exist "%SDK_DIR%\cmdline-tools\cmdline-tools" (
    xcopy /E /I /Y "%SDK_DIR%\cmdline-tools\cmdline-tools\*" "%SDK_DIR%\cmdline-tools\latest\" >nul 2>&1
    rmdir /S /Q "%SDK_DIR%\cmdline-tools\cmdline-tools"
)

echo [5/5] Cleaning up temporary files...
del /F /Q "%ZIP_FILE%" 2>nul

echo.
echo ==========================================
echo Download Complete!
echo SDK Path: %SDK_DIR%
echo ==========================================
echo.
echo Next: Run setup_android_sdk.bat to install components
pause
