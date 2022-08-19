import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

const Wrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100vh;
  background-color: #e0ece2;

  h1 {
    color: #295349;
    font-size: 5rem;
  }
`;

const Landing = () => {
  const navigate = useNavigate();

  useEffect(() => {
    setTimeout(() => {
      navigate("/main");
    }, 2000);
  }, [navigate]);

  return (
    <Wrapper>
      <h1>ㅅㅌ</h1>
    </Wrapper>
  );
};

export default Landing;
