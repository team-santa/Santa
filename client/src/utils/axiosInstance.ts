import axios from "axios";

export const axiosInstance = axios.create({
  baseURL: "https://gloom.loca.lt", // 서버 배포 후 서버 url로 변경
});
