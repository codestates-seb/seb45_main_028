import React, { useState } from 'react';

const ShoppingCart = () => {
  const [products] = useState([
    { id: 1, name: '소이와 데이트권', price: 100000 },
    { id: 2, name: '다연이와 식데권 ', price: 200000 },
    { id: 3, name: '성환이의 사랑', price: 371019247301 },
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
    alert('주문이 완료되었습니다.');
  };

  return (
    <div className="container mx-auto p-4 flex flex-col h-screen">
      <h1 className="text-2xl font-bold mb-4">장바구니</h1>

      <div className="flex-grow grid grid-cols-2 gap-4">
        <div>
          <h2 className="text-lg font-semibold mb-2">상품 목록</h2>
          <ul>
            {products.map((product) => (
              <li
                key={product.id}
                className="flex justify-between items-center p-2 rounded-lg shadow-md bg-white mb-2"
              >
                <span className="text-gray-700">{product.name}</span>
                <span className="text-blue-600">${product.price}</span>
                <button
                  onClick={() => addToCart(product)}
                  className="bg-blue-500 text-white px-2 py-1 rounded hover:bg-blue-700"
                >
                  추가
                </button>
              </li>
            ))}
          </ul>
        </div>

        <div>
          <h2 className="text-lg font-semibold mb-2">장바구니</h2>
          <ul>
            {cart.map((product) => (
              <li
                key={product.id}
                className="flex justify-between items-center p-2 rounded-lg shadow-md bg-white mb-2"
              >
                <span className="text-gray-700">{product.name}</span>
                <span className="text-red-600">${product.price}</span>
                <button
                  onClick={() => removeFromCart(product)}
                  className="bg-red-500 text-white px-2 py-1 rounded hover:bg-red-700"
                >
                  제거
                </button>
              </li>
            ))}
          </ul>
          <div className="mt-4">
            <strong className="text-lg">총 가격:</strong> ${total}
          </div>
        </div>
      </div>

      <div className="flex justify-center mt-4">
        <button
          onClick={handleCheckout}
          className="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-700"
        >
          주문 결제
        </button>
      </div>
    </div>
  );
};

export default ShoppingCart;
