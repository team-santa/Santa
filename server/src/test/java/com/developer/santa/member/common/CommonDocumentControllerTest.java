package com.developer.santa.member.common;

import com.developer.santa.member.config.RestDocsTestSupport;
import com.developer.santa.member.oauth.entity.ProviderType;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;

import java.util.Arrays;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommonDocumentControllerTest extends RestDocsTestSupport {

    // Pathparameter의 정보를 제공하기 위해 새로운 snippet 생성
    @Test
    public void providerId() throws Exception {
        mock.perform(get("/docs"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        customResponseFields("custom-response", beneathPath("provider").withSubsectionId("provider"),attributes(key("title").value("제공자 Id")),
                                enumConvertFieldDescriptor(ProviderType.values()))
                ));
    }

    private FieldDescriptor[] enumConvertFieldDescriptor(ProviderType[] enumTypes) {
        return Arrays.stream(enumTypes)
                .map(enumType -> fieldWithPath(enumType.getProviderId()).description(enumType.getDecription()))
                .toArray(FieldDescriptor[]::new);
    }

    public static CustomResponseFieldsSnippet customResponseFields(String type,
                                                                   PayloadSubsectionExtractor<?> subsectionExtractor,
                                                                   Map<String, Object> attributes, FieldDescriptor... descriptors) {
        return new CustomResponseFieldsSnippet(type, subsectionExtractor, Arrays.asList(descriptors), attributes, true);
    }
}
