import React from "react";
import SocialButton from "src/components/SocialButton/SocialButton";
import styled from "styled-components";
import Logo from "src/assets/images/santa_log.svg";
import { colors } from "src/utils/colors";

const Login = () => {
  const HandleClick = async (mode: string) => {
    window.location.href = `http://localhost:8080/oauth2/authorization/${mode}?redirect_uri=http://localhost:3000/oauth/redirect`;
  };

  return (
    <Container>
      <LogoBox>
        <img src={Logo} alt="Logo" />
      </LogoBox>
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

const LogoBox = styled.div`
  margin-bottom: 2rem;
`;

const SocialButtons = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  padding: 4rem;
  border-radius: 1rem;
  border: solid 1px ${colors.mainColor};
`;
