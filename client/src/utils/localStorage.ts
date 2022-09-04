import { getCookie } from "./cookie";

export const useUser = () => {
  const user =
    localStorage.getItem("user") && getCookie("token")
      ? JSON.parse(localStorage.getItem("user")!)
      : null;

  return user;
};
