import { useEffect } from "react";
import {
  addNewCartLine,
  addToCart,
  deleteEmpties,
  deleteSaveForLater,
  getSaveForLater,
} from "../../../features/cart/cartSlice";
import { useDispatch, useSelector } from "react-redux";

const CartSaveForLater = () => {
  const dispatch = useDispatch();
  const { empties } = useSelector((state) => state.cart);
  const { userInfo } = useSelector((state) => state.user);

  useEffect(() => {
    if(userInfo){
      dispatch(getSaveForLater(userInfo.id));
    }
  }, [userInfo]);

  return (
    <>
      <div className="bg-lime-400">
        <div className="w-full h-full bg-white px-4 col-span-4 pb-1">
          <div className="font-titleFont flex items-center justify-between border-b-[1px] border-b-gray-400 py-3">
            <h2 className="text-3x1 font-medium">
              {empties.length > 0 ? (
                <div>Saved for later ({empties.length} items)</div>
              ) : (
                <div>No items saved for later</div>
              )}
            </h2>
          </div>
          <div className="max-w-screen-2xl mx-auto grid grid-cols-1 md:grid-cols-2 xl:grid-cols-4  xl:gap-4 py-4">
            {empties.map((item) => (
              <div
                class="grid grid-cols-4 gap-1 bg-white border-[1px] border-gray-200 hover:border-transparent shadow-none hover:shadow-testShadow duration-200"
                key={item.variantDto.id}
              >
                <div className=" col-span-4 px-3">
                  <div className="text-sm italic text-gray-500 text-right">
                    {item.variantDto.skuCode}
                  </div>
                </div>
                <div className=" col-span-4">
                  <div className="w-full h-auto flex items-center justify-center relative group">
                    <img
                      className="w-52 h-44 object-contain"
                      src={item.variantDto.img}
                      alt="ProductImg"
                    />
                  </div>
                </div>
                <div className=" col-span-4 px-4 h-12">
                  <div className="flex items-center justify-between  ">
                    <h2
                      className="font-titleFont tracking-wide text-md text-amazon_blue
                          font-medium alain-top"
                    >
                      {item.variantDto.name.substring(0, 45)}
                      {item.variantDto.name.length > 45 ? "..." : ""}
                    </h2>
                  </div>
                </div>
                <div class=" col-span-4 px-4">
                  <p className="text-md font-semibold">
                    ${item.variantDto.price}
                  </p>
                  <button
                    onClick={() => {
                      userInfo
                        ? dispatch(
                            addNewCartLine({
                              id: "",
                              quantity: item.quantity,
                              cartId: item.cartDto.id,
                              variantId: item.variantDto.id,
                            })
                          ) && dispatch(deleteSaveForLater(item.id))
                        : dispatch(
                            addToCart({
                              id: "",
                              quantity: item.quantity,
                              cartDto: {
                                id: item.cartDto.id,
                                userId: item.cartDto.userId,
                              },
                              variantDto: {
                                id: item.variantDto.id,
                                name: item.variantDto.name,
                                skuCode: item.variantDto.skuCode,
                                stockQuantity: item.variantDto.stockQuantity,
                                weight: item.variantDto.weight,
                                price: item.variantDto.price,
                                img: item.variantDto.img,
                              },
                            })
                          ) && dispatch(deleteEmpties(item.variantDto.id));
                    }}
                    className="w-full font-titleFont  text-base 
                    border hover:bg-gray-100 shadow-md
                  border-gray-300 
                    duration-200 py-1.5 rounded-md mt-1"
                  >
                    Move to Cart
                  </button>
                  <button
                    onClick={() => {
                      userInfo
                        ? dispatch(deleteSaveForLater(item.id))
                        : dispatch(deleteEmpties(item.variantDto.id));
                    }}
                    className="text-sm text-cyan-600 hover:underline mt-3"
                  >
                    Delete
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </>
  );
};

export default CartSaveForLater;
