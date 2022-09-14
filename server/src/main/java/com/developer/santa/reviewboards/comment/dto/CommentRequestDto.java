package com.developer.santa.reviewboards.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentRequestDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post{
        private Long reviewBoardId;
        private String memberId;
        private String commentBody;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch {
        private String memberId;
        private String commentBody;
    }
}
