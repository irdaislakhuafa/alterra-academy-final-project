package com.irdaislakhuafa.alterraacademyfinalproject.services;

import java.util.List;
import java.util.Optional;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.UserDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.User;

import org.springframework.security.core.userdetails.*;

public class UserService implements UserDetailsService, BaseService<User, UserDto> {

    @Override
    public Optional<User> save(User entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<User> update(User entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<User> findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteById(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean existsById(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public User mapToEntity(UserDto dto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> mapToEntities(List<UserDto> dtos) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> findAllById(List<String> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> saveAll(List<User> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

}
