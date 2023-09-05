import React from 'react';
import './App.css';
import '../src/css/input.css';
import Login from './components/Login';
import SignUp from './components/SignUp';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';

const App = () => {
  return (
    <Router>
    

        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
        </Routes> 
  
  
    </Router>
  );
};

export default App;