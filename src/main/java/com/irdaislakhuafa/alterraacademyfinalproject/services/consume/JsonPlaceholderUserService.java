package com.irdaislakhuafa.alterraacademyfinalproject.services.consume;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class JsonPlaceholderUserService {
    private final RestTemplate restTemplate;

    @Value(value = "${json.placeholder.users}")
    private String jsonPlaceholderUserUrl;

    public List<?> findAll() {
        List<Object> list = (List<Object>) restTemplate.getForObject(jsonPlaceholderUserUrl, Object.class);
        return list;
    }
}
