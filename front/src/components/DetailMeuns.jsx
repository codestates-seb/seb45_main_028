import React from "react";
import { NavLink, useParams } from "react-router-dom";
import QuestionWrite from "../pages/QuestionWrite";

function DetailMeuns() {
  const { id } = useParams();
  return (
    <div className="w-full p-[10px] flex mx-8">
      <li className="border-solid border-black rounded-none border flex-auto text-center">
        <NavLink
          to={"product"}
          style={({ isActive }) => (isActive ? active : {})}
        >
          상품설명
        </NavLink>
      </li>
      <li>
        <NavLink
          to={"review"}
          style={({ isActive }) => (isActive ? active : {})}
        >
          리뷰
        </NavLink>
      </li>
      <li>
        <NavLink
          to={QuestionWrite}
          style={({ isActive }) => (isActive ? active : {})}
        >
          문의
        </NavLink>
      </li>
    </div>


  );
}

export default DetailMeuns;
