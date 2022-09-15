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
import { axiosAuthInstance } from "src/utils";
import { DropDown } from "src/components";
import { MdOutlineAddAPhoto } from "react-icons/md";
import Resizer from "react-image-file-resizer";
import LiveTag from "src/components/TagBox/LiveTag";
import {
  getCourseList,
  getLocalList,
  getMountainList,
  getReviewDetail,
} from "src/redux/actions/review";
import {
  changeSelectedPlace,
  resetOption,
  useAppDispatch,
  useAppSelector,
} from "src/redux";
import { ChageSelectedPlace } from "src/types";
import CustomToolbar, { formats, modules } from "../Write/CustomToolbar";

const EditWrite = () => {
  const navigate = useNavigate();
  const user = useUser();
  const params = useParams();
  console.log(params);
  const dispatch = useAppDispatch();
  const { reviewDetail } = useAppSelector((state) => state.review);
  const { localList, mountainList, courseList } = useAppSelector(
    (state) => state.review
  );
  console.log(reviewDetail);
  useEffect(() => {
    dispatch(getReviewDetail({ reviewBoardId: params.id as string }));
  }, [dispatch, params]);

  const [title, setTitle] = useState(reviewDetail?.title);
  const [content, setContent] = useState<string>(reviewDetail?.body || "");
  const [tag, setTag] = useState("");
  const [tags, setTags] = useState<string[]>(reviewDetail?.tagList || []);
  const [isOpen, setIsOpen] = useState(false);
  const [options, setOption] = useState<string[]>([]);
  const [imgText, setImgText] = useState(reviewDetail?.thumbnail);
  const [mainImg, setMainImg] = useState<
    string | null | File | Blob | ProgressEvent<FileReader>
  >(null);

  const [dropDownValue, setDropDownValue] = useState({
    local: "지역",
    mountain: "산 이름",
    course: "등산로",
  });
  const [dropDownIsOpen, setDropDownIsOpen] = useState({
    local: false,
    mountain: false,
    course: false,
  });

  const handleDropDownClick = useCallback(
    (name: string) => {
      const newObj = {
        local: false,
        mountain: false,
        course: false,
      };
      newObj[name as keyof typeof newObj] =
        !dropDownIsOpen[name as keyof typeof newObj];
      setDropDownIsOpen(newObj);
    },
    [dropDownIsOpen]
  );

  const debouceValue = useDebounce(tag);
  const fetchTagList = async () => {
    const result = await axiosAuthInstance.get(`tag?text=${debouceValue}`);
    const tagNames: string[] = result.data.map((data: any) => data.tagName);
    setOption(tagNames);
  };

  useEffect(() => {
    if (debouceValue) fetchTagList();
    if (debouceValue.length === 0) setIsOpen(false);
  }, [debouceValue]);

  useEffect(() => {
    dispatch(resetOption());
    dispatch(getLocalList());
  }, [dispatch]);

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

  const handleDispatch = (payload: ChageSelectedPlace) => {
    const { name } = payload;
    dispatch(changeSelectedPlace(payload));

    if (name === "local") {
      dispatch(getMountainList());
    }

    if (name === "mountain") {
      dispatch(getCourseList());
    }
  };

  const HandleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const requestForm = {
      reviewBoardId: params.id as string,
      memberId: user.memberId,
      title,
      body: content,
      localName: dropDownValue.local,
      mountainName: dropDownValue.mountain,
      courseName: dropDownValue.course,
      thumbnail: mainImg,
      tagList: tags,
    };

    await axiosAuthInstance.patch(
      `/reviewboards/${reviewDetail?.reviewBoardId}`,
      requestForm
    );
    navigate("/main/review");
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
            list={localList}
            name="local"
            isOpen={dropDownIsOpen}
            value={dropDownValue.local}
            setValue={setDropDownValue}
            handleClick={handleDropDownClick}
            dispatch={handleDispatch}
          />
          <DropDown
            width="100%"
            list={mountainList}
            name="mountain"
            isOpen={dropDownIsOpen}
            value={dropDownValue.mountain}
            setValue={setDropDownValue}
            handleClick={handleDropDownClick}
            dispatch={handleDispatch}
          />
        </div>
        <div className="dropdown-column">
          <DropDown
            width="100%"
            list={courseList}
            name="course"
            isOpen={dropDownIsOpen}
            value={dropDownValue.course}
            setValue={setDropDownValue}
            handleClick={handleDropDownClick}
            dispatch={handleDispatch}
          />
        </div>
        <FileBox>
          <label htmlFor="profile">
            {imgText?.slice(0, 30)} <MdOutlineAddAPhoto />
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
