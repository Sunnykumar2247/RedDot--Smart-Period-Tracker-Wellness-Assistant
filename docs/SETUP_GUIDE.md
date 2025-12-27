# RedDot Setup Guide

## Prerequisites

- Java 17+
- Python 3.9+
- Node.js 18+
- PostgreSQL 14+
- Maven 3.8+
- npm/yarn

## Database Setup

1. Install PostgreSQL
2. Create database:
```sql
CREATE DATABASE reddot;
CREATE USER reddot_user WITH PASSWORD 'reddot_password';
GRANT ALL PRIVILEGES ON DATABASE reddot TO reddot_user;
```

## Backend Setup

### Spring Boot API

1. Navigate to backend directory:
```bash
cd backend/spring-boot-api
```

2. Configure environment variables (create `.env` or set in `application.yml`):
```yaml
SPRING_DATASOURCE_URL: jdbc:postgresql://localhost:5432/reddot
SPRING_DATASOURCE_USERNAME: reddot_user
SPRING_DATASOURCE_PASSWORD: reddot_password
JWT_SECRET: your-secret-key-minimum-32-characters
```

3. Build and run:
```bash
mvn clean install
mvn spring-boot:run
```

API will be available at `http://localhost:8080`

### FastAPI Prediction Service

1. Navigate to prediction service:
```bash
cd backend/prediction-service
```

2. Create virtual environment:
```bash
python -m venv venv
source venv/bin/activate  # Windows: venv\Scripts\activate
```

3. Install dependencies:
```bash
pip install -r requirements.txt
```

4. Run service:
```bash
uvicorn main:app --reload --port 8001
```

Service will be available at `http://localhost:8001`

## Frontend Setup

1. Navigate to frontend:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start development server:
```bash
npm start
```

Frontend will be available at `http://localhost:3000`

## Docker Setup (Alternative)

Run all services with Docker Compose:

```bash
docker-compose up -d
```

## Testing

### API Tests
```bash
cd tests/api-tests
mvn test
```

### E2E Tests
```bash
cd tests/e2e-tests
npm install
npx playwright install
npx playwright test
```

## Access Points

- Frontend: http://localhost:3000
- Spring Boot API: http://localhost:8080
- FastAPI Service: http://localhost:8001
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/api-docs

## Troubleshooting

1. **Database connection errors**: Ensure PostgreSQL is running and credentials are correct
2. **Port conflicts**: Change ports in configuration files
3. **CORS errors**: Check CORS configuration in SecurityConfig
4. **JWT errors**: Ensure JWT_SECRET is set and at least 32 characters

