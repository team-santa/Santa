package com.developer.santa.reviewboards.dto;

import com.developer.santa.member.entity.Member;
import com.developer.santa.tag.entity.TagSelect;
import lombok.*;

import javax.validation.constraints.NotBlank;
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
        private String writer;
        private String title;
        private String photo;


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
        @NotBlank(message = "제목은 공백이 아니어야 합니다.")
        private String writer;

        private String title;
        @NotBlank
        private String body;

        private String photo;
        @NotBlank
        public String localName;
        @NotBlank
        public String mountainName;
        @NotBlank
        private String courseName;

        @NotBlank
        private Long views;

        private List<String> tagList;

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