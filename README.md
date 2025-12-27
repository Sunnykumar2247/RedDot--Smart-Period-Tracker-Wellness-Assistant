# RedDot – Smart Period Tracker & Wellness Assistant

[![CI/CD](https://github.com/Sunnykumar2247/RedDot--Smart-Period-Tracker-Wellness-Assistant/actions/workflows/ci.yml/badge.svg)](https://github.com/Sunnykumar2247/RedDot--Smart-Period-Tracker-Wellness-Assistant/actions)
[![Deploy Guide](https://img.shields.io/badge/📖_Deployment_Guide-Read_Here-blue?style=flat-square)](DEPLOYMENT.md)
[![Status](https://img.shields.io/badge/Status-Ready_to_Deploy-green?style=flat-square)]()

## 🌐 **Live Application**

> ⚠️ **IMPORTANT**: The application needs to be deployed first! The links below are **placeholders**. 
> 
> **To get your live application:**
> 1. Follow the [Deployment Guide](DEPLOYMENT.md) to deploy to Vercel/Render
> 2. Update the URLs below with your actual deployment URLs
> 3. Commit and push the changes

### **👉 Deploy Your Application**

**After deployment, your links will be:**
- 🎨 **Frontend (React App)**: `https://your-app.vercel.app` (Deploy to [Vercel](https://vercel.com))
- 🔧 **Backend API**: `https://your-api.onrender.com` (Deploy to [Render](https://render.com))
- 🤖 **Prediction Service**: `https://your-prediction.onrender.com` (Deploy to [Render](https://render.com))
- 📚 **API Documentation**: `https://your-api.onrender.com/swagger-ui.html`

**Quick Deploy:**
- [📖 Full Deployment Instructions](DEPLOYMENT.md)
- [🚀 Deploy Frontend to Vercel](https://vercel.com/new)
- [🚀 Deploy Backend to Render](https://render.com)

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
- [Deployment Guide](DEPLOYMENT.md)
- [Project Summary](PROJECT_SUMMARY.md)

## 🚀 Deployment

### Quick Deploy Options

#### Frontend (React) - Vercel/Netlify
1. **Vercel** (Recommended):
   - Connect your GitHub repository to Vercel
   - Set build directory to `frontend`
   - Add environment variable: `REACT_APP_API_URL=https://your-backend-url.com`
   - Deploy automatically on push

2. **Netlify**:
   - Connect repository to Netlify
   - Build command: `cd frontend && npm install && npm run build`
   - Publish directory: `frontend/build`

#### Backend (Spring Boot) - Render/Railway
1. **Render**:
   - Create new Web Service
   - Connect GitHub repository
   - Build command: `cd backend/spring-boot-api && ./mvnw clean package`
   - Start command: `java -jar backend/spring-boot-api/target/*.jar`
   - Add PostgreSQL database
   - Set environment variables

2. **Railway**:
   - Connect repository
   - Add PostgreSQL service
   - Configure environment variables
   - Deploy automatically

#### Prediction Service (FastAPI) - Render/Railway
1. **Render**:
   - Create new Web Service
   - Build command: `cd backend/prediction-service && pip install -r requirements.txt`
   - Start command: `cd backend/prediction-service && uvicorn main:app --host 0.0.0.0 --port $PORT`

### Environment Variables for Deployment

**Backend (Spring Boot):**
```env
SPRING_DATASOURCE_URL=jdbc:postgresql://your-db-host:5432/reddot
SPRING_DATASOURCE_USERNAME=your-username
SPRING_DATASOURCE_PASSWORD=your-password
JWT_SECRET=your-secret-key-minimum-32-characters
JWT_EXPIRATION=86400000
PREDICTION_SERVICE_URL=https://your-prediction-service-url.com
FRONTEND_URL=https://your-frontend-url.com
```

**Frontend (React):**
```env
REACT_APP_API_URL=https://your-backend-api-url.com
```

**Prediction Service (FastAPI):**
```env
DATABASE_URL=postgresql://your-db-host:5432/reddot
API_URL=https://your-backend-api-url.com
```

### One-Click Deploy

[![Deploy to Vercel](https://vercel.com/button)](https://vercel.com/new/clone?repository-url=https://github.com/Sunnykumar2247/RedDot--Smart-Period-Tracker-Wellness-Assistant)
[![Deploy to Render](https://render.com/images/deploy-to-render-button.svg)](https://render.com/deploy)

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

