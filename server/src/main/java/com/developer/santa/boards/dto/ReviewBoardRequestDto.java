package com.developer.santa.boards.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

public class ReviewBoardRequestDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post{
        @NotBlank
        public String localName;
        @NotBlank
        public String mountainName;
        @NotBlank
        private String courseName;
        @NotBlank(message = "제목은 공백이 아니어야 합니다.")
        private String title;

        @NotBlank
        private String body;

        private String photo;
    }
    @Getter
    public static class Patch{
        private long reviewBoardId;

        @NotBlank
        public String localName;
        @NotBlank
        public String mountainName;
        @NotBlank(message = "방문한 코스는 필수로 있어야 합니다.")
        private String courseName;

        @NotBlank(message = "제목은 공백이 아니어야 합니다.")
        private String title;
        @NotBlank
        public String body;

        public String photo;

        public void setReviewBoardId(long reviewBoardId) {
            this.reviewBoardId = reviewBoardId;
        }
    }

}
