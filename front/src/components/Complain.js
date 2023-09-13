import React, { useState } from "react";
import Modal from "./Modal";
import axios from "axios";
import { navigate } from "react-router-dom";

const Complain = ({ question }) => {
  const [isOpen, setIsOpen] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const canEdit = isLoggedIn;

  const handleLogin = () => {
    setIsLoggedIn(true);
  };

  const saveEdit = async => {
   axios
      .pacth(
        "http://ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com:8080/complain/{complainId}"
      )
      .then((res) => {
        alert("수정되었습니다.");
        navigate("/questionlist");
      });
  };

  const hadleDelete = (complainId) => {
    axios
      .delete(
        "http://ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com:8080/complain/{complainId}"
      )
      .then((res) => {
        alert("삭제되었습니다.");
        navigate("/questionlist");
      });
  };

  return (
    <div>
      <div className="flex justify-end">
        {canEdit ? (
          <button className="p-2 text-[13px]" onClick={handleLogin}>
            수정
          </button>
        ) : null}

        <button
          className=" text-[13px]"
          onClick={() => hadleDelete(question.complainId)}
        >
          삭제
        </button>
      </div>
      <div
        className="flex items-center h-[50px] w-[900px] border border-gray-300 border-solid rounded bg-white focus:outline-1 focus:ring focus:outline-gray-200 relative" // relative 위치 설정
        type="text"
      >
        <span className="pl-8">{/* [{question.memberId}] */}</span>
        <span>{/* {question.itemId} */}</span>
        <span className="text-center flex-grow">{/* {question.title} */}</span>

        <button
          className="absolute right-0 mr-7 p-2 bg-gray-100 border border-gray-300 border-solid rounded text-[14px] hover:bg-gray-200" // 버튼 위치 설정
          onClick={() => setIsOpen(!isOpen)}
        >
          내용보기
        </button>
        {isOpen && <Modal closeModal={() => setIsOpen(!isOpen)}></Modal>}
      </div>
    </div>
  );
};

export default Complain;
