/* eslint-disable camelcase */
import React, { useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { setCookie } from "src/utils/cookie";
import jwt_decode from "jwt-decode";
import axios from "axios";
import { User } from "src/types";

interface Decode {
  exp: number;
  role: string;
  sub: string;
}

const GetToken = () => {
  const [searchParams] = useSearchParams();
  useEffect(() => {
    const token = searchParams.get("token");
    if (token) {
      setCookie("token", token, { path: "/" });
      const decoded: Decode = jwt_decode(token);
      axios.get(`http://localhost:8080/members/${decoded.sub}`).then((res) => {
        const user: User = res.data;
        const memberId = decoded.sub;
        const mergeUser = {
          ...user,
          memberId,
        };
        localStorage.setItem("user", JSON.stringify(mergeUser));
      });
    }

    if (searchParams.get("signup") === "true") {
      window.location.replace("/main/signup");
    } else {
      window.location.replace("/main");
    }
  });

  return <div>GetToken</div>;
};

export default GetToken;
