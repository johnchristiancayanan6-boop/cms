
# 🧹 Resetting Eastwest CMMS Database and MinIO Data

If you need to delete the existing PostgresSQL and MinIO data for a fresh start, follow this step-by-step guide.

> ⚠️ This will **permanently delete** all database and file storage data. Proceed with caution.

---

## 📁 Step 1: Stop and Remove Containers

Shut down all running containers related to Eastwest CMMS:

```bash
docker compose down
```

---

## 🗑️ Step 2: Remove Docker Volumes

Eastwest CMMS uses named Docker volumes for data persistence:
- `eastwest-cmms_postgres_data` for PostgreSQL
- `eastwest-cmms_minio_data` for MinIO

Delete them with:

```bash
docker volume rm eastwest-cmms_postgres_data eastwest-cmms_minio_data
```

## 🚀 Step 4: Restart Eastwest CMMS

Recreate containers and volumes with fresh data:

```bash
docker compose up -d
```

---

## ✅ Result

You now have:
- A fresh PostgreSQL database (`POSTGRES_DB`)
- An empty MinIO bucket
- Eastwest CMMS services running on a clean slate