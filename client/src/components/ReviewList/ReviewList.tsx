import { REVIEW_LIST } from "src/utils";
import { Wrapper } from "./ReviewListWrapper";

const ReviewList = () => {
  return (
    <Wrapper>
      {REVIEW_LIST.map((review) => (
        <li key={review.id}>
          <div className="review-content-container">
            <div className="review-content">
              {review.title.length > 30 ? (
                <h3>{review.title.slice(0, 25)} ...</h3>
              ) : (
                <h3>{review.title}</h3>
              )}
              <div className="profile-container">
                <img src={review.profileImg} alt="profile" />
                <span>{review.author}</span>
              </div>
              <div className="util-container">
                <span>{review.date}</span>
                <span>댓글 {review.comment}</span>
                <span>조회 {review.view}</span>
              </div>
            </div>
            <div className="img-container">
              <img src={review.img} alt="mountain" />
            </div>
          </div>
          <div className="review-hashtag-container">
            {review.hashTag.map((tag) => (
              <span key={tag}>{tag}</span>
            ))}
          </div>
        </li>
      ))}
    </Wrapper>
  );
};

export default ReviewList;
