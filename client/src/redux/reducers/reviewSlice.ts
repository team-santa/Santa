/* eslint-disable no-console */
import { createSlice, Reducer } from "@reduxjs/toolkit";
import {
  REVIEW_LIST,
  REGION_LIST,
  MOUNTAIN_LIST,
  HIKING_TRAIL_LIST,
} from "src/utils";
import { getReviewDetail, getReviewList } from "../actions/review";
import { REVIEW_DETAIL } from "../../utils/dummy-data";
import { ReviewInitialState } from "../../types/review";

const initialState: ReviewInitialState = {
  isLoading: false,
  reviewList: REVIEW_LIST,
  reviewDetail: REVIEW_DETAIL,
  localList: REGION_LIST,
  mountainList: MOUNTAIN_LIST,
  courseList: HIKING_TRAIL_LIST,
  currentPage: 1,
  pageInfo: {
    page: 1,
    size: 10,
    totalElement: 100,
    totalPages: 3,
  },
};

const reviewSlice = createSlice({
  name: "review",
  initialState,
  reducers: {
    increasePage: (state) => {
      state.currentPage += 1;
    },
    resetPage: (state) => {
      state.currentPage = 1;
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
      })
      .addCase(getReviewDetail.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getReviewDetail.fulfilled, (state, { payload }) => {
        state.isLoading = false;
        state.reviewDetail = payload;
      })
      .addCase(getReviewDetail.rejected, (state, { payload }) => {
        state.isLoading = false;
        console.log(payload);
      }),
});

export const { increasePage, resetPage } = reviewSlice.actions;
export const reviewReducer: Reducer<typeof initialState> = reviewSlice.reducer;
