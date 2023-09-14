
import QuestionList from "./pages/QuestionList";
import "./css/input.css";
import ReviewList from "./pages/ReviewList";
import ProductWrite from "./pages/ProductWrite";
import QuestionWrite from "./pages/QuestionWrite";
import ProductList from "./pages/ProductList";
import ProductUpdate from "./pages/ProductUpdate";
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
      <div className="App">
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/login" element={<SignUp />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;

/*<Route path="/questionlist" element={<QuestionList />} />
<Route path="/reviewlist" element={<ReviewList />} />
<Route path="/productwrite" element={<ProductWrite />} />
<Route path="/questionwrite" element={<QuestionWrite />} />
<Route path="/productlist" element={<ProductList />} />
<Route path="/productupdate" element={<ProductUpdate />} />*/