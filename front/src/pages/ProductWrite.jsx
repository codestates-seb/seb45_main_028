import React, { useState } from "react";
import Search from "../components/Search";
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const ProductWrite = () => {
  const navigate = useNavigate();

  const [product, setProduct] = useState({
    title:'',
    contents: '',
    writer: '',
  })

  const { title, contents, writer } = product; //비구조화 할당

  const onChange = (event) => {
    const {value, name} = event.target;
    setProduct({
      ...product,
      [name]: value,
    });
  };
  const saveProduct = async () => {
    await axios.post('http://ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com/', product).then((res) => {
      alert('등록되었습니다.');
      navigate('/productlist'); // then (Set처럼) 안에다 넣고 실패생르 때가 catch
    });
  };

  const backToList = () => {
    navigate('/productlist');
  };

  return (
    <div className="z-0 w-full">
      <div className="flex items-center">
        <h1 className="font-semibold p-2 text-xl basis-4/5">상품등록</h1>
        <Search />
      </div>
      <div className="my-28 flex flex-col items-center ">
        <div className="p-4 ">
          <span className="text-gray-700 ">제목</span>
          <input
            className="h-[40px] w-[800px] border border-gray-300 border-solid  bg-white focus:outline-1 focus:ring focus:outline-gray-200"
            type="text" name="title" value={title} onChange={onChange} />
        </div>
        <div>
          <span className="text-gray-700">작성자</span>
          <input
            className="h-[40px] w-[800px]  border border-gray-300 border-solid bg-white focus:outline-1 focus:ring focus:outline-gray-200"
            type="text" name="writer" value={writer} onChange={onChange} />
        </div>
        <div className="p-4">
          <h2 className="text-gray-700">내용</h2>
          <textarea
            className="h-[600px] w-[800px] border border-gray-300 border-solid  bg-white focus:outline-1 focus:ring focus:outline-gray-200"
            name="contents" cols="30" rows="10" value={contents} onChange={onChange}
          ></textarea>
        </div>
        <div>
        <img type="image" name="image" onChange={onChange}></img>
        </div>
      </div>
      <div className="flex justify-evenly">
        <button className="border border-solid border-gray-300 rounded-3xl p-2 text-[15px] hover:bg-gray-200" onClick={saveProduct}>
          작성완료
        </button>
        <button className="border border-solid border-gray-300 rounded-3xl p-2 text-[15px] hover:bg-gray-200" onClick={backToList}>
          취소하기
        </button>
      </div>
    </div>
  );
}

export default ProductWrite;
