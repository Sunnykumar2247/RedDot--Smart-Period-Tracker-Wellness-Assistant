import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import './Onboarding.css';

const Onboarding = () => {
  const navigate = useNavigate();
  const [step, setStep] = useState(1);
  const [formData, setFormData] = useState({
    dateOfBirth: null,
    height: null,
    weight: null,
    averageCycleLength: null,
    averagePeriodLength: null,
    healthConditions: [],
    activityLevel: '',
    dietType: '',
    consentGiven: true
  });

  const handleChange = (field, value) => {
    setFormData(prev => ({ ...prev, [field]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/api/profile/onboarding', {
        ...formData,
        dateOfBirth: formData.dateOfBirth ? formData.dateOfBirth.toISOString().split('T')[0] : null
      });
      toast.success('Onboarding completed!');
      navigate('/dashboard');
    } catch (error) {
      toast.error('Failed to complete onboarding');
    }
  };

  return (
    <div className="onboarding-container">
      <div className="onboarding-card">
        <div className="onboarding-header">
          <h1>Welcome to RedDot! 🔴</h1>
          <p>Let's set up your profile</p>
          <div className="progress-bar">
            <div className="progress" style={{ width: `${(step / 3) * 100}%` }}></div>
          </div>
        </div>

        <form onSubmit={handleSubmit}>
          {step === 1 && (
            <div className="onboarding-step">
              <h2>Basic Information</h2>
              <div className="form-group">
                <label className="form-label">Date of Birth</label>
                <DatePicker
                  selected={formData.dateOfBirth}
                  onChange={(date) => handleChange('dateOfBirth', date)}
                  className="form-input"
                  dateFormat="yyyy-MM-dd"
                  maxDate={new Date()}
                  showYearDropdown
                />
              </div>

              <div className="form-group">
                <label className="form-label">Height (cm)</label>
                <input
                  type="number"
                  className="form-input"
                  value={formData.height || ''}
                  onChange={(e) => handleChange('height', parseFloat(e.target.value) || null)}
                />
              </div>

              <div className="form-group">
                <label className="form-label">Weight (kg)</label>
                <input
                  type="number"
                  className="form-input"
                  value={formData.weight || ''}
                  onChange={(e) => handleChange('weight', parseFloat(e.target.value) || null)}
                />
              </div>

              <button type="button" className="btn btn-primary" onClick={() => setStep(2)}>
                Next
              </button>
            </div>
          )}

          {step === 2 && (
            <div className="onboarding-step">
              <h2>Cycle Information</h2>
              <div className="form-group">
                <label className="form-label">Average Cycle Length (days)</label>
                <input
                  type="number"
                  className="form-input"
                  value={formData.averageCycleLength || ''}
                  onChange={(e) => handleChange('averageCycleLength', parseInt(e.target.value) || null)}
                  placeholder="Usually 21-35 days"
                />
              </div>

              <div className="form-group">
                <label className="form-label">Average Period Length (days)</label>
                <input
                  type="number"
                  className="form-input"
                  value={formData.averagePeriodLength || ''}
                  onChange={(e) => handleChange('averagePeriodLength', parseInt(e.target.value) || null)}
                  placeholder="Usually 3-7 days"
                />
              </div>

              <div className="form-group">
                <label className="form-label">Activity Level</label>
                <select
                  className="form-input"
                  value={formData.activityLevel}
                  onChange={(e) => handleChange('activityLevel', e.target.value)}
                >
                  <option value="">Select...</option>
                  <option value="SEDENTARY">Sedentary</option>
                  <option value="MODERATE">Moderate</option>
                  <option value="ACTIVE">Active</option>
                </select>
              </div>

              <div className="button-group">
                <button type="button" className="btn btn-secondary" onClick={() => setStep(1)}>
                  Back
                </button>
                <button type="button" className="btn btn-primary" onClick={() => setStep(3)}>
                  Next
                </button>
              </div>
            </div>
          )}

          {step === 3 && (
            <div className="onboarding-step">
              <h2>Health & Preferences</h2>
              <div className="form-group">
                <label className="form-label">Diet Type</label>
                <select
                  className="form-input"
                  value={formData.dietType}
                  onChange={(e) => handleChange('dietType', e.target.value)}
                >
                  <option value="">Select...</option>
                  <option value="VEGETARIAN">Vegetarian</option>
                  <option value="VEGAN">Vegan</option>
                  <option value="OMNIVORE">Omnivore</option>
                </select>
              </div>

              <div className="consent-section">
                <h3>Medical Disclaimer</h3>
                <p className="disclaimer-text">
                  RedDot is a wellness tracking tool and is not a substitute for professional medical advice. 
                  Always consult with healthcare providers for medical concerns.
                </p>
                <label className="checkbox-label">
                  <input
                    type="checkbox"
                    checked={formData.consentGiven}
                    onChange={(e) => handleChange('consentGiven', e.target.checked)}
                    required
                  />
                  <span>I understand and agree to the terms</span>
                </label>
              </div>

              <div className="button-group">
                <button type="button" className="btn btn-secondary" onClick={() => setStep(2)}>
                  Back
                </button>
                <button type="submit" className="btn btn-primary">
                  Complete Setup
                </button>
              </div>
            </div>
          )}
        </form>
      </div>
    </div>
  );
};

export default Onboarding;

