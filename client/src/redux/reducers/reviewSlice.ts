import { createSlice, Reducer } from "@reduxjs/toolkit";
import {
  REVIEW_LIST,
  REGION_LIST,
  MOUNTAIN_LIST,
  HIKING_TRAIL_LIST,
} from "src/utils";

const initialState = {
  data: REVIEW_LIST,
  regionList: REGION_LIST,
  mountainList: MOUNTAIN_LIST,
  hikingTrailList: HIKING_TRAIL_LIST,
};

const reviewSlice = createSlice({
  name: "review",
  initialState,
  reducers: {},
});

// export const {} = reviewSlice.actions;
export const reviewReducer: Reducer<typeof initialState> = reviewSlice.reducer;
