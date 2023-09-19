import React, { useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import axios from "axios";

const Product = ({ product }) => {
  const navigate = useNavigate();
  const { itemId } = useParams();
  const [isProduct, setProduct] = useState({
    name: product.name,
    price: product.price,
  });

  const [isEdit, setEdit] = useState(false);

  const saveProduct = (itemId) => {
    axios
      .pacth(
        "http://ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com:8080/item/{item-Id}",
        {}
      )
      .then((res) => {
        alert("수정되었습니다.");
      });
  };

  const deleteProduct = () => {
    if (window.confirm("게시글을 삭제하시겠습니까?")) {
      axios
        .delete(
          "http://ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com:8080/item/{item-Id}"
        )
        .then((res) => {
          console.log(res);
          alert("삭제되었습니다.");
        })
        .catch((err) => {
          console.log(err);
          alert("실패하였습니다.");
        });
    }
  };

  const moveToList = () => {
    navigate("/productlist");
  };

  const moveToQuestionWrite = () => {
    navigate("/questionwrite");
  };

  const moveToProductDetail = () => {
    navigate("/productdetail/:itemid");
  };

  return (
    <div className="flex-wrap justify-between flex-row inline-flex p-9 ">
      <div>
        <div>
          <img className="w-[250px] h-[200px]" />
          <div className="text-center p-[15px]">
            {isEdit ? (
              <input
                className="text-gray-800 text-sm text-center"
                value={isProduct.name}
                onChange={(e) =>
                  setProduct({ ...isProduct, name: e.target.value })
                }
              />
            ) : (
              <div
                className="text-gray-800 text-sm"
                onClick={() => moveToProductDetail(isProduct.itemId)}
              >
                {isProduct.name}
              </div>
            )}
            {isEdit ? (
              <input
                className="text-gray-800 text-sm text-center font-semibold"
                value={isProduct.price}
                onChange={(e) =>
                  setProduct({ ...isProduct, price: e.target.value })
                }
              />
            ) : (
              <span className="text-xs font-semibold">{product.price}원</span>
            )}
            {/* <button
              onClick={() => navigate(`/questionwrite/${isProduct.itemId}`)}
            >
              문의하기
            </button> */}
          </div>
        </div>
        <div className="flex float-right flex-row text-gray-400 text-xs space-x-1">
          {!isEdit ? (
            <button onClick={() => setEdit(true)}>수정</button>
          ) : (
            <button
              onClick={() => {
                setEdit(false);
              }}
            >
              저장
            </button>
          )}
          <button onClick={() => deleteProduct(isProduct.itemId)}>삭제</button>
          {/* <button onClick={moveToList}>목록</button> */}
        </div>
      </div>
    </div>
  );
};

export default Product;

// <img> src={`${product.imageurl}`}
// questionwrite/:itemId
