/* eslint-disable consistent-return */
/* eslint-disable camelcase */
import { createAsyncThunk } from "@reduxjs/toolkit";
import { CreateAsyncThunkTypes } from "src/redux";
import { Decode, LocalUser, User } from "src/types/User";
import jwt_decode from "jwt-decode";
import { axiosInstance } from "src/utils";

export const getUserAction = createAsyncThunk<
  LocalUser,
  string,
  CreateAsyncThunkTypes
>("test/testAsyncAction", async (payload) => {
  try {
    const decoded: Decode = jwt_decode(payload);
    const memberId = decoded.sub;

    const response = await axiosInstance.get(`/members/${memberId}`, {
      headers: {
        Authorization: `Bearer ${payload}`,
      },
    });

    const user: User = response.data;

    const mergeUser = {
      ...user,
      memberId,
    };

    localStorage.setItem("user", JSON.stringify(mergeUser));

    return mergeUser;
  } catch (error: any) {
    throw new Error("유저 정보 등록 실패");
  }
});
