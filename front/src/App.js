import QuestionList from "./pages/QuestionList";
import "./css/input.css";
import ReviewList from "./pages/ReviewList";
import ProductWrite from "./pages/ProductWrite";
import QuestionWrite from "./pages/QuestionWrite";
import ProductList from "./pages/ProductList";
import ProductUpdate from "./pages/ProductUpdate";
import React from "react";
import "./App.css";
import "../src/css/input.css";
import Login from "./components/Login";
import SignUp from "./components/SignUp";
//import ShoppingCart from './pages/ShoppingCart';
import OAuth from "./components/OAuth";
import UserProfile from "./components/UserProfile";
import ViewProfile from "./components/ViewProfile";
import Header from "./components/Header";
import Footer from "./components/Footer";
import Main from "./pages/Main";
import About from "./pages/About";
import Announcement from "./pages/Announcement";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import ProductDetail from "./pages/ProductDetail";
import ShoppingCart from "./pages/ShoppingCart";

const App = () => {
  return (
    <Router>
      <div>
        <Routes>
          <Route path="/" element={<Main />} />
          <Route path="/SignUp" element={<SignUp />} />
          <Route path="/Login" element={<Login />} />
          <Route path="/About" element={<About />} />
          <Route path="/Announcement" element={<Announcement />} />
          <Route path="/questionlist" element={<QuestionList />} />
          <Route path="/reviewlist" element={<ReviewList />} />
          <Route path="/productwrite" element={<ProductWrite />} />
          <Route path="/questionwrite" element={<QuestionWrite />} />
          <Route path="/productlist" element={<ProductList />} />
          <Route path="/productupdate" element={<ProductUpdate />} />
          <Route path="/productdetail/:itemid" element={<ProductDetail />} />
          <Route path="/shoppingcart" element={<ShoppingCart />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;
