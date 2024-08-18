import React from "react";
import Banner from "../components/main/home/Banner";
import Products from "../components/main/home/Products";

function Home() {

  return (
    <div className="font-bodyFont bg-gray-100">
      <Banner />
      <div className="relative w-full -mt-10 xl:-mt-36 py-10 z-0">
        <Products />
      </div>
    </div>
  );
}

export default Home;
