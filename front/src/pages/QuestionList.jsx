import React, { useEffect, useState } from "react";
import Search from "../components/Search";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Complain from "../components/Complain";

const QuestionList = () => {
  const navigate = useNavigate();
  const [isQuestionList, setQuestionList] = useState([]);
  


  const getQuestionList = () => {
    return axios
      .get(
        "http://ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com:8080/complain/?page=1&size=10"
      )
      .then((res) => {
        console.log(res);
        setQuestionList(res.data);
      })
      .catch((err) => console.log(err));
  };

  const moveToWrite = () => {
    navigate("/QuestionWrite");
  };

  useEffect(() => {
    getQuestionList();
  }, []);

  return (
    <div className="my-28 w-full">
      <div className="flex items-center">
        <h1 className="font-semibold p-2 text-xl basis-4/5">Q&A</h1>
        <Search />
      </div>
      <div className="flex justify-end p-16">
        <button onClick={moveToWrite}>글쓰기</button>
      </div>
      <div className="flex flex-col items-center py-10 justify-between">
        <ul>
          {isQuestionList.map((question, idx) => (
            <Complain key={idx} question={question} />
          ))}
        </ul>
        <Complain />
      </div>
    </div>
  );
};

export default QuestionList;
