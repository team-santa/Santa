import React, { useEffect, useState } from "react";
import { colors } from "src/utils/colors";
import styled from "styled-components";
import { axiosAuthInstance } from "src/utils";
import { useUser } from "src/utils/localStorage";
import ReviewCard from "./ReviewCard";
import FavoritCard from "./FavoritCard";

const TabMenu = ["Review", "Favorite"];

interface Review {
  reviewBoardId: number;
  tagList: string[];
  title: string;
  thumbnail: string;
}

const ProfileTab = () => {
  const [currentTab, setCurrentTab] = useState("Review");
  const user = useUser();

  const [myReview, setMyReview] = useState([]);
  const [myFavorites, setMyFavorites] = useState([]);

  useEffect(() => {
    (async () => {
      const reviews = await axiosAuthInstance.get(
        `/members/${user.memberId}/reviewboards`
      );
      const favorites = await axiosAuthInstance.get(
        `/members/${user.memberId}/mountains`
      );
      const results = await Promise.allSettled([reviews, favorites]);
      if (results[0].status === "fulfilled") {
        setMyReview(results[0].value.data);
      }
      if (results[1].status === "fulfilled") {
        setMyFavorites(results[1].value.data.mountainNames);
      }
    })();
  }, []);
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
            {myReview.length === 0 ? (
              <Empty>
                <img
                  src="http://www.bullmetrix.com/wp-content/plugins/elementor/assets/images/no-search-results.svg"
                  alt="No-ContentImg"
                />
                <h2>작성한 리뷰가 없습니다.</h2>
              </Empty>
            ) : (
              myReview.map((obj: Review) => (
                <ReviewCard
                  key={obj.reviewBoardId}
                  title={obj.title}
                  img={obj.thumbnail}
                  tags={obj.tagList}
                  id={obj.reviewBoardId}
                />
              ))
            )}
          </div>
        ) : (
          <div>
            {myFavorites.length === 0 ? (
              <Empty>
                <img
                  src="http://www.bullmetrix.com/wp-content/plugins/elementor/assets/images/no-search-results.svg"
                  alt="No-ContentImg"
                />
                <h2>좋아하는 산을 등록해 주세요.</h2>
              </Empty>
            ) : (
              <FavoritContainer>
                {myFavorites.map((moutain, idx) => (
                  <FavoritCard key={moutain} title={moutain} />
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
