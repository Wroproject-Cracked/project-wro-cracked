# Build script for Project Wro (No-Auth)
$ErrorActionPreference = "Stop"

Write-Host "[1/3] Compiling source patches in patch_src..." -ForegroundColor Cyan
javac --release 17 -cp ".;patch_out" -d patch_out `
    patch_src\net\wro\auth\AuthManager.java `
    patch_src\net\wro\auth\Hwid.java `
    patch_src\net\wro\utils\system\SystemUtils.java

Write-Host "[2/3] Updating classes in net/ folder..." -ForegroundColor Cyan
Copy-Item "patch_out\net\wro\auth\AuthManager.class"        "net\wro\auth\AuthManager.class"        -Force
Copy-Item "patch_out\net\wro\auth\AuthManager`$State.class" "net\wro\auth\AuthManager`$State.class" -Force
Copy-Item "patch_out\net\wro\auth\Hwid.class"               "net\wro\auth\Hwid.class"               -Force
Copy-Item "patch_out\net\wro\utils\system\SystemUtils.class" "net\wro\utils\system\SystemUtils.class" -Force

Write-Host "[3/3] Packaging Project-Wro-2.0.0-noauth.jar..." -ForegroundColor Cyan
$outJar = "Project-Wro-2.0.0-noauth.jar"
Remove-Item $outJar -ErrorAction SilentlyContinue

jar cf $outJar META-INF net dev assets fabric.mod.json wro.mixins.json wro.core.mixins.json wro.accesswidener LICENSE_Project-Wro

$sizeMb = [math]::Round((Get-Item $outJar).Length / 1MB, 2)
Write-Host "Build Complete! Output: $outJar ($sizeMb MB)" -ForegroundColor Green
