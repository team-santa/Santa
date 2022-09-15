/* eslint-disable @typescript-eslint/no-non-null-assertion */
/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable consistent-return */
import { createAsyncThunk } from "@reduxjs/toolkit";
import { ReviewList, ReviewDetail } from "src/types/index";
import { axiosAuthInstance, axiosInstance } from "../../utils/axiosInstance";
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
      sortByViews,
    } = thunkAPI.getState().review;

    const sort = sortByViews ? "views" : "newest";

    if (currentPage <= pageInfo.totalPages) {
      const response = await axiosInstance.get(
        `/reviewboards?local=${selectedLocal}&mountain=${selectedMountain}&course=${selectedCourse}&page=${currentPage}&sort=${sort}`
      );
      return response.data;
    }
  } catch (error: any) {
    return thunkAPI.rejectWithValue(error.message);
  }
});

export const getSpecificReviewList = createAsyncThunk<
  ReviewList,
  undefined,
  CreateAsyncThunkTypes
>("review/getSpecificReviewList", async (payload, thunkAPI) => {
  try {
    const {
      currentPage,
      selectedCourse,
      selectedLocal,
      selectedMountain,
      sortByViews,
    } = thunkAPI.getState().review;

    const sort = sortByViews ? "views" : "newest";

    const response = await axiosInstance.get(
      `/reviewboards?local=${selectedLocal}&mountain=${selectedMountain}&course=${selectedCourse}&page=${currentPage}&sort=${sort}`
    );
    return response.data;
  } catch (error: any) {
    return thunkAPI.rejectWithValue(error.message);
  }
});

export const deleteReview = createAsyncThunk(
  "review/deleteReview",
  async (payload: { reviewBoardId: number }, thunkAPI) => {
    try {
      await axiosInstance.delete(`/reviewboards/${payload.reviewBoardId}`);
    } catch (error: any) {
      return thunkAPI.rejectWithValue(error.message);
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
      `/reviewboards/${payload.reviewBoardId}`
    );
    return response.data;
  } catch (error: any) {
    return thunkAPI.rejectWithValue(error.message);
  }
});

export const getLocalList = createAsyncThunk<
  { localName: string }[],
  undefined,
  CreateAsyncThunkTypes
>("review/getLocalList", async (_, { rejectWithValue }) => {
  try {
    const response = await axiosInstance.get("/local");
    return response.data;
  } catch (error: any) {
    return rejectWithValue(error.message);
  }
});

export const getMountainList = createAsyncThunk<
  { mountainName: string }[],
  undefined,
  CreateAsyncThunkTypes
>("review/getMountainList", async (_, thunkAPI) => {
  try {
    const { selectedLocal } = thunkAPI.getState().review;
    const response = await axiosInstance.get(
      `/mountain/selection?localName=${selectedLocal}`
    );
    return response.data;
  } catch (error: any) {
    return thunkAPI.rejectWithValue(error.message);
  }
});

export const getCourseList = createAsyncThunk<
  { courseName: string }[],
  undefined,
  CreateAsyncThunkTypes
>("review/getCourseList", async (_, thunkAPI) => {
  try {
    const { selectedMountain } = thunkAPI.getState().review;
    const response = await axiosInstance.get(
      `/course/selection?mountainName=${selectedMountain}`
    );
    return response.data;
  } catch (error: any) {
    return thunkAPI.rejectWithValue(error.message);
  }
});

export const addComment = createAsyncThunk<
  undefined,
  { userId: string; body: string },
  CreateAsyncThunkTypes
>("review/addComment", async (payload, thunkAPI) => {
  try {
    const { userId, body } = payload;
    const { reviewBoardId } = thunkAPI.getState().review.reviewDetail!;
    const requestBody = {
      memberId: userId,
      reviewBoardId,
      commentBody: body,
    };
    return await axiosAuthInstance.post("/comment", requestBody);
    // thunkAPI.dispatch()
  } catch (error) {
    console.log(error);
  }
});

// export const editComment = createAsyncThunk<
