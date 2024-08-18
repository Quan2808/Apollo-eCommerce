import React from "react";
import Header from "../components/main/payment/Header";
import Footer from "../components/main/payment/Footer";
import Address from "../components/main/payment/ShippingAddress";
import Method from "../components/main/payment/PaymentMethod";
import PayMentTotal from "../components/main/payment/PayMentTotal";
import ReviewItemAndShipping from "../components/main/payment/ReviewItemAndShipping";
function Payment() {
  return (
    <div className="font-bodyFont bg-white">
      <div>
        <Header />
      </div>
      <div class="grid grid-rows-3 grid-flow-col gap-4 p-6">
        <div class="row-span-2 col-span-4 ">
          <div>
            <Address />
          </div>
          <div>
            <Method />
          </div>
          <div>
            <ReviewItemAndShipping />
          </div>
          <div>
            <Footer />
          </div>
        </div>
        <div class="row-span-3 ...">
          <PayMentTotal />
        </div>
      </div>
    </div>
  );
}

export default Payment;
