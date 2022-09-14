package com.developer.santa.reviewboards.controller;

import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

@WebMvcTest(ReviewBoardsController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class ReviewBoardsControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private Gson gson;
//
//    @MockBean
//    private ReviewBoardService reviewBoardService;
//
//    @MockBean
//    private ReviewBoardMapper mapper;
//
//
//
//    // 토큰값 필요
//    @Test
//    void postReview() throws Exception {
//        //given
//        ReviewBoardRequestDto.Post post = new ReviewBoardRequestDto.Post(
//                "2411017009",
//                "테스트제목",
//                "바디내용",
//                "서울시",
//                "관악산",
//                "등산코스A",
//                "썸네일.png",
//                new ArrayList<>(List.of(new String[]{"정상", "등산"})));
//        String content = gson.toJson(post);
//        LocalDateTime dateTime = LocalDateTime.now();
//        ReviewBoardResponseDto.Detail response = new ReviewBoardResponseDto.Detail(
//                1L,
//                "2411017009",
//                "프로필이미지.jpg",
//                "글쓴이", dateTime,
//                "테스트제목",
//                "바디내용",
//                "썸네일.png",
//                "서울시",
//                "관악산",
//                "등산코스A",
//                1L,
//                new ArrayList<>(List.of(new String[]{"정상", "등산"})), //태그
//                new ArrayList<>()); // 댓글
//
//        given(mapper.reviewBoardPostToReviewBoard(Mockito.any(ReviewBoardRequestDto.Post.class))).willReturn(new ReviewBoard());
//
//        given(reviewBoardService.createMyBoard(Mockito.any(ReviewBoard.class))).willReturn(new ReviewBoard());
//
//        given(mapper.reviewBoardToDetail(Mockito.any(ReviewBoard.class))).willReturn(response);
//
//        // when
//        ResultActions actions =
//                mockMvc.perform(
//                        post("/v1/reviewboards")
//                                .accept(MediaType.APPLICATION_JSON)
//                                .header(HttpHeaders.AUTHORIZATION,"Bearer ")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(content));
//        // then
//        actions
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.memberId").value("2411017009"))
//                .andDo(document( "post-reviewBoard",
//                        requestFields(
//                                List.of(
//                                        fieldWithPath("memberId").type(JsonFieldType.STRING).description("회원식별ID"),
//                                        fieldWithPath("title").type(JsonFieldType.STRING).description("리뷰게시판 제목"),
//                                        fieldWithPath("body").type(JsonFieldType.STRING).description("리뷰게시판 내용"),
//                                        fieldWithPath("localName").type(JsonFieldType.STRING).description("지역"),
//                                        fieldWithPath("mountainName").type(JsonFieldType.STRING).description("산"),
//                                        fieldWithPath("courseName").type(JsonFieldType.STRING).description("코스"),
//                                        fieldWithPath("thumbnail").type(JsonFieldType.STRING).description("썸네일"),
//                                        fieldWithPath("tagList").type(JsonFieldType.ARRAY).description("태그리스트")
//                                )
//                        ),
//                        responseFields(
//
//                        )
//                )).andReturn();
//
//
//    }
//
//    @Test
//    void courseReview() throws Exception {
//
//    }
//
//    @Test
//    void findReviewBoard() {
//
//    }
//
//    @Test
//    void editReviewBoard() {
//    }
//
//    @Test
//    void deleteReviewBoard() throws Exception {
//        // given
//        long reviewId = 1L;
//        doNothing().when(reviewBoardService).deleteReviewBoard(Mockito.anyLong());
//
//        // when
//        ResultActions actions = mockMvc.perform(
//                RestDocumentationRequestBuilders
//                        .delete("/v1/reviewboards/{reviewBoardId}", reviewId));
//        // then
//        actions.andExpect(status().isNoContent())
//                .andDo(
//                        document(
//                                "delete-member",
////                                preprocessRequest(prettyPrint()),
////                                preprocessResponse(prettyPrint()),
//                                pathParameters(
//                                        Arrays.asList(parameterWithName("reviewBoardId").description("리뷰게시판 식별자 ID"))
//                                )
//                        )
//                );
//    }

}