import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { addShopOrder, clear } from "../../../features/payment/paymentSlice";
import { clearCartLine } from "../../../features/cart/cartSlice";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const PayMentTotal = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { userInfo } = useSelector((state) => state.user);
  const { products } = useSelector((state) => state.cart);
  const { addressId, paymentMethodId, shippingMethodId, totalCost, deliveryOptions, selectedDeliveryOption } = useSelector((state) => state.payment);
  const [totalPrice, setTotalPrice] = useState(0);
  const [totalItem, setTotalItem] = useState(0);
  const date = new Date();

  const handleOrder = () => {
    const newValues = products.map((item) => ({
      userId: userInfo.id,
      variantId: item.variantDto.id,
      orderDate: `${date.getDate()}/${date.getMonth() + 1}/${date.getFullYear()}`, // Ensure correct month
      addressId: addressId,
      paymentMethodId: paymentMethodId,
      shippingMethodId: shippingMethodId,
      quantity: item.quantity,
      orderTotal: (item.quantity * item.variantDto.price).toFixed(2),
    }));
    if (addressId && paymentMethodId && shippingMethodId) {
      dispatch(addShopOrder(newValues));
      dispatch(clear());
      dispatch(clearCartLine(userInfo.id));
      navigate("/");
    } else {
      notifyError();
    }
  };

  const notifyError = () => {
    toast.error("Request complete information !");
  };

  useEffect(() => {
    let totalPrice = 0;
    let totalItem = 0;
    products.forEach((item) => {
      totalPrice += item.variantDto.price * item.quantity;
      totalItem += item.quantity;
    });
    setTotalItem(totalItem);

    const selectedOption = deliveryOptions.find(option => option.label === selectedDeliveryOption);
    const shippingCost = selectedOption ? selectedOption.cost : 0;
    setTotalPrice(totalPrice + shippingCost);
  }, [products, deliveryOptions, selectedDeliveryOption]);

  return (
      <>
        <div className="border border-gray-400 rounded-md">
          <div className="w-auto h-auto col-span-1 flex flex-col p-4">
            <div>
              <ToastContainer position="top-right" />
            </div>
            <button
                className="w-full font-titleFont sm:text-xs md:text-md lg:text-lg bg-gradient-to-tr bg-yellow-400 hover:bg-yellow-500 duration-200 py-1.5 rounded-xl mt-3"
                onClick={() => handleOrder()}
            >
              Place your order
            </button>
            <div className="text-center">
              <p className="flex gap-1 items-start sm:text-xs lg:text-sm pt-4">
                By placing your order, you agree to Amazon's privacy notice and conditions of use.
              </p>
              <p className="flex gap-1 items-start sm:text-xs lg:text-sm pt-2">
                You also agree to AmazonGlobal's terms and conditions.
              </p>
            </div>
            <div className="font-semibold border-t border-gray-500 my-3">
              Order Summary
            </div>
            <div className="grid grid-cols-4 gap-1 text-sm font-sans">
              <div className="col-span-2">Items ({totalItem}):</div>
              <div className="col-span-2 text-end">${totalPrice.toFixed(2)}</div>
              <div className="col-span-2">Shipping & handling:</div>
              <div className="col-span-2 text-end">${totalCost.toFixed(2)}</div>
              <div className="col-span-2"></div>
              <div className="col-span-2 text-end">
                <div className="border border-gray-300"></div>
              </div>
              <div className="col-span-2">Total before tax:</div>
              <div className="col-span-2 text-end">
                ${((totalPrice) + totalCost).toFixed(2)}
              </div>
              <div className="col-span-2">Estimated tax to be</div>
              <div className="col-span-2 text-end">
                ${(totalPrice * 0.10).toFixed(2)}
              </div>
              <div className="col-span-2 text-red-600 font-bold text-lg">
                Order total:
              </div>
              <div className="col-span-2 text-end text-red-600 font-bold text-lg">
                ${((totalPrice) + totalCost + (totalPrice * 0.10)).toFixed(2)}
              </div>
              <div className="border border-gray-300 col-span-4"></div>
            </div>
            <div>
              <button className="text-blue-600 text-sm hover:text-orange-500 hover:underline">
                Exchange rate
              </button>
            </div>
            <div>1 USD = 24540 VND</div>
            <div>
              {((totalPrice) + totalCost + (totalPrice * 0.10)).toFixed(2)} USD ={" "}
              {(((totalPrice) + totalCost + (totalPrice * 0.10)) * 24540).toFixed(0)}{" "}
              VND
            </div>
          </div>
          <div className="bg-gray-200 text-sm p-4 font-sans border-t border-gray-400 rounded-b-lg ">
            <div className="text-blue-600 text-sm hover:text-orange-500 hover:underline">
              What is the Amazon Currency Converter?
            </div>{" "}
            You can track your shipment and view any applicable import fees deposit before placing your order.{" "}
            <span className="text-blue-600 text-sm hover:text-orange-500 hover:underline">
            Learn more
          </span>{" "}
            <div className="text-blue-600 text-sm hover:text-orange-500 hover:underline">
              How are shipping costs calculated?
            </div>{" "}
            <div className="text-blue-600 text-sm hover:text-orange-500 hover:underline">
              Why didn't I qualify for free shipping?
            </div>
          </div>
        </div>
      </>
  );
};

export default PayMentTotal;
