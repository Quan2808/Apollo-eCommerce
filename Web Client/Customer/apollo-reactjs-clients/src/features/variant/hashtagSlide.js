import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { createHashtag } from '../../api/hashtagAPI';

const initialState = {
    hashtag: null,
    loading: false,
    error: null,
    success: false,
};

export const addHashtag = createAsyncThunk("hashtag/create", async (hashtag) => {
    const response = await createHashtag(hashtag);
    return response.data;
});

export const hashtagSlide = createSlice({
    name: "hashtag",
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
            //Update states of add book action
            .addCase(addHashtag.pending, (state) => {
                state.success = false;
                state.loading = true;
                state.error = false;
            })
            .addCase(addHashtag.rejected, (state, action) => {
                state.success = false;
                state.loading = false;
                state.error = action.error;
            })
            .addCase(addHashtag.fulfilled, (state, action) => {
                state.success = true;
                state.loading = false;
                state.hashtag = action.payload;
                state.error = false;
            })
    },
});
export const {
    setLoading,
    setError,
    setSuccess,
} = hashtagSlide.actions;
export const selectLoading = (state) => state.hashtag.loading;
export const selectError = (state) => state.hashtag.error;
export const selectSuccess = (state) => state.hashtag.success;
export const selectHashtag = (state) => state.hashtag.comment;


//Enhancement feature of book slice
export const setLoadingTrueIfCalled = (isCalled) => (dispatch, getState) => {
    const currentValue = selectLoading(getState());
    if (currentValue === isCalled) {
        dispatch(setLoading(true));
    }
};

export default hashtagSlide.reducer;