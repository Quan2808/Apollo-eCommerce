import { configureStore } from "@reduxjs/toolkit";
import {
  persistStore,
  persistReducer,
  FLUSH,
  REHYDRATE,
  PAUSE,
  PERSIST,
  PURGE,
  REGISTER,
} from "redux-persist";
import storage from "redux-persist/lib/storage";
import cartReducer from "../features/cart/cartSlice";
import variantReducer from "../features/variant/variantSlice";
import userReducer from "../features/user/userSlice";
import bulletReducer from "../features/variant/bulletSlide";
import hashtagReducer from "../features/variant/hashtagSlide";
import productReducer from "../features/variant/productSlide";
import shopReducer from "../features/variant/shopSlide";
import commentReducer from "../features/coment_review/commentSlide";
import reviewReducer from "../features/coment_review/reviewSlide";
import adminStoreReducer from "../features/adminStore/adminStoreSlice";
import adminReducer from "../features/admin/adminSlice";
import categoryReducer from "../features/variant/categorySlide";
import homeReducer from "../features/home/homeSlice";
import storeCategoryReducer from "../features/variant/storeCategorySlide"
import optionReducer from "../features/variant/optionSlide"
import optionValueReducer from "../features/variant/optionValueSlide"
import paymentReducer from "../features/payment/paymentSlice";
import imageReducer from "../features/variant/ImageSlide"


const persistConfig = {
  key: "root",
  version: 1,
  storage,
};
const userPersistedReducer = persistReducer(persistConfig, userReducer);
const adminStorePersistedReducer = persistReducer(persistConfig, adminStoreReducer);
const cartPersistedReducer = persistReducer(persistConfig, cartReducer);
const hashtagPersistedReducer = persistReducer(persistConfig, hashtagReducer);
const productPersistedReducer = persistReducer(persistConfig, productReducer);
const shopPersistedReducer = persistReducer(persistConfig, shopReducer);
const commentPersistedReducer = persistReducer(persistConfig, commentReducer);
const reviewPersistedReducer = persistReducer(persistConfig, reviewReducer);
const adminPersistedReducer = persistReducer(persistConfig, adminReducer);
const categoryPersistedReducer = persistReducer(persistConfig, categoryReducer);
const storeCategoryPersistedReducer = persistReducer(persistConfig, storeCategoryReducer);

export const store = configureStore({
  reducer: {
    home: homeReducer,
    cart: cartPersistedReducer,
    adminStore: adminStorePersistedReducer,
    variant: variantReducer,
    user: userPersistedReducer,
    bullet: bulletReducer,
    hashtag: hashtagPersistedReducer,
    product: productPersistedReducer,
    shop: shopPersistedReducer,
    comment: commentPersistedReducer,
    review: reviewPersistedReducer,
    admin: adminPersistedReducer,
    category: categoryPersistedReducer,
    storeCategory: storeCategoryPersistedReducer,
    option: optionReducer,
    optionValue: optionValueReducer,
    payment: paymentReducer,
    image: imageReducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        ignoredActions: [FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER],
      },
    }),
});

export let persistor = persistStore(store);
