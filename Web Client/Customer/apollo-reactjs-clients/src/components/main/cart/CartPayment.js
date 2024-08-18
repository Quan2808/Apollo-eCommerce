import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

const CartPayment = () => {
  const navigate = useNavigate();
  const { products } = useSelector((state) => state.cart);
  const {userInfo} = useSelector((state)=> state.user);
  const [totalPrice, setTotalPrice] = useState("");

  useEffect(() => {
    let total = 0;
    products.map((item) => {
      total += item.variantDto.price * item.quantity;
      return setTotalPrice(total.toFixed(2));
    });
  }, [products]);

  const handlePayment = ()=>{
    if(userInfo && products.length > 0){
      navigate("/payment");
    }
    if(!userInfo){
      navigate("/signin");
    }
  }

  return (
    <>
      <div className="bg-red-500">
        <div className="w-auto h-auto bg-white col-span-1 flex flex-col justify-center items-center p-4">
          <div>
            <p className="flex gap-1 items-start sm:text-xs lg:text-sm">
              <span>
                <CheckCircleIcon
                  className="bg-white text-green-500 rounded-full"
                  fontSize="small"
                />
              </span>{" "}
              you order quantifies for Free Shopping Choose this option at
              checkout, See details...
            </p>
          </div>
          <div>
            <p className="sm:text-xs md:text-md lg:text-lg font-semibold sm:px-1 lg:px-10 py-1 flex items-center gap-2 justify-between">
              Total:{" "}
              <span className="sm:text-xs md:text-md lg:text-lg font-bold ">
                ${products.length > 0 ? totalPrice : 0}
              </span>
            </p>
          </div>
          <button
            className="w-full font-titleFont font-medium sm:text-xs md:text-md lg:text-lg bg-gradient-to-tr from-yellow-400 to-yellow-200 border hover:from-yellow-300 hover:to-yellow border-yellow-500 hover:border-yellow-700 active:bg-gradient-to-bl active:from-yellow-400 active:to-yellow-500 duration-200 py-1.5 rounded-md mt-3"
            onClick={() => handlePayment()}
          >
            Proceed to Pay
          </button>
        </div>
      </div>
    </>
  );
};

export default CartPayment;
