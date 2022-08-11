import styled from "styled-components";

interface Props {
  selected: boolean;
}

export const Wrapper = styled.div<Props>`
  box-sizing: border-box;
  width: 100%;
  height: 100%;
  padding: 1rem;

  .dropdown-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
  }

  .dropdown-column {
    display: flex;
    align-items: center;
    gap: 3rem;
    margin-bottom: 1.5rem;
  }

  .review-title-container {
    display: flex;
    justify-content: space-between;
    align-items: end;
    margin-top: 1rem;

    h2 {
      font-size: 2.5rem;
      font-weight: 700;
    }

    span {
      font-size: 1.2rem;
      margin-left: 1.5rem;
      cursor: pointer;
    }
  }

  .newFirst {
    color: ${(props) => props.selected && "#d7cccc"};
  }

  .viewFirst {
    color: ${(props) => !props.selected && "#d7cccc"};
  }
`;
