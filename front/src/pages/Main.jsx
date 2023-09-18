import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { NavLink ,Link } from 'react-router-dom';
import MainPageImg from '../components/MainPageImg';
import Modal from 'react-modal';
import Header from '../components/Header';
import MainProduct from '../components/MainProduct';


const customStyles = {
  overlay: {
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
    position: 'fixed',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    zIndex: '50',
  },
  content: {
    backgroundColor: 'white',
    width: '300px',
    height: '100px',
    padding: '4',
    borderRadius: 'rounded-md',
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
  },
};

function Main() {
  const [isModalOpen, setModalOpen] = useState(false);
  const [products, setProducts] = useState([]);

  const openModal = () => {
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
  };

  useEffect(() => {
    axios.get('http://ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com/item/search')
      .then((response) => {
        setProducts(response.data);
      })
      .catch((error) => {
        console.error('상품 데이터를 가져오는 데 실패했습니다.', error);
      });
  }, []);

  return (
    <main className="bg-white py-4 flex flex-col justify-center items-center mb-32">
      <Header />
      <MainPageImg />
      <div className="flex justify-between items-center mt-10 mb-20">
        <ul className="flex space-x-20 justify-items-center">
          <li className="text-center text-black ">
            <img
              className="w-32 h-32 rounded-md cursor-pointer"
              src="/img/main_icon_1.png"
              alt="main img 1"
              onClick={openModal}
            />
            <div className='mt-5'>개발자들 추천 상품</div>
            <Modal
              isOpen={isModalOpen}
              onRequestClose={closeModal}
              style={customStyles}
              contentLabel="Modal"
            >
              <p className='text-center text-xl my-4'>준비중입니다 곧 만나요!</p>
              <button className='text-center mx-auto block text-xl hover:text-red-500' onClick={closeModal}>닫기</button>
            </Modal>
          </li>
          <li className="text-center text-black">
            <img className="w-32 h-32 rounded-md" src='/img/main_icon_2.png' alt="main img 2" />
            <div className='mt-5'>추천 상품</div>
          </li>
          <li className="text-center text-black">
            <a
              href="https://www.youtube.com/"
              target="_blank"
              rel="noopener noreferrer"
            >
              <img className="w-32 h-32 rounded-md" src='/img/main_icon_3.png' alt="main img 3" />
              <div className='mt-5'>개발자들 유튜브</div>
            </a>
          </li>
          <Link to="QuestionWrite">
          <li className="text-center text-black">
            <img className="w-32 h-32 rounded-md" src='/img/main_icon_4.png' alt="main img 4" />
            <div className='mt-5'>제휴문의</div>
          </li>
          </Link>
        </ul>
      </div>
      <div className="flex flex-col justify-center mt-20 mb-20">
        <MainProduct />
      </div>
    </main>
  );
}

export default Main;
