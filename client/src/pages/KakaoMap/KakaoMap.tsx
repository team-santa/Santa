import React, { useEffect, useRef, useState } from "react";
import { Map } from "react-kakao-maps-sdk";
import { axiosInstance } from "src/utils";
import { IBounds, ILatlng, IMountain, IRegionCode } from "src/types/kakaomap";
import EventMapMarker from "src/components/EventMapMarker/EventMapMarker";

const KakaoMap = () => {
  const mapRef = useRef<kakao.maps.Map>(null);
  const [mountainList, setMountainList] = useState<IMountain[]>([
    {
      title: "관악산",
      latlng: {
        lat: 37.444473113711695,
        lng: 126.96389458550166,
      },
      difficulty: "하",
    },
  ]);
  const [boundInfo, setBoundInfo] = useState<IBounds>();
  const [center, setCenter] = useState<ILatlng>({
    lat: 37.444473113711695,
    lng: 126.96389458550166,
  });
  const [region, setRegion] = useState("");
  const [selectedMarker, setSelectedMarker] = useState<number | null>(null);
  const geocoder = new kakao.maps.services.Geocoder();

  const getMountainLists = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        setCenter({
          lat: position.coords.latitude,
          lng: position.coords.longitude,
        });
      });
    } else {
      console.log("위치 확인 불가");
    }
  };

  const getBoundInfo = (map: kakao.maps.Map) => {
    const centerCoords = mapRef.current?.getCenter();
    setBoundInfo({
      sw: {
        lat: map.getBounds().getSouthWest().getLat(),
        lng: map.getBounds().getSouthWest().getLng(),
      },
      ne: {
        lat: map.getBounds().getNorthEast().getLat(),
        lng: map.getBounds().getNorthEast().getLng(),
      },
    });
    setCenter({
      lat: centerCoords?.getLat() || center.lat,
      lng: centerCoords?.getLng() || center.lng,
    });
  };

  const displayCenterInfo = (result: IRegionCode[], status: string) => {
    if (status === kakao.maps.services.Status.OK) {
      // 행정동의 region_type 값은 'H' 이므로
      const response =
        `${result[0].region_1depth_name} ${result[0].region_2depth_name}`.split(
          " "
        );
      if (result[0].region_1depth_name === "서울특별시") {
        setRegion(result[0].region_1depth_name);
        return result[0].region_1depth_name;
      }
      setRegion(response.slice(0, 2).join(" "));
      return response.slice(0, 2).join(" ");
    }
    return "에러 발생";
  };

  const getMountainList = async (
    swLat: number,
    swLng: number,
    neLat: number,
    neLng: number,
    region: string
  ) => {
    const mountainArr: any[] = [];

    try {
      const response = await axiosInstance
        .get(
          `/connect?geomFilter=BOX(${swLng},${swLat},${neLng},${neLat})&crs=EPSG:4326&page=1&size=20&localName=${region}`
        )
        .then(
          (response) => response.data.response.result.featureCollection.features
        );

      for (let i = 0; i < response.length; i += 1) {
        if (i === 0) mountainArr.push(response[i]);
        else if (
          response[i - 1].properties.mntn_nm !== response[i].properties.mntn_nm
        ) {
          mountainArr.push(response[i]);
        }
      }

      setMountainList(
        mountainArr.map((el: any) => {
          return {
            latlng: {
              lng: el.geometry.coordinates[0][0][0],
              lat: el.geometry.coordinates[0][0][1],
            },
            title: el.properties.mntn_nm,
            difficulty: el.properties.cat_nam,
          };
        })
      );
      return response;
    } catch (error) {
      console.log(error);
      return error;
    }
  };

  useEffect(() => {
    getMountainLists();
    geocoder.coord2RegionCode(center.lng, center.lat, displayCenterInfo);
    if (boundInfo) {
      getMountainList(
        boundInfo.sw.lat,
        boundInfo.sw.lng,
        boundInfo.ne.lat,
        boundInfo.ne.lng,
        region
      );
    }
  }, [center]);

  return (
    <Map
      center={center}
      style={{ width: "100%", height: "100vh" }}
      level={4}
      ref={mapRef}
      onDragEnd={getBoundInfo}
    >
      {mountainList.map((mountain: IMountain, idx: number) => (
        <EventMapMarker
          key={`EventMarkerContainer-${mountain.latlng.lat}-${mountain.latlng.lng}`}
          position={mountain.latlng}
          content={mountain.title}
          difficulty={mountain.difficulty}
          onClick={() => setSelectedMarker(idx)}
          selectedMarker={selectedMarker}
          idx={idx}
        />
      ))}
    </Map>
  );
};

export default KakaoMap;
