import React, { useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { setCookie } from "src/utils/cookie";

/*
- Oauth 로그인시 토큰이 url에 담겨서 오기 때문에 url에 있는 token을 빼서 저장하고 
  특정 페이지로 리 다이렉트 시켜주는 페이지
  // TODO : 저장을 어디에 해야할까 ?? 쿠키 ? 
*/
const GetToken = () => {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  useEffect(() => {
    // Token을 빼서 쿠키에 저장 하는 로직 추가해야함
    const token = searchParams.get("token");
    if (token) setCookie("token", token);
    navigate("/main");
  });
  return <div>GetToken</div>;
};

export default GetToken;
