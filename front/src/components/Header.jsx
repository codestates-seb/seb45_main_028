import React, { useState } from 'react';
import { NavLink ,Link } from 'react-router-dom';

function Header() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [shopDropdownOpen, setShopDropdownOpen] = useState(false);
  const [communityDropdownOpen, setCommunityDropdownOpen] = useState(false);

  const toggleShopDropdown = () => {
    setShopDropdownOpen(!shopDropdownOpen);
  };

  const toggleCommunityDropdown = () => {
    setCommunityDropdownOpen(!communityDropdownOpen);
  };


  const handleLogout = () => {
    // 클라이언트에서 JWT 토큰을 삭제합니다.
    localStorage.removeItem('token');
    
    // 사용자 로그인 상태를 업데이트합니다.
    setIsLoggedIn(false);
    
    // 로그아웃 후 홈페이지로 리디렉션 또는 다른 작업을 수행할 수 있습니다.
    // 예: 홈페이지로 리디렉션
    window.location.href = '/';
  };


  return (
    <header className="bg-white py-4 fixed top-0 left-0 right-0 z-10">
      <div className="container mx-auto flex justify-between items-center">
        <Link to="/Main">
          <img style={{ width: '70px', height: '70px', cursor: 'pointer'}} src='/img/logo.png' alt="logo"></img>
        </Link>
        <nav>
          <ul className="flex space-x-32 mr-40 text-center">
          <NavLink
            className={({ isActive}) =>
                isActive ? 'text-orange-400 cursor-pointer' : 'text-black cursor-pointer hover:text-orange-400'
          }  to="/About">  
              About
          </NavLink>
          <NavLink
            className={({ isActive}) =>
                isActive ? 'text-orange-400 cursor-pointer' : 'text-black cursor-pointer hover:text-orange-400'
          } onMouseEnter={toggleShopDropdown} onMouseLeave={toggleShopDropdown} to="/ProductList"> 
              {shopDropdownOpen && (
                <ul className="absolute h-50 bg-white shadow-lg py-2">
                  <li className="px-4 py-5 hover:bg-gray-100 hover:text-orange-400">
                    <a href="#">Best</a>
                  </li>
                </ul>
              )}
            </NavLink>
            <NavLink
            className={({ isActive}) =>
                isActive ? 'text-orange-400 cursor-pointer' : 'text-black cursor-pointer hover:text-orange-400'
          } onMouseEnter={toggleCommunityDropdown} onMouseLeave={toggleCommunityDropdown} to="/Announcement"> 
              Community
            {/* <li className="text-black relative" onMouseEnter={toggleCommunityDropdown} onMouseLeave={toggleCommunityDropdown}> */}
              {/* <Link to="/Announcement">
              <span className="cursor-pointer hover:text-orange-400">Community</span>
              </Link> */}
            </li>
            <li className="text-black relative" onMouseEnter={toggleCommunityDropdown} onMouseLeave={toggleCommunityDropdown}>
              <Link to="/Announcement">
                <span className="cursor-pointer hover:text-red-700">Community</span>
              </Link>
              {communityDropdownOpen && (
                <ul className="absolute h-50  bg-white shadow-lg py-2 text-center">
                  <Link to="/Announcement">
                  <li className="px-2 py-4">
                    <a>공지사항</a>
                  </li>
                  </Link>
                  <Link to="/QuestionList">
                  <li className="px-2 py-4">
                    <a>Q&A</a>
                  </li>
                  </Link>
                  <Link to="/QuestionWrite">
                  <li className="px-2 py-4">
                    <a>1:1문의</a>
                  </li>
                  </Link>
                </ul>
              )}
            </NavLink>
            <NavLink
            className={({ isActive}) =>
                isActive ? 'text-orange-400 cursor-pointer' : 'text-black cursor-pointer hover:text-orange-400'
          }  to="/ShoppingCart">  
              Cart
            </NavLink>
            <li className="text-black cursor-pointer hover:text-orange-400">Order</li>
          </ul>
        </nav>
        <h1 className="text-black cursor-pointer hover:text-red-700">MyPage</h1>        
        <h1 className="text-black cursor-pointer hover:text-red-700" onClick={handleLogout}>Logout</h1>
      </div>
    </header>
  );
}

export default Header;
