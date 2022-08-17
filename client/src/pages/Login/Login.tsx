import React from "react";
import SocialButton from "src/components/SocialButton/SocialButton";
import styled from "styled-components";

const Login = () => {
  return (
    <Container>
      <Logo>ㅅㅌ</Logo>
      <SocialButtons>
        <SocialButton
          mode="kakao"
          width={25}
          height={5}
          borderRadius={0.5}
          onClick={() => window.alert("카카오 로그인")}
        >
          카카오 로그인
        </SocialButton>
        <SocialButton
          mode="naver"
          width={25}
          height={5}
          borderRadius={0.5}
          onClick={() => window.alert("네이버 로그인")}
        >
          네이버 로그인
        </SocialButton>
        <SocialButton
          mode="google"
          width={25}
          height={5}
          borderRadius={0.5}
          onClick={() => window.alert("네이버 로그인")}
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
  height: 100%;
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
