/* eslint-disable react/no-unused-prop-types */
/* eslint-disable jsx-a11y/click-events-have-key-events */
import { useNavigate } from "react-router-dom";
import { getDateToString } from "src/utils";
import { Wrapper } from "./ReviewCardWrapper";

const noImg =
  "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQBKEGmmEQ4WlpXIfdqhhaFbJER2pXMLOFU3A&usqp=CAU";

interface Prop {
  reviewBoardId: number;
  title: string;
  thumbnail: string;
  views: number;
  writer: string;
  profileImgUrl: string;
  modifiedAt: string;
  tagList: string[];
}

const ReviewCard = ({
  reviewBoardId,
  title,
  thumbnail,
  views,
  writer,
  profileImgUrl,
  modifiedAt,
  tagList,
}: Prop) => {
  const navigate = useNavigate();

  return (
    <Wrapper>
      <li
        key={reviewBoardId}
        onClick={() => navigate(`/main/review/${reviewBoardId}`)}
      >
        <div className="review-content-container">
          <div className="review-content">
            {title.length > 24 ? (
              <h3>{title.slice(0, 24)} ...</h3>
            ) : (
              <h3>{title}</h3>
            )}
            <div className="profile-container">
              <img src={profileImgUrl} alt="profile" />
              <span>{writer}</span>
            </div>
            <div className="util-container">
              <span>{getDateToString(modifiedAt)}</span>
              <span>·</span>
              <span>조회 {views}</span>
            </div>
          </div>
          <div className="img-container">
            <img src={thumbnail || noImg} alt="mountain" />
          </div>
        </div>
        <div className="review-hashtag-container">
          {tagList.map((tag) => <span key={tag}>{tag}</span>).slice(0, 2)}
        </div>
      </li>
    </Wrapper>
  );
};

export default ReviewCard;
