import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import StarIcon from "@mui/icons-material/Star";
import { Button } from "@mui/material";
import "../../../assets/css/cart/shoppingTrend.css";
import { useNavigate } from "react-router-dom";
import { findProductsRecentlyViewed } from "../../../api/CartAPI";
import Spinner from "./Spinner";
import { useEffect } from "react";
import { useState } from "react";

const RecentlyViewed = () => {
  const navigate = useNavigate();
  const [isSuccess, setIsSuccess] = useState(false);
  const [list, setList] = useState([]);

  useEffect(() => {
    findProductsRecentlyViewed().then((item) => {
      if (!isSuccess) {
        setList(item.data);
        setIsSuccess(true);
      }
    });
  }, [isSuccess]);

  const settings = {
    className: "center",
    infinite: true,
    centerPadding: "60px",
    slidesToShow: 7,
    slidesToScroll: 7,
    swipeToSlide: true,
    prevArrow: <Button class="slick-prev" />,
    nextArrow: <Button class="slick-next" />,
    responsive: [
      {
        breakpoint: 768,
        settings: {
          slidesToShow: 4,
          slidesToScroll: 4,
        },
      },
      {
        breakpoint: 480,
        settings: {
          slidesToShow: 3,
          slidesToScroll: 3,
        },
      },
    ],
  };

  return (
    <>
      <div className="">
        {!isSuccess ? (
          <Spinner />
        ) : (
          <div className="px-8">
            <div className="grid grid-flow-row auto-rows-max ">
              <div className=" sm:text-xs md:text-sm lg:text-md py-3 font-medium">
                Customers who viewed items in your browsing history also viewed
              </div>
            </div>
            <Slider {...settings}>
              {list.map((item) => (
                <div className="sm:p-0 md:p-1 lg:p-3 pt-4" key={item.id}>
                  <div className="bg-cover bg-center">
                    <img
                        className="sm:w-16 sm:h-20 md:w-32 md:h-32 lg:w-36 lg:h-36"
                      src={item.image}
                      alt="Modern building architecture"
                    />
                  </div>
                  <button
                      className="text-left sm:text-xs md:text-sm lg:text-md hover:text-teal-600"
                    onClick={() => navigate(`/product/${item.id}`)}
                  >
                    {item.title.substring(0, 60)}
                    {item.title.length > 60 ? "..." : ""}
                  </button>
                  <div className="sm:text-xs md:text-sm lg:text-md text-amazon_blue">
                    ${item.price.toFixed(2)}
                  </div>
                  <div className="text-yellow-500">
                    <StarIcon fontSize="small" />
                    <StarIcon fontSize="small" />
                    <StarIcon fontSize="small" />
                    <StarIcon fontSize="small" />
                    <StarIcon fontSize="small" />
                  </div>
                </div>
              ))}
            </Slider>
          </div>
        )}
      </div>
    </>
  );
};

export default RecentlyViewed;
