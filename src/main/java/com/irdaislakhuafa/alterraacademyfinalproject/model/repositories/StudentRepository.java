package com.irdaislakhuafa.alterraacademyfinalproject.model.repositories;

import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

}
