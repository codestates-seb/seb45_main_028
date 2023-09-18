import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Header from "../components/Header";
import Footer from "../components/Footer";

const QuestionWrite = () => {
  const navigate = useNavigate();

  const [question, setQuestion] = useState({
    title: "",
    itemId: "",
    memberid: "",
    contents: "",
  });

  const { title, itemId, memberid, contents } = question;

  const onChange = (event) => {
    const { value, name } = event.target;
    setQuestion({
      ...question,
      [name]: value,
    });
  };

  const saveQuestion = () => {
    axios
      .post(
        "http://ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com:8080/complain/new",
        {
          content: "string",
          itemId: 1,
          memberid: 1,
          title: "string",
        }
      )
      .then((res) => {
        alert("등록되었습니다.");
        navigate("/questionlist");
      });
  };

  const backToList = () => {
    navigate("/questionlist");
  };



  return (
    <div className="z-0 w-full">
      <Header />
      <div className="m-11 flex flex-col items-center ">
        <div className="p-4">
          <div className="text-3xl text-center pb-8 font-light">1:1 문의</div>
          <span className="border flex items-center border-solid border-b-sky-400  border-b-[2px] "></span>
          <div className="text-gray-700 pt-9 ">ID</div>
          <div>{question.email}</div>
          <input
            className="h-[40px] w-[600px] rounded-2xl border border-gray-300 border-solid bg-white focus:outline-1 focus:ring focus:outline-sky-200"
            type="text"
            name="memberid"
            value={memberid}
          />
        </div>
        <div className="pb-4">
          <div className="text-gray-700">상품이름</div>
          <div>{question.itemId}</div>
          <input
            className="h-[40px] w-[600px] rounded-2xl border border-gray-300 border-solid bg-white focus:outline-1 focus:ring focus:outline-sky-200"
            type="text"
            name="itemId"
            value={itemId}
          />
        </div>
        <div>
          <div className="text-gray-700">제목</div>
          <input
            className="h-[40px] w-[600px] rounded-2xl border border-gray-300 border-solid bg-white focus:outline-1 focus:ring focus:outline-sky-200"
            type="text"
            name="title"
            value={title}
            onChange={onChange}
          />
        </div>
        <div className="p-4">
          <h2 className="text-gray-700">문의내용</h2>
          <textarea
            className="h-[300px] w-[600px] rounded-2xl border border-gray-300 border-solid bg-white focus:outline-1 focus:ring focus:outline-sky-200"
            name="contents"
            value={contents}
            onChange={onChange}
          />
        </div>
        <div className="flex justify-evenly pb-16">
          <button
            className="border border-solid border-gray-300 rounded-3xl p-2 text-[15px] hover:bg-gray-200"
            onClick={saveQuestion}
          >
            작성완료
          </button>
          <button
            className="border border-solid border-gray-300 rounded-3xl p-2 text-[15px] hover:bg-gray-200"
            onClick={backToList}
          >
            작성취소
          </button>
        </div>
      </div>
      <Footer />
    </div>
  );
};
export default QuestionWrite;

// 상품 옆에 문의글로 바로 갈 수 있게해서 상품이름이 자동으로 뜨게 할 것인지,
// 1:1 문의 페이지에서 드롭다운으로 선택할 수 있게끔 할 것인지 (백엔드 상의)
// 아이디

// <div className="my-28 flex flex-col items-center">
