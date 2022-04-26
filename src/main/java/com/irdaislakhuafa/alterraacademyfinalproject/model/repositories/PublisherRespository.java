package com.irdaislakhuafa.alterraacademyfinalproject.model.repositories;

import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Publisher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRespository extends JpaRepository<Publisher, String> {

}
