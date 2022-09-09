/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable jsx-a11y/no-static-element-interactions */

import { useCallback, useState } from "react";
import { DropDown, ReviewCard } from "src/components";
import { useInfiniteScroll } from "src/hooks/useInfiniteScroll";
import { increasePage, useAppDispatch, useAppSelector } from "src/redux";
import { getReviewList } from "src/redux/actions/review";

import { Wrapper, SListContainer } from "./ReviewListWrapper";

const ReviewList = () => {
  const dispatch = useAppDispatch();
  const { reviewList, localList, mountainList, courseList } = useAppSelector(
    (state) => state.review
  );

  const handleInfiniteScroll = () => {
    dispatch(increasePage());
    dispatch(
      getReviewList({
        local: "string",
        mountain: "string",
        course: "string",
      })
    );
  };
  const setObservationTarget = useInfiniteScroll(handleInfiniteScroll);

  const [sortByViews, setSortByViews] = useState(false);
  const [dropDownValue, setDropDownValue] = useState({
    region: "지역",
    mountain: "산 이름",
    hikingTrail: "등산로",
  });
  const [dropDownIsOpen, setDropDownIsOpen] = useState({
    region: false,
    mountain: false,
    hikingTrail: false,
  });

  const handleDropDownClick = useCallback(
    (name: string) => {
      const newObj = {
        region: false,
        mountain: false,
        hikingTrail: false,
      };
      newObj[name as keyof typeof newObj] =
        !dropDownIsOpen[name as keyof typeof newObj];
      setDropDownIsOpen(newObj);
    },
    [dropDownIsOpen]
  );

  return (
    <Wrapper selected={sortByViews}>
      <div className="dropdown-column">
        <DropDown
          width="100%"
          list={localList}
          name="region"
          isOpen={dropDownIsOpen}
          value={dropDownValue.region}
          setValue={setDropDownValue}
          handleClick={handleDropDownClick}
          dispatch={(name) => console.log(name)}
        />
        <DropDown
          width="100%"
          list={mountainList}
          name="mountain"
          isOpen={dropDownIsOpen}
          value={dropDownValue.mountain}
          setValue={setDropDownValue}
          handleClick={handleDropDownClick}
          dispatch={(name) => console.log(name)}
        />
      </div>
      <div className="dropdown-column">
        <DropDown
          width="100%"
          list={courseList}
          name="hikingTrail"
          isOpen={dropDownIsOpen}
          value={dropDownValue.hikingTrail}
          setValue={setDropDownValue}
          handleClick={handleDropDownClick}
          dispatch={(name) => console.log(name)}
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