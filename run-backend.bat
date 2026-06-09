@echo off
cd /d "%~dp0"
set "JAVA_HOME=C:\Program Files\Java\jdk-21"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo ==========================================
echo   memoloop backend  -  http://localhost:8080
echo   (Ctrl+C to stop)
echo ==========================================
echo.

call gradlew.bat bootRun
