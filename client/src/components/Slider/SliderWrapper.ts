import styled from "styled-components";

export const Wrapper = styled.div`
  height: inherit;

  .swiper {
    height: inherit;
  }

  .swiper-slide img {
    display: block;
    width: 100%;
    height: inherit;
    border-radius: 12px;
  }

  .swiper-button-next::after {
    color: white;
    font-size: 2rem !important;
  }

  .swiper-button-prev::after {
    color: white;
    font-size: 2rem !important;
  }
`;
