import axios from "axios";

const ADMIN_MANAGEMENT_API = "http://localhost:9999/api/admins";

export const findAdmin = async (adminId, token) => {
  let result = null;
  try {
    result = await axios.get(`${ADMIN_MANAGEMENT_API}/${adminId}`, {
      headers: { Authorization: "Bearer " + token },
    });
  } catch (e) {
    console.log("Find admin API error: " + e);
  }
  return result;
};

