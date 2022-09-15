package com.developer.santa.api;


import com.developer.santa.api.controller.CourseController;

import com.developer.santa.api.domain.course.Course;
import com.developer.santa.api.domain.course.CourseDTO;
import com.developer.santa.api.domain.local.Local;
import com.developer.santa.api.domain.mountain.Mountain;
import com.developer.santa.api.service.CourseService;
import com.developer.santa.api.service.MountainService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WithMockCustomUser
public class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private CourseService courseService;

    @Test
    public void getDetailCourse() throws Exception{
        String courseName = "관악산 등산로 A";
        Local local = new Local(1L,"서울특별시 관악구");
        Mountain mountain = new Mountain(1L,"관악산",local);
        Course course = new Course(1L,courseName,"[37.12345678, 118.12345678]","하","145",mountain);
        given(courseService.getDetailCourse(Mockito.anyString())).willReturn(course);
        ResultActions actions = mockMvc.perform(
                get("/course/{courseName}", courseName)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("courseName").value(courseName))
                .andExpect(jsonPath("courseLocation").value(course.getCourseLocation()))
                .andExpect(jsonPath("courseLevel").value(course.getCourseLevel()))
                .andExpect(jsonPath("courseDistance").value(course.getCourseDistance()))
                .andExpect(jsonPath("mountain.id").value(1L))
                .andExpect(jsonPath("mountain.mountainName").value(mountain.getMountainName()))
                .andExpect(jsonPath("mountain.local.id").value(1L))
                .andExpect(jsonPath("mountain.local.localName").value(local.getLocalName()))
                .andDo(document(
                        "get-courseDetail",
                        pathParameters(
                                parameterWithName("courseName").description("코스 이름")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("course_id"),
                                        fieldWithPath("courseName").type(JsonFieldType.STRING).description("코스 이름"),
                                        fieldWithPath("courseLocation").type(JsonFieldType.STRING).description("코스 좌표"),
                                        fieldWithPath("courseLevel").type(JsonFieldType.STRING).description("코스 난이도"),
                                        fieldWithPath("courseDistance").type(JsonFieldType.STRING).description("코스 길이"),
                                        fieldWithPath("mountain").type(JsonFieldType.OBJECT).description("소속 산 객체"),
                                        fieldWithPath("mountain.id").type(JsonFieldType.NUMBER).description("소속 산 id"),
                                        fieldWithPath("mountain.@id").type(JsonFieldType.NUMBER).description("소속 산 @id"),
                                        fieldWithPath("mountain.mountainName").type(JsonFieldType.STRING).description("소속 산 이름"),
                                        fieldWithPath("mountain.local").type(JsonFieldType.OBJECT).description("소속 지역 객체"),
                                        fieldWithPath("mountain.local.id").type(JsonFieldType.NUMBER).description("소속 지역 id"),
                                        fieldWithPath("mountain.local.localName").type(JsonFieldType.STRING).description("소속 지역 이름")
                                        )
                        )
                ));
    }
    @Test
    public void getSelectionCourse() throws Exception {
        String mountainName = "관악산";
        List<CourseDTO> courseList = new ArrayList<>();
        CourseDTO courseDTO1 = new CourseDTO("관악산 등산로 A");
        CourseDTO courseDTO2 = new CourseDTO("관악산 등산로 B");
        CourseDTO courseDTO3 = new CourseDTO("관악산 등산로 C");
        CourseDTO courseDTO4 = new CourseDTO("삼성산 등산로 A");
        CourseDTO courseDTO5 = new CourseDTO("삼성산 등산로 B");
        CourseDTO courseDTO6 = new CourseDTO("청계산 등산로 A");
        courseList.add(courseDTO1);
        courseList.add(courseDTO2);
        courseList.add(courseDTO3);
        courseList.add(courseDTO4);
        courseList.add(courseDTO5);
        courseList.add(courseDTO6);
        given(courseService.getMountainSelectCourse(Mockito.anyString())).willReturn(courseList);
        ResultActions actions = mockMvc.perform(
                get("/course/selection")
                        .param("mountainName", mountainName)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        actions.andExpect(status().isOk())
                .andExpect(jsonPath(".[0].courseName").value(courseDTO1.getCourseName()))
                .andExpect(jsonPath(".[1].courseName").value(courseDTO2.getCourseName()))
                .andExpect(jsonPath(".[2].courseName").value(courseDTO3.getCourseName()))
                .andExpect(jsonPath(".[3].courseName").value(courseDTO4.getCourseName()))
                .andExpect(jsonPath(".[4].courseName").value(courseDTO5.getCourseName()))
                .andExpect(jsonPath(".[5].courseName").value(courseDTO6.getCourseName()))
                .andDo(document(
                        "get-courseSelection",
                        requestParameters(
                                parameterWithName("mountainName").description("산 이름")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("[]").type(JsonFieldType.ARRAY).description("코스 목록"),
                                        fieldWithPath("[0].courseName").type(JsonFieldType.STRING).description("코스 이름"),
                                        fieldWithPath("[1].courseName").type(JsonFieldType.STRING).description("코스 이름"),
                                        fieldWithPath("[2].courseName").type(JsonFieldType.STRING).description("코스 이름"),
                                        fieldWithPath("[3].courseName").type(JsonFieldType.STRING).description("코스 이름"),
                                        fieldWithPath("[4].courseName").type(JsonFieldType.STRING).description("코스 이름"),
                                        fieldWithPath("[5].courseName").type(JsonFieldType.STRING).description("코스 이름")
                                )
                        )
                ));
    }
}
