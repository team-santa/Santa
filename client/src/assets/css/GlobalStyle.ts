import { createGlobalStyle } from "styled-components";
import reset from "styled-reset";

export const GlobalStyle = createGlobalStyle`
  ${reset}
  html, body {
    font-size: 62.5%;
    height: 100vh;
    box-sizing: border-box;
  }
`;

export default GlobalStyle;
