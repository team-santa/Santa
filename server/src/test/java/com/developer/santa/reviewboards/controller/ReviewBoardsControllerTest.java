package com.developer.santa.reviewboards.controller;

import com.developer.santa.member.annotation.WithMockCustomUser;
import com.developer.santa.member.entity.Member;
import com.developer.santa.reviewboards.dto.ReviewBoardRequestDto;
import com.developer.santa.reviewboards.dto.ReviewBoardResponseDto;
import com.developer.santa.reviewboards.entity.ReviewBoard;
import com.developer.santa.reviewboards.mapper.ReviewBoardMapper;
import com.developer.santa.reviewboards.service.ReviewBoardService;
import com.google.gson.Gson;
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


    // ????????? ??????
    @Test
    void postReview() throws Exception {

        //given
        ReviewBoardRequestDto.Post post = new ReviewBoardRequestDto.Post(
                "2411017009",
                "???????????????",
                "????????????",
                "?????????",
                "?????????",
                "????????????A",
                "?????????.png",
                new ArrayList<>(List.of(new String[]{"??????", "??????"})));
        String content = gson.toJson(post);
        LocalDateTime dateTime = LocalDateTime.now();
        ReviewBoardResponseDto.Detail response = new ReviewBoardResponseDto.Detail(
                1L,
                "2411017009",
                "??????????????????.jpg",
                "?????????", dateTime,
                "???????????????",
                "????????????",
                "?????????.png",
                "?????????",
                "?????????",
                "????????????A",
                1L,
                new ArrayList<>(List.of(new String[]{"??????", "??????"})), //??????
                new ArrayList<>()); // ??????

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
                                        fieldWithPath("memberId").type(JsonFieldType.STRING).description("????????????ID"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("??????????????? ??????"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("??????????????? ??????"),
                                        fieldWithPath("localName").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("mountainName").type(JsonFieldType.STRING).description("???"),
                                        fieldWithPath("courseName").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("thumbnail").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("tagList").type(JsonFieldType.ARRAY).description("???????????????")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("reviewBoardId").type(JsonFieldType.NUMBER).description("????????? ?????? ID"),
                                        fieldWithPath("memberId").type(JsonFieldType.STRING).description("?????? ?????? ID"),
                                        fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("??????????????????"),
                                        fieldWithPath("writer").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("?????????????????????"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("thumbnail").type(JsonFieldType.STRING).description("??????????????????"),
                                        fieldWithPath("localName").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("mountainName").type(JsonFieldType.STRING).description("???"),
                                        fieldWithPath("courseName").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("views").type(JsonFieldType.NUMBER).description("?????????"),
                                        fieldWithPath("tagList").type(JsonFieldType.ARRAY).description("???????????????"),
                                        fieldWithPath("commentList").type(JsonFieldType.ARRAY).description("?????? ?????????")

                                )
                        )
                )).andReturn();


    }

    @Test
    void courseReview() throws Exception {
        //given
        String local ="";
        String mountain ="";
        String course ="";
        String sort ="views";
        String page = "1";
        LocalDateTime dateTime = LocalDateTime.now();

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("local", local);
        queryParams.add("mountain", mountain);
        queryParams.add("course", course);
        queryParams.add("sort", sort);
        queryParams.add("page", page);

        ReviewBoard reviewBoard1 = new ReviewBoard(1L, 1L, "???????????????1",  "??????????????????1.png", new ArrayList<>(List.of(new String[]{"??????", "??????"})));
        ReviewBoard reviewBoard2 = new ReviewBoard(2L, 2L,"???????????????2",  "??????????????????2.png", new ArrayList<>(List.of(new String[]{"??????", "??????"})) );
        Member member1 = new Member("memberId1",  "?????????", "??????????????????.png");
        Member member2 = new Member("memberId2", "?????????", "??????????????????.png");
        reviewBoard1.setMember(member1);
        reviewBoard2.setMember(member2);

        Page<ReviewBoard> reviewBoards = new PageImpl<>(List.of(reviewBoard1, reviewBoard2),
                PageRequest.of(0, 10, Sort.by("reviewBoardId").descending()), 2);

        List<ReviewBoardResponseDto.Page> responses = List.of(
                new ReviewBoardResponseDto.Page(1L,
                        "memberId1",
                        "??????????????????.png",
                        "?????????",
                        dateTime,
                        "???????????????1",
                        1L,
                        new ArrayList<>(List.of(new String[]{"??????", "??????"})),
                        "??????????????????1.png"),
                new ReviewBoardResponseDto.Page(2L,
                        "memberId2",
                        "??????????????????.png",
                        "?????????",
                        dateTime,
                        "???????????????2",
                        2L,
                        new ArrayList<>(List.of(new String[]{"??????", "??????"})),
                        "??????????????????2.png")
        );

        given(reviewBoardService.findReviewBoards(Mockito.anyInt(),Mockito.anyMap(),Mockito.anyString())).willReturn(reviewBoards);
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
                                                parameterWithName("local").description("??????"),
                                                parameterWithName("mountain").description("???"),
                                                parameterWithName("course").description("??????"),
                                                parameterWithName("sort").description("????????????"),
                                                parameterWithName("page").description("Page ??????")
                                        )
                                ),
                                responseFields(
                                        Arrays.asList(
                                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("?????? ?????????")
                                                        .optional(),
                                                fieldWithPath("data[].reviewBoardId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                fieldWithPath("data[].memberId").type(JsonFieldType.STRING).description("?????? ?????? ID"),
                                                fieldWithPath("data[].profileImageUrl").type(JsonFieldType.STRING).description("??????????????????"),
                                                fieldWithPath("data[].writer").type(JsonFieldType.STRING).description("?????????"),
                                                fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("?????????????????????"),
                                                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("????????? ??????"),
                                                fieldWithPath("data[].views").type(JsonFieldType.NUMBER).description("?????????"),
                                                fieldWithPath("data[].tagList").type(JsonFieldType.ARRAY).description("???????????????"),
                                                fieldWithPath("data[].thumbnail").type(JsonFieldType.STRING).description("??????????????????"),
                                                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("????????? ??????")
                                                        .optional(),
                                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("????????? ??????")
                                                        .optional(),
                                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("????????? ?????????")
                                                        .optional(),
                                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("?????? ??? ???")
                                                        .optional(),
                                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("?????? ????????? ???")
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
                "??????????????????.jpg",
                "?????????", dateTime,
                "???????????????",
                "????????????",
                "?????????.png",
                "?????????",
                "?????????",
                "????????????A",
                1L,
                new ArrayList<>(List.of(new String[]{"??????", "??????"})), //??????
                new ArrayList<>()); // ??????

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
                                Arrays.asList(parameterWithName("reviewBoardId").description("?????????????????? ID"))
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("reviewBoardId").type(JsonFieldType.NUMBER).description("????????? ?????? ID"),
                                        fieldWithPath("memberId").type(JsonFieldType.STRING).description("?????? ?????? ID"),
                                        fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("??????????????????"),
                                        fieldWithPath("writer").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("?????????????????????"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("thumbnail").type(JsonFieldType.STRING).description("??????????????????"),
                                        fieldWithPath("localName").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("mountainName").type(JsonFieldType.STRING).description("???"),
                                        fieldWithPath("courseName").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("views").type(JsonFieldType.NUMBER).description("?????????"),
                                        fieldWithPath("tagList").type(JsonFieldType.ARRAY).description("???????????????"),
                                        fieldWithPath("commentList").type(JsonFieldType.ARRAY).description("?????? ?????????")

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
                "???????????????",
                "????????????",
                "?????????",
                "?????????",
                "????????????A",
                "?????????.png",
                new ArrayList<>(List.of(new String[]{"??????", "??????"})) //??????
        );

        String content = gson.toJson(patch);

        ReviewBoardResponseDto.Detail response = new ReviewBoardResponseDto.Detail(
                1L,
                "2411017009",
                "??????????????????.jpg",
                "?????????", dateTime,
                "???????????????",
                "????????????",
                "?????????.png",
                "?????????",
                "?????????",
                "????????????A",
                1L,
                new ArrayList<>(List.of(new String[]{"??????", "??????"})), //??????
                new ArrayList<>()); // ??????

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
                                Arrays.asList(parameterWithName("reviewBoardId").description("?????????????????? ID"))
                        )
                        , requestFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.STRING).description("????????????ID"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("??????????????? ??????"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("??????????????? ??????"),
                                        fieldWithPath("localName").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("mountainName").type(JsonFieldType.STRING).description("???"),
                                        fieldWithPath("courseName").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("thumbnail").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("tagList").type(JsonFieldType.ARRAY).description("???????????????")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("reviewBoardId").type(JsonFieldType.NUMBER).description("????????? ?????? ID"),
                                        fieldWithPath("memberId").type(JsonFieldType.STRING).description("?????? ?????? ID"),
                                        fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("??????????????????"),
                                        fieldWithPath("writer").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("?????????????????????"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("thumbnail").type(JsonFieldType.STRING).description("??????????????????"),
                                        fieldWithPath("localName").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("mountainName").type(JsonFieldType.STRING).description("???"),
                                        fieldWithPath("courseName").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("views").type(JsonFieldType.NUMBER).description("?????????"),
                                        fieldWithPath("tagList").type(JsonFieldType.ARRAY).description("???????????????"),
                                        fieldWithPath("commentList").type(JsonFieldType.ARRAY).description("?????? ?????????")

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
                                        Arrays.asList(parameterWithName("reviewBoardId").description("??????????????? ????????? ID"))
                                )
                        )
                );
    }

}