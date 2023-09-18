import React from 'react';
import { Link } from 'react-router-dom';

function UserProfile() {
  return (
    <div className="bg-white rounded-lg shadow-md p-6">
      <div className="text-center">
        <img
          src="https://www.gstatic.com/webp/gallery/2.jpg" // 여기에 사용자 프로필 이미지 URL을 추가하세요
          alt="User Profile"
          className="w-24 h-24 rounded-full mx-auto mb-4"
        />
        <h2 className="text-3xl font-semibold">John Doe</h2>
      </div>
        
        
        <Link
          to="/EditProfile"
          className="bg-black hover:bg-pink-600 text-white py-2 px-4 rounded-full font-semibold transition duration-300"
        >
          프로필 수정
        </Link>
      </div>
    
  );
}

export default UserProfile;
