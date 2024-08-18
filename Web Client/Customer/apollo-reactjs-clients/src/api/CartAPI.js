import axios from "axios";

const CART_MANAGEMENT_API = "http://localhost:9999/api";

export const findProductsSame = async () => {
  let result = null;
  try {
    result = await axios.get(`https://fakestoreapi.com/products?limit=20`);
  } catch (e) {
    console.log("Find products API error: " + e);
  }
  return result;
};

export const findFiveProducts = async () => {
  let result = null;
  try {
    result = await axios.get(`${CART_MANAGEMENT_API}/cart-lines`);
  } catch (e) {
    console.log("Find products API error: " + e);
  }
  return result;
};

export const findProductsRecentlyViewed = async () => {
  let result = null;
  try {
    result = await axios.get(`https://fakestoreapi.com/products?limit=12`);
  } catch (e) {
    console.log("Find products API error: " + e);
  }
  return result;
};

export const findCartLines = async (userId) => {
  let result = null;
  try {
    result = await axios.get(`${CART_MANAGEMENT_API}/cart-lines/${userId}`);
  } catch (e) {
    console.log("Find products API error: " + e);
  }
  return result;
};

export const addProductsInCart = async (cartLine) => {
  let result = null;
  try {
    result = await axios.get(
      `${CART_MANAGEMENT_API}/cart-lines/add-to-cart/${cartLine}`
    );
  } catch (e) {
    console.log("Find products API error: " + e);
  }
  return result;
};

export const updateCartLine = async (cartLine) => {
  let result = null;
  try {
    result = await axios.put(`${CART_MANAGEMENT_API}/cart-lines`, cartLine);
  } catch (e) {
    console.log("Find products API error: " + e);
  }
  return result;
};

export const addCartLine = async (product) => {
  let result = null;
  try {
    result = await axios.post(
      `${CART_MANAGEMENT_API}/cart-lines/add-to-cart`,
      product
    );
  } catch (e) {
    console.log("add product API error: " + e);
  }
  return result;
};

export const removeCartLine = async (cartLineId) => {
  let result = null;
  try {
    result = await axios.delete(
      `${CART_MANAGEMENT_API}/cart-lines/delete/${cartLineId}`
    );
  } catch (e) {
    console.log("delete products API error: " + e);
  }
  return result;
};

export const clearAllCartLine = async (cartId) => {
  let result = null;
  try {
    result = await axios.delete(
      `${CART_MANAGEMENT_API}/cart-lines/delete-all/${cartId}`
    );
  } catch (e) {
    console.log("clear products API error: " + e);
  }
  return result;
};

export const findSaveForLater = async (cartId) => {
  let result = null;
  try {
    result = await axios.get(`${CART_MANAGEMENT_API}/save-for-later/${cartId}`);
  } catch (e) {
    console.log("Find products API error: " + e);
  }
  return result;
};

export const addSaveForLater = async (product) => {
  let result = null;
  try {
    result = await axios.post(`${CART_MANAGEMENT_API}/save-for-later`, product);
  } catch (e) {
    console.log("save products API error: " + e);
  }
  return result;
};

export const removeSaveForLater = async (saveForLaterId) => {
  let result = null;
  try {
    result = await axios.delete(
      `${CART_MANAGEMENT_API}/save-for-later/delete/${saveForLaterId}`
    );
  } catch (e) {
    console.log("delete products API error: " + e);
  }
  return result;
};

