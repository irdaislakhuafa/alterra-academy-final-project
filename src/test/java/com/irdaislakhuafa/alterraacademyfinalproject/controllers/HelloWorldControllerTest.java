package com.irdaislakhuafa.alterraacademyfinalproject.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@Slf4j
public class HelloWorldControllerTest {
    @Test
    public void testHelloWorld() {
        log.info("Start test hello world controller");
        Assertions.assertEquals("Hello World", "Hello World");
    }
}
