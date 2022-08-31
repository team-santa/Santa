import { ButtonHTMLAttributes } from "react";
import styled from "styled-components";

interface Props extends ButtonHTMLAttributes<HTMLButtonElement> {
  bgColor?: string;
  width?: number;
  height?: number;
  text?: string;
  borderRadius?: number;
  textColor?: string;
  fontSize?: number;
  borderColor?: string;
  onClick?: () => void;
}

const BaseButton = ({ text, onClick, ...props }: Props) => {
  return (
    // eslint-disable-next-line react/jsx-props-no-spreading
    <Button {...props} onClick={onClick}>
      {text ?? "Example"}
    </Button>
  );
};

export default BaseButton;

const Button = styled.button<Props>`
  width: ${(props) => props.width ?? 13}rem;
  height: ${(props) => props.height ?? 5}rem;
  background-color: ${({ bgColor }) => bgColor ?? "white"};
  color: ${(props) => props.textColor ?? "white"};
  font-size: ${(props) => props.fontSize ?? 1.7}rem;
  border-radius: ${({ borderRadius }) => borderRadius ?? 1}rem;
  border: ${(props) =>
    props.borderColor ? `${props.borderColor} solid 1px` : "none"};
  cursor: pointer;
  margin: 3px;
  &:hover {
    transform: scale(0.99);
  }

  &:disabled {
    filter: grayscale(70%);
  }
`;
