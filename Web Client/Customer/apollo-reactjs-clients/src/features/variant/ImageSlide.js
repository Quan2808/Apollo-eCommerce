import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import {
    createImages
} from "../../api/ImageAPI";
const initialState = {
    images: [],
    image: {},
    loading: false,
    error: null,
    success: false,
};

export const createImagesList = createAsyncThunk("image/create", async (imageUrls, variantId) => {
    const response = await createImages(imageUrls, variantId);
    console.log(response.data);
    return response.data;
});
export const imageSlide = createSlice({
    name: "image",
    initialState,
    reducers: {
        setLoading: (state, action) => {
            state.loading = action.payload;
        },
        setError: (state, action) => {
            state.error = action.payload;
        },
        setSuccess: (state, action) => {
            state.success = action.payload;
        },
    },
    extraReducers: (builder) => {
        builder
            .addCase(createImagesList.pending, (state) => {
                state.success = false;
                state.loading = true;
                state.error = false;
            })
            .addCase(createImagesList.rejected, (state, action) => {
                state.success = false;
                state.loading = false;
                state.error = action.error;
            })
            .addCase(createImagesList.fulfilled, (state, action) => {
                state.success = true;
                state.loading = false;
                state.image = action.payload;
                state.error = false;
            })
    },
});
export const {
    setLoading,
    setError,
    setSuccess,
} = imageSlide.actions;
export const selectLoading = (state) => state.option.loading;
export const selectError = (state) => state.option.error;
export const selectSuccess = (state) => state.option.success;




export default imageSlide.reducer;