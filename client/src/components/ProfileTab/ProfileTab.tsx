import React, { useEffect, useState } from "react";
import { colors } from "src/utils/colors";
import styled from "styled-components";
import San from "src/assets/images/san.jpg";
import { useAppDispatch } from "src/redux";
import { axiosAuthInstance } from "src/utils";
import { useUser } from "src/utils/localStorage";
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
    img: "https://www.hwasun.go.kr/upfiles/gallery/0000000016/L_0000000016_20160119142646_0.jpg",
    tags: ["날씨좋음", "등린이", "무등산"],
  },
  {
    id: 3,
    title: "관악산 산책하기 후기 입니당 ~~",
    img: "https://t1.daumcdn.net/cfile/blog/998207415BD6585C2B",
    tags: ["관악산", "날씨좋음", "등린이"],
  },
  {
    id: 4,
    title: "유달산 산책하기 후기 입니당 ~~",
    img: "https://t1.daumcdn.net/cfile/tistory/99C9F63C5D6DC55B0C",
    tags: ["등린이"],
  },
  {
    id: 5,
    title: "마이산 너무 좋아요 !!",
    img: "https://www.mpva.go.kr/site/hogug/images/contents/cts615_img1.jpg",
    tags: ["날씨좋음", "등린이"],
  },
];

interface Review {
  reviewBoardId: number;
  tagList: string[];
  title: string;
  thumbnail: string;
}

const ProfileTab = () => {
  const [currentTab, setCurrentTab] = useState("Review");
  const user = useUser();
  const dispatch = useAppDispatch();
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
        console.log(results[0].value.data);
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
