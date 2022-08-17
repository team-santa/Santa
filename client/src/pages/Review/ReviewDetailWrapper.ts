import styled from "styled-components";

export const Wrapper = styled.div`
  padding: 2rem;

  .title-container {
    svg {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 1rem;
    }

    h1 {
      margin-bottom: 1rem;
      line-height: 3rem;
      font-size: 2.4rem;
      font-weight: 700;
    }

    .info-container {
      display: flex;
      justify-content: space-between;
      align-items: center;

      div {
        display: flex;
        align-items: center;
      }

      img {
        width: 2rem;
        height: 2rem;
        border-radius: 50%;
        margin-right: 1rem;
      }

      .author-name {
        font-weight: 700;
      }

      span {
        padding-top: 0.5rem;
        margin-right: 1rem;
        font-size: 1.2rem;
        color: #696868;
      }
    }

    .hashTag-container {
      display: flex;
      flex-wrap: wrap;
      align-items: center;
      gap: 0.5rem;
      margin-top: 1rem;
      margin-bottom: 1.6rem;

      span {
        padding: 0.8rem 2.8rem;
        font-size: 1rem;
        border-radius: 16px;
        background-color: #d4effe;
      }
    }
  }

  .image-container {
    margin-bottom: 1rem;
    height: 240px;
  }

  .review-container {
    font-size: 1.4rem;
    line-height: 2.8rem;
  }
`;
