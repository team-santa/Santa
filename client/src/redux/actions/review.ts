/* eslint-disable consistent-return */
import { createAsyncThunk } from "@reduxjs/toolkit";
import { ReviewListPayload, ReviewList, ReviewDetail } from "src/types/index";
import { axiosInstance } from "../../utils/axiosInstance";
import { CreateAsyncThunkTypes } from "../store/index";

export const getReviewList = createAsyncThunk<
  ReviewList | undefined,
  undefined,
  CreateAsyncThunkTypes
>("review/getReviewList", async (payload, thunkAPI) => {
  try {
    const {
      currentPage,
      pageInfo,
      selectedCourse,
      selectedLocal,
      selectedMountain,
    } = thunkAPI.getState().review;
    if (currentPage <= pageInfo.totalPages) {
      console.log(selectedLocal, selectedMountain, selectedCourse, currentPage);
      const response = await axiosInstance.get(
        `/v1/reviewboards/local=${selectedLocal}/mountain=${selectedMountain}/course=${selectedCourse}/page=${currentPage}`
      );
      return response.data;
    }
  } catch (error: any) {
    return thunkAPI.rejectWithValue(error.message);
  }
});

export const deleteReview = createAsyncThunk(
  "review/deleteReview",
  async (payload: { reviewBoardId: string }) => {
    try {
      await axiosInstance.delete(`/v1/reviewboards/${payload.reviewBoardId}`);
    } catch (error: any) {
      console.log(error.message);
    }
  }
);

export const getReviewDetail = createAsyncThunk<
  ReviewDetail,
  { reviewBoardId: string },
  CreateAsyncThunkTypes
>("review/getReviewDetail", async (payload, thunkAPI) => {
  try {
    const response = await axiosInstance.get(
      `v1/reviewboards/${payload.reviewBoardId}`
    );
    return response.data;
  } catch (error: any) {
    return thunkAPI.rejectWithValue(error.message);
  }
});
