package com.developer.santa.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class TagResponseDto {

    @Getter
    @AllArgsConstructor
    public static class Recommend {
//        @NotBlank
//        private long tagId;
        @NotBlank
        private String tagName;
    }
}
