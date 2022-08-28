package com.developer.santa.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class MemberDto {

    @Getter
    @AllArgsConstructor
    public static class Response {

        @NotBlank
        private String username;

        @Email
        private String email;

        private String profileImageUrl;
    }
}
