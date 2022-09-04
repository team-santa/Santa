/* eslint-disable camelcase */
import React, { useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { setCookie } from "src/utils/cookie";
import jwt_decode from "jwt-decode";

const GetToken = () => {
  const [searchParams] = useSearchParams();
  useEffect(() => {
    const token = searchParams.get("token");
    if (token) {
      setCookie("token", token, { path: "/", maxAge: 40000 });
      const decoded = jwt_decode(token);
      localStorage.setItem("user", JSON.stringify(decoded));
    }

    if (searchParams.get("signup")) {
      window.location.replace("/main/signup");
    } else {
      window.location.replace("/main");
    }
  });
  return <div>GetToken</div>;
};

export default GetToken;
