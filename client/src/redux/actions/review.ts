/* eslint-disable consistent-return */
import { createAsyncThunk } from "@reduxjs/toolkit";
import { ReviewListPayload, ReviewList, ReviewDetail } from "src/types/index";
import { axiosInstance } from "../../utils/axiosInstance";
import { CreateAsyncThunkTypes } from "../store/index";

export const getReviewList = createAsyncThunk<
  ReviewList | undefined,
  ReviewListPayload,
  CreateAsyncThunkTypes
>("review/getReviewList", async (payload, thunkAPI) => {
  try {
    const { currentPage, pageInfo } = thunkAPI.getState().review;
    const { local, mountain, course } = payload;
    if (currentPage <= pageInfo.totalPages) {
      console.log(local, mountain, course, currentPage);
      const response = await axiosInstance.get(
        `/v1/reviewboards/local=${local}/mountain=${mountain}/course=${course}/page=${currentPage}`
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
