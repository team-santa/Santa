package com.developer.santa.member.controller;


import com.developer.santa.member.annotation.WithMockCustomUser;
import com.developer.santa.member.dto.MemberDto;
import com.developer.santa.member.entity.Member;
import com.developer.santa.member.mapper.MemberMapper;
import com.developer.santa.member.oauth.entity.PrincipalDetails;
import com.developer.santa.member.oauth.entity.ProviderType;
import com.developer.santa.member.oauth.entity.RoleType;
import com.developer.santa.member.service.MemberService;
import com.developer.santa.reviewboards.dto.ReviewBoardResponseDto;
import com.developer.santa.reviewboards.mapper.ReviewBoardMapper;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({MemberController.class})
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WithMockCustomUser
class MemberControllerTest {

    @Autowired
    private MockMvc mock;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberMapper memberMapper;

    @MockBean
    private ReviewBoardMapper reviewBoardMapper;

    @Autowired
    private Gson gson;

    @Test
    void login() throws Exception {

        String providerId = "google";
        String verifyUri = "https://accounts.google.com/o/oauth2/v2/auth*/**";

        ResultActions actions = mock.perform(get("/oauth2/authorization/{providerId}", providerId)
                .param("redirect_uri", "http://localhost:3000/oauth/redirect")
        );

        actions.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(verifyUri))
                .andDo(document("oauth2-login",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("providerId").description("????????? ?????????")
                        )
                ));

    }

    @Test
    void getMember() throws Exception {

        String memberId = "1";
        Member member = new Member(memberId, "testEmail@email.com", "profileImageUrl", ProviderType.KAKAO, RoleType.USER);
        member.setUsername("testUser");
        MemberDto.Response response = new MemberDto.Response("testUser", "testEmail@email.com", "profileImageUrl");

        given(memberService.findMember(Mockito.anyString())).willReturn(new Member());
        given(memberService.verifyMember(Mockito.anyString())).willReturn(new Member());

        given(memberMapper.memberToMemberDtoResponse(Mockito.any(Member.class))).willReturn(response);

        ResultActions actions = mock.perform(
                get("/members/{memberId}", memberId)
                        .accept(MediaType.APPLICATION_JSON)

        );

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(member.getUsername()))
                .andExpect(jsonPath("$.email").value(member.getEmail()))
                .andExpect(jsonPath("$.profileImageUrl").value(member.getProfileImageUrl()))
                .andDo(document("get-profile",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("memberId").description("?????? ??????ID")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("username").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("?????? ?????????"),
                                        fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("????????? ????????? ??????")
                                )
                        )
                ));
    }

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
                                parameterWithName("memberId").description("?????? ??????ID")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("username").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("?????? ?????????"),
                                        fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("????????? ????????? ??????")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("username").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("?????? ?????????"),
                                        fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("????????? ????????? ??????")
                                )
                        )
                ));
    }

    @Test
    public void checkDuplicateUsername() throws Exception {
        String username = "testUser";

        given(memberService.checkDuplicateUsername(Mockito.anyString())).willReturn(false);

        ResultActions actions = mock.perform(
                get("/members/{username}/check", username)
                        .accept(MediaType.APPLICATION_JSON)
        );

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(true))
                .andDo(document("check-username",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("username").description("?????? ?????????")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("???????????? ??????")
                        )
                ));
    }

    @PostMapping("/{memberId}/mountains/{mountainName}")
    @Test
    public void likeMountain() throws Exception {

        String memberId = "1";
        String mountainName = "?????????";

        MemberDto.Mountain mountains = new MemberDto.Mountain(List.of(mountainName));

        given(memberService.postMemberFavoriteMountain(Mockito.anyString(), Mockito.anyString())).willReturn(new Member());

        given(memberMapper.memberToMemberDtoMountain(Mockito.any(Member.class))).willReturn(mountains);

        ResultActions actions = mock.perform(
                post("/members/{memberId}/mountains/{mountainName}", memberId, mountainName)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        );

        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mountainNames[0]").value(mountainName))
                .andDo(document("member-post-mountain",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("memberId").description("?????? ??????ID"),
                                parameterWithName("mountainName").description("??? ??????")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("mountainNames").type(JsonFieldType.ARRAY).description("??? ??????")
                                )
                        )
                ));
    }

    @Test
    public void deleteMountain() throws Exception {
        String memberId = "1";
        String mountainName = "?????????";

        ResultActions actions = mock.perform(
                delete("/members/{memberId}/mountains/{mountainName}", memberId, mountainName)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        );

        actions
                .andExpect(status().isNoContent())
                .andDo(document("member-delete-mountain",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("memberId").description("?????? ??????ID"),
                                parameterWithName("mountainName").description("??? ??????")
                        )
                ));

    }

    @Test
    public void getMountains() throws Exception {
        String memberId = "1";
        String mountainName = "?????????";

        MemberDto.Mountain mountains = new MemberDto.Mountain(List.of(mountainName));

        given(memberService.findMember(Mockito.anyString())).willReturn(new Member());

        given(memberMapper.memberToMemberDtoMountain(Mockito.any(Member.class))).willReturn(mountains);

        ResultActions actions = mock.perform(
                get("/members/{memberId}/mountains", memberId, mountainName)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        );

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mountainNames[0]").value(mountainName))
                .andDo(document("member-post-mountain",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("memberId").description("?????? ??????ID")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("mountainNames").type(JsonFieldType.ARRAY).description("??? ??????")
                                )
                        )
                ));
    }

    @Test
    public void getReviewBoards() throws Exception {
        String memberId = "1";

        ReviewBoardResponseDto.Page page = new ReviewBoardResponseDto.Page(1L, "1234567", "profileImageUrl", "testUser", LocalDateTime.now(), "testTitle", 1L, List.of("testTag1", "testTag2"), "thumbnail");

        given(memberService.findMember(Mockito.anyString())).willReturn(new Member());

        given(reviewBoardMapper.reviewBoardListToPages(Mockito.anyList())).willReturn(List.of(page));

        ResultActions actions = mock.perform(
                get("/members/{memberId}/reviewboards", memberId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].reviewBoardId").value(page.getReviewBoardId()))
                .andExpect(jsonPath("$.[0].memberId").value(page.getMemberId()))
                .andExpect(jsonPath("$.[0].profileImageUrl").value(page.getProfileImageUrl()))
                .andExpect(jsonPath("$.[0].writer").value(page.getWriter()))
                .andExpect(jsonPath("$.[0].title").value(page.getTitle()))
                .andExpect(jsonPath("$.[0].views").value(page.getViews()))
                .andExpect(jsonPath("$.[0].tagList[0]").value(page.getTagList().get(0)))
                .andExpect(jsonPath("$.[0].thumbnail").value(page.getThumbnail()))
                .andDo(document("member-get-reviewBoards",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("memberId").description("?????? ??????ID")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("[]").type(JsonFieldType.ARRAY).description("????????? ??????????????? ??????"),
                                        fieldWithPath("[].reviewBoardId").type(JsonFieldType.NUMBER).description("??????????????? ??????ID"),
                                        fieldWithPath("[].memberId").type(JsonFieldType.STRING).description("?????? ??????ID"),
                                        fieldWithPath("[].profileImageUrl").type(JsonFieldType.STRING).description("????????? ???????????????"),
                                        fieldWithPath("[].writer").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("[].modifiedAt").type(JsonFieldType.STRING).description("??????????????? ????????????"),
                                        fieldWithPath("[].title").type(JsonFieldType.STRING).description("??????????????? ??????"),
                                        fieldWithPath("[].views").type(JsonFieldType.NUMBER).description("??????????????? ?????????"),
                                        fieldWithPath("[].tagList").type(JsonFieldType.ARRAY).description("??????????????? ??????"),
                                        fieldWithPath("[].thumbnail").type(JsonFieldType.STRING).description("??????????????? ????????? ??????")

                                )
                        )
                ));
    }
}