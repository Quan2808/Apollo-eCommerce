import axios from "axios";

const STORE_FOR_ADMIN_MANAGEMENT_API =
    "http://localhost:9999/api/stores";

export const findShops = async () => {
    let result = null;
    try {
        result = await axios.get(`${STORE_FOR_ADMIN_MANAGEMENT_API}`);
    } catch (e) {
        console.log("Find books API error: " + e);
    }
    return result;
};

export const findShop = async (shopId) => {
    let result = null;
    try {
        result = await axios.get(`${STORE_FOR_ADMIN_MANAGEMENT_API}/${shopId}`);
    } catch (e) {
        console.log("Find store API error: " + e);
    }
    return result;
};

export const createShop = async ({shop,adminId}) => {
    let result = null;
    try {
        result = await axios.post(`${STORE_FOR_ADMIN_MANAGEMENT_API}/create/${adminId}`, shop);
    } catch (e) {
        console.log("create store API error: " + e);
    }
    return result;
};


export const updateShop = async (shop) => {
    let result = null;
    try {
        result = await axios.put(`${STORE_FOR_ADMIN_MANAGEMENT_API}/${shop.id}`, shop);
    } catch (e) {
        console.log("Update book API error: " + e);
    }
    return result;
};