import React, { useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";

/*
- Oauth 로그인시 토큰이 url에 담겨서 오기 때문에 url에 있는 token을 빼서 저장하고 
  특정 페이지로 리 다이렉트 시켜주는 페이지
  // TODO : 저장을 어디에 해야할까 ?? 쿠키 ? 
*/
const GetToken = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const navigate = useNavigate();
  useEffect(() => {
    console.log(searchParams, searchParams.get("token"));
    if (searchParams.get("token")) {
      navigate("/main");
    }
  });
  return <div>GetToken</div>;
};

export default GetToken;
