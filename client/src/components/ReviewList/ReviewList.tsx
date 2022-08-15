/* eslint-disable jsx-a11y/click-events-have-key-events */
import { useNavigate } from "react-router-dom";
import { REVIEW_LIST } from "src/utils";
import { Wrapper } from "./ReviewListWrapper";

const ReviewList = () => {
  const navigate = useNavigate();

  return (
    <Wrapper>
      {REVIEW_LIST.map((review) => (
        <li
          key={review.id}
          onClick={() => navigate(`/main/review/${review.id}`)}
        >
          <div className="review-content-container">
            <div className="review-content">
              {review.title.length > 26 ? (
                <h3>{review.title.slice(0, 26)} ...</h3>
              ) : (
                <h3>{review.title}</h3>
              )}
              <div className="profile-container">
                <img src={review.profileImg} alt="profile" />
                <span>{review.author}</span>
              </div>
              <div className="util-container">
                <span>{review.date}</span>
                <span>·</span>
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
