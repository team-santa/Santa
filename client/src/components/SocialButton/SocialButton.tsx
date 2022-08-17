/* eslint-disable react/jsx-props-no-spreading */
import styled from "styled-components";
import { SocialIcons } from "src/assets/images/SocialIcons";

interface Props {
  children: React.ReactNode;
  width?: number;
  height?: number;
  bgColor?: string;
  color?: string;
  borderRadius?: number;
  mode?: "kakao" | "google" | "naver" | "github" | "facebook";
  onClick?: () => void;
}

const SocialLoginOption = {
  kakao: {},
  google: {},
  naver: {},
  facebook: {},
  github: {},
};

const SocialLoginButton = ({ mode, children, ...props }: Props) => {
  if (mode === "kakao") {
    return (
      <Button {...props} bgColor="#F8DF02" onClick={props.onClick}>
        <img src={SocialIcons.kakaoLogo} alt="kakaoLogoIcons" />
        <span>{children}</span>
      </Button>
    );
  }
  if (mode === "naver") {
    return (
      <Button
        {...props}
        bgColor="#39B35D"
        color="white"
        onClick={props.onClick}
      >
        <img src={SocialIcons.naverLogo} alt="kakaoLogoIcons" />
        <span>{children}</span>
      </Button>
    );
  }
  if (mode === "google") {
    return (
      <Button {...props} bgColor="#EAEDEF" onClick={props.onClick}>
        <img src={SocialIcons.googleLogo} alt="kakaoLogoIcons" />
        <span>{children}</span>
      </Button>
    );
  }
  if (mode === "facebook") {
    return (
      <Button
        {...props}
        bgColor="#4267B2"
        color="white"
        onClick={props.onClick}
      >
        <img src={SocialIcons.facebookLogo} alt="kakaoLogoIcons" />
        <span>{children}</span>
      </Button>
    );
  }
  if (mode === "github") {
    return (
      <Button
        {...props}
        bgColor="#222222"
        color="white"
        onClick={props.onClick}
      >
        <img src={SocialIcons.githubLogo} alt="kakaoLogoIcons" />
        <span>{children}</span>
      </Button>
    );
  }
  return (
    <Button {...props} bgColor="black" color="white">
      <span>{children ?? "Login"}</span>
    </Button>
  );
};

export default SocialLoginButton;

const Button = styled.button<Props>`
  margin: 0.3rem 0rem;
  position: relative;
  width: ${(props) => props.width ?? "20"}rem;
  height: ${(props) => props.height ?? "3"}rem;
  background-color: ${(props) => props.bgColor ?? "skyblue"};
  color: ${(props) => props.color ?? "black"};
  display: flex;
  justify-content: center;
  align-items: center;
  border: none;
  cursor: pointer;
  font-weight: bold;
  border-radius: ${(props) => props.borderRadius ?? 0}rem;

  & > img {
    padding-right: 0.5rem;
    width: 20px;
    height: 20px;
    left: 0;
    margin-left: 20px;
    position: absolute;
  }

  & > span {
    padding-left: 0.5rem;
  }
`;
