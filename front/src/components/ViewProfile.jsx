import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

function ViewProfile() {
  const [user, setUser] = useState(null);
  const [isFetching, setIsFetching] = useState(false);

  useEffect(() => {
    if (!isFetching) {
      setIsFetching(true);
      // API로부터 사용자 정보 가져오기
      axios
        .get('http://ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com:8080/member/myPage')
        .then((response) => {
          setUser(response.data);
          setIsFetching(false);
        })
        .catch((error) => {
          console.error('API 오류:', error);
          setIsFetching(false);
        });
    }
  }, [isFetching]);

  const handleFetchUserInfo = () => {
    setIsFetching(true);
  };

  if (!user) {
    return (
      <div>
        <p>Loading...</p>
        <button onClick={handleFetchUserInfo}>회원 정보 조회</button>
        <Link to="/edit-profile">프로필 수정</Link>
      </div>
    );
  }

  return (
    <div>
      <h2>회원 정보 조회</h2>
      <p>이름: {user.name}</p>
      <p>주소: {user.address}</p>
      <button onClick={handleFetchUserInfo}>회원 정보 다시 조회</button>
      <Link to="/edit-profile">프로필 수정</Link>
    </div>
  );
}

export default ViewProfile;
