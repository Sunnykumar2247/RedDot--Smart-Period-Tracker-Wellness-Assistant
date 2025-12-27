import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Navbar from '../components/Navbar';
import { toast } from 'react-toastify';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import './PeriodTracking.css';

const PeriodTracking = () => {
  const [periods, setPeriods] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    startDate: new Date(),
    endDate: null,
    averageFlowIntensity: 'MODERATE',
    painLevel: 0,
    notes: ''
  });

  useEffect(() => {
    fetchPeriods();
  }, []);

  const fetchPeriods = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/periods');
      setPeriods(response.data);
    } catch (error) {
      console.error('Failed to fetch periods:', error);
      toast.error('Failed to load periods');
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        ...formData,
        startDate: formData.startDate.toISOString().split('T')[0],
        endDate: formData.endDate ? formData.endDate.toISOString().split('T')[0] : null
      };
      
      await axios.post('http://localhost:8080/api/periods', payload);
      toast.success('Period logged successfully!');
      setShowForm(false);
      setFormData({
        startDate: new Date(),
        endDate: null,
        averageFlowIntensity: 'MODERATE',
        painLevel: 0,
        notes: ''
      });
      fetchPeriods();
    } catch (error) {
      toast.error('Failed to log period');
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
        <div className="page-header">
          <h1>Period Tracking</h1>
          <button className="btn btn-primary" onClick={() => setShowForm(!showForm)}>
            {showForm ? 'Cancel' : 'Log Period'}
          </button>
        </div>

        {showForm && (
          <div className="card period-form-card">
            <h2>Log New Period</h2>
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label className="form-label">Start Date</label>
                <DatePicker
                  selected={formData.startDate}
                  onChange={(date) => setFormData({ ...formData, startDate: date })}
                  className="form-input"
                  dateFormat="yyyy-MM-dd"
                />
              </div>

              <div className="form-group">
                <label className="form-label">End Date (optional)</label>
                <DatePicker
                  selected={formData.endDate}
                  onChange={(date) => setFormData({ ...formData, endDate: date })}
                  className="form-input"
                  dateFormat="yyyy-MM-dd"
                  isClearable
                />
              </div>

              <div className="form-group">
                <label className="form-label">Flow Intensity</label>
                <select
                  className="form-input"
                  value={formData.averageFlowIntensity}
                  onChange={(e) => setFormData({ ...formData, averageFlowIntensity: e.target.value })}
                >
                  <option value="LIGHT">Light</option>
                  <option value="MODERATE">Moderate</option>
                  <option value="HEAVY">Heavy</option>
                  <option value="VERY_HEAVY">Very Heavy</option>
                </select>
              </div>

              <div className="form-group">
                <label className="form-label">Pain Level (0-10)</label>
                <input
                  type="range"
                  min="0"
                  max="10"
                  value={formData.painLevel}
                  onChange={(e) => setFormData({ ...formData, painLevel: parseInt(e.target.value) })}
                  className="form-input"
                />
                <span>{formData.painLevel}</span>
              </div>

              <div className="form-group">
                <label className="form-label">Notes</label>
                <textarea
                  className="form-input"
                  value={formData.notes}
                  onChange={(e) => setFormData({ ...formData, notes: e.target.value })}
                  rows="3"
                />
              </div>

              <button type="submit" className="btn btn-primary">Save Period</button>
            </form>
          </div>
        )}

        <div className="periods-list">
          {periods.length === 0 ? (
            <div className="card">
              <p>No periods logged yet. Start tracking your cycle!</p>
            </div>
          ) : (
            periods.map(period => (
              <div key={period.id} className="card period-card">
                <div className="period-header">
                  <h3>{new Date(period.startDate).toLocaleDateString()}</h3>
                  <span className="period-status">{period.averageFlowIntensity}</span>
                </div>
                {period.endDate && (
                  <p className="period-dates">
                    {new Date(period.startDate).toLocaleDateString()} - 
                    {new Date(period.endDate).toLocaleDateString()}
                  </p>
                )}
                {period.painLevel > 0 && (
                  <p className="period-pain">Pain Level: {period.painLevel}/10</p>
                )}
                {period.notes && <p className="period-notes">{period.notes}</p>}
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  );
};

export default PeriodTracking;

