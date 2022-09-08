/* eslint-disable no-console */
import { createSlice, Reducer } from "@reduxjs/toolkit";
import {
  REVIEW_LIST,
  REGION_LIST,
  MOUNTAIN_LIST,
  HIKING_TRAIL_LIST,
} from "src/utils";
import { REVIEW_DETAIL } from "../../utils/dummy-data";
import { ReviewInitialState } from "../../types/review";
import { getReviewList } from "../actions/review";

const initialState: ReviewInitialState = {
  isLoading: false,
  reviewList: REVIEW_LIST,
  reviewDetail: REVIEW_DETAIL,
  pageInfo: {
    page: 1,
    size: 10,
    totalElement: 100,
    totalPages: 3,
  },
  localList: REGION_LIST,
  mountainList: MOUNTAIN_LIST,
  courseList: HIKING_TRAIL_LIST,
  currentPage: 0,
};

const reviewSlice = createSlice({
  name: "review",
  initialState,
  reducers: {
    increasePage: (state) => {
      state.currentPage += 1;
    },
    resetPage: (state) => {
      state.currentPage = 0;
    },
  },
  extraReducers: (builder) =>
    builder // getReviewList
      .addCase(getReviewList.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getReviewList.fulfilled, (state, { payload }) => {
        state.isLoading = false;
        if (payload) {
          state.reviewList.push(...payload.data);
          state.pageInfo = payload.pageInfo;
        }
      })
      .addCase(getReviewList.rejected, (state, { payload }) => {
        state.isLoading = false;
        console.log(payload);
      }),
});

export const { increasePage, resetPage } = reviewSlice.actions;
export const reviewReducer: Reducer<typeof initialState> = reviewSlice.reducer;
