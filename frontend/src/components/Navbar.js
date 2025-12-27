import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import './Navbar.css';

const Navbar = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="navbar">
      <div className="navbar-container">
        <Link to="/dashboard" className="navbar-brand">
          <span className="brand-icon">🔴</span>
          RedDot
        </Link>
        
        <div className="navbar-menu">
          <Link to="/dashboard" className="nav-link">Dashboard</Link>
          <Link to="/periods" className="nav-link">Periods</Link>
          <Link to="/analytics" className="nav-link">Analytics</Link>
          <Link to="/wellness" className="nav-link">Wellness</Link>
          <Link to="/profile" className="nav-link">Profile</Link>
          {user && (
            <div className="nav-user">
              <span>{user.firstName}</span>
              <button onClick={handleLogout} className="btn-logout">Logout</button>
            </div>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;

