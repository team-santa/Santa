import { useEffect, useState } from "react";

const useDebounce = (value: string, delay = 500) => {
  const [debouceValue, setDebouceValue] = useState(value);

  useEffect(() => {
    const handlerId = setTimeout(() => {
      setDebouceValue(value);
    }, delay);
    return () => {
      clearTimeout(handlerId);
    };
  }, [value, delay]);

  return debouceValue;
};

export default useDebounce;
