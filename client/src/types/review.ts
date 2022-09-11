export interface ReviewInitialState {
  isLoading: boolean;
  reviewList: Array<Review>;
  reviewDetail: ReviewDetail | null;
  pageInfo: PageInfo;
  localList: Array<string>;
  mountainList: Array<string>;
  selectedLocal: string;
  selectedMountain: string;
  selectedCourse: string;
  sortByViews: boolean;
  courseList: Array<string>;
  currentPage: number;
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
  profileImageUrl: string;
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
}

export interface ReviewDetail extends Review {
  body: string;
  localName: string;
  mountainName: string;
  courseName: string;
}

export type SelectOption = "local" | "mountain" | "course";

export interface ChageSelectedPlace {
  name: SelectOption;
  value: string;
}
