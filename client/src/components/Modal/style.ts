import styled, { css } from "styled-components";

import { fadeIn, fadeOut } from "./animation";
import { ModalStyle, Position } from "./types";

export interface BackgroundProps {
  isMount: boolean;
}

type ModalMainProps = ModalStyle & BackgroundProps;

export const Background = styled.div`
  position: fixed;
  top: 0%;
  left: 0%;
  width: 100vw;
  height: 100vh;
  background-color: rgba(60, 60, 60, 0.645);
  opacity: 0.6;
  z-index: 99;

  ${({ isMount }: BackgroundProps) => css`
    animation: ${isMount ? fadeIn : fadeOut} 0.3s linear;
  `}
  animation-fill-mode: forwards;
`;

const isPixel = ({ x, y }: Exclude<Position, undefined>) => {
  return x.match(/px/g) && y.match(/px/);
};

export const ModalMain = styled.div`
  position: fixed;
  transform: translate(-50%, -50%);
  background-color: white;
  box-shadow: 1px 1px 3px rgba(143, 143, 143, 0.897);
  box-sizing: border-box;
  z-index: 100;
  overflow: hidden;

  ${({
    width,
    height,
    minWidth,
    minHeight,
    isMount,
    position = { x: "50%", y: "50%" },
    borderRadius = "10px",
    boxShadow,
  }: ModalMainProps) => css`
    width: ${width};
    height: ${height};
    min-width: ${minWidth};
    min-height: ${minHeight};
    animation: ${isMount ? fadeIn : fadeOut} 0.3s ease-out;
    border-radius: ${borderRadius};
    box-shadow: ${boxShadow};

    ${position &&
    css`
      left: ${position.x};
      top: ${position.y};
      transform: ${isPixel(position) && "translate(0, 0)"};
    `}
  `}
  animation-fill-mode: forwards;
`;

export const Close = styled.button`
  display: flex;
  align-items: center;
  position: absolute;
  right: 0%;
  top: 0%;
  transform: translate(-50%, 100%);
  background-color: rgba(255, 255, 255, 0);
  border: 0px;
  color: red;
  font-size: 1.5rem;
  cursor: pointer;

  & > img {
    width: 20px;
    height: 20px;
  }
`;
