package com.developer.santa.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class MemberDto {

    @Getter
    @AllArgsConstructor
    public static class Response {
        private String username;
        private String email;
        private String profileImageUrl;
    }
}
