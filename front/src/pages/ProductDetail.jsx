import React, { useEffect } from 'react';
import { Outlet, useLocation, useNavigate, useParams } from 'react-router-dom';
import DetailMeuns from '../components/DetailMeuns';
import Header from '../components/Header';
import Footer from '../components/Footer';

function ProductDetail() {
    const { id } = useParams();
    const location = useLocation();
    const currentPath = location.pathname;
    const navigate = useNavigate();

    useEffect(() => {
        if (currentPath === `/product/${id}` || currentPath === `/product/${id}/`) {
            navigate(`/product/${id}/description`);
        }
    }, [id, currentPath, navigate]);


    return (
        <>
        <div className='w-full z-0'>
            <Header />
            {/* 상단 상품 상세정보 컴포넌트 */}
            <div>
                {/* 메뉴바 */}
                <DetailMeuns />
                <div>
                    {/* 메뉴 바 밑에 보여줄 페이지 */}
                    <Outlet />
                </div>
            </div>
            <Footer />
        </div>
        </>
    );
}

export default ProductDetail;