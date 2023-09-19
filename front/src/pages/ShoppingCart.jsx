import React, { useState } from "react";
import Header from "../components/Header";
import Footer from "../components/Footer";

const ShoppingCart = () => {
  const [products] = useState([
    { id: 1, name: "로지텍 키보드", price: 55000 },
    { id: 2, name: "macbook air ", price: 1800000 },
    { id: 3, name: "로지텍 G102", price: 100000 },
  ]);
  const [cart, setCart] = useState([]);

  const addToCart = (product) => {
    setCart([...cart, product]);
  };

  const removeFromCart = (product) => {
    const updatedCart = cart.filter((item) => item.id !== product.id);
    setCart(updatedCart);
  };

  const total = cart.reduce((acc, product) => acc + product.price, 0);

  const handleCheckout = () => {
    // 여기에서 결제 처리 로직을 추가할 수 있습니다.
    alert("주문이 완료되었습니다.");
  };

  return (
    <div className="w-full z-0 mx-auto">
      <Header />
      <div className="m-11 flex flex-col items-center">
        <h1 className="font-bold mb-4 underline underline-offset-8 decoration-2 decoration-blue-300">
          장바구니
        </h1>
        <div className="w-[1024px] h-[400px] flex border border-solid">
          <div className="flex-grow grid grid-cols-2 gap-2 ">
            <div>
              <ul>
                {products.map((product) => (
                  <li
                    key={product.id}
                    className="flex justify-between  items-center p-12 border-b-gray-200 border border-solide bg-white mb-2 w-[800px] h-[90px]"
                  >
                    <span className="text-gray-700">{product.name}</span>
                    <span className="text-gray-700">{product.price}원</span>
                    <button
                      onClick={() => addToCart(product)}
                      className="bg-gray-100 text-gray-800 px-2 py-1 hover:bg-gray-200"
                    >
                      추가
                    </button>
                  </li>
                ))}
              </ul>
              <div className="w-[1024px] h-[80px] bg-gray-100 flex justify-end items-center pr-14">
                <strong className="text-xl ">상품구매금액: </strong>
                {total}원
              </div>
            </div>
          </div>
          <div>
            <ul>
              {cart.map((product) => (
                <li
                  key={product.id}
                  className="flex justify-between text-sm items-center pr-8 pt-8 bg-white mb-2"
                >
                  <span className="text-gray-800">{product.name}</span>
                  <span className="text-gray-800">{product.price}원</span>
                  <button
                    onClick={() => removeFromCart(product)}
                    className="bg-gray-100 text-blck px-2 py-1 text-xs rounded-xl hover:bg-gray-200"
                  >
                    제거
                  </button>
                </li>
              ))}
            </ul>
          </div>
        </div>
      </div>
      {/* <div className=" flex flex-col items-center">
        <h2 className="font-bold mb-4 underline underline-offset-8 decoration-2 decoration-blue-300">
          배송지정보
        </h2>
        <div className="w-[1024px] h-[400px] flex flex-col border border-solid">
          <div>
            <span className="border-b-gray-400 w-[250px] h-[133px] bg-gray-100">
              받으시는 분
            </span>
            <input className="w-[50px]"></input>
          </div>
          <div>
            <div className="border-b-gray-400 w-[250px] h-[133px] bg-gray-100">
              휴대전화
            </div>
            <input className="w-[50px]"></input>
          </div>
          <div>
            <div className="border-b-gray-400 w-[250px] h-[133px] bg-gray-100">주소</div>
            <input className="w-[50px]"></input>
          </div>
        </div>
      </div> */}
      <div className="flex justify-center mt-4">
        <button
          onClick={handleCheckout}
          className="bg-zinc-700 text-white border border-solid
           px-4 py-2 rounded-xl text-sm hover:bg-zinc-600"
        >
          주문 결제
        </button>
      </div>
      <Footer />
    </div>
  );
};

export default ShoppingCart;
