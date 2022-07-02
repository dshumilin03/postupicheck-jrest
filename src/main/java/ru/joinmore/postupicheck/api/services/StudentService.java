package ru.joinmore.postupicheck.api.services;

import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotFoundException;
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
                .orElseThrow(ResourceNotFoundException::new);
    }

    public Student create(Student student) {
        return repository.save(student);
    }

    public Student replace(Student updatedStudent, long id) {
        Student student = repository.findById(id) //
                .orElseThrow(ResourceNotFoundException::new);
        return replaceStudent(student, updatedStudent);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    private Student replaceStudent(Student student, Student updatedStudent) {
            student.setName(updatedStudent.getName());
            student.setSnils(updatedStudent.getSnils());
            return repository.save(student);
    }
}
