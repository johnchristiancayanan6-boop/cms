# Quick Start Script for EastWest BPO - MCI (CMMS System)

function Test-DockerAvailable {
    try {
        docker info | Out-Null
        return $true
    }
    catch {
        return $false
    }
}

function Invoke-DbCompatibilityFixes {
    param(
        [string]$DbUser = "rootUser"
    )

    if ([string]::IsNullOrWhiteSpace($DbUser)) {
        $DbUser = "rootUser"
    }

    $dbRunning = docker ps --filter "name=^/eastwest_db$" --format "{{.Names}}"
    if (-not $dbRunning) {
        Write-Host "⚠️ Skipping DB compatibility fixes (eastwest_db is not running)." -ForegroundColor Yellow
        return
    }

    Write-Host "🔄 Applying database compatibility fixes..." -ForegroundColor Yellow

    $eastwestExists = (docker exec eastwest_db psql -U $DbUser -d postgres -tAc "SELECT 1 FROM pg_database WHERE datname='eastwest';" 2>$null).Trim()
    if (-not $eastwestExists) {
        $atlasExists = (docker exec eastwest_db psql -U $DbUser -d postgres -tAc "SELECT 1 FROM pg_database WHERE datname='atlas';" 2>$null).Trim()
        if ($atlasExists) {
            docker exec eastwest_db psql -U $DbUser -d postgres -c "CREATE DATABASE eastwest WITH TEMPLATE atlas;" | Out-Null
        }
        else {
            docker exec eastwest_db psql -U $DbUser -d postgres -c "CREATE DATABASE eastwest;" | Out-Null
        }
    }

    $quartzExists = (docker exec eastwest_db psql -U $DbUser -d eastwest -tAc "SELECT to_regclass('public.qrtz_job_details') IS NOT NULL;" 2>$null).Trim()
    if ($quartzExists -eq "t") {
        docker exec eastwest_db psql -U $DbUser -d eastwest -c "UPDATE qrtz_job_details SET job_class_name = REPLACE(job_class_name, 'com.grash.job.', 'com.eastwest.job.') WHERE job_class_name LIKE 'com.grash.job.%';" | Out-Null
    }

    $roleExists = (docker exec eastwest_db psql -U $DbUser -d eastwest -tAc "SELECT to_regclass('public.role') IS NOT NULL;" 2>$null).Trim()
    if ($roleExists -eq "t") {
        docker exec eastwest_db psql -U $DbUser -d eastwest -c "UPDATE role SET name = 'SuperAdministratorEastwest@p;l' WHERE role_type = 0;" | Out-Null
    }

    Write-Host "✅ Compatibility fixes applied." -ForegroundColor Green
}

Write-Host "🚀 EastWest BPO - MCI (CMMS System) - Quick Start" -ForegroundColor Cyan
Write-Host ""

if (-not (Test-DockerAvailable)) {
    Write-Host "❌ Docker is not available." -ForegroundColor Red
    Write-Host "Please start Docker Desktop (or Docker Engine) and try again." -ForegroundColor Yellow
    exit 1
}

Write-Host "Select mode:" -ForegroundColor Yellow
Write-Host "1. Production Mode (optimized, rebuild for code changes)"
Write-Host "2. Development Mode (live editing)"
Write-Host "3. Stop All Containers"
Write-Host "4. View Logs"
Write-Host ""

$choice = Read-Host "Enter choice (1-4)"

switch ($choice) {
    "1" {
        Write-Host "🏭 Starting in PRODUCTION mode..." -ForegroundColor Green
        docker-compose down
        docker-compose up -d
        Start-Sleep -Seconds 5
        Invoke-DbCompatibilityFixes -DbUser $env:POSTGRES_USER
        docker-compose restart api | Out-Null
        Write-Host ""
        Write-Host "✅ Production mode started!" -ForegroundColor Green
        Write-Host "🌐 Frontend: http://localhost:3000" -ForegroundColor Cyan
        Write-Host "🔧 Backend: http://localhost:8080" -ForegroundColor Cyan
        Write-Host ""
        Write-Host "Login: superadmin@test.com / pls_change_me" -ForegroundColor Yellow
    }
    "2" {
        Write-Host "🔧 Starting in DEVELOPMENT mode..." -ForegroundColor Green
        Write-Host "⏳ This will take a few minutes on first run..." -ForegroundColor Yellow
        docker-compose down
        docker-compose -f docker-compose.dev.yml up -d
        Start-Sleep -Seconds 5
        Invoke-DbCompatibilityFixes -DbUser $env:POSTGRES_USER
        docker-compose -f docker-compose.dev.yml restart api | Out-Null
        Write-Host ""
        Write-Host "✅ Development mode started!" -ForegroundColor Green
        Write-Host "🌐 Frontend: http://localhost:3000" -ForegroundColor Cyan
        Write-Host "🔧 Backend: http://localhost:8080" -ForegroundColor Cyan
        Write-Host ""
        Write-Host "📝 Edit files in frontend/src/ and see changes live." -ForegroundColor Yellow
        Write-Host "Login: superadmin@test.com / pls_change_me" -ForegroundColor Yellow
    }
    "3" {
        Write-Host "🛑 Stopping all containers..." -ForegroundColor Red
        docker-compose down
        docker-compose -f docker-compose.dev.yml down
        Write-Host "✅ All containers stopped!" -ForegroundColor Green
    }
    "4" {
        Write-Host "Select container logs:" -ForegroundColor Yellow
        Write-Host "1. Frontend (production)"
        Write-Host "2. Frontend (development)"
        Write-Host "3. Backend"
        Write-Host "4. Database"
        Write-Host "5. MinIO Storage"
        $logChoice = Read-Host "Enter choice (1-5)"

        switch ($logChoice) {
            "1" { docker logs -f eastwest-frontend }
            "2" { docker logs -f eastwest-frontend-dev }
            "3" { docker logs -f eastwest-backend }
            "4" { docker logs -f eastwest_db }
            "5" { docker logs -f eastwest_minio }
            default { Write-Host "❌ Invalid choice" -ForegroundColor Red }
        }
    }
    default {
        Write-Host "❌ Invalid choice!" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "📚 See CUSTOMIZATION-GUIDE.md for customization details." -ForegroundColor Cyan
