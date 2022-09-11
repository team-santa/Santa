import styled from "styled-components";

interface Props {
  selected: boolean;
}

export const Wrapper = styled.div<Props>`
  padding: 2rem 1rem 6rem 1rem;
  display: flex;
  flex-direction: column;

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
    margin: 2rem 1rem;

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

  .new-first {
    color: ${(props) => props.selected && "#d7cccc"};
  }

  .view-first {
    color: ${(props) => !props.selected && "#d7cccc"};
  }
`;

export const SListContainer = styled.ul`
  min-height: 1000px;
`;
