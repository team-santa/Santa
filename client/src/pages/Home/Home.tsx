import axios from "axios";
import { useEffect, useState } from "react";
import styled from "styled-components";
import { Pagination, Scrollbar } from "swiper";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
// import "swiper/css/navigation";
// import "swiper/css/pagination";
import "swiper/css/scrollbar";
import {
  BsThermometerLow,
  BsThermometerHalf,
  BsThermometerHigh,
  BsUmbrella,
} from "react-icons/bs";
import Sun from "src/assets/images/WeatherIcons/sun.png";
import Night from "src/assets/images/WeatherIcons/night.png";
import Cloudy from "src/assets/images/WeatherIcons/cloudy.png";
import CloudyNight from "src/assets/images/WeatherIcons/cloudynight.png";
import Cloud from "src/assets/images/WeatherIcons/cloud.png";

interface ITodayWeather {
  temp: number;
  precip: number;
  cloud: number;
}

interface IWeekWeather {
  min: number;
  max: number;
  popAm: number;
  popPm: number;
}

// spread operator 쓰면 동작 안됨, 이유 찾아보기
interface IWeekWeatherFrontData {
  a1Day?: IWeekWeather | null;
  a2Day?: IWeekWeather | null;
}

interface IWeekWeatherBackData {
  a3Day?: IWeekWeather | null;
  a4Day?: IWeekWeather | null;
  a5Day?: IWeekWeather | null;
  a6Day?: IWeekWeather | null;
  a7Day?: IWeekWeather | null;
}

const Home = () => {
  const shortWeatherKey = process.env.REACT_APP_WEATHER_KEY_SHORT;
  const midWeatherKey = process.env.REACT_APP_WEATHER_KEY_MID;

  const [todayWeatherData, setTodayWeatherData] = useState<
    ITodayWeather[] | null
  >(null);
  const [weekWeatherFrontData, setWeekWeatherFrontData] =
    useState<IWeekWeatherFrontData | null>(null);
  const [weekWeatherBackData, setWeekWeatherBackData] =
    useState<IWeekWeatherBackData | null>(null);

  const funcGetDate = (date: Date) => {
    const year = date.getFullYear();
    const month = `0${1 + date.getMonth()}`.slice(-2);
    const day = `0${date.getDate()}`.slice(-2);
    const hour = new Date().getHours();

    return { year, month, day, hour };
  };

  const getYesterday = () => {
    const today = new Date();
    const yesterday = new Date(today.setDate(today.getDate() - 1));
    const yestdDate = funcGetDate(yesterday);

    return yestdDate.year + yestdDate.month + yestdDate.day;
  };

  const getToday = () => {
    const today = new Date();
    const todayDate = funcGetDate(today);
    const hour = today.getHours();
    const time = hour >= 19 || hour <= 6 ? "1800" : "0600";

    return {
      curTime: `0${hour}00`.slice(-4),
      today: todayDate.year + todayDate.month + todayDate.day,
      todayShort:
        hour >= 0 && hour <= 2
          ? getYesterday()
          : todayDate.year + todayDate.month + todayDate.day,
      todayMid:
        hour >= 0 && hour <= 6
          ? getYesterday() + time
          : todayDate.year + todayDate.month + todayDate.day + time,
    };
  };

  const getNxtDay = () => {
    const today = new Date();
    const tomorrow = new Date(today.setDate(today.getDate() + 1));
    const dAftTomorrow = new Date(today.setDate(today.getDate() + 1));

    const tmrDate = funcGetDate(tomorrow);
    const dAftTmrDate = funcGetDate(dAftTomorrow);

    return {
      tomorrow: tmrDate.year + tmrDate.month + tmrDate.day,
      dAftTomorrow: dAftTmrDate.year + dAftTmrDate.month + dAftTmrDate.day,
    };
  };

  const getTime = () => {
    const reportTime = [
      "0200",
      "0500",
      "0800",
      "1100",
      "1400",
      "1700",
      "2000",
      "2300",
    ];
    const today = new Date();
    const hour = today.getHours();

    if (hour >= 3 && hour <= 5) return reportTime[0];
    if (hour >= 6 && hour <= 8) return reportTime[1];
    if (hour >= 9 && hour <= 11) return reportTime[2];
    if (hour >= 12 && hour <= 14) return reportTime[3];
    if (hour >= 15 && hour <= 17) return reportTime[4];
    if (hour >= 18 && hour <= 20) return reportTime[5];
    if (hour >= 21 && hour <= 23) return reportTime[6];
    return reportTime[7]; // if (hour >= 0 && hour <= 2)
  };

  const getMidWeather = async () => {
    try {
      const responseTmp = await axios
        .get(
          `http://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa?serviceKey=${midWeatherKey}&numOfRows=10&pageNo=1&regId=11B10101&tmFc=${
            getToday().todayMid
          }&dataType=JSON`
        )
        .then((res) => res.data.response.body.items.item[0]);
      const responsePop = await axios
        .get(
          `http://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst?serviceKey=${midWeatherKey}&numOfRows=100&pageNo=1&regId=11B10101&tmFc=${
            getToday().todayMid
          }&dataType=JSON`
        )
        .then((res) => res.data.response.body.items.item[0]);

      setWeekWeatherBackData({
        a3Day: {
          min: responseTmp.taMin3,
          max: responseTmp.taMax3,
          popAm: responsePop.rnSt3Am,
          popPm: responsePop.rnSt3Pm,
        },
        a4Day: {
          min: responseTmp.taMin4,
          max: responseTmp.taMax4,
          popAm: responsePop.rnSt4Am,
          popPm: responsePop.rnSt4Pm,
        },
        a5Day: {
          min: responseTmp.taMin5,
          max: responseTmp.taMax5,
          popAm: responsePop.rnSt5Am,
          popPm: responsePop.rnSt5Pm,
        },
        a6Day: {
          min: responseTmp.taMin6,
          max: responseTmp.taMax6,
          popAm: responsePop.rnSt6Am,
          popPm: responsePop.rnSt6Pm,
        },
        a7Day: {
          min: responseTmp.taMin7,
          max: responseTmp.taMax7,
          popAm: responsePop.rnSt7Am,
          popPm: responsePop.rnSt7Pm,
        },
      });
    } catch (error) {
      console.log("error", error);
    }
  };

  const calcPop = (arr: number[], start: number, end: number) => {
    return (
      Math.floor(
        arr
          .slice(start, end)
          .map((el: any) => +el.fcstValue)
          .reduce((acc: number, cur: number) => acc + cur) / 120
      ) * 10
    );
  };

  const getTodayInfo = (arr: any, ctg: string, curTime: string) => {
    const targetArr = arr.filter((el: any) => el.category === ctg);
    const targetIdx = targetArr.findIndex((el: any) => el.fcstTime === curTime);
    return targetArr
      .slice(targetIdx, targetIdx + 24)
      .map((el: any) => +el.fcstValue);
  };

  const getShortWeather = async () => {
    try {
      const response = await axios
        .get(
          `http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?serviceKey=${shortWeatherKey}&numOfRows=1000&pageNo=1&base_date=${
            getToday().todayShort
          }&base_time=${getTime()}&nx=59&ny=125&dataType=JSON`
        )
        .then((res) => res.data.response.body.items.item);

      // 현재기온 및 강수 확률
      const todayTmp = getTodayInfo(response, "TMP", getToday().curTime);
      const todayPop = getTodayInfo(response, "POP", getToday().curTime);
      const todaySky = getTodayInfo(response, "SKY", getToday().curTime);

      const weekPop = response.filter((el: any) => {
        const nxtDay = getNxtDay();
        return (
          el.category === "POP" &&
          (el.fcstDate === nxtDay.tomorrow ||
            el.fcstDate === nxtDay.dAftTomorrow)
        );
      });
      const [tmrTmN, tmrTmX, dAftTmrTmN, dAftTmrTmX] = response
        .filter((el: any) => {
          const nxtDay = getNxtDay();
          return (
            (el.category === "TMN" || el.category === "TMX") &&
            (el.fcstDate === nxtDay.tomorrow ||
              el.fcstDate === nxtDay.dAftTomorrow)
          );
        })
        .map((el: any) => +el.fcstValue);
      const tmrPopAm = calcPop(weekPop, 0, 12);
      const tmrPopPm = calcPop(weekPop, 12, 24);
      const dAftTmrPopAm = calcPop(weekPop, 24, 36);
      const dAftTmrPopPm = calcPop(weekPop, 36, 48);

      setTodayWeatherData([
        { temp: todayTmp[0], precip: todayPop[0], cloud: todaySky[0] },
        { temp: todayTmp[1], precip: todayPop[1], cloud: todaySky[1] },
        { temp: todayTmp[2], precip: todayPop[2], cloud: todaySky[2] },
        { temp: todayTmp[3], precip: todayPop[3], cloud: todaySky[3] },
        { temp: todayTmp[4], precip: todayPop[4], cloud: todaySky[4] },
        { temp: todayTmp[5], precip: todayPop[5], cloud: todaySky[5] },
        { temp: todayTmp[6], precip: todayPop[6], cloud: todaySky[6] },
        { temp: todayTmp[7], precip: todayPop[7], cloud: todaySky[7] },
        { temp: todayTmp[8], precip: todayPop[8], cloud: todaySky[8] },
        { temp: todayTmp[9], precip: todayPop[9], cloud: todaySky[9] },
        { temp: todayTmp[10], precip: todayPop[10], cloud: todaySky[10] },
        { temp: todayTmp[11], precip: todayPop[11], cloud: todaySky[11] },
        { temp: todayTmp[12], precip: todayPop[12], cloud: todaySky[12] },
        { temp: todayTmp[13], precip: todayPop[13], cloud: todaySky[13] },
        { temp: todayTmp[14], precip: todayPop[14], cloud: todaySky[14] },
        { temp: todayTmp[15], precip: todayPop[15], cloud: todaySky[15] },
        { temp: todayTmp[16], precip: todayPop[16], cloud: todaySky[16] },
        { temp: todayTmp[17], precip: todayPop[17], cloud: todaySky[17] },
        { temp: todayTmp[18], precip: todayPop[18], cloud: todaySky[18] },
        { temp: todayTmp[19], precip: todayPop[19], cloud: todaySky[19] },
        { temp: todayTmp[20], precip: todayPop[20], cloud: todaySky[20] },
        { temp: todayTmp[21], precip: todayPop[21], cloud: todaySky[21] },
        { temp: todayTmp[22], precip: todayPop[22], cloud: todaySky[22] },
        { temp: todayTmp[23], precip: todayPop[23], cloud: todaySky[23] },
      ]);

      setWeekWeatherFrontData({
        a1Day: { min: tmrTmN, max: tmrTmX, popAm: tmrPopAm, popPm: tmrPopPm },
        a2Day: {
          min: dAftTmrTmN,
          max: dAftTmrTmX,
          popAm: dAftTmrPopAm,
          popPm: dAftTmrPopPm,
        },
      });
    } catch (error) {
      console.log("error", error);
    }
  };

  // TODO: 시간대별로 해/달 바뀌어 나오도록 하기
  const weatherIcons = [Sun, Cloudy, "", Cloud];

  useEffect(() => {
    getShortWeather();
    getMidWeather();
  }, []);

  return (
    <Wrapper>
      <h1>이번주 추천 등산지</h1>
      <img
        src="https://cdn.visitkorea.or.kr/img/call?cmd=VIEW&id=61cfd8fd-d05f-421d-84e4-69b1f2ed4d2d"
        alt="mountain"
      />
      {/* TODO: 설정 아이콘 import 필요 */}
      {/* TODO: 산 이름 설정 결과에 따라 저장 필요 */}
      <h1>관악산 날씨</h1>
      <WeatherContainer>
        <h2>시간대별 날씨</h2>
        <Swiper
          // install Swiper modules
          modules={[Scrollbar, Pagination]}
          spaceBetween={10}
          slidesPerView={3}
          scrollbar={{ draggable: true }}
          pagination={{ clickable: true }}
        >
          {todayWeatherData ? (
            todayWeatherData.map((el: ITodayWeather, idx) => {
              const current = new Date();
              return (
                // eslint-disable-next-line react/no-array-index-key
                <SwiperSlide key={idx}>
                  <TodayWeatherBox>
                    {idx === 0
                      ? "현재"
                      : `${new Date(
                          current.setHours(current.getHours() + idx)
                        ).getHours()}시`}
                    <img src={weatherIcons[el.cloud - 1]} alt="cloud" />
                    <span>
                      <BsThermometerHalf />
                      {el.temp}° <BsUmbrella />
                      {el.precip}%
                    </span>
                  </TodayWeatherBox>
                </SwiperSlide>
              );
            })
          ) : (
            <div>loading...</div>
          )}
        </Swiper>
      </WeatherContainer>

      {/* TODO: component 분리, 효율적으로 refactoring 필요 */}
      {/* TODO: css 변경 적용 필요(UI 개선) */}
      {/* TODO: attribute 표기 필요(링크 아래 참조) */}
      {/* <a href="https://www.flaticon.com/free-icons/weather" title="weather icons">Weather icons created by iconixar - Flaticon</a> */}
      <WeatherContainer>
        <h2>주간 날씨</h2>
        <WeekWeatherBox>
          <div>목</div>
          <div className="firstline">
            <span>최저 | 최고</span>
            <span>
              <BsThermometerLow />
              {weekWeatherFrontData?.a1Day?.min}° | <BsThermometerHigh />
              {weekWeatherFrontData?.a1Day?.max}°
            </span>
          </div>
          <div className="firstline">
            <span>오전 | 오후</span>
            <span>
              <BsUmbrella /> {weekWeatherFrontData?.a1Day?.popAm}% |{" "}
              <BsUmbrella /> {weekWeatherFrontData?.a1Day?.popPm}%
            </span>
          </div>
        </WeekWeatherBox>
        <WeekWeatherBox>
          <div>금</div>
          <div>
            <BsThermometerLow /> {weekWeatherFrontData?.a2Day?.min}° |{" "}
            <BsThermometerHigh /> {weekWeatherFrontData?.a2Day?.max}°
          </div>
          <div>
            <BsUmbrella /> {weekWeatherFrontData?.a2Day?.popAm}% |{" "}
            <BsUmbrella /> {weekWeatherFrontData?.a2Day?.popPm}%
          </div>
        </WeekWeatherBox>
        <WeekWeatherBox>
          <div>토</div>
          <div>
            <BsThermometerLow /> {weekWeatherBackData?.a3Day?.min}° |{" "}
            <BsThermometerHigh />
            {weekWeatherBackData?.a3Day?.max}°
          </div>
          <div>
            <BsUmbrella /> {weekWeatherBackData?.a3Day?.popAm}% | <BsUmbrella />{" "}
            {weekWeatherBackData?.a3Day?.popPm}%
          </div>
        </WeekWeatherBox>
        <WeekWeatherBox>
          <div>일</div>
          <div>
            <BsThermometerLow /> {weekWeatherBackData?.a4Day?.min}° |{" "}
            <BsThermometerHigh /> {weekWeatherBackData?.a4Day?.max}°
          </div>
          <div>
            <BsUmbrella /> {weekWeatherBackData?.a4Day?.popAm}% | <BsUmbrella />{" "}
            {weekWeatherBackData?.a4Day?.popPm}%
          </div>
        </WeekWeatherBox>
        <WeekWeatherBox>
          <div>월</div>
          <div>
            <BsThermometerLow /> {weekWeatherBackData?.a5Day?.min}° |{" "}
            <BsThermometerHigh />
            {weekWeatherBackData?.a5Day?.max}°
          </div>
          <div>
            <BsUmbrella /> {weekWeatherBackData?.a5Day?.popAm}% | <BsUmbrella />{" "}
            {weekWeatherBackData?.a5Day?.popPm}%
          </div>
        </WeekWeatherBox>
        <WeekWeatherBox>
          <div>화</div>
          <div>
            <BsThermometerLow /> {weekWeatherBackData?.a6Day?.min}° |{" "}
            <BsThermometerHigh />
            {weekWeatherBackData?.a6Day?.max}°
          </div>
          <div>
            <BsUmbrella /> {weekWeatherBackData?.a6Day?.popAm}% | <BsUmbrella />{" "}
            {weekWeatherBackData?.a6Day?.popPm}%
          </div>
        </WeekWeatherBox>
        <WeekWeatherBox>
          <div>수</div>
          <div>
            <BsThermometerLow /> {weekWeatherBackData?.a7Day?.min}° |{" "}
            <BsThermometerHigh />
            {weekWeatherBackData?.a7Day?.max}°
          </div>
          <div>
            <BsUmbrella /> {weekWeatherBackData?.a7Day?.popAm}% | <BsUmbrella />{" "}
            {weekWeatherBackData?.a7Day?.popPm}%
          </div>
        </WeekWeatherBox>
      </WeatherContainer>
    </Wrapper>
  );
};

export default Home;

const Wrapper = styled.div`
  display: flex;
  /* justify-content: center; */
  /* margin-top: 6rem; */
  padding: 2rem 0;
  align-items: center;
  width: 100%;
  height: 100vh;
  background-color: white;
  flex-direction: column;

  h1 {
    font-size: 2.4rem;
    margin-bottom: 1rem;
    text-align: start;
    width: 80%;
    color: #016483;
    font-weight: 600;
  }

  h2 {
    margin-bottom: 0.5rem;
    font-size: 18px;
  }

  img {
    width: 80%;
    border-radius: 1rem;
    margin-bottom: 3rem;
  }
`;

const WeatherContainer = styled.div`
  width: 80%;
  box-sizing: border-box;
  background-color: #d4effe;
  padding: 1rem;
  border-radius: 1rem;
  margin-bottom: 1rem;
`;

const TodayWeatherBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  font-size: 1.4rem;
  background-color: white;
  border-radius: 1rem;
  padding: 0.5rem;
  margin-bottom: 2rem;

  & span {
    margin-bottom: 0.5rem;
  }

  & img {
    width: 3rem;
    margin-bottom: 1rem;
  }
`;

const WeekWeatherBox = styled.div`
  margin-bottom: 1rem;
  padding: 1rem 2rem;
  border-radius: 1rem;
  font-size: 1.4rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: white;

  & .firstline {
    display: flex;
    flex-direction: column;
    text-align: center;
  }
`;
