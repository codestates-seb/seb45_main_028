import React from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import { useState } from 'react';
import { useEffect } from 'react';

const ProductUpdate = () => {
    const navigate = useNavigate();
    const { idx } = useParams();
    const [product, setProduct] = useState({
        idx: 0,
        title: '',
        writer: '',
        contents: '',
    });

    const { title, writer, contents } = product;

    const onChange = (event) => {
        const {value, name } = event.target;
        setProduct({
            ...product,
            [name]:value,
        });
    };

    const getProduct = async () => {
        const resp = await (await axios.get(`http://ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com/${idx}`)).data;
        setProduct(resp.data);
    };

    const updateProduct = async () => {
        await axios.patxh(`http://ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com/`,product).then((res) => {
            alert('수정되었습니다.');
            navigate('/productlist/' + idx);
        });
    };

    const backToDetail = () => {
        navigate('/productlist/' + idx);
    };

    useEffect(() => {
        getProduct();
    }, []);

    return (
        <div>
            <div>
                <span>제목</span>
                <input type="text" name="title" value={title} onChange={onChange}></input>
            </div>
            <br />
            <div>
                <span>작성자</span>
                <input type="text" name="writer" value={writer} readOnly={true} />
            </div>
            <br />
            <div>
                <span>내용</span>
                <textarea 
                name="contents"
                cols="30"
                rows="10"
                value={contents}
                onChange={onChange}></textarea>
            </div>
            <br />
            <div>
                <button onClick={updateProduct}>수정</button>
                <button onClick={backToDetail}>취소</button>
            </div>
        </div>
    );
}

export default ProductUpdate;