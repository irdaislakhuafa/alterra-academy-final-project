package com.irdaislakhuafa.alterraacademyfinalproject.model.repositories;

import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}
