@echo off
cd /d "%~dp0\frontend"

echo ==========================================
echo   memoloop frontend  -  http://localhost:4200
echo   (Ctrl+C to stop)
echo ==========================================
echo.

call npm start
