package ru.joinmore.postupicheck.api.services;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.repositories.StudentRepository;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAll() {
        return repository.findAll();
    }

    public Student get(long id) {
        return repository.findById(id) //
                .orElseThrow(() -> new ResourceNotExistsException("Student with id" + id));
    }

    public Student create(Student student) {
        Boolean existsSnils = repository.
                existsStudentBySnils(student.getSnils());
        if (existsSnils) {
            throw new AlreadyExistsException("Snils");
        }
        return repository.save(student);
    }

    public Student replace(Student updatedStudent, long id) {
        Student student = repository.findById(id) //
                .orElseThrow(() -> new ResourceNotExistsException("Student with id " + id));
        return replaceStudent(student, updatedStudent);
    }

    public void delete(long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotExistsException("Student with id " + id);
        }
    }

    private Student replaceStudent(Student student, Student updatedStudent) {
            student.setName(updatedStudent.getName());
            student.setSnils(updatedStudent.getSnils());
            return repository.save(student);
    }
}
