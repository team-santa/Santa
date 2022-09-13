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
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                post("/v1/comment")
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

/*
    @Test
    public void putMember() throws Exception {
        String memberId = "1";

        MemberDto.Put put = new MemberDto.Put("testUser", "testEmail@email.com", "profileImageUrl");
        String body = gson.toJson(put);
        MemberDto.Response response = new MemberDto.Response("testUser", "testEmail@email.com", "profileImageUrl");

        given(memberMapper.memberPutDtoToMember(Mockito.any(MemberDto.Put.class))).willReturn(new Member());

        given(memberService.putMember(Mockito.anyString(),
                Mockito.any(Member.class),
                Mockito.any(PrincipalDetails.class),
                Mockito.any(HttpServletResponse.class))).willReturn(new Member());

        given(memberMapper.memberToMemberDtoResponse(Mockito.any(Member.class))).willReturn(response);

        ResultActions actions = mock.perform(
                put("/members/{memberId}", memberId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(body)
        );

        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(put.getUsername()))
                .andExpect(jsonPath("$.email").value(put.getEmail()))
                .andExpect(jsonPath("$.profileImageUrl").value(put.getProfileImageUrl()))
                .andDo(document("put-profile",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("memberId").description("회원 고유ID")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("username").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("프로필 이미지 경로")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("username").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("프로필 이미지 경로")
                                )
                        )
                ));
    }
*/

/*    @RequestMapping("v1/comment")
    public class CommentController {
        private final CommentService commentService;
        private final CommentMapper mapper;
        @PostMapping // 댓글 생성
        public ResponseEntity<?> createComment(@Valid @RequestBody CommentRequestDto.Post requestBody){
            Comment comment = mapper.commentPostToComment(requestBody);
            Comment createComment = commentService.createComment(comment);
            return new ResponseEntity<>(mapper.commentToResponseComment(createComment), HttpStatus.CREATED);
        }
        @GetMapping("{reviewBoardId}") // 게시판 전체 댓글 조회
        public ResponseEntity<?> findComment(@PathVariable Long reviewBoardId){
            return null;
        }

        @PatchMapping("{commentId}") // 댓글 수정
        public ResponseEntity<?> editComment(
                @PathVariable Long commentId,
                @Valid @RequestBody CommentRequestDto.Patch requestBody){
            Comment editComment = mapper.commentPatchToComment(requestBody);
            Comment updateBoard = commentService.updateComment(commentId, editComment);
            return new ResponseEntity<>(mapper.commentToResponseComment(updateBoard), HttpStatus.OK);
        }

        @DeleteMapping("{commentId}") // 댓글 삭제
        public ResponseEntity<?> deleteComment(@PathVariable Long commentId){
            commentService.deleteReviewBoard(commentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }*/
}