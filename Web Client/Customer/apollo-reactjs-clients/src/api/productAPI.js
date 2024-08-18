import axios from "axios";

const PRODUCT_MANAGEMENT_API = "http://localhost:9999/api/products";

export const findProducts = async (shopId) => {
    let result = null;
    try {
        result = await axios.get(`${PRODUCT_MANAGEMENT_API}/{shopId}`);
    } catch (e) {
        console.log("Find books API error: " + e);
    }
    return result;
};

export const findProduct = async (productId) => {
    let result = null;
    try {
        result = await axios.get(`${PRODUCT_MANAGEMENT_API}/shops/${productId}`);
    } catch (e) {
        console.log("Find book API error: " + e);
    }
    return result;
};

export const createProduct = async ({product, storeId, categoryId, storeCategoryId}) => {
    let result = null;
    try {
        result = await axios.post(
            `http://localhost:9999/api/product-detail/create/${storeId}/${categoryId}/${storeCategoryId}`,
            product
        );
    } catch (e) {
        console.log("create product API error: " + e);
    }
    return result;
};


export const updateProduct = async (product) => {
    let result = null;
    try {
        result = await axios.put(`${PRODUCT_MANAGEMENT_API}/${product.id}`, product);
    } catch (e) {
        console.log("Update book API error: " + e);
    }
    return result;
};