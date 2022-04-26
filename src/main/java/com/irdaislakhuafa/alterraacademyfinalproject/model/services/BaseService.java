package com.irdaislakhuafa.alterraacademyfinalproject.model.services;

import java.util.List;
import java.util.Optional;

public interface BaseService<A, B> {
    public Optional<A> save(A entity);

    public Optional<A> update(A entity);

    public Optional<A> findById(String id);

    public List<A> findAll();

    public boolean deleteById(String id);

    public boolean existsById(String id);

    public A mapToEntity(B dto);

    public List<A> mapToEntities(List<B> dtos);

    public List<A> findAllById(List<String> ids);
}
