/* eslint-disable no-console */
/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable no-useless-escape */
/* eslint-disable jsx-a11y/no-static-element-interactions */
/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable react/button-has-type */
import "react-quill/dist/quill.snow.css";
import React, { useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Dialog from "src/components/Dialog/Dialog";
import ReactQuill from "react-quill";
import styled from "styled-components";
import BaseButton from "src/components/BaseButton/BaseButton";
import { colors } from "src/utils/colors";
import axios from "axios";
import useDebounce from "src/hooks/useDebounce";
import { useUser } from "src/utils/localStorage";
import { REGION_LIST, MOUNTAIN_LIST, HIKING_TRAIL_LIST } from "src/utils";
import { DropDown } from "src/components";
import CustomToolbar, { formats, modules } from "./CustomToolbar";

const Write = () => {
  const navigate = useNavigate();
  const user = useUser();

  const [title, setTitle] = useState("");
  const [content, setContent] = useState<string>("");
  const [tag, setTag] = useState("");
  const [tags, setTags] = useState<string[]>([]);
  const [isOpen, setIsOpen] = useState(false);
  const [options, setOption] = useState([]);

  // DropDown
  const [dropDownValue, setDropDownValue] = useState({
    region: "지역",
    mountain: "산 이름",
    hikingTrail: "등산로",
  });

  const [dropDownIsOpen, setDropDownIsOpen] = useState({
    region: false,
    mountain: false,
    hikingTrail: false,
  });

  const handleClick = useCallback(
    (name: string) => {
      const newObj = {
        region: false,
        mountain: false,
        hikingTrail: false,
      };
      newObj[name as keyof typeof newObj] =
        !dropDownIsOpen[name as keyof typeof newObj];
      setDropDownIsOpen(newObj);
    },
    [dropDownIsOpen]
  );

  const debouceValue = useDebounce(tag);

  const fetchTagList = async () => {
    const result = await axios.get(
      `https://olive-shrimps-go-222-117-186-4.loca.lt/v1/tag?text=${debouceValue}`
    );
    const tagNames = result.data.map((data: any) => data.tagName);
    setOption(tagNames);
  };

  useEffect(() => {
    if (debouceValue) fetchTagList();
  }, [debouceValue]);

  const handleTitle = (e: React.ChangeEvent<HTMLInputElement>) =>
    setTitle(e.currentTarget.value);

  const handleTag = (e: React.ChangeEvent<HTMLInputElement>) =>
    setTag(e.target.value);

  const handleContent = (value: any) => setContent(value);

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

  const handleRemoveTag = (tag: string) => {
    const findIndex = tags.indexOf(tag);
    if (findIndex !== -1) {
      const tagFilter = tags.filter((tagItem) => tagItem !== tag);
      setTags(tagFilter);
    }
  };

  const HandleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    let thumbnail = null;

    if (content.includes("img")) {
      const re = /<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>/i;
      const result = content.match(re);
      if (result) [thumbnail] = result;
    }

    const requestForm = {
      username: "CuteHwanMin",
      title,
      content,
      thumbnail,
      tags,
      dropDownValue,
    };

    console.log(requestForm);
  };

  return (
    <Container>
      {user ? null : (
        <Dialog
          confirm={() => navigate("/main/login")}
          close={() => navigate("/main/")}
        />
      )}
      <Form onSubmit={HandleSubmit}>
        <input
          type="text"
          className="title"
          placeholder="제목을 입력해 주세요."
          value={title}
          onChange={handleTitle}
        />
        <TagContainer>
          <div>
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
              placeholder="Tag"
              className="inputTag"
              value={tag}
              onChange={handleTag}
              onClick={() => setIsOpen(!isOpen)}
            />
          </div>
        </TagContainer>
        <div className="dropdown-column">
          <DropDown
            width="100%"
            list={REGION_LIST}
            name="region"
            isOpen={dropDownIsOpen}
            value={dropDownValue.region}
            setValue={setDropDownValue}
            handleClick={handleClick}
          />
          <DropDown
            width="100%"
            list={MOUNTAIN_LIST}
            name="mountain"
            isOpen={dropDownIsOpen}
            value={dropDownValue.mountain}
            setValue={setDropDownValue}
            handleClick={handleClick}
          />
        </div>
        <div className="dropdown-column">
          <DropDown
            width="100%"
            list={HIKING_TRAIL_LIST}
            name="hikingTrail"
            isOpen={dropDownIsOpen}
            value={dropDownValue.hikingTrail}
            setValue={setDropDownValue}
            handleClick={handleClick}
          />
        </div>
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
        <div className="text-editor" onClick={() => setIsOpen(false)}>
          <CustomToolbar />
          <ReactQuill
            modules={modules}
            formats={formats}
            value={content}
            onChange={handleContent}
            placeholder="소중한 당신의 후기를 공유해 주세요."
          />
        </div>
        <ButtonBox>
          <BaseButton
            text="전송"
            bgColor={colors.mainColor}
            width={7.2}
            height={4.2}
            disabled={
              content === "" || title === "" || content === "<p><br></p>"
            }
          />
        </ButtonBox>
      </Form>
    </Container>
  );
};

export default Write;

const Container = styled.div`
  overflow-y: scroll;

  .dropdown-column {
    display: flex;
    align-items: center;
    gap: 3rem;
    width: 90%;
    margin-top: 1rem;
  }
`;

const Form = styled.form`
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  margin-top: 1rem;

  .title {
    box-sizing: content-box;
    width: 84.35%;
    height: 1rem;
    padding: 1rem 1rem;
    border: solid 1px lightgray;
    border-radius: 5px;

    &:focus {
      outline: none;
      border-color: ${colors.mainColor};
    }
  }

  .text-editor {
    width: 90%;
    margin-top: 0.5rem;
  }

  .ql-container {
    min-height: 47rem;
    height: 50vh;
    flex: 1;
    display: flex;
    flex-direction: column;
    border-bottom-left-radius: 5px;
    border-bottom-right-radius: 5px;
  }

  .ql-editor {
    height: 100%;
    flex: 1;
    overflow-y: hidden;
    width: 100%;
  }
`;

const TagContainer = styled.div`
  border: solid 1px lightgray;
  border-radius: 2px;
  display: flex;
  justify-content: space-between;
  margin-top: 0.5rem;
  width: 89%;
  bottom: 57px;

  div {
    display: flex;
    text-align: center;
    flex-wrap: wrap;

    span {
      margin-top: 0.3rem;
      padding: 0.4rem;
      font-size: 1.4rem;
      color: #444;
    }

    .inputTag {
      border: none;
      font-size: 1.3rem;
      padding: 0.5rem 0rem;
      padding-left: 1rem;
      &:focus {
        outline: none;
        border-color: ${colors.mainColor};
      }
    }
  }
`;

const DropBox = styled.div`
  position: absolute;
  border-radius: 5px;
  border: solid 1px ${colors.mainColor};
  width: 90%;
  height: 20rem;
  z-index: 2;
  top: 70px;
  background-color: white;
  overflow: scroll;

  span {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 1.5rem;
    font-size: 1.2rem;
    border-bottom: solid 1px lightgray;
  }
`;

const ButtonBox = styled.div`
  position: fixed;
  top: 0;
  right: 0;
  margin-right: 2rem;
  margin-top: 1rem;
`;
