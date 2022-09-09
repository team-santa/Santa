import React from "react";
import ReactDOM from "react-dom";
import { Provider } from "react-redux";
import App from "./App";
import GlobalStyle from "./assets/css/GlobalStyle";
import { store } from "./redux";
import { Modal } from "./components/Modal";

ReactDOM.render(
  <React.StrictMode>
    <Provider store={store}>
      <GlobalStyle />
      <Modal background>
        <App />
      </Modal>
    </Provider>
  </React.StrictMode>,
  document.getElementById("root")
);
