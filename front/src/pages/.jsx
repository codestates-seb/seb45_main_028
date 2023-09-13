import Search from "../components/Search";
import React from "react";

function QuestionList() {


  return (
    <div className="z-0 max-w-7xl">
      <div className="flex items-center">
        <h1 className="font-semibold p-2 text-xl basis-4/5">질문목록</h1>
        <Search />
      </div>
      <button className="flex ml-auto mr-56 border-solid border border-gray-300 rounded-full p-2 text-gray-600 hover:bg-gray-200 mt-14 mb-10 text-[14px]">
        질문하기
      </button>
      <div className="flex basis-2 flex-row flex-wrap justify-center gap-8">
        <div className="mx-20 border-black border-solid border w-80 h-44 rounded-3xl flex-row">
          <div id='content' className="grid grid-cols-2 pt-32 justify-items-center">
            <span id='questionId'>작성자</span>
            <span id='date'>작성날짜</span>
          </div>
        </div>
        <div className="mx-20 border-black border-solid border w-80 h-44 rounded-3xl flex-row">
          <div className="grid grid-cols-2 pt-32 justify-items-center">
            <span>작성자</span>
            <span>작성날짜</span>
          </div>
        </div>
        <div className="mx-20 border-black border-solid border w-80 h-44 rounded-3xl flex-row">
          <div className="grid grid-cols-2 pt-32 justify-items-center">
            <span>작성자</span>
            <span>작성날짜</span>
          </div>
        </div>
        <div className="mx-20 border-black border-solid border w-80 h-44 rounded-3xl flex-row">
          <div className="grid grid-cols-2 pt-32 justify-items-center">
            <span>작성자</span>
            <span>작성날짜</span>
          </div>
        </div>
        <div className="mx-20 border-black border-solid border w-80 h-44 rounded-3xl flex-row">
          <div className="grid grid-cols-2 pt-32 justify-items-center">
            <span>작성자</span>
            <span>작성날짜</span>
          </div>
        </div>
        <div className="mx-20 border-black border-solid border w-80 h-44 rounded-3xl flex-row">
          <div className="grid grid-cols-2 pt-32 justify-items-center">
            <span>작성자</span>
            <span>작성날짜</span>
          </div>
        </div>
      </div>
    </div>
  );
}

export default QuestionList;
