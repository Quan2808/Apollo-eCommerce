import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { findAdmin } from "../../api/adminAPI";

const initialState = {
  adminInfo: null,
  loading: false,
  success: false,
  error: false,
};

export const setAdminInfo = createAsyncThunk("admin/info", async (adminID) => {
  const token = window.localStorage.getItem("admin_token");
  const response = await findAdmin(adminID, token);
  return response.data;
});

export const adminSlice = createSlice({
  name: "admin",
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
    logOutAdmin: (state) => {
      state.adminInfo = null;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(setAdminInfo.pending, (state) => {
        state.success = false;
        state.loading = true;
        state.error = false;
      })
      .addCase(setAdminInfo.rejected, (state, action) => {
        state.success = false;
        state.loading = false;
        state.error = action.error;
      })
      .addCase(setAdminInfo.fulfilled, (state, action) => {
        state.success = true;
        state.loading = false;
        state.adminInfo = action.payload;
        state.error = false;
      });
  },
});

export const { setLoading, setError, setSuccess, logOutAdmin } =
  adminSlice.actions;

export const selectLoading = (state) => state.admin.loading;
export const selectError = (state) => state.admin.error;
export const selectSuccess = (state) => state.admin.success;
export const selectAdminDetail = (state) => state.admin.adminInfo;

export const setLoadingTrueIfCalled = (isCalled) => (dispatch, getState) => {
  const currentValue = selectLoading(getState());
  if (currentValue === isCalled) {
    dispatch(setLoading(true));
  }
};

export default adminSlice.reducer;
