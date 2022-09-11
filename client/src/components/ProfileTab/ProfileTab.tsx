import React, { useState } from "react";
import { colors } from "src/utils/colors";
import styled from "styled-components";
import San from "src/assets/images/san.jpg";
import ReviewCard from "./ReviewCard";
import FavoritCard from "./FavoritCard";

const TabMenu = ["Review", "Favorite"];

const Reviews = [
  {
    id: 1,
    title: "관악산 산책하기 후기 입니당 !! 관악산 날씨가 너무 좋네요 ~~",
    img: San,
    tags: ["관악산", "날씨좋음", "등린이"],
  },
  {
    id: 2,
    title: "무등산 등산 후기 !!",
    img: San,
    tags: ["날씨좋음", "등린이"],
  },
  {
    id: 3,
    title: "관악산 산책하기 후기 입니당 ~~",
    img: San,
    tags: ["관악산", "날씨좋음", "등린이"],
  },
  {
    id: 4,
    title: "관악산 산책하기 후기 입니당 ~~",
    img: San,
    tags: ["관악산", "날씨좋음", "등린이"],
  },
  {
    id: 5,
    title: "관악산 산책하기 후기 입니당 ~~",
    img: San,
    tags: ["관악산", "날씨좋음", "등린이"],
  },
];

//
const Favorits: any[] = [
  { id: 1, mountine: "동백산" },
  { id: 2, mountine: "무등산" },
  { id: 3, mountine: "맛동산" },
  { id: 4, mountine: "한라산" },
  { id: 5, mountine: "관악산" },
];

const ProfileTab = () => {
  const [currentTab, setCurrentTab] = useState("Review");
  return (
    <Container>
      <TabHeader>
        {TabMenu.map((tab) => (
          <Tab
            key={tab}
            Tab={tab}
            currentTab={currentTab}
            onClick={() => setCurrentTab(tab)}
          >
            {tab}
          </Tab>
        ))}
      </TabHeader>
      <TabBody>
        {currentTab === "Review" ? (
          <div>
            {Reviews.length === 0 ? (
              <Empty>
                <img
                  src="http://www.bullmetrix.com/wp-content/plugins/elementor/assets/images/no-search-results.svg"
                  alt="No-ContentImg"
                />
                <h2>작성한 리뷰가 없습니다.</h2>
              </Empty>
            ) : (
              Reviews.map((obj) => (
                <ReviewCard
                  key={obj.id}
                  title={obj.title}
                  img={obj.img}
                  tags={obj.tags}
                />
              ))
            )}
          </div>
        ) : (
          <div>
            {Favorits.length === 0 ? (
              <Empty>
                <img
                  src="http://www.bullmetrix.com/wp-content/plugins/elementor/assets/images/no-search-results.svg"
                  alt="No-ContentImg"
                />
                <h2>좋아하는 산을 등록해 주세요.</h2>
              </Empty>
            ) : (
              <FavoritContainer>
                {Favorits.map((obj) => (
                  <FavoritCard key={obj.id} title={obj.mountine} />
                ))}
              </FavoritContainer>
            )}
          </div>
        )}
      </TabBody>
    </Container>
  );
};

export default ProfileTab;

const Container = styled.div`
  width: 100%;
  height: 100%;
`;

const TabHeader = styled.div`
  display: flex;
  justify-content: space-around;
  color: black;
`;

const Tab = styled.h1<{ Tab: string; currentTab: string }>`
  text-align: center;
  width: 50%;
  padding-bottom: 1rem;
  font-weight: 500;
  border-bottom: ${({ Tab, currentTab }) =>
    Tab === currentTab ? `solid 4px ${colors.mainColor2}` : null};
`;

const TabBody = styled.div`
  height: 95%;
  margin-top: 0.7rem;
  overflow-y: hidden;
`;

const Empty = styled.div`
  margin-top: 8rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  h2 {
    margin-top: 2rem;
    color: #777;
  }
`;

const FavoritContainer = styled.div`
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  gap: 1.5rem;
  overflow-y: scroll;
`;
