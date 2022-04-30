package com.irdaislakhuafa.alterraacademyfinalproject.services;

import static com.irdaislakhuafa.alterraacademyfinalproject.utils.LogMessage.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.AddressDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.dtos.StudentDto;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Student;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.StudentRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StudentService implements BaseService<Student, StudentDto> {
    private final StudentRepository studentRepository;
    private final AddressService addressService;

    @Override
    public Optional<Student> save(Student entity) {
        logSave(entity);
        var savedStudent = Optional.of(this.studentRepository.save(entity));
        logSuccessSave(entity);
        return savedStudent;
    }

    @Override
    public Optional<Student> update(Student entity) {
        logUpdate(entity);
        var updatedStudent = Optional.of(this.studentRepository.save(entity));
        logSuccessUpdate(entity);
        return updatedStudent;
    }

    @Override
    public Optional<Student> findById(String id) {
        logPrepareFindById(new Student());
        var student = this.studentRepository.findById(id);

        if (!student.isPresent()) {
            logEntityNotFound(new Student());
            return Optional.empty();
        }

        logEntityFound(new Student());
        return student;
    }

    @Override
    public List<Student> findAll() {
        logFindAll(new Student());
        var students = this.studentRepository.findAll();
        logSuccessFindAll(new Student());
        return students;
    }

    @Override
    public boolean deleteById(String id) {
        if (this.existsById(id)) {
            this.studentRepository.deleteById(id);
            logSuccessDelete(new Student());
            return true;
        }

        logEntityNotFound(new Student());
        return false;
    }

    @Override
    public boolean existsById(String id) {
        return this.studentRepository.existsById(id);
    }

    @Override
    public Student mapToEntity(StudentDto dto) {
        logMapDtoToEntity(new Student());
        var student = Student.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .semester(dto.getSemester())
                .batchOfYears(dto.getBatchOfYears())
                .build();
        logSuccessMapDtoToEntity(student);
        return student;
    }

    @Override
    public List<Student> mapToEntities(List<StudentDto> dtos) {
        return dtos.stream().map((dto) -> this.mapToEntity(dto)).collect(Collectors.toList());
    }

    @Override
    public List<Student> findAllById(List<String> ids) {
        return this.studentRepository.findAllById(ids);
    }

    @Override
    public List<Student> saveAll(List<Student> entities) {
        return this.studentRepository.saveAll(entities);
    }

    public Optional<Student> addAddress(Optional<Student> targetUpdate, List<AddressDto> addressDtos) {
        var savedAddresses = this.addressService.saveAll(this.addressService.mapToEntities(addressDtos));
        var addresses = targetUpdate.get().getAddresses();
        // var newAddress = new ArrayList<Address>();

        savedAddresses.addAll(addresses);

        targetUpdate.get().setAddresses(savedAddresses);
        targetUpdate = this.update(targetUpdate.get());
        log.info("Success add address for student");
        return targetUpdate;
    }
}
