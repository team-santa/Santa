import styled from "styled-components";

export const Wrapper = styled.div`
  padding: 2rem;

  .title-container {
    margin-bottom: 1.6rem;

    h1 {
      margin-bottom: 1rem;
      line-height: 3rem;
      font-size: 2.4rem;
      font-weight: 700;
      line-height: 3rem;
    }

    .info-container {
      display: flex;
      align-items: center;
      margin-bottom: 1.2rem;

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
        margin-right: 1rem;
        font-size: 1.2rem;
        color: #696868;
      }
    }

    .hashTag-container {
      display: flex;
      flex-wrap: wrap;
      align-items: center;

      span {
        padding: 0.8rem 3rem;
        margin: 0.4rem 1rem 0.8rem 0;
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
