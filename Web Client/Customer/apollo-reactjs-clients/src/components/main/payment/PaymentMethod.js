import React, { useEffect, useState } from "react";
import Modal from "react-modal";
import { GrClose } from "react-icons/gr";
import { useDispatch, useSelector } from "react-redux";
import {
  addNewPaymentMethod,
  getPaymentMethod,
  addPaymentMethodId,
} from "../../../features/payment/paymentSlice";

Modal.setAppElement("#root");

function Method() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isAddress, setIsAddress] = useState(true);
  const dispatch = useDispatch();
  const [paymentMethodNew, setPaymentMethodNew] = useState({
    id: '',
    cartNumber: '',
    nameOnCard: '',
    expirationDate: '',
  });

  const { userInfo } = useSelector((state) => state.user);
  const { paymentMethod } = useSelector((state) => state.payment);
  const [formPayment, setFormPayment] = useState({
    id: "",
    userId: userInfo.id,
    cartNumber: "",
    nameOnCard: "",
    expirationDate: "",
    defaultPayment:false,
  });

  const handleFormChange = (event) => {
    setFormPayment({
      ...formPayment,
      [event.target.name]: event.target.value,
    });
  };

  const handleFormSubmit = () => {
    dispatch(addNewPaymentMethod(formPayment));
        setIsModalOpen(false);
  };

  useEffect(() => {
    if (paymentMethod.length <= 0 && userInfo !== null) {
      dispatch(getPaymentMethod(userInfo.id));
    }
  }, []);

  return (
    <div class="px-20 py-3">
      <div>
        {isAddress ? (
          <div class="grid grid-cols-3">
            <div class="...">
              <h2 className="text-xl font-bold font-sans">
                <span class="pr-4">2</span>Payment method
              </h2>
            </div>
            <div class="text-md">
              <div>
                <span class="font-semibold">Visa</span>
                <span class="text-gray-400 ml-2">{formPayment.cartNumber}</span>
              </div>
              <div class="w-23">
                Installments unavailable.{" "}
                <button class="text-blue-600 hover:text-orange-500 hover:underline">
                  Why ?
                </button>
              </div>
              <div>
                <button class="text-blue-600 hover:text-orange-500 hover:underline">
                  Billing address:
                </button>
                <span class="ml-3">{paymentMethodNew.nameOnCard}</span>
              </div>
            </div>
            <div class="text-right">
              <button
                class="text-blue-600 hover:text-orange-500 hover:underline"
                onClick={() => setIsAddress(!isAddress)}
              >
                Change
              </button>
            </div>
          </div>
        ) : (
          <div class="grid grid-cols-2">
            <div class="...">
              <h2 className="text-xl font-bold font-sans text-orange-700">
                <span class="pr-4">2</span>Choose a payment method
              </h2>
            </div>
            <div class="text-right ">
              <button
                class="text-blue-600 hover:text-orange-500 hover:underline text-md"
                onClick={() => setIsAddress(!isAddress)}
              >
                Close
              </button>
              <button class="pl-2" onClick={() => setIsAddress(!isAddress)}>
                <GrClose />
              </button>
            </div>
            <div class="col-span-2 border border-gray-400 mx-8 mt-2">
              <div class="grid grid-cols-8 text-md">
                <div class="col-span-6 ml-6">
                  Shopping in a foreign currency ? Use{" "}
                  <strong>Amazon Currency Converter </strong>at checkout and
                  lock in your exchange rate.
                  <div>Terms and Conditions apply.</div>
                </div>
                <div class="col-span-2 text-end">
                  <button class="bg-white rounded-lg text-sm m-3 px-2 p-1 hover:bg-gray-100 border border-gray-400 shadow-md">
                    Learn more
                  </button>
                </div>
              </div>
            </div>
            <div class="col-span-2 border border-gray-400 rounded-md mx-8 mt-2">
              <div class="px-6">
                <div class="border-b border-gray-400 my-4">
                  <div class="grid grid-cols-2">
                    <div class="">
                      <h2 className="text-xl font-bold font-sans">
                        Your credit and debit cards
                      </h2>
                    </div>
                  </div>
                  <div class="font-sans text-gray-400 grid grid-cols-8">
                    <div class="col-span-4"></div>
                    <div class="col-span-2 text-center">Name on card</div>
                    <div class="col-span-2 text-center">Expires on</div>
                  </div>
                </div>
                {paymentMethod.map((item) => (
                  <div
                    key={item.id}
                    className="text-md rounded-md border border-orange-300 bg-orange-100/40 my-2 p-3"
                  >
                    <input
                      type="radio"
                      name="paymentMethod"
                      className="mr-2"
                      onClick={() => 
                        {
                          setPaymentMethodNew({
                          id: item.id,
                          cartNumber: item.cartNumber,
                          nameOnCard: item.nameOnCard,
                          expirationDate: item.expirationDate,
                        });
                        dispatch(
                          addPaymentMethodId({ paymentMethodId: item.id })
                        )
                      }
                      }
                    />
                    <span className="font-semibold">{item.nameOnCard}</span>{" "}
                    {item.cartNumber}, {item.expirationDate}
                  </div>
                ))}
                <div class="flex">
                  <div class="flex-none">
                    <button class="text-gray-400 text-3xl hover:text-gray-300">
                      +
                    </button>
                  </div>
                  <div class="px-3">
                    <img src="https://m.media-amazon.com/images/I/61a-ezJKtKL._SL40_.png" />
                  </div>
                  <div class="flex-initial">
                    <button
                      class="text-blue-600 mt-1 hover:text-orange-500 hover:underline"
                      onClick={() => setIsModalOpen(true)}
                    >
                      Add a credit or debit card
                    </button>
                    <Modal
                      isOpen={isModalOpen}
                      onRequestClose={() => setIsModalOpen(false)}
                      contentLabel="Add New Address"
                      className="fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-white w-3/6 rounded-lg border border-gray-400"
                    >
                      <button
                        onClick={() => setIsModalOpen(false)}
                        className="absolute right-2 my-5  text-gray-600 hover:text-gray-500 rounded-md border-4 border-sky-200"
                      >
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          className="h-6 w-6"
                          fill="none"
                          viewBox="0 0 24 24"
                          stroke="currentColor"
                        >
                          <path
                            stroke-linecap="round"
                            stroke-linejoin="round"
                            stroke-width="2"
                            d="M6 18L18 6M6 6l12 12"
                          />
                        </svg>
                      </button>
                      <div className=" text-lg bg-gray-200 py-5 pl-6 rounded-t-lg font-semibold">
                        Add a credit or debit card
                      </div>
                      <form onSubmit={handleFormSubmit}>
                        <div className="grid grid-cols-5 gap-2 p-4">
                          <div className="font-semibold text-end mr-3">
                            Card number
                          </div>
                          <div className="...">
                            <input
                              type="text"
                              name="cartNumber"
                              value={formPayment.cartNumber}
                              onChange={handleFormChange}
                              className="border border-gray-700 rounded-md w-full"
                            />
                          </div>
                          <div className="col-span-3">
                            Amazon accepts all major credit and debit cards.
                          </div>
                          <div className="font-semibold text-end mr-3">
                            Name on card
                          </div>
                          <div className="...">
                            <input
                              type="text"
                              name="nameOnCard"
                              value={formPayment.nameOnCard}
                              onChange={handleFormChange}
                              className="border border-gray-700 rounded-md w-full"
                            />
                          </div>
                          <div className="col-span-3 row-span-3 content-center">
                            <img
                              className="w-32 ml-14"
                              src="https://www.psdgraphics.com/file/debit-card.jpg"
                            />
                          </div>
                          <div className="font-semibold text-end mr-3">
                            Expiration date
                          </div>
                          <div className="">
                            <input
                              type="text"
                              name="expirationDate"
                              value={formPayment.expirationDate}
                              onChange={handleFormChange}
                              className="border border-gray-700 rounded-md w-full"
                            />
                          </div>
                          <div className="col-span-2 w-full pl-8">
                            <input
                              type="checkbox"
                              name="setAsDefault"
                              checked={formPayment.setAsDefault}
                              onChange={handleFormChange}
                            />
                            <span className="text-sm ml-2">
                              Set as default payment method.
                            </span>
                          </div>
                        </div>
                        <div className="bg-gray-200 py-2">
                          <div className="text-end">
                            <button
                              type="button"
                              className="text-sm font-semibold border border-gray-400 bg-white rounded-md hover:bg-gray-100 px-4 p-1"
                              onClick={() => setIsModalOpen(false)}
                            >
                              Cancel
                            </button>
                            <button
                              type="submit"
                              className="text-sm font-semibold bg-yellow-300 rounded-md hover:bg-yellow-400 px-4 p-1 ml-6 mr-10"
                              onClick={() => {
                                setIsModalOpen(false);
                                handleFormSubmit();
                              }}
                            >
                              Add your card
                            </button>
                          </div>
                        </div>
                      </form>
                    </Modal>
                  </div>
                </div>
              </div>
              <div class="bg-gray-200/30 border-t border-gray-400 pl-2">
                <button
                  class="bg-yellow-300 rounded-lg font-semibold text-sm m-3 px-2 p-1 hover:bg-yellow-400"
                  onClick={() => setIsAddress(true)}
                >
                  Use this payment
                </button>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default Method;
