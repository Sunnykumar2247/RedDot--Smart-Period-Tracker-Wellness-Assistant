import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Navbar from '../components/Navbar';
import { toast } from 'react-toastify';
import './Wellness.css';

const Wellness = () => {
  const [wellnessTip, setWellnessTip] = useState('');
  const [wellnessLog, setWellnessLog] = useState({
    waterIntake: 0,
    sleepHours: 0,
    sleepQuality: 3,
    exerciseMinutes: 0,
    exerciseType: '',
    notes: ''
  });

  useEffect(() => {
    fetchWellnessTip();
  }, []);

  const fetchWellnessTip = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/wellness/tip');
      setWellnessTip(response.data);
    } catch (error) {
      console.error('Failed to fetch wellness tip:', error);
    }
  };

  const handleLogWellness = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/api/wellness/log', wellnessLog);
      toast.success('Wellness data logged!');
      setWellnessLog({
        waterIntake: 0,
        sleepHours: 0,
        sleepQuality: 3,
        exerciseMinutes: 0,
        exerciseType: '',
        notes: ''
      });
    } catch (error) {
      toast.error('Failed to log wellness data');
    }
  };

  return (
    <div>
      <Navbar />
      <div className="container">
        <h1>Wellness Assistant</h1>

        <div className="wellness-grid">
          <div className="card wellness-tip-card">
            <h2>Daily Wellness Tip</h2>
            <p className="wellness-tip-text">{wellnessTip}</p>
            <button className="btn btn-secondary" onClick={fetchWellnessTip}>
              Get New Tip
            </button>
          </div>

          <div className="card wellness-log-card">
            <h2>Log Wellness Data</h2>
            <form onSubmit={handleLogWellness}>
              <div className="form-group">
                <label className="form-label">Water Intake (ml)</label>
                <input
                  type="number"
                  className="form-input"
                  value={wellnessLog.waterIntake}
                  onChange={(e) => setWellnessLog({ ...wellnessLog, waterIntake: parseInt(e.target.value) || 0 })}
                  min="0"
                />
              </div>

              <div className="form-group">
                <label className="form-label">Sleep Hours</label>
                <input
                  type="number"
                  className="form-input"
                  value={wellnessLog.sleepHours}
                  onChange={(e) => setWellnessLog({ ...wellnessLog, sleepHours: parseInt(e.target.value) || 0 })}
                  min="0"
                  max="24"
                />
              </div>

              <div className="form-group">
                <label className="form-label">Sleep Quality (1-5)</label>
                <input
                  type="range"
                  min="1"
                  max="5"
                  value={wellnessLog.sleepQuality}
                  onChange={(e) => setWellnessLog({ ...wellnessLog, sleepQuality: parseInt(e.target.value) })}
                  className="form-input"
                />
                <span>{wellnessLog.sleepQuality}</span>
              </div>

              <div className="form-group">
                <label className="form-label">Exercise Minutes</label>
                <input
                  type="number"
                  className="form-input"
                  value={wellnessLog.exerciseMinutes}
                  onChange={(e) => setWellnessLog({ ...wellnessLog, exerciseMinutes: parseInt(e.target.value) || 0 })}
                  min="0"
                />
              </div>

              <div className="form-group">
                <label className="form-label">Exercise Type</label>
                <input
                  type="text"
                  className="form-input"
                  value={wellnessLog.exerciseType}
                  onChange={(e) => setWellnessLog({ ...wellnessLog, exerciseType: e.target.value })}
                  placeholder="e.g., Walking, Yoga, Running"
                />
              </div>

              <div className="form-group">
                <label className="form-label">Notes</label>
                <textarea
                  className="form-input"
                  value={wellnessLog.notes}
                  onChange={(e) => setWellnessLog({ ...wellnessLog, notes: e.target.value })}
                  rows="3"
                />
              </div>

              <button type="submit" className="btn btn-primary">Save Log</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Wellness;

