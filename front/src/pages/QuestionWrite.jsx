import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const QuestionWrite = () => {
  const navigate = useNavigate();

  const [question, setQuestion] = useState({
    title: "",
    itemId: "",
    memberId: "",
    contents: "",
  });

  const { title, itemId, memberId, contents } = question;

  const onChange = (event) => {
    const { value, name } = event.target;
    setQuestion({
      ...question,
      [name]: value,
    });
  };

  const saveQuestion = async () => {
    await axios
      .post(
        "http://ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com:8080/complain/new",
        question
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
      <div className="flex items-center">
        <h1 className="font-semibold p-2 text-xl basis-4/5">1:1 문의</h1>
      </div>
      <br />
      <div className="my-28 flex flex-col items-center">
        <div>
          <span className="text-gray-700">ID</span>
          <input
            className="h-[40px] w-[800px] border border-gray-300 border-solid bg-white focus:outline-1 focus:ring focus:outline-gray-200"
            type="text"
            name="memberId"
            value={memberId}
            onChange={onChange}
          />
        </div>
        <div>
          <span className="text-gray-700">상품이름</span>
          <input
            className="h-[40px] w-[800px]  border border-gray-300 border-solid bg-white focus:outline-1 focus:ring focus:outline-gray-200"
            type="text"
            name="itemId"
            value={itemId}
            onChange={onChange}
          />
        </div>
        <br />
        <div>
          <span className="text-gray-700">제목</span>
          <input
            className="h-[40px] w-[800px]  border border-gray-300 border-solid bg-white focus:outline-1 focus:ring focus:outline-gray-200"
            type="text"
            name="title"
            value={title}
            onChange={onChange}
          />
        </div>
        <div className="p-4">
          <h2 className="text-gray-700">문의내용</h2>
          <textarea
            className="h-[600px] w-[800px] border border-gray-300 border-solid  bg-white focus:outline-1 focus:ring focus:outline-gray-200"
            name="contents"
            value={contents}
            onChange={onChange}
          />
        </div>
        <br />
        <div>
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
    </div>
  );
};
export default QuestionWrite;

// 상품 옆에 문의글로 바로 갈 수 있게해서 상품이름이 자동으로 뜨게 할 것인지,
// 1:1 문의 페이지에서 드롭다운으로 선택할 수 있게끔 할 것인지 (백엔드 상의)
// 아이디
