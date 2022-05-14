package com.irdaislakhuafa.alterraacademyfinalproject.services.consume;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.NoSuchElementException;

import com.irdaislakhuafa.alterraacademyfinalproject.SimpleTestNameGenerator;
import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.jsonplaceholder.User;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

@DisplayNameGeneration(value = SimpleTestNameGenerator.class)
@Tag(value = "jsonPlaceholderUserServiceTest")
@SpringBootTest
public class JsonPlaceholderUserServiceTest {
    @MockBean
    private RestTemplate restTemplate;

    private String url = "https://jsonplaceholder.typicode.com/users";

    @Autowired
    private JsonPlaceholderUserService jpUserService;

    private final User user = User.builder()
            .name("irda islakhu afa")
            .username("irdaislakhuafa")
            .email("irdhaislakhuafa@gmail.com")
            .build();

    @Test
    public void testFindAllSuccess() {
        when(restTemplate.getForObject(url, User[].class))
                .thenReturn(Arrays.asList(user).toArray(new User[1]));

        var result = jpUserService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testFindByEmailSuccess() {
        when(restTemplate.getForObject(url, User[].class))
                .thenReturn(Arrays.asList(user).toArray(new User[1]));

        var result = jpUserService.findByEmail(user.getEmail());
        assertNotNull(result);
        assertTrue(result.isPresent());
    }

    @Test
    public void testFindByEmailFailed() {
        when(restTemplate.getForObject(url, User[].class))
                .thenReturn(Arrays.asList(user).toArray(new User[1]));
        assertThrows(NoSuchElementException.class, () -> jpUserService.findByEmail("emailNotFound@gmail.com"));
    }
}
