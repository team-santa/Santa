package com.developer.santa.api;


import com.developer.santa.api.controller.MountainController;

import com.developer.santa.api.domain.local.Local;
import com.developer.santa.api.domain.local.LocalMapper;
import com.developer.santa.api.domain.mountain.Mountain;
import com.developer.santa.api.domain.mountain.MountainDTO;
import com.developer.santa.api.service.LocalService;
import com.developer.santa.api.service.MountainService;
import com.developer.santa.member.annotation.WithMockCustomUser;
import com.developer.santa.member.entity.FavoriteMountain;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MountainController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WithMockCustomUser
public class MountainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private MountainService mountainService;

    @Test
    public void getDetailMountain() throws Exception {
        String mountain = "관악산";
        Local local = new Local(1L,"서울특별시 관악구");
        Mountain  mountainDetail = new Mountain (1L,mountain,local);
        given(mountainService.getDetailMountain(Mockito.anyString())).willReturn(mountainDetail);

        ResultActions actions = mockMvc.perform(
                get("/mountain/{mountain}",mountain)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        actions.andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("mountainName").value(mountain))
                .andExpect(jsonPath("local.localName").value(local.getLocalName()))
                .andDo(document(
                        "get-mountainDetail",
                        pathParameters(
                                parameterWithName("mountain").description("산 이름")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("mountain_id"),
                                        fieldWithPath("@id").type(JsonFieldType.NUMBER).description("@id"),
                                        fieldWithPath("mountainName").type(JsonFieldType.STRING).description("산 이름"),
                                        fieldWithPath("local").type(JsonFieldType.OBJECT).description("소속 지역"),
                                        fieldWithPath("local.id").type(JsonFieldType.NUMBER).description("소속 지역 id"),
                                        fieldWithPath("local.localName").type(JsonFieldType.STRING).description("소속 지역명")

                                )
                        )
                ));
    }

    @Test
    public void getSelectionMountain() throws Exception {
        String localName = "서울특별시 관악구";
        List<MountainDTO> mountainList = new ArrayList<>();
        MountainDTO mountainDTO1 = new MountainDTO("관악산");
        MountainDTO mountainDTO2 = new MountainDTO("삼성산");
        MountainDTO mountainDTO3 = new MountainDTO("청룡산");
        MountainDTO mountainDTO4 = new MountainDTO("청계산");
        mountainList.add(mountainDTO1);
        mountainList.add(mountainDTO2);
        mountainList.add(mountainDTO3);
        mountainList.add(mountainDTO4);

        given(mountainService.getLocalSelectMountain(Mockito.anyString())).willReturn(mountainList);
        ResultActions actions = mockMvc.perform(
                get("/mountain/selection")
                        .param("localName", localName)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        actions.andExpect(status().isOk())
                .andExpect(jsonPath(".[0].mountainName").value("관악산"))
                .andExpect(jsonPath(".[1].mountainName").value("삼성산"))
                .andExpect(jsonPath(".[2].mountainName").value("청룡산"))
                .andExpect(jsonPath(".[3].mountainName").value("청계산"))
                .andDo(document(
                        "get-mountainSelection",
                        requestParameters(
                                parameterWithName("localName").description("지역 이름")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("[]").type(JsonFieldType.ARRAY).description("산 목록"),
                                        fieldWithPath("[0].mountainName").type(JsonFieldType.STRING).description("산 이름"),
                                        fieldWithPath("[1].mountainName").type(JsonFieldType.STRING).description("산 이름"),
                                        fieldWithPath("[2].mountainName").type(JsonFieldType.STRING).description("산 이름"),
                                        fieldWithPath("[3].mountainName").type(JsonFieldType.STRING).description("산 이름")
                                )
                        )
                ));
    }


}
