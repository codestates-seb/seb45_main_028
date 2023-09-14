import React, { useState, useEffect } from "react";
import Search from "../components/Search";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";

export default function ProductList() {
  const navigate = useNavigate();
  const [isProductList, setProductList] = useState([]);

  const getProductList = () => {
    return axios
      .post(
        "http://ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com:8080/item/search",
        { page: 1, size: 10 }
      )
      .then((res) => console.log(res))
      .catch((err) => console.log(err));
  };

  const moveToWrite = () => {
    navigate("/productwrite");
  };

  useEffect(() => {
    getProductList();
  }, []);

  return (
    <div className="z-0 w-full ">
      <div className="flex">
        <h1 className="font-semibold p-2 text-xl basis-4/5">Product List</h1>
        <Search />
      </div>
      <div className="flex justify-end p-16">
        <button onClick={moveToWrite}>글쓰기</button>
      </div>

      <div className="grid grid-cols-4 gap-8">
        <div>
          <img className="w-[250px] h-[200px]" src="chair.jpeg" />
          <div className="">
            <span>의자</span>
            <br />
            <span>150,000원</span>
          </div>
        </div>
        {isProductList.map((product, idx) => (
          <div key={idx}>
            <Link to={`/productlist/${product.itemId}`}>{product.title}</Link>
          </div>
        ))}
        <div>
          <img className="w-[250px] h-[200px]" src="chair.jpeg" />
          <div className="">
            <span>의자</span>
            <br />
            <span>150,000원</span>
          </div>
        </div>
        <div>
          <img className="w-[250px] h-[200px]" src="chair.jpeg" />
          <div className="p-2">
            <span>의자</span>
            <br />
            <span>150,000원</span>
          </div>
        </div>
        <div>
          <img className="w-[250px] h-[200px]" src="chair.jpeg" />
          <div className="p-2">
            <span>의자</span>
            <br />
            <span>150,000원</span>
          </div>
        </div>
        <div>
          <img className="w-[250px] h-[200px]" src="chair.jpeg" />
          <div className="p-2">
            <span>의자</span>
            <br />
            <span>150,000원</span>
          </div>
        </div>
      </div>
    </div>
  );
}
