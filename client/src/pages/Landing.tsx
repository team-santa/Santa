import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Logo from "src/assets/images/santa_log.svg";

const Wrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100vh;
  background-color: #d4effe;
  flex-direction: column;
  color: #016483;
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
      <span>메세지 메세지</span>
      <img src={Logo} alt="Logo" />
    </Wrapper>
  );
};

export default Landing;
