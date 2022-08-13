/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable jsx-a11y/no-static-element-interactions */

import { useState } from "react";
import { DropDown, ReviewList } from "src/components";
import { REGION_LIST, MOUNTAIN_LIST, HIKING_TRAIL_LIST } from "src/utils";
import { Wrapper } from "./ReviewWrapper";

const Review = () => {
  const [selected, setIsSelected] = useState(false);

  return (
    <Wrapper selected={selected}>
      <div className="dropdown-column">
        <DropDown title="지역" width="100%" list={REGION_LIST} />
        <DropDown title="산 이름" width="100%" list={MOUNTAIN_LIST} />
      </div>
      <div className="dropdown-column">
        <DropDown title="등산로" width="100%" list={HIKING_TRAIL_LIST} />
      </div>
      <section>
        <div className="review-title-container">
          <h2>리뷰 목록</h2>
          <div>
            <span className="new-first" onClick={() => setIsSelected(false)}>
              최신순
            </span>
            <span className="view-first" onClick={() => setIsSelected(true)}>
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
