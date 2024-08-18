import React, { useEffect, useState } from "react";
import Modal from "react-modal";
import { GrClose } from "react-icons/gr";
import { useDispatch, useSelector } from "react-redux";
import { addNewAddress, getAddress, addAdressId, setTotalCost, setSelectedDeliveryOption } from "../../../features/payment/paymentSlice";

Modal.setAppElement("#root");
const ORIGIN = "391A Đ. Nam Kỳ Khởi Nghĩa, Phường 14, Quận 3, Thành phố Hồ Chí Minh";

function ShippingAddress() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isAddress, setIsAddress] = useState(true);
  const [suggestions, setSuggestions] = useState([]);
  const [distance, setDistance] = useState("");
  const dispatch = useDispatch();
  const { userInfo } = useSelector((state) => state.user);
  const { address, totalCost, deliveryOptions, selectedDeliveryOption } = useSelector((state) => state.payment);
  const [addressNew, setAddressNew] = useState({ id: '', name: '', district: '', ward: '', city: '', street: '', });
  const [formAddress, setFormAddress] = useState({ id: "", userId: userInfo.id, street: "", ward: "", district: "", city: "", defaultAddress: false, });

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormAddress({
      ...formAddress,
      [name]: value,
    });
    if (name === 'street' && value.length > 2) {
      autosuggest(value);
    } else {
      setSuggestions([]);
    }
  };

  const handleFormSubmit = (event) => {
    event.preventDefault();
    dispatch(addNewAddress(formAddress));
    setIsModalOpen(false);
  };

  useEffect(() => {
    if (address.length <= 0 && userInfo !== null) {
      dispatch(getAddress(userInfo.id));
    }
  }, [dispatch, address.length, userInfo]);

  // Start Algorithm to calculate shipping cost
  useEffect(() => {
    if (distance) {
      const distanceInKm = parseFloat(distance.replace(/[^\d.]/g, '')) || 0;
      const selectedOption = deliveryOptions.find(option => option.label === selectedDeliveryOption);
      const shippingCost = selectedOption ? selectedOption.cost : 0;
      const fixedCost = 5;
      const newTotalCost = fixedCost + (distanceInKm * shippingCost);
      dispatch(setTotalCost(newTotalCost));
    }
  }, [distance, selectedDeliveryOption, deliveryOptions, dispatch]);
  // End Algorithm to calculate shipping cost

  const autosuggest = async (text) => {
    const apiKey = '4a5b0ffe3b18321f073bc3f6e7e66ef4';
    try {
      const response = await fetch(`http://api.map4d.vn/sdk/autosuggest?key=${apiKey}&text=${text}&location=10.762622,106.660172&acronym=false`);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const results = await response.json();
      console.log('API results:', results);
      if (results.result) {
        setSuggestions(results.result);
      } else {
        setSuggestions([]);
      }
    } catch (error) {
      console.error('Error fetching autosuggest:', error);
    }
  };

  const calculateDistance = async (destination) => {
    const apiKey = '4a5b0ffe3b18321f073bc3f6e7e66ef4';
    try {
      const response = await fetch(`http://api.map4d.vn/sdk/route?key=${apiKey}&origin=${ORIGIN}&destination=${destination}&mode=car`);
      const data = await response.json();
      if (data.result && data.result.routes.length > 0) {
        const route = data.result.routes[0];
        setDistance(route.legs[0].distance.text);
      } else {
        setDistance("Unable to calculate distance");
      }
    } catch (error) {
      console.error('Error calculating distance:', error);
      setDistance("Unable to calculate distance");
    }
  };

  const handleSuggestionClick = (address) => {
    setFormAddress({
      ...formAddress,
      street: address
    });
    setSuggestions([]);
    calculateDistance(address);
  };

  const handleDeliveryOptionChange = (event) => {
    const selectedOption = deliveryOptions.find(option => option.label === event.target.value);
    if (selectedOption) {
      dispatch(setSelectedDeliveryOption(selectedOption.label));
      const distanceInKm = parseFloat(distance.replace(/[^\d.]/g, '')) || 0;
      const newTotalCost = distanceInKm * selectedOption.cost;
      dispatch(setTotalCost(newTotalCost));
    }
  };

  return (
      <div className="px-20 py-3">
        <div>
          {isAddress ? (
              <div className="grid grid-cols-3 border-b border-gray-400">
                <div>
                  <h2 className="text-xl font-bold font-sans">1. Shipping address</h2>
                </div>
                <div className="text-md">
                  <div>
                    <div className="w-23">
                      <div>{addressNew.name}</div>
                      <div>
                        <span>{addressNew.street}</span><br />
                        <span>Distance: {distance}</span>
                      </div>
                      <div>
                        <h3 className="text-lg font-bold">Total Cost: ${totalCost.toFixed(2)}</h3>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="text-right">
                  <button className="text-blue-600 hover:text-orange-500 hover:underline" onClick={() => setIsAddress(!isAddress)}>Change</button>
                </div>
              </div>
          ) : (
              <div className="grid grid-cols-2">
                <div>
                  <h2 className="text-xl font-bold font-sans text-orange-700"><span className="pr-4">1</span>Choose a shipping address</h2>
                </div>
                <div className="text-right">
                  <button className="text-blue-600 hover:text-orange-500 hover:underline text-md" onClick={() => setIsAddress(!isAddress)}>Close</button>
                  <button className="pl-2" onClick={() => setIsAddress(!isAddress)}><GrClose /></button>
                </div>
                <div className="col-span-2 border border-gray-400 rounded-md mx-8 mt-2">
                  <div className="px-6">
                    <div className="grid grid-cols-2 border-b border-gray-400 my-4">
                      <div>
                        <h2 className="text-xl font-bold font-sans">Your addresses</h2>
                      </div>
                      <div className="text-right">
                        <button className="text-md font-sans font-bold text-blue-600 hover:text-orange-500 hover:underline">Shipping to more than one address?</button>
                      </div>
                    </div>
                    {address.map((item) => (
                        <div key={item.id} className="text-md rounded-md border border-orange-300 bg-orange-100/40 my-2 p-3">
                          <input
                              type="radio"
                              name="address"
                              className="mr-2"
                              value={item.id}
                              onClick={() => {
                                dispatch(addAdressId({ addressId: item.id }));
                                setAddressNew({
                                  id: item.id,
                                  name: userInfo.clientName,
                                  district: item.district,
                                  ward: item.ward,
                                  city: item.city,
                                  street: item.street,
                                });
                                calculateDistance(item.street);
                              }}
                          />
                          <span className="font-semibold">{item.name}</span>{" "}
                          <span>{item.street}</span>
                          <button className="text-blue-600 px-2 text-sm hover:text-orange-500 hover:underline">Edit address</button>
                        </div>
                    ))}
                    <div className="flex">
                      <div className="flex-none">
                        <button className="text-gray-400 text-3xl hover:text-gray-300">+</button>
                      </div>
                      <div className="flex-initial pl-2">
                        <button
                            className="text-blue-600 mt-1 hover:text-orange-500 hover:underline"
                            onClick={() => {
                              setIsModalOpen(true);
                            }}
                        >
                          Add a new address
                        </button>
                        <Modal
                            isOpen={isModalOpen}
                            onRequestClose={() => setIsModalOpen(false)}
                            contentLabel="Add New Address"
                            className="fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-white w-2/5 rounded-lg border border-gray-400"
                        >
                          <button
                              onClick={() => setIsModalOpen(false)}
                              className="absolute right-2 my-5 text-gray-600 hover:text-gray-500 rounded-md border-4 border-sky-200"
                          >
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                className="h-6 w-6"
                                fill="none"
                                viewBox="0 0 24 24"
                                stroke="currentColor"
                            >
                              <path
                                  strokeLinecap="round"
                                  strokeLinejoin="round"
                                  strokeWidth="2"
                                  d="M6 18L18 6M6 6l12 12"
                              />
                            </svg>
                          </button>
                          <div className="text-lg bg-gray-200 py-5 pl-6 rounded-t-lg font-semibold">Enter a new shipping address</div>
                          <h2 className="text-2xl font-semibold p-6">Add New Address</h2>
                          <form onSubmit={handleFormSubmit}>
                            <div className="px-6 w-5/6">
                              <div>
                                <div className="flex flex-col items-start relative">
                                  <label className="text-base font-semibold ml-4">Street:</label>
                                  <input
                                      type="text"
                                      name="street"
                                      value={formAddress.street}
                                      onChange={handleChange}
                                      className="form-input w-full mt-1 ml-4 rounded-lg px-3 py-2 border border-gray-400"
                                  />
                                  {suggestions.length > 0 && (
                                      <div
                                          style={{
                                            position: 'absolute',
                                            backgroundColor: 'white',
                                            border: '1px solid #ddd',
                                            top: '100%',
                                            left: '16px',
                                            width: 'calc(100% - 32px)',
                                            maxHeight: '200px',
                                            overflowY: 'auto',
                                            borderRadius: '4px',
                                            marginTop: '5px',
                                            boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
                                          }}
                                      >
                                        {suggestions.map((suggestion) => (
                                            <div
                                                key={suggestion.location.lat}
                                                style={{
                                                  padding: '10px',
                                                  cursor: 'pointer',
                                                  whiteSpace: 'nowrap',
                                                  overflow: 'hidden',
                                                  textOverflow: 'ellipsis',
                                                }}
                                                onClick={() => handleSuggestionClick(suggestion.address)}
                                            >
                                              {suggestion.address}
                                            </div>
                                        ))}
                                      </div>
                                  )}
                                </div>
                              </div>
                              <button
                                  className="text-sm font-semibold bg-yellow-300 rounded-md hover:bg-yellow-400 px-4 p-1 my-5 ml-6"
                                  type="submit"
                              >
                                Use This Address
                              </button>
                            </div>
                          </form>
                        </Modal>
                      </div>
                    </div>
                  </div>
                  <div className="bg-gray-200/30 border-t border-gray-400 pl-2">
                    <button
                        className="bg-yellow-300 rounded-lg font-semibold text-sm m-3 px-2 p-1 hover:bg-yellow-400"
                        onClick={() => setIsAddress(true)}
                    >
                      Use this address
                    </button>
                  </div>
                </div>
              </div>
          )}
        </div>
      </div>
  );
}

export default ShippingAddress;
