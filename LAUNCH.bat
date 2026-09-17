@echo off
title Bank Account Management System - BAMS
color 0A

echo.
echo  ================================================================================
echo            BANK ACCOUNT MANAGEMENT SYSTEM  --  BAMS v1.0
echo            Java Project ^| 2nd Year ^| Programming in Java
echo  ================================================================================
echo.

:: Step 1: Compile using bundled ecj.jar (no need for JDK to be installed)
echo  [*] Compiling project...
java -jar lib\ecj.jar -8 -cp "lib\sqlite-jdbc-3.45.1.0.jar;lib\slf4j-api-1.7.36.jar;lib\slf4j-simple-1.7.36.jar" -d bin src\com\bank\model\*.java src\com\bank\exception\*.java src\com\bank\dao\*.java src\com\bank\service\*.java src\com\bank\util\*.java src\com\bank\main\*.java > nul 2>&1

if %ERRORLEVEL% NEQ 0 (
    color 0C
    echo.
    echo  [ERROR] Compilation failed! Make sure Java is installed.
    echo  Check that your JRE is working by running: java -version
    echo.
    pause
    exit /b 1
)

echo  [OK] Compiled successfully!
echo  [*] Starting application...
echo.

:: Step 2: Run the app
java -cp "bin;lib\*" com.bank.main.BankApp

echo.
echo  Session ended. Press any key to close this window.
pause > nul
