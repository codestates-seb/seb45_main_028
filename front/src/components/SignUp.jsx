import React, { useState } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { Link } from 'react-router-dom';

const SignUp = () => {
  //const [username, setUsername] = useState('');
  const [userId, setUserId] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');
 // const [birthdate, setBirthdate] = useState(null);
  const [name, setName] = useState('');
  const [phone, setPhone] = useState('');
  const [address, setAddress] = useState('');
  const [error, setError] = useState('');
  const apiAddress = 'http://ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com:8080/member/new'; // API 주소

/*  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };*/

  const handleUserIdChange = (e) => {
    setUserId(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handlePasswordConfirmChange = (e) => {
    setPasswordConfirm(e.target.value);
  };

 /* const handleBirthdateChange = (date) => {
    setBirthdate(date);
  };
*/

  const handleNameChange = (e) => {
    setName(e.target.value);
  }

  const handlePhoneChange = (e) => {
    setPhone(e.target.value);
  }

  const handleAddressChange = (e) => {
    setAddress(e.target.value);
  }

  const handleSignUp = () => {
    if (!userId || !password || !passwordConfirm || !name || !phone || !address) {
      setError('모든 필드를 입력해주세요.');
      return;
    }

    if (password !== passwordConfirm) {
      setError('비밀번호가 일치하지 않습니다.');
      return;
    }

    const requestData = {    // 프론트엔드 변수명 변경
      email: userId,      // 프론트엔드 변수명 변경
      password: password,   // 프론트엔드 변수명 변경
      name: name,           // 프론트엔드 변수명 변경
      phone: phone,         // 프론트엔드 변수명 변경
      address: address,     // 프론트엔드 변수명 변경
    };
    // API를 호출하여 회원가입 처리
    fetch(apiAddress, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(requestData),
    })

      .then((response) => {
        if (!response.ok) {
          throw new Error('API 호출에 실패했습니다.');
        }
        // API 호출이 성공하면 원하는 동작 수행 (예: 페이지 이동)
      })
      .catch((error) => {
        setError('회원가입에 실패했습니다.');
        console.error(error);
      });
  };

  return (
    <div className="flex justify-center items-center h-screen">
      <div className="bg-white p-8 rounded shadow-md w-80">
        <h1 className="text-xl font-semibold mb-4">회원가입</h1>
        {error && <p className="text-red-500 mb-4">{error}</p>}
        <div className="mb-4">
          
        </div>
        <div className="mb-4">
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

        <div className="mb-4">
          <label className="block text-sm font-medium mb-1" htmlFor="signup-name">
            이름:
          </label>
          <input
            className="w-full border rounded py-2 px-3 bg-white"
            type="text"
            id="signup-name"
            value={name}
            onChange={handleNameChange}
          />
        </div>
        <div className="mb-4">
          <label className="block text-sm font-medium mb-1" htmlFor="signup-phone">
            전화번호:
          </label>
          <input
            className="w-full border rounded py-2 px-3 bg-white"
            type="text"
            id="signup-phone"
            value={phone}
            onChange={handlePhoneChange}
          />
        </div>
        <div className="mb-4">
          <label className="block text-sm font-medium mb-1" htmlFor="signup-address">
            주소:
          </label>
          <input
            className="w-full border rounded py-2 px-3 bg-white"
            type="text"
            id="signup-address"
            value={address}
            onChange={handleAddressChange}
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