/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable jsx-a11y/no-static-element-interactions */

import { useState } from "react";
import { DropDown, ReviewList } from "src/components";
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

  const handleClick = (name: string) => {
    const newObj = {
      region: false,
      mountain: false,
      hikingTrail: false,
    };
    newObj[name as keyof typeof newObj] =
      !dropDownIsOpen[name as keyof typeof newObj];
    setDropDownIsOpen(newObj);
  };

  return (
    <Wrapper selected={sortByViews}>
      <div className="dropdown-column">
        <DropDown
          name="region"
          value="지역"
          width="100%"
          list={REGION_LIST}
          isOpen={dropDownIsOpen}
          handleClick={handleClick}
        />
        <DropDown
          name="mountain"
          value="산 이름"
          width="100%"
          list={MOUNTAIN_LIST}
          isOpen={dropDownIsOpen}
          handleClick={handleClick}
        />
      </div>
      <div className="dropdown-column">
        <DropDown
          name="hikingTrail"
          value="등산로"
          width="100%"
          list={HIKING_TRAIL_LIST}
          isOpen={dropDownIsOpen}
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
