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
        private String nickName;
        private String title;
        private String photo;


        public void setNickName(Member member){
            String nick = "unknown";
            if(member != null)
                nick = member.getUsername();
            this.nickName = nick;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Detail{
        private long reviewBoardId;

        private String nickName;
        @NotBlank(message = "제목은 공백이 아니어야 합니다.")
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

        public void setNickName(Member member){
            String nick = "unknown";
            if(member != null)
                nick = member.getUsername();
            this.nickName = nick;
        }
    }
}
