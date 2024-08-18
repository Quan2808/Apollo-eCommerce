import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import {
    findCommentList,
    createComment
} from "../../api/commentAPI";

const initialState = {
    comments: null,
    comment: null,
    loading: false,
    error: null,
    success: false,
};

export const getCommentlistByReviewId = createAsyncThunk("find/CommentList", async (reviewId) => {
    const response = await findCommentList(reviewId);
    return response.data;
});

export const addComment = createAsyncThunk("create/Comment", async (comment) => {
    const response = await createComment(comment);
    return response.data;
});



export const commentSlide = createSlice({
    name: "comment",
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
            .addCase(getCommentlistByReviewId.pending, (state) => {
                state.success = false;
                state.loading = true;
                state.error = false;
            })
            .addCase(getCommentlistByReviewId.rejected, (state, action) => {
                state.success = false;
                state.loading = false;
                state.error = action.error;
            })
            .addCase(getCommentlistByReviewId.fulfilled, (state, action) => {
                state.success = true;
                state.loading = false;
                state.comments = action.payload;
                state.error = false;
            })

            

            //Update states of add book action
            .addCase(addComment.pending, (state) => {
                state.success = false;
                state.loading = true;
                state.error = false;
            })
            .addCase(addComment.rejected, (state, action) => {
                state.success = false;
                state.loading = false;
                state.error = action.error;
            })
            .addCase(addComment.fulfilled, (state, action) => {
                state.success = true;
                state.loading = false;
                state.comment = action.payload;
                state.error = false;
            })
    },
});

export const {
    setLoading,
    setError,
    setSuccess,
} = commentSlide.actions;

export const selectLoading = (state) => state.comment.loading;
export const selectError = (state) => state.comment.error;
export const selectSuccess = (state) => state.comment.success;
export const selectCommentListByReviewId = (state) => state.comment.comments;
export const selectCommentCreated = (state) => state.comment.comment;

//Enhancement feature of book slice
export const setLoadingTrueIfCalled = (isCalled) => (dispatch, getState) => {
    const currentValue = selectLoading(getState());
    if (currentValue === isCalled) {
        dispatch(setLoading(true));
    }
};

export default commentSlide.reducer;
