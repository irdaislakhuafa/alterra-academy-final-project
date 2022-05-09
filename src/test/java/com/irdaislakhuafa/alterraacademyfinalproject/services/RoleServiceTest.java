package com.irdaislakhuafa.alterraacademyfinalproject.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.util.*;

import com.irdaislakhuafa.alterraacademyfinalproject.SimpleTestNameGenerator;
import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.RoleDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Role;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.RoleRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@Tag(value = "roleServiceTest")
@SpringBootTest
@DisplayNameGeneration(value = SimpleTestNameGenerator.class)
public class RoleServiceTest implements BaseServiceTest {
    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    private final Role role = Role.builder()
            .id("roleId")
            .name("ADMIN")
            .description("-")
            .build();

    private final RoleDto roleDto = RoleDto.builder()
            .name(role.getName())
            .description(role.getDescription())
            .build();

    @Test
    @Override
    public void testSaveSuccess() {
        when(this.roleRepository.save(any())).thenReturn(role);
        var result = this.roleService.save(role);
        assertTrue(result.isPresent());
    }

    @Test
    @Override
    public void testSaveFailed() {

    }

    @Test
    @Override
    public void testFindByIdSuccess() {
        when(this.roleRepository.findById(any())).thenReturn(Optional.of(role));
        var result = this.roleService.findById("roleId");
        assertTrue(result.isPresent());
    }

    @Test
    @Override
    public void testFindByIdFailed() {

    }

    @Test
    @Override
    public void testDeleteByIdSuccess() {
        when(this.roleRepository.existsById(any())).thenReturn(true);
        var result = this.roleService.deleteById("roleId");
        assertTrue(result);
    }

    @Test
    @Override
    public void testDeleteByIdFailed() {
        when(this.roleRepository.existsById(any())).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> this.roleService.deleteById("roleId"));
    }

    @Test
    @Override
    public void testFindAllSuccess() {
        when(this.roleRepository.findAll()).thenReturn(List.of(role));
        var result = this.roleService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    @Override
    public void testMapToEntitySuccess() {
        var result = this.roleService.mapToEntity(roleDto);
        assertEquals(role.getName(), result.getName());
    }

    @Test
    @Override
    public void testMapToEntityFailed() {

    }

    @Test
    @Override
    public void testMapToEntitiesSuccess() {
        var result = this.roleService.mapToEntities(List.of(roleDto));
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @Override
    public void testMapToEntitiesFailed() {

    }

    @Test
    @Override
    public void testSaveAllSuccess() {
        when(this.roleRepository.saveAll(anyList())).thenReturn(List.of(role));
        var result = this.roleService.saveAll(List.of(role));
        assertEquals(1, result.size());
    }

    @Test
    @Override
    public void testSaveAllFailed() {
        // TODO Auto-generated method stub

    }

    @Test
    @Override
    public void testFindAllByIdSuccess() {
        when(this.roleRepository.findAllById(anyList())).thenReturn(List.of(role));
        var result = this.roleService.findAllById(List.of(role.getId()));
        assertEquals(1, result.size());
    }

    @Test
    @Override
    public void testUpdateSuccess() {
        when(this.roleRepository.save(any())).thenReturn(role);
        var result = this.roleService.update(role);
        assertTrue(result.isPresent());
    }

    @Test
    @Override
    public void testUpdateFailed() {

    }

    @Test
    public void testFindAllByNamesSuccess() {
        when(this.roleRepository.findByNameIgnoreCase(anyString())).thenReturn(Optional.of(role));
        var result = this.roleService.findByNames("ADMIN");
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testFindAllByNamesFailed() {
        when(this.roleRepository.findByNameIgnoreCase(anyString())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> this.roleService.findByNames("ADMIN"));
    }

}
