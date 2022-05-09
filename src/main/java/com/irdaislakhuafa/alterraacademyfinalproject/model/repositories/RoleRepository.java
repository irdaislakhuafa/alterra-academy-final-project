package com.irdaislakhuafa.alterraacademyfinalproject.model.repositories;

import java.util.List;
import java.util.Optional;

import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    public Optional<Role> findByNameIgnoreCase(String name);
}
