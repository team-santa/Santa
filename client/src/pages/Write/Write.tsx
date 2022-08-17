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
  const handleText = (value: any) => {
    console.log(value);
    setContent(value);
  };
  const handleTitle = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value);
  };

  const HandleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const requestForm = {
      usename: "CuteHwanMin",
      userProfile: "/images/CC",
      title,
      content,
    };
    console.log(requestForm);
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
        <BaseButton
          text="글쓰기"
          bgColor={colors.mainColor}
          width={34}
          disabled={content === "" || title === "" || content === "<p><br></p>"}
        />
      </Form>
    </Container>
  );
};

export default Write;

const Container = styled.div``;

const Form = styled.form`
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
  }
  .title:focus {
    outline: none;
    border-color: ${colors.mainColor};
  }
  .text-editor {
    width: 90%;
    margin-top: 0.5rem;
  }

  .ql-container {
    /* min-height: 30rem; */
    height: 60vh;
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
