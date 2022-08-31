/* eslint-disable react/jsx-props-no-spreading */
import styled from "styled-components";
import { SocialIcons } from "src/assets/images/SocialIcons";
import { ButtonHTMLAttributes } from "react";

interface Props extends ButtonHTMLAttributes<HTMLButtonElement> {
  children: React.ReactNode;
  width?: number;
  height?: number;
  bgColor?: string;
  color?: string;
  borderRadius?: number;
  mode: "kakao" | "google" | "naver" | "github" | "facebook";
  onClick?: () => void;
}

interface SocialType {
  bgColor: string;
  color: string;
  logoIcon: string;
}

interface SocialIconsOptionType {
  [key: string]: SocialType;
}

const SocialLoginOption: SocialIconsOptionType = {
  kakao: {
    bgColor: "#F8DF02",
    color: "black",
    logoIcon: SocialIcons.kakaoLogo,
  },
  naver: {
    bgColor: "#39B35D",
    color: "white",
    logoIcon: SocialIcons.naverLogo,
  },
  google: {
    bgColor: "#EAEDEF",
    color: "black",
    logoIcon: SocialIcons.googleLogo,
  },
  facebook: {
    bgColor: "#4267B2",
    color: "white",
    logoIcon: SocialIcons.facebookLogo,
  },
  github: {
    bgColor: "#222222",
    color: "white",
    logoIcon: SocialIcons.githubLogo,
  },
};

const SocialLoginButton = ({ mode, children, ...props }: Props) => {
  const { bgColor, color, logoIcon } = SocialLoginOption[mode];
  return (
    <Button
      {...props}
      bgColor={bgColor}
      color={color}
      onClick={props.onClick}
      mode={mode}
    >
      <img src={logoIcon} alt="kakaoLogoIcons" />
      <span>{children}</span>
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
