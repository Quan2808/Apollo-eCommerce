import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { findStore } from "../../api/storeAPI";

const initialState = {
  storeBanner: "",
  storeInfo: {},
  categories: [],
  breadcrumb: {
    category: "",
    subCategory: "",
    bannerImage: "",
  },
  selectedCategory: null,
  selectedSubCategory: null,
  selectedCurrent: null,
  moreSideBar: false,
  moreCategoryToggle: {
    deals: false,
  },
  searchProducts: [],
  searchParams: "",
  searchParamsResult: "",
  loading: false,
  error: null,
  success: false,
};

export const setStore = createAsyncThunk("store/info", async (storeID) => {
  const response = await findStore(storeID);
  console.log(response.data);
  return response.data;
});

export const adminStoreSlice = createSlice({
  name: "adminStore",
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
    setSearchParams: (state, action) => {
      state.searchParams = action.payload;
    },
    setSearchParamsResult: (state, action) => {
      state.searchParamsResult = action.payload;
    },
    setSearchProducts: (state, action) => {
      state.searchProducts = action.payload;
    },
    setCategory: (state, action) => {
      state.categories = action.payload;
    },
    setStoreBanner: (state, action) => {
      state.storeBanner = action.payload;
    },
    setSelectedCategory: (state, action) => {
      state.selectedCategory = action.payload;
    },
    setSelectedSubCategory: (state, action) => {
      state.selectedSubCategory = action.payload;
    },
    setSelectedCurrent: (state, action) => {
      state.selectedCurrent = action.payload;
    },
    changeCategory: (state, action) => {
      state.breadcrumb.category = action.payload;
    },
    changeSubCategory: (state, action) => {
      state.breadcrumb.subCategory = action.payload;
    },
    changeBannerImage: (state, action) => {
      state.breadcrumb.bannerImage = action.payload;
    },
    toggleMoreSideBar: (state, action) => {
      state.moreSideBar = action.payload;
    },
    addMoreCategoryToggle: (state, action) => {
      state.moreCategoryToggle = {
        ...state.moreCategoryToggle,
        [action.payload]: false,
      };
    },
    toggleMoreCategory: (state, action) => {
      const categoryId = action.payload;
      state.moreCategoryToggle = {
        ...state.moreCategoryToggle,
        [categoryId]: !state.moreCategoryToggle[categoryId],
      };
    },
    toggleOffMoreCategory: (state, action) => {
      const categoryId = action.payload;
      state.moreCategoryToggle = {
        ...state.moreCategoryToggle,
        deals: false,
        [categoryId]: false,
      };
    },
    toggleOffMoreCategoryForDeals: (state, action) => {
      const categoryId = action.payload;
      state.moreCategoryToggle = {
        ...state.moreCategoryToggle,
        [categoryId]: false,
      };
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(setStore.pending, (state) => {
        state.success = false;
        state.loading = true;
        state.error = false;
      })
      .addCase(setStore.rejected, (state, action) => {
        state.success = false;
        state.loading = false;
        state.error = action.error;
      })
      .addCase(setStore.fulfilled, (state, action) => {
        state.success = true;
        state.loading = false;
        state.storeInfo = action.payload;
        state.error = false;
      });
  },
});

export const {
  setLoading,
  setError,
  setSuccess,
  setCategory,
  setSearchProducts,
  setSearchParams,
  setSearchParamsResult,
  setStoreBanner,
  setSelectedCategory,
  setSelectedSubCategory,
  setSelectedCurrent,
  changeCategory,
  changeSubCategory,
  toggleMoreSideBar,
  addMoreCategoryToggle,
  toggleMoreCategory,
  toggleOffMoreCategory,
  toggleOffMoreCategoryForDeals,
  changeBannerImage,
} = adminStoreSlice.actions;

export const selectLoading = (state) => state.adminStore.loading;
export const selectError = (state) => state.adminStore.error;
export const selectSuccess = (state) => state.adminStore.success;
export const selectBreadcrumb = (state) => state.adminStore.breadcrumb;
export const selectStore = (state) => state.adminStore.store;
export const selectCategories = (state) => state.adminStore.categories;

export default adminStoreSlice.reducer;
