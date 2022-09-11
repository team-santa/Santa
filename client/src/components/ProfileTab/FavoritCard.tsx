import React from "react";
import styled from "styled-components";
import { MdFavorite } from "react-icons/md";

interface Props {
  title: string;
}

const FavoritCard = ({ title }: Props) => {
  return (
    <FavoritBox>
      <ImgBox>
        <MdFavorite />
      </ImgBox>
      <span>{title}</span>
    </FavoritBox>
  );
};

export default FavoritCard;

const FavoritBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  width: 7rem;
  height: 7rem;
  border: solid 1px red;
  border-radius: 1rem;

  & > span {
    font-size: 1.5rem;
    color: #333;
    font-weight: bold;
  }

  &:hover {
    box-shadow: 0 0 5px #f774d4;
  }
`;

const ImgBox = styled.div`
  color: red;
  font-size: 3.5rem;
`;
