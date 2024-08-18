import CartHotItems from "./CartHotItems";
import CartLine from "./CartLine";
import CartSaveForLater from "./CartSaveForLater";
import RecentlyViewed from "./RecentlyViewed";
import ShoppingTrend from "./ShoppingTrend";


const Cart = () => {
  
  return (
    <div className="w-full bg-gray-100 p-4">
      <div className="grid grid-cols-4 gap-4">
        <div className="col-span-3">
          <div className="grid gap-4">
            <CartLine />
            <CartSaveForLater />
          </div>
        </div>
        <div>
          <div className="col-span-3 ">
            <CartHotItems />
          </div>
        </div>
        <div className="col-span-4 bg-white">
          <ShoppingTrend />
        </div>
        <div className="col-span-4 bg-white">
          <RecentlyViewed/>
        </div>
      </div>
    </div>
  );
};

export default Cart;
