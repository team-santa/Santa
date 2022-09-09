package com.developer.santa.reviewboards.dto;

import com.developer.santa.member.entity.Member;
import com.developer.santa.reviewboards.comment.dto.CommentResponseDto;
import com.developer.santa.reviewboards.comment.entity.Comment;
import com.developer.santa.tag.entity.TagSelect;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public class ReviewBoardResponseDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Page{
        private long reviewBoardId;
        @Setter(AccessLevel.NONE)
        private String memberId;

        private String profileImageUrl;
        private String writer;
        private LocalDateTime modifiedAt;
        private String title;
        private Long views;
        private List<String> tagList;
        private String thumbnail;


        public void setWriterAndId(Member member){
            String writer = "unknown";
            String memberId = "0000000000";
            if(member != null ) {
                memberId = member.getMemberId();
                writer = member.getUsername();

            }
            this.memberId = memberId;
            this.writer = writer;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Detail{
        private long reviewBoardId;

        private String memberId;
        private String profileImageUrl;
        @NotBlank(message = "제목은 공백이 아니어야 합니다.")
        private String writer;
        private LocalDateTime modifiedAt;
        private String title;
        @NotBlank
        private String body;

        private String thumbnail;
        @NotBlank
        public String localName;
        @NotBlank
        public String mountainName;
        @NotBlank
        private String courseName;

        @NotBlank
        private Long views;

        private List<String> tagList;

        private List<CommentResponseDto> commentList;
        public void setWriterAndId(Member member){
            String writer = "unknown";
            String memberId = "0000000000";
            if(member != null ) {
                memberId = member.getMemberId();
                writer = member.getUsername();

            }
            this.memberId = memberId;
            this.writer = writer;
        }
    }
}
