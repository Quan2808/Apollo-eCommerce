import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { findUser } from "../../api/userAPI";

const initialState = {
  userInfo: null,
  loading: false,
  success: false,
  error: false,
};

export const setUserInfo = createAsyncThunk(
  "user/info",
  async (userID) => {
    const token = window.localStorage.getItem("token");
    console.log(userID);
    const response = await findUser(userID, token);
    return response.data;
  }
);

export const userSlice = createSlice({
  name: "user",
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
        logOutUser:(state) => {
          state.userInfo = null;
        }
    },
    extraReducers: (builder) => {
    builder
      .addCase(setUserInfo.pending, (state) => {
        state.success = false;
        state.loading = true;
        state.error = false;
      })
      .addCase(setUserInfo.rejected, (state, action) => {
        state.success = false;
        state.loading = false;
        state.error = action.error;
      })
      .addCase(setUserInfo.fulfilled, (state, action) => {
        state.success = true;
        state.loading = false;
        state.userInfo = action.payload;
        state.error = false;
      })
    }
})

export const {
  setLoading,
  setError,
  setSuccess,
  logOutUser
} = userSlice.actions;

export const selectLoading = (state) => state.user.loading;
export const selectError = (state) => state.user.error;
export const selectSuccess = (state) => state.user.success;
export const selectUserDetail = (state) => state.user.userInfo;


export const setLoadingTrueIfCalled = (isCalled) => (dispatch, getState) => {
  const currentValue = selectLoading(getState());
  if (currentValue === isCalled) {
    dispatch(setLoading(true));
  }
};

export default userSlice.reducer;