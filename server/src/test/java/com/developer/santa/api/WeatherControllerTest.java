package com.developer.santa.api;


import com.developer.santa.api.controller.WeatherController;
import com.developer.santa.api.domain.weather.Weather;
import com.developer.santa.api.service.WeatherService;
import com.developer.santa.member.annotation.WithMockCustomUser;
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

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WeatherController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WithMockCustomUser
public class WeatherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private WeatherService weatherService;

    @Test
    public void getWeather() throws Exception {
        String weatherLocal = "서울특별시";
        String weatherCity = "서울";
        String weatherCode = "11B10101";
        Weather weather =new Weather(1L,weatherLocal,weatherCity,weatherCode);
        given(weatherService.getWeather(Mockito.anyString())).willReturn(weather);
        ResultActions actions = mockMvc.perform(
                get("/weather/{weatherLocal}",weatherLocal)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("weatherLocal").value(weatherLocal))
                .andExpect(jsonPath("weatherCity").value(weatherCity))
                .andExpect(jsonPath("weatherCode").value(weatherCode))
                .andDo(document(
                        "get-weather",
                        pathParameters(
                                parameterWithName("weatherLocal").description("지역명")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("mountain_id"),
                                        fieldWithPath("weatherLocal").type(JsonFieldType.STRING).description("지역명"),
                                        fieldWithPath("weatherCity").type(JsonFieldType.STRING).description("지역 도시명"),
                                        fieldWithPath("weatherCode").type(JsonFieldType.STRING).description("지역 코드")
                                )
                        )
                ));

    }
}
