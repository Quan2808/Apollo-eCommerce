import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import {
    findReview,
    findReviewByProductId,
    createReview,
} from "../../api/reviewAPI";

const initialState = {
    reviews: [],
    reviewsByProduct :null,
    review: null,
    loading: false,
    error: null,
    success: false,
};

export const getReviewsByVariantId = createAsyncThunk("getreview", async (variantId) => {
    const response = await findReview(variantId);
    return response.data;
});

export const getReviewByProductId = createAsyncThunk("Reviews", async (productId) => {
    const response = await findReviewByProductId(productId);
    return response.data;
});

export const addReview = createAsyncThunk("Review/create", async (review,variantId, userId) => {
    const response = await createReview(review, variantId, userId);
    return response.data;
});



export const reviewSlide = createSlice({
    name: "review",
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
            //Update states of get books action
            .addCase(getReviewsByVariantId.pending, (state) => {
                state.success = false;
                state.loading = true;
                state.error = false;
            })
            .addCase(getReviewsByVariantId.rejected, (state, action) => {
                state.success = false;
                state.loading = false;
                state.error = action.error;
            })
            .addCase(getReviewsByVariantId.fulfilled, (state, action) => {
                state.success = true;
                state.loading = false;
                state.reviews = action.payload;
                state.error = false;
            })

            //Update states of get book action
            .addCase(getReviewByProductId.pending, (state) => {
                state.success = false;
                state.loading = true;
                state.error = false;
            })
            .addCase(getReviewByProductId.rejected, (state, action) => {
                state.success = false;
                state.loading = false;
                state.error = action.error;
            })
            .addCase(getReviewByProductId.fulfilled, (state, action) => {
                state.success = true;
                state.loading = false;
                state.reviewsByProduct = action.payload;
                state.error = false;
            })

            //Update states of add book action
            .addCase(addReview.pending, (state) => {
                state.success = false;
                state.loading = true;
                state.error = false;
            })
            .addCase(addReview.rejected, (state, action) => {
                state.success = false;
                state.loading = false;
                state.error = action.error;
            })
            .addCase(addReview.fulfilled, (state, action) => {
                state.success = true;
                state.loading = false;
                state.review = action.payload;
                state.error = false;
            })
    },
});

export const {
    setLoading,
    setError,
    setSuccess,
} = reviewSlide.actions;

export const selectLoading = (state) => state.shop.loading;
export const selectError = (state) => state.shop.error;
export const selectSuccess = (state) => state.shop.success;
export const selectReviewListByVariantId = (state) => state.review.reviews;
export const selectReviewListByProductId = (state) => state.review.reviewsByProduct;
export const selectReviewCreated = (state) => state.review.review;

//Enhancement feature of book slice
export const setLoadingTrueIfCalled = (isCalled) => (dispatch, getState) => {
    const currentValue = selectLoading(getState());
    if (currentValue === isCalled) {
        dispatch(setLoading(true));
    }
};

export default reviewSlide.reducer;
