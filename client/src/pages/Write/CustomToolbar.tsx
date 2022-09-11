/* eslint-disable react/button-has-type */
import React from "react";

export const modules = {
  toolbar: {
    container: "#toolbar",
  },
};
export const formats = [
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
const CustomToolbar = () => {
  return (
    <div>
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
      </div>
    </div>
  );
};

export default CustomToolbar;
