/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable jsx-a11y/no-static-element-interactions */

import { useCallback, useState } from "react";
import { useNavigate } from "react-router-dom";
import { DropDown, ReviewList } from "src/components";
import { testAsyncAction, useAppDispatch } from "src/redux";
import { REGION_LIST, MOUNTAIN_LIST, HIKING_TRAIL_LIST } from "src/utils";
import { Wrapper } from "./ReviewWrapper";

const Review = () => {
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

  const handleClick = useCallback(
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

  const dispatch = useAppDispatch();

  return (
    <Wrapper selected={sortByViews}>
      <div className="dropdown-column">
        <DropDown
          width="100%"
          list={REGION_LIST}
          name="region"
          isOpen={dropDownIsOpen}
          value={dropDownValue.region}
          setValue={setDropDownValue}
          handleClick={handleClick}
        />
        <DropDown
          width="100%"
          list={MOUNTAIN_LIST}
          name="mountain"
          isOpen={dropDownIsOpen}
          value={dropDownValue.mountain}
          setValue={setDropDownValue}
          handleClick={handleClick}
        />
      </div>
      <div className="dropdown-column">
        <DropDown
          width="100%"
          list={HIKING_TRAIL_LIST}
          name="hikingTrail"
          isOpen={dropDownIsOpen}
          value={dropDownValue.hikingTrail}
          setValue={setDropDownValue}
          handleClick={handleClick}
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
        <ReviewList />
      </section>
    </Wrapper>
  );
};

export default Review;
