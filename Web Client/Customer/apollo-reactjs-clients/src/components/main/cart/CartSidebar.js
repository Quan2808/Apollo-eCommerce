import StarIcon from "@mui/icons-material/Star";
import { useDispatch} from "react-redux";
import { addToCart } from "../../../features/cart/cartSlice";


const CartSidebar = (props) => {
  const dispatch = useDispatch();
    const {empties} = props;
    return (
      <>
        <div className="bg-white rounded-lg">
          <h2 className="p-4 font-semibold">
            Customers Who Bought Items in Your Recent History Also Bought
          </h2>
          {empties.map((item) => (
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
                  <div className="uppercase tracking-wide text-sm text-indigo-500 font-semibold">
                    {item.title.substring(0, 27)}
                    {item.title.length > 30 ? "..." : ""}
                  </div>
                  <p className="mt-2 text-slate-500">
                    ${item.price.toFixed(2)}
                    <br />
                    <div className="text-xs">{item.category}</div>
                    <div className="text-yellow-500 text-xs">
                      <StarIcon />
                      <StarIcon />
                      <StarIcon />
                      <StarIcon />
                      <StarIcon />
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
                      className="w-auto font-titleFont  text-xs bg-gradient-to-tr
                    from-yellow-400 to-yellow-200 border hover:from-yellow-300 hover:to-yellow-400
                    border-yellow-500 hover:border-yellow-700 active:bg-gradient-to-bl
                    active:from-yellow-400 active:to-yellow-500 duration-200 rounded-lg"
                    >
                      Add to Cart
                    </button>
                  </p>
                </div>
              </div>
            </div>
          ))}
        </div>
      </>
    );
}
 
export default CartSidebar;