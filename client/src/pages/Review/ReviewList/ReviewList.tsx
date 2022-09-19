/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable jsx-a11y/no-static-element-interactions */

import { useCallback, useEffect, useState } from "react";
import { DropDown, LoadingSpinner, ReviewCard } from "src/components";
import { useInfiniteScroll } from "src/hooks/useInfiniteScroll";
import {
  changeSelectedPlace,
  changeSortByViews,
  increasePage,
  resetOption,
  resetPage,
  useAppDispatch,
  useAppSelector,
} from "src/redux";
import {
  getCourseList,
  getLocalList,
  getMountainList,
  getReviewList,
  getSpecificReviewList,
} from "src/redux/actions/review";
import { ChageSelectedPlace } from "src/types/review";
import { Wrapper, SListContainer } from "./ReviewListWrapper";

const ReviewList = () => {
  // http://www.bullmetrix.com/wp-content/plugins/elementor/assets/images/no-search-results.svg
  const dispatch = useAppDispatch();
  const {
    isLoading,
    reviewList,
    localList,
    mountainList,
    courseList,
    sortByViews,
  } = useAppSelector((state) => state.review);

  const handleInfiniteScroll = () => {
    dispatch(increasePage());
    dispatch(getReviewList());
  };
  const setObservationTarget = useInfiniteScroll(handleInfiniteScroll);

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
    const { name } = payload;
    dispatch(changeSelectedPlace(payload));

    if (name === "local") {
      dispatch(getMountainList());
    }

    if (name === "mountain") {
      dispatch(getCourseList());
    }

    dispatch(resetPage());
    dispatch(getSpecificReviewList());
  };

  const handleSortByViews = () => {
    dispatch(changeSortByViews(true));
    dispatch(getSpecificReviewList());
  };

  const handleSortByNewest = () => {
    dispatch(changeSortByViews(false));
    dispatch(getSpecificReviewList());
  };

  useEffect(() => {
    dispatch(resetOption());
    dispatch(getLocalList());
    dispatch(getReviewList());
  }, [dispatch]);

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
            <span className="new-first" onClick={handleSortByNewest}>
              최신순
            </span>
            <span className="view-first" onClick={handleSortByViews}>
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
              profileImgUrl={review.profileImageUrl}
              modifiedAt={review.modifiedAt}
              views={review.views}
              tagList={review.tagList}
            />
          ))}
          {isLoading && <LoadingSpinner />}
        </SListContainer>
        <div ref={setObservationTarget} />
      </section>
    </Wrapper>
  );
};

export default ReviewList;
