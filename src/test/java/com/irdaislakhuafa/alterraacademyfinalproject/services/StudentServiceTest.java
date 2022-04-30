package com.irdaislakhuafa.alterraacademyfinalproject.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.StudentDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Address;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Student;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.StudentRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class StudentServiceTest implements BaseServiceTest {
    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    private final Address address = Address.builder()
            .id("id")
            .city("tuban")
            .country("indonesia")
            .build();

    private final Student student = Student.builder()
            .id("id")
            .name("irda islakhu afa")
            .email("irdhaislakhuafa@gmail.com")
            .semester(Byte.valueOf("6"))
            .batchOfYears(2019)
            .addresses(List.of(address))
            .build();

    private final StudentDto studentDto = StudentDto.builder()
            .name(student.getName())
            .email(student.getEmail())
            .semester(student.getSemester())
            .batchOfYears(student.getBatchOfYears())
            .build();

    @Test
    @Override
    public void testSaveSuccess() {
        when(this.studentRepository.save(any())).thenReturn(student);
        assertTrue(this.studentService.save(student).isPresent());
    }

    @Test
    @Override
    public void testSaveFailed() {
        when(this.studentRepository.save(any())).thenReturn(null);
        assertThrows(NullPointerException.class, () -> this.studentService.save(student));
    }

    @Test
    @Override
    public void testFindByIdSuccess() {
        when(this.studentRepository.findById(anyString())).thenReturn(Optional.of(student));
        assertTrue(this.studentService.findById("id").isPresent());
    }

    @Test
    @Override
    public void testFindByIdFailed() {
        when(this.studentRepository.findById(anyString())).thenReturn(Optional.empty());
        assertFalse(this.studentService.findById("id").isPresent());
    }

    @Test
    @Override
    public void testDeleteByIdSuccess() {
        when(this.studentRepository.existsById(anyString())).thenReturn(true);
        assertTrue(this.studentService.deleteById("id"));
    }

    @Test
    @Override
    public void testDeleteByIdFailed() {
        when(this.studentRepository.existsById(anyString())).thenReturn(false);
        assertFalse(this.studentService.deleteById("id"));
    }

    @Test
    @Override
    public void testFindAllSuccess() {
        when(this.studentRepository.findAll()).thenReturn(List.of(student));
        var result = this.studentService.findAll();
        assertNotNull(result);
        assertEquals(List.of(student), result);
    }

    @Test
    @Override
    public void testMapToEntitySuccess() {
        var mappedStudent = this.studentService.mapToEntity(studentDto);
        assertNotNull(mappedStudent);
        assertNotEquals(student.getId(), mappedStudent.getId());
        assertEquals(student.getName(), mappedStudent.getName());
        assertEquals(student.getEmail(), mappedStudent.getEmail());
        assertEquals(student.getSemester(), mappedStudent.getSemester());
        assertEquals(student.getBatchOfYears(), mappedStudent.getBatchOfYears());
        assertNotEquals(student.getAddresses(), mappedStudent.getAddresses());
    }

    @Test
    @Override
    public void testMapToEntityFailed() {
        assertThrows(NullPointerException.class, () -> this.studentService.mapToEntity(null));
    }

    @Test
    @Override
    public void testMapToEntitiesSuccess() {
        var mappedEntities = this.studentService.mapToEntities(List.of(studentDto));
        assertNotNull(mappedEntities);
        assertEquals(student.getEmail(), mappedEntities.get(0).getEmail());
    }

    @Test
    @Override
    public void testMapToEntitiesFailed() {

    }

    @Test
    @Override
    public void testSaveAllSuccess() {

    }

    @Test
    @Override
    public void testSaveAllFailed() {

    }

    @Test
    @Override
    public void testFindAllByIdSuccess() {

    }

    @Test
    @Override
    public void testUpdateSuccess() {

    }

    @Test
    @Override
    public void testUpdateFailed() {

    }

}
