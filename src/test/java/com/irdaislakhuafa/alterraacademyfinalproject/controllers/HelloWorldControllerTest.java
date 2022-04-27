package com.irdaislakhuafa.alterraacademyfinalproject.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(value = { SpringExtension.class, MockitoExtension.class })
public class HelloWorldControllerTest {
    @MockBean
    public RestTemplate restTemplate;

    @Test
    public void testHelloWorld() {
        Assertions.assertNotNull(restTemplate);
    }
}
