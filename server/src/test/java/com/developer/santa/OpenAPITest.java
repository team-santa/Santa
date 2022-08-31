package com.developer.santa;


import com.developer.santa.api.domain.local.Local;
import com.developer.santa.api.domain.local.LocalDTO;
import com.developer.santa.api.service.ApiService;
import com.developer.santa.api.service.LocalService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class OpenAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocalService localService;



    @Test
    @DisplayName("getAllLocal")
    public void getLocal() throws Exception {
//        List<LocalDTO> localDTOList = new ArrayList<>();
//        int totalLocal = 5;
//        IntStream.range(0,totalLocal)
//                .forEach(e -> {
//                    LocalDTO localDTO = new LocalDTO("지역"+e);
//                    localDTOList.add(localDTO);
//                });
//
//        given(localService.getLocal()).willReturn(localDTOList);
//
//        ResultActions actions = mockMvc.perform(
//                get("/local")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//        );
//        actions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.localName").value(localDTOList.get(0)));
    }

    @Test
    @DisplayName("getAllMountain")
    public void getMountain() throws Exception {

    }

    @Test
    @DisplayName("getAllCourse")
    public void getLoad() throws Exception {

    }
}
