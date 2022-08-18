import React from "react";
import { colors } from "src/utils/colors";
import styled from "styled-components";
import BaseButton from "../BaseButton/BaseButton";

interface Props {
  title?: string;
  content?: string;
  confirm?: () => void;
  close?: () => void;
}

const Dialog = ({ title, content, confirm, close }: Props) => {
  return (
    <Container>
      <Content>
        <div />
        <h1>{title ?? "로그인 후 이용해주세요."}</h1>
        <p>
          {content ??
            "이 서비스가 마음에 드시나요 ? 좀 더 다양한 기능을 사용 하시려면 로그인을 해주세요 ."}
        </p>
        <div className="buttons">
          <BaseButton
            width={9}
            text="닫기"
            textColor={colors.mainColor}
            borderColor={colors.mainColor}
            onClick={close}
          />
          <BaseButton
            width={9}
            text="로그인"
            bgColor={colors.mainColor}
            onClick={confirm}
          />
        </div>
      </Content>
    </Container>
  );
};

export default Dialog;

const Container = styled.div`
  z-index: 10;
  position: fixed;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  left: 0;
  top: 0;
  height: 100vh;
  background: rgba(0, 0, 0, 0.5);
`;

const Content = styled.div`
  padding: 2rem;
  background-color: white;
  width: 30rem;
  height: 20rem;
  display: flex;
  flex-direction: column;
  border-radius: 1rem;

  h1 {
    font-size: 2.2rem;
    font-weight: bold;
    margin: 1.2rem 0rem;
  }

  p {
    margin: 1.5rem 0rem;
    font-size: 1.95rem;
    color: #444;
    line-height: 2.5rem;
  }

  .buttons {
    display: flex;
    justify-content: flex-end;
  }
`;
