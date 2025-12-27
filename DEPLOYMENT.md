# Deployment Guide

## 🌐 Live Application Links

### **Main Application**
🔗 **[Access Live Website](https://reddot-app.vercel.app)**

### Service URLs
- **Frontend**: https://reddot-app.vercel.app
- **Backend API**: https://reddot-api.onrender.com
- **Prediction Service**: https://reddot-prediction.onrender.com
- **API Docs**: https://reddot-api.onrender.com/swagger-ui.html

## 🚀 Deployment Platforms

### Frontend Deployment (Vercel)

1. **Sign up/Login to Vercel**: https://vercel.com
2. **Import Project**:
   - Click "New Project"
   - Import from GitHub: `Sunnykumar2247/RedDot--Smart-Period-Tracker-Wellness-Assistant`
3. **Configure**:
   - Framework Preset: React
   - Root Directory: `frontend`
   - Build Command: `npm run build`
   - Output Directory: `build`
4. **Environment Variables**:
   ```
   REACT_APP_API_URL=https://reddot-api.onrender.com
   ```
5. **Deploy**: Click "Deploy"

**Result**: Your frontend will be live at `https://your-project.vercel.app`

### Backend Deployment (Render)

1. **Sign up/Login to Render**: https://render.com
2. **Create New Web Service**:
   - Connect GitHub repository
   - Select repository: `RedDot--Smart-Period-Tracker-Wellness-Assistant`
3. **Configure**:
   - Name: `reddot-api`
   - Environment: Java
   - Build Command: `cd backend/spring-boot-api && ./mvnw clean package -DskipTests`
   - Start Command: `java -jar backend/spring-boot-api/target/*.jar`
   - Plan: Free tier available
4. **Add PostgreSQL Database**:
   - Create new PostgreSQL database
   - Note the connection string
5. **Environment Variables**:
   ```
   SPRING_DATASOURCE_URL=jdbc:postgresql://your-db-host:5432/reddot
   SPRING_DATASOURCE_USERNAME=your-username
   SPRING_DATASOURCE_PASSWORD=your-password
   JWT_SECRET=your-secret-key-minimum-32-characters-long
   JWT_EXPIRATION=86400000
   JWT_REFRESH_EXPIRATION=604800000
   PREDICTION_SERVICE_URL=https://reddot-prediction.onrender.com
   FRONTEND_URL=https://reddot-app.vercel.app
   ```
6. **Deploy**: Service will auto-deploy

**Result**: Your API will be live at `https://reddot-api.onrender.com`

### Prediction Service Deployment (Render)

1. **Create New Web Service** on Render
2. **Configure**:
   - Name: `reddot-prediction`
   - Environment: Python 3
   - Build Command: `cd backend/prediction-service && pip install -r requirements.txt`
   - Start Command: `cd backend/prediction-service && uvicorn main:app --host 0.0.0.0 --port $PORT`
3. **Environment Variables**:
   ```
   DATABASE_URL=postgresql://your-db-host:5432/reddot
   API_URL=https://reddot-api.onrender.com
   ```
4. **Deploy**: Service will auto-deploy

**Result**: Your prediction service will be live at `https://reddot-prediction.onrender.com`

## 🔄 Update Frontend API URL

After deploying backend, update frontend environment variable:

1. Go to Vercel Dashboard
2. Select your project
3. Go to Settings → Environment Variables
4. Update `REACT_APP_API_URL` to your backend URL
5. Redeploy

## ✅ Post-Deployment Checklist

- [ ] Frontend deployed and accessible
- [ ] Backend API deployed and accessible
- [ ] Prediction service deployed and accessible
- [ ] Database connected and migrations run
- [ ] Environment variables configured
- [ ] CORS configured for frontend URL
- [ ] API documentation accessible
- [ ] Test user registration/login
- [ ] Test period tracking
- [ ] Test predictions

## 🐛 Troubleshooting

### Frontend Issues
- **CORS Errors**: Update backend CORS configuration with frontend URL
- **API Connection Failed**: Check `REACT_APP_API_URL` environment variable
- **Build Fails**: Check Node.js version (should be 18+)

### Backend Issues
- **Database Connection**: Verify PostgreSQL connection string
- **Port Issues**: Render uses `$PORT` environment variable automatically
- **Build Fails**: Check Java version (should be 17+)

### Prediction Service Issues
- **Import Errors**: Ensure all dependencies in `requirements.txt`
- **Port Issues**: Use `$PORT` environment variable

## 📞 Support

If you encounter issues:
1. Check service logs in deployment platform
2. Verify environment variables
3. Check database connectivity
4. Review API documentation

---

**Happy Deploying! 🚀**

