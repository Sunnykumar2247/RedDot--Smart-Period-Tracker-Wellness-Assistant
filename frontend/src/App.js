import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './App.css';

import { AuthProvider } from './context/AuthContext';
import PrivateRoute from './components/PrivateRoute';
import Login from './pages/Login';
import Signup from './pages/Signup';
import Dashboard from './pages/Dashboard';
import PeriodTracking from './pages/PeriodTracking';
import Analytics from './pages/Analytics';
import Wellness from './pages/Wellness';
import Profile from './pages/Profile';
import Onboarding from './pages/Onboarding';

function App() {
  return (
    <AuthProvider>
      <div className="App">
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/onboarding" element={<PrivateRoute><Onboarding /></PrivateRoute>} />
          <Route path="/dashboard" element={<PrivateRoute><Dashboard /></PrivateRoute>} />
          <Route path="/periods" element={<PrivateRoute><PeriodTracking /></PrivateRoute>} />
          <Route path="/analytics" element={<PrivateRoute><Analytics /></PrivateRoute>} />
          <Route path="/wellness" element={<PrivateRoute><Wellness /></PrivateRoute>} />
          <Route path="/profile" element={<PrivateRoute><Profile /></PrivateRoute>} />
          <Route path="/" element={<Navigate to="/dashboard" replace />} />
        </Routes>
        <ToastContainer position="top-right" autoClose={3000} />
      </div>
    </AuthProvider>
  );
}

export default App;

