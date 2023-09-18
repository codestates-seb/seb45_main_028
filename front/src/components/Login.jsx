import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import CatImg from './Catt.png'

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

  const handleLogin = async () => {
    try {
      // 로그인 API 호출
      const response = await axios.post('http://ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com:8080/member/login', {
        email: username,
        password: password,
      });

      const token = response.data.token;

      // 토큰을 로컬 스토리지에 저장
      localStorage.setItem('token', token);

      // 로그인 성공 시 이동할 페이지로 네비게이션
      navigate('/Main');
      
      // 로그인 상태 업데이트
      setIsLoggedIn(true);
    } catch (error) {
      // 로그인 실패 시 처리
      console.error('로그인 실패:', error);
      setIsLoggedIn(false);
    }
  };

  useEffect(() => {
    // 페이지가 로드될 때, 이전에 로그인한 상태인지 확인하고 필요한 경우 API 요청을 보내 인증을 유지하실 수 있습니다.
    const token = localStorage.getItem('token');
    if (token) {
      // 이전에 로그인한 사용자임을 서버에게 알리는 요청을 보낼 수 있습니다.
      const axiosInstance = axios.create({
        baseURL: 'http://ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com:8080',
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });

      axiosInstance.get('/check-auth')
        .then(response => {
          // 인증이 유효하면 setIsLoggedIn(true) 등을 호출하여 사용자를 로그인 상태로 설정할 수 있습니다.
        })
        .catch(error => {
          // 인증이 실패하면 로컬 스토리지의 토큰을 삭제하고 setIsLoggedIn(false) 등을 호출하여 사용자를 로그아웃 상태로 설정할 수 있습니다.
          localStorage.removeItem('token');
          setIsLoggedIn(false);
        });
    }
  }, []);

  return (
    <div className="min-h-screen flex justify-center items-center"
      style={{
        backgroundImage: `url(${CatImg})`, // Ponyoimg를 배경 이미지로 설정
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover', // 필요에 따라 배경 이미지 크기를 조정합니다.
      }}
      >
      <div className="bg-white p-8 rounded-lg shadow-md w-80">
        <h1 className="text-3xl font-semibold text-center mb-4">로그인</h1>
        <div className="mb-4">
          <input
            className="w-full p-3 rounded border border-gray-300 focus:border-blue-500"
            type="text"
            placeholder="사용자 이름 또는 이메일"
            value={username}
            onChange={handleUsernameChange}
          />
        </div>
        <div className="mb-6">
          <input
            className="w-full p-3 rounded border border-gray-300 focus:border-blue-500"
            type="password"
            placeholder="비밀번호"
            value={password}
            onChange={handlePasswordChange}
          />
        </div>
        <button
          className="w-full bg-indigo-500 text-white p-3 rounded hover:bg-pink-500 transition duration-300"
          onClick={handleLogin}
        >
          로그인
        </button>
        <p className="mt-4 text-center text-gray-600">
          아직 계정이 없으신가요?{' '}
          <Link to="/signup" className="text-pink-500 hover:underline">
            가입하기
          </Link>
        </p>
      </div>
    </div>
  );
};

export default Login;
