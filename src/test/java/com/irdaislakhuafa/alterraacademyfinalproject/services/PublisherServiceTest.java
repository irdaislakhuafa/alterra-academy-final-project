package com.irdaislakhuafa.alterraacademyfinalproject.services;

import com.irdaislakhuafa.alterraacademyfinalproject.SimpleTestNameGenerator;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.PublisherRespository;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayNameGeneration(value = SimpleTestNameGenerator.class)
@SpringBootTest(classes = { PublisherService.class })
public class PublisherServiceTest {
    @MockBean
    private PublisherRespository publisherRespository;

    @Autowired
    private PublisherService publisherService;

}
