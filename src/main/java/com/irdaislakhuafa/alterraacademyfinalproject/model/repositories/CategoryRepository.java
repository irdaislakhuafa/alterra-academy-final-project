package com.irdaislakhuafa.alterraacademyfinalproject.model.repositories;

import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

}
