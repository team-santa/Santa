import { Slider, Comment } from "src/components";
import { AiOutlineArrowLeft } from "react-icons/ai";
import { useNavigate, useParams } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "src/redux";
import { useEffect } from "react";
import { getReviewDetail } from "src/redux/actions/review";
import {
  Wrapper,
  SCommentsContainer,
  SInput,
  SInputContainer,
} from "./ReviewDetailWrapper";

const ReviewDetail = () => {
  const params = useParams();
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const { reviewDetail } = useAppSelector((state) => state.review);

  // useEffect(() => {
  //   dispatch(getReviewDetail({ reviewBoardId: params.id as string }));
  // }, [dispatch, params]);

  return (
    <Wrapper>
      <section className="title-container">
        <AiOutlineArrowLeft onClick={() => navigate(-1)} />
        <h1>{reviewDetail.title}</h1>
        <div className="info-container">
          <div>
            <img src={reviewDetail.profileImgUrl} alt="profile" />
            <span className="author-name">{reviewDetail.writer}</span>
            <span>·</span>
            <span>{reviewDetail.modifiedAt}</span>
            <span>·</span>
            <span>조회 {reviewDetail.views}</span>
          </div>
          <div>
            <span>수정</span>
            <span>·</span>
            <span>삭제</span>
          </div>
        </div>
        <div className="hashTag-container">
          {reviewDetail.tagList.map((tag) => (
            <span key={tag}>{tag}</span>
          ))}
        </div>
      </section>
      <section className="image-container">
        <Slider imgList={[reviewDetail.thumbnail]} />
      </section>
      <section className="review-container">
        <p>{reviewDetail.body}</p>
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
