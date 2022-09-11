/* eslint-disable consistent-return */
/* eslint-disable react-hooks/exhaustive-deps */
import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import useDebounce from "src/hooks/useDebounce";
import { useAppDispatch } from "src/redux";
import { updateProfileAction } from "src/redux/actions/updateProfileAction";
import { colors } from "src/utils/colors";
import styled from "styled-components";

const SignUp = () => {
  const navigate = useNavigate();
  const [username, setUserName] = useState("");
  const debouceValue = useDebounce(username);
  const [error, setError] = useState(false);
  const dispatch = useAppDispatch();
  useEffect(() => {
    const Fetch = async () => {
      return axios
        .get(`http://localhost:8080/members/${debouceValue}/check`)
        .then((res) => {
          setError(!res.data);
        });
    };
    if (username) Fetch();
  }, [debouceValue]);

  const HandleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (error) return window.alert("유저네임을 유효하게 입력해 주세요.");
    if (username.trim().length <= 2)
      return window.alert("유저네임을 2글자 이상으로 설정해 주세요.");
    dispatch(updateProfileAction({ username }));
    navigate("/main");
  };

  return (
    <Container>
      <AuthBox>
        <Title>추가 정보 입력</Title>
        <Form onSubmit={HandleSubmit}>
          <Input
            type="text"
            value={username}
            onChange={(e) => setUserName(e.target.value)}
            placeholder="닉네임을 입력해 주세요."
            error={error}
            required
          />
          {error ? <Error>* 중복된 아이디 입니다.</Error> : null}
          <button type="submit">설정 완료</button>
        </Form>
      </AuthBox>
    </Container>
  );
};

export default SignUp;

const Container = styled.div`
  display: flex;
  justify-content: center;
  height: 85vh;
`;

const AuthBox = styled.div`
  margin-top: 5rem;
  width: 90%;
`;

const Title = styled.h1`
  font-size: 2rem;
  font-weight: 700;
`;

const Form = styled.form`
  margin-top: 3rem;
  width: 100%;

  button {
    border: none;
    border-radius: 0.5rem;
    margin-top: 1rem;
    width: 100%;
    height: 4rem;
    background-color: ${colors.mainColor};
    color: white;
    cursor: pointer;
    font-size: 1.5rem;
  }
`;

const Input = styled.input<{ error: boolean }>`
  width: 95%;
  height: 4rem;
  padding-left: 1rem;
  font-size: 1.5rem;
  border-radius: 0.5rem;
  border: solid 1px lightgray;

  border-color: ${({ error }) => (error ? "red" : "none")};

  &:focus {
    outline: none;
    border-color: ${colors.mainColor};
  }
`;

const Error = styled.p`
  margin-top: 1rem;
  font-size: 1.2rem;
  color: red;
`;
