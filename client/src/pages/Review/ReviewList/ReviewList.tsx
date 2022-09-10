/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable jsx-a11y/no-static-element-interactions */

import { useCallback, useState } from "react";
import { DropDown, ReviewCard } from "src/components";
import { useInfiniteScroll } from "src/hooks/useInfiniteScroll";
import {
  changeSelectedPlace,
  increasePage,
  useAppDispatch,
  useAppSelector,
} from "src/redux";
import { getReviewList } from "src/redux/actions/review";
import { ChageSelectedPlace } from "src/types/review";
import { Wrapper, SListContainer } from "./ReviewListWrapper";

const ReviewList = () => {
  const dispatch = useAppDispatch();
  const { reviewList, localList, mountainList, courseList } = useAppSelector(
    (state) => state.review
  );

  const handleInfiniteScroll = () => {
    dispatch(increasePage());
    dispatch(getReviewList());
  };
  const setObservationTarget = useInfiniteScroll(handleInfiniteScroll);

  const [sortByViews, setSortByViews] = useState(false);
  const [dropDownValue, setDropDownValue] = useState({
    local: "지역",
    mountain: "산 이름",
    course: "등산로",
  });
  const [dropDownIsOpen, setDropDownIsOpen] = useState({
    local: false,
    mountain: false,
    course: false,
  });

  const handleDropDownClick = useCallback(
    (name: string) => {
      const newObj = {
        local: false,
        mountain: false,
        course: false,
      };
      newObj[name as keyof typeof newObj] =
        !dropDownIsOpen[name as keyof typeof newObj];
      setDropDownIsOpen(newObj);
    },
    [dropDownIsOpen]
  );

  const handleDispatch = (payload: ChageSelectedPlace) => {
    dispatch(changeSelectedPlace(payload));
  };

  return (
    <Wrapper selected={sortByViews}>
      <div className="dropdown-column">
        <DropDown
          width="100%"
          list={localList}
          name="local"
          isOpen={dropDownIsOpen}
          value={dropDownValue.local}
          setValue={setDropDownValue}
          handleClick={handleDropDownClick}
          dispatch={handleDispatch}
        />
        <DropDown
          width="100%"
          list={mountainList}
          name="mountain"
          isOpen={dropDownIsOpen}
          value={dropDownValue.mountain}
          setValue={setDropDownValue}
          handleClick={handleDropDownClick}
          dispatch={handleDispatch}
        />
      </div>
      <div className="dropdown-column">
        <DropDown
          width="100%"
          list={courseList}
          name="course"
          isOpen={dropDownIsOpen}
          value={dropDownValue.course}
          setValue={setDropDownValue}
          handleClick={handleDropDownClick}
          dispatch={handleDispatch}
        />
      </div>
      <section>
        <div className="review-title-container">
          <h2>리뷰 목록</h2>
          <div>
            <span className="new-first" onClick={() => setSortByViews(false)}>
              최신순
            </span>
            <span className="view-first" onClick={() => setSortByViews(true)}>
              조회순
            </span>
          </div>
        </div>
        <SListContainer>
          {reviewList.map((review) => (
            <ReviewCard
              key={review.reviewBoardId}
              reviewBoardId={review.reviewBoardId}
              title={review.title}
              thumbnail={review.thumbnail}
              writer={review.writer}
              profileImgUrl={review.profileImgUrl}
              modifiedAt={review.modifiedAt}
              views={review.views}
              tagList={review.tagList}
            />
          ))}
        </SListContainer>
        <div ref={setObservationTarget} />
      </section>
    </Wrapper>
  );
};

export default ReviewList;
