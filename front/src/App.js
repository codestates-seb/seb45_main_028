import React from 'react';
import './App.css';
import '../src/css/input.css';
import Login from './components/Login';
import SignUp from './components/Signup';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

const App = () => {
  return (
    <Router>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
        </Routes> 
    </Router>
  );
};

export default App;