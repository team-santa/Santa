/* eslint-disable consistent-return */
/* eslint-disable camelcase */
import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { CreateAsyncThunkTypes } from "src/redux";
import { LocalUser } from "src/types/User";
import { useUser } from "src/utils/localStorage";

interface PayLoad {
  username: string;
  profileImageUrl?: string;
}
/**
 * TODO : 헤더에서 받은 토큰을 갈아주는 작업을 해야한다.
 */
export const updateProfileAction = createAsyncThunk<
  LocalUser,
  PayLoad,
  CreateAsyncThunkTypes
>("test/testAsyncAction", async (payload) => {
  const user: LocalUser = useUser();
  const { username, profileImageUrl } = payload;
  const form = {
    username,
    profileImageUrl: profileImageUrl ?? user.profileImageUrl,
  };
  try {
    const result = await axios.put(
      `http://localhost:8080/members/${user.memberId}`,
      form
    );
    const userData = result.data;
    const mergeUser = {
      ...user,
      ...userData,
    };
    localStorage.setItem("user", JSON.stringify(mergeUser));
    return mergeUser;
  } catch (error: any) {
    throw new Error("유저 정보 업데이트 실패");
  }
});
