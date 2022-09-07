/* eslint-disable jsx-a11y/no-static-element-interactions */
/* eslint-disable jsx-a11y/click-events-have-key-events */
import { useNavigate } from "react-router-dom";
import Dialog from "src/components/Dialog/Dialog";
import styled from "styled-components";
import { useUser } from "src/utils/localStorage";
import { BiEdit } from "react-icons/bi";
import { LocalUser } from "src/types";

const Profile = () => {
  const navigate = useNavigate();
  const user: LocalUser = useUser();

  if (!user)
    return (
      <Dialog
        confirm={() => navigate("/main/login")}
        close={() => navigate(-1)}
      />
    );
  return (
    <Container>
      <TitleBox>
        <h1>프로필</h1>
        <div onClick={() => navigate("/main/editprofile")}>
          <BiEdit />
        </div>
      </TitleBox>
      <ProfileBox>
        <img src={String(user.profileImageUrl)} alt="profile-img" />
        <UserName>{user.username}</UserName>
      </ProfileBox>
      <MyReviewBox />
    </Container>
  );
};

export default Profile;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
  padding: 0rem 2rem;
  overflow: scroll;

  h1 {
    font-size: 2.5rem;
    margin: 1rem 0rem;
  }
`;

const TitleBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-weight: bold;

  & > div {
    padding: 0.5rem;
    font-size: 2.5rem;
    color: gray;
  }
`;

const ProfileBox = styled.div`
  display: flex;
  align-items: center;
  flex-direction: column;
  padding-bottom: 3rem;
  border-bottom: solid 1px lightgray;

  width: 100%;
  margin-top: 2rem;

  img {
    width: 13rem;
    height: 13rem;
    border-radius: 100%;
    margin-bottom: 1rem;
    object-fit: cover;
  }
`;
const MyReviewBox = styled.div`
  width: 100%;
  margin: 1rem 0rem;
  background-color: red;
`;

const UserName = styled.h2`
  margin-top: 1rem;
  font-size: 2rem;
  font-weight: 500;
`;
