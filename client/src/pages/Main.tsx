import { Outlet, useNavigate } from "react-router-dom";
import styled from "styled-components";
import {
  IoSearch,
  IoHome,
  IoLocationSharp,
  IoChatboxEllipses,
  IoPersonSharp,
} from "react-icons/io5";
import { useEffect, useState } from "react";
import Logo from "src/assets/images/santa_log.svg";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
  font-size: 2rem;
  margin: 0;
  padding: 0;

  header {
    width: 100%;
    height: 7rem;
    position: sticky;
    top: 0;
    box-sizing: border-box;
    background-color: white;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 3rem;
    font-size: 4rem;
    color: #666;
    z-index: 20;

    &.shadow {
      box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 3px;
    }
  }

  section {
    flex-basis: 85%;
  }

  nav {
    width: 100%;
    height: 6rem;
    position: fixed;
    box-sizing: border-box;
    bottom: 0;
    background-color: #f5f5f5;
    display: flex;
    justify-content: space-between;
    padding: 0 10%;
    align-items: center;
    border-top: 1px solid lightgray;
    z-index: 10000;
    button {
      border: none;
      background: transparent;
      font-size: 3rem;
      margin-bottom: -7px;
      color: #666;

      &:hover {
        color: #016483;
        transition: all 0.3s;
      }
    }
  }
`;

const Main = () => {
  const navigate = useNavigate();
  const [show, setShow] = useState<boolean>(false);

  useEffect(() => {
    window.addEventListener("scroll", () => {
      if (window.scrollY > 50) {
        setShow(true);
      } else {
        setShow(false);
      }
    });
  }, []);

  return (
    <Wrapper>
      <header className={`${show && "shadow"}`}>
        <img src={Logo} alt="Logo" />
        <IoSearch onClick={() => navigate("/main/write")} />
      </header>

      <section>
        <Outlet />
      </section>

      <nav>
        <button type="button" onClick={() => navigate("/main")}>
          <IoHome />
        </button>
        <button type="button" onClick={() => navigate("/main/map")}>
          <IoLocationSharp />
        </button>
        <button type="button" onClick={() => navigate("/main/review")}>
          <IoChatboxEllipses />
        </button>
        <button type="button" onClick={() => navigate("/main/profile")}>
          <IoPersonSharp />
        </button>
      </nav>
    </Wrapper>
  );
};

export default Main;
