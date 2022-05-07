package com.irdaislakhuafa.alterraacademyfinalproject.model.repositories;

import java.util.Optional;

import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public Optional<User> findByEmailEqualsIgnoreCase(String email);
}
