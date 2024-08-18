import axios from "axios";

const STORECATE_MANAGEMENT_API =
    "http://localhost:9999/api/store-category";


export const createCategory = async (category) => {
    let result = null;
    try {
        result = await axios.post(`${STORECATE_MANAGEMENT_API}`, category);
    } catch (e) {
        console.log("create book API error: " + e);
    }
    return result;
};
export const createCategoryList = async ({categoryList, storeId}) => {
    let result = null;
    try {
        result = await axios.post(`${STORECATE_MANAGEMENT_API}/${storeId}`, categoryList);
        console.log(result);
    } catch (e) {
        console.log("create categoryList API error: " + e);
    }
    return result;
};

