@echo off
cd /d "%~dp0"

echo Launching backend + frontend in separate windows...
start "memoloop backend"  cmd /k run-backend.bat
start "memoloop frontend" cmd /k run-frontend.bat

echo.
echo Once both windows say they're ready:
echo   Frontend:  http://localhost:4200
echo   Backend:   http://localhost:8080
echo.
echo Press any key to close this launcher window.
pause >nul
