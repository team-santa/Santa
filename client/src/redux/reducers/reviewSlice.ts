/* eslint-disable no-console */
import { createSlice, PayloadAction, Reducer } from "@reduxjs/toolkit";
import {
  REVIEW_LIST,
  REGION_LIST,
  MOUNTAIN_LIST,
  HIKING_TRAIL_LIST,
} from "src/utils";
import {
  getCourseList,
  getMountainList,
  getLocalList,
  getReviewDetail,
  getReviewList,
  getSpecificReviewList,
} from "../actions/review";
import { REVIEW_DETAIL } from "../../utils/dummy-data";
import { ReviewInitialState, ChageSelectedPlace } from "../../types/review";

const initialState: ReviewInitialState = {
  isLoading: false,
  reviewList: [],
  reviewDetail: null,
  localList: [],
  mountainList: [],
  courseList: [],
  selectedLocal: "",
  selectedMountain: "",
  selectedCourse: "",
  sortByViews: false,
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
    changeSelectedPlace: (
      state,
      { payload }: PayloadAction<ChageSelectedPlace>
    ) => {
      const { name, value } = payload;
      switch (name) {
        case "local":
          state.selectedLocal = value;
          break;
        case "mountain":
          state.selectedMountain = value;
          break;
        case "course":
          state.selectedCourse = value;
      }
    },
    changeSortByViews: (state, { payload }: PayloadAction<boolean>) => {
      state.currentPage = 1;
      state.sortByViews = payload;
    },
    resetOption: (state) => {
      state.reviewList = [];
      state.localList = [];
      state.mountainList = [];
      state.courseList = [];
      state.selectedLocal = "";
      state.selectedMountain = "";
      state.selectedCourse = "";
      state.sortByViews = false;
      state.currentPage = 1;
      state.pageInfo = {
        page: 1,
        size: 10,
        totalElement: 100,
        totalPages: 3,
      };
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
          state.reviewList = [];
          state.reviewList.push(...payload.data);
          state.pageInfo = payload.pageInfo;
        }
      })
      .addCase(getReviewList.rejected, (state, { payload }) => {
        state.isLoading = false;
        console.log(payload);
      })
      .addCase(getSpecificReviewList.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getSpecificReviewList.fulfilled, (state, { payload }) => {
        state.isLoading = false;
        state.reviewList = payload.data;
        state.pageInfo = payload.pageInfo;
      })
      .addCase(getSpecificReviewList.rejected, (state, { payload }) => {
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
      })
      .addCase(getLocalList.fulfilled, (state, { payload }) => {
        const list = payload.map((el) => el.localName);
        state.localList = list;
      })
      .addCase(getMountainList.fulfilled, (state, { payload }) => {
        const list = payload.map((el) => el.mountainName);
        state.mountainList = list;
      })
      .addCase(getCourseList.fulfilled, (state, { payload }) => {
        const list = payload.map((el) => el.courseName);
        state.courseList = list;
      }),
});

export const {
  increasePage,
  resetPage,
  resetOption,
  changeSelectedPlace,
  changeSortByViews,
} = reviewSlice.actions;
export const reviewReducer: Reducer<typeof initialState> = reviewSlice.reducer;
