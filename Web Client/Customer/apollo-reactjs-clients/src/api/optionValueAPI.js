import axios from "axios";
const OPTION_VALUE_MANAGEMENT_API =
    "http://localhost:9999/api/option-value";

export const findOption = async (productId) => {
    let result = null;
    try {
        result = await axios.get(`${OPTION_VALUE_MANAGEMENT_API}/${productId}`);
        console.log(result);
    } catch (e) {
        console.log("Find variant API error: " + e);
    }
    return result;
};

export const createOptionValue = async ({ optionValues, optionId }) => {
    let result = null;
    try {
        result = await axios.post(`${OPTION_VALUE_MANAGEMENT_API}/${optionId}/create`, optionValues);
    } catch (e) {
        console.log("create variant API error: " + e);
    }
    return result;
};