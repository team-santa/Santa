/* eslint-disable no-alert */
/* eslint-disable no-console */
/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable jsx-a11y/no-static-element-interactions */
import React from "react";
import { colors } from "src/utils/colors";
import styled from "styled-components";

interface Props {
  tags: string[];
  setTags: React.Dispatch<React.SetStateAction<string[]>>;
  setTag: React.Dispatch<React.SetStateAction<string>>;
  setIsOpen: React.Dispatch<React.SetStateAction<boolean>>;
  tag: string;
  isOpen: boolean;
  options: string[];
}

const LiveTag = ({
  tags,
  setTags,
  setTag,
  setIsOpen,
  tag,
  isOpen,
  options,
}: Props) => {
  const handleRemoveTag = (tag: string) => {
    const findIndex = tags.indexOf(tag);
    if (findIndex !== -1) {
      const tagFilter = tags.filter((tagItem) => tagItem !== tag);
      setTags(tagFilter);
    }
  };
  const handleTag = (e: React.ChangeEvent<HTMLInputElement>) =>
    setTag(e.target.value);

  const handleAddTags = async (tag: string) => {
    console.log(tag);
    if (tags.indexOf(tag) !== -1) return window.alert("중복된 태그 입니다.");
    if (tag.length >= 15)
      return window.alert("태그는 최대 15자 까지 입력해주세요.");
    if (tags.length >= 5)
      return window.alert("태그는 최대 5개 까지 입력이 가능합니다.");
    if (tag.trim().length === 0)
      return window.alert("공백태그는 입력할 수 없습니다.");
    setTags([...tags, tag]);
    setTag("");
    return "";
  };

  return (
    <TagBox>
      {tags?.map((tag) => (
        <span
          key={tag}
          onClick={(e) => handleRemoveTag(tag)}
          aria-hidden="true"
        >
          # {tag}
        </span>
      ))}
      <input
        type="text"
        placeholder="# 태그는 최대 3개"
        className="inputTag"
        value={tag}
        onChange={handleTag}
        onClick={() => setIsOpen(!isOpen)}
      />
      {isOpen ? (
        <DropBox>
          {options?.map((option) => (
            <span
              key={option}
              onClick={() => {
                handleAddTags(option);
                setIsOpen(false);
              }}
            >
              {option}
            </span>
          ))}
        </DropBox>
      ) : null}
    </TagBox>
  );
};

export default LiveTag;

const TagBox = styled.div`
  padding-left: 1px;
`;

const DropBox = styled.div`
  position: absolute;
  border-radius: 4px;
  border: solid 1px ${colors.mainColor};
  width: 88.5%;
  height: 20rem;
  z-index: 2;
  top: 70px;
  background-color: white;
  overflow: scroll;
  animation: growDown 250ms ease-in;
  transform-origin: top center;
  display: flex;
  flex-direction: column;

  span {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 1.5rem;
    font-size: 1.2rem;
    border-bottom: solid 1px lightgray;
  }

  @keyframes growDown {
    0% {
      transform: scaleY(0);
    }
    100% {
      transform: scaleY(1);
    }
  }
`;
