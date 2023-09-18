import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

function EditProfile({ user }) {
  const [name, setName] = useState(user.name);
  const [address, setAddress] = useState(user.address);

  const handleNameChange = (e) => {
    setName(e.target.value);
  };

  const handleAddressChange = (e) => {
    setAddress(e.target.value);
  };

  const handleSave = () => {
    // 업데이트할 사용자 정보 객체 생성
    const updatedData = {
      name: name,
      address: address,
    };

    // Axios를 사용하여 API로 업데이트 요청 보내기
    axios.put('API_UPDATE_URL_HERE', updatedData)
      .then((response) => {
        console.log('업데이트 성공:', response.data);
        // 업데이트가 성공하면 홈페이지로 이동하거나 다른 작업을 수행할 수 있습니다.
      })
      .catch((error) => {
        console.error('API 업데이트 오류:', error);
        // 업데이트 오류 처리를 여기에 추가할 수 있습니다.
      });
  };

  return (
    <div>
      <h2>프로필 수정</h2>
      <div>
        <label>이름:</label>
        <input type="text" value={name} onChange={handleNameChange} />
      </div>
      <div>
        <label>주소:</label>
        <input type="text" value={address} onChange={handleAddressChange} />
      </div>
      <button onClick={handleSave}>저장</button>
      <Link to="/">뒤로 가기</Link>
    </div>
  );
}

export default EditProfile;
