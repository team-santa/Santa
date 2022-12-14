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
        String mountain = "?????????";
        Local local = new Local(1L,"??????????????? ?????????");
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
                                parameterWithName("mountain").description("??? ??????")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("mountain_id"),
                                        fieldWithPath("@id").type(JsonFieldType.NUMBER).description("@id"),
                                        fieldWithPath("mountainName").type(JsonFieldType.STRING).description("??? ??????"),
                                        fieldWithPath("local").type(JsonFieldType.OBJECT).description("?????? ??????"),
                                        fieldWithPath("local.id").type(JsonFieldType.NUMBER).description("?????? ?????? id"),
                                        fieldWithPath("local.localName").type(JsonFieldType.STRING).description("?????? ?????????")

                                )
                        )
                ));
    }

    @Test
    public void getSelectionMountain() throws Exception {
        String localName = "??????????????? ?????????";
        List<MountainDTO> mountainList = new ArrayList<>();
        MountainDTO mountainDTO1 = new MountainDTO("?????????");
        MountainDTO mountainDTO2 = new MountainDTO("?????????");
        MountainDTO mountainDTO3 = new MountainDTO("?????????");
        MountainDTO mountainDTO4 = new MountainDTO("?????????");
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
                .andExpect(jsonPath(".[0].mountainName").value("?????????"))
                .andExpect(jsonPath(".[1].mountainName").value("?????????"))
                .andExpect(jsonPath(".[2].mountainName").value("?????????"))
                .andExpect(jsonPath(".[3].mountainName").value("?????????"))
                .andDo(document(
                        "get-mountainSelection",
                        requestParameters(
                                parameterWithName("localName").description("?????? ??????")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("[]").type(JsonFieldType.ARRAY).description("??? ??????"),
                                        fieldWithPath("[0].mountainName").type(JsonFieldType.STRING).description("??? ??????"),
                                        fieldWithPath("[1].mountainName").type(JsonFieldType.STRING).description("??? ??????"),
                                        fieldWithPath("[2].mountainName").type(JsonFieldType.STRING).description("??? ??????"),
                                        fieldWithPath("[3].mountainName").type(JsonFieldType.STRING).description("??? ??????")
                                )
                        )
                ));
    }


}
