import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import Slider from "react-slick";
import { setStoreBanner } from "../../../features/adminStore/adminStoreSlice";

function StoreBanner() {
  const storeBanner = useSelector((state) => state.adminStore.storeBanner);
  const storeInfo = useSelector((state) => state.adminStore.storeInfo)
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(setStoreBanner(storeInfo.homeImage));
  }, [])

  const settings = {
    dots: false,
    infinite: false,
    autoplay: false,
    slidesToShow: 1,
    slidesToScroll: 1,
    arrows: false,
    responsive: [
      {
        breakpoint: 1024,
        settings: {
          slidesToShow: 3,
          slidesToScroll: 3,
          infinite: true,
          dots: true,
        },
      },
      {
        breakpoint: 600,
        settings: {
          slidesToShow: 2,
          slidesToScroll: 2,
          initialSlide: 2,
        },
      },
      {
        breakpoint: 480,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1,
        },
      },
    ],
  };

  return (
    <div className="w-full px-2 bg-white">
      <div className="w-full h-full relative">
        <Slider {...settings}>
          <div>
            <img src={storeBanner} alt="banner"></img>
          </div>
        </Slider>
      </div>
    </div>
  );
}

export default StoreBanner;
