import axios from "axios";
import React from "react";
import SocialButton from "src/components/SocialButton/SocialButton";
import styled from "styled-components";

// 소셜 로그인 양식 주소 !!
const url = `http://localhost:8080/oauth2/authorization/{provider-id}?redirect_uri=http://localhost:3000/oauth/redirect`;
const Login = () => {
  const HandleClick = async (mode: string) => {
    window.location.href = `http://localhost:8080/oauth2/authorization/${mode}?redirect_uri=http://localhost:3000/oauth/redirect`;
  };
  return (
    <Container>
      <Logo>ㅅㅌ</Logo>
      <SocialButtons>
        <SocialButton
          mode="kakao"
          width={25}
          height={5}
          borderRadius={0.5}
          onClick={() => HandleClick("kakao")}
        >
          카카오 로그인
        </SocialButton>
        <SocialButton
          mode="naver"
          width={25}
          height={5}
          borderRadius={0.5}
          onClick={() => HandleClick("naver")}
        >
          네이버 로그인
        </SocialButton>
        <SocialButton
          mode="google"
          width={25}
          height={5}
          borderRadius={0.5}
          onClick={() => HandleClick("google")}
        >
          구글 로그인
        </SocialButton>
      </SocialButtons>
    </Container>
  );
};

export default Login;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 80vh;
`;

const Logo = styled.div``;

const SocialButtons = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  padding: 4rem;
  border: solid 2px lightgray;
  border-radius: 1rem;
  /* box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px; */
`;
