import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
/*import jwt from 'jsonwebtoken';*/
import axios from 'axios';



const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const navigate = useNavigate();

  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };


  const handleSignUp = () => {
    navigate('/signup');
  };


  const getLogin = async (email, password) => {
    try {
      const response = await axios.post('http://ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com:8080/member/login', {
        email: email,
        password: password,
      },
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`, // JWT 토큰을 헤더에 추가합니다.
        },
      });

      console.log(response.data);
      return response.data; // 백엔드에서 반환된 데이터를 반환합니다.
    } catch (error) {
      console.error('로그인 실패:', error);
      throw error; // 에러를 다시 던집니다.
    }
  };
  

const handleLogin = async () => {
  try {
    const response = await getLogin(username, password);
    const token = response.token;

    localStorage.setItem('token', token);

    console.log(response);
    // 로그인 성공 후의 처리를 여기에 추가하세요
    setIsLoggedIn(true);
  } catch (error) {
    // 로그인 실패 후의 처리를 여기에 추가하세요
    setIsLoggedIn(false);
  }
};


const handleLogout = () => {
  // 클라이언트에서 JWT 토큰을 삭제합니다.
  localStorage.removeItem('token');
  setIsLoggedIn(false);
};

return (
  <div className="flex justify-center items-center h-screen">
    <div className="bg-white p-8 rounded shadow-md w-80">
      <h1 className="text-xl font-semibold mb-4">로그인</h1>
      {isLoggedIn ? (
        <div>
          <p className="mb-2">로그인 되었습니다.</p>
          <button
            className="w-full bg-red-500 text-white py-2 px-4 rounded hover:bg-red-600 border-solid border-black"
            onClick={handleLogout}
          >
            로그아웃
          </button>
        </div>
      ) : (
        <div>
          <div className="mb-4">
            <label className="block text-sm font-medium mb-1" htmlFor="username">ID: </label>
            <input
              className="w-full border rounded py-2 px-3 bg-white"
              type="text"
              id="username"
              value={username}
              onChange={handleUsernameChange}
            />
          </div>
          <div className="mb-4">
            <label className="block text-sm font-medium mb-1" htmlFor="password">Password: </label>
            <input
              className="w-full border rounded py-2 px-3"
              type="password"
              id="password"
              value={password}
              onChange={handlePasswordChange}
            />
          </div>
          <button
            className="w-full bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600 border-solid border-black"
            onClick={handleLogin}
          >
            로그인
          </button>
        </div>
      )}
      <p className="mt-4">
        {isLoggedIn ? (
          <Link to="/" className="text-blue-500 hover:underline">
            홈으로 이동
          </Link>
        ) : (
          <>
            아직 계정이 없으신가요?{' '}
            <Link to="/signup" className="text-blue-500 hover:underline">
              회원가입
            </Link>
          </>
        )}
      </p>
    </div>
  </div>
);
};

export default Login;