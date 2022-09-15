package com.developer.santa.reviewboards.comment.controller;

import com.developer.santa.reviewboards.comment.dto.CommentRequestDto;
import com.developer.santa.reviewboards.comment.dto.CommentResponseDto;
import com.developer.santa.reviewboards.comment.entity.Comment;
import com.developer.santa.reviewboards.comment.mapper.CommentMapper;
import com.developer.santa.reviewboards.comment.service.CommentService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({CommentController.class})
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WithMockUser
class CommentControllerTest {

    @Autowired
    private MockMvc mock;

    @MockBean
    private CommentService commentService;

    @MockBean
    private CommentMapper commentMapper;

    @Autowired
    private Gson gson;

    @Test
    public void createComment() throws Exception {
        CommentRequestDto.Post postCommentDto = new CommentRequestDto.Post(1L, "1234567", "commentBody");
        String body = gson.toJson(postCommentDto);
        CommentResponseDto commentResponseDto = new CommentResponseDto(1L, "1234567", "profileImgUrl", "testUser", LocalDateTime.now(), "commentBody");

        given(commentMapper.commentPostToComment(Mockito.any(CommentRequestDto.Post.class))).willReturn(new Comment());
        given(commentService.createComment(Mockito.any(Comment.class))).willReturn(new Comment());
        given(commentMapper.commentToResponseComment(Mockito.any(Comment.class))).willReturn(commentResponseDto);

        ResultActions actions = mock.perform(
                post("/comment")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(body)
        );

        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.memberId").value(commentResponseDto.getMemberId()))
                .andExpect(jsonPath("$.profileImageUrl").value(commentResponseDto.getProfileImageUrl()))
                .andExpect(jsonPath("$.writer").value(commentResponseDto.getWriter()))
                .andExpect(jsonPath("$.body").value(commentResponseDto.getBody()))
                .andDo(document("post-comment",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                List.of(
                                        fieldWithPath("reviewBoardId").type(JsonFieldType.NUMBER).description("리뷰게시글ID"),
                                        fieldWithPath("memberId").type(JsonFieldType.STRING).description("회원 고유ID"),
                                        fieldWithPath("commentBody").type(JsonFieldType.STRING).description("댓글 본문")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("댓글ID"),
                                        fieldWithPath("memberId").type(JsonFieldType.STRING).description("회원 고유ID"),
                                        fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("프로필 이미지경로"),
                                        fieldWithPath("writer").type(JsonFieldType.STRING).description("작성자 닉네임"),
                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("댓글 수정날짜"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("댓글 본문")
                                )
                        )
                ));
    }

    @Test
    public void patchComment() throws Exception {
        Long commentId = 1L;

        CommentRequestDto.Patch patchCommentDto = new CommentRequestDto.Patch("1234567", "commentBody");
        String body = gson.toJson(patchCommentDto);
        CommentResponseDto commentResponseDto = new CommentResponseDto(1L, "1234567", "profileImgUrl", "testUser", LocalDateTime.now(), "commentBody");

        given(commentMapper.commentPatchToComment(Mockito.any(CommentRequestDto.Patch.class))).willReturn(new Comment());
        given(commentService.updateComment(Mockito.anyLong(), Mockito.any(Comment.class))).willReturn(new Comment());
        given(commentMapper.commentToResponseComment(Mockito.any(Comment.class))).willReturn(commentResponseDto);

        ResultActions actions = mock.perform(
                patch("/comment/{commentId}", commentId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(body)
        );

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").value(commentResponseDto.getMemberId()))
                .andExpect(jsonPath("$.profileImageUrl").value(commentResponseDto.getProfileImageUrl()))
                .andExpect(jsonPath("$.writer").value(commentResponseDto.getWriter()))
                .andExpect(jsonPath("$.body").value(commentResponseDto.getBody()))
                .andDo(document("patch-comment",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("commentId").description("댓글ID")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.STRING).description("회원 고유ID"),
                                        fieldWithPath("commentBody").type(JsonFieldType.STRING).description("수정할 본문")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("댓글ID"),
                                        fieldWithPath("memberId").type(JsonFieldType.STRING).description("회원 고유ID"),
                                        fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("프로필 이미지경로"),
                                        fieldWithPath("writer").type(JsonFieldType.STRING).description("작성자 닉네임"),
                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("댓글 수정날짜"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("댓글 본문")
                                )
                        )
                ));
    }

    @Test
    public void deleteComment() throws Exception {
        Long commentId = 1L;

        ResultActions actions = mock.perform(
                delete("/comment/{commentId}", commentId)
                        .with(csrf())
        );

        actions
                .andExpect(status().isNoContent())
                .andDo(document("delete-comment",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("commentId").description("댓글ID")
                        )
                ));
    }
}