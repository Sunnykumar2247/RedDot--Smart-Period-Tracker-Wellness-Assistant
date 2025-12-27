# RedDot API Documentation

## Base URL
- Development: `http://localhost:8080`
- Production: `https://api.reddot.com`

## Authentication

All protected endpoints require a JWT token in the Authorization header:
```
Authorization: Bearer <access_token>
```

## Endpoints

### Authentication

#### POST /api/auth/signup
Register a new user.

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "password123",
  "firstName": "Jane",
  "lastName": "Doe",
  "consentGiven": true
}
```

**Response:**
```json
{
  "accessToken": "jwt_token",
  "refreshToken": "refresh_token",
  "tokenType": "Bearer",
  "expiresIn": 86400,
  "user": { ... }
}
```

#### POST /api/auth/login
Authenticate user.

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

#### POST /api/auth/forgot-password
Request password reset OTP.

**Request Body:**
```json
{
  "email": "user@example.com"
}
```

#### POST /api/auth/reset-password
Reset password using OTP.

**Request Body:**
```json
{
  "email": "user@example.com",
  "otp": "123456",
  "newPassword": "newpassword123"
}
```

### Period Tracking

#### GET /api/periods
Get all periods for current user.

**Response:**
```json
[
  {
    "id": 1,
    "startDate": "2025-01-01",
    "endDate": "2025-01-05",
    "averageFlowIntensity": "MODERATE",
    "painLevel": 3,
    "notes": "Normal period"
  }
]
```

#### POST /api/periods
Create a new period entry.

**Request Body:**
```json
{
  "startDate": "2025-01-01",
  "endDate": "2025-01-05",
  "averageFlowIntensity": "MODERATE",
  "painLevel": 3,
  "notes": "Normal period"
}
```

### Predictions

#### GET /api/predictions/cycle
Get cycle prediction.

**Response:**
```json
{
  "predictedPeriodStart": "2025-02-01",
  "predictedOvulationDate": "2025-01-18",
  "fertileWindowStart": "2025-01-13",
  "fertileWindowEnd": "2025-01-19",
  "predictionConfidence": 0.82,
  "explanation": "Based on your 5 logged periods...",
  "isIrregular": false,
  "estimatedCycleLength": 28
}
```

### Analytics

#### GET /api/analytics/dashboard
Get complete dashboard analytics.

**Response:**
```json
{
  "cycleConsistency": { ... },
  "symptomFrequency": { ... },
  "moodTrends": { ... },
  "wellnessScore": { ... }
}
```

### Wellness

#### POST /api/wellness/log
Log wellness data.

**Request Body:**
```json
{
  "waterIntake": 2000,
  "sleepHours": 8,
  "sleepQuality": 4,
  "exerciseMinutes": 30,
  "exerciseType": "Yoga"
}
```

#### GET /api/wellness/tip
Get daily wellness tip.

**Response:**
```
"Stay hydrated! Aim for 8-10 glasses of water daily."
```

### User Profile

#### GET /api/profile
Get current user profile.

#### PUT /api/profile
Update user profile.

**Request Body:**
```json
{
  "firstName": "Jane",
  "lastName": "Doe",
  "height": 165,
  "weight": 60,
  "averageCycleLength": 28
}
```

## Swagger UI

Interactive API documentation available at:
- `http://localhost:8080/swagger-ui.html`

