export interface ReviewInitialState {
  isLoading: boolean;
  reviewList: Array<Review>;
  pageInfo: PageInfo;
  localList: Array<string>;
  mountainList: Array<string>;
  courseList: Array<string>;
}

export interface ReviewList {
  data: Array<Review>;
  pageInfo: PageInfo;
}

export interface Review {
  reviewBoardId: number;
  memberId: string;
  title: string;
  thumbnail: string;
  writer: string;
  profileImgUrl: string;
  modifiedAt: string;
  views: number;
  tagList: Array<string>;
}

export interface PageInfo {
  page: number;
  size: number;
  totalElement: number;
  totalPages: number;
}

export interface ReviewListPayload {
  local: string;
  mountain: string;
  course: string;
  page: number;
}
