import React from "react";

function Modal(props) {
  function closeModal() {
    props.closeModal();
  }

  return (
    <div onClick={closeModal}>
      <div
        className="grid justify-items-stretch absolute right-0 mt-8 w-[900px] h-[150px] bg-white border border-solid border-gray-200  shadow rounded"
        onClick={(e) => e.stopPropagation()}
      >
        <button className="justify-self-center  z-[100]" onClick={closeModal}>
          안녕하세요 불편을 드려 정말 죄송합니다!
          {/* {question.contents} */}
          <br />
          얄리얄리얄라셩
        </button>

        {props.children}
      </div>
    </div>
  );
}

export default Modal;
