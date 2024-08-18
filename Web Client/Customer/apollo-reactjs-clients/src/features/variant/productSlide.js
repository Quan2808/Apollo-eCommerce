import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import {
    findProducts,
    findProduct,
    createProduct,
    updateProduct
} from "../../api/productAPI";

const initialState = {
    products: [],
    product: {},
    loading: false,
    error: null,
    success: false,
};

export const getProducts = createAsyncThunk("product/list", async (shopId) => {
    const response = await findProducts(shopId);
    return response.data;
});

export const getProduct = createAsyncThunk("product/detail", async (productId) => {
    const response = await findProduct(productId);
    return response.data;
});

export const addProduct = createAsyncThunk("product/create", async (product,storeId,categoryId, storeCategoryId) => {
    const response = await createProduct(product, storeId, categoryId, storeCategoryId);
    return response.data;
});

export const editProduct = createAsyncThunk("book/edit", async (product) => {
    const response = await updateProduct(product);
    return response.data;
});


export const productSlice = createSlice({
    name: "product",
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
            .addCase(getProducts.pending, (state) => {
                state.success = false;
                state.loading = true;
                state.error = false;
            })
            .addCase(getProducts.rejected, (state, action) => {
                state.success = false;
                state.loading = false;
                state.error = action.error;
            })
            .addCase(getProducts.fulfilled, (state, action) => {
                state.success = true;
                state.loading = false;
                state.products = action.payload;
                state.error = false;
            })

            //Update states of get book action
            .addCase(getProduct.pending, (state) => {
                state.success = false;
                state.loading = true;
                state.error = false;
            })
            .addCase(getProduct.rejected, (state, action) => {
                state.success = false;
                state.loading = false;
                state.error = action.error;
            })
            .addCase(getProduct.fulfilled, (state, action) => {
                state.success = true;
                state.loading = false;
                state.product = action.payload;
                state.error = false;
            })

            //Update states of add book action
            .addCase(addProduct.pending, (state) => {
                state.success = false;
                state.loading = true;
                state.error = false;
            })
            .addCase(addProduct.rejected, (state, action) => {
                state.success = false;
                state.loading = false;
                state.error = action.error;
            })
            .addCase(addProduct.fulfilled, (state, action) => {
                state.success = true;
                state.loading = false;
                state.product = action.payload;
                state.error = false;
            })

            //Update states of edit book action
            .addCase(editProduct.pending, (state) => {
                state.success = false;
                state.loading = true;
                state.error = false;
            })
            .addCase(editProduct.rejected, (state, action) => {
                state.success = false;
                state.loading = false;
                state.error = action.error;
            })
            .addCase(editProduct.fulfilled, (state, action) => {
                state.success = true;
                state.loading = false;
                state.product = action.payload;
                state.error = false;
            })
    },
});

export const {
    setLoading,
    setError,
    setSuccess,
} = productSlice.actions;

export const selectLoading = (state) => state.product.loading;
export const selectError = (state) => state.product.error;
export const selectSuccess = (state) => state.product.success;
export const selectProductByShopId = (state) => state.product.products;
export const selectProductDetail = (state) => state.product.product;
export const selectProductAdded = (state) => state.product.product;
export const selectProductEdited = (state) => state.product.product;

//Enhancement feature of book slice
export const setLoadingTrueIfCalled = (isCalled) => (dispatch, getState) => {
    const currentValue = selectLoading(getState());
    if (currentValue === isCalled) {
        dispatch(setLoading(true));
    }
};

export default productSlice.reducer;
