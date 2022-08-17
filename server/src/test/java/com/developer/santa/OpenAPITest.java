package com.developer.santa;


import com.developer.santa.api.apiService.ApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
public class OpenAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ApiService apiService;

    @Test
    @DisplayName("getLocal")
    public void getLocal() throws Exception {

    }

    @Test
    @DisplayName("getMountain")
    public void getMountain() throws Exception {

    }

    @Test
    @DisplayName("getLoad")
    public void getLoad() throws Exception {

    }
}
