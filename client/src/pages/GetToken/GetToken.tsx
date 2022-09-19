/* eslint-disable camelcase */
import React, { useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { LoadingSpinner } from "src/components";
import { useAppDispatch } from "src/redux";
import { getUserAction } from "src/redux/actions/getUserAction";
import { setCookie } from "src/utils/cookie";

const GetToken = () => {
  const getAccessToken = async (token: string) => {
    await dispatch(getUserAction(token));
  };

  const [searchParams] = useSearchParams();
  const dispatch = useAppDispatch();

  useEffect(() => {
    const token = searchParams.get("token");

    if (token) {
      setCookie("token", token, { path: "/" });

      (async () => {
        await getAccessToken(token);
      })();

      setTimeout(() => {
        if (searchParams.get("signup") === "true") {
          window.location.replace("/main/signup");
        } else {
          window.location.replace("/main");
        }
      }, 1000);
    }
  });

  return <LoadingSpinner />;
};

export default GetToken;
