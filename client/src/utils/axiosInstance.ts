import axios from "axios";

export const axiosInstance = axios.create({
  baseURL: "http://localhost:8080", // 서버 배포 후 서버 url로 변경
});
