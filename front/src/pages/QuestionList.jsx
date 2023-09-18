import React, { useEffect, useState } from "react";
import Search from "../components/Search";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Complain from "../components/Complain";
import Header from "../components/Header";
import Footer from "../components/Footer";

const QuestionList = () => {
  const navigate = useNavigate();
  const [isQuestionList, setQuestionList] = useState([]);
  
  const getQuestionlist = () => {
    return axios
      .get(
        "http://ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com:8080/complain?page=1&size=10",
        {
          "content": "string",
          "createdAt": "2023-09-18T04:44:52.403Z",
          "itemName": "string",
          "modifiedAt": "2023-09-18T04:44:52.403Z",
          "name": "string",
          "title": "string",
        }
      ) 
      .then((res) => {
        console.log(res); 
        setQuestionList(res.data.data);
      }) 
      .catch((err) => console.log(err)); 
  };

  const moveToWrite = () => {
    navigate("/questionwrite"); 
  };
 
  useEffect(() => {
    getQuestionlist(); 
  }, []);

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);
 
  return (
    <div className="w-full z-0">
      <Header />
      <div className="flex justify-end w-[1024px] mx-auto">
        <Search /> 
      </div>
      <div className="text-gray-800 text-sm w-[1024px] flex justify-end mx-auto pt-4 pr-8">
        <button onClick={moveToWrite}>질문하기</button> 
      </div>
      <div className="flex flex-col items-center py-10 justify-between">
        <ul>
          {isQuestionList.map((question, idx) => ( 
            <Complain key={idx} question={question} />  
          ))}
        </ul>
        <Complain />
      </div>
      <Footer />
    </div>
  );
};

export default QuestionList;
