import React, { useState, useEffect } from 'react';
import axios from 'axios';


  const Questions = [
    {
      Tag: '[쇼핑하기]',
      QuestionTitle: '배송은 언제 시작하나요?',
    },
    {
      Tag: '[쇼핑하기]',
      QuestionTitle: '반품 및 취소는 어떻게하나요?',
    },
    {
      Tag: '[쇼핑하기]',
      QuestionTitle: '배송 중 파손된 물품이 왔어요.',
    },
  ];
  


  
function MainProduct() {
    const [bestProductList, setbestProductList] = useState([]);

    const getProduct = async (item) => {
      try {
        const res = await axios.get(
          `http://ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com:8080/item/${item.id}`
        );
        setbestProductList(prevProducts => [...prevProducts, res.data]);
      } catch (err) {
        console.log(err);
      }
    };
    
    useEffect(() => {
      const itemID = [
        {
          id : 1
        },
        {
          id : 2
          },
          {
            id : 3
          }
      ];
      itemID.forEach(item => getProduct(item));
    }, []);
    
    
  return (
    <main className="bg-white py-4 flex flex-col justify-center items-center mb-5">
      <h1 className="text-black text-3xl">Best Item</h1>
      <div className="flex space-x-16 mt-10">
        {bestProductList.map((product, idx) => (
            <div key={idx} className="flex flex-col items-center">
            <img
                className="w-72 h-52 rounded-lg cursor-pointer"
                src={product.data.imageURLs[0].url}
                alt={`product-${product.data.imageURLs[0].imageName}`}
            />
            <p className="mt-2 text-lg font-bold">{product.data.item.name}</p>
            <p className="text-gray-600">{product.data.item.price}원</p>
            </div>
        ))}
    </div>
    <div className="flex flex-col justify-center items-center  mt-40 mb-20">
        <h1 className="text-black text-3xl">Best Question</h1>
        <div className="mt-10">
            {Questions.slice(0, 3).map((question, index) => (
            <div key={index} className="flex items-center mt-10 ">
                <p className="text-2xl font-semibold text-sky-500 mr-32">{question.Tag}</p>
                <p className="mt-2 text-2xl">{question.QuestionTitle}</p>
            </div>
            ))}
        </div>
    </div>
    </main>
  );
}

export default MainProduct;

