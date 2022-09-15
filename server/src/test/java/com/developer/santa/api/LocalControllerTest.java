package com.developer.santa.api;


import com.developer.santa.api.controller.LocalController;
import com.developer.santa.api.domain.local.Local;
import com.developer.santa.api.domain.local.LocalDTO;
import com.developer.santa.api.domain.local.LocalMapper;
import com.developer.santa.api.service.LocalService;
import com.developer.santa.member.annotation.WithMockCustomUser;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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


import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(LocalController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WithMockCustomUser
public class LocalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private LocalService localService;

    @Test
    public void getAllLocal() throws Exception {
        LocalDTO local1 = new LocalDTO("서울특별시 관악구");
        LocalDTO local2 = new LocalDTO("경기도 시흥시");
        LocalDTO local3 = new LocalDTO("강원도 춘천시");
        LocalDTO local4 = new LocalDTO("경상남도 진주시");
        LocalDTO local5 = new LocalDTO("대전광역시 유성구");
        LocalDTO local6 = new LocalDTO("전라북도 전주시");
        List<LocalDTO> localList = new ArrayList<>();
        localList.add(local1);
        localList.add(local2);
        localList.add(local3);
        localList.add(local4);
        localList.add(local5);
        localList.add(local6);

        given(localService.getLocal()).willReturn(localList);
        ResultActions actions = mockMvc.perform(
                get("/local")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        actions.andExpect(status().isOk())
                .andExpect(jsonPath(".[0].localName").value(local1.getLocalName()))
                .andExpect(jsonPath(".[1].localName").value(local2.getLocalName()))
                .andExpect(jsonPath(".[2].localName").value(local3.getLocalName()))
                .andExpect(jsonPath(".[3].localName").value(local4.getLocalName()))
                .andExpect(jsonPath(".[4].localName").value(local5.getLocalName()))
                .andExpect(jsonPath(".[5].localName").value(local6.getLocalName()))
                .andDo(document(
                        "get-localList",
                        responseFields(
                                List.of(

                                        fieldWithPath("[].localName").type(JsonFieldType.STRING).description("지역명")
                                )
                        )
                ));
    }
}
