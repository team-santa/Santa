import styled from "styled-components";

export const Wrapper = styled.div`
  padding: 2rem 2rem 11rem 2rem;

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
        padding-bottom: 0.2rem;
        margin-right: 0.5rem;
        font-size: 1.2rem;
        color: #696868;
        cursor: pointer;
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

export const SCommentsContainer = styled.section`
  margin-top: 2rem;
  border-top: 2px solid lightgray;

  & > h1 {
    margin: 2rem 0 1rem 0;
  }
`;

export const SInputContainer = styled.section`
  position: fixed;
  display: flex;
  justify-content: space-between;
  bottom: 60px;
  width: 100%;
  margin-left: -20px;
  border-top: 1px solid lightgray;

  & > button {
    width: 8rem;
    background-color: white;
    border: none;
    color: #016483;
    cursor: pointer;
  }
`;

export const SInput = styled.input`
  width: 100%;
  padding: 0.5rem 2rem;
  height: 3rem;
  border: none;
  outline: none;
  overflow: auto;
`;
