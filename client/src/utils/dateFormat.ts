/** getDateToString('2021-11-17') or getDateToString('2021-11-17 00:00:00') */
export const getDateToString = (value: string) => {
  const today = new Date();
  const timeValue = new Date(`${value}`);

  const betweenTime = Math.floor(
    (today.getTime() - timeValue.getTime()) / 1000 / 60
  );
  if (betweenTime < 60) {
    return `${betweenTime}분 전`;
  }

  const betweenTimeHour = Math.floor(betweenTime / 60);
  if (betweenTimeHour < 24) {
    return `${betweenTimeHour}시간 전`;
  }

  const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
  if (betweenTimeDay < 365) {
    return `${betweenTimeDay}일 전`;
  }

  const betweenTimeMonth = Math.floor(betweenTimeDay / 60 / 24 / 30);
  if (betweenTimeMonth < 12) {
    return `${betweenTimeMonth}달 전`;
  }

  return `${Math.floor(betweenTimeMonth / 12)}년 전`;
};
