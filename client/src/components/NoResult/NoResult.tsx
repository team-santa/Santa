import { Link } from "react-router-dom";
import styled from "styled-components";

const SContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 20px;
  padding-top: 20px;

  & > img {
    object-fit: cover;
  }

  & > p {
    font-size: 16px;
  }

  & > a {
    color: #3b7693;
    font-size: 12px;
    text-decoration: none;
    outline: none;

    &:hover,
    &:active {
      color: #3b7693;
      text-decoration: none;
    }
  }
`;

const NoResult = () => {
  return (
    <SContainer>
      <img
        src="http://www.bullmetrix.com/wp-content/plugins/elementor/assets/images/no-search-results.svg"
        alt="no-result"
      />
      <p>검색 결과가 없습니다.</p>
      <Link to="/main/write">리뷰 등록하러 가기</Link>
    </SContainer>
  );
};

export default NoResult;
