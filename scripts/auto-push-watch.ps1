param(
  [string]$RepoPath = (Get-Location).Path,
  [string]$Branch = 'main',
  [string]$Remote = 'origin',
  [int]$PollSeconds = 15,
  [string]$CommitPrefix = 'chore: auto update'
)

$ErrorActionPreference = 'Stop'

Set-Location $RepoPath

$insideRepo = git rev-parse --is-inside-work-tree 2>$null
if ($LASTEXITCODE -ne 0 -or $insideRepo -ne 'true') {
  throw "Not a git repository: $RepoPath"
}

$currentBranch = git branch --show-current
if ($currentBranch -ne $Branch) {
  Write-Host "Current branch is '$currentBranch' (auto-push target is '$Branch')." -ForegroundColor Yellow
  Write-Host "Switch to '$Branch' or pass -Branch '$currentBranch'." -ForegroundColor Yellow
  exit 1
}

$excludePaths = @(
  'node_modules',
  'frontend/node_modules',
  'mobile/node_modules',
  'api/target',
  'frontend/build',
  'mobile/android/app/build',
  'mobile/ios/build'
)

Write-Host "Auto push watcher started for $RepoPath" -ForegroundColor Cyan
Write-Host "Remote: $Remote | Branch: $Branch | Poll: ${PollSeconds}s" -ForegroundColor Cyan
Write-Host 'Press Ctrl+C to stop.' -ForegroundColor Cyan

while ($true) {
  Start-Sleep -Seconds $PollSeconds

  $changes = git status --porcelain
  if ($LASTEXITCODE -ne 0) {
    Write-Host 'git status failed. Retrying...' -ForegroundColor Yellow
    continue
  }

  if ([string]::IsNullOrWhiteSpace($changes)) {
    continue
  }

  git add -A | Out-Null

  foreach ($path in $excludePaths) {
    git reset -- $path 2>$null | Out-Null
  }

  $pending = git status --porcelain
  if ([string]::IsNullOrWhiteSpace($pending)) {
    continue
  }

  $timestamp = Get-Date -Format 'yyyy-MM-dd HH:mm:ss'
  $message = "$CommitPrefix $timestamp"

  Write-Host "Committing changes: $message" -ForegroundColor Green
  git commit --no-verify -m "$message" | Out-Host
  if ($LASTEXITCODE -ne 0) {
    Write-Host 'Commit failed. Waiting for next cycle...' -ForegroundColor Red
    continue
  }

  Write-Host "Pushing to $Remote/$Branch..." -ForegroundColor Green
  git push $Remote $Branch | Out-Host
  if ($LASTEXITCODE -ne 0) {
    Write-Host 'Push failed. Resolve manually, then rerun this script.' -ForegroundColor Red
    break
  }

  Write-Host 'Auto push completed.' -ForegroundColor Green
}
