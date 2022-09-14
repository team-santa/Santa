/* eslint-disable jsx-a11y/no-static-element-interactions */
/* eslint-disable jsx-a11y/click-events-have-key-events */
import { useEffect, useState } from "react";
import { getDateToString } from "src/utils";
import styled from "styled-components";
import { useModal } from "../Modal";
import DeleteModal from "./DeleteModal";

interface Prop {
  commentId: number;
  memberId: string;
  profileImageUrl: string;
  writer: string;
  modifiedAt: string;
  body: string;
}

const Comment = ({
  commentId,
  memberId,
  profileImageUrl,
  writer,
  modifiedAt,
  body,
}: Prop) => {
  const [value, setValue] = useState(body);
  const [isEdit, setIsEdit] = useState(false);

  const { openModal, closeModal } = useModal({
    position: { x: "50%", y: "50%" },
    height: "110px",
    width: "70%",
  });

  const handleEdit = () => {
    setIsEdit(false);
  };

  useEffect(() => {
    return () => closeModal();
  }, [closeModal]);

  return (
    <SComment>
      <SHeader>
        <User>
          <img src={profileImageUrl} alt="user" />
          <span>{writer}</span>
          <span>{getDateToString(modifiedAt)}</span>
        </User>
        <SUtils>
          <span onClick={() => setIsEdit(true)}>수정</span>
          <span>·</span>
          <span
            onClick={() =>
              openModal(<DeleteModal type="comment" commentId={commentId} />)
            }
          >
            삭제
          </span>
        </SUtils>
      </SHeader>
      {isEdit ? (
        <SEditContainer>
          <textarea value={value} onChange={(e) => setValue(e.target.value)} />
          <button type="button" onClick={handleEdit}>
            완료
          </button>
        </SEditContainer>
      ) : (
        <p>{value}</p>
      )}
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

const SEditContainer = styled.div`
  position: relative;

  & > textarea {
    box-sizing: border-box;
    width: 100%;
    height: 10rem;
    padding: 1rem;
    resize: none;
    border: 1.5px solid #016483;
    border-radius: 5px;
    outline: none;
  }

  & > button {
    position: absolute;
    width: 40px;
    bottom: 10px;
    right: 0;
    background-color: inherit;
    border: none;
    color: #016483;
    cursor: pointer;
    font-weight: 500;
  }
`;
