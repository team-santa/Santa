/* eslint-disable camelcase */
import React, { useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { useAppDispatch } from "src/redux";
import { getUserAction } from "src/redux/actions/getUserAction";
import { setCookie } from "src/utils/cookie";

const GetToken = () => {
  const [searchParams] = useSearchParams();
  const dispatch = useAppDispatch();

  useEffect(() => {
    const token = searchParams.get("token");

    if (token) {
      setCookie("token", token, { path: "/" });
      dispatch(getUserAction(token));
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
