import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import {
    searchCategory, findCategory
} from "../../api/categoryAPI";
const initialState = {
    categories: [],
    category: null,
    loading: false,
    error: null,
    success: false,
};
export const getCategoryResult = createAsyncThunk("cate/list", async (searchParam) => {
    const response = await searchCategory(searchParam);
    console.log(response);
    return response.data.categoryDtoList;
});
export const getCategory = createAsyncThunk("cate/detail", async (categoryId) => {
    const response = await findCategory(categoryId);
    return response.data;
});
export const categorySlide = createSlice({
    name: "category",
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
            .addCase(getCategoryResult.pending, (state) => {
                state.success = false;
                state.loading = true;
                state.error = false;
            })
            .addCase(getCategoryResult.rejected, (state, action) => {
                state.success = false;
                state.loading = false;
                state.error = action.error;
            })
            .addCase(getCategoryResult.fulfilled, (state, action) => {
                state.success = true;
                state.loading = false;
                state.categories = action.payload;
                state.error = false;
            })
            //Update states of get category action
            .addCase(getCategory.pending, (state) => {
                state.success = false;
                state.loading = true;
                state.error = false;
            })
            .addCase(getCategory.rejected, (state, action) => {
                state.success = false;
                state.loading = false;
                state.error = action.error;
            })
            .addCase(getCategory.fulfilled, (state, action) => {
                state.success = true;
                state.loading = false;
                state.category = action.payload;
                state.error = false;
            })
    },
});
export const {
    setLoading,
    setError,
    setSuccess,
} = categorySlide.actions;
export const selectLoading = (state) => state.category.loading;
export const selectError = (state) => state.category.error;
export const selectSuccess = (state) => state.category.success;
// export const selectCategoryList = (state) => state.category.categories;
// export const selectCategory = (state) => state.category.category;



//Enhancement feature of book slice
export const setLoadingTrueIfCalled = (isCalled) => (dispatch, getState) => {
    const currentValue = selectLoading(getState());
    if (currentValue === isCalled) {
        dispatch(setLoading(true));
    }
};

export default categorySlide.reducer;