/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable react/jsx-no-comment-textnodes */
import { MdKeyboardArrowDown } from "react-icons/md";
import { MouseEvent, useCallback, useState } from "react";
import { Wrapper } from "./DropDownWrapper";

interface Props {
  width: string;
  list: Array<string>;
  value: string;
  name: string;
  isOpen: {
    [name: string]: boolean;
  };
  handleClick: (name: string) => void;
}

const DropDown: React.FC<Props> = ({
  width,
  value,
  list,
  name,
  isOpen,
  handleClick,
}) => {
  // const [isOpen, setIsOpen] = useState(false);
  const [dropDownTitle, setDropDownTitle] = useState(value);

  const handleListClick = useCallback(
    (event: MouseEvent<HTMLUListElement>) => {
      const selected = (event.target as HTMLLIElement).id;
      setDropDownTitle(selected);
      handleClick(name);
    },
    [name, handleClick]
  );

  return (
    <Wrapper isOpen={isOpen[name]} width={width}>
      <button type="button" onClick={() => handleClick(name)}>
        <h4>{dropDownTitle}</h4>
        <MdKeyboardArrowDown />
      </button>
      <ul onClick={handleListClick}>
        {list.map((el) => (
          <li key={el} id={el}>
            {el}
          </li>
        ))}
      </ul>
    </Wrapper>
  );
};

export default DropDown;
