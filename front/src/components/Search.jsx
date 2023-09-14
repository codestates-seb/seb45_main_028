import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";

function Search() {
  return (
    <div className="flex justify-end justify-items-end">
      <div className="flex flex-1 items-center border-[1px] border-gray-400 border-solid rounded-3xl pl-2.5 relative ">
        <FontAwesomeIcon
          icon={faMagnifyingGlass}
          className="p-2 text-gray-400"
        />
        <div>
          <input
            className="w-full border-none p-2 text-[14px] focus:outline-0 focus:ring rounded-3xl "
            type="text"
            placeholder="검색어를 입력해주세요"
          ></input>
        </div>
      </div>
      <div className="p-2 text-[13px]  text-gray-600">
        <button>검색</button>
      </div>
    </div>
  );
}

export default Search;
