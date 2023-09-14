import React, { useState } from 'react';
import { GoogleLogin } from '@react-oauth/google';

const GoogleOAuthLogin = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const responseGoogle = (response) => {
    // Google 로그인 성공 시 실행되는 콜백 함수
    console.log('Google 로그인 성공:', response);
    setIsLoggedIn(true);
  };

  const responseErrorGoogle = (error) => {
    // Google 로그인 실패 시 실행되는 콜백 함수
    console.error('Google 로그인 에러:', error);
    setIsLoggedIn(false);
  };

  return (
    <div>
      {isLoggedIn ? (
        <div>
          <p>로그인 되었습니다.</p>
        </div>
      ) : (
        <div>
          <h1>Google 로그인</h1>
          <GoogleLogin
            clientId="YOUR_CLIENT_ID" // Google API Console에서 생성한 클라이언트 ID로 교체
            buttonText="Google로 로그인"
            onSuccess={responseGoogle}
            onFailure={responseErrorGoogle}
            cookiePolicy={'single_host_origin'}
          />
        </div>
      )}
    </div>
  );
};

export default GoogleOAuthLogin;
