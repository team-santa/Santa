package com.developer.santa.reviewboards.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private String memberId;
    private String profileImageUrl;
    private String writer;
    private LocalDateTime modifiedAt;
    private String body;
}
