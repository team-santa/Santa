/* eslint-disable no-console */
/* eslint-disable no-alert */
/* eslint-disable jsx-a11y/label-has-associated-control */
/* eslint-disable react-hooks/exhaustive-deps */
import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import useDebounce from "src/hooks/useDebounce";
import { colors } from "src/utils/colors";
import { useUser } from "src/utils/localStorage";
import styled from "styled-components";
import Resizer from "react-image-file-resizer";
import { MdOutlineAddAPhoto } from "react-icons/md";
import { AiOutlineArrowLeft } from "react-icons/ai";
import { LocalUser } from "src/types/User";
import { useAppDispatch } from "src/redux";
import { updateProfileAction } from "src/redux/actions/updateProfileAction";

const EditProfile = () => {
  const user: LocalUser = useUser();
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const [username, setUserName] = useState(user.username); // user.username
  const [userImg, setUserImg] = useState<any>(user.profileImageUrl);

  const debouceValue = useDebounce(username);
  const [error, setError] = useState(false);

  useEffect(() => {
    const Fetch = async () => {
      return axios
        .get(`http://localhost:8080/members/${debouceValue}/check`)
        .then((res) => {
          if (user.username === username) setError(false);
          else setError(!res.data);
        });
    };

    if (debouceValue) Fetch();
  }, [debouceValue]);

  function fileChangedHandler(e: React.ChangeEvent<HTMLInputElement>) {
    if (e.currentTarget.files !== null) {
      const file = e.currentTarget.files[0];
      Resizer.imageFileResizer(
        file,
        300,
        300,
        "JPEG",
        30,
        0,
        (uri) => {
          setUserImg(uri);
        },
        "base64",
        200,
        200
      );
    }
  }

  // eslint-disable-next-line consistent-return
  const HandleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (error) return window.alert("유저네임을 유효하게 입력해 주세요.");
    if (username.trim().length <= 2)
      return window.alert("유저네임을 2글자 이상으로 설정해 주세요.");
    console.log(userImg.length);
    dispatch(updateProfileAction({ username, profileImageUrl: userImg }));
    navigate("/main");
  };

  if (!username && !userImg) return <div>로딩중...</div>;

  return (
    <Container>
      <AuthBox>
        <IconBox>
          <AiOutlineArrowLeft onClick={() => navigate(-1)} />
        </IconBox>
        <Title>프로필 수정</Title>
        <ProfileImgBox>
          <img src={String(userImg)} alt="profileImg" />
        </ProfileImgBox>
        <Form onSubmit={HandleSubmit}>
          <Input
            type="text"
            value={username}
            onChange={(e) => setUserName(e.target.value)}
            placeholder="닉네임을 입력해 주세요."
            error={error}
            required
          />
          <FileBox>
            <label htmlFor="profile">
              <MdOutlineAddAPhoto />
            </label>
            <input type="file" id="profile" onChange={fileChangedHandler} />
          </FileBox>
          {error ? <Error>* 중복된 아이디 입니다.</Error> : null}
          <button type="submit">설정 완료</button>
        </Form>
      </AuthBox>
    </Container>
  );
};

export default EditProfile;

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

const ProfileImgBox = styled.div`
  text-align: center;
  img {
    height: 15rem;
    width: 15rem;
    border-radius: 50%;
    object-fit: cover;
  }
`;

const IconBox = styled.div`
  cursor: pointer;
  margin-bottom: 2rem;
`;

const Form = styled.form`
  margin-top: 1rem;
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

const FileBox = styled.div`
  top: 270px;
  right: 100px;
  position: absolute;
  width: 5rem;
  height: 5rem;
  margin-top: 1rem;
  /* border: solid 1px lightgray; */
  border-radius: 0.5rem;
  display: flex;
  align-items: center;
  padding: 2rem;

  label {
    display: inline-block;
    padding-left: 1rem;
    color: #444;
    height: 5rem;
    width: 5rem;
    font-size: 3rem;
  }

  & input[type="file"] {
    /* 파일 필드 숨기기 */
    position: absolute;
    width: 1px;
    height: 1px;
    padding: 0;
    margin: -1px;
    overflow: hidden;
    clip: rect(0, 0, 0, 0);
    border: 0;
  }
`;
