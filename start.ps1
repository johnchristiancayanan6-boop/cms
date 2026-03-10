# Quick Start Script for ZED CMMS PH

Write-Host "🚀 ZED CMMS PH - Quick Start" -ForegroundColor Cyan
Write-Host ""

# Check if Docker is running
if (-not (Get-Process -Name "Docker Desktop" -ErrorAction SilentlyContinue)) {
    Write-Host "❌ Docker Desktop is not running!" -ForegroundColor Red
    Write-Host "Please start Docker Desktop first." -ForegroundColor Yellow
    exit 1
}

Write-Host "Select mode:" -ForegroundColor Yellow
Write-Host "1. Production Mode (optimized, need rebuild for changes)"
Write-Host "2. Development Mode (live editing, see changes instantly)"
Write-Host "3. Stop All Containers"
Write-Host "4. View Logs"
Write-Host ""

$choice = Read-Host "Enter choice (1-4)"

switch ($choice) {
    "1" {
        Write-Host "🏭 Starting in PRODUCTION mode..." -ForegroundColor Green
        docker-compose down
        docker-compose up -d
        Write-Host ""
        Write-Host "✅ Production mode started!" -ForegroundColor Green
        Write-Host "🌐 Frontend: http://localhost:3000" -ForegroundColor Cyan
        Write-Host "🔧 Backend: http://localhost:8080" -ForegroundColor Cyan
        Write-Host ""
        Write-Host "Login: superadmin@test.com / pls_change_me" -ForegroundColor Yellow
    }
    "2" {
        Write-Host "🔧 Starting in DEVELOPMENT mode..." -ForegroundColor Green
        Write-Host "⏳ This will take a few minutes first time..." -ForegroundColor Yellow
        docker-compose down
        docker-compose -f docker-compose.dev.yml up -d
        Write-Host ""
        Write-Host "✅ Development mode started!" -ForegroundColor Green
        Write-Host "🌐 Frontend: http://localhost:3000" -ForegroundColor Cyan
        Write-Host "🔧 Backend: http://localhost:8080" -ForegroundColor Cyan
        Write-Host ""
        Write-Host "📝 Edit files in frontend/src/ and see changes live!" -ForegroundColor Yellow
        Write-Host "Login: superadmin@test.com / pls_change_me" -ForegroundColor Yellow
    }
    "3" {
        Write-Host "🛑 Stopping all containers..." -ForegroundColor Red
        docker-compose down
        docker-compose -f docker-compose.dev.yml down
        Write-Host "✅ All containers stopped!" -ForegroundColor Green
    }
    "4" {
        Write-Host "Select container to view logs:" -ForegroundColor Yellow
        Write-Host "1. Frontend"
        Write-Host "2. Backend"
        Write-Host "3. Database"
        Write-Host "4. MinIO Storage"
        $logChoice = Read-Host "Enter choice (1-4)"
        
        switch ($logChoice) {
            "1" { docker logs -f zed-cmms-frontend }
            "2" { docker logs -f zed-cmms-backend }
            "3" { docker logs -f zed_db }
            "4" { docker logs -f zed_minio }
            default { Write-Host "Invalid choice" -ForegroundColor Red }
        }
    }
    default {
        Write-Host "❌ Invalid choice!" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "📚 See CUSTOMIZATION-GUIDE.md for how to customize your project!" -ForegroundColor Cyan
