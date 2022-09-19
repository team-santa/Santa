import { getCookie, removeCookie } from "./cookie";

export const useUser = () => {
  const user =
    localStorage.getItem("user") && getCookie("token")
      ? JSON.parse(localStorage.getItem("user")!)
      : null;

  return user;
};

export const LogOut = () => {
  localStorage.removeItem("user");
  removeCookie("token");
};
