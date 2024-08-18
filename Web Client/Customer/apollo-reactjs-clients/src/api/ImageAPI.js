import axios from "axios";

const IMAGE_MANAGEMENT_API = "http://localhost:9999/api/image";

export const createImages = async ({ imageFiles, variantId }) => {
    let result = null;
    const formData = new FormData();

    // Append each image file to the FormData object
    imageFiles.forEach(file => {
        formData.append("files", file);
    });

    // Append the variantId to the FormData object
    formData.append("variantId", variantId);

    try {
        result = await axios.post(`${IMAGE_MANAGEMENT_API}/create`, formData, {
            headers: {
                "Content-Type": "multipart/form-data"
            }
        });
    } catch (e) {
        console.log("create image API error: " + e);
    }

    return result;
};
