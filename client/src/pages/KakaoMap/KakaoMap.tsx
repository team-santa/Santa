import React, { useEffect, useRef, useState } from "react";
import { Map, MapMarker } from "react-kakao-maps-sdk";

interface ILocation {
  title: string;
  latlng: {
    lat: number;
    lng: number;
  };
}

interface IBounds {
  sw: string;
  ne: string;
}

const KakaoMap = () => {
  const mapRef = useRef<kakao.maps.Map>(null);
  const [location, setLocation] = useState<ILocation[]>([
    {
      title: "관악산",
      latlng: {
        lat: 37.444473113711695,
        lng: 126.96389458550166,
      },
    },
  ]);
  const [boundInfo, setBoundInfo] = useState<IBounds>({ sw: "", ne: "" });

  const getLocation = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) =>
        setLocation([
          {
            title: "",
            latlng: {
              lat: position.coords.latitude,
              lng: position.coords.longitude,
            },
          },
        ])
      );
    } else {
      console.log("위치 확인 불가");
    }
  };

  function getData() {
    setTimeout(() => {
      const map = mapRef.current;
      console.log(
        `sw: ${map?.getBounds().getSouthWest()}`,
        `ne: ${map?.getBounds().getNorthEast()}`
      );
    }, 1000);
  }

  useEffect(() => {
    getLocation();
    console.log("location", location);
    getData();
  }, []);

  return (
    <div>
      <Map
        center={{ lat: location[0].latlng.lat, lng: location[0].latlng.lng }}
        style={{ width: "100%", height: "100vh" }}
        level={4}
        ref={mapRef}
        onBoundsChanged={(map) => {
          setBoundInfo({
            sw: map.getBounds().getSouthWest().toString(),
            ne: map.getBounds().getNorthEast().toString(),
          });
          console.log(boundInfo);
        }}
      >
        {location.map((location: ILocation, idx: number) => (
          <MapMarker
            key={`${location.title}-${location.latlng}`}
            position={location.latlng}
            image={{
              src: "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png", // 마커이미지의 주소입니다
              size: {
                width: 24,
                height: 35,
              },
            }}
            title={location.title}
          />
        ))}
      </Map>
    </div>
  );
};

export default KakaoMap;
