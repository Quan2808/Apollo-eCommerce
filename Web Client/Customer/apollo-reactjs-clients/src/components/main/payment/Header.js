import React, {useEffect, useState} from "react";
import {logoBlack} from "../../../assets";
import LockIcon from '@mui/icons-material/Lock';
import {useNavigate} from "react-router-dom";
import {useSelector} from "react-redux";

function Header() {
    const { products } = useSelector((state) => state.cart);
    const [totalItem, setTotalItem] = useState(0);
    const navigate = useNavigate();

    useEffect(() => {
        let totalItem = 0;
        products.map((item) => {
            totalItem += item.quantity;
            setTotalItem(totalItem);
        });
    }, [products]);

    return (
      <div className="w-ful flex items-center p-2 border border-gray-300  bg-gradient-to-t from-gray-200 to-white">
        <div className="container mx-auto grid grid-cols-3">
          <div
            onClick={() => navigate("/cart")}
            className="col-span-1 flex justify-center"
          >
            <img class="h-10 w-35 mt-1" alt="logoBlack" src={logoBlack} />
          </div>
          <div className="col-span-1">
            <div class="font-mono mt-1 text-center align-middle text-3xl">
              Checkout(<span class="text-teal-600">{totalItem} item</span>)
            </div>
          </div>
          <div className="col-span-1 flex justify-center mt-2">
            <LockIcon />
          </div>
        </div>
      </div>
    );
}

export default Header;