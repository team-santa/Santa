package com.developer.santa.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

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

    @Getter
    @AllArgsConstructor
    public static class Put {

        @NotBlank
        private String username;

        @Email
        private String email;

        private String profileImageUrl;
    }

    @Getter
    @AllArgsConstructor
    public static class Mountain {

        List<String> mountainNames;

    }

}
