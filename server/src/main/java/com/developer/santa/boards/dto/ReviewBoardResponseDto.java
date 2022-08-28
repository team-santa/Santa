package com.developer.santa.boards.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ReviewBoardResponseDto {
    @Getter
    @AllArgsConstructor
    public static class Page{
        private long reviewBoardId;
        private String nickName;
        private String title;
        public String photo;
    }

    @Getter
    @AllArgsConstructor
    public static class Detail{
        private long reviewBoardId;
        private String nickName;
        private String courseName;
        private String title;
        public String body;
        public String photo;
        // 태그 리스트
        //회원 정보 + 회원 권한
    }
}
