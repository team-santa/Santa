import { MdKeyboardArrowDown } from "react-icons/md";
import { MouseEvent, useCallback, useState } from "react";
import { Wrapper } from "./wrapper";

interface Props {
  width: string;
  list: Array<string>;
  title: string;
}

const DropDown: React.FC<Props> = ({ width, title, list }) => {
  const [isOpen, setIsOpen] = useState(false);
  const [dropDownTitle, setDropDownTitle] = useState(title);

  const handleListClick = useCallback(
    (event: MouseEvent<HTMLUListElement>) => {
      const selected = (event.target as HTMLLIElement).id;
      setDropDownTitle(selected);
      setIsOpen(!isOpen);
    },
    [isOpen]
  );

  return (
    <Wrapper isOpen={isOpen} width={width}>
      <button type="button" onClick={() => setIsOpen((prev) => !prev)}>
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
