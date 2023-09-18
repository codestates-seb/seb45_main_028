import React, { useState } from 'react';
import { Link } from 'react-router-dom';
function Header() {
  const [shopDropdownOpen, setShopDropdownOpen] = useState(false);
  const [communityDropdownOpen, setCommunityDropdownOpen] = useState(false);

  const toggleShopDropdown = () => {
    setShopDropdownOpen(!shopDropdownOpen);
  };

  const toggleCommunityDropdown = () => {
    setCommunityDropdownOpen(!communityDropdownOpen);
  };

  return (
    <header className="bg-white py-4 sticky top-0 left-0 right-0  z-10">
      <div className="container mx-auto flex justify-between  items-center">
        <Link to ='/Main' >
          <img style={{ width: '70px', height: '70px', cursor: 'pointer'}} src='/img/logo.png' alt="logo"></img>
        </Link>
        <nav>
          <ul className="flex space-x-32 mr-40 text-center">
          <Link to="/About">
            <li className="text-black cursor-pointer hover:text-red-700">
              About
            </li>
          </Link>
            <li className="text-black relative" onMouseEnter={toggleShopDropdown} onMouseLeave={toggleShopDropdown}>
              <span className="cursor-pointer hover:text-red-700">Shop</span>
              {shopDropdownOpen && (
                <ul className="absolute h-50 top-full bg-white shadow-lg py-2">
                  <li className="px-4 py-5 hover:bg-gray-100 hover:text-red-700">
                    <a href="#">Best</a>
                  </li>
                </ul>
              )}
            </li>
            <li className="text-black relative" onMouseEnter={toggleCommunityDropdown} onMouseLeave={toggleCommunityDropdown}>
              <Link to="/Announcement">
              <span className="cursor-pointer hover:text-red-700">Community</span>
              </Link>
              {communityDropdownOpen && (
                <ul className="absolute h-50 top-full  bg-white shadow-lg py-2 text-center">
                  <Link to="/Announcement">
                  <li className="px-2 py-4 hover:bg-gray-100 hover:text-red-700">
                    <a>공지사항</a>
                  </li>
                  </Link>
                  <li className="px-2 py-4 hover:bg-gray-100 hover:text-red-700 ">
                    <a>Q&A</a>
                  </li>
                  <li className="px-2 py-4 hover:bg-gray-100 hover:text-red-700">
                    <a>1:1문의</a>
                  </li>
                </ul>
              )}
            </li>
            <li className="text-black cursor-pointer hover:text-red-700">MyPage</li>
            <li className="text-black cursor-pointer hover:text-red-700">Cart</li>
            <li className="text-black cursor-pointer hover:text-red-700">Order</li>
          </ul>
        </nav>
        <h1 className="text-black cursor-pointer hover:text-red-700">Login</h1>
        <h1 className="text-black cursor-pointer hover:text-red-700">Logout</h1>
      </div>
    </header>
  );
}

export default Header;
