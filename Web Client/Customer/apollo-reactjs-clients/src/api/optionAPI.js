import axios from "axios";
const OPTION_MANAGEMENT_API = "http://localhost:9999/api/option";

export const findOption = async (productId) => {
    let result = null;
    try {
        result = await axios.get(`${OPTION_MANAGEMENT_API}/${productId}`);
        console.log(result);
    } catch (e) {
        console.log("Find variant API error: " + e);
    }
    return result;
};
export const updateOption = async (Option) => {
    let result = null;
    try {
        result = await axios.put(`${OPTION_MANAGEMENT_API}/${Option.id}`, Option);
    } catch (e) {
        console.log("Update variant API error: " + e);
    }
    return result;
};
export const createOptions = async ({ optionList, productId }) => {
    let result = null;
    try {
        result = await axios.post(`${OPTION_MANAGEMENT_API}/${productId}/create`, optionList);
    } catch (e) {
        console.log("create variant API error: " + e);
    }
    return result;
};