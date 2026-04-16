param(
    [string]$Server = "117.72.102.95",
    [string]$User = "root",
    [string]$ServiceName = "fraslab-backend",
    [string]$BackendProjectDir = "D:\javaweb\backend",
    [string]$FrontendProjectDir = "D:\javaweb\frontend",
    [string]$RemoteBackendJar = "/opt/fraslab/backend/app.jar",
    [string]$RemoteFrontendDir = "/var/www/fraslab",
    [switch]$SkipBackendBuild,
    [switch]$SkipFrontendBuild
)

$ErrorActionPreference = "Stop"

function Step([string]$Message) {
    Write-Host ""
    Write-Host "==== $Message ====" -ForegroundColor Cyan
}

$backendJar = Join-Path $BackendProjectDir "target\fraslab-site-0.0.1-SNAPSHOT.jar"
$remote = "$User@$Server"
Step "1/6 Build backend"
if (-not $SkipBackendBuild) {
    Push-Location $BackendProjectDir
    try {
        mvn clean package -DskipTests
    }
    finally {
        Pop-Location
    }
}
else {
    Write-Host "Skip backend build (-SkipBackendBuild)"
}

if (-not (Test-Path $backendJar)) {
    throw "Backend JAR not found: $backendJar"
}

Step "2/6 Upload backend and restart service"
scp "$backendJar" "${remote}:$RemoteBackendJar"
ssh $remote "systemctl restart $ServiceName && systemctl status $ServiceName --no-pager"

Step "3/6 Build frontend"
if (-not $SkipFrontendBuild) {
    Push-Location $FrontendProjectDir
    try {
        npm.cmd run build
    }
    finally {
        Pop-Location
    }
}
else {
    Write-Host "Skip frontend build (-SkipFrontendBuild)"
}

$distPath = Join-Path $FrontendProjectDir "dist"
if (-not (Test-Path $distPath)) {
    throw "Frontend dist directory not found: $distPath"
}

Step "4/6 Clean remote frontend directory"
ssh $remote "rm -rf $RemoteFrontendDir/*"

Step "5/6 Upload frontend static files"
scp -r "$distPath\*" "${remote}:$RemoteFrontendDir/"

Step "6/6 Validate nginx and backend health"
ssh $remote "nginx -t && systemctl reload nginx && curl -s http://127.0.0.1:8080/actuator/health"

Write-Host ""
Write-Host "Deploy finished." -ForegroundColor Green
Write-Host "Site: http://$Server"
Write-Host "Admin: http://$Server/admin/login"
