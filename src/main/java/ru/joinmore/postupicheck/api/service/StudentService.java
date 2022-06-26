package ru.joinmore.postupicheck.api.service;

import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.exception.StudentNotFoundException;
import ru.joinmore.postupicheck.api.model.Student;
import ru.joinmore.postupicheck.api.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student getStudent(Long id) {
        return repository.findById(id) //
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public Student createStudent(Student student) {
        return repository.save(student);
    }

    public Student replaceStudent(Student updatedStudent, Long id) {
        Student student = repository.findById(id) //
                .orElseThrow(() -> new StudentNotFoundException(id));
        return replaceStudent(student, updatedStudent);
    }

    public void deleteStudent(Long id) {
        repository.deleteById(id);
    }

    private Student replaceStudent(Student student, Student updatedStudent) {
            student.setName(updatedStudent.getName());
            student.setSnils(updatedStudent.getSnils());
            return repository.save(updatedStudent);
    }
}
