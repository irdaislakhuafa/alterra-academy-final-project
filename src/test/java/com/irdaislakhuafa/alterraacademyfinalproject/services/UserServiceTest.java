package com.irdaislakhuafa.alterraacademyfinalproject.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.util.*;

import com.irdaislakhuafa.alterraacademyfinalproject.SimpleTestNameGenerator;
import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.UserDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Role;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.User;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.UserRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootTest
@Tag(value = "userServiceTest")
@DisplayNameGeneration(value = SimpleTestNameGenerator.class)
public class UserServiceTest implements BaseServiceTest {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleService roleService;

    @Autowired
    private UserService userService;

    private final Role role = Role.builder()
            .id("roleId")
            .name("ROLE_ADMIN")
            .description("-")
            .build();

    private final User user = User.builder()
            .id("userId")
            .name("irda islakhu afa")
            .email("irdhaislakhuafa@gmail.com")
            .password("anaardani")
            .isEnable(true)
            .roles(new HashSet<>() {
                {
                    add(role);
                }
            })
            .build();

    private final UserDto userDto = UserDto.builder()
            .name(user.getName())
            .email(user.getEmail())
            .password(user.getPassword())
            .roles(List.of(role.getName()))
            .build();

    @Test
    @Override
    public void testSaveSuccess() {
        when(this.userRepository.save(user)).thenReturn(user);
        var result = this.userService.save(user);
        assertTrue(result.isPresent());
    }

    @Test
    @Override
    public void testSaveFailed() {

    }

    @Test
    @Override
    public void testFindByIdSuccess() {
        when(this.userRepository.findById(anyString())).thenReturn(Optional.of(user));
        var result = this.userService.findById("userId");
        assertNotNull(result);
        assertTrue(result.isPresent());
    }

    @Test
    @Override
    public void testFindByIdFailed() {

    }

    @Test
    @Override
    public void testDeleteByIdSuccess() {
        when(this.userRepository.existsById(anyString())).thenReturn(true);
        var result = this.userService.deleteById("userId");
        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    @Override
    public void testDeleteByIdFailed() {
        when(this.userRepository.existsById(anyString())).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> this.userService.deleteById("userId"));
    }

    @Test
    @Override
    public void testFindAllSuccess() {
        when(this.userRepository.findAll()).thenReturn(List.of(user));
        var result = this.userService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @Override
    public void testMapToEntitySuccess() {
        when(this.roleService.findByNames(anyString())).thenReturn(List.of(role));
        var result = this.userService.mapToEntity(userDto);
        assertNotNull(userDto);
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    @Override
    public void testMapToEntityFailed() {
    }

    @Test
    @Override
    public void testMapToEntitiesSuccess() {
        var result = this.userService.mapToEntities(List.of(userDto));
        assertNotNull(userDto);
        assertEquals(1, result.size());
    }

    @Test
    @Override
    public void testMapToEntitiesFailed() {

    }

    @Test
    @Override
    public void testSaveAllSuccess() {
        when(this.userRepository.saveAll(anyList())).thenReturn(List.of(user));
        var result = this.userService.saveAll(List.of(user));
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @Override
    public void testSaveAllFailed() {

    }

    @Test
    @Override
    public void testFindAllByIdSuccess() {
        when(this.userRepository.findAllById(anyList())).thenReturn(List.of(user));
        var result = this.userService.findAllById(List.of("userId"));
        assertEquals(1, result.size());
        assertNotNull(result);
    }

    @Test
    @Override
    public void testUpdateSuccess() {
        when(this.userRepository.save(any())).thenReturn(user);
        when(this.userRepository.findByEmailEqualsIgnoreCase(anyString())).thenReturn(Optional.of(user));
        var result = this.userService.update(user);
        assertTrue(result.isPresent());
    }

    @Test
    @Override
    public void testUpdateFailed() {
        when(this.userRepository.findByEmailEqualsIgnoreCase(anyString())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> this.userService.update(user));
    }

    @Test
    public void testFindByEmailSuccess() {
        when(this.userRepository.findByEmailEqualsIgnoreCase(anyString())).thenReturn(Optional.of(user));
        var result = this.userService.findByEmail("irdhaislakhuafa@gmail.com");
        assertTrue(result.isPresent());
    }

    @Test
    public void loadByUsernameSuccess() {
        when(this.userRepository.findByEmailEqualsIgnoreCase(anyString())).thenReturn(Optional.of(user));
        var result = this.userService.loadUserByUsername("irdhaislakhuafa@gmail.com");
        assertNotNull(result);
        assertEquals(user.getEmail(), result.getUsername());
    }

    @Test
    public void loadByUsernameFailed() {
        when(this.userRepository.findByEmailEqualsIgnoreCase(anyString())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,
                () -> this.userService.loadUserByUsername("irdhaislakhuafa@gmail.com"));

    }

}
