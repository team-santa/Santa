/* eslint-disable camelcase */
import React, { useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { setCookie } from "src/utils/cookie";
import jwt_decode from "jwt-decode";

const GetToken = () => {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  useEffect(() => {
    const token = searchParams.get("token");
    if (token) {
      setCookie("token", token, { path: "/", maxAge: 40000 });
      const decoded = jwt_decode(token);
      localStorage.setItem("user", JSON.stringify(decoded));
    }

    if (searchParams.get("signup")) {
      navigate("/main/signup");
    } else {
      navigate("/main");
    }
  });
  return <div>GetToken</div>;
};

export default GetToken;
