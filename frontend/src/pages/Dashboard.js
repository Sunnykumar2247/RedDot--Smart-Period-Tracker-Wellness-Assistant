import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Navbar from '../components/Navbar';
import { useAuth } from '../context/AuthContext';
import { toast } from 'react-toastify';
import './Dashboard.css';

const Dashboard = () => {
  const { user } = useAuth();
  const [prediction, setPrediction] = useState(null);
  const [wellnessTip, setWellnessTip] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchDashboardData();
  }, []);

  const fetchDashboardData = async () => {
    try {
      const [predictionRes, tipRes] = await Promise.all([
        axios.get('http://localhost:8080/api/predictions/cycle'),
        axios.get('http://localhost:8080/api/wellness/tip')
      ]);
      
      setPrediction(predictionRes.data);
      setWellnessTip(tipRes.data);
    } catch (error) {
      console.error('Failed to fetch dashboard data:', error);
      toast.error('Failed to load dashboard data');
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div>
        <Navbar />
        <div className="container">Loading...</div>
      </div>
    );
  }

  return (
    <div>
      <Navbar />
      <div className="container">
        <div className="dashboard-header">
          <h1>Welcome back, {user?.firstName}!</h1>
          <p>Here's your health overview</p>
        </div>

        <div className="dashboard-grid">
          <div className="dashboard-card prediction-card">
            <h2>Next Period Prediction</h2>
            {prediction ? (
              <div className="prediction-content">
                <div className="prediction-date">
                  {new Date(prediction.predictedPeriodStart).toLocaleDateString()}
                </div>
                <div className="prediction-confidence">
                  Confidence: {Math.round(prediction.predictionConfidence * 100)}%
                </div>
                <p className="prediction-explanation">{prediction.explanation}</p>
                
                <div className="prediction-details">
                  <div className="detail-item">
                    <span className="detail-label">Ovulation:</span>
                    <span>{new Date(prediction.predictedOvulationDate).toLocaleDateString()}</span>
                  </div>
                  <div className="detail-item">
                    <span className="detail-label">Fertile Window:</span>
                    <span>
                      {new Date(prediction.fertileWindowStart).toLocaleDateString()} - 
                      {new Date(prediction.fertileWindowEnd).toLocaleDateString()}
                    </span>
                  </div>
                </div>
              </div>
            ) : (
              <p>Start logging your periods to get predictions!</p>
            )}
          </div>

          <div className="dashboard-card wellness-card">
            <h2>Daily Wellness Tip</h2>
            <p className="wellness-tip">{wellnessTip}</p>
          </div>

          <div className="dashboard-card quick-actions">
            <h2>Quick Actions</h2>
            <div className="action-buttons">
              <button className="btn btn-primary">Log Period</button>
              <button className="btn btn-secondary">Log Symptom</button>
              <button className="btn btn-secondary">Log Mood</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;

