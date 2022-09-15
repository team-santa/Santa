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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
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
    public void getMountain() throws Exception {
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
                .andExpect(jsonPath("local.localName").value(local.getLocalName()));
    }


}
