/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable react/jsx-no-comment-textnodes */
import { MdKeyboardArrowDown } from "react-icons/md";
import { Dispatch, MouseEvent, useCallback, SetStateAction } from "react";
import { Wrapper } from "./DropDownWrapper";

interface Props {
  width: string;
  list: Array<string>;
  name: string;
  isOpen: { [name: string]: boolean };
  value: string;
  setValue: Dispatch<SetStateAction<any>>;
  handleClick: (name: string) => void;
  dispatch: (name: string) => void;
}

const DropDown: React.FC<Props> = ({
  width,
  list,
  name,
  isOpen,
  value,
  setValue,
  handleClick,
  dispatch,
}) => {
  const handleListClick = useCallback(
    (event: MouseEvent<HTMLUListElement>) => {
      const selected = (event.target as HTMLLIElement).id;
      setValue((state: object) => {
        return { ...state, [name]: selected };
      });
      handleClick(selected);
    },
    [name, setValue, handleClick]
  );

  return (
    <Wrapper isOpen={isOpen[name]} width={width}>
      <button type="button" onClick={() => handleClick(name)}>
        <h4>{value}</h4>
        <MdKeyboardArrowDown />
      </button>
      {list.length > 0 && (
        <ul onClick={handleListClick}>
          {list.map((el) => (
            <li key={el} id={el} onClick={() => dispatch(el)}>
              {el}
            </li>
          ))}
        </ul>
      )}
    </Wrapper>
  );
};

export default DropDown;
