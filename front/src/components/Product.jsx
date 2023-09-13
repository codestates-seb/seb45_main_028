import React from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';


const Product = ({ idx, title, contents, writer, image }) => {
    const navigate = useNavigate();

    const moveToUpdate = () => {
        navigate('/productupdate/' + idx);
    };

    const deleteProduct = async () => {
        if (window.confirm('게시글을 삭제하시겠습니까?')) {
            await axios.delete(`http://ec2-52-79-52-23.ap-northeast-2.compute.amazonaws.com/${idx}`).then((res) => {
                alert('삭제되었습니다.');
                navigate('/productlist');
            });
        }
    };
    const moveToList = () => {
        navigate('/productlist');
    };

    return (
        <div>
            <div>
                <h2>{title}</h2>
                <h5>{writer}</h5>
                <hr />
                <p>{contents}</p>
                <p>{image}</p>
        </div>
        <div>
            <button onClick={moveToUpdate}>수정</button>
            <button onClick={deleteProduct}>삭제</button>
            <button onClick={moveToList}>목록</button>
        </div>
        </div>
    );
};

export default Product;