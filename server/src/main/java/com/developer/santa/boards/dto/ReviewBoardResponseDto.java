package com.developer.santa.boards.dto;

import com.developer.santa.member.entity.Member;
import lombok.*;

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
    @AllArgsConstructor
    public static class Detail{
        private long reviewBoardId;
        private String nickName;
        private String courseName;
        private String title;
        private String body;
        private String photo;
        // 태그 리스트
        //회원 정보 + 회원 권한
    }
}
