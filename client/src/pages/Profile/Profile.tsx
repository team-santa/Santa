import { useNavigate } from "react-router-dom";
import BaseButton from "src/components/BaseButton/BaseButton";
import Dialog from "src/components/Dialog/Dialog";
import styled from "styled-components";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";

const MyReviews = [
  "A산 리뷰",
  "B산 리뷰",
  "C산 리뷰 ",
  "D산 리뷰",
  "E산 리뷰",
  "K산 리뷰",
];

const Profile = () => {
  const navigate = useNavigate();

  return (
    <Container>
      {true || (
        <Dialog
          confirm={() => navigate("/main/login")}
          close={() => navigate("/main/")}
        />
      )}
      <ProfileBox>
        <img />
        <BaseButton
          text="프로필 수정"
          width={35}
          bgColor="lightgrey"
          textColor="black"
        />
      </ProfileBox>
      <MyReviewBox>
        <h1>내 리뷰</h1>
        <Swiper
          spaceBetween={50}
          slidesPerView={3}
          onSlideChange={() => console.log("slide change")}
          onSwiper={(swiper) => console.log(swiper)}
        >
          {MyReviews.map((text, idx) => (
            <SwiperSlide key={text}>
              <DummyCard>{text}</DummyCard>
            </SwiperSlide>
          ))}
        </Swiper>
      </MyReviewBox>
      <InterestBox>
        <h1>보고 싶은 산 목록</h1>
        <div className="content">
          <DummyCard2>관악산</DummyCard2>
          <DummyCard2>설악산</DummyCard2>
          <DummyCard2>한라산</DummyCard2>
        </div>
      </InterestBox>
    </Container>
  );
};

export default Profile;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100%;
  padding: 0rem 2rem;
  overflow: scroll;

  h1 {
    font-size: 2.5rem;
    margin: 1rem 0rem;
  }
`;

const ProfileBox = styled.div`
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
  width: 100%;
  margin-top: 2rem;

  img {
    width: 15rem;
    height: 15rem;
    border-radius: 50%;
    background-color: black;
    margin-bottom: 1rem;
  }
`;
const MyReviewBox = styled.div`
  width: 100%;
  margin: 1rem 0rem;
`;
const InterestBox = styled.div`
  width: 100%;
`;

const DummyCard = styled.div`
  width: 10rem;
  height: 10rem;
  background-color: #3a3f47;
  color: white;
  border-radius: 1rem;
  padding: 1rem;
`;

const DummyCard2 = styled.div`
  display: flex;
  align-items: center;
  /* justify-content: center; */
  background-color: lightgray;
  width: 100%;
  height: 5rem;
  border-radius: 0.5rem;
  margin: 0.5rem 0rem;
`;
