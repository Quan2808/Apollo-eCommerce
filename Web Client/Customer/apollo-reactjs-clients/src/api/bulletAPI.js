import axios from "axios";

const BULLET_MANAGEMENT_API = "http://localhost:9999/api/bullet";

export const createBullet = async(bullet) => {
    let result = null;
    try {
        result = await axios.post(`${BULLET_MANAGEMENT_API}`, bullet);
    } catch (e) {
        console.log("create book API error: " + e);
    }
    return result;
};