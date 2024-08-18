import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { findProducts } from "../../api/homeAPI";

export const getProducts = createAsyncThunk("products/list", async () => {
  const response = await findProducts();
  return response.data;
});

const initialState = {
  products: [],
  success: false,
  loading: false,
  error: false,
};

export const homeSlice = createSlice({
  name: "home",
  initialState,
  reducers: {},
  extraReducers: (builder) =>{
    builder
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
        state.error = false;
        state.products = action.payload;
      });
  }
})

export default homeSlice.reducer