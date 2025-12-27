import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Navbar from '../components/Navbar';
import { toast } from 'react-toastify';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  BarElement,
  Title,
  Tooltip,
  Legend,
  ArcElement
} from 'chart.js';
import { Line, Bar, Doughnut } from 'react-chartjs-2';
import './Analytics.css';

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  BarElement,
  Title,
  Tooltip,
  Legend,
  ArcElement
);

const Analytics = () => {
  const [dashboardData, setDashboardData] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchAnalytics();
  }, []);

  const fetchAnalytics = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/analytics/dashboard');
      setDashboardData(response.data);
    } catch (error) {
      console.error('Failed to fetch analytics:', error);
      toast.error('Failed to load analytics');
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

  const cycleData = dashboardData?.cycleConsistency;
  const symptomData = dashboardData?.symptomFrequency;
  const moodData = dashboardData?.moodTrends;
  const wellnessData = dashboardData?.wellnessScore;

  const cycleChartData = {
    labels: cycleData?.cycleLengths?.map((_, i) => `Cycle ${i + 1}`) || [],
    datasets: [{
      label: 'Cycle Length (days)',
      data: cycleData?.cycleLengths || [],
      borderColor: '#ff6b9d',
      backgroundColor: 'rgba(255, 107, 157, 0.1)',
      tension: 0.4
    }]
  };

  const symptomChartData = {
    labels: Object.keys(symptomData?.frequency || {}),
    datasets: [{
      label: 'Frequency',
      data: Object.values(symptomData?.frequency || {}),
      backgroundColor: [
        '#ff6b9d',
        '#c44569',
        '#f8d7da',
        '#ff8fb3',
        '#e55a8a'
      ]
    }]
  };

  const moodChartData = {
    labels: Object.keys(moodData?.moodDistribution || {}),
    datasets: [{
      label: 'Mood Count',
      data: Object.values(moodData?.moodDistribution || {}),
      backgroundColor: '#ff6b9d'
    }]
  };

  return (
    <div>
      <Navbar />
      <div className="container">
        <h1>Analytics Dashboard</h1>

        <div className="analytics-grid">
          <div className="analytics-card">
            <h2>Cycle Consistency</h2>
            {cycleData?.cycleLengths?.length > 0 ? (
              <Line data={cycleChartData} options={{ responsive: true }} />
            ) : (
              <p>Not enough data yet. Keep logging your periods!</p>
            )}
            <div className="stats-info">
              <div className="stat-item">
                <span className="stat-label">Average:</span>
                <span className="stat-value">{cycleData?.averageCycleLength || 'N/A'} days</span>
              </div>
              <div className="stat-item">
                <span className="stat-label">Consistency:</span>
                <span className="stat-value">{cycleData?.consistency || 'N/A'}</span>
              </div>
            </div>
          </div>

          <div className="analytics-card">
            <h2>Symptom Frequency</h2>
            {symptomData?.frequency && Object.keys(symptomData.frequency).length > 0 ? (
              <Doughnut data={symptomChartData} options={{ responsive: true }} />
            ) : (
              <p>No symptoms logged yet.</p>
            )}
          </div>

          <div className="analytics-card">
            <h2>Mood Trends</h2>
            {moodData?.moodDistribution && Object.keys(moodData.moodDistribution).length > 0 ? (
              <Bar data={moodChartData} options={{ responsive: true }} />
            ) : (
              <p>No moods logged yet.</p>
            )}
          </div>

          <div className="analytics-card wellness-score-card">
            <h2>Wellness Score</h2>
            <div className="wellness-score">
              <div className="score-circle">
                <span className="score-value">{wellnessData?.score || 0}</span>
                <span className="score-label">/ 100</span>
              </div>
              <p className="score-level">{wellnessData?.level || 'N/A'}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Analytics;

