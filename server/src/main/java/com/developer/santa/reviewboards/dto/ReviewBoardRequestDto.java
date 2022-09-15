package com.developer.santa.reviewboards.dto;

import com.developer.santa.member.entity.Member;
import com.developer.santa.member.service.MemberService;
import com.developer.santa.tag.entity.Tag;
import com.developer.santa.tag.entity.TagSelect;
import lombok.*;
import org.springframework.context.annotation.Bean;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;


public class ReviewBoardRequestDto {


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post{

        private String memberId;
        @NotBlank(message = "제목은 공백이 아니어야 합니다.")
        private String title;
        @NotBlank
        private String body;
        @NotBlank
        public String localName;
        @NotBlank
        public String mountainName;
        @NotBlank
        private String courseName;

        private String thumbnail;

        private List<String> tagList;


        public Member getMember() {
            Member member = new Member();
            member.setMemberId(memberId);
            return member;
        }
        public Tag getTag(String tagName){
            Tag tag = new Tag();
            tag.setTagName(tagName);
            return tag;
        }
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch{

        private long reviewBoardId;

        @NotBlank
        private String memberId;
        @NotBlank(message = "제목은 공백이 아니어야 합니다.")
        private String title;
        @NotBlank
        private String body;
        @NotBlank
        public String localName;
        @NotBlank
        public String mountainName;
        @NotBlank
        private String courseName;

        private String thumbnail;

        private List<String> tagList;



        public void setReviewBoardId(long reviewBoardId) {
            this.reviewBoardId = reviewBoardId;
        }

//        public String getThumbnail() {
//            return thumbnail.orElse(null);
//        }
    }


}
