import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import {
  findCartLines,
  updateCartLine,
  addCartLine,
  removeCartLine,
  clearAllCartLine,
  findSaveForLater,
  addSaveForLater,
  removeSaveForLater,
} from "../../api/CartAPI";

export const getCartLines = createAsyncThunk(
  "cartLine/list",
  async (userId) => {
    const response = await findCartLines(userId);
    return response.data;
  }
);

export const addNewCartLine = createAsyncThunk(
  "cartLine/add",
  async (cartLine) => {
    const response = await addCartLine(cartLine);
    return response.data;
  }
);

export const editCartLine = createAsyncThunk(
  "cartLine/edit",
  async (cartLine) => {
    const response = await updateCartLine(cartLine);
    return response.data;
  }
);

export const clearCartLine = createAsyncThunk(
  "cartLine/clearAll",
  async (cartId) => {
    const response = await clearAllCartLine(cartId);
    return response.data;
  }
);

export const deleteCartLine = createAsyncThunk(
  "cartLine/delete",
  async (cartLineId) => {
    const response = await removeCartLine(cartLineId);
    return response.data;
  }
);

export const getSaveForLater = createAsyncThunk(
  "saveForLater/list",
  async (cartId) => {
    const response = await findSaveForLater(cartId);
    return response.data;
  }
);

export const createSaveForLater = createAsyncThunk(
  "saveForLater/add",
  async (product) => {
    const response = await addSaveForLater(product);
    return response.data;
  }
);

export const deleteSaveForLater = createAsyncThunk(
  "saveForLater/delete",
  async (saveForLaterId) => {
    const response = await removeSaveForLater(saveForLaterId);
    return response.data;
  }
);

const initialState = {
  products: [],
  empties: [],
  success: false,
  loading: false,
  error: false,
};

export const cartSlice = createSlice({
  name: "cart",
  initialState,
  reducers: {
    addToCart: (state, action) => {
      const item = state.products.find(
        (item) => item.variantDto.id === action.payload.variantDto.id
      );
      if (item) {
        item.quantity += action.payload.quantity;
      } else {
        state.products.push(action.payload);
      }
    },
    incrementQuantity: (state, action) => {
      const item = state.products.find(
        (item) => item.variantDto.id === action.payload.variantDto.id
      );
      item.quantity++;
    },
    decrementQuantity: (state, action) => {
      const item = state.products.find(
        (item) => item.variantDto.id === action.payload.variantDto.id
      );
      if (item.quantity === 1) {
        item.quantity = 1;
      } else {
        item.quantity--;
      }
    },
    deleteItem: (state, action) => {
      state.products = state.products.filter(
        (item) => item.variantDto.id !== action.payload
      );
    },
    resetCart: (state) => {
      state.products = [];
    },
    resetSaveForLater: (state) => {
      state.empties = [];
    },
    saveForLater: (state, action) => {
      const item = state.empties.find(
        (item) => item.variantDto.id === action.payload.variantDto.id
      );
      if (!item) {
        state.empties.push(action.payload);
      }
    },
    deleteEmpties: (state, action) => {
      state.empties = state.empties.filter(
        (item) => item.variantDto.id !== action.payload
      );
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(getCartLines.pending, (state) => {
        state.success = false;
        state.loading = true;
        state.error = false;
      })
      .addCase(getCartLines.rejected, (state, action) => {
        state.success = false;
        state.loading = false;
        state.error = action.error;
      })
      .addCase(getCartLines.fulfilled, (state, action) => {
        state.success = true;
        state.loading = false;
        state.error = false;
        state.products = action.payload;
      })
      .addCase(addNewCartLine.pending, (state) => {
        state.success = false;
        state.loading = true;
        state.error = false;
      })
      .addCase(addNewCartLine.rejected, (state, action) => {
        state.success = false;
        state.loading = false;
        state.error = action.error;
      })
      .addCase(addNewCartLine.fulfilled, (state, action) => {
        state.success = true;
        state.loading = false;
        state.error = false;
        const item = state.products.find(
          (item) => item.id === action.payload.id
        );
        if (item) {
          item.quantity += 1;
        } else {
          state.products.push(action.payload);
        }
      })
      .addCase(editCartLine.pending, (state) => {
        state.success = false;
        state.loading = true;
        state.error = false;
      })
      .addCase(editCartLine.rejected, (state, action) => {
        state.success = false;
        state.loading = false;
        state.error = action.error;
      })
      .addCase(editCartLine.fulfilled, (state, action) => {
        state.success = true;
        state.loading = false;
        state.error = false;
        const item = state.products.find(
          (item) => item.id === action.meta.arg.id
        );
        item.quantity = action.meta.arg.quantity;
      })
      .addCase(clearCartLine.pending, (state) => {
        state.success = false;
        state.loading = true;
        state.error = false;
      })
      .addCase(clearCartLine.rejected, (state, action) => {
        state.success = false;
        state.loading = false;
        state.error = action.error;
      })
      .addCase(clearCartLine.fulfilled, (state) => {
        state.success = true;
        state.loading = false;
        state.error = false;
        state.products = [];
      })
      .addCase(deleteCartLine.pending, (state) => {
        state.success = false;
        state.loading = true;
        state.error = false;
      })
      .addCase(deleteCartLine.rejected, (state, action) => {
        state.success = false;
        state.loading = false;
        state.error = action.error;
      })
      .addCase(deleteCartLine.fulfilled, (state, action) => {
        state.success = true;
        state.loading = false;
        state.error = false;
        state.products = state.products.filter(
          (item) => item.id !== action.meta.arg
        );
      })
      .addCase(getSaveForLater.pending, (state) => {
        state.success = false;
        state.loading = true;
        state.error = false;
      })
      .addCase(getSaveForLater.rejected, (state, action) => {
        state.success = false;
        state.loading = false;
        state.error = action.error;
      })
      .addCase(getSaveForLater.fulfilled, (state, action) => {
        state.success = true;
        state.loading = false;
        state.error = false;
        state.empties = action.payload;
      })
      .addCase(createSaveForLater.pending, (state) => {
        state.success = false;
        state.loading = true;
        state.error = false;
      })
      .addCase(createSaveForLater.rejected, (state, action) => {
        state.success = false;
        state.loading = false;
        state.error = action.error;
      })
      .addCase(createSaveForLater.fulfilled, (state, action) => {
        state.success = true;
        state.loading = false;
        state.error = false;
        const item = state.empties.find(
          (item) => item.variantDto.id === action.payload.variantDto.id
        );
        if (!item) {
          state.empties.push(action.payload);
        }
      })
      .addCase(deleteSaveForLater.pending, (state) => {
        state.success = false;
        state.loading = true;
        state.error = false;
      })
      .addCase(deleteSaveForLater.rejected, (state, action) => {
        state.success = false;
        state.loading = false;
        state.error = action.error;
      })
      .addCase(deleteSaveForLater.fulfilled, (state, action) => {
        state.success = true;
        state.loading = false;
        state.error = false;
        state.empties = state.empties.filter(
          (item) => item.id !== action.meta.arg
        );
      });
  },
});

export const {
  addToCart,
  deleteItem,
  resetCart,
  decrementQuantity,
  incrementQuantity,
  saveForLater,
  deleteEmpties,
  resetSaveForLater,
} = cartSlice.actions;

export default cartSlice.reducer;
