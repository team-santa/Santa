package com.developer.santa.tag.controller;

import com.developer.santa.member.annotation.WithMockCustomUser;
import com.developer.santa.reviewboards.controller.ReviewBoardsController;
import com.developer.santa.reviewboards.mapper.ReviewBoardMapper;
import com.developer.santa.reviewboards.service.ReviewBoardService;
import com.developer.santa.tag.dto.TagResponseDto;
import com.developer.santa.tag.entity.Tag;
import com.developer.santa.tag.mapper.TagMapper;
import com.developer.santa.tag.service.TagService;
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
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TagController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WithMockCustomUser
class TagControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private TagService tagService;

    @MockBean
    private TagMapper mapper;
    @Test
    void tagSearch() throws Exception {

        //given
        String requestText = "산";
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("text", requestText);

        Tag tag1 = new Tag("100대명산");
        Tag tag2 = new Tag("200대명산");
        Tag tag3 = new Tag("400대명산");
        Tag tag4 = new Tag("등산");
        Tag tag5 = new Tag("산");


        Page<Tag> tagPage = new PageImpl<>(List.of(tag1,tag2,tag3,tag4,tag5),
                PageRequest.of(0,10),5);

        List<TagResponseDto.Recommend> response  = List.of(
                new TagResponseDto.Recommend("100대명산"),
                new TagResponseDto.Recommend("200대명산"),
                new TagResponseDto.Recommend("400대명산"),
                new TagResponseDto.Recommend("등산"),
                new TagResponseDto.Recommend("산")
        );
        //when
        given(tagService.findTags(Mockito.anyInt(), Mockito.anyMap())).willReturn(tagPage);
        given(mapper.tagListToRecommendList(Mockito.anyList())).willReturn(response);
        //then
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/tag")
                                .params(queryParams)
                                .accept(MediaType.APPLICATION_JSON)
                );
        actions .andExpect(status().isOk())
                .andDo(
                        document(
                                "get-tags",
                                requestParameters(
                                        List.of(
                                                parameterWithName("text").description("태그검색")
                                        )
                                ),responseFields(
                                        Arrays.asList(
                                                fieldWithPath("[].tagName").type(JsonFieldType.STRING).description("태그이름")
                                        )
                                )
                        )
                ).andReturn();

    }
}