import React, { useState }  from 'react';
import MainPageImg from '../Components/MainPageImg'
import Modal from 'react-modal';

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

    const openModal = () => {
      setModalOpen(true);
    };
  
    const closeModal = () => {
      setModalOpen(false);
    };
  

  return (
    <main className="bg-white py-4 flex flex-col justify-center items-center mb-32">
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
                <div>개발자들 추천 아이템</div>

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
                <div>콜라보 아이템</div>
                </li>
                <li className="text-center text-black">
                    <a
                        href="https://www.youtube.com/"
                        target="_blank"
                        rel="noopener noreferrer"
                    >
                        <img className="w-32 h-32 rounded-md" src='/img/main_icon_3.png' alt="main img 3" />
                        <div>개발자들 유튜브</div>
                    </a>
                </li>
                <li className="text-center text-black">
                <img className="w-32 h-32 rounded-md" src='/img/main_icon_4.png' alt="main img 4" />
                <div>제휴문의</div>
                </li>
            </ul>
        </div>
        <div className="flex flex-col justify-center mt-20 mb-20">
            <h1 className="text-black text-3xl">오늘의 신상을 만나보세요!</h1>
                <div className="flex space-x-16 mt-10">
                    <img className="w-72 h-48 rounded-lg cursor-pointer" src="/img/main_item_1.jpeg" alt="main img 1-1" />
                    <img className="w-72 h-48 rounded-lg cursor-pointer" src="/img/main_item_2.jpeg" alt="main img 1-2" />   
                    <img className="w-72 h-48 rounded-lg cursor-pointer" src="/img/main_item_3.jpeg" alt="main img 1-3" />   
                </div>
            </div>
            <div className="flex flex-col justify-center mt-10 mb-20">
            <h1 className="text-black text-3xl">핫한 콜라보 제품을 만나보세요!</h1>
                <div className="flex space-x-16 mt-10">
                    <img className="w-72 h-96 rounded-lg cursor-pointer" src="/img/main_collabo_1.jpg" alt="main img 1-1" />
                    <img className="w-72 h-96 rounded-lg cursor-pointer" src="/img/main_collabo_2.png" alt="main img 1-2" />   
                    <img className="w-72 h-96 rounded-lg cursor-pointer" src="/img/main_collabo_3.jpg" alt="main img 1-3" />   
                </div>
        </div>
    </main>
  );
}

export default Main;