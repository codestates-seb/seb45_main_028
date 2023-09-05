import React, { useState } from 'react';
import DatePicker from 'react-datepicker'; // DatePicker 컴포넌트를 사용하기 위한 라이브러리
import 'react-datepicker/dist/react-datepicker.css'; // DatePicker 스타일 추가
import { Link } from 'react-router-dom';

const SignUp = () => {
  const [username, setUsername] = useState('');
  const [userId, setUserId] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [birthdate, setBirthdate] = useState(null); // 생년월일 추가
  const [error, setError] = useState('');

  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };

  const handleUserIdChange = (e) => {
    setUserId(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handlePasswordConfirmChange = (e) => {
    setPasswordConfirm(e.target.value);
  };

  const handleBirthdateChange = (date) => {
    setBirthdate(date);
  };

  const handleSignUp = () => {
    // 입력 유효성 검사
    if (!username || !userId || !password || !passwordConfirm || !birthdate) {
      setError('모든 필드를 입력해주세요.');
      return;
    }

    if (password !== passwordConfirm) {
      setError('비밀번호가 일치하지 않습니다.');
      return;
    }

    // 회원가입 로직을 수행하면 됩니다.
  };

  return (
    <div className="flex justify-center items-center h-screen">
      <div className="bg-white p-8 rounded shadow-md w-80">
        <h1 className="text-xl font-semibold mb-4">회원가입</h1>
        {error && <p className="text-red-500 mb-4">{error}</p>}
        <div className="mb-4">
          <label className="block text-sm font-medium mb-1" htmlFor="signup-username">
            사용자 이름:
          </label>
          <input
            className="w-full border rounded py-2 px-3 bg-white"
            type="text"
            id="signup-username"
            value={username}
            onChange={handleUsernameChange}
          />
        </div>
        <div className="mb-4">
          <label className="block text-sm font-medium mb-1" htmlFor="signup-birthdate">
            생년월일:
          </label>
          <DatePicker
            className="w-full border rounded py-2 px-3"
            selected={birthdate}
            onChange={handleBirthdateChange}
            dateFormat="yyyy-MM-dd"
            id="signup-birthdate"
          />
        </div>
        <div className="mb-4">
          <label className="block text-sm font-medium mb-1" htmlFor="signup-userId">
            사용자 ID:
          </label>
          <input
            className="w-full border rounded py-2 px-3 bg-white"
            type="text"
            id="signup-userId"
            value={userId}
            onChange={handleUserIdChange}
          />
        </div>
        <div className="mb-4">
          <label className="block text-sm font-medium mb-1" htmlFor="signup-password">
            비밀번호:
          </label>
          <input
            className="w-full border rounded py-2 px-3"
            type="password"
            id="signup-password"
            value={password}
            onChange={handlePasswordChange}
          />
        </div>
        <div className="mb-4">
          <label className="block text-sm font-medium mb-1" htmlFor="signup-password-confirm">
            비밀번호 확인:
          </label>
          <input
            className="w-full border rounded py-2 px-3"
            type="password"
            id="signup-password-confirm"
            value={passwordConfirm}
            onChange={handlePasswordConfirmChange}
          />
        </div>
        
        <button
          className="w-full bg-green-500 text-white py-2 px-4 rounded hover:bg-green-600 border-solid border-black"
          onClick={handleSignUp}
        >
          회원가입
        </button>
        <p className="mt-4">
          이미 계정이 있으신가요?{' '}
          <Link to="/login" className="text-blue-500 hover:underline p-0">
            로그인
          </Link>
        </p>
      </div>
    </div>
  );
};

export default SignUp;
