import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import {
    createOptionValue
} from "../../api/optionValueAPI";
const initialState = {
    optionValues: [],
    optionValue: {},
    loading: false,
    error: null,
    success: false,
};

export const createOptionValueList = createAsyncThunk("option-value/create", async (optionValues, optionId) => {
    const response = await createOptionValue(optionValues, optionId);
    console.log(response.data);
    return response.data;
});
export const optionValueSlide = createSlice({
    name: "optionValue",
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
            .addCase(createOptionValueList.pending, (state) => {
                state.success = false;
                state.loading = true;
                state.error = false;
            })
            .addCase(createOptionValueList.rejected, (state, action) => {
                state.success = false;
                state.loading = false;
                state.error = action.error;
            })
            .addCase(createOptionValueList.fulfilled, (state, action) => {
                state.success = true;
                state.loading = false;
                state.optionValues.push(action.payload.optionValueDtoList);
                state.error = false;
            })
    },
});
export const {
    setLoading,
    setError,
    setSuccess,
} = optionValueSlide.actions;
export const selectLoading = (state) => state.optionValue.loading;
export const selectError = (state) => state.optionValue.error;
export const selectSuccess = (state) => state.optionValue.success;
export const selectOptionsList = (state) => state.optionValue.optionValues;
export const selectOptions = (state) => state.optionValue.optionValue;



//Enhancement feature of book slice
export const setLoadingTrueIfCalled = (isCalled) => (dispatch, getState) => {
    const currentValue = selectLoading(getState());
    if (currentValue === isCalled) {
        dispatch(setLoading(true));
    }
};

export default optionValueSlide.reducer;