/* eslint-disable react/no-array-index-key */
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import "swiper/css/navigation";
import { Navigation } from "swiper";
import { Wrapper } from "./SliderWrapper";

interface Props {
  imgList: Array<string>;
}

const Slider: React.FC<Props> = ({ imgList }) => {
  return (
    <Wrapper>
      <Swiper spaceBetween={30} navigation modules={[Navigation]}>
        {imgList.map((img, index) => (
          <SwiperSlide key={index}>
            <img src={img} alt="산" />
          </SwiperSlide>
        ))}
      </Swiper>
    </Wrapper>
  );
};

export default Slider;
