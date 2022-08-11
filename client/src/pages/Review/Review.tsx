/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable jsx-a11y/no-static-element-interactions */

import { useState } from "react";
import { DropDown } from "src/components";
import { REGION_LIST, MOUNTAIN_LIST, HIKING_TRAIL_LIST } from "src/utils";
import { Wrapper } from "./wrapper";

const Review = () => {
  const [selected, setIsSelected] = useState(false);

  return (
    <Wrapper selected={selected}>
      <section className="dropdown-container">
        <div className="dropdown-column">
          <DropDown title="지역" width="100%" list={REGION_LIST} />
          <DropDown title="산 이름" width="100%" list={MOUNTAIN_LIST} />
        </div>
        <div className="dropdown-column">
          <DropDown title="등산로" width="100%" list={HIKING_TRAIL_LIST} />
        </div>
      </section>
      <section className="review-container">
        <div className="review-title-container">
          <h2>리뷰 목록</h2>
          <div>
            <span className="newFirst" onClick={() => setIsSelected(false)}>
              최신순
            </span>
            <span className="viewFirst" onClick={() => setIsSelected(true)}>
              조회순
            </span>
          </div>
        </div>
      </section>
    </Wrapper>
  );
};

export default Review;
