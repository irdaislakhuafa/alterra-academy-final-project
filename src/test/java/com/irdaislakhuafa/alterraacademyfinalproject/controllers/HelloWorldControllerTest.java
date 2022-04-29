package com.irdaislakhuafa.alterraacademyfinalproject.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.web.client.RestTemplate;

@WebMvcTest(controllers = HelloWorldController.class)
// @ExtendWith(value = { SpringExtension.class, MockitoExtension.class })
public class HelloWorldControllerTest {
    @MockBean
    public RestTemplate restTemplate;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHelloWorld() throws Exception {
        Assertions.assertNotNull(restTemplate);

        mockMvc.perform(get("/hello-world")).andExpect(status().isOk());
    }
}
