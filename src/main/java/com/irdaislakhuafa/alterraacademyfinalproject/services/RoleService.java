package com.irdaislakhuafa.alterraacademyfinalproject.services;

import java.util.*;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.RoleDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Role;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.RoleRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService implements BaseService<Role, RoleDto> {
    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> save(Role entity) {
        return Optional.of(this.roleRepository.save(entity));
    }

    @Override
    public Optional<Role> update(Role entity) {
        return this.save(entity);
    }

    @Override
    public Optional<Role> findById(String id) {
        return this.roleRepository.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }

    @Override
    public boolean deleteById(String id) {
        if (!this.existsById(id)) {
            throw new NoSuchElementException("role with id: " + id + " not found");
        }
        this.roleRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean existsById(String id) {
        return this.roleRepository.existsById(id);
    }

    @Override
    public Role mapToEntity(RoleDto dto) {
        return Role.builder()
                .name(dto.getName().toUpperCase())
                .description(dto.getDescription())
                .build();
    }

    @Override
    public List<Role> mapToEntities(List<RoleDto> dtos) {
        return dtos.stream().map((dto) -> this.mapToEntity(dto)).collect(Collectors.toList());
    }

    @Override
    public List<Role> findAllById(List<String> ids) {
        return this.roleRepository.findAllById(ids);
    }

    @Override
    public List<Role> saveAll(List<Role> entities) {
        return this.roleRepository.saveAll(entities);
    }

    public List<Role> findByNames(String... names) {
        var roles = new HashSet<Role>();
        for (String name : names) {
            var role = this.roleRepository.findByNameIgnoreCase(name);
            if (!role.isPresent()) {
                throw new NoSuchElementException("role with name: " + name + " not found");
            }
            roles.add(role.get());
        }
        return new ArrayList<>(roles);
    }

}
