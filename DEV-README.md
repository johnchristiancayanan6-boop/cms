# ZED CMMS PH - Development Mode

## Quick Start (Development Mode - Editable)

For development with **live editing** (changes reflect immediately):

```powershell
# Stop any running containers
docker-compose down

# Start in development mode
docker-compose -f docker-compose.dev.yml up
```

Now you can:
- Edit files in `frontend/src/` and see changes instantly in the browser
- Edit files in `api/src/` and rebuild as needed
- All changes are persisted to your local files

### Frontend Development
- Files: `frontend/src/`
- Changes auto-reload in browser
- Branding files:
  - `frontend/src/layouts/ExtendedSidebarLayout/Sidebar/index.tsx`
  - `frontend/src/components/NavBar/index.tsx`
  - `frontend/src/components/AppInit/index.tsx`
  - `frontend/public/index.html`
  - `frontend/public/manifest.json`

### Backend Development  
- Files: `api/src/`
- Requires rebuild: `docker-compose -f docker-compose.dev.yml up --build api`

### Stop Development Mode
```powershell
docker-compose -f docker-compose.dev.yml down
```

---

## Production Mode (Optimized Build)

For production deployment:

```powershell
docker-compose up -d
```

---

## Environment Configuration

Edit `.env` file to customize:
- `BRAND_CONFIG` - Branding (company name, powered by text)
- `CUSTOM_COLORS` - Color scheme
- `LOGO_PATHS` - Custom logo
- `ENABLE_CORS` - Must be `true` for frontend/backend communication
- Database credentials
- Email settings
- Storage settings

## Default Login
- Email: `superadmin@test.com`
- Password: `pls_change_me`

## Access URLs
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080
- MinIO Console: http://localhost:9001
- Database: localhost:5432
