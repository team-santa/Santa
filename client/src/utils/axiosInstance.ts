import axios from "axios";
import { getCookie } from "./cookie";

export const axiosInstance = axios.create({
  baseURL: "http://3.38.169.199:8080", // 서버 배포 후 서버 url로 변경
});

export const axiosAuthInstance = axios.create({
  baseURL: "http://3.38.169.199:8080",
  headers: {
    Authorization: `Bearer ${getCookie("token")}`,
  },
});
