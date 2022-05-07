package com.irdaislakhuafa.alterraacademyfinalproject.services;

import java.util.*;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.UserDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.User;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.UserRepository;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService, BaseService<User, UserDto> {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Optional<User> save(User entity) {
        entity.setPassword(this.passwordEncoder.encode(entity.getPassword()));
        return Optional.of(this.userRepository.save(entity));
    }

    @Override
    public Optional<User> update(User entity) {
        return Optional.of(this.userRepository.save(entity));
    }

    @Override
    public Optional<User> findById(String id) {
        return this.userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public boolean deleteById(String id) {
        if (!this.existsById(id)) {
            throw new NoSuchElementException("user with id: " + id + " not found");
        }
        return true;
    }

    @Override
    public boolean existsById(String id) {
        return this.userRepository.existsById(id);
    }

    @Override
    public User mapToEntity(UserDto dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .roles(new HashSet<>(
                        this.roleService.findByNames(
                                dto.getRoles().toArray(
                                        new String[dto.getRoles().size()]))))
                .build();
    }

    @Override
    public List<User> mapToEntities(List<UserDto> dtos) {
        return dtos.stream().map((dto) -> this.mapToEntity(dto)).collect(Collectors.toList());
    }

    @Override
    public List<User> findAllById(List<String> ids) {
        return this.userRepository.findAllById(ids);
    }

    @Override
    public List<User> saveAll(List<User> entities) {
        return this.userRepository.saveAll(entities);
    }

    @Override
    public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
        return this.userRepository.findByEmailEqualsIgnoreCase(arg0)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("user with email: " + arg0 + " not found");
                });
    }

}
