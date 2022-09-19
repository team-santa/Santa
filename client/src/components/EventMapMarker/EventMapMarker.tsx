import React, { useState } from "react";
import { MapMarker } from "react-kakao-maps-sdk";
import { ILatlng } from "src/types/kakaomap";
import { IoClose, IoHeart } from "react-icons/io5";
import styled from "styled-components";

interface Props {
  position: ILatlng;
  content: string;
  difficulty: string;
  onClick: () => void;
  selectedMarker: number | null;
  idx: number;
}

const EventMapMarker = ({
  position,
  content,
  difficulty,
  onClick,
  selectedMarker,
  idx,
}: Props) => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <MapMarker
      position={position} // 마커를 표시할 위치
      onClick={() => {
        onClick();
        setIsOpen(selectedMarker === idx);
      }}
    >
      {isOpen && (
        <WrapperContainer>
          <div className="info">
            <Title>
              {content}
              <button type="button" onClick={() => setIsOpen(false)}>
                <IoClose />
              </button>
            </Title>
            <MountainInfo>난이도 {difficulty} | 리뷰 0</MountainInfo>
            <ButtonContainer>
              <Button className="like" type="button">
                <IoHeart />
              </Button>
              <Button className="review" type="button">
                리뷰보기
              </Button>
            </ButtonContainer>
          </div>
        </WrapperContainer>
      )}
    </MapMarker>
  );
};

export default EventMapMarker;

const WrapperContainer = styled.div`
  font-size: 1.4rem;
  padding: 0.5rem;
  width: 100%;
`;

const Title = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 1.6rem;
  font-weight: bold;
  color: #666;
  width: 100%;

  & button {
    border: none;
    background-color: transparent;
    font-size: 2rem;
    color: #666;
  }
`;

const MountainInfo = styled.div`
  color: #888;
  font-size: 1.3rem;
  display: flex;
  margin-bottom: 4px;
  margin-top: -2px;
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: space-around;
  align-items: center;

  & .like {
    border: 1px solid #cccccc;
    background-color: transparent;
    color: red;
    font-size: 1.6rem;
  }

  & .review {
    border: none;
    background-color: #016483;
    color: white;
    font-size: 1.2rem;
    font-weight: bold;
  }
`;

const Button = styled.button`
  border-radius: 3px;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 22px;
  width: 65px;
`;
