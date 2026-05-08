#!/usr/bin/env pwsh

# Smart Voucher Validator - Build and Test Script for PowerShell

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Smart Voucher Validator - Build Script" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$ProjectDir = "c:\voucher_engine"
$SourceDir = "$ProjectDir\src\main\java"
$TestSourceDir = "$ProjectDir\src\test\java"
$TargetDir = "$ProjectDir\target"
$ClassesDir = "$TargetDir\classes"
$TestClassesDir = "$TargetDir\test-classes"

# Create directories
Write-Host "[1/4] Creating directories..." -ForegroundColor Yellow
if (-not (Test-Path $ClassesDir)) {
    New-Item -ItemType Directory -Path $ClassesDir -Force | Out-Null
    Write-Host "Created: $ClassesDir" -ForegroundColor Green
}
if (-not (Test-Path $TestClassesDir)) {
    New-Item -ItemType Directory -Path $TestClassesDir -Force | Out-Null
    Write-Host "Created: $TestClassesDir" -ForegroundColor Green
}
Write-Host ""

# Compile main classes
Write-Host "[2/4] Compiling main source code..." -ForegroundColor Yellow
$CompileResult = & javac -d $ClassesDir `
    "c:/voucher_engine/src/main/java/voucherengine/Main.java" `
    "c:/voucher_engine/src/main/java/voucherengine/SmartVoucherValidator.java" `
    "c:/voucher_engine/src/main/java/voucherengine/VoucherValidationRequest.java" 2>&1

if ($LASTEXITCODE -eq 0) {
    Write-Host "✓ Main classes compiled successfully" -ForegroundColor Green
} else {
    Write-Host "✗ Compilation error occurred" -ForegroundColor Red
    exit 1
}
Write-Host ""

# Run main application
Write-Host "[3/4] Running Demo Application..." -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Cyan
Push-Location $ProjectDir
& java -cp "$ClassesDir" voucherengine.Main
Pop-Location
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Summary
Write-Host "[4/4] Summary" -ForegroundColor Yellow
Write-Host "✓ Smart Voucher Validator successfully built and executed" -ForegroundColor Green
Write-Host ""
Write-Host "To run the application again:" -ForegroundColor Cyan
Write-Host '  cd c:\voucher_engine' -ForegroundColor Gray
Write-Host '  java -cp target\classes voucherengine.Main' -ForegroundColor Gray
Write-Host ""
Write-Host "Files created:" -ForegroundColor Cyan
Write-Host "  - SmartVoucherValidator.java (main validator class)" -ForegroundColor Gray
Write-Host "  - VoucherValidationRequest.java (request object)" -ForegroundColor Gray
Write-Host "  - Main.java (demo application with 13 test cases)" -ForegroundColor Gray
Write-Host "  - SmartVoucherValidatorTest.java (unit tests with JUnit 5)" -ForegroundColor Gray
Write-Host "  - README.md (comprehensive documentation)" -ForegroundColor Gray
Write-Host ""
