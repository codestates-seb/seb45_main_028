import React from 'react';
import './App.css';
import '../src/css/input.css';
import Login from './components/Login';
import SignUp from './components/Signup';
//import ShoppingCart from './pages/ShoppingCart';
import OAuth from './components/OAuth'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

const App = () => {
  return (
    <Router>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/" element={<OAuth/>}  />
          <Route path="/signup" element={<SignUp />} />
        </Routes> 
    </Router>
  );
};

export default App;