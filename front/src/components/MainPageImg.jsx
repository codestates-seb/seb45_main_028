import React, { useState } from "react";
import "react-responsive-carousel/lib/styles/carousel.min.css";
import { Carousel } from "react-responsive-carousel";
import imageData from "./imagedata";

const renderSlides = imageData.map(image => (
  <div key={image.alt}>
    <img src={image.url} alt={image.alt} />
  </div>
));

const MainPageImg = () => {
  const [currentIndex, setCurrentIndex] = useState();
  function handleChange(index) {
    setCurrentIndex(index);
  }

  return (
    <div className="flex justify-center items-center py-5 px-3 mt-10 mb-10">
      <Carousel
        showArrows={false}
        autoPlay={true}
        infiniteLoop={true}
        showThumbs={false}
        selectedItem={currentIndex}
        onChange={handleChange}
        className="w-[800px] h-[450px]">
        {renderSlides}
      </Carousel>
    </div>
  );
};

export default MainPageImg;
