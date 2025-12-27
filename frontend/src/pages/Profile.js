import React, { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import Navbar from '../components/Navbar';
import { toast } from 'react-toastify';
import './Profile.css';

const Profile = () => {
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);
  const [editing, setEditing] = useState(false);
  const [formData, setFormData] = useState({});

  const fetchProfile = useCallback(async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/profile');
      setProfile(response.data);
      setFormData(response.data);
    } catch (error) {
      console.error('Failed to fetch profile:', error);
      toast.error('Failed to load profile');
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchProfile();
  }, [fetchProfile]);

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      await axios.put('http://localhost:8080/api/profile', formData);
      toast.success('Profile updated successfully!');
      setEditing(false);
      fetchProfile();
    } catch (error) {
      toast.error('Failed to update profile');
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
          <h1>Profile</h1>
          <button
            className="btn btn-secondary"
            onClick={() => setEditing(!editing)}
          >
            {editing ? 'Cancel' : 'Edit'}
          </button>
        </div>

        <div className="card profile-card">
          {editing ? (
            <form onSubmit={handleUpdate}>
              <div className="form-group">
                <label className="form-label">First Name</label>
                <input
                  type="text"
                  className="form-input"
                  value={formData.firstName || ''}
                  onChange={(e) => setFormData({ ...formData, firstName: e.target.value })}
                />
              </div>

              <div className="form-group">
                <label className="form-label">Last Name</label>
                <input
                  type="text"
                  className="form-input"
                  value={formData.lastName || ''}
                  onChange={(e) => setFormData({ ...formData, lastName: e.target.value })}
                />
              </div>

              <div className="form-group">
                <label className="form-label">Email</label>
                <input
                  type="email"
                  className="form-input"
                  value={formData.email || ''}
                  disabled
                />
              </div>

              <div className="form-group">
                <label className="form-label">Height (cm)</label>
                <input
                  type="number"
                  className="form-input"
                  value={formData.height || ''}
                  onChange={(e) => setFormData({ ...formData, height: parseFloat(e.target.value) || null })}
                />
              </div>

              <div className="form-group">
                <label className="form-label">Weight (kg)</label>
                <input
                  type="number"
                  className="form-input"
                  value={formData.weight || ''}
                  onChange={(e) => setFormData({ ...formData, weight: parseFloat(e.target.value) || null })}
                />
              </div>

              <div className="form-group">
                <label className="form-label">Average Cycle Length (days)</label>
                <input
                  type="number"
                  className="form-input"
                  value={formData.averageCycleLength || ''}
                  onChange={(e) => setFormData({ ...formData, averageCycleLength: parseInt(e.target.value) || null })}
                />
              </div>

              <button type="submit" className="btn btn-primary">Save Changes</button>
            </form>
          ) : (
            <div className="profile-info">
              <div className="profile-item">
                <span className="profile-label">Name:</span>
                <span className="profile-value">{profile?.firstName} {profile?.lastName}</span>
              </div>
              <div className="profile-item">
                <span className="profile-label">Email:</span>
                <span className="profile-value">{profile?.email}</span>
              </div>
              <div className="profile-item">
                <span className="profile-label">Height:</span>
                <span className="profile-value">{profile?.height ? `${profile.height} cm` : 'Not set'}</span>
              </div>
              <div className="profile-item">
                <span className="profile-label">Weight:</span>
                <span className="profile-value">{profile?.weight ? `${profile.weight} kg` : 'Not set'}</span>
              </div>
              <div className="profile-item">
                <span className="profile-label">Average Cycle Length:</span>
                <span className="profile-value">{profile?.averageCycleLength ? `${profile.averageCycleLength} days` : 'Not set'}</span>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Profile;

