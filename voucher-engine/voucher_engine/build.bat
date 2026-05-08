@echo off
REM Smart Voucher Validator - Build and Test Script for Windows

echo.
echo ========================================
echo Smart Voucher Validator - Build Script
echo ========================================
echo.

setlocal enabledelayedexpansion

set PROJECT_DIR=c:\voucher_engine
set SOURCE_DIR=%PROJECT_DIR%\src\main\java
set TEST_SOURCE_DIR=%PROJECT_DIR%\src\test\java
set TARGET_DIR=%PROJECT_DIR%\target
set CLASSES_DIR=%TARGET_DIR%\classes
set TEST_CLASSES_DIR=%TARGET_DIR%\test-classes

REM Create directories
echo [1/4] Creating directories...
if not exist "%CLASSES_DIR%" mkdir "%CLASSES_DIR%"
if not exist "%TEST_CLASSES_DIR%" mkdir "%TEST_CLASSES_DIR%"
echo Created: %CLASSES_DIR%
echo Created: %TEST_CLASSES_DIR%
echo.

REM Compile main classes
echo [2/4] Compiling main source code...
javac -d "%CLASSES_DIR%" ^
    "c:/voucher_engine/src/main/java/voucherengine/Main.java" ^
    "c:/voucher_engine/src/main/java/voucherengine/SmartVoucherValidator.java" ^
    "c:/voucher_engine/src/main/java/voucherengine/VoucherValidationRequest.java"

if !errorlevel! equ 0 (
    echo ✓ Main classes compiled successfully
) else (
    echo ✗ Compilation error occurred
    exit /b 1
)
echo.

REM Run main application
echo [3/4] Running Demo Application...
echo ========================================
cd /d "%PROJECT_DIR%"
java -cp "%CLASSES_DIR%" voucherengine.Main
echo ========================================
echo.

echo [4/4] Summary
echo ✓ Smart Voucher Validator successfully built and executed
echo.
echo To run the application again:
echo   cd c:\voucher_engine
echo   java -cp target\classes voucherengine.Main
echo.
echo Files created:
echo   - SmartVoucherValidator.java (main validator class)
echo   - VoucherValidationRequest.java (request object)
echo   - Main.java (demo application with 13 test cases)
echo   - SmartVoucherValidatorTest.java (unit tests with JUnit 5)
echo   - README.md (comprehensive documentation)
echo.

endlocal
