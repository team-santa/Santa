import { createAsyncThunk } from "@reduxjs/toolkit";
import { ReviewListPayload, ReviewList } from "src/types/index";
import { axiosInstance } from "../../utils/axiosInstance";
import { CreateAsyncThunkTypes } from "../store/index";

export const getReviewList = createAsyncThunk<
  ReviewList,
  ReviewListPayload,
  CreateAsyncThunkTypes
>("review/getReviewList", async (payload, { rejectWithValue }) => {
  try {
    const { local, mountain, course, page } = payload;
    const response = await axiosInstance.get(
      `/v1/reviewboards/local=${local}/mountain=${mountain}/course=${course}/page=${page}`
    );
    return response.data;
  } catch (error: any) {
    return rejectWithValue(error.message);
  }
});
