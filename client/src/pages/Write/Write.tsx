/* eslint-disable react/button-has-type */
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Dialog from "src/components/Dialog/Dialog";
import "react-quill/dist/quill.snow.css";
import ReactQuill from "react-quill";
import styled from "styled-components";
import BaseButton from "src/components/BaseButton/BaseButton";
import { colors } from "src/utils/colors";

const CustomToolbar = () => (
  <div id="toolbar">
    <select className="ql-size">
      <option value="small">Size 1</option>
      <option value="extra-small">Size 2</option>
      <option value="medium">Size 3</option>
      <option value="large">Size 4</option>
    </select>
    <button className="ql-bold" />
    <button className="ql-italic" />
    <select className="ql-color">
      <option value="red" />
      <option value="green" />
      <option value="blue" />
      <option value="orange" />
      <option value="violet" />
      <option value="#d0d1d2" />
      <option value="black" />
    </select>
    <select className="ql-background" />
    <button className="ql-link" />
    <button className="ql-image" />
  </div>
);

const Write = () => {
  const navigate = useNavigate();
  const modules = {
    toolbar: {
      container: "#toolbar",
    },
  };
  const formats = [
    "header",
    "font",
    "size",
    "bold",
    "italic",
    "underline",
    "list",
    "bullet",
    "align",
    "color",
    "background",
    "image",
  ];
  const [title, setTitle] = useState("");
  const [content, setContent] = useState<string>("");
  const [tag, setTag] = useState("");
  const [tags, setTags] = useState<string[]>([]);
  const handleText = (value: any) => {
    setContent(value);
  };
  const handleTitle = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(e.currentTarget.value);
  };

  const handleTag = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTag(e.currentTarget.value);
  };

  const handleAddTags = async (e: React.MouseEvent<HTMLButtonElement>) => {
    // TODO
    // Tag Validation Check !!
    if (tags.indexOf(tag) !== -1) {
      window.alert("중복된 태그 입니다.");
      return;
    }
    if (tag.length >= 15) {
      window.alert("태그가 너무 깁니다.");
      return;
    }
    if (tags.length >= 5) {
      window.alert("태그는 최대 5개 까지 입력이 가능합니다.");
      return;
    }
    setTags([...tags, tag]);
    setTag("");
  };
  const handleRemoveTag = (tag: string) => {
    const findIndex = tags.indexOf(tag);
    if (findIndex !== -1) {
      const tagFilter = tags.filter((tagItem) => tagItem !== tag);
      setTags(tagFilter);
    }
  };

  const HandleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const requestForm = {
      usename: "CuteHwanMin",
      userProfile: "/images/CC",
      title,
      content,
      tags,
    };
    console.log("I'm Handle Submit ~");
  };
  return (
    <Container>
      {true || (
        <Dialog
          confirm={() => navigate("/main/login")}
          close={() => navigate(-1)}
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
        <div className="text-editor">
          <CustomToolbar />
          <ReactQuill
            modules={modules}
            formats={formats}
            value={content}
            onChange={handleText}
            placeholder="소중한 당신의 후기를 공유해 주세요."
          />
        </div>

        <TagContainer>
          <div>
            {tags.map((tag) => (
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
              placeholder="# Tag"
              className="inputTag"
              value={tag}
              onChange={handleTag}
              size={tag.length}
            />
          </div>
          <button type="button" onClick={handleAddTags}>
            + 추가
          </button>
        </TagContainer>
        <BaseButton
          text="글쓰기"
          bgColor={colors.mainColor}
          width={35}
          disabled={content === "" || title === "" || content === "<p><br></p>"}
        />
      </Form>
    </Container>
  );
};

export default Write;

const Container = styled.div``;

const Form = styled.form`
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  margin-top: 2rem;

  .title {
    box-sizing: content-box;
    width: 84.35%;
    height: 2rem;
    padding: 1rem 1rem;
    border: solid 1px lightgray;

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
    min-height: 50rem;
    height: 50vh;
    flex: 1;
    display: flex;
    flex-direction: column;
  }

  .ql-editor {
    height: 100%;
    flex: 1;
    overflow-y: hidden;
    width: 100%;
  }
`;

const TagContainer = styled.div`
  background-color: white;
  position: absolute;
  display: flex;
  justify-content: space-between;
  margin-top: 0.5rem;
  width: 89%;
  bottom: 57px;

  div {
    flex: 9;
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
      min-width: 3.5rem;
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

  button {
    flex: 1.2;
    border: none;
    background-color: transparent;
  }
`;
