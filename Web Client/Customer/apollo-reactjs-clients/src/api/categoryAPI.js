import axios from "axios";

const CATEGORY_MANAGEMENT_API = "http://localhost:9999/api/category";

export const searchCategory = async (searchParam) => {
    let result = null;
    try {
        result = await axios.get(`${CATEGORY_MANAGEMENT_API}/search?query=${searchParam}`);
        console.log(result);
    } catch (e) {
        console.log("Find category API error: " + e);
    }
    return result;
};
export const findCategory = async (categoryId) => {
    let result = null;
    try {
        result = await axios.get(`${CATEGORY_MANAGEMENT_API}/${categoryId}`);
    } catch (e) {
        console.log("Find book API error: " + e);
    }
    return result;
};