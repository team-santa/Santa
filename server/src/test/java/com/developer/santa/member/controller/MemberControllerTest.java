package com.developer.santa.member.controller;


import com.developer.santa.member.annotation.WithMockCustomUser;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

@WebMvcTest({MemberController.class})
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WithMockCustomUser
class MemberControllerTest {

//    @Autowired
//    private MockMvc mock;
//
//    @MockBean
//    private MemberService memberService;
//
//    @MockBean
//    private MemberMapper memberMapper;
//
//    @MockBean
//    private ReviewBoardMapper reviewBoardMapper;
//
//    @Autowired
//    private Gson gson;
//
//    @Test
//    void login() throws Exception {
//
//        String providerId = "google";
//        String verifyUri = "https://accounts.google.com/o/oauth2/v2/auth*/**" + providerId;
//
//        ResultActions actions = mock.perform(get("/oauth2/authorization/{providerId}", providerId)
//                .param("redirect_uri", "http://localhost:3000/oauth/redirect")
//        );
//
//        actions.andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrlPattern(verifyUri))
//                .andDo(document("oauth2-login",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("providerId").description("로그인 제공자")
//                        )
//                ));
//
//    }
//
//    @Test
//    void getMember() throws Exception {
//
//        String memberId = "1";
//        Member member = new Member(memberId, "testEmail@email.com", "profileImageUrl", ProviderType.KAKAO, RoleType.USER);
//        member.setUsername("testUser");
//        MemberDto.Response response = new MemberDto.Response("testUser", "testEmail@email.com", "profileImageUrl");
//
//        given(memberService.findMember(Mockito.anyString())).willReturn(new Member());
//        given(memberService.verifyMember(Mockito.anyString())).willReturn(new Member());
//
//        given(memberMapper.memberToMemberDtoResponse(Mockito.any(Member.class))).willReturn(response);
//
//        ResultActions actions = mock.perform(
//                get("/members/{memberId}", memberId)
//                        .accept(MediaType.APPLICATION_JSON)
//
//        );
//
//        actions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username").value(member.getUsername()))
//                .andExpect(jsonPath("$.email").value(member.getEmail()))
//                .andExpect(jsonPath("$.profileImageUrl").value(member.getProfileImageUrl()))
//                .andDo(document("get-profile",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("memberId").description("회원 고유ID")
//                        ),
//                        responseFields(
//                                List.of(
//                                        fieldWithPath("username").type(JsonFieldType.STRING).description("회원 이름"),
//                                        fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
//                                        fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("프로필 이미지 경로")
//                                )
//                        )
//                ));
//    }
//
//    @Test
//    public void putMember() throws Exception {
//        String memberId = "1";
//
//        MemberDto.Put put = new MemberDto.Put("testUser", "testEmail@email.com", "profileImageUrl");
//        String body = gson.toJson(put);
//        MemberDto.Response response = new MemberDto.Response("testUser", "testEmail@email.com", "profileImageUrl");
//
//        given(memberMapper.memberPutDtoToMember(Mockito.any(MemberDto.Put.class))).willReturn(new Member());
//
//        given(memberService.putMember(Mockito.anyString(),
//                Mockito.any(Member.class),
//                Mockito.any(PrincipalDetails.class),
//                Mockito.any(HttpServletResponse.class))).willReturn(new Member());
//
//        given(memberMapper.memberToMemberDtoResponse(Mockito.any(Member.class))).willReturn(response);
//
//        ResultActions actions = mock.perform(
//                put("/members/{memberId}", memberId)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .with(csrf())
//                        .content(body)
//        );
//
//        actions
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.username").value(put.getUsername()))
//                .andExpect(jsonPath("$.email").value(put.getEmail()))
//                .andExpect(jsonPath("$.profileImageUrl").value(put.getProfileImageUrl()))
//                .andDo(document("put-profile",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("memberId").description("회원 고유ID")
//                        ),
//                        requestFields(
//                                List.of(
//                                        fieldWithPath("username").type(JsonFieldType.STRING).description("회원 이름"),
//                                        fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
//                                        fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("프로필 이미지 경로")
//                                )
//                        ),
//                        responseFields(
//                                List.of(
//                                        fieldWithPath("username").type(JsonFieldType.STRING).description("회원 이름"),
//                                        fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
//                                        fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("프로필 이미지 경로")
//                                )
//                        )
//                ));
//    }
//
//    @Test
//    public void checkDuplicateUsername() throws Exception {
//        String username = "testUser";
//
//        given(memberService.checkDuplicateUsername(Mockito.anyString())).willReturn(false);
//
//        ResultActions actions = mock.perform(
//                get("/members/{username}/check", username)
//                        .accept(MediaType.APPLICATION_JSON)
//        );
//
//        actions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data").value(true))
//                .andDo(document("check-username",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("username").description("회원 닉네임")
//                        ),
//                        responseFields(
//                                fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("변경가능 여부")
//                        )
//                ));
//    }
//
//    @PostMapping("/{memberId}/mountains/{mountainName}")
//    @Test
//    public void likeMountain() throws Exception {
//
//        String memberId = "1";
//        String mountainName = "도봉산";
//
//        MemberDto.Mountain mountains = new MemberDto.Mountain(List.of(mountainName));
//
//        given(memberService.postMemberFavoriteMountain(Mockito.anyString(), Mockito.anyString())).willReturn(new Member());
//
//        given(memberMapper.memberToMemberDtoMountain(Mockito.any(Member.class))).willReturn(mountains);
//
//        ResultActions actions = mock.perform(
//                post("/members/{memberId}/mountains/{mountainName}", memberId, mountainName)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .with(csrf())
//        );
//
//        actions
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.mountainNames[0]").value(mountainName))
//                .andDo(document("member-post-mountain",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("memberId").description("회원 고유ID"),
//                                parameterWithName("mountainName").description("산 이름")
//                        ),
//                        responseFields(
//                                List.of(
//                                        fieldWithPath("mountainNames").type(JsonFieldType.ARRAY).description("산 목록")
//                                )
//                        )
//                ));
//    }
//
//    @Test
//    public void deleteMountain() throws Exception {
//        String memberId = "1";
//        String mountainName = "도봉산";
//
//        ResultActions actions = mock.perform(
//                delete("/members/{memberId}/mountains/{mountainName}", memberId, mountainName)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .with(csrf())
//        );
//
//        actions
//                .andExpect(status().isNoContent())
//                .andDo(document("member-delete-mountain",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("memberId").description("회원 고유ID"),
//                                parameterWithName("mountainName").description("산 이름")
//                        )
//                ));
//
//    }
//
//    @Test
//    public void getMountains() throws Exception {
//        String memberId = "1";
//        String mountainName = "도봉산";
//
//        MemberDto.Mountain mountains = new MemberDto.Mountain(List.of(mountainName));
//
//        given(memberService.findMember(Mockito.anyString())).willReturn(new Member());
//
//        given(memberMapper.memberToMemberDtoMountain(Mockito.any(Member.class))).willReturn(mountains);
//
//        ResultActions actions = mock.perform(
//                get("/members/{memberId}/mountains", memberId, mountainName)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .with(csrf())
//        );
//
//        actions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.mountainNames[0]").value(mountainName))
//                .andDo(document("member-post-mountain",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("memberId").description("회원 고유ID")
//                        ),
//                        responseFields(
//                                List.of(
//                                        fieldWithPath("mountainNames").type(JsonFieldType.ARRAY).description("산 목록")
//                                )
//                        )
//                ));
//    }
//
//    @Test
//    public void getReviewBoards() throws Exception {
//        String memberId = "1";
//
//        ReviewBoardResponseDto.Page page = new ReviewBoardResponseDto.Page(1L, "testUser", "testTitle", "photo");
//
//        given(memberService.findMember(Mockito.anyString())).willReturn(new Member());
//
//        given(reviewBoardMapper.reviewBoardListToPages(Mockito.anyList())).willReturn(List.of(page));
//
//        ResultActions actions = mock.perform(
//                get("/members/{memberId}/reviewboards", memberId)
//                        .accept(MediaType.APPLICATION_JSON)
//        );
//
//        actions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.[0].reviewBoardId").value(page.getReviewBoardId()))
//                .andExpect(jsonPath("$.[0].nickName").value(page.getNickName()))
//                .andExpect(jsonPath("$.[0].title").value(page.getTitle()))
//                .andExpect(jsonPath("$.[0].photo").value(page.getPhoto()))
//                .andDo(document("member-get-reviewBoards",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("memberId").description("회원 고유ID")
//                        ),
//                        responseFields(
//                                List.of(
//                                        fieldWithPath("[]").type(JsonFieldType.ARRAY).description("작성한 리뷰게시판 목록"),
//                                        fieldWithPath("[].reviewBoardId").type(JsonFieldType.NUMBER).description("리뷰게시판 고유ID"),
//                                        fieldWithPath("[].nickName").type(JsonFieldType.STRING).description("회원 닉네임"),
//                                        fieldWithPath("[].title").type(JsonFieldType.STRING).description("리뷰게시판 제목"),
//                                        fieldWithPath("[].photo").type(JsonFieldType.STRING).description("리뷰게시판 이미지 경로")
//                                )
//                        )
//                ));
//    }
}