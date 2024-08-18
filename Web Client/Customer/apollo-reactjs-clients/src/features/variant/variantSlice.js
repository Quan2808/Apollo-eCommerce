import { createAsyncThunk, createSlice } from "@reduxjs/toolkit"
import { findVariant, findVariantById, createVariant, updateVariantAfterCreate, deleteVariantAfterCreate } from "../../api/variantAPI";

const initialState = {
    variant: null,
    variantDetail: null,
    variants:[],
    loading: false,
    error: null,
    success: false,
}

export const getVariant = createAsyncThunk("variant/detail", async (productId) => {
  const response = await findVariant(productId);
    return response.data;
})
export const getVariantInfo = createAsyncThunk("variant/info", async (productId) => {
  const response = await findVariantById(productId);
  return response.data;
})
export const addVariant = createAsyncThunk("create/variant", async (variant, variantId) => {
  const response = await createVariant(variant, variantId);
  console.log(response.data);
  return response.data;
});
export const updateVariant = createAsyncThunk("update/variant", async (variant, variantId) => {
  const response = await updateVariantAfterCreate(variant, variantId);
  console.log(response.data);
  return response.data;
});
export const deleteVariant = createAsyncThunk("delete/variant", async (variantId) => {
  const response = await deleteVariantAfterCreate(variantId);
  console.log(response.data);
  return response.data;
});
export const variantSlice = createSlice({
    name: "variant",
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
        }
    },
    extraReducers: (builder) => {
    builder
    //Update states of get variant action
      .addCase(getVariant.pending, (state) => {
        state.success = false;
        state.loading = true;
        state.error = false;
      })
      .addCase(getVariant.rejected, (state, action) => {
        state.success = false;
        state.loading = false;
        state.error = action.error;
      })
      .addCase(getVariant.fulfilled, (state, action) => {
        state.success = true;
        state.loading = false;
        state.variant = action.payload;
        state.error = false;
      })
      .addCase(getVariantInfo.pending, (state) => {
        state.success = false;
        state.loading = true;
        state.error = false;
      })
      .addCase(getVariantInfo.rejected, (state, action) => {
        state.success = false;
        state.loading = false;
        state.error = action.error;
      })
      .addCase(getVariantInfo.fulfilled, (state, action) => {
        state.success = true;
        state.loading = false;
        state.variantDetail = action.payload;
        state.error = false;
      })
      .addCase(addVariant.pending, (state) => {
        state.success = false;
        state.loading = true;
        state.error = false;
      })
      .addCase(addVariant.rejected, (state, action) => {
        state.success = false;
        state.loading = false;
        state.error = action.error;
      })
      .addCase(addVariant.fulfilled, (state, action) => {
        state.success = true;
        state.loading = false;
        state.variants.push(action.payload.variantDto);
        state.error = false;
      })
      .addCase(updateVariant.pending, (state) => {
        state.success = false;
        state.loading = true;
        state.error = false;
      })
      .addCase(updateVariant.rejected, (state, action) => {
        state.success = false;
        state.loading = false;
        state.error = action.error;
      })
      .addCase(updateVariant.fulfilled, (state, action) => {
        state.success = true;
        state.loading = false;
        const item = state.variants.find(
          (item) => item.id === action.payload.id
        );
        if (item) {
          item.name = action.payload.name;
          item.skuCode = action.payload.skuCode;
          item.stockQuantity = action.payload.stockQuantity;
          item.weight = action.payload.weight;
          item.price = action.payload.price;
          item.salePrice = action.payload.salePrice;
        } else {
          // 
        }
        state.error = false;
      })
      .addCase(deleteVariant.pending, (state) => {
        state.success = false;
        state.loading = true;
        state.error = false;
      })
      .addCase(deleteVariant.rejected, (state, action) => {
        state.success = false;
        state.loading = false;
        state.error = action.error;
      })
      .addCase(deleteVariant.fulfilled, (state, action) => {
        state.success = true;
        state.loading = false;
        const item = state.variants.find(
          (item) => item.id === action.payload
        );
        if (item) {
          state.variants = state.variants.filter(
            (item) => item.id !== action.payload
          );
        } else {
          // 
        }
        state.error = false;
      })
    }
})
export const {
  setLoading,
  setError,
  setSuccess,
} = variantSlice.actions;

export const selectLoading = (state) => state.variant.loading;
export const selectError = (state) => state.variant.error;
export const selectSuccess = (state) => state.variant.success;
export const selectVariantDetail = (state) => state.variant.variant;
export const selectVariantInfo = (state) => state.variant.variantDetail;
export const selectVariantListCreated = (state) => state.variant.variants;

export default variantSlice.reducer;

