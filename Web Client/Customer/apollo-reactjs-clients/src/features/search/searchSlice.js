import { createSlice } from '@reduxjs/toolkit';

export const searchSlice = createSlice({
    name: 'search',
    initialState: {
        query: '',  // Initial state for the search query
    },
    reducers: {
        setSearchQuery: (state, action) => {
            state.query = action.payload;  // Set the search query
        },
        clearSearchQuery: (state) => {
            state.query = '';  // Clear the search query
        },
    },
});

export const { setSearchQuery, clearSearchQuery } = searchSlice.actions;

export default searchSlice.reducer;
