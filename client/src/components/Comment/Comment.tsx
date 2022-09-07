import styled from "styled-components";

const Comment = () => {
  return (
    <SComment>
      <SHeader>
        <User>
          <img
            src="https://w.namu.la/s/91986ffc01b6136fb453c4ff8b3e63adf90e525e6b10e1643058339c924830276d1fc646c2ac918d1f31e83c59308ea232acb64afb280dabeee0afc024b9dd3022b4bcdb7c0bfb7aa9e44ebc858c8e7c71746540c7a8dce7894942e85f2d0b53"
            alt="user"
          />
          <span>이재용</span>
          <span>3분 전</span>
        </User>
        <SUtils>
          <span>수정</span>
          <span>·</span>
          <span>삭제</span>
        </SUtils>
      </SHeader>
      <p>
        우리나라에서 가장 높은 산인데 언젠가 한번 등산하겠지라고만 생각하고 막상
        가려고하면 무서워서 못 갔던 곳 ~ 우리나라에서 가장 높은 산인데 언젠가
        한번 등산하겠지라고만 생각하고 막상 가려고하면 무서워서 못 갔던 곳 ~
        우리나라에서 가장 높은 산인데 언젠가 한번 등산하겠지라고만 생각하고 막상
        가려고하면 무서워서 못 갔던 곳 ~
      </p>
    </SComment>
  );
};

export default Comment;

const SComment = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  padding: 1rem 0;
  border-bottom: 1px solid lightgray;

  & > p {
    font-size: 1.2rem;
    line-height: 2rem;
  }
`;

const SHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const User = styled.div`
  display: flex;
  align-items: center;
  gap: 1rem;

  & > img {
    width: 20px;
    height: 20px;
    border-radius: 50%;
  }

  & > span {
    padding-top: 3px;
    font-size: 1.5rem;
    font-weight: 600;
  }

  & > span:last-child {
    font-size: 1.2rem;
    color: #696868;
  }
`;

const SUtils = styled.div`
  & > span {
    padding-top: 0.5rem;
    margin-right: 0.5rem;
    font-size: 1.2rem;
    color: #696868;
    cursor: pointer;
  }
`;
