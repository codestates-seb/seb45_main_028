import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
/*  axios로 api 주소 받아오는 로직
import axios from 'axios'*/


const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleLogin = () => {
    // 로그인 처리 로직 추가
  };

  const handleSignUp = () => {
    navigate('/signup');
  };

/*
  const login = async (username, password) => {
  try {
    const response = await axios.post('API', {
      username: username,
      password: password,
    });
  } catch (error) {
    console.error('로그인 실패:', error);
  }
};

const handleLogin = async () => {
  try {
    await login(username, password);
    // 로그인 성공 시에 필요한 처리를 추가하세요.
  } catch (error) {
    // 로그인 실패 시에 필요한 처리를 추가하세요.
  }
};

*/

  return (
    <div className="flex justify-center items-center h-screen">
      <div className="bg-white p-8 rounded shadow-md w-80">
        <h1 className="text-xl font-semibold mb-4">로그인</h1>
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
        <p className="mt-4">
          아직 계정이 없으신가요?{' '}
          <Link to="/signup" className="text-blue-500 hover:underline p-0">
          <button className="text-blue-500 hover:underline p-0">
          회원가입
          </button>
          </Link>
        </p>
      </div>
    </div>
  );
};

export default Login;
