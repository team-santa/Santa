import { Outlet, useNavigate } from "react-router-dom";
import styled from "styled-components";
import { AiOutlinePlusCircle } from "react-icons/ai";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  height: 100vh;
  font-size: 2rem;

  header {
    flex-basis: 7.5%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 20px;
    border: 2px solid red;
    font-size: 3rem;
  }

  section {
    flex-basis: 85%;
  }

  nav {
    flex-basis: 7.5%;
    display: flex;
    justify-content: space-between;
    border: 2px solid red;

    button {
      flex-basis: 20%;
    }
  }
`;

const Main = () => {
  const navigate = useNavigate();
  return (
    <Wrapper>
      <header>
        <h1>ㅅㅌ</h1>
        <AiOutlinePlusCircle onClick={() => navigate("/main/write")} />
      </header>

      <section>
        <Outlet />
      </section>

      <nav>
        <button type="button" onClick={() => navigate("/main")}>
          home
        </button>
        <button type="button" onClick={() => navigate("/main/map")}>
          map
        </button>
        <button type="button" onClick={() => navigate("/main/review")}>
          review
        </button>
        <button type="button" onClick={() => navigate("/main/profile")}>
          profile
        </button>
      </nav>
    </Wrapper>
  );
};

export default Main;
