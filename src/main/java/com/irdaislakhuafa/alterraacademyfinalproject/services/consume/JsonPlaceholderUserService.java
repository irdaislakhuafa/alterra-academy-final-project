package com.irdaislakhuafa.alterraacademyfinalproject.services.consume;

import java.util.*;

import javax.transaction.Transactional;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.jsonplaceholder.User;

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

    public List<User> findAll() {
        List<User> list = (List<User>) restTemplate.getForObject(jsonPlaceholderUserUrl, Object.class);
        return list;
    }

    public Optional<User> findByEmail(String email) throws NoSuchElementException {
        var user = this.findAll().stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst();
        if (!user.isPresent()) {
            throw new NoSuchElementException("user with email: " + email + " not found in jsonplaceholder");
        }
        return user;
    }
}
