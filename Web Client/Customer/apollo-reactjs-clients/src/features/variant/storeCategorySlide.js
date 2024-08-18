import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import {
    createCategoryList, createCategory
} from "../../api/store_categoryAPI";
const initialState = {
    storecategories: [],
    storecategory: {},
    loading: false,
    error: null,
    success: false,
};
export const addCategory = createAsyncThunk("catestore/list", async (searchParam) => {
    const response = await createCategory(searchParam);
    console.log(response);
    return response.data.categoryDtoList;
});
export const createStoreCategoryList = createAsyncThunk("catestore/detail", async (categoryList,storeId) => {
    const response = await createCategoryList(categoryList,storeId);
    console.log(response.data);
    return response.data;
});
export const storeCategorySlide = createSlice({
    name: "storeCategory",
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
            .addCase(addCategory.pending, (state) => {
                state.success = false;
                state.loading = true;
                state.error = false;
            })
            .addCase(addCategory.rejected, (state, action) => {
                state.success = false;
                state.loading = false;
                state.error = action.error;
            })
            .addCase(addCategory.fulfilled, (state, action) => {
                state.success = true;
                state.loading = false;
                state.storecategory = action.payload;
                state.error = false;
            })
            //Update states of get category action
            .addCase(createStoreCategoryList.pending, (state) => {
                state.success = false;
                state.loading = true;
                state.error = false;
            })
            .addCase(createStoreCategoryList.rejected, (state, action) => {
                state.success = false;
                state.loading = false;
                state.error = action.error;
            })
            .addCase(createStoreCategoryList.fulfilled, (state, action) => {
                state.success = true;
                state.loading = false;
                state.storecategories = action.payload;
                state.error = false;
            })
    },
});
export const {
    setLoading,
    setError,
    setSuccess,
} = storeCategorySlide.actions;
export const selectLoading = (state) => state.storeCategory.loading;
export const selectError = (state) => state.storeCategory.error;
export const selectSuccess = (state) => state.storeCategory.success;
export const selectStoreCategoryList = (state) => state.storeCategory.storecategories;
export const selectStoreCategory = (state) => state.storeCategory.storecategory;



//Enhancement feature of book slice
export const setLoadingTrueIfCalled = (isCalled) => (dispatch, getState) => {
    const currentValue = selectLoading(getState());
    if (currentValue === isCalled) {
        dispatch(setLoading(true));
    }
};

export default storeCategorySlide.reducer;