import axios from "axios";

const COMMENT_MANAGEMENT_API = "http://localhost:9999/api/comment";

export const findCommentList = async (reviewId) => {
    let result = null;
    try {
        result = await axios.get(`${COMMENT_MANAGEMENT_API}/{reviewId}`);
    } catch (e) {
        console.log("Find books API error: " + e);
    }
    return result;
};

export const createComment = async (comment) => {
    let result = null;
    try {
        result = await axios.post(`${COMMENT_MANAGEMENT_API}`, comment);
    } catch (e) {
        console.log("create book API error: " + e);
    }
    return result;
};
