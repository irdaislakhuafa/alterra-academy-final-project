package com.irdaislakhuafa.alterraacademyfinalproject.services;

import javax.transaction.Transactional;

import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.CategoryRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService implements {
    private final CategoryRepository categoryRepository;

}
