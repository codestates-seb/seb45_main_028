import './App.css';
import '../src/css/input.css';
import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Header from './Components/Header.jsx';
import Footer from './Components/Footer.jsx';
import Main from './Pages/Main.jsx';
import About from './Pages/About.jsx';
import Annoucement from './Pages/Announcement.jsx';

function App() {
  return (
    <BrowserRouter>
      <div>
        <Header />
        <Routes>
          <Route path="/Main" element={<Main />} />
          <Route path="/About" element={<About />} />
          <Route path="/Announcement" element={<Annoucement />} />
        </Routes>
        <Footer />
      </div>
    </BrowserRouter>
  );
}

export default App;
