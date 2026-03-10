# 🎨 ZED CMMS PH - Complete Customization Guide

## ✅ Current Status
Your project is now **fully editable**. All branding has been changed from "Atlas CMMS / Powered by Intelloop" to "ZED CMMS PH".

---

## 📝 Files You Can Edit (Your Source Code)

### 🎨 **BRANDING & TEXT**

#### 1. **Sidebar Branding** (Left menu)
**File:** `frontend/src/layouts/ExtendedSidebarLayout/Sidebar/index.tsx`
- Line 81: `Powered by ZED CMMS PH` (appears in sidebar)
- Line 140: `Powered by ZED CMMS PH` (appears in mobile drawer)

#### 2. **Navigation Bar Branding** (Top bar)
**File:** `frontend/src/components/NavBar/index.tsx`
- Line 136: `Powered by ZED CMMS PH`

#### 3. **Loading Screen Branding**
**File:** `frontend/src/components/AppInit/index.tsx`
- Line 32: `Powered by ZED CMMS PH`

#### 4. **Page Title & Meta Tags**
**File:** `frontend/public/index.html`
- Line 15: `<meta name="author" content="ZED CMMS PH" />`
- Line 20: `<meta property="og:title" content="ZED CMMS PH - Maintenance Management Software" />`
- Line 26: `<meta property="og:site_name" content="ZED CMMS PH" />`
- Line 46: `<h1>ZED CMMS PH - Maintenance Management Software</h1>`

#### 5. **App Name (Mobile/PWA)**
**File:** `frontend/public/manifest.json`
- Line 6: `"short_name": "ZED"`
- Line 7: `"name": "ZED CMMS PH"`

#### 6. **README**
**File:** `README.MD`
- Line 2: `<h1 align="center">ZED CMMS PH</h1>`
- Line 80: Access instructions

---

### 🎨 **LOGO & IMAGES**

#### Replace Logo
**Folder:** `frontend/public/static/images/logo/`
Replace these files with your own:
- `logo.png` - Main logo (192x192px recommended)
- `favicon.ico` - Browser icon
- `favicon-16x16.png`
- `favicon-32x32.png`

#### Upload Custom Logo via Environment
**File:** `.env`
```env
LOGO_PATHS={"login":"/path/to/login-logo.png","sidebar":"/path/to/sidebar-logo.png"}
```

---

### 🎨 **COLORS & THEME**

#### Change Colors
**File:** `.env`
```env
CUSTOM_COLORS={"primary":"#1975ff","secondary":"#00b4d8"}
```

#### Advanced Theme Customization
**File:** `frontend/src/theme/ThemeProvider.tsx`
- Modify color palette
- Change fonts
- Adjust spacing

**File:** `frontend/src/theme/schemes/PureLightTheme.ts`
- Light mode colors

**File:** `frontend/src/theme/schemes/NebulaFighterTheme.ts`
- Dark mode colors

---

### 💼 **BRANDING CONFIGURATION**

**File:** `.env`
```env
BRAND_CONFIG={"companyName":"ZED CMMS PH","poweredBy":"ZED CMMS PH"}
```

Change this to:
```env
BRAND_CONFIG={"companyName":"Your Company Name","poweredBy":"Your Brand Text"}
```

---

### 📱 **MOBILE APP**

#### App Name
**File:** `mobile/app.config.ts`
- Line 9: `name: 'ZED CMMS PH'`
- Line 10: `slug: 'zed-cmms-ph'`

**File:** `mobile/android/app/src/main/res/values/strings.xml`
- Line 2: `<string name="app_name">ZED CMMS PH</string>`

**File:** `mobile/android/settings.gradle`
- Line 34: `rootProject.name = 'ZED CMMS PH'`

---

### 🗄️ **DOCKER CONTAINERS**

**File:** `docker-compose.yml`
- Line 1: `name: zed-cmms-ph` (Project name)
- Line 5: `container_name: zed_db`
- Line 16: `container_name: zed-cmms-backend`
- Line 71: `container_name: zed-cmms-frontend`
- Line 95: `container_name: zed_minio`

---

## 🚀 How to See Your Changes

### Frontend Changes (React/HTML/CSS)
After editing frontend files, **RESTART** the containers:
```powershell
docker-compose down
docker-compose up -d --build frontend
```
Wait 5 minutes for build, then refresh browser.

### Quick Development Mode (Live Reload)
For instant changes without rebuilding:
```powershell
docker-compose down
docker-compose -f docker-compose.dev.yml up
```
Now edit files in `frontend/src/` and see changes instantly!

### Backend Changes (Java)
```powershell
docker-compose down
docker-compose up -d --build api
```

### Environment Variables Only
```powershell
docker-compose down
docker-compose up -d
```

---

## 🎯 Quick Customization Checklist

- [ ] Change company name in `.env` → `BRAND_CONFIG`
- [ ] Replace logo files in `frontend/public/static/images/logo/`
- [ ] Change colors in `.env` → `CUSTOM_COLORS`
- [ ] Update "Powered by" text in 4 React component files
- [ ] Change page title in `frontend/public/index.html`
- [ ] Update app name in `frontend/public/manifest.json`
- [ ] Change container names in `docker-compose.yml`
- [ ] Update README.md with your project name

---

## 📂 Project Structure

```
cmms/
├── frontend/          ← React app (YOUR UI)
│   ├── src/
│   │   ├── components/    ← UI components
│   │   ├── layouts/       ← Page layouts
│   │   ├── content/       ← Page content
│   │   ├── theme/         ← Colors & styling
│   │   └── i18n/          ← Translations
│   └── public/        ← Static files (logo, favicon)
├── api/               ← Java backend
├── mobile/            ← React Native mobile app
├── .env               ← Configuration
└── docker-compose.yml ← Docker setup
```

---

## 🔧 Advanced Customization

### Add New Features
- Frontend: Add React components in `frontend/src/components/`
- Backend: Add Java classes in `api/src/main/java/com/grash/`

### Change Database
- Edit `.env` → `POSTGRES_USER`, `POSTGRES_PWD`

### Add Custom Pages
- Create files in `frontend/src/content/own/`
- Add routes in `frontend/src/router.tsx`

### Modify API Endpoints
- Edit controllers in `api/src/main/java/com/grash/controller/`

---

## 📞 Default Login
- **Email:** `superadmin@test.com`
- **Password:** `pls_change_me`

Change password after first login!

---

## 🌐 Access URLs
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080  
- MinIO Storage: http://localhost:9001
- Database: localhost:5432

---

## 💡 Tips

1. **Always backup `.env` and database before major changes**
2. **Use development mode for quick testing**
3. **Commit changes to Git to track your modifications**
4. **Test in production mode before deploying**
5. **Keep Docker images updated**

---

## 🆘 Common Issues

**Changes not showing?**
- Hard refresh browser (Ctrl+Shift+R)
- Clear browser cache
- Rebuild frontend container

**Can't login?**
- Check backend logs: `docker logs zed-cmms-backend`
- Verify CORS is enabled in `.env`
- Reset database: `docker-compose down -v`

**Port already in use?**
- Change ports in `docker-compose.yml`
- Kill existing processes on those ports

---

**Your project is ready for customization! 🎉**
