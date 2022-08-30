package com.developer.santa.member.controller;

import com.developer.santa.member.dto.MemberDto;
import com.developer.santa.member.entity.Member;
import com.developer.santa.member.mapper.MemberMapper;
import com.developer.santa.member.oauth.entity.ProviderType;
import com.developer.santa.member.oauth.entity.RoleType;
import com.developer.santa.member.service.MemberService;
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

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({MemberController.class})
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WithMockUser
class MemberControllerTest {

    @Autowired
    private MockMvc mock;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberMapper mapper;

    @Test
    void register() throws Exception {

        String providerId = "google";

        ResultActions actions = mock.perform(get("/oauth2/authorization/{providerId}", providerId)
                .param("redirect_uri", "http://localhost:3000/oauth/redirect")
        );

        actions.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("https://accounts.google.com/o/oauth2/v2/**"))
                .andDo(document("oauth2-login",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("providerId").description("로그인 제공자")
                        )
                ));

    }

    @Test
    void getMember() throws Exception {

        String memberId = "1";
        Member member = new Member(memberId, "testEmail@email.com", "profileImageUrl", ProviderType.KAKAO, RoleType.USER);
        MemberDto.Response response = new MemberDto.Response("testUser", "testEmail@email.com", "profileImageUrl");

        given(memberService.findMember(Mockito.anyString())).willReturn(new Member());
        given(memberService.verifyMember(Mockito.anyString())).willReturn(new Member());

        given(mapper.memberToMemberDtoResponse(Mockito.any(Member.class))).willReturn(response);

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
                                parameterWithName("memberId").description("제공자 고유번호")
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
}