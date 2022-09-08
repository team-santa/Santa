import { Slider, Comment } from "src/components";
import { AiOutlineArrowLeft } from "react-icons/ai";
import { useNavigate } from "react-router-dom";
import {
  Wrapper,
  SCommentsContainer,
  SInput,
  SInputContainer,
} from "./ReviewDetailWrapper";

const IMG_LIST = [
  "https://post-phinf.pstatic.net/MjAyMDEyMDRfMTE4/MDAxNjA3MDQ3NTQ5ODY2.OSsBiGkH385D4weVF6R76_9qQGWDPH6bw42eAMSqiWYg.MYdbtP5P6IsNk3WtOxc8oQcyDTvKP8W-4MPWOwrlZfEg.JPEG/%EC%A7%80%EB%A6%AC%EC%82%B01.jpg?type=w1200",
  "https://i.pinimg.com/736x/53/48/7e/53487e31f078f7a16b4f151852107cd7.jpg",
  "http://san.chosun.com/news/photo/201704/10825_44653_4757.jpg",
];

const ReviewDetail = () => {
  const navigate = useNavigate();

  return (
    <Wrapper>
      <section className="title-container">
        <AiOutlineArrowLeft onClick={() => navigate(-1)} />
        <h1>등산 초보(등린이)도 갈만한 서울 쉬운산 인왕산 데이트 등반 후기</h1>
        <div className="info-container">
          <div>
            <img
              src="data:image/jpeg;base64,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"
              alt="profile"
            />
            <span className="author-name">모상빈</span>
            <span>·</span>
            <span>20분 전</span>
            <span>·</span>
            <span>조회 10</span>
          </div>
          <div>
            <span>수정</span>
            <span>·</span>
            <span>삭제</span>
          </div>
        </div>
        <div className="hashTag-container">
          <span>경기도</span>
          <span>광교산</span>
          <span>광교산</span>
          <span>광교산</span>
        </div>
      </section>
      <section className="image-container">
        <Slider imgList={IMG_LIST} />
      </section>
      <section className="review-container">
        <p>
          한라산... 우리나라에서 가장 높은 산인데 언젠가 한번 등산하겠지라고만
          생각하고 막상 가려고하면 무서워서 못 갔던 곳... 드디어 다녀왔다 가암히
          등산 쵸보가 덤볐다가 무려 12시간 넘게 걸린 눈물없이 읽지 못할 한라산
          등반 스토리 시작합니다....
          <br />
          한라산은 오래걸리니까 아무래도 장비대여가 필요할 것같아서 한라산등산
          전문 게스트하우스로 예약했다 이름도 한라산게스트하우스 ㅎㅎ 아래 리뷰
          있으니 참고!
          <br />
          아침에 한라산 드롭을 해줘서 7시에 게하 출발했고 아래에서 김밥한줄 먹고
          7시반에 등산을 시작했다
        </p>
      </section>
      <SCommentsContainer>
        <h1>댓글</h1>
        <Comment />
        <Comment />
        <Comment />
      </SCommentsContainer>
      <SInputContainer>
        <SInput type="text" placeholder="댓글을 남겨주세요 :)" />
        <button type="button">등록</button>
      </SInputContainer>
    </Wrapper>
  );
};

export default ReviewDetail;
