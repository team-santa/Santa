/* eslint-disable jsx-a11y/no-static-element-interactions */
/* eslint-disable react/jsx-no-undef */
/* eslint-disable jsx-a11y/click-events-have-key-events */
import { Slider, Comment, DeleteModal } from "src/components";
import { AiOutlineArrowLeft } from "react-icons/ai";
import { useNavigate, useParams } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "src/redux";
import { useEffect, useState } from "react";
import { addComment, getReviewDetail } from "src/redux/actions/review";
import { useModal } from "src/components/Modal";
import { getDateToString } from "src/utils";
import { useUser } from "src/utils/localStorage";
import parse from "html-react-parser";
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
  const user = useUser();
  const [inputValue, setInputValue] = useState("");
  const { reviewDetail } = useAppSelector((state) => state.review);
  const { openModal, closeModal } = useModal({
    position: { x: "50%", y: "50%" },
    height: "110px",
    width: "70%",
  });

  const handleAddComment = () => {
    dispatch(
      addComment({
        userId: user.memberId,
        body: inputValue,
      })
    );
    setInputValue("");
  };

  useEffect(() => {
    dispatch(getReviewDetail({ reviewBoardId: params.id as string }));
    return () => closeModal();
  }, [closeModal, dispatch, params]);

  if (reviewDetail) {
    return (
      <Wrapper>
        <section className="title-container">
          <AiOutlineArrowLeft onClick={() => navigate(-1)} />
          <h1>{reviewDetail.title}</h1>
          <div className="info-container">
            <div>
              <img src={reviewDetail.profileImageUrl} alt="profile" />
              <span className="author-name">{reviewDetail.writer}</span>
              <span>·</span>
              <span>{getDateToString(reviewDetail.modifiedAt)}</span>
              <span>·</span>
              <span>조회 {reviewDetail.views}</span>
            </div>
            {!user ||
              (user.memberId === reviewDetail.memberId && (
                <div>
                  <span onClick={() => navigate(`/main/write/${params.id}`)}>
                    수정
                  </span>
                  <span>·</span>
                  <span
                    onClick={() =>
                      openModal(
                        <DeleteModal
                          type="review"
                          reviewBoardId={reviewDetail.reviewBoardId}
                        />
                      )
                    }
                  >
                    삭제
                  </span>
                </div>
              ))}
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
          {parse(reviewDetail.body)}
        </section>
        <SCommentsContainer>
          <h1>댓글</h1>
          {reviewDetail.commentList.map((comment) => (
            <Comment
              key={comment.commentId}
              commentId={comment.commentId}
              memberId={comment.memberId}
              profileImageUrl={comment.profileImageUrl}
              writer={comment.writer}
              modifiedAt={comment.modifiedAt}
              body={comment.body}
            />
          ))}
        </SCommentsContainer>
        <SInputContainer>
          <SInput
            type="text"
            placeholder="댓글을 남겨주세요 :)"
            value={inputValue}
            onChange={(e) => setInputValue(e.target.value)}
          />
          <button type="button" onClick={handleAddComment}>
            등록
          </button>
        </SInputContainer>
      </Wrapper>
    );
  }

  return <div />;
};

export default ReviewDetail;
