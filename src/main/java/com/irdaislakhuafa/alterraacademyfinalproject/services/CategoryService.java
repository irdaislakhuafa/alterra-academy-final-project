package com.irdaislakhuafa.alterraacademyfinalproject.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.irdaislakhuafa.alterraacademyfinalproject.services.utils.LogMessage.*;

import javax.transaction.Transactional;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.CategoryDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Category;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.CategoryRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService implements BaseService<Category, CategoryDto> {
    private final CategoryRepository categoryRepository;

    @Override
    public Optional<Category> save(Category entity) {
        logSave(entity);
        var savedCategory = Optional.of(categoryRepository.save(entity));
        logSuccessSave(entity);
        return savedCategory;
    }

    @Override
    public Optional<Category> update(Category entity) {
        logUpdate(entity);
        var updatedCategory = Optional.of(categoryRepository.save(entity));
        logSuccessUpdate(entity);
        return updatedCategory;
    }

    @Override
    public Optional<Category> findById(String id) {
        logPrepareFindById(Category.builder().id(id).build());
        var category = categoryRepository.findById(id);

        if (!category.isPresent()) {
            logEntityNotFound(Category.builder().id(id).build());
            return Optional.empty();
        }
        logEntityFound(Category.builder().id(id).build());
        return category;
    }

    @Override
    public List<Category> findAll() {
        logFindAll(new Category());
        var allCategory = categoryRepository.findAll();
        logFindAll(new Category());
        return allCategory;
    }

    @Override
    public boolean deleteById(String id) {
        if (!this.existsById(id)) {
            logEntityNotFound(Category.builder().id(id).build());
            return false;
        }
        categoryRepository.deleteById(id);
        logSuccessDelete(Category.builder().id(id).build());
        return true;
    }

    @Override
    public boolean existsById(String id) {
        return categoryRepository.existsById(id);
    }

    @Override
    public Category mapToEntity(CategoryDto dto) {
        logMapDtoToEntity(new Category());
        var category = Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
        logSuccessMapDtoToEntity(category);
        return category;
    }

    @Override
    public List<Category> mapToEntities(List<CategoryDto> dtos) {
        return dtos.stream().map((dto) -> this.mapToEntity(dto)).collect(Collectors.toList());
    }

    @Override
    public List<Category> findAllById(List<String> ids) {
        return categoryRepository.findAllById(ids);
    }

    @Override
    public List<Category> saveAll(List<Category> entities) {
        return categoryRepository.saveAll(entities);
    }

}
