import styled from "styled-components";

interface Props {
  isOpen: boolean;
  width: string;
}

export const Wrapper = styled.div<Props>`
  width: ${(props) => props.width};
  font-size: 1.3rem;
  position: relative;

  button {
    display: flex;
    justify-content: center;
    position: relative;
    align-items: center;
    width: inherit;
    padding: 1rem 0;
    border: none;
    border-radius: 4px;
    font-weight: 500;
    background: #e8f4fa;
    cursor: pointer;
    &:hover {
      background-color: #d4effe;
    }
  }

  svg {
    position: absolute;
    right: 1rem;
    font-size: 2rem;
  }

  ul {
    width: inherit;
    position: absolute;
    display: ${(props) => (props.isOpen ? "block" : "none")};
    margin-top: 1rem;
    border: 1px solid #016483;
    border-radius: 4px;
    font-size: 1.3rem;
    max-height: 27.2rem;
    cursor: pointer;
    overflow: auto;
    z-index: 10;
    animation: growDown 250ms ease-in;
    transform-origin: top center;
  }

  li {
    display: flex;
    justify-content: center;
    padding: 1rem;
    border-bottom: 1px solid lightgray;
    background-color: white;

    &:hover {
      background-color: #d4effe;
    }
  }

  @keyframes growDown {
    0% {
      transform: scaleY(0);
    }
    100% {
      transform: scaleY(1);
    }
  }
`;
