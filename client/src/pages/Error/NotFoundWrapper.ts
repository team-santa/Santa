import styled from "styled-components";

export const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;

  svg {
    font-size: 10rem;
    color: #016483;
    margin-bottom: 3rem;
  }

  h1 {
    font-size: 2.5rem;
    font-weight: 600;
    margin-bottom: 3rem;

    strong {
      color: #016483;
    }
  }

  p {
    font-size: 1.4rem;
    color: #6a6a6a;
    line-height: 2rem;
  }

  div {
    display: flex;
    gap: 2rem;
    margin-top: 3rem;

    button {
      padding: 1rem 2rem;
      background-color: white;
      border: 1px solid black;
      border-radius: 1rem;
      box-shadow: 5px 5px 10px 0px rgba(0, 0, 0, 0.5);
    }
  }
`;
