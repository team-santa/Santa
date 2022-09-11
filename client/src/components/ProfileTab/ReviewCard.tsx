import React from "react";
import styled from "styled-components";

interface Props {
  title: string;
  img: string;
  tags: string[];
}

const ReviewCard = ({ title, img, tags }: Props) => {
  return (
    <CardBox>
      <ImgBox>
        <img src={img} alt="ReviewImg" />
      </ImgBox>
      <ContentBox>
        <h1>{title.length >= 34 ? `${title.slice(0, 35)}...` : title}</h1>
        <TagBox>
          {tags.map((tag) => (
            <span key={tag}>{tag}</span>
          ))}
        </TagBox>
      </ContentBox>
    </CardBox>
  );
};

export default ReviewCard;

const CardBox = styled.div`
  border: solid 1px lightgray;
  border-radius: 1rem;

  height: 6.7rem;
  margin-bottom: 1rem;
  display: flex;
`;

const ImgBox = styled.div`
  img {
    border-top-left-radius: 1rem;
    border-bottom-left-radius: 1rem;
    width: 12.5rem;
    height: 100%;
    object-fit: cover;
  }
`;
const ContentBox = styled.div`
  display: flex;
  flex-direction: column;
  h1 {
    height: 60%;
    width: 90%;
    padding-top: 5px;
    padding-right: 1rem;
    padding-left: 1rem;
    font-size: 1.4rem;
    color: #444;
    font-weight: 555;
  }
`;

const TagBox = styled.div`
  height: 1rem;
  display: flex;
  padding: 1rem;
  align-items: center;
  gap: 0.5rem;
  & > span {
    padding: 0.5rem 1rem;
    font-size: 1rem;
    border-radius: 16px;
    background-color: #d4effe;
  }
`;
