import { MdError } from "react-icons/md";
import { useNavigate } from "react-router-dom";
import { Wrapper } from "./NotFoundWrapper";

const NotFound = () => {
  const navigate = useNavigate();

  return (
    <Wrapper>
      <MdError />
      <h1>
        페이지를 <strong>찾을 수 없습니다.</strong>
      </h1>
      <p>페이지가 존재하지 않거나, 사용할 수 없는 페이지 입니다.</p>
      <p>입력하신 주소가 정확한지 다시 한번 확인해 주시기 바랍니다.</p>
      <div>
        <button type="button" onClick={() => navigate(-1)}>
          이전 화면
        </button>
        <button type="button" onClick={() => navigate("/main")}>
          홈으로 가기
        </button>
      </div>
    </Wrapper>
  );
};

export default NotFound;
