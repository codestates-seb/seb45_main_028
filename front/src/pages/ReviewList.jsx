import React from "react";
import Search from "../components/Search";


function ReviewList() {

  return (
    <div className="z-0 max-w-7xl">
      <div className="flex items-center">
        <h1 className="font-semibold p-2 text-xl basis-4/5">리뷰 리스트(best 선정)</h1>
        <Search />
      </div>
      <div className="my-28 w-[1300px] h-[800px] bg-gray-200 flex flex-col items-center py-10 justify-between">
        <div className="h-[100px] w-[1200px] border border-gray-300 border-solid rounded-3xl bg-white">
        </div>
        <div className="h-[100px] w-[1200px] border border-gray-300 border-solid rounded-3xl bg-white"></div>
        <div className="h-[100px] w-[1200px] border border-gray-300 border-solid rounded-3xl bg-white"></div>
        <div className="h-[100px] w-[1200px] border border-gray-300 border-solid rounded-3xl bg-white"></div>
        <div className="h-[100px] w-[1200px] border border-gray-300 border-solid rounded-3xl bg-white"></div>
      </div>
    </div>
  );
}

export default ReviewList;
