import React, { useState } from "react";
import Footer from "../components/Footer";
import Header from "../components/Header";
import axios from "axios";
import { useNavigate } from "react-router-dom";

// import AWS, { Imagebuilder } from "aws-sdk";

const ProductWrite = () => {
  const navigate = useNavigate();

  // const aws_key = process.env.aws_key;
  // const aws_sec = process.env.aws_sec;

  const [product, setProduct] = useState({
    title: "",
    contents: "",
    writer: "",
  });

  // AWS.config.update({
  //   accessKeyId: aws_key,
  //   secretAccessKey: aws_sec,
  // });

  // const s3 = new AWS.S3();

  // const getImg = async (product, title) => {
  //   async function download(filename) {
  //     const data = await s3
  //     .getObject({
  //       Key: filename,
  //       Bucket: "28be",
  //     })
  //     .promise();

  //     const blob = new Blob([data.Body], {type: "iamge/jpeg"});
  //     const urlCreator = urlCreator.createObjectURL(blob);

  //     return Imagebuilder;
  //   }

  //   let data;

  //   try {
  //     data = await download(`qr_img/${product}/${title}.jpg`);
  //   } catch {
  //     try {
  //       data = await download( `qr_img/${product}/${title}.jpeg`);
  //     } catch {
  //       try {
  //         data = await download(`qr_img/${product}/${title}.png`);
  //       } catch {
  //         data = "";
  //       }
  //     }
  //   }
  //   return data;
  // };

  const { title, contents, writer } = product;

  const onChange = (event) => {
    const { value, name } = event.target;
    setProduct({
      ...product,
      [name]: value,
    });
  };
  const saveProduct = () => {
    axios
      .post(
        "http://ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com/item/new"
      )
      .then((res) => {
        alert("등록되었습니다.");
        navigate("/productlist");
      });
  };

  const backToList = () => {
    navigate("/productlist");
  };

  return (
    <div className="z-0 w-full">
      <Header />
      <div className="m-11 flex flex-col items-center ">
        <div className="p-4 ">
          <div className="text-3xl text-center pb-8 font-light">CONTACT US</div>
          <span className="border flex items-center border-solid border-b-sky-400  border-b-[2px] "></span>
          <div className="text-gray-700 pt-9 ">제목</div>
          <div>{product.title}</div>
          <textarea
            className="h-[40px] w-[600px] border border-gray-300 border-solid rounded-2xl bg-white focus:outline-1 focus:ring focus:outline-gray-200"

            name="title"
            value={title}
            onChange={onChange}
          />
        </div>
        <div className="pb-4 ">
          <div className="text-gray-700 ">이메일 주소</div>
          <div>{product.writer}</div>
          <textarea
            className="h-[40px] w-[600px] rounded-2xl border border-gray-300 border-solid bg-white focus:outline-1 focus:ring focus:outline-sky-200"
            name="writer"
            value={writer}
            onChange={onChange}
          />
        </div>
        <div >
          <div className="text-gray-700">첨부파일</div>
          <div className="h-[40px] w-[600px] rounded-2xl text-center border border-gray-300 border-solid bg-white focus:outline-1 focus:ring focus:outline-sky-200">
            <input className="" type="file"></input>
          </div>
        </div>
        <div>
          <img type="image" name="image" onChange={onChange}></img>
        </div>
        <div className="p-4">
          <h2 className="text-gray-700">내용</h2>
          <textarea
            className="h-[600px] w-[600px] border border-gray-300 text-center rounded-2xl border-solid  bg-white focus:outline-1 focus:ring focus:outline-gray-200"
            name="contents"
            rows="5" cols="5"
            value={contents}
            onChange={onChange}
            placeholder="내용을 입력해주세요"
          ></textarea>
        </div>
      </div>
      <div className="flex justify-evenly pb-16 ">
        <button
          className="border border-solid border-gray-300 rounded-2xl p-2 text-[15px] hover:bg-gray-200"
          onClick={saveProduct}
        >
          작성완료
        </button>
        <button
          className="border border-solid border-gray-300 rounded-2xl p-2 text-[15px] hover:bg-gray-200"
          onClick={backToList}
        >
          취소하기
        </button>
      </div>
      <Footer />
    </div>
  );
};

export default ProductWrite;
