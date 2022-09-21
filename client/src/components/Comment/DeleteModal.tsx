import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAppDispatch } from "src/redux";
import { deleteComment, deleteReview } from "src/redux/actions/review";
import styled from "styled-components";

import { useModal } from "../Modal";

export const SArticle = styled.article`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1.5rem;
  padding: 25px;
`;

export const H2 = styled.h2`
  font: revert;
  font-size: 17px;
`;

export const ButtonsContainer = styled.div`
  display: flex;
  gap: 1rem;

  & > button {
    width: 8rem;
    padding: 0.5rem;
    border-radius: 0.5rem;
  }
`;

export const Confirm = styled.button`
  color: white;
  background-color: #016483;
  border: none;
`;

export const Cancel = styled.button`
  color: #016483;
  background-color: inherit;
  border: 1px solid #016483;
`;

interface Prop {
  type: "review" | "comment";
  reviewBoardId?: number;
  commentId?: number;
}

const DeleteModal = ({ type, reviewBoardId, commentId }: Prop) => {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const { closeModal } = useModal();
  const [deleteIsDone, setDeleteIsDone] = useState(false);

  const handleDelete = async () => {
    if (type === "review") {
      await dispatch(deleteReview({ reviewBoardId: reviewBoardId as number }));
      setDeleteIsDone(true);
    }
    if (type === "comment") {
      dispatch(deleteComment({ commentId: commentId as number }));
    }
    closeModal();
  };

  useEffect(() => {
    if (deleteIsDone) {
      navigate("/main/review");
    }
  }, [navigate, deleteIsDone]);

  return (
    <SArticle>
      <H2>정말 삭제하시겠습니까 ?</H2>
      <ButtonsContainer>
        <Confirm type="button" onClick={handleDelete}>
          확인
        </Confirm>
        <Cancel type="button" onClick={closeModal}>
          취소
        </Cancel>
      </ButtonsContainer>
    </SArticle>
  );
};

export default DeleteModal;
