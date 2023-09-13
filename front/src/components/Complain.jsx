import React, { useState } from "react";
import Modal from "./Modal";

const Complain = ({question}) => {
  const [isOpen, setIsOpen] = useState(false);
  return (
    <div>
       <div className="flex justify-end">
          <button className="p-2 text-[13px]">수정</button>
          <button className=" text-[13px]">삭제</button>
        </div>
      <div
        className="flex items-center h-[50px] w-[900px] border border-gray-300 border-solid rounded bg-white focus:outline-1 focus:ring focus:outline-gray-200 relative" // relative 위치 설정
        type="text"
      >
        <span className="pl-8">
          {/* [{question.memberId}] */}
          </span>
        <span>
          {/* {question.itemId} */}
        </span>
        <span className="text-center flex-grow">
        {/* {question.title} */}
        </span>
        
        <button
          className="absolute right-0 mr-7 p-2 bg-gray-100 border border-gray-300 border-solid rounded text-[14px] hover:bg-gray-200" // 버튼 위치 설정
          onClick={() => setIsOpen(!isOpen)}
        >
          내용보기
          {/* {question.contents} */}
        </button>
        {isOpen && <Modal closeModal={() => setIsOpen(!isOpen)}></Modal>}
      </div>
     
    </div>
  );
};

export default Complain;