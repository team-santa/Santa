package com.developer.santa.reviewboards.controller;

import com.developer.santa.member.annotation.WithMockCustomUser;
import com.developer.santa.member.entity.Member;
import com.developer.santa.member.oauth.entity.RoleType;
import com.developer.santa.member.oauth.token.AuthToken;
import com.developer.santa.member.oauth.token.AuthTokenProvider;
import com.developer.santa.reviewboards.dto.ReviewBoardRequestDto;
import com.developer.santa.reviewboards.dto.ReviewBoardResponseDto;
import com.developer.santa.reviewboards.entity.ReviewBoard;
import com.developer.santa.reviewboards.mapper.ReviewBoardMapper;
import com.developer.santa.reviewboards.service.ReviewBoardService;
import com.developer.santa.utils.HeaderUtils;
import com.google.gson.Gson;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewBoardsController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WithMockCustomUser
class ReviewBoardsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private ReviewBoardService reviewBoardService;

    @MockBean
    private ReviewBoardMapper mapper;


    // 토큰값 필요
    @Test
    void postReview() throws Exception {

        //given
        ReviewBoardRequestDto.Post post = new ReviewBoardRequestDto.Post(
                "2411017009",
                "테스트제목",
                "바디내용",
                "서울시",
                "관악산",
                "등산코스A",
                "썸네일.png",
                new ArrayList<>(List.of(new String[]{"정상", "등산"})));
        String content = gson.toJson(post);
        LocalDateTime dateTime = LocalDateTime.now();
        ReviewBoardResponseDto.Detail response = new ReviewBoardResponseDto.Detail(
                1L,
                "2411017009",
                "프로필이미지.jpg",
                "글쓴이", dateTime,
                "테스트제목",
                "바디내용",
                "썸네일.png",
                "서울시",
                "관악산",
                "등산코스A",
                1L,
                new ArrayList<>(List.of(new String[]{"정상", "등산"})), //태그
                new ArrayList<>()); // 댓글

        given(mapper.reviewBoardPostToReviewBoard(Mockito.any(ReviewBoardRequestDto.Post.class))).willReturn(new ReviewBoard());

        given(reviewBoardService.createMyBoard(Mockito.any(ReviewBoard.class))).willReturn(new ReviewBoard());

        given(mapper.reviewBoardToDetail(Mockito.any(ReviewBoard.class))).willReturn(response);

        // when
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/reviewboards")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content));
        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.memberId").value("2411017009"))
                .andDo(document("post-reviewBoard",
                        requestFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.STRING).description("회원식별ID"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("리뷰게시판 제목"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("리뷰게시판 내용"),
                                        fieldWithPath("localName").type(JsonFieldType.STRING).description("지역"),
                                        fieldWithPath("mountainName").type(JsonFieldType.STRING).description("산"),
                                        fieldWithPath("courseName").type(JsonFieldType.STRING).description("코스"),
                                        fieldWithPath("thumbnail").type(JsonFieldType.STRING).description("썸네일"),
                                        fieldWithPath("tagList").type(JsonFieldType.ARRAY).description("태그리스트")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("reviewBoardId").type(JsonFieldType.NUMBER).description("게시판 식별 ID"),
                                        fieldWithPath("memberId").type(JsonFieldType.STRING).description("회원 식별 ID"),
                                        fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("프로필이미지"),
                                        fieldWithPath("writer").type(JsonFieldType.STRING).description("작성자"),
                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("마지막수정날짜"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("게시글 본문"),
                                        fieldWithPath("thumbnail").type(JsonFieldType.STRING).description("썸네일이미지"),
                                        fieldWithPath("localName").type(JsonFieldType.STRING).description("지역"),
                                        fieldWithPath("mountainName").type(JsonFieldType.STRING).description("산"),
                                        fieldWithPath("courseName").type(JsonFieldType.STRING).description("코스"),
                                        fieldWithPath("views").type(JsonFieldType.NUMBER).description("조회수"),
                                        fieldWithPath("tagList").type(JsonFieldType.ARRAY).description("태그리스트"),
                                        fieldWithPath("commentList").type(JsonFieldType.ARRAY).description("댓글 리스트")

                                )
                        )
                )).andReturn();


    }

    @Test
    void courseReview() throws Exception {
        //given
        String local = "";
        String mountain = "";
        String course = "";
        String sort = "views";
        String page = "1";
        LocalDateTime dateTime = LocalDateTime.now();

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("local", local);
        queryParams.add("mountain", mountain);
        queryParams.add("course", course);
        queryParams.add("sort", sort);
        queryParams.add("page", page);

        ReviewBoard reviewBoard1 = new ReviewBoard(1L, 1L, "테스트제목1", "썸네일이미지1.png", new ArrayList<>(List.of(new String[]{"정상", "등산"})));
        ReviewBoard reviewBoard2 = new ReviewBoard(2L, 2L, "테스트제목2", "썸네일이미지2.png", new ArrayList<>(List.of(new String[]{"정상", "등산"})));
        Member member1 = new Member("memberId1", "첫번째", "프로필이미지.png");
        Member member2 = new Member("memberId2", "두번째", "프로필이미지.png");
        reviewBoard1.setMember(member1);
        reviewBoard2.setMember(member2);

        Page<ReviewBoard> reviewBoards = new PageImpl<>(List.of(reviewBoard1, reviewBoard2),
                PageRequest.of(0, 10, Sort.by("reviewBoardId").descending()), 2);

        List<ReviewBoardResponseDto.Page> responses = List.of(
                new ReviewBoardResponseDto.Page(1L,
                        "memberId1",
                        "프로필이미지.png",
                        "첫번째",
                        dateTime,
                        "테스트제목1",
                        1L,
                        new ArrayList<>(List.of(new String[]{"정상", "등산"})),
                        "썸네일이미지1.png"),
                new ReviewBoardResponseDto.Page(2L,
                        "memberId2",
                        "프로필이미지.png",
                        "두번째",
                        dateTime,
                        "테스트제목2",
                        2L,
                        new ArrayList<>(List.of(new String[]{"정상", "등산"})),
                        "썸네일이미지2.png")
        );

        given(reviewBoardService.findReviewBoards(Mockito.anyInt(), Mockito.anyMap(), Mockito.anyString())).willReturn(reviewBoards);
        given(mapper.reviewBoardListToPages(Mockito.anyList())).willReturn(responses);
        //when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/reviewboards")
                        .params(queryParams)
                        .accept(MediaType.APPLICATION_JSON));
        //then
        actions
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "get-reviewBoards",
                                requestParameters(
                                        List.of(
                                                parameterWithName("local").description("지역"),
                                                parameterWithName("mountain").description("산"),
                                                parameterWithName("course").description("코스"),
                                                parameterWithName("sort").description("정렬방법"),
                                                parameterWithName("page").description("Page 번호")
                                        )
                                ),
                                responseFields(
                                        Arrays.asList(
                                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터")
                                                        .optional(),
                                                fieldWithPath("data[].reviewBoardId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                                fieldWithPath("data[].memberId").type(JsonFieldType.STRING).description("회원 식별 ID"),
                                                fieldWithPath("data[].profileImageUrl").type(JsonFieldType.STRING).description("프로필이미지"),
                                                fieldWithPath("data[].writer").type(JsonFieldType.STRING).description("작성자"),
                                                fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("마지막수정날짜"),
                                                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                                fieldWithPath("data[].views").type(JsonFieldType.NUMBER).description("조회수"),
                                                fieldWithPath("data[].tagList").type(JsonFieldType.ARRAY).description("태그리스트"),
                                                fieldWithPath("data[].thumbnail").type(JsonFieldType.STRING).description("썸네일이미지"),
                                                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보")
                                                        .optional(),
                                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 번호")
                                                        .optional(),
                                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 사이즈")
                                                        .optional(),
                                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 건 수")
                                                        .optional(),
                                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                                                        .optional()
                                        )
                                )
                        )
                )
                .andReturn();
    }

    @Test
    void findReviewBoard() throws Exception {
        //given
        Long reviewBoardId = 1L;

        LocalDateTime dateTime = LocalDateTime.now();

        ReviewBoardResponseDto.Detail response = new ReviewBoardResponseDto.Detail(
                1L,
                "2411017009",
                "프로필이미지.jpg",
                "글쓴이", dateTime,
                "테스트제목",
                "바디내용",
                "썸네일.png",
                "서울시",
                "관악산",
                "등산코스A",
                1L,
                new ArrayList<>(List.of(new String[]{"정상", "등산"})), //태그
                new ArrayList<>()); // 댓글

        given(reviewBoardService.findReviewBoard(Mockito.anyLong(), Mockito.anyString())).willReturn(new ReviewBoard());
        given(mapper.reviewBoardToDetail(Mockito.any(ReviewBoard.class))).willReturn(response);

        //when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/reviewboards/{reviewBoardId}", reviewBoardId)
                        .accept(MediaType.APPLICATION_JSON));
        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").value("2411017009"))
                .andDo(document("get-reviewBoard",
                        pathParameters(
                                Arrays.asList(parameterWithName("reviewBoardId").description("게시판식별자 ID"))
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("reviewBoardId").type(JsonFieldType.NUMBER).description("게시판 식별 ID"),
                                        fieldWithPath("memberId").type(JsonFieldType.STRING).description("회원 식별 ID"),
                                        fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("프로필이미지"),
                                        fieldWithPath("writer").type(JsonFieldType.STRING).description("작성자"),
                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("마지막수정날짜"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("게시글 본문"),
                                        fieldWithPath("thumbnail").type(JsonFieldType.STRING).description("썸네일이미지"),
                                        fieldWithPath("localName").type(JsonFieldType.STRING).description("지역"),
                                        fieldWithPath("mountainName").type(JsonFieldType.STRING).description("산"),
                                        fieldWithPath("courseName").type(JsonFieldType.STRING).description("코스"),
                                        fieldWithPath("views").type(JsonFieldType.NUMBER).description("조회수"),
                                        fieldWithPath("tagList").type(JsonFieldType.ARRAY).description("태그리스트"),
                                        fieldWithPath("commentList").type(JsonFieldType.ARRAY).description("댓글 리스트")

                                )
                        )
                ));

    }

    @Test
    void editReviewBoard() throws Exception {
        //given
        Long reviewBoardId = 1L;
        LocalDateTime dateTime = LocalDateTime.now();

        ReviewBoardRequestDto.Patch patch = new ReviewBoardRequestDto.Patch(
                "2411017009",
                "테스트제목",
                "바디내용",
                "서울시",
                "관악산",
                "등산코스A",
                "썸네일.png",
                new ArrayList<>(List.of(new String[]{"정상", "등산"})) //태그
        );

        String content = gson.toJson(patch);

        ReviewBoardResponseDto.Detail response = new ReviewBoardResponseDto.Detail(
                1L,
                "2411017009",
                "프로필이미지.jpg",
                "글쓴이", dateTime,
                "테스트제목",
                "바디내용",
                "썸네일.png",
                "서울시",
                "관악산",
                "등산코스A",
                1L,
                new ArrayList<>(List.of(new String[]{"정상", "등산"})), //태그
                new ArrayList<>()); // 댓글

        given(mapper.reviewBoardPatchToReviewBoard(Mockito.any(ReviewBoardRequestDto.Patch.class))).willReturn(new ReviewBoard());

        given(reviewBoardService.updateMyBoard(Mockito.anyLong(), Mockito.any(ReviewBoard.class))).willReturn(new ReviewBoard());

        given(mapper.reviewBoardToDetail(Mockito.any(ReviewBoard.class))).willReturn(response);
        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .patch("/reviewboards/{reviewBoardId}", reviewBoardId)
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").value("2411017009"))
                .andDo(document("patch-reviewBoard",
                        pathParameters(
                                Arrays.asList(parameterWithName("reviewBoardId").description("게시판식별자 ID"))
                        )
                        , requestFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.STRING).description("회원식별ID"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("리뷰게시판 제목"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("리뷰게시판 내용"),
                                        fieldWithPath("localName").type(JsonFieldType.STRING).description("지역"),
                                        fieldWithPath("mountainName").type(JsonFieldType.STRING).description("산"),
                                        fieldWithPath("courseName").type(JsonFieldType.STRING).description("코스"),
                                        fieldWithPath("thumbnail").type(JsonFieldType.STRING).description("썸네일"),
                                        fieldWithPath("tagList").type(JsonFieldType.ARRAY).description("태그리스트")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("reviewBoardId").type(JsonFieldType.NUMBER).description("게시판 식별 ID"),
                                        fieldWithPath("memberId").type(JsonFieldType.STRING).description("회원 식별 ID"),
                                        fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("프로필이미지"),
                                        fieldWithPath("writer").type(JsonFieldType.STRING).description("작성자"),
                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("마지막수정날짜"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("게시글 본문"),
                                        fieldWithPath("thumbnail").type(JsonFieldType.STRING).description("썸네일이미지"),
                                        fieldWithPath("localName").type(JsonFieldType.STRING).description("지역"),
                                        fieldWithPath("mountainName").type(JsonFieldType.STRING).description("산"),
                                        fieldWithPath("courseName").type(JsonFieldType.STRING).description("코스"),
                                        fieldWithPath("views").type(JsonFieldType.NUMBER).description("조회수"),
                                        fieldWithPath("tagList").type(JsonFieldType.ARRAY).description("태그리스트"),
                                        fieldWithPath("commentList").type(JsonFieldType.ARRAY).description("댓글 리스트")

                                )
                        )
                )).andReturn();
    }

    @Test
    void deleteReviewBoard() throws Exception {
        // given
        long reviewId = 1L;
        doNothing().when(reviewBoardService).deleteReviewBoard(Mockito.anyLong());

        // when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .delete("/reviewboards/{reviewBoardId}", reviewId)
                        .with(csrf()));
        // then
        actions.andExpect(status().isNoContent())
                .andDo(
                        document(
                                "delete-reviewBoard",
                                pathParameters(
                                        parameterWithName("reviewBoardId").description("리뷰게시판 식별자 ID")
                                )
                        )
                ).andReturn();
    }

}