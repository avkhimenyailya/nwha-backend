package io.grayproject.nwha.api.controller.final_pkg;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.grayproject.nwha.api.dto.authentication.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author Ilya Avkhimenya
 */
@SpringBootTest
@AutoConfigureMockMvc
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private AuthResponse authResponse;

    @BeforeEach
    void getAccess() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("username", "nikola3");
        objectNode.put("password", "34234223");

        String jsonString = objectMapper.writeValueAsString(objectNode);
        MockHttpServletRequestBuilder content = post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonString);

        String contentAsString = mockMvc
                .perform(content)
                .andReturn()
                .getResponse()
                .getContentAsString();

        authResponse = objectMapper.readValue(contentAsString, AuthResponse.class);
        System.out.println(authResponse);
    }

    @Test
    void getProfile() {

    }

    @Test
    void getProfileById() {
    }

    @Test
    void getProfileThings() {
    }

    @Test
    void getProfileThingsByProfileId() {
    }

    @Test
    void getProfileCollectionsThings() {
    }

    @Test
    void getProfileCollectionsThingsByProfileId() {
    }

    @Test
    void updateProfile() {
    }
}