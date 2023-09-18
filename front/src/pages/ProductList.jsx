import React, { useState, useEffect } from "react";
import Search from "../components/Search";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Product from "../components/Product";
import Header from "../components/Header";
import Footer from "../components/Footer";

export default function ProductList() {
  const navigate = useNavigate();
  const [isProductList, setProductList] = useState([]);

  const getProductList = () => {
    return axios
      .post(
        "http://ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com:8080/item/search?page=1&size=9"
      )
      .then((res) => setProductList(res.data.data))
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  const moveToWrite = () => {
    navigate("/productwrite");
  };

  useEffect(() => {
    getProductList();
  }, []);

  return (
    <div className=" w-full z-0 ">
      <Header />
      <div className="flex justify-end w-[1024px] mx-auto">
        <Search />
      </div>
      <button className="text-gray-400 text-sm w-[1024px] flex justify-end mx-auto pt-4 pr-8" onClick={moveToWrite}>
          글쓰기
        </button>
      <div className="w-[1024px] columns-3 mx-auto">
        <ul>
          {isProductList.map((product, idx) => (
            <Product key={idx} product={product} />
          ))}
        </ul>
      </div>
      <Footer />
    </div>
  );
}

// {isProductList.map((product, idx) => (
//   <div key={idx}>
//     <Link to={`/productlist/${product.itemId}`}>{product.title}</Link>
//   </div>
// ))}

// 이미지 누르면 productdetail로 가게끔 Navigate 설정

// http://ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com:8080/item/search?page=1&size=8
