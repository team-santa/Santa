import styled from "styled-components";

export const Wrapper = styled.ul`
  li {
    display: flex;
    flex-direction: column;
    height: 14rem;
    margin: 1rem;
    border-bottom: 1px solid lightgray;
  }

  .review-content-container {
    flex-basis: 70%;
    display: flex;
    justify-content: space-between;
    gap: 5rem;
    margin-bottom: 0.5rem;
    padding: 0 1rem;
  }

  .review-content {
    flex-basis: 70%;
    display: flex;
    flex-direction: column;
    justify-content: space-around;

    h3 {
      /* margin-bottom: 2rem; */
      height: 40px;
      overflow: hidden;
      line-height: 2rem;
      font-size: 1.6rem;
      font-weight: 600;
    }
  }

  .profile-container {
    /* margin-bottom: 1rem; */
    display: flex;
    align-items: center;
    font-size: 1.4rem;
    font-weight: 500;

    img {
      width: 2rem;
      height: 2rem;
      border-radius: 50%;
      margin-right: 1rem;
    }
  }

  .util-container {
    display: flex;
    align-items: center;
    font-size: 1rem;

    span {
      margin-right: 1rem;
      color: #898989;
    }
  }

  .img-container {
    img {
      width: 12rem;
      height: 10rem;
      border-radius: 12px;
    }
  }

  .review-hashtag-container {
    display: flex;
    padding: 0 1rem;
    align-items: center;
    gap: 2rem;

    span {
      padding: 0.6rem 3rem;
      font-size: 1rem;
      border-radius: 16px;
      background-color: #d4effe;
    }
  }
`;
