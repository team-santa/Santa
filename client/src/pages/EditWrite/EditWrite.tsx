/* eslint-disable jsx-a11y/label-has-associated-control */
/* eslint-disable no-console */
/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable no-useless-escape */
/* eslint-disable jsx-a11y/no-static-element-interactions */
/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable react/button-has-type */
import "react-quill/dist/quill.snow.css";
import React, { useCallback, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
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
import { MdOutlineAddAPhoto } from "react-icons/md";
import Resizer from "react-image-file-resizer";
import LiveTag from "src/components/TagBox/LiveTag";
import CustomToolbar, { formats, modules } from "../Write/CustomToolbar";

const EditWrite = () => {
  const { id } = useParams();
  console.log(id);

  const navigate = useNavigate();
  const user = useUser();

  const [title, setTitle] = useState("");
  const [content, setContent] = useState<string>("");
  const [tag, setTag] = useState("");
  const [tags, setTags] = useState<string[]>([]);
  const [isOpen, setIsOpen] = useState(false);
  const [options, setOption] = useState<string[]>([]);
  const [imgText, setImgText] = useState("대표 이미지를 선택해 주세요.");
  const [mainImg, setMainImg] = useState<
    string | null | File | Blob | ProgressEvent<FileReader>
  >(null);

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
    const tagNames: string[] = result.data.map((data: any) => data.tagName);
    setOption(tagNames);
  };

  useEffect(() => {
    if (debouceValue) fetchTagList();
    if (debouceValue.length === 0) setIsOpen(false);
  }, [debouceValue]);

  const handleTitle = (e: React.ChangeEvent<HTMLInputElement>) =>
    setTitle(e.currentTarget.value);

  const handleContent = (value: any) => setContent(value);

  function fileChangedHandler(e: React.ChangeEvent<HTMLInputElement>) {
    if (e.currentTarget.files !== null) {
      const file = e.currentTarget.files[0];
      setImgText(file.name);
      Resizer.imageFileResizer(
        file,
        300,
        300,
        "JPEG",
        30,
        0,
        (uri: any) => {
          setMainImg(uri);
        },
        "base64",
        200,
        200
      );
    }
  }

  const HandleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const requestForm = {
      username: user.username,
      thumbnail: mainImg,
      title,
      content,
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
          <LiveTag
            tags={tags}
            setTags={setTags}
            setTag={setTag}
            setIsOpen={setIsOpen}
            tag={tag}
            isOpen={isOpen}
            options={options}
          />
        </TagContainer>
        <div className="dropdown-column">
          <DropDown
            width="100%"
            list={REGION_LIST}
            name="local"
            isOpen={dropDownIsOpen}
            value={dropDownValue.region}
            setValue={setDropDownValue}
            handleClick={handleClick}
            dispatch={(name) => console.log(name)}
          />
          <DropDown
            width="100%"
            list={MOUNTAIN_LIST}
            name="mountain"
            isOpen={dropDownIsOpen}
            value={dropDownValue.mountain}
            setValue={setDropDownValue}
            handleClick={handleClick}
            dispatch={(name) => console.log(name)}
          />
        </div>
        <div className="dropdown-column">
          <DropDown
            width="100%"
            list={HIKING_TRAIL_LIST}
            name="course"
            isOpen={dropDownIsOpen}
            value={dropDownValue.hikingTrail}
            setValue={setDropDownValue}
            handleClick={handleClick}
            dispatch={(name) => console.log(name)}
          />
        </div>
        <FileBox>
          <label htmlFor="profile">
            {imgText} <MdOutlineAddAPhoto />
          </label>
          <input type="file" id="profile" onChange={fileChangedHandler} />
        </FileBox>
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

export default EditWrite;

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
    min-height: 30rem;
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
  border-radius: 4px;
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

const ButtonBox = styled.div`
  z-index: 9999;
  position: fixed;
  top: 0;
  right: 0;
  margin-right: 2rem;
  margin-top: 1rem;
`;

const FileBox = styled.div`
  width: 90%;
  margin-top: 1rem;
  border: solid 1px lightgray;
  border-radius: 0.5rem;
  display: flex;
  align-items: center;
  justify-content: center;

  label {
    display: inline-block;
    padding-left: 1rem;
    color: #888;
    height: 100%;
    width: 100%;
    padding: 1rem;
    font-size: 1.5rem;
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
