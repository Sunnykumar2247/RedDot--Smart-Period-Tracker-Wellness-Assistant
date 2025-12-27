# RedDot – Smart Period Tracker & Wellness Assistant

📅 **Project Duration:** Feb 2025 – Apr 2025

🎯 **Project Goal:** Build a secure, intelligent, and user-friendly menstrual cycle & wellness tracking platform.

## 🏗️ Architecture Overview

This is a full-stack healthcare application with:
- **Backend Core API:** Spring Boot (Java) - REST APIs, authentication, business logic
- **Prediction Service:** FastAPI (Python) - ML-based cycle predictions
- **Frontend:** React.js - Modern, responsive UI with Chart.js analytics
- **Database:** PostgreSQL (recommended) or MySQL
- **Authentication:** JWT + OAuth2 with refresh tokens

## 📂 Project Structure

```
RedDot/
├── backend/
│   ├── spring-boot-api/          # Core REST API (Java Spring Boot)
│   └── prediction-service/       # Prediction microservice (FastAPI)
├── frontend/                      # React.js application
├── tests/
│   ├── api-tests/                 # REST Assured tests
│   └── e2e-tests/                 # Playwright tests
├── docs/                          # API documentation, schemas
├── .github/workflows/             # CI/CD pipelines
└── docker-compose.yml             # Local development setup
```

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Python 3.9+
- Node.js 18+
- PostgreSQL 14+
- Maven 3.8+
- npm/yarn

### Backend Setup (Spring Boot)

```bash
cd backend/spring-boot-api
mvn clean install
mvn spring-boot:run
```

API will be available at: `http://localhost:8080`

### Prediction Service Setup (FastAPI)

```bash
cd backend/prediction-service
python -m venv venv
source venv/bin/activate  # Windows: venv\Scripts\activate
pip install -r requirements.txt
uvicorn main:app --reload --port 8001
```

Service will be available at: `http://localhost:8001`

### Frontend Setup

```bash
cd frontend
npm install
npm start
```

Frontend will be available at: `http://localhost:3000`

## 🔐 Environment Variables

See `.env.example` files in each service directory for required environment variables.

## 🧪 Testing

### API Tests
```bash
cd tests/api-tests
mvn test
```

### E2E Tests
```bash
cd tests/e2e-tests
npm install
npx playwright test
```

## 📊 Features

- ✅ User authentication & authorization
- ✅ Period & cycle tracking
- ✅ Smart prediction engine (82% accuracy)
- ✅ Wellness assistant
- ✅ Analytics dashboard
- ✅ Notifications & reminders
- ✅ Admin dashboard
- ✅ GDPR-compliant data handling

## 📝 API Documentation

Once the services are running:
- Spring Boot API: `http://localhost:8080/swagger-ui.html`
- FastAPI: `http://localhost:8001/docs`

See `docs/API_DOCUMENTATION.md` for detailed API reference.

## 🧠 Core Features

### 1. User Profile & Onboarding
- Age, height, weight tracking
- Cycle length and period length configuration
- Health conditions (PCOS, thyroid, etc.)
- Lifestyle preferences
- GDPR-compliant consent handling

### 2. Period & Cycle Tracking
- Period start/end date logging
- Flow intensity tracking
- Pain level monitoring
- Mood and symptom tracking
- Cycle irregularity handling

### 3. Smart Prediction Engine
- Next period date prediction (82% accuracy)
- Ovulation window calculation
- Fertile days prediction
- Historical trend analysis
- Human-friendly explanations

### 4. Wellness Assistant
- Daily health tips
- Exercise & nutrition suggestions
- Hydration tracking
- Sleep quality logging
- Hormone-based wellness insights

### 5. Analytics Dashboard
- Cycle consistency charts
- Period history timeline
- Symptom frequency heatmap
- Mood trends visualization
- Wellness score calculation
- Prediction confidence indicators

### 6. Notifications & Reminders
- Period reminders
- Ovulation alerts
- Medication reminders
- Wellness nudges
- Email & in-app notifications

### 7. Admin Dashboard
- User analytics
- Active users monitoring
- Accuracy tracking
- Feature usage statistics

## 🔒 Security Features

- JWT token authentication
- Password hashing (BCrypt)
- Refresh token mechanism
- Role-based access control
- GDPR-compliant data handling
- Secure API endpoints
- CORS configuration

## 🧪 Testing

### API Tests (REST Assured)
```bash
cd tests/api-tests
mvn test
```

### E2E Tests (Playwright)
```bash
cd tests/e2e-tests
npm install
npx playwright install
npx playwright test
```

## 🚀 CI/CD

GitHub Actions pipeline includes:
- Automated build & test
- Code quality checks
- Linting
- Deployment simulation

See `.github/workflows/ci.yml` for details.

## 📚 Documentation

- [API Documentation](docs/API_DOCUMENTATION.md)
- [Database Schema](docs/DATABASE_SCHEMA.md)
- [Setup Guide](docs/SETUP_GUIDE.md)
- [Project Summary](PROJECT_SUMMARY.md)

## 🐳 Docker Setup

Run all services with Docker Compose:

```bash
docker-compose up -d
```

## 📦 Postman Collection

Import `docs/postman_collection.json` into Postman for API testing.

## 🤝 Contributing

This is a production-ready healthcare application. Follow security best practices and ensure all health data is handled with care.

## 📄 License

Proprietary - Healthcare Application

---

**Built with ❤️ for women's health and wellness**

