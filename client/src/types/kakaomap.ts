export interface ILatlng {
  lat: number;
  lng: number;
}

export interface IMountain {
  title: string;
  latlng: ILatlng;
  difficulty: string;
}

export interface IBounds {
  sw: ILatlng;
  ne: ILatlng;
}

export interface IRegionCode {
  region_1depth_name: string;
  region_2depth_name: string;
}
