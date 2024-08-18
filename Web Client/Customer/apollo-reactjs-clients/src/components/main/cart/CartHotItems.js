import StarIcon from "@mui/icons-material/Star";
import {useDispatch} from "react-redux";
import CartPayment from "./CartPayment";
import {addToCart} from "../../../features/cart/cartSlice";
import {useEffect} from "react";
import {useState} from "react";
import {findFiveProducts} from "../../../api/CartAPI";
import Spinner from "./Spinner";
import {useParams} from "react-router-dom";

const CartSidebar = () => {
  const dispatch = useDispatch();
  const [list, setList] = useState([]);
  const [isSuccess, setIsSuccess] = useState(false);
  
  // useEffect(() => {
  //   findFiveProducts().then((item) => { 
  //     if (!isSuccess) {
  //       setList(item.data);
  //       setIsSuccess(true);
  //     }
  //   });
  // }, [isSuccess]);

    return (
        <div>
            <div className="flex flex-col gap-4">
                <CartPayment/>
                {!isSuccess ? (
                    // <Spinner/>
                    <></>
                ) : (
                    <div className="bg-white rounded-lg">
                        <h3 className="sm:p-2 lg:p-4 font-semibold md:text-md sm:text-xs lg:text-lg">
                            Top 10 best-selling products
                        </h3>
                        {list.map((item) => (
                            <div className="max-w-md mx-auto bg-white  overflow-hidden md:max-w-2xl rounded-lg">
                                <div className="md:flex p-2">
                                    <div className="md:shrink-0 ">
                                        <img
                                            className="h-auto w-full object-cover md:h-auto md:w-20 p-2"
                                            src={item.image}
                                            alt="Modern building architecture"
                                        />
                                    </div>

                                    <div className="p-1">
                                        <div className="tracking-wide md:text-md sm:text-xs lg:text-base">
                                            {item.title.substring(0, 27)}
                                            {item.title.length > 30 ? "..." : ""}
                                        </div>
                                        <p className="mt-2 text-slate-500 md:text-md sm:text-xs lg:text-md">
                                            ${item.price.toFixed(2)}
                                            <br/>
                                            <div className="md:text-md sm:text-xs lg:text-md">
                                                {item.category}
                                            </div>
                                            <div className="text-yellow-500">
                                                <StarIcon fontSize="small"/>
                                                <StarIcon fontSize="small"/>
                                                <StarIcon fontSize="small"/>
                                                <StarIcon fontSize="small"/>
                                                <StarIcon fontSize="small"/>
                                            </div>
                                            <button
                                                onClick={() =>
                                                    dispatch(
                                                        addToCart({
                                                            id: item.id,
                                                            title: item.title,
                                                            description: item.description,
                                                            price: item.price,
                                                            category: item.category,
                                                            image: item.image,
                                                            quantity: 1,
                                                        })
                                                    )
                                                }
                                                className="font-titleFont text-xs
                    bg-yellow-400 hover:bg-yellow-500 text-zinc-900 rounded-lg px-2 p-1 mt-1"
                                            >
                                                Add to Cart
                                            </button>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
};

export default CartSidebar;
