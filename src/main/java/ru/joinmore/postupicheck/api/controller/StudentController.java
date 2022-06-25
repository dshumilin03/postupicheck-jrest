package ru.joinmore.postupicheck.api.controller;

import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.exception.StudentNotFoundException;
import ru.joinmore.postupicheck.api.model.Student;
import ru.joinmore.postupicheck.api.repository.StudentRepository;

import java.util.List;


@RestController
public class StudentController {

    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/students")
    List<Student> all() {
        return repository.findAll();
    }

    @PostMapping("/students")
    Student newStudent(@RequestBody Student newStudent) {
       return repository.save(newStudent);
    }

    @GetMapping("/students/{id}")
    Student one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));}


    @PutMapping("/students/{id}")
    Student replaceStudent(@RequestBody Student updatedStudent, Long id) {
        return repository.findById(id) //
                .map(student -> {
                student.setName(updatedStudent.getName());
                student.setSnils(updatedStudent.getSnils());
                    return repository.save(updatedStudent);
                })
                .orElseGet(() -> {
                    updatedStudent.setId(id);
                    return repository.save(updatedStudent);
                });
    }

    @DeleteMapping("/students/{id}")
    void deleteStudent(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
